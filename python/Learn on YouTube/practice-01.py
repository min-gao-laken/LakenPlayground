print("hello world!!")
print("*" * 10)

course_name = "Python for Beginners"
print(course_name)
print(len(course_name))
print(course_name[0])
print(course_name[-1])
print(course_name[-2])
print(course_name[5])
print(course_name[6])
print(course_name[0:5])
print(course_name[1:5])
print(course_name[0:6])
print(course_name[0:])
print(course_name[:6])
print(course_name[:])

course_1 = "Python for \"Beginners"
print(course_1)
course_2 = "Python for \'Beginners"
print(course_2)
course_3 = "Python for \nBeginners"
print(course_3)
course_4 = "Python for \\Beginners"
print(course_4)

first_name = "John"
last_name = "Smith"
full_name_1 = first_name + " " + last_name
print(full_name_1)
full_name_2 = f"{first_name} {last_name} {2+3}_{len(first_name)}"
print(full_name_2)

course = "Python for Beginners"
print(course.upper())
print(course)  # original string is not changed
print(course.lower())

course_test_string = "   python for beginners   "
# converts first letter of each word to uppercase
print(course_test_string.title())
print(course_test_string.strip())  # removes leading and trailing spaces
print(course_test_string.lstrip())  # removes leading spaces
print(course_test_string.rstrip())  # removes trailing spaces
# returns index of first occurrence of substring
print(course_test_string.find("python"))
print(course_test_string.replace("beginners", "Absolute Beginners"))
print("Python" in course_test_string)  # returns True if substring is found
print("python" in course_test_string)  # case-sensitive
print("Java" in course_test_string)  # returns False if substring is not found
print(course_test_string.find("Java"))  # returns -1 if substring is not found
# returns True if substring is not found
print("Java" not in course_test_string)
