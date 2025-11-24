package lab2.array.assignment2;

import java.util.Arrays;

/**
 * @author Laken
 * @date 2025-11-23
 * @description
 */
public class StudentGradeTest {
    public static void main(String[] args) {

        String[] name = {"Robin", "Jo", "Kelly", "Jaimie"};
        int[] midtermScore = {28, 78, 92, 83};
        int[] finalScore = {58, 75, 96, 79};
        int[] assignmentGrade = {33, 80, 90, 83};

        System.out.println("Names: " + Arrays.toString(name));
        System.out.println("Midterm: " + Arrays.toString(midtermScore));
        System.out.println("Final: " + Arrays.toString(finalScore));
        System.out.println("Assignment: " + Arrays.toString(assignmentGrade));

        int[] finalGrade = getFinalGrades(midtermScore, finalScore, assignmentGrade);

        System.out.println("Final Grades: " + Arrays.toString(finalGrade));

        double avg = calculateAverage(finalGrade);
        System.out.printf("Average final grade: %.2f%n", avg);

        String[] newName = new String[6];
        int[] newMidScore = new int[6];
        int[] newFinScore = new int[6];
        int[] newAssignGrade = new int[6];

        for (int i = 0; i < 4; i++) {
            newName[i] = name[i];
            newMidScore[i] = midtermScore[i];
            newFinScore[i] = finalScore[i];
            newAssignGrade[i] = assignmentGrade[i];
        }

        newName[4] = "Terry";
        newMidScore[4] = 86;
        newFinScore[4] = 76;
        newAssignGrade[4] = 91;

        newName[5] = "Kerry";
        newMidScore[5] = 71;
        newFinScore[5] = 75;
        newAssignGrade[5] = 78;

        int[] newFinalGrade = getFinalGrades(newMidScore, newFinScore, newAssignGrade);

        System.out.println("\nUpdated Names: " + Arrays.toString(newName));
        System.out.println("Updated Midterm: " + Arrays.toString(newMidScore));
        System.out.println("Updated Final: " + Arrays.toString(newFinScore));
        System.out.println("Updated Assignment: " + Arrays.toString(newAssignGrade));
        System.out.println("Updated Final Grades: " + Arrays.toString(newFinalGrade));

        double newAvg = calculateAverage(newFinalGrade);
        System.out.printf("New average final grade: %.2f%n", newAvg);

        int minIndex = 0;
        int maxIndex = 0;
        for (int i = 1; i < newFinalGrade.length; i++) {
            if (newFinalGrade[i] < newFinalGrade[minIndex]) minIndex = i;
            if (newFinalGrade[i] > newFinalGrade[maxIndex]) maxIndex = i;
        }
        System.out.printf("Lowest grade: %s %d%n", newName[minIndex], newFinalGrade[minIndex]);
        System.out.printf("Highest grade: %s %d%n", newName[maxIndex], newFinalGrade[maxIndex]);

        int passed = findFrequency(newFinalGrade, 60);
        System.out.printf("Number of students who passed (≥60): %d%n", passed);
    }

    public static int[] getFinalGrades(int[] mid, int[] fin, int[] ass) {
        int[] finalGrade = new int[mid.length];
        for (int i = 0; i < mid.length; i++) {
            finalGrade[i] = (int) (ass[i] * 0.15 + mid[i] * 0.40 + fin[i] * 0.45);
        }
        return finalGrade;
    }

    public static double calculateAverage(int[] grades) {
        int sum = 0;
        for (int grade : grades) sum += grade;
        return (double) sum / grades.length;
    }

    public static int findFrequency(int[] array, int value) {
        int count = 0;
        for (int grade : array) {
            if (grade >= value) count++;
        }
        return count;
    }
}
