from django.db import models
from django.utils import timezone

class Manager(models.Model):
    class Role(models.TextChoices):
        ADMIN = 'admin', 'Admin'
        ANALYST = 'analyst', 'Analyst'

    id = models.AutoField(primary_key=True)
    number = models.CharField(max_length=32)
    password = models.CharField(max_length=32)
    name = models.CharField(max_length=32)
    role = models.CharField(max_length=16, choices=Role.choices, default=Role.ADMIN)

    class Meta:
        db_table = 'manager'


class Publisher(models.Model):
    publisher_name = models.CharField(max_length=32, verbose_name='Publisher Name')
    publisher_address = models.CharField(max_length=32, verbose_name='Publisher Address')

    class Meta:
        db_table = 'publisher'


class Book(models.Model):
    id = models.AutoField(primary_key=True)
    name = models.CharField(max_length=32)
    price = models.DecimalField(max_digits=5, decimal_places=2)
    inventory = models.IntegerField(verbose_name='Inventory')
    sale_num = models.IntegerField(verbose_name='Sales')
    publisher = models.ForeignKey(to='Publisher', on_delete=models.CASCADE)

    class Meta:
        db_table = 'book'


class Author(models.Model):
    id = models.AutoField(primary_key=True)
    name = models.CharField(max_length=32)
    book = models.ManyToManyField(to='Book')

    class Meta:
        db_table = 'author'


class BorrowRecord(models.Model):
    id = models.AutoField(primary_key=True)
    book = models.ForeignKey(to='Book', on_delete=models.CASCADE, related_name='borrow_records')
    borrowed_on = models.DateField(default=timezone.now)
    quantity = models.PositiveIntegerField(default=1)

    class Meta:
        db_table = 'borrow_record'
        ordering = ['-borrowed_on', '-id']
