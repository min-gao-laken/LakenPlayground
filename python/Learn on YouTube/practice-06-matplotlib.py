import matplotlib.pyplot as plt
import seaborn as sns
import numpy as np
import pandas as pd

# Generate sample data
x = np.linspace(0, 10, 100)
y = np.sin(x)

plt.plot(x, y)
plt.title("Sine Wave")
plt.xlabel("x")
plt.ylabel("sin(x)")
plt.show()

# Using seaborn for a more styled plot
np.random.seed(42)
x = np.random.rand(50)
y = np.random.rand(50)

plt.scatter(x, y, color="red")
plt.title("Scatter Plot")
plt.xlabel("x")
plt.ylabel("y")
plt.show()

# Bar Chart
categories = ["A", "B", "C", "D", "E"]
values = [10, 54, 30, 40, 50]

plt.bar(categories, values, color="green")
plt.title("Bar Chart")
plt.xlabel("Categories")
plt.ylabel("Values")
plt.show()


# Histogram
data = np.random.randn(1000)
plt.hist(data, bins=100, color="skyblue", edgecolor="black")

plt.title("Histogram")
plt.xlabel("Value")
plt.ylabel("Frequency")
plt.show()

# Styled Histogram with Seaborn
sns.set_theme()
data = np.random.randn(1000)

plt.hist(data, bins=20, edgecolor="black")
plt.title("Histogram with Seaborn Style")
plt.show()

# Styled Histogram with Seaborn
sns.set_theme()
data = np.random.randn(1000)

plt.hist(data, bins=20, edgecolor="black")
plt.title("Histogram with Seaborn Style")
plt.show()

# Loading a built-in dataset from seaborn
tips = sns.load_dataset("tips")
tips.head()
