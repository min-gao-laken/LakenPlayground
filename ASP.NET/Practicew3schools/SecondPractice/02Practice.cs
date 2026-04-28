namespace Practicew3schools
{
    internal class _02Practice
    {
        public static void Run()
        {
            Console.WriteLine("Run in _02Practice class");
            int time = 20;
            string result = (time < 18) ? "Good day." : "Good evening.";
            Console.WriteLine(result);

            int day = 4;
            switch (day)
            {
                case 6:
                    Console.WriteLine("Today is Saturday.");
                    break;
                case 7:
                    Console.WriteLine("Today is Sunday.");
                    break;
                default:
                    Console.WriteLine("Looking forward to the Weekend.");
                    break;
            }

            int i = 0;
            while (i < 5)
            {
                Console.WriteLine(i);
                i++;
            }

            int j = 0;
            do
            {
                Console.WriteLine(j);
                j++;
            }
            while (j < 5);

            for (int z = 0; z < 5; z++)
            {
                Console.WriteLine(z);
            }

            string[] cars = { "Volvo", "BMW", "Ford", "Mazda" };// 这种创建数组的方式最为常见，直接在声明时初始化数组元素。
            foreach (string c in cars)
            {
                Console.WriteLine(c);
            }

        }
    }
}
