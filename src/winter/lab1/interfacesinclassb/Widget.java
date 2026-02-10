package winter.lab1.interfacesinclassb;

/**
 * @author Laken
 * @date 2026-02-10
 * @description
 */
public class Widget implements Comparable {
    private int id;
    private String name;
    private int amount;

    public Widget() {
    }
    // constructor

    public Widget(int id, String name, int amount) {
        this.id = id;
        this.name = name;
        this.amount = amount;
    }

    // getter/setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    // toString

    @Override
    public String toString() {
        return String.format("%d,%s #%d", id, name, amount);
    }

    /**
     * compares this Widget with another object for order.
     * use id to make the decision
     *
     * @param o Object - the other object we are comparing this to
     * @return int - returns -1, 0 or positive 1 if this object is less than,
     * equal to, or greater than the other object
     */
    public int compareTo(Object o) {
        int result = 0;
        // check if o is a widget
        if (o instanceof Widget otherWidget) {
            // this < o
            if (id < otherWidget.getId()) {
                result = -1;
            }
            // this == 0
            else if (id == otherWidget.getId()) {
                result = 0;
            }
            // this > o
            else if (id > otherWidget.getId()) {
                result = 1;
            }
        }
        return result;
    }
}
