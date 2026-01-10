print(1 == 2)
print(1 != 2)
print(1 > 2)

print("-------------------")

print("apple" == "apple")
print("bag" > "apple")
# ASCII value of lowercase letters is higher than uppercase letters
print("bag" > "Bag")
print(ord("b"))  # get ASCII value of character
print(ord("B"))

print("------------------- conditional statements -------------------")
temprature = 30  # change this value to test, 30, 20, 10
if temprature > 25:
    print("It's a hot day")  # use 4 spaces for indentation
    print("Drink plenty of water")
elif temprature > 15:
    print("It's a nice day")
else:
    print("It's not a hot day")
    print("Enjoy your day!")
print("Done, thank you!")  # this will always execute
