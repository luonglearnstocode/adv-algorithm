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
    
    public void minmax2() { // 2n - 2
        for (int i = 1; i < arr.length; i++) {
            if (less(i, min))
                min = i;
            if (less(max, i))
                max = i;
        }
    }
    
    public void minmax() { // 3n/2 - 2
        int N = arr.length;
        int[] result = minmaxHelper(0, N - 1);
        min = result[0];
        max = result[1]; 
    }
    
    /*
    * T(2)  = 1
    * T(n)  = 2T(n/2) + 2 
    *       = 4T(n/4) + 4 + 2
    *       = 8T(n/8) + 8 + 4 + 2
    *       = ...
    *       = n/2 + (2^1 + 2^2 + ... + 2^(logn-1)) 
    *       = n/2 + 2^logn - 2 = n/2 + n - 2 = 3n/2 - 2
    */
    public int[] minmaxHelper(int lo, int hi) {
        if (hi == lo)
            return new int[]{lo, lo};
        
        if (hi - lo == 1) {
            if (less(lo, hi)) 
                return new int[]{lo, hi};
            else 
                return new int[]{hi, lo}; 
        } else {
            int mid = (hi + lo) / 2;
            int[] left = minmaxHelper(lo, mid);
            int[] right = minmaxHelper(mid + 1, hi);
            return new int[]{min(left[0], right[0]), max(left[1], right[1])};
        }
    }
    
    // return left if arr[left] < arr[right] otherwise right
    public int min(int left, int right) {
        return less(left, right) ? left : right;
    }
    
    // return left if arr[left] > arr[right] otherwise right
    public int max(int left, int right) {
        return less(left, right) ? right : left;
    }
    
    // return if arr[i] < arr[j] 
    public boolean less(int i, int j) {
//        System.out.println(comparisions + " Comparing arr[" + i + "] = " + arr[i] 
//                + " vs arr[" + j + "] = " + arr[j]);
        comparisions++;
        return arr[i].compareTo(arr[j]) < 0;
    }
}
