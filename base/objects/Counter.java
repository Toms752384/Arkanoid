//209379239 Tom Sasson
package base.objects;

/**
 * @author Tom Sasson
 * @version 1
 * @since 2024 - 2 - 14
 */

public class Counter {
    //members
    private int value;
    /**
     * Constructor.
     * @param count is the number to apply.
     */
    public Counter(int count) {
        //apply
        this.value = count;
    }
    /**
     * add number to current count.
     * @param number is the number to add.
     */
    public void increase(int number) {
        //add
       this.value += number;
    }
    /**
     * subtract number from current count.
     * @param number is the number.
     */
    public void decrease(int number) {
        //subtract
        this.value -= number;
    }
    /**
     * Getter method.
     * @return value.
     */
    public int getValue() {
        //return member
        return this.value;
    }
}
