from django.db import models


class Manager(models.Model):
    id = models.AutoField(primary_key=True)
    number = models.CharField(max_length=32)
    password = models.CharField(max_length=32)
    name = models.CharField(max_length=32)

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
