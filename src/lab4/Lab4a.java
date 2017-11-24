package lab4;

import java.io.PrintWriter;
import java.security.SecureRandom;

public class Lab4a {
    private static final int STARTN = 100;
    private static final int ENDN = 1000;
    private static final int INCN = 100;
    private static final String OUTPUTFILE = "inputOutput/lab4a.csv";
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
    private final static int N = 16;
    
    public static void main(String[] args) {     
        try {
            PrintWriter writer = new PrintWriter(OUTPUTFILE, "UTF-8");

            writer.println("N;conventional;minmax");

            for (int n = STARTN; n <= ENDN; n += INCN) {
                System.out.println("N: " + n);
                initTestSeq(n);
                
                MinMax minmax = new MinMax(collection);
                minmax.minmax2();
                int comparisons = minmax.getComparisons();
                System.out.println("N = " + N);
                System.out.println("Brute Force minmax search");
                System.out.println("Min index " + minmax.getMin() + ", max index " + minmax.getMax());
                System.out.println("Number of comparisions " + comparisons);
                
                StringBuilder result = new StringBuilder(); 
                result.append(n);                
                result.append(";"); 
                result.append(comparisons);
                result.append(";");
                
                minmax = new MinMax(collection);        
                minmax.minmax();
                comparisons = minmax.getComparisons();
                System.out.println("Divide and Conquer minmax search");
                System.out.println("Min index " + minmax.getMin() + ", max index " + minmax.getMax());
                System.out.println("Number of comparisions " + comparisons);
                result.append(comparisons);
                
                writer.println(result.toString());
            }
            
            writer.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
