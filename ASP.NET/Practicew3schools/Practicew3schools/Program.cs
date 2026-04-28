// See https://aka.ms/new-console-template for more information
//Console.WriteLine("Hello, World!");

namespace Practicew3schools
{
    /* 类名为 HelloWorld */
    public class HelloWorld
    {
        /* main函数 */
        static void Main(string[] args)
        {
            /* 我的第一个 C# 程序 */
            Console.WriteLine("Hello World!"); // 这是主程序跑的
            //Console.ReadKey(); // 让程序暂停，等待用户按键后继续执行

            // 调用另一个文件里的逻辑
            Test.Run("Laken");

            Exectute ex = new Exectute();
            ex.Run();

            /*
             * this is a comment
             */
            Rectangle.Run2(); // 因为Run2是静态方法，所以直接用类名调用就行了

            TestVariable.Display(); // 因为Display是静态方法，所以直接用类名调用就行了
            TestVariable testVariable = new TestVariable();
            testVariable.Display2(); // 因为Display2是实例方法，所以需要先创建对象，再调用方法

            testVariable.Display3();
        }
    }
}