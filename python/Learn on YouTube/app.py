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
