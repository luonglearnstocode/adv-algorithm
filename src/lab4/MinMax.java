package lab4;

/**
 *
 * @author Luong Nguyen
 */
public class MinMax {
    private int min;
    private int max;
    private int comparisions;
    private Comparable[] arr;
    
    public MinMax(Comparable[] array) {
        arr = array;
        min = 0;
        max = 0;
        comparisions = 0;
    }
    
    public int getMax() {
        return max;
    }
    
    public int getMin() { 
        return min;
    }
    
    public int getComparisons() {
        return comparisions;
    }
    
    public void minmax2() {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i].compareTo(arr[min]) < 0)
                min = i;
            if (arr[i].compareTo(arr[max]) > 0)
                max = i;
            comparisions += 2;
        }
    }
    
    public void minmax() {
        
    }
}
