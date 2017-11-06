package lab3;

import java.util.Arrays;

/**
 *
 * @author Luong Nguyen
 */
public class JavaSort implements Stopwatch.Test {
    private Comparable[] array;
    
    public void setArray(Comparable[] array) {
        this.array = array;
    }
    
    public void sort() {
        Arrays.sort(array);
    }

    @Override
    public void setup() {
        TestLab3a.shuffle(array);
    }

    @Override
    public void test() {
        sort();
    }
    
}
