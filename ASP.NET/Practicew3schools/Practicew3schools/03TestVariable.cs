namespace Practicew3schools
{
    internal class TestVariable
    {
        static int myNum = 5;
        private string myString = "Hello";
        double myDouble = 3.14;
        bool myBool = true;
        char myChar = 'A';
        const int myConstNum = 10;
        // 这一段代码会遇到一个 C# 中非常基础但关键的报错：“非静态字段不能用于静态方法中”。

        public static void Display()
        {
            Console.WriteLine("直接调用方法: ");
            Console.WriteLine("Integer: " + myNum);

        }

        public void Display2()
        {
            Console.WriteLine("需要先创建对象，再调用方法: ");
            Console.WriteLine("Integer: " + myNum);
            Console.WriteLine("String: " + myString);
            Console.WriteLine("Double: " + myDouble);
            Console.WriteLine("Boolean: " + myBool);
            Console.WriteLine("Character: " + myChar);
            Console.WriteLine("Constant Integer: " + myConstNum);
        }

        public void Display3()
        {
            Console.WriteLine("测试类型转换: ");
            int intValue = 42;
            float floatValue = (float)intValue; // 强制从 int 到 float，数据可能损失精度 Explicit Casting

            int intValue2 = 123;
            string stringValue = intValue2.ToString(); // 将 int 转换为字符串

            Console.WriteLine(floatValue); // 输出: 42
            Console.WriteLine(stringValue); // 输出: "123"


            // Type Conversion Methods
            int myint = 100;
            Console.WriteLine("测试Cover方法: " + Convert.ToString(myint));


        }
    }
}
