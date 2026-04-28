from django.contrib import admin
from playground.models import PracticeTask, ConcurrencyLabItem, ConcurrencyLabEvent


@admin.register(PracticeTask)
class PracticeTaskAdmin(admin.ModelAdmin):
    list_display = ("id", "title", "topic", "status", "updated_at")
    list_filter = ("topic", "status")
    search_fields = ("title", "notes")


@admin.register(ConcurrencyLabItem)
class ConcurrencyLabItemAdmin(admin.ModelAdmin):
    list_display = ("id", "name", "stock", "updated_at")
    search_fields = ("name",)


@admin.register(ConcurrencyLabEvent)
class ConcurrencyLabEventAdmin(admin.ModelAdmin):
    list_display = ("id", "item", "mode", "actor", "requested_qty", "success", "before_stock", "after_stock", "created_at")
    list_filter = ("mode", "success")
    search_fields = ("actor", "message")
