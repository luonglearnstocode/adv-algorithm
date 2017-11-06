package lab3;

import java.io.PrintWriter;
import java.util.Random;

/**
 *
 * @author Luong Nguyen
 */
public class TestLab3a {
    private static final int STARTN = 1000;
    private static final int ENDN = 10000;
    private static final int INCN = 1000;
    private static final String OUTPUTFILE = "lab3a.csv";
    
    /*
    * Generate random alpha numeric string
    * https://dzone.com/articles/generate-random-alpha-numeric
    */
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    
    public static String generateString(int length) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        
        return builder.toString();
    }
    
    public static void swap(Comparable[] a, int i, int j) {
        Comparable tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }
    
    public static boolean less(Comparable a, Comparable b) {
        return a.compareTo(b) < 0;
    }
    
    public static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++) {
            if (less(a[i], a[i-1])) return false;
        }
        return true;
    }
    
//    https://introcs.cs.princeton.edu/java/15inout/Shuffle.java.html
    public static void shuffle(Comparable[] a) {
        int n = a.length;
        for (int i = 0; i < n; i++) {
            int r = i + (int) (Math.random() * (n-i));   // between i and n-1
            swap(a, i, r);
        }
    }
    
    public static void main(String[] args) { 
        /*
        * Test each sort class
        */
        String[] array = new String[50];
        for (int i = 0; i < array.length; i++) {
            array[i] = generateString(32);
        } 
        
        InsertionSort insertionSort = new InsertionSort();
        insertionSort.setArray(array);
        insertionSort.setup();
        System.out.println(isSorted(array));
        insertionSort.sort();
        System.out.println(isSorted(array));
        
        JavaSort javaSort = new JavaSort();
        javaSort.setArray(array);
        javaSort.setup();
        System.out.println(isSorted(array));
        javaSort.sort();
        System.out.println(isSorted(array));
        
        
//        try {
//            PrintWriter writer = new PrintWriter(OUTPUTFILE, "UTF-8");
//
//            System.out.println("Number of CPU cores " + Runtime.getRuntime().availableProcessors());
//            writer.println("N;Linear;Binary");
//            Stopwatch sw = new Stopwatch();
//            Random randomGenerator = new Random();
//
//            for (int n = STARTN; n <= ENDN; n += INCN) {
//                System.out.println("N: " + n);
//                
//                InsertionSort insertionSort = new InsertionSort();
//                JavaSort javaSort = new JavaSort();
//              
//                String[] array = new String[n];
//                for (int i = 0; i < array.length; i++) {
//                    array[i] = generateString(32);
//                } 
//               
//                insertionSort.setArray(array);
//                javaSort.setArray(array);
//                
//                StringBuilder result = new StringBuilder(); 
//                result.append(n);                
//
//                sw.measure(insertionSort); result.append(";"); sw.toValue(result);
//                sw.measure(javaSort); result.append(";"); sw.toValue(result);
//                
//                writer.println(result.toString());
////                writer.println(result.toString().replace(".",","));
//            }
//            
//            writer.close();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
    }
}
