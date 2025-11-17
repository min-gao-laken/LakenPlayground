package lab1.method;

/**
 * @author Laken
 * @date 2025-11-13
 * @description
 */


import java.util.Scanner;
import java.util.Random;

/*
 * MethodsMiniBook.java
 *
 * Each chapter has:
 *   - Clear bullet-point explanation (simple English)
 *   - A step-by-step plan
 *   - A small runnable demo
 *   - Expected output (as comments)
 *   - Practice prompts
 *
 * HOW TO USE
 * - Open in your IDE.
 * - Scroll to a chapter's demo method.
 * - Uncomment its call in main() to run that chapter alone.
 * - We only use the tools you already learned: print, Scanner, if/else, while, do-while, switch, int, double.
 */

public class MiniBook {

    /* ============================================================
       1 — WHAT IS A METHOD?
       ============================================================

       Simple idea
       - A method is a small set of steps with a name.
       - The name tells us what the steps do.
       - We can give the method some inputs (these are called parameters).
       - The method can do an action (like print) or give back a result (this is called a return value).
       - We can call the same method many times.

       Why we use methods
       - Reuse: write steps once, use them many times.
       - Clear code: main becomes short and easy to read.
       - One change fixes all: if we improve the method, every place that calls it gets the fix.
       - Teamwork: names help us talk about code (“Call addTax here”).
       - Testing: we can try a small piece alone and check it.

       Picture (kitchen and recipe card)
       - Think about a kitchen.
       - A recipe card has a name, ingredients, and steps.
       - The method is like that card.
         • Name = job name (for example, sayHello).
         • Ingredients = inputs (for example, a name to greet).
         • Steps = Java statements inside the method.

       New words (we will use these words again and again)
       - Method: a named block of steps.
       - Parameter: the input name in the method header.
       - Argument: the real value we pass when we call the method.
       - Return value: the answer the method gives back to us.
       - void: no answer is returned; the method just does an action.

       When to print vs when to return
       - Print when we only need to show a message now.
       - Return when we need to keep the result to use later.

       Step-by-step plan to make a method
       1) Choose a short name that says the job (for example, square).
       2) Decide the inputs (for example, int n).
       3) Decide the result type (void if no result; a type like int if there is a result).
       4) Write the steps inside.
       5) Call the method from main and read the output.

       Small demo (two tiny methods)
       - We make a void method that prints a line.
       - We make a method that takes an int and returns an int.
    */
    static void chapter1_demo() {
        // 1) Call a void method (does an action: print)
        announce();
        // 2) Call a method that returns a value (keeps a result)
        int x = 5;
        int sq = square(x);
        System.out.println("square(" + x + ") = " + sq);

        // Optional simple check using if/else (no ternary)
        if (sq == 25) {
            System.out.println("check: ok");
        } else {
            System.out.println("check: not ok");
        }

        // Expected output:
        // --- Start Demo ---
        // square(5) = 25
        // check: ok
        // --- End Demo ---
    }

    static void announce() {
        System.out.println("--- Start Demo ---");
        // This method just prints a heading. It returns nothing (void).
    }

    static int square(int n) {
        // Steps: multiply the number by itself and return the result.
        int result = n * n;
        return result;
    }

    /*
Rationale

       - announce() is void: it prints and finishes.
       - square(n) returns an int: main keeps the value in a variable and can use it later.
       - If the rule for square ever changed (for example, n*n + 1), we would change it once here.

       Common mistakes
       - Forgetting to write return in a non-void method.
       - Mixing printing and returning in a confusing way (for now, keep them separate).
       - Long method names that do not explain the job. Use short and clear names.

       Practice (write these on your own)
       - Make a method line() that prints "-----" and call it before and after the demo.
       - Make a method cube(int n) that returns n*n*n. Call it with 3 and print the result.
       - Explain in one or two short lines: when do you want void? when do you want a return value?
    */

    /* ============================================================
       2 — FIRST METHOD: SAY HELLO (PRINT ONLY)
       ============================================================

       Goal
       - We will write a method that prints a greeting.
       - The method will take one input: the name to greet.
       - The method does not return a value (it just prints).

       What we need to know
       - The method header has the name and the parameter list.
       - Inside the body we use System.out.println to print the message.
       - In main we call the method and pass a real value (an argument).

       Plan
       1) Choose the name: sayHello.
       2) Choose one parameter: String name.
       3) Write the body: print "Hello, " and the name.
       4) Call sayHello("Rayhan") and sayHello("Class").
       5) Read the output and see if it matches what we expect.

       Extra note about file order
       - We keep helper methods below main to keep the top clean.
       - Java also allows methods above. Either way is fine.

       Demo
    */
    static void chapter2_demo() {
        sayHello("Rayhan");
        sayHello("Class");

        // Expected output:
        // Hello, Rayhan!
        // Hello, Class!
    }

    static void sayHello(String name) {
        System.out.println("Hello, " + name + "!");
    }

    /*
Rationale

       - Parameter vs argument:
         • Parameter: the name inside the header (String name).
         • Argument: the real value we pass (for example, "Rayhan").
       - sayHello is void: we do not need to keep any result; we only want to print now.
       - If we need a different message later, we change the method once.

       Common mistakes
       - Forgetting the parameter type (must write String name).
       - Writing print inside main many times instead of reusing the method.
       - Typo in quotes or plus signs when joining strings.
Exercises

       - Add sayHello("Amina").
       - Make a new method sayWelcome(String name) that prints "Welcome, " + name + "!" three times using a while loop.
       - Call sayWelcome("Team") from main to test it.
    */

    /* ============================================================
       3 — RETURNING A VALUE VS. PRINTING (NUMBERS ONLY)
       ============================================================

       Goal
       - Learn the difference between printing and returning.
       - Printing: we show the result now (and it is gone).
       - Returning: we keep the result in a variable and can use it later.

       When to print
       - We only need to show a message now.
       - We do not need to keep the value for future steps.

       When to return
       - We will use the result later (for example, for another calculation).
       - We want to compare results in if/else.
       - We want to print the result many times or in different places.

       Plan
       1) Write add(int a, int b) → returns a+b.
       2) Write doubleIt(int n) → returns 2*n.
       3) In main, call the methods, keep the results, print them.
       4) Use if/else to check a simple case (no ternary).

       Demo
    */
    static void chapter3_demo() {
        int a = 7;
        int b = 5;

        int sum = add(a, b);         // keep the result
        int twice = doubleIt(sum);   // reuse the result

        System.out.println("add(" + a + "," + b + ") = " + sum);
        System.out.println("doubleIt(" + sum + ") = " + twice);

        // Simple check with if/else (no ternary)
        // We expect: add(7,5) = 12 and doubleIt(12) = 24
        if (sum == 12) {
            System.out.println("sum check: ok");
        } else {
            System.out.println("sum check: not ok");
        }

        if (twice == 24) {
            System.out.println("doubleIt check: ok");
        } else {
            System.out.println("doubleIt check: not ok");
        }

        // Expected output:
        // add(7,5) = 12
        // doubleIt(12) = 24
        // sum check: ok
        // doubleIt check: ok
    }

    static int add(int a, int b) {
        int result = a + b;
        return result;
    }

    static int doubleIt(int n) {
        int result = 2 * n;
        return result;
    }

    /*
Rationale

       - add and doubleIt return int values, so main can store them and do more work.
       - We can print the value now or later; we can compute with it again.
       - If we printed inside the method instead, we would not be able to use the number later.
       - Returning makes the method more flexible.

       Common mistakes
       - Mixing printing and returning in the same method can confuse beginners. For now, do one thing.
       - Forgetting to store the returned value in a variable in main.
       - Doing extra work in main that should be in a method.
Exercises

       - Make a method tripleIt(int n) that returns 3*n. Call it with 10 and print the result.
       - Make a method average(int x, int y) that returns (x + y) / 2 as an int. Try it with (5,6). What do you see? Why?
       - Change average to return a double result (for example, (x + y) / 2.0). Try again and print both versions.
    */


    /* ============================================================
   4 — METHOD SIGNATURE ANATOMY (MATCH SLIDES)
   ============================================================

   Purpose
   - Understand each part of a method header (signature).
   - Use clear names for parameters.
   - Connect to what we already know: print, if/else, while, switch.

   Parts of a signature (left to right)
   - access level: public (visible to other classes) or package-private (no keyword).
   - static: attached to the class; we can call it from main without making an object.
   - return type: the kind of value returned (int, double, String, or void when nothing is returned).
   - name: short and clear, tells the job (addNumbers, areaRectangle).
   - parameters: the input list in parentheses. Each has a type and a name. Separate with commas.

   Example
   - public static int addNumbers(int num1, int num2)
     • public → other classes can use it (later, when we split files).
     • static → callable from main directly.
     • int → returns an int.
     • addNumbers → job name.
     • (int num1, int num2) → two inputs, both int.

   Why names matter
   - num1, num2 are okay, but width, height or a, b can be better depending on the job.
   - Good names reduce comments. They explain the purpose without long text.

   Plan (what we will do now)
   1) Show two signatures and break them down.
   2) Run a small demo that calls them.
   3) Practice renaming parameters to be clearer.
*/
    static void chapter4_demo() {
        // Call the sample methods:
        int s = addNumbers(8, 4);
        double rect = areaRectangle(3.0, 5.0);

        System.out.println("addNumbers(8,4) = " + s);
        System.out.println("areaRectangle(3.0,5.0) = " + rect);

        // Simple checks with if/else
        if (s == 12) {
            System.out.println("addNumbers check: ok");
        } else {
            System.out.println("addNumbers check: not ok");
        }

        if (rect == 15.0) {
            System.out.println("areaRectangle check: ok");
        } else {
            System.out.println("areaRectangle check: not ok");
        }

        // Expected:
        // addNumbers(8,4) = 12
        // areaRectangle(3.0,5.0) = 15.0
        // addNumbers check: ok
        // areaRectangle check: ok
    }

    // Signature example 1
    public static int addNumbers(int num1, int num2) {
        int result = num1 + num2;
        return result;
    }

    // Signature example 2
    static double areaRectangle(double width, double height) {
        double area = width * height;
        return area;
    }

    /*
Rationale

       - The header tells the reader everything: who can call it, how to call it, and what comes back.
       - The body is simple and focused on one job.

       Common mistakes
       - Missing a type for a parameter (must write the type).
       - Method name that does not match the job.
       - Wrong return type for what you return.
Exercises

       - Rewrite addNumbers to use names a and b. Does the program still work? Why?
       - Write areaTriangle(double base, double height) that returns (base*height)/2. Call it from chapter4_demo and print the result.
       - Add an if/else check for areaTriangle using a simple case you can do by hand.
    */

    /* ============================================================
       5 — READING & VALIDATING INPUT IN A METHOD (SCANNER)
       ============================================================

       Purpose
       - Read an integer from the user inside a method.
       - Force the user to enter a value in a range (for example, 1 to 5).
       - Return the valid number to the caller.

       Why we do this
       - Many programs need safe input.
       - Putting input steps in a method lets us reuse the same rule everywhere.
       - If the range changes later, we fix it in one place.

       Plan
       1) Make readIntInRange(min, max).
       2) Inside the method, create Scanner and loop until the input is valid.
       3) Return the valid number.
       4) Demo: ask for a menu choice 1–5.

       Notes
       - We keep the method simple: ask → read → check → repeat on error.
       - We use only the tools we already know: while, if/else, print, int, Scanner.
    */
    static void chapter5_demo() {
        int choice = readIntInRange(1, 5);
        System.out.println("You chose: " + choice);

        // Expected (example run):
        // Please enter a number from 1 to 5: 9
        // Invalid. Try again (1 to 5).
        // Please enter a number from 1 to 5: 3
        // You chose: 3
    }

    static int readIntInRange(int min, int max) {
        Scanner sc = new Scanner(System.in);
        int value;
        while (true) {
            System.out.print("Please enter a number from " + min + " to " + max + ": ");
            if (sc.hasNextInt()) {
                value = sc.nextInt();
                if (value >= min && value <= max) {
                    return value; // valid → return to caller
                } else {
                    System.out.println("Invalid. Try again (" + min + " to " + max + ").");
                }
            } else {
                // Not an int at all; consume the bad token and ask again
                sc.next(); // discard the non-int input
                System.out.println("Not a whole number. Try again.");
            }
        }
        // (We do not close Scanner tied to System.in inside a looped program.)
    }

    /*
Rationale

       - The loop continues until we get a valid int in range.
       - We check two things: is it an int? is it inside the range?
       - When valid, we return it. The method stops at return.

       Common mistakes
       - Forgetting to consume the bad token when it is not an int.
       - Off-by-one on the range (for example, using > instead of >=).
       - Printing error messages that do not tell the user the valid range.
Exercises

       - Write readIntPositive() that accepts only values 1 or bigger.
       - Write readMenuChoice() that accepts only 1, 2, or 3 (use readIntInRange under the hood).
       - Use readIntInRange(100, 200) to read a “height in cm” and print it.
    */

    /* ============================================================
       6 — RANDOM NUMBERS (MINIMAL, SAFE INTRO)
       ============================================================

       Purpose
       - Generate simple random integers for games and tests.
       - Keep the helper methods small and easy to reuse.

       Tools
       - Random from java.util.
       - We will write two helpers:
         • randomInRange(int lo, int hi) → a number between lo and hi, inclusive.
         • rollDie() → a number 1 to 6.

       Plan
       1) Show the two helpers.
       2) Demo: roll the die 5 times.
       3) Demo: generate three numbers from 100 to 200.

       Notes
       - We use only int and print.
       - No advanced math and no arrays (yet).
    */
    static void chapter6_demo() {
        System.out.println("Roll a die 5 times:");
        int i = 0;
        while (i < 5) {
            int d = rollDie();
            System.out.println("roll = " + d);
            i = i + 1;
        }

        System.out.println("Three codes from 100 to 200:");
        int c1 = randomInRange(100, 200);
        int c2 = randomInRange(100, 200);
        int c3 = randomInRange(100, 200);
        System.out.println(c1);
        System.out.println(c2);
        System.out.println(c3);

        // Expected (example; your numbers will differ):
        // Roll a die 5 times:
        // roll = 2
        // roll = 6
        // roll = 3
        // roll = 5
        // roll = 1
        // Three codes from 100 to 200:
        // 147
        // 189
        // 103
    }

    static int randomInRange(int lo, int hi) {
        // Inclusive range [lo, hi]
        Random r = new Random();
        int span = hi - lo + 1;      // number of possible results
        int n = r.nextInt(span);     // 0..(span-1)
        int value = lo + n;          // shift into [lo, hi]
        return value;
    }

    static int rollDie() {
        // 1..6
        return randomInRange(1, 6);
    }

    /*
Rationale

       - nextInt(span) gives a number from 0 to span-1.
       - Adding lo moves it up into [lo..hi].
       - We keep the helpers small so we can reuse them later (for example, guess-the-number).

       Common mistakes
       - Forgetting +1 in (hi - lo + 1) → this would miss the top value.
       - Swapping lo and hi by accident.
       - Printing inside helpers when you really want to return the number and decide outside.
Exercises

       - Roll two dice and print the total. If total is 12, print "Jackpot!".
       - Use randomInRange(1, 5) to print “Choose door #<n>” three times.
       - Make randomEvenInRange(lo, hi) that keeps calling randomInRange until it gets an even number, then returns it.
    */


    /* ============================================================
   7 — TEMPERATURE CONVERTERS + ROUNDING TO 2 DECIMALS
   ============================================================

   Purpose
   - Write methods that convert temperatures between Celsius and Fahrenheit.
   - Show how to round a double to 2 decimals using Math.round.
   - Keep code simple and match what we already know.

   Formulas
   - toCelsius(F) = (F − 32) * 5 / 9
   - toFahrenheit(C) = C * 9 / 5 + 32

   Rounding helper
   - round2(x) = Math.round(x * 100.0) / 100.0
   - We compute, then round, then print.

   Plan
   1) Write toCelsius(double f) → double.
   2) Write toFahrenheit(double c) → double.
   3) Write round2(double x) → double.
   4) Demo: convert a few fixed values and print both raw and rounded.

   Notes
   - We avoid strings comparison and advanced formatting.
   - Only doubles, prints, simple math.
*/
    static void chapter7_demo() {
        double f1 = 68.0;  // about room temperature
        double c1 = toCelsius(f1);
        double c1r = round2(c1);

        double c2 = 20.0;
        double f2 = toFahrenheit(c2);
        double f2r = round2(f2);

        System.out.println("toCelsius(" + f1 + ") = " + c1 + "  | rounded = " + c1r);
        System.out.println("toFahrenheit(" + c2 + ") = " + f2 + "  | rounded = " + f2r);

        // Optional: one quick interactive read for practice (comment in if needed)
        // Scanner sc = new Scanner(System.in);
        // System.out.print("Enter Celsius to convert: ");
        // double userC = sc.nextDouble();
        // double userF = toFahrenheit(userC);
        // System.out.println("Fahrenheit (rounded) = " + round2(userF));

        // Example expected (numbers may differ slightly due to double):
        // toCelsius(68.0) = 20.0  | rounded = 20.0
        // toFahrenheit(20.0) = 68.0  | rounded = 68.0
    }

    static double toCelsius(double f) {
        double c = (f - 32.0) * 5.0 / 9.0;
        return c;
    }

    static double toFahrenheit(double c) {
        double f = c * 9.0 / 5.0 + 32.0;
        return f;
    }

    static double round2(double x) {
        double r = Math.round(x * 100.0) / 100.0;
        return r;
    }

    /*
Rationale

       - Each method does one job. Names tell the job.
       - We return the numeric result so main can decide how to use it.
       - round2 is reusable anywhere we need two decimals.

       Common mistakes
       - Using int math: always use doubles here (9.0, 5.0, 32.0).
       - Rounding too early or rounding twice. Compute first, then round once for display.
       - Mixing up the formulas.
Exercises

       - Convert 0°C, 37°C, and 100°C to Fahrenheit. Print rounded values.
       - Convert 32°F, 98.6°F, and 212°F to Celsius. Print rounded values.
       - Write toKelvin(double c) that returns c + 273.15. Print round2 for a few values.
    */

    /* ============================================================
       8 — REVERSE THE DIGITS (INTEGER MATH ONLY)
       ============================================================

       Purpose
       - Reverse the digits of a non-negative integer using % and /.
       - No strings; only math and loops.
       - Build a method we can reuse later.

       Idea
       - Take the last digit with n % 10.
       - Drop the last digit with n / 10 (integer division).
       - Build the reversed number: rev = rev * 10 + lastDigit.

       Plan
       1) Start with rev = 0.
       2) While n > 0:
          • last = n % 10
          • rev = rev * 10 + last
          • n = n / 10
       3) Return rev.

       Notes
       - Today we handle non-negative numbers (n >= 0).
       - If n = 0, reversed is 0.
    */
    static void chapter8_demo() {
        int a = 12345;
        int b = 90020;
        int c = 0;

        int ra = reverseDigits(a);
        int rb = reverseDigits(b);
        int rc = reverseDigits(c);

        System.out.println("reverseDigits(" + a + ") = " + ra); // 54321
        System.out.println("reverseDigits(" + b + ") = " + rb); // 2009
        System.out.println("reverseDigits(" + c + ") = " + rc); // 0

        // Simple checks using if/else
        if (ra == 54321) System.out.println("check a: ok");
        else System.out.println("check a: not ok");
        if (rb == 2009) System.out.println("check b: ok");
        else System.out.println("check b: not ok");
        if (rc == 0) System.out.println("check c: ok");
        else System.out.println("check c: not ok");

        // Expected:
        // reverseDigits(12345) = 54321
        // reverseDigits(90020) = 2009
        // reverseDigits(0) = 0
        // check a: ok
        // check b: ok
        // check c: ok
    }

    static int reverseDigits(int n) {
        int rev = 0;
        while (n > 0) {
            int last = n % 10;
            rev = rev * 10 + last;
            n = n / 10;
        }
        return rev;
    }

    /*
Rationale

       - % 10 gives the last digit. / 10 removes it from the right.
       - We rebuild from left to right in the reversed number by multiplying rev by 10 and adding the last digit.

       Common mistakes
       - Forgetting to update n = n / 10 inside the loop (causes infinite loop).
       - Using negative inputs without a plan (we skip negatives for now).
       - Printing inside the method when we need to return the number.
Exercises

       - Write isPalindromeNumber(int n) that returns true if n equals reverseDigits(n), else false.
         (For now, just print "palindrome" or "not palindrome" using if/else in main; do not return boolean if you have not taught it.)
       - Try reverseDigits on 10, 1000, 1200300. Explain why leading zeros are dropped in the result.
       - Combine with Chapter 3: doubleIt(reverseDigits(42)) and print.
    */

    /* ============================================================
       9 — OVERLOADING BY COUNT AND BY TYPE (SUM VARIANTS)
       ============================================================

       Purpose
       - Keep one idea under one name: sum.
       - Let Java pick the right version based on the parameter list (shape).
       - Practice with int and double, and with different numbers of inputs.

       Overloading examples we will write
       - sum(int a, int b, int c)
       - sum(double a, double b, double c)
       - sum(int a, int b, int c, int d)

       Plan
       1) Write the three methods.
       2) Call each with the correct kinds of arguments.
       3) Print results and do simple checks with if/else.

       Notes
       - Choose clear names.
       - Keep each method focused and short.
    */
    static void chapter9_demo() {
        int s3 = sum(2, 3, 4);               // int,int,int
        double sd3 = sum(1.5, 2.0, 3.25);    // double,double,double
        int s4 = sum(10, 20, 30, 40);        // int,int,int,int

        System.out.println("sum(2,3,4) = " + s3);            // 9
        System.out.println("sum(1.5,2.0,3.25) = " + sd3);    // 6.75
        System.out.println("sum(10,20,30,40) = " + s4);      // 100

        // checks
        if (s3 == 9) System.out.println("s3 check: ok");
        else System.out.println("s3 check: not ok");
        // For doubles, compare rounded values for display only
        if (round2(sd3) == 6.75) System.out.println("sd3 check: ok");
        else System.out.println("sd3 check: not ok");
        if (s4 == 100) System.out.println("s4 check: ok");
        else System.out.println("s4 check: not ok");

        // Expected:
        // sum(2,3,4) = 9
        // sum(1.5,2.0,3.25) = 6.75
        // sum(10,20,30,40) = 100
        // s3 check: ok
        // sd3 check: ok
        // s4 check: ok
    }

    static int sum(int a, int b, int c) {
        int result = a + b + c;
        return result;
    }

    static double sum(double a, double b, double c) {
        double result = a + b + c;
        return result;
    }

    static int sum(int a, int b, int c, int d) {
        int result = a + b + c + d;
        return result;
    }

    /*
Rationale

       - The name sum is the same, but parameter lists are different.
       - Java chooses the version that matches the call.
       - This keeps related ideas together and reduces new names to remember.

       Common mistakes
       - Calling with mixed types by accident (for example, passing 2.0 to the int version).
       - Writing one method that tries to do too much.
       - Forgetting that overloading is based on parameter types and count (not only return type).
Exercises

       - Write sumFirstN(int n) that returns 1+2+...+n using a while loop.
       - Write sumRange(int a, int b) that returns a + (a+1) + ... + b (assume a <= b).
       - Add sum(double a, double b) and test it with small decimals. Print round2 of the result.
    */


    /* ============================================================
   10 — DECOMPOSITION: BUILD BIGGER FROM SMALLER (MAX OF TWO → MAX OF THREE)
   ============================================================

   Purpose
   - Learn to solve a bigger problem by first solving a smaller one.
   - Reuse a simple method (maxOfTwo) to build maxOfThree.
   - Keep names clear and code short.

   Idea
   - Step 1: Write maxOfTwo(int a, int b) → returns the larger number.
   - Step 2: Use maxOfTwo to write maxOfThree(int a, int b, int c).
             For example: maxOfThree(a,b,c) = maxOfTwo( maxOfTwo(a,b), c ).

   Why this helps
   - You do not repeat the same logic three times.
   - You test the small piece first, then trust it.
   - If you later improve maxOfTwo (edge handling), maxOfThree gets better for free.

   Plan
   1) Write and test maxOfTwo.
   2) Write and test maxOfThree (built from maxOfTwo).
   3) Try a few inputs and print the results.
*/
    static void chapter10_demo() {
        // Test maxOfTwo
        System.out.println("maxOfTwo(7, 3) = " + maxOfTwo(7, 3));   // 7
        System.out.println("maxOfTwo(4, 9) = " + maxOfTwo(4, 9));   // 9
        System.out.println("maxOfTwo(5, 5) = " + maxOfTwo(5, 5));   // 5

        // Simple checks
        if (maxOfTwo(7, 3) == 7) System.out.println("maxOfTwo check1: ok");
        else System.out.println("maxOfTwo check1: not ok");
        if (maxOfTwo(4, 9) == 9) System.out.println("maxOfTwo check2: ok");
        else System.out.println("maxOfTwo check2: not ok");

        // Test maxOfThree using maxOfTwo
        System.out.println("maxOfThree(3, 8, 6) = " + maxOfThree(3, 8, 6)); // 8
        System.out.println("maxOfThree(9, 1, 7) = " + maxOfThree(9, 1, 7)); // 9
        System.out.println("maxOfThree(5, 5, 5) = " + maxOfThree(5, 5, 5)); // 5

        if (maxOfThree(3, 8, 6) == 8) System.out.println("maxOfThree check1: ok");
        else System.out.println("maxOfThree check1: not ok");
        if (maxOfThree(9, 1, 7) == 9) System.out.println("maxOfThree check2: ok");
        else System.out.println("maxOfThree check2: not ok");

        // Expected:
        // maxOfTwo(7, 3) = 7
        // maxOfTwo(4, 9) = 9
        // maxOfTwo(5, 5) = 5
        // maxOfTwo check1: ok
        // maxOfTwo check2: ok
        // maxOfThree(3, 8, 6) = 8
        // maxOfThree(9, 1, 7) = 9
        // maxOfThree(5, 5, 5) = 5
        // maxOfThree check1: ok
        // maxOfThree check2: ok
    }

    static int maxOfTwo(int a, int b) {
        if (a >= b) {
            return a;
        } else {
            return b;
        }
    }

    static int maxOfThree(int a, int b, int c) {
        int m = maxOfTwo(a, b);
        int ans = maxOfTwo(m, c);
        return ans;
    }

    /*
Rationale

       - maxOfTwo solves the smaller problem once.
       - maxOfThree calls maxOfTwo twice; this keeps code short and clear.
       - If rules change (for ties, etc.), we change maxOfTwo and all higher methods benefit.

       Common mistakes
       - Rewriting the same comparisons in maxOfThree instead of reusing maxOfTwo.
       - Missing equal cases; we used >= so a tie returns a.
       - Printing inside these methods; we return values, then print in main.
Exercises

       - Write minOfTwo(int a, int b). Then write minOfThree(int a, int b, int c) using minOfTwo.
       - Test your min methods with equal numbers and different orders (e.g., 3,3 and 3,2).
       - Extend: write middleOfThree(int a,int b,int c) that prints which number is in the middle
         (hint: use maxOfThree and minOfThree to help).
    */

    /* ============================================================
       11 — CALL ORDER & DEBUGGING: TRACE THE STAIRCASE
       ============================================================

       Purpose
       - See method calls as a path: main → outer → middle → inner.
       - Print “enter” and “exit” to understand the order.
       - Link to common debugger terms: Step Into (go inside) and Step Over (run a call without going in).

       Idea
       - main prints "enter main".
       - main calls outer(); outer prints "enter outer".
       - outer calls middle(); middle prints "enter middle".
       - middle calls inner(times); inner prints "enter inner", then a loop print, then "exit inner".
       - Then we go back up: exit middle → exit outer → exit main.

       Plan
       1) Add enter/exit prints in each method.
       2) Call them in order from main.
       3) Read the console to follow the path.
    */
    static void chapter11_demo() {
        System.out.println("enter main");
        outer();
        System.out.println("exit main");

        // Expected order:
        // enter main
        // enter outer
        // enter middle
        // enter inner
        // inner i=0
        // inner i=1
        // inner i=2
        // exit inner
        // exit middle
        // exit outer
        // exit main
    }

    static void outer() {
        System.out.println("enter outer");
        middle();
        System.out.println("exit outer");
    }

    static void middle() {
        System.out.println("enter middle");
        inner(3);
        System.out.println("exit middle");
    }

    static void inner(int times) {
        System.out.println("enter inner");
        int i = 0;
        while (i < times) {
            System.out.println("inner i=" + i);
            i = i + 1;
        }
        System.out.println("exit inner");
    }

    /*
Rationale

       - The prints show the real order: down into calls, then up when each call finishes.
       - This helps when output looks “nested” or “out of order.”
       - In a debugger, Step Into follows this path inside a call; Step Over runs the call and returns.

       Common mistakes
       - Forgetting an exit print (harder to match enter/exit pairs).
       - Changing the order (calling inner before printing "enter middle").
Exercises

       - Add one more layer: shell() that calls outer(). Call shell() from chapter11_demo and predict the order.
       - Change times to 1 and confirm the inner loop prints only once.
       - Try Step Into and Step Over in your IDE once to see the difference.
    */

    /* ============================================================
       12 — INPUT-DRIVEN MINI-FLOW: "GUESS THE CODE" (1–500)
       ============================================================

       Purpose
       - Build a small game using methods we already wrote.
       - Use randomInRange and readIntInRange together.
       - Count the number of guesses and give feedback (Higher/Lower).

       Rules (different from the assignment on purpose)
       - Secret number is 1..500 (inclusive).
       - Player guesses until correct.
       - After each guess, print “Higher” or “Lower”.
       - At the end, print how many guesses were used.

       Plan
       1) Secret = randomInRange(1, 500).
       2) Loop:
          • guess = readIntInRange(1, 500)
          • if guess < secret → “Higher”
          • else if guess > secret → “Lower”
          • else → “Correct! in X guesses” and stop.
       3) Keep a counter for guesses.

       Notes
       - We reuse our helpers. No new tools.
       - Keep the messages short and clear.
    */
    static void chapter12_demo() {
        int secret = randomInRange(1, 500);
        int guesses = 0;

        System.out.println("I picked a secret number from 1 to 500.");
        System.out.println("Try to guess it!");

        while (true) {
            int g = readIntInRange(1, 500);
            guesses = guesses + 1;

            if (g < secret) {
                System.out.println("Higher");
            } else if (g > secret) {
                System.out.println("Lower");
            } else {
                System.out.println("Correct! You used " + guesses + " guesses.");
                break;
            }
        }

        // Example run (your secret will differ):
        // I picked a secret number from 1 to 500.
        // Try to guess it!
        // Please enter a number from 1 to 500: 300
        // Lower
        // Please enter a number from 1 to 500: 150
        // Higher
        // Please enter a number from 1 to 500: 200
        // Correct! You used 3 guesses.
    }

    /*
Rationale

       - randomInRange picks the secret; readIntInRange protects us from bad input.
       - The loop continues until the guess equals the secret.
       - The counter increases each guess, so we can report the total at the end.

       Common mistakes
       - Using wrong range (0..500 or 1..499 by mistake).
       - Forgetting to increase the guess counter.
       - Printing in helpers when you should return, or returning when you meant to print.
Exercises

       - Change the range to 1..1000 and run again.
       - Add a limit: if guesses reach 12, end the game and print “Out of guesses.”
       - Add a hint: if the difference is 50 or more, print “Far”; else print “Close” (after Higher/Lower).
    */


    /* ============================================================
   13 — PERCENTAGE & ROUNDING UTILITY (FOR MONEY-LIKE OUTPUT)
   ============================================================

   Purpose
   - Work with percentages using doubles.
   - Round to 2 decimals for display.
   - Keep the math simple and clear.

   Notes
   - We already have round2(double x).
   - We will write two helpers:
     • addPercent(double base, double percent)  → base increased by percent% (e.g., 10%).
     • takePercent(double base, double percent) → percent% of base (e.g., 10% of 250 = 25).

   Plan
   1) Show helpers.
   2) Demo with simple numbers.
   3) Print rounded results only at the end.
*/
    static void chapter13_demo() {
        double price = 120.0;
        double taxPercent = 11.0;
        double tipPercent = 15.0;

        double withTax = addPercent(price, taxPercent);      // 120 + 11% of 120
        double tipOnly = takePercent(price, tipPercent);      // 15% of 120
        double finalBill = price + takePercent(price, taxPercent) + tipOnly;

        System.out.println("Base price = " + price);
        System.out.println("Tax (" + taxPercent + "%) added = " + round2(withTax));
        System.out.println("Tip (" + tipPercent + "%) amount = " + round2(tipOnly));
        System.out.println("Final bill (price + tax + tip) = " + round2(finalBill));

        // Expected (rounded):
        // Base price = 120.0
        // Tax (11.0%) added = 133.2
        // Tip (15.0%) amount = 18.0
        // Final bill (price + tax + tip) = 151.2
    }

    static double addPercent(double base, double percent) {
        double add = base * (percent / 100.0);
        double ans = base + add;
        return ans;
    }

    static double takePercent(double base, double percent) {
        double part = base * (percent / 100.0);
        return part;
    }

    /*
Rationale

       - Percent is per 100. We divide by 100.0 to get the fraction.
       - We return the double result so the caller can combine or round later.
       - We round only for display to keep the math accurate.

       Common mistakes
       - Rounding too early (can add small errors).
       - Using int math by mistake.
       - Confusing “add percent” with “take percent of.”
Exercises

       - Compute a discount: price minus 20%. Print round2 of the final price.
       - For a list of base prices (hardcode 3–4 values), compute tax for each using the same percent.
       - Write percentChange(oldValue, newValue) that returns the percent increase (or decrease if negative).
    */

    /* ============================================================
       14 — BRACKETED CHARGES (RIVERLAND UTILITY EXAMPLE)
       ============================================================

       Purpose
       - Practice a two-step process like your assignments: compute an eligible base, then compute a charge with brackets.
       - Use clear helper methods and round for display.

       Scenario (not your assignment; numbers are different)
       - Riverland Utility computes an “eligible base” from three inputs a, b, c and two deduct values:
         • eligible = a + b + c − baseDeduct − (variableDeduct * 0.50)
         • If eligible < 0, make it 0.
         • Round for display with round2 when printing.
       - Charge brackets on eligible (marginal example):
         • 0 to 20000 → 12% of that part
         • 20000 to 50000 → 18% of that part
         • above 50000 → 23% of that part

       Plan
       1) computeEligibleBase(a,b,c, baseDeduct, variableDeduct)
       2) computeCharge(eligible)
       3) Demo with 2–3 example inputs and print rounded results.
    */
    static void chapter14_demo() {
        double a1 = 12000, b1 = 5000, c1 = 3000;
        double eligible1 = computeEligibleBase(a1, b1, c1, 2000, 4000); // baseDeduct=2000, variableDeduct=4000
        double charge1 = computeCharge(eligible1);

        double a2 = 60000, b2 = 12000, c2 = 8000;
        double eligible2 = computeEligibleBase(a2, b2, c2, 5000, 6000);
        double charge2 = computeCharge(eligible2);

        System.out.println("Eligible #1 = " + round2(eligible1));
        System.out.println("Charge   #1 = " + round2(charge1));
        System.out.println("Eligible #2 = " + round2(eligible2));
        System.out.println("Charge   #2 = " + round2(charge2));

        // Example expected (your numbers may vary slightly before rounding):
        // Eligible #1 = 21000.0
        // Charge   #1 = 3180.0
        // Eligible #2 = 67500.0
        // Charge   #2 = 12725.0
    }

    static double computeEligibleBase(double a, double b, double c, double baseDeduct, double variableDeduct) {
        double eligible = a + b + c - baseDeduct - (variableDeduct * 0.50);
        if (eligible < 0) {
            eligible = 0.0;
        }
        return eligible;
    }

    static double computeCharge(double eligible) {
        double left = eligible;
        double total = 0.0;

        // First bracket: 0..20000 at 12%
        double take = takeUpTo(left, 20000.0);
        total = total + take * 0.12;
        left = left - take;

        // Second bracket: 20000..50000 at 18%
        take = takeUpTo(left, 30000.0); // 50000 - 20000 = 30000 range width
        total = total + take * 0.18;
        left = left - take;

        // Above 50000 at 23%
        if (left > 0) {
            total = total + left * 0.23;
        }

        return total;
    }

    // Helper to take up to 'cap' from 'left'
    static double takeUpTo(double left, double cap) {
        if (left <= 0) return 0.0;
        if (left <= cap) return left;
        return cap;
        // Example: left=45000, cap=30000 → returns 30000
    }

    /*
Rationale

       - We split the work: eligible first, then charge with brackets.
       - Each part is simple and testable.
       - If rules change, we update the small parts.

       Common mistakes
       - Forgetting to clamp eligible to 0.
       - Mixing up bracket widths (e.g., second bracket is 30000 wide, not 50000).
       - Rounding too early; we return raw doubles and round only when printing.
Exercises

       - Change the bracket rates to 10%, 17%, 25% and run again.
       - Try eligible below 0 by using small a,b,c and large deductions; confirm it becomes 0.
       - Print a small table for eligible = 0, 10000, 25000, 50000, 80000.
    */

    /* ============================================================
       15 — VALUE COMPARISON: BURGER VS WRAP (AREA + PRICE)
       ============================================================

       Purpose
       - Compare two items by “value per area”.
       - Reuse area formulas and simple division.
       - Round display values for clarity.

       Definitions (choose one way and keep it consistent)
       - valuePerArea = area / price  (bigger is better: more food per dollar)
         (If you prefer “cost per area = price / area”, then smaller is better. We will use valuePerArea here.)

       Formulas
       - Circle area by diameter d:  π * (d/2)^2
       - Rectangle area: width * height
       - Use Math.PI for π.

       Plan
       1) areaCircleByDiameter(d)
       2) areaRectangle(w,h)
       3) valuePerArea(price, area)
       4) Demo: compare one burger (circle) vs one wrap (rectangle)
    */
    static void chapter15_demo() {
        double burgerDiameter = 15.0;     // cm
        double burgerPrice = 8.50;        // dollars
        double wrapW = 12.0, wrapH = 10.0; // cm x cm
        double wrapPrice = 7.75;

        double burgerArea = areaCircleByDiameter(burgerDiameter);
        double wrapArea = areaRectangle(wrapW, wrapH);

        double burgerValue = valuePerArea(burgerPrice, burgerArea);
        double wrapValue = valuePerArea(wrapPrice, wrapArea);

        System.out.println("Burger area = " + round2(burgerArea) + " cm^2, value = " + round2(burgerValue));
        System.out.println("Wrap   area = " + round2(wrapArea) + " cm^2, value = " + round2(wrapValue));

        if (burgerValue > wrapValue) {
            System.out.println("Better value: Burger");
        } else if (wrapValue > burgerValue) {
            System.out.println("Better value: Wrap");
        } else {
            System.out.println("Same value");
        }

        // Example output (rounded may differ):
        // Burger area = 176.71 cm^2, value = 20.79
        // Wrap   area = 120.0 cm^2, value = 15.48
        // Better value: Burger
    }

    static double areaCircleByDiameter(double d) {
        double r = d / 2.0;
        double area = Math.PI * r * r;
        return area;
    }

    // areaRectangle(double,double) already exists in Chapter 4.
    // Reusing it here.

    static double valuePerArea(double price, double area) {
        double v = area / price;
        return v;
    }

    /*
Rationale

       - We compute areas, then compute a single score (area per dollar).
       - We compare scores with if/else and print the better one.
       - Rounding is only for printing.

       Common mistakes
       - Mixing units (cm vs inch). Keep units the same.
       - Dividing in the wrong order (price/area vs area/price). Choose one and keep it.
Exercises

       - Compare two burgers with different diameters and prices.
       - Compare two wraps with different widths/heights and prices.
       - Print both “value per area” and “cost per area” to see the difference.
    */

    /* ============================================================
       16 — LOOPS + METHODS: REUSABLE PATTERNS
       ============================================================

       Purpose
       - Learn two common patterns you will reuse with arrays later.
       - Pattern A: repeat and print something returned by a method.
       - Pattern B: build a total by calling a method inside a loop.

       Plan
       - A: print lines of stars using makeStars(n).
       - B: sum of first N squares using square(i) from Chapter 1.
    */
    static void chapter16_demo() {
        // Pattern A
        int lines = 3;
        int i = 1;
        while (i <= lines) {
            System.out.println(makeStars(i));
            i = i + 1;
        }

        // Pattern B
        int N = 5;
        int sumSquares = 0;
        int k = 1;
        while (k <= N) {
            sumSquares = sumSquares + square(k); // square(int) from Chapter 1
            k = k + 1;
        }
        System.out.println("Sum of first " + N + " squares = " + sumSquares);

        // Expected:
        // *
        // **
        // ***
        // Sum of first 5 squares = 55
    }


    static String makeStars(int n) {
        String s = "";
        int i = 0;
        while (i < n) {
            s = s + "*";
            i = i + 1;
        }
        return s;
    }

    /*
Rationale

       - Pattern A separates “how many times” (loop) from “what to do once” (method).
       - Pattern B shows how to build a result across a loop using a helper method.
       - These two patterns will map directly to arrays next week.
Exercises

       - Print a table: n and square(n) for n from 1 to 10.
       - Compute sum of first N odd numbers using a helper isOdd(i) that returns 1 if odd else 0, then add up.
       - Build a line of hashes "#", reuse makeStars idea but with '#'.
    */

    /* ============================================================
       17 — SIMPLE CHECKS WITHOUT TERNARY (SMALL “TESTS”)
       ============================================================

       Purpose
       - Do small checks with only if/else (no ternary).
       - Build confidence before using methods in bigger tasks.

       Plan
       1) Check add with a few cases.
       2) Check reverseDigits with a few cases.
       3) For doubles, compare rounded values for display.

       Notes
       - Keep each check small and readable.
    */
    static void chapter17_demo() {
        // Check add
        checkAddCase(2, 3, 5);
        checkAddCase(10, -2, 8);

        // Check reverseDigits
        checkReverseCase(123, 321);
        checkReverseCase(900, 9);
        checkReverseCase(0, 0);

        // Check rounding display for doubles (use round2)
        double x = toFahrenheit(20.0); // from Chapter 7
        if (round2(x) == 68.0) {
            System.out.println("toFahrenheit(20) rounded check: ok");
        } else {
            System.out.println("toFahrenheit(20) rounded check: not ok");
        }

        // Expected:
        // add(2,3) expected 5 -> ok
        // add(10,-2) expected 8 -> ok
        // reverseDigits(123) expected 321 -> ok
        // reverseDigits(900) expected 9 -> ok
        // reverseDigits(0) expected 0 -> ok
        // toFahrenheit(20) rounded check: ok
    }

    static void checkAddCase(int a, int b, int expected) {
        int got = add(a, b);
        if (got == expected) {
            System.out.println("add(" + a + "," + b + ") expected " + expected + " -> ok");
        } else {
            System.out.println("add(" + a + "," + b + ") expected " + expected + " -> not ok (got " + got + ")");
        }
    }

    static void checkReverseCase(int n, int expected) {
        int got = reverseDigits(n);
        if (got == expected) {
            System.out.println("reverseDigits(" + n + ") expected " + expected + " -> ok");
        } else {
            System.out.println("reverseDigits(" + n + ") expected " + expected + " -> not ok (got " + got + ")");
        }
    }

    /*
Rationale

       - Small checks help catch mistakes early.
       - For doubles, compare rounded values for display only.
       - We print clear messages so we can read the result fast.
Exercises

       - Add check cases for maxOfThree.
       - Add check cases for areaCircleByDiameter with easy diameters.
       - Create checkSumFirstN(n, expected) once you write sumFirstN.
    */

    /* ============================================================
       18 — ARRAYS PREVIEW: SUM, MAX, FIND
       ============================================================

       Purpose
       - See how loops + methods apply to arrays next week.
       - We will only read arrays, not modify them.

       Plan
       1) sumArray(int[] a) → total of all elements.
       2) maxArray(int[] a) → largest element (assume length ≥ 1).
       3) findInArray(int[] a, int t) → index of t or -1 if not found.

       Notes
       - Same loop pattern as Chapter 16, but use a.length and a[i].
    */
    static void chapter18_demo() {
        int[] eggs = {3, 1, 4, 1, 5};

        int s = sumArray(eggs);
        int m = maxArray(eggs);
        int i1 = findInArray(eggs, 4);
        int i2 = findInArray(eggs, 9);

        System.out.println("sum = " + s);     // 14
        System.out.println("max = " + m);     // 5
        System.out.println("find 4 = " + i1); // 2
        System.out.println("find 9 = " + i2); // -1

        // Expected:
        // sum = 14
        // max = 5
        // find 4 = 2
        // find 9 = -1
    }

    static int sumArray(int[] a) {
        int s = 0;
        int i = 0;
        while (i < a.length) {
            s = s + a[i];
            i = i + 1;
        }
        return s;
    }

    static int maxArray(int[] a) {
        int m = a[0];
        int i = 1;
        while (i < a.length) {
            if (a[i] > m) {
                m = a[i];
            }
            i = i + 1;
        }
        return m;
    }

    static int findInArray(int[] a, int t) {
        int i = 0;
        while (i < a.length) {
            if (a[i] == t) {
                return i;
            }
            i = i + 1;
        }
        return -1;
    }

    /*
Rationale

       - Same loop shapes as before, now with a.length and index i.
       - Methods keep each array task small and clear.
       - Next week you will use the same patterns with real problems.
Exercises

       - Write countInArray(int[] a, int t) that returns how many times t appears.
       - Write minArray(int[] a) similar to maxArray.
       - Write sumFirstK(int[] a, int k) that sums the first k items (assume k <= a.length).
    */


    // ------------------------------------------------------------
    // Run a single chapter by uncommenting its call below.
    // ------------------------------------------------------------
    public static void main(String[] args) {
//         chapter1_demo();
//         chapter2_demo();
//         chapter3_demo();
//         chapter4_demo();
//         chapter5_demo();
//         chapter6_demo();
//         chapter7_demo();
//         chapter8_demo();
//         chapter9_demo();
//         chapter10_demo();
//         chapter11_demo();
        chapter12_demo();
//         chapter13_demo();
//         chapter14_demo();
//         chapter15_demo();
//         chapter16_demo();
//         chapter17_demo();
//         chapter18_demo();

    }
}

