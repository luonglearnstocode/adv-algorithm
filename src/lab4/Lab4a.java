package lab4;

import java.security.SecureRandom;

public class Lab4a {
    static private Integer[] collection;
    static private SecureRandom rnd = new SecureRandom();

    /* create a random unsigned value array with lenght of n */
    static void initTestSeq(int n) {
        /* first create the collection of strings */
        collection = new Integer[n];
        for (int i = 0; i < n; i++) {
            collection[i] = rnd.nextInt();
        }
    }

    /* print out the sequence */
    static void printTestSeq() {
        for (int i = 0; i < collection.length; i++) {
            System.out.format("%1$02d: %2$11d\n", i, collection[i]);
        }
        System.out.println();
    }
    private final static int N = 10;
    
    public static void main(String[] args) {
        int max, min;
        initTestSeq(N); printTestSeq();
        MinMax minmax = new MinMax(collection);
        minmax.minmax2();
        System.out.println("Brute Force minmax search");
        System.out.println("Min index " + minmax.getMin() + ", max index " + minmax.getMax());
        System.out.println("Number of comparisions " + minmax.getComparisons());
//        minmax = new MinMax(collection);
//        minmax.minmax();
//        System.out.println("Divide and Conquer minmax search");
//        System.out.println("Min index " + minmax.getMin() + ", max index " + minmax.getMax());
//        System.out.println("Number of comparisions " + minmax.getComparisons());
    }
}
