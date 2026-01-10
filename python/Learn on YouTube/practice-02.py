# Working with basic arithmetic operations in Python
import math

print(10+3)
print(10-3)
print(10*3)
print(10/3)
print(10//3)  # floor division
print(5//3)
print(10 % 3)
print(10**3)
print(10**4)
print(2**3)

print("-------------------")

print(round(2.9))
print(abs(-2.9))

print("-------------------")

print(math.ceil(2.1))  # rounds up to the nearest integer
print(math.floor(2.9))  # rounds down to the nearest integer

x = input("x:")
y = int(x) + 1  # convert string input to integer
print(f"y: {y}. Type of y is {type(y)}, type of x is {type(x)}")

print("-------------------")
print(bool(1))  # True
print(bool(0))  # False
print(bool(-1))  # True
print(bool(""))  # False
print(bool("Hello"))  # True
print(bool([]))  # False
print(bool([1, 2, 3]))  # True
print(bool(None))  # False
print(bool({}))  # False
print(bool({"key": "value"}))  # True
