package lab3;

/**
 *
 * @author Luong Nguyen
 */
public class BubbleSort implements Stopwatch.Test {
    private Comparable[] array;
    
    public void setArray(Comparable[] array) {
        this.array = array;
    }
    
    public void sort() {
        int n = array.length;
        int lastSwap;
        while (n != 0) {
            lastSwap = 0;
            
            for (int i = 1; i < n; i++) {
                if (TestLab3a.less(array[i], array[i-1])) {
                    TestLab3a.swap(array, i, i-1);
                    lastSwap = i;
                }
            }
            n = lastSwap;
        }
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
