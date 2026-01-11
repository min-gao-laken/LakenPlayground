high_income = True
good_credit = False
student = False

if (high_income or good_credit) and not student:
    print("Eligible for loan - complex condition met")
else:
    print("Not eligible for loan - complex condition not met")

if high_income and good_credit:
    print("Eligible for loan - both conditions met")
else:
    print("Not eligible for loan")

# Using 'or' operator
if high_income or good_credit:
    print("Eligible for loan - at least one condition met")
else:
    print("Not eligible for loan")


# Using 'not' operator
if not good_credit:
    print("Eligible for loan - good credit is not present")

print("-------------------")
age = 22
if 18 <= age < 65:
    print("Eligible for work")
else:
    print("Not eligible for work")
print("-------------------")

if 10 == "10":
    print("a")
elif "bag" > "apple" and "bag" > "cat":
    print("b")
else:
    print("c")
print("-------------------")

for number in range(4):
    print("Attempt", number, (number + 1) * ".")

print("repeat_" * 3)

for number in range(1, 3):
    print("Attempt", number, number * ".")

print("------------------- for + if + break -------------------")
successful = True
for number in range(3):
    print("Attempt", number)
    if successful:
        print("Successful!")
        break
else:
    print("Attempted 3 times and failed")

print("------------------- for + if (not successful, wrong: without 'break') -------------------")
successful = True
for number in range(3):
    print("Attempt", number)
    if successful:
        print("Successful!")
        # break
else:
    print("Attempted 3 times and failed")

print("------------------- for + if (not successful) -------------------")
successful = False
for number in range(3):
    print("Attempt", number)
    if successful:
        print("Successful!")
        break
else:
    print("_Attempted 3 times and failed")


print("------------------- nested loops -------------------")
for x in range(2):
    for y in range(3):
        print(f"({x}, {y})")
