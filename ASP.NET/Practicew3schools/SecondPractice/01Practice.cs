namespace Practicew3schools
{
    internal class _01Practice
    {
        public static void Run()
        {
            Console.WriteLine("Run in _01Practice class");
            string txt = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            Console.WriteLine("The length of the txt string is: " + txt.Length);

            Console.WriteLine(txt.ToUpper());
            Console.WriteLine(txt.ToLower());

            string firstName = "John ";
            string lastName = "Doe";
            string name = string.Concat(firstName, lastName);
            Console.WriteLine(name);
            Console.WriteLine(firstName + lastName);
            Console.WriteLine($"{firstName} --- {lastName}");
            Console.WriteLine(firstName[0]);

            string txt2 = "We are the so-called \"Vikings\" from the north.";
            string txt3 = "It\'s alright.";
            string txt4 = "The character \\ is called backslash.";
            Console.WriteLine($"{txt2} - {txt3} - {txt4}");

            string txt5 = "Hello\nWorld!";
            Console.WriteLine(txt5);



        }
    }
}
