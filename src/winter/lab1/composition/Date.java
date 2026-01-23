package winter.lab1.composition;

/**
 * @author Laken
 * @date 2026-01-14
 * @description
 */
public class Date {
    int[] days = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    int month;
    int day;
    int year;

    // constructor
    public Date() {
    }

    public Date(int year, int month, int day) {
        // change the date
        setYear(year);
        setMonth(month);
        setDay(day);
    }

    // getters and setters
    public int getDay() {
        return this.day;
    }

    public int getMonth() {
        return this.month;
    }

    public int getYear() {
        return this.year;
    }

    public void setDay(int day) {
        if (month == 2 && day == 29 && ((year % 400 == 0) || (year % 4 == 0 && year % 100 != 0))) {
            //day is valid
            this.day = day;
        } else if (day < 1 || day > days[month]) {
            // day is not valid default to 1
            this.day = 1;
        } else {
            //day is valid
            this.day = day;
        }
    }

    public void setMonth(int month) {
//        setMonth should ensure a month between 1 and 12.
        if (1 <= month && month <= 12) {
            this.month = month;
        } else {
            // month is not valid default to 1
            this.month = 1;
            // System.out.println("Invalid value for month");
        }
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String toString() {
//        return "hello world!";
        return year + "-" + month + "-" + day;
    }

//    public static void main(String[] args) {
//
//    }
}
