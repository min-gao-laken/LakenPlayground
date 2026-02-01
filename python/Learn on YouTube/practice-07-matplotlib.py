import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns

# -----------------------------
# generate data (numpy)
# -----------------------------
x = np.linspace(0, 10, 100)  # 0~10，100 points
y = np.sin(x)                # sin(x)

# -----------------------------
# create DataFrame (pandas)
# -----------------------------
df = pd.DataFrame({
    "x": x,
    "sin(x)": y
})


print(df.head())

# -----------------------------
# set style (seaborn)
# -----------------------------
sns.set_theme(style="darkgrid")

# -----------------------------
# draw plot (matplotlib + seaborn + pandas)
# -----------------------------
plt.figure(figsize=(10, 5))               # size
sns.lineplot(data=df, x="x", y="sin(x)", color="blue")  # line plot
plt.title("Sine Wave with Seaborn + pandas")           # title
plt.xlabel("x")                                      # x
plt.ylabel("sin(x)")                                 # y
plt.show()

print("OK! Plot displayed.")
