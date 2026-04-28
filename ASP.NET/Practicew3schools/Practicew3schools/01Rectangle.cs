namespace Practicew3schools
{
    internal class Rectangle
    {
        public static void Run2()
        {
            Console.WriteLine("Run2 in Rectangle class");
        }

        double length;
        double width;

        public void Acceptdetails()
        {
            length = 10.5;
            width = 5.5;
        }

        public double GetArea()
        {
            return length * width;
        }
        public void Display()
        {
            Console.WriteLine($"Length: {length}");
            Console.WriteLine("Width: {0}", width);
            Console.WriteLine("Area: {0}", GetArea());
        }
    }

    class Exectute
    {
        public void Run()
        {
            Rectangle rect = new Rectangle();
            rect.Acceptdetails();
            rect.Display();
        }
    }


}
