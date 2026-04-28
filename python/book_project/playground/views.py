import json
import math
import time

from django.db import transaction
from django.db.models import Count, Q
from django.http import JsonResponse
from django.shortcuts import render
from django.views.decorators.csrf import csrf_exempt

from circulation.models import Loan
from manager.models import Book
from playground.models import PracticeTask, ConcurrencyLabItem, ConcurrencyLabEvent


# lake go
# 这个是get方法
@csrf_exempt
def api_drill_tasks(request):
    if (request.method == "GET"):
        queryset = PracticeTask.objects.all()
        title = request.GET.get("title")
        topic = request.GET.get("topic")
        status = request.GET.get("status")

        keyword = (request.GET.get("q") or "").strip()
        ordering = (request.GET.get("ordering") or "-updated_at").strip()
        page = _safe_int(request.GET.get("page"), 1)
        page_size = _safe_int(request.GET.get("page_size"), 10)

        if page < 1:
            page = 1
        if page_size < 1:
            page_size = 10
        page_size = min(page_size, 100)

        allowed_order_fields = {"id", "title", "topic", "status", "created_at", "updated_at"}
        normalized_order_field = ordering[1:] if ordering.startswith("-") else ordering
        if normalized_order_field not in allowed_order_fields:
            return JsonResponse({"detail": "invalid ordering field"}, status=400)

        if title:
            queryset = queryset.filter(title__icontains=title)
        if topic:
            queryset = queryset.filter(topic=topic)
        if status:
            queryset = queryset.filter(status=status)
        if keyword:
            queryset = queryset.filter(
                Q(title__icontains=keyword) |
                Q(notes__icontains=keyword)
            )

        queryset = queryset.order_by(ordering)
        total = queryset.count()
        start = (page - 1) * page_size
        end = start + page_size
        page_items = queryset[start:end]

        return JsonResponse(
            {
                "results": [_task_to_dict(task) for task in page_items],
                "pagination": {
                    "page": page,
                    "page_size": page_size,
                    "total": total,
                    "total_pages": (total + page_size - 1) // page_size,
                },
                "filters": {
                    "title": title or "",
                    "topic": topic or "",
                    "status": status or "",
                    "q": keyword,
                    "ordering": ordering,
                },
            }
        )

    if (request.method == "POST"):
        payload = _parse_json_body(request)
        if payload is None:
            return JsonResponse({"detail": "Invalid JSON body"}, status=400)
        title = payload.get("title")
        topic = payload.get("topic")
        status = payload.get("status")

        if not title:
            return JsonResponse({"detail": "title is required"}, status=400)
        if not topic:
            return JsonResponse({"detail": "topic is required"}, status=400)
        if not status:
            return JsonResponse({"detail": "status is required"}, status=400)

        task = PracticeTask.objects.create(title=title, topic=topic, status=status)
        return JsonResponse(_task_to_dict(task), status=201)
    return JsonResponse({"detail": "Method not allowed"}, status=405)


@csrf_exempt
def api_drill_task_detail(request, task_id):
    task = PracticeTask.objects.filter(id=task_id).first()
    if not task:
        return JsonResponse({"detail": "Task not found"}, status=404)

    if (request.method == "PUT"):
        payload = _parse_json_body(request)
        if payload is None:
            return JsonResponse({"detail": "Invalid JSON body"}, status=400)
        title = payload.get("title")
        status = payload.get("status")
        if title is not None:
            if not title:
                return JsonResponse({"detail": "title is required"}, status=400)
            task.title = title
        if status is not None:
            if status not in PracticeTask.Status.values:
                return JsonResponse({"detail": "status is invalid"}, status=400)
            task.status = status

        task.save()
        return JsonResponse(_task_to_dict(task), status=200)

    if (request.method == "DELETE"):
        task.delete()
        return JsonResponse(
            {"detail": "Task deleted successfully"}, status=200
        )
    return JsonResponse({"detail": "Method not allowed"}, status=405)


def _event_to_dict(event):
    return {
        "id": event.id,
        "mode": event.mode,
        "actor": event.actor,
        "requested_qty": event.requested_qty,
        "success": event.success,
        "before_stock": event.before_stock,
        "after_stock": event.after_stock,
        "message": event.message,
        "created_at": event.created_at.isoformat(),
    }


def _item_to_dict(item):
    return {
        "id": item.id,
        "name": item.name,
        "stock": item.stock,
        "updated_at": item.updated_at.isoformat(),
    }


@csrf_exempt
def api_concurrency_setup(request):
    if request.method != "POST":
        return JsonResponse({"detail": "Method not allowed"}, status=405)

    payload = _parse_json_body(request) or {}
    name = (payload.get("name") or "book-atomic-lab").strip() or "book-atomic-lab"
    initial_stock = _safe_int(payload.get("stock"), 1)
    if initial_stock < 0:
        return JsonResponse({"detail": "stock must be >= 0"}, status=400)

    item, _created = ConcurrencyLabItem.objects.get_or_create(name=name, defaults={"stock": initial_stock})
    item.stock = initial_stock
    item.save(update_fields=["stock", "updated_at"])
    ConcurrencyLabEvent.objects.filter(item=item).delete()

    return JsonResponse(
        {
            "message": "lab reset",
            "item": _item_to_dict(item),
        }
    )


# setup：重置库存和日志
@csrf_exempt
def api_student_concurrency_setup(request):
    if request.method != "POST":
        return JsonResponse({"detail": "Method not allowed"}, status=405)
    payload = _parse_json_body(request) or {}

    name = (payload.get("name") or "book-atomic-lab").strip() or "book-atomic-lab"
    initial_stock = _safe_int(payload.get("stock"), 1)
    if initial_stock < 0:
        return JsonResponse({"detail": "stock must be >= 0"}, status=400)

    item, _created = ConcurrencyLabItem.objects.get_or_create(name=name, defaults={"stock": initial_stock})
    item.stock = initial_stock
    item.save(update_fields=["stock", "updated_at"])
    ConcurrencyLabEvent.objects.filter(item=item).delete()

    return JsonResponse(
        {
            "message": "lab reset",
            "item": _item_to_dict(item),
        }
    )


# unlocked：普通读库存再扣库存（不加锁）
@csrf_exempt
def api_student_concurrency_borrow_unlocked(request):
    if request.method != "POST":
        return JsonResponse({"detail": "Method not allowed"}, status=405)

    payload = _parse_json_body(request) or {}
    item_id = _safe_int(payload.get("item_id"), 0)
    qty = max(1, _safe_int(payload.get("qty"), 1))
    actor = (payload.get("actor") or "anonymous").strip() or "anonymous"
    delay_ms = max(0, min(_safe_int(payload.get("delay_ms"), 0), 5000))

    if item_id <= 0:
        return JsonResponse({"detail": "item_id must be a positive integer"}, status=400)

    _event, response = _borrow_without_lock(item_id=item_id, qty=qty, actor=actor, delay_ms=delay_ms)
    return response


# locked：transaction.atomic + select_for_update
@csrf_exempt
def api_student_concurrency_borrow_locked(request):
    if (request.method != "POST"):
        return JsonResponse({"detail": "Method not allowed"}, status=405)

    payload = _parse_json_body(request) or {}
    item_id = _safe_int(payload.get("item_id"), 0)
    qty = max(1, _safe_int(payload.get("qty"), 1))
    actor = (payload.get("actor") or "anonymous").strip() or "anonymous"
    delay_ms = max(0, min(_safe_int(payload.get("delay_ms"), 0), 5000))

    if item_id <= 0:
        return JsonResponse({"detail": "item_id must be a positive integer"}, status=400)

    _event, response = _borrow_with_lock(item_id=item_id, qty=qty, actor=actor, delay_ms=delay_ms)
    return response

# state：返回当前库存 + 最近日志
def api_student_concurrency_state(request):
    if request.method != "GET":
        return JsonResponse({"detail": "Method not allowed"}, status=405)

    item_id = _safe_int(request.GET.get("item_id"), 0)
    if item_id <= 0:
        return JsonResponse({"detail": "item_id must be a positive integer"}, status=400)

    item = ConcurrencyLabItem.objects.filter(id=item_id).first()
    if not item:
        return JsonResponse({"detail": "item not found"}, status=404)

    events = ConcurrencyLabEvent.objects.filter(item_id=item_id)[:30]
    return JsonResponse(
        {
            "item": _item_to_dict(item),
            "events": [_event_to_dict(event) for event in events],
        }
    )


# 这是不带锁的，测试用的；好像就删掉了with transaction.atomic()，select_for_update
def _borrow_without_lock(item_id, qty, actor, delay_ms):
    item = ConcurrencyLabItem.objects.filter(id=item_id).first()
    if not item:
        return None, JsonResponse({"detail": "item not found"}, status=404)

    before_stock = item.stock
    if delay_ms > 0:
        time.sleep(delay_ms / 1000)

    if before_stock < qty:
        event = ConcurrencyLabEvent.objects.create(
            item=item,
            mode=ConcurrencyLabEvent.Mode.UNLOCKED,
            actor=actor,
            requested_qty=qty,
            success=False,
            before_stock=before_stock,
            after_stock=before_stock,
            message="insufficient_stock",
        )
        return event, JsonResponse({"ok": False, "event": _event_to_dict(event)})

    item.stock = before_stock - qty
    item.save(update_fields=["stock", "updated_at"])
    event = ConcurrencyLabEvent.objects.create(
        item=item,
        mode=ConcurrencyLabEvent.Mode.UNLOCKED,
        actor=actor,
        requested_qty=qty,
        success=True,
        before_stock=before_stock,
        after_stock=item.stock,
        message="borrowed",
    )
    return event, JsonResponse({"ok": True, "event": _event_to_dict(event), "item": _item_to_dict(item)})


def _borrow_with_lock(item_id, qty, actor, delay_ms):
    with transaction.atomic():
        item = ConcurrencyLabItem.objects.select_for_update().filter(id=item_id).first()
        if not item:
            return None, JsonResponse({"detail": "item not found"}, status=404)

        before_stock = item.stock
        if delay_ms > 0:
            time.sleep(delay_ms / 1000)

        if before_stock < qty:
            event = ConcurrencyLabEvent.objects.create(
                item=item,
                mode=ConcurrencyLabEvent.Mode.LOCKED,
                actor=actor,
                requested_qty=qty,
                success=False,
                before_stock=before_stock,
                after_stock=before_stock,
                message="insufficient_stock",
            )
            return event, JsonResponse({"ok": False, "event": _event_to_dict(event)})

        item.stock = before_stock - qty
        item.save(update_fields=["stock", "updated_at"])
        event = ConcurrencyLabEvent.objects.create(
            item=item,
            mode=ConcurrencyLabEvent.Mode.LOCKED,
            actor=actor,
            requested_qty=qty,
            success=True,
            before_stock=before_stock,
            after_stock=item.stock,
            message="borrowed",
        )
        return event, JsonResponse({"ok": True, "event": _event_to_dict(event), "item": _item_to_dict(item)})


@csrf_exempt
def api_concurrency_borrow_unlocked(request):
    if request.method != "POST":
        return JsonResponse({"detail": "Method not allowed"}, status=405)

    payload = _parse_json_body(request) or {}
    item_id = _safe_int(payload.get("item_id"), 0)
    qty = max(1, _safe_int(payload.get("qty"), 1))
    actor = (payload.get("actor") or "anonymous").strip() or "anonymous"
    delay_ms = max(0, min(_safe_int(payload.get("delay_ms"), 0), 5000))

    if item_id <= 0:
        return JsonResponse({"detail": "item_id must be a positive integer"}, status=400)

    _event, response = _borrow_without_lock(item_id=item_id, qty=qty, actor=actor, delay_ms=delay_ms)
    return response


@csrf_exempt
def api_concurrency_borrow_locked(request):
    if request.method != "POST":
        return JsonResponse({"detail": "Method not allowed"}, status=405)

    payload = _parse_json_body(request) or {}
    item_id = _safe_int(payload.get("item_id"), 0)
    qty = max(1, _safe_int(payload.get("qty"), 1))
    actor = (payload.get("actor") or "anonymous").strip() or "anonymous"
    delay_ms = max(0, min(_safe_int(payload.get("delay_ms"), 0), 5000))

    if item_id <= 0:
        return JsonResponse({"detail": "item_id must be a positive integer"}, status=400)

    _event, response = _borrow_with_lock(item_id=item_id, qty=qty, actor=actor, delay_ms=delay_ms)
    return response


def api_concurrency_state(request):
    if request.method != "GET":
        return JsonResponse({"detail": "Method not allowed"}, status=405)

    item_id = _safe_int(request.GET.get("item_id"), 0)
    if item_id <= 0:
        return JsonResponse({"detail": "item_id must be a positive integer"}, status=400)

    item = ConcurrencyLabItem.objects.filter(id=item_id).first()
    if not item:
        return JsonResponse({"detail": "item not found"}, status=404)

    events = ConcurrencyLabEvent.objects.filter(item_id=item_id)[:30]
    return JsonResponse(
        {
            "item": _item_to_dict(item),
            "events": [_event_to_dict(event) for event in events],
        }
    )


ROADMAP = [
    {
        "id": "django-crud",
        "title": "Django CRUD API",
        "goal": "熟悉路由、视图、请求解析、JsonResponse。",
        "checkpoints": ["写 GET 列表", "写 POST 创建", "写 PUT/DELETE 详情接口"],
    },
    {
        "id": "db-migration",
        "title": "Model + Migration",
        "goal": "理解模型变更如何映射到数据库结构。",
        "checkpoints": ["修改模型字段", "执行 makemigrations/migrate", "验证 SQL 表变化"],
    },
    {
        "id": "algorithm-cf",
        "title": "Collaborative Filtering",
        "goal": "理解相似用户、候选物品、打分排序。",
        "checkpoints": ["计算余弦相似度", "汇总候选书籍得分", "处理冷启动回退热门"],
    },
    {
        "id": "react-api",
        "title": "React + Axios",
        "goal": "把后端接口接入前端页面并处理 loading/error。",
        "checkpoints": ["调用列表接口", "表单提交", "错误展示与刷新"],
    },
]


def _task_to_dict(task):
    return {
        "id": task.id,
        "title": task.title,
        "topic": task.topic,
        "status": task.status,
        "notes": task.notes,
        "created_at": task.created_at.isoformat(),
        "updated_at": task.updated_at.isoformat(),
    }


def _parse_json_body(request):
    try:
        return json.loads(request.body.decode("utf-8"))
    except (json.JSONDecodeError, UnicodeDecodeError):
        return None


def _safe_int(value, default_value):
    try:
        return int(value)
    except (TypeError, ValueError):
        return default_value


def index(request):
    return render(request, "playground/index.html", {"roadmap": ROADMAP})


def api_roadmap(request):
    if request.method != "GET":
        return JsonResponse({"detail": "Method not allowed"}, status=405)
    return JsonResponse({"results": ROADMAP})


@csrf_exempt
def api_tasks(request):
    if request.method == "GET":
        queryset = PracticeTask.objects.all()
        topic = request.GET.get("topic")
        status = request.GET.get("status")
        if topic:
            queryset = queryset.filter(topic=topic)
        if status:
            queryset = queryset.filter(status=status)
        return JsonResponse({"results": [_task_to_dict(task) for task in queryset]})

    if request.method == "POST":
        payload = _parse_json_body(request)
        if payload is None:
            return JsonResponse({"detail": "Invalid JSON body"}, status=400)

        title = (payload.get("title") or "").strip()
        topic = payload.get("topic") or PracticeTask.Topic.DJANGO_API
        notes = payload.get("notes") or ""
        status = payload.get("status") or PracticeTask.Status.TODO

        if not title:
            return JsonResponse({"detail": "title is required"}, status=400)

        if topic not in PracticeTask.Topic.values:
            return JsonResponse({"detail": "invalid topic"}, status=400)
        if status not in PracticeTask.Status.values:
            return JsonResponse({"detail": "invalid status"}, status=400)

        task = PracticeTask.objects.create(title=title, topic=topic, notes=notes, status=status)
        return JsonResponse(_task_to_dict(task), status=201)

    return JsonResponse({"detail": "Method not allowed"}, status=405)


@csrf_exempt
def api_task_detail(request, task_id):
    task = PracticeTask.objects.filter(id=task_id).first()
    if not task:
        return JsonResponse({"detail": "Task not found"}, status=404)

    if request.method == "PUT":
        payload = _parse_json_body(request)
        if payload is None:
            return JsonResponse({"detail": "Invalid JSON body"}, status=400)

        title = payload.get("title")
        topic = payload.get("topic")
        notes = payload.get("notes")
        status = payload.get("status")

        if title is not None:
            title = title.strip()
            if not title:
                return JsonResponse({"detail": "title cannot be empty"}, status=400)
            task.title = title
        if topic is not None:
            if topic not in PracticeTask.Topic.values:
                return JsonResponse({"detail": "invalid topic"}, status=400)
            task.topic = topic
        if status is not None:
            if status not in PracticeTask.Status.values:
                return JsonResponse({"detail": "invalid status"}, status=400)
            task.status = status
        if notes is not None:
            task.notes = notes

        task.save()
        return JsonResponse(_task_to_dict(task))

    if request.method == "DELETE":
        task.delete()
        return JsonResponse({}, status=204)

    return JsonResponse({"detail": "Method not allowed"}, status=405)


def _user_cf_debug(reader_id, top_k_users, limit):
    interaction_rows = Loan.objects.values_list("reader_id", "book_id")
    reader_books = {}
    for current_reader_id, book_id in interaction_rows:
        reader_books.setdefault(current_reader_id, set()).add(book_id)

    target_books = reader_books.get(reader_id, set())
    if not target_books:
        popular = (
            Book.objects.annotate(loan_count=Count("loans"))
            .order_by("-loan_count", "-sale_num", "id")[:limit]
        )
        return {
            "mode": "cold_start",
            "target_books": [],
            "neighbors": [],
            "results": [
                {
                    "book_id": book.id,
                    "book_name": book.name,
                    "score": float(book.loan_count),
                    "reason": "popular_fallback",
                }
                for book in popular
            ],
        }

    similarities = []
    for other_reader_id, other_books in reader_books.items():
        if other_reader_id == reader_id:
            continue
        overlap = len(target_books.intersection(other_books))
        if overlap == 0:
            continue
        score = overlap / math.sqrt(len(target_books) * len(other_books))
        similarities.append(
            {
                "reader_id": other_reader_id,
                "similarity": round(score, 6),
                "common_books": sorted(target_books.intersection(other_books)),
            }
        )

    similarities.sort(key=lambda item: (-item["similarity"], item["reader_id"]))
    neighbors = similarities[:top_k_users]
    score_map = {}
    support_map = {}

    for neighbor in neighbors:
        sim = neighbor["similarity"]
        neighbor_books = reader_books.get(neighbor["reader_id"], set())
        for book_id in neighbor_books:
            if book_id in target_books:
                continue
            score_map[book_id] = score_map.get(book_id, 0.0) + sim
            support_map[book_id] = support_map.get(book_id, 0) + 1

    if not score_map:
        fallback = (
            Book.objects.exclude(id__in=target_books)
            .annotate(loan_count=Count("loans"))
            .order_by("-loan_count", "-sale_num", "id")[:limit]
        )
        results = [
            {
                "book_id": book.id,
                "book_name": book.name,
                "score": float(book.loan_count),
                "reason": "popular_fallback",
            }
            for book in fallback
        ]
    else:
        ranked_book_ids = sorted(
            score_map.keys(),
            key=lambda book_id: (-score_map[book_id], -support_map[book_id], book_id),
        )[:limit]
        book_map = {book.id: book for book in Book.objects.filter(id__in=ranked_book_ids)}
        results = []
        for book_id in ranked_book_ids:
            book = book_map.get(book_id)
            if not book:
                continue
            results.append(
                {
                    "book_id": book.id,
                    "book_name": book.name,
                    "score": round(float(score_map[book_id]), 6),
                    "neighbor_support": support_map[book_id],
                    "reason": "collaborative_filtering",
                }
            )

    return {
        "mode": "normal",
        "target_books": sorted(target_books),
        "neighbors": neighbors,
        "results": results,
    }


def api_recommendation_lab(request):
    if request.method != "GET":
        return JsonResponse({"detail": "Method not allowed"}, status=405)

    reader_id = _safe_int(request.GET.get("reader_id"), 0)
    if reader_id <= 0:
        return JsonResponse({"detail": "reader_id must be a positive integer"}, status=400)

    top_k_users = max(1, min(_safe_int(request.GET.get("top_k_users"), 5), 50))
    limit = max(1, min(_safe_int(request.GET.get("limit"), 5), 30))

    debug_result = _user_cf_debug(reader_id=reader_id, top_k_users=top_k_users, limit=limit)
    return JsonResponse(
        {
            "reader_id": reader_id,
            "algorithm": "user_cf_cosine_implicit",
            "top_k_users": top_k_users,
            "limit": limit,
            "debug": debug_result,
            "explain": [
                "1) 先找和目标读者有共同借阅的读者，计算余弦相似度。",
                "2) 取最相似的 Top-K 邻居。",
                "3) 对邻居借过但目标读者没借过的书累加相似度作为推荐分。",
                "4) 如果没有候选，回退到热门书。",
            ],
        }
    )
