package lab2.array.arrayminibook;

import java.util.Scanner;

/**
 * @author Laken
 * @date 2025-11-19
 * @description ----------------------------------------
 * 1. Create an array of 3 Strings to store your favorite movies.
 * 2. Use Scanner to fill the array from user input.
 * 3. Print the movie names backward (from last to first).
 * ----------------------------------------
 */
public class Chapter5_UserInput {
    public static void main(String[] args) {

        String[] myFavoriteMovies = new String[3];
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the name of your favorite movies: ");
        for (int i = 0; i < myFavoriteMovies.length; i++) {
            System.out.println("Movie " + i);
            myFavoriteMovies[i] = sc.nextLine();
        }

        for (int i = myFavoriteMovies.length - 1; i >= 0; i--) {
            System.out.println(i + ": " + myFavoriteMovies[i]);
        }

        sc.close();
    }
}
