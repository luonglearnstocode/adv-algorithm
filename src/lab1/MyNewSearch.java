/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab1;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author Luong Nguyen
 */
public class MyNewSearch {
    public static class MyLinearSearch implements Stopwatch.Test {
        private Comparable[] array;
        private Comparable[] toBeSearched;
    
        public void setArray(Comparable[] array) {
            this.array = array;
        }
        
        public void setToBeSearchArray(Comparable[] array) {
            this.toBeSearched = array;
        }
        
        public int LinearSearch(Comparable elem) {
            for (int i = 0; i < this.array.length; i++) {
                if (array[i].compareTo(elem) == 0) {
                    return i;
                }
            }
            return -1;
        }

        @Override
        public void test() {
            for (Comparable n : toBeSearched) {
                LinearSearch(n);
            }
        }
    }
    
    public static class MyBinarySearch implements Stopwatch.Test {
        private Comparable[] array;
        private Comparable[] toBeSearched;
    
        public void setArray(Comparable[] array) {
            this.array = array;
        }
        
        public void setToBeSearchArray(Comparable[] array) {
            this.toBeSearched = array;
        }
        
        public int BinarySearch(Comparable elem) {
            int low = 0;
            int high = this.array.length - 1;
            while (low <= high) {
                int mid = (low + high) / 2;
                int compareResult = this.array[mid].compareTo(elem);

                if (compareResult == 0) {
                    return mid;
                } 
                if (compareResult < 0) { 
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
            return -1;
        }

        @Override
        public void test() {
            for (Comparable n : toBeSearched) {
                BinarySearch(n);
            }
        }
    }
    
    private static final int STARTN = 10000;
    private static final int ENDN = 55000;
    private static final int INCN = 5000;
    private static final String OUTPUTFILE = "inputOutput/lab1b.csv";
    
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
    
    public static void main(String[] args) {

        try {
            PrintWriter writer = new PrintWriter(OUTPUTFILE, "UTF-8");

            System.out.println("Number of CPU cores " + Runtime.getRuntime().availableProcessors());
            writer.println("N;Linear;Binary");
            Stopwatch sw = new Stopwatch();
            Random randomGenerator = new Random();

            for (int n = STARTN; n <= ENDN; n += INCN) {
                System.out.println("N: " + n);
                MyLinearSearch linear = new MyLinearSearch();
                MyBinarySearch binary = new MyBinarySearch();
                
                /*
                * #####################################################################
                * Exercise 1b
                */
                
                // create array
//                Integer[] array = new Integer[n];
//                for (int i = 0; i < array.length; i++) {
//                    array[i] = randomGenerator.nextInt(Integer.MAX_VALUE);
//                }               
//                
//                // array to be searched
//                Integer[] toBeSearched = new Integer[500];
//                for (int i = 0; i < toBeSearched.length; i++) {
//                    toBeSearched[i] = randomGenerator.nextInt(Integer.MAX_VALUE);
//                }
                
                /*
                * #####################################################################
                * Exercise 1c
                */
                
                String[] array = new String[n];
                for (int i = 0; i < array.length; i++) {
                    array[i] = generateString(32);
                } 
                
                String[] toBeSearched = new String[500];
                for (int i = 0; i < toBeSearched.length; i++) {
                    toBeSearched[i] = generateString(32);
                }
                
                // #####################################################################
                
                Arrays.sort(array);
                
                linear.setArray(array);
                linear.setToBeSearchArray(toBeSearched);
                binary.setArray(array);
                binary.setToBeSearchArray(toBeSearched);
                
                StringBuilder result = new StringBuilder(); 
                result.append(n);                

                sw.measure(linear); result.append(";"); sw.toValue(result);
                sw.measure(binary); result.append(";"); sw.toValue(result);
                
                writer.println(result.toString());
//                writer.println(result.toString().replace(".",","));
            }
            
            writer.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
