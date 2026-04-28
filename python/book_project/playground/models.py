from django.db import models


class PracticeTask(models.Model):
    class Topic(models.TextChoices):
        DJANGO_MODEL = "django_model", "Django Model"
        DJANGO_API = "django_api", "Django API"
        REACT_AXIOS = "react_axios", "React + Axios"
        SQL_AND_MIGRATION = "sql_migration", "SQL + Migration"
        ALGORITHM = "algorithm", "Algorithm"

    class Status(models.TextChoices):
        TODO = "todo", "Todo"
        DOING = "doing", "Doing"
        DONE = "done", "Done"

    title = models.CharField(max_length=120)
    topic = models.CharField(max_length=40, choices=Topic.choices)
    status = models.CharField(max_length=16, choices=Status.choices, default=Status.TODO)
    notes = models.TextField(blank=True, default="")
    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)

    class Meta:
        db_table = "playground_task"
        ordering = ["status", "-updated_at", "-id"]


class ConcurrencyLabItem(models.Model):
    name = models.CharField(max_length=64, unique=True)
    stock = models.IntegerField(default=0)
    updated_at = models.DateTimeField(auto_now=True)

    class Meta:
        db_table = "playground_concurrency_item"


class ConcurrencyLabEvent(models.Model):
    class Mode(models.TextChoices):
        UNLOCKED = "unlocked", "Unlocked"
        LOCKED = "locked", "Locked"

    item = models.ForeignKey(ConcurrencyLabItem, on_delete=models.CASCADE, related_name="events")
    mode = models.CharField(max_length=16, choices=Mode.choices)
    actor = models.CharField(max_length=64, default="anonymous")
    requested_qty = models.PositiveIntegerField(default=1)
    success = models.BooleanField(default=False)
    before_stock = models.IntegerField(default=0)
    after_stock = models.IntegerField(default=0)
    message = models.CharField(max_length=128, default="")
    created_at = models.DateTimeField(auto_now_add=True)

    class Meta:
        db_table = "playground_concurrency_event"
        ordering = ["-id"]
