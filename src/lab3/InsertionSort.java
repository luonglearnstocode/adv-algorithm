package lab3;

/**
 *
 * @author Luong Nguyen
 */
public class InsertionSort implements Stopwatch.Test {
    private Comparable[] array;
    
    public void setArray(Comparable[] array) {
        this.array = array;
    }
    
    public void sort() {
        int n = array.length;
        for (int i = 1; i < n; i++) {
            int j = i;
            Comparable tmp = array[i];
            while (j > 0 && TestLab3a.less(tmp, array[j-1])) {
                array[j] = array[j-1];
                j--;
            }
            array[j] = tmp;
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
