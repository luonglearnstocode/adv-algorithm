/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab1;

import java.io.PrintWriter;
import java.util.Random;

/**
 *
 * @author Jarkko
 */
public class MaxSubSeq {
    public static class MaxSubSum1 implements Stopwatch.Test {
        private int[] table;
        private int   answer;
        
        MaxSubSum1(int n) {
            Random randomGenerator = new Random();
            table = new int[n];
            
            for (int e: table)
                e = randomGenerator.nextInt(Integer.MAX_VALUE);
            
            answer = maxSubSum1(table);
        }
        
        @Override
        public void test() {
            if (maxSubSum1(table) != answer)
                throw new IllegalStateException("Incorrect function to be tested operation");
        }
        
        /* cubic maximum contiquous subsequence sum algorithm */
        public int maxSubSum1(int[] array) {            
            int i, j, k, maxSum = 0;

            for (i = 0; i < array.length; i++) {
                for (j = i; j < array.length; j++) {
                    int thisSum = 0;

                    for (k = i; k <= j; k++) {
                        thisSum += array[k];
                    }

                    if (thisSum >= maxSum) {
                        maxSum = thisSum;
                    }
                }
            }
            
            return maxSum;
        }
    }
    
    public static class MaxSubSum2 implements Stopwatch.Test {
        private int[] table;
        private int   answer;
        
        MaxSubSum2(int n) {
            Random randomGenerator = new Random();
            table = new int[n];
            
            for (int e: table)
                e = randomGenerator.nextInt(Integer.MAX_VALUE);
            
            answer = maxSubSum2(table);
        }
        
        @Override
        public void test() {
            if (maxSubSum2(table) != answer)
                throw new IllegalStateException("Incorrect function to be tested operation");
        }
        
        /* quadratic maximum contiquous subsequence sum algorithm */
        int maxSubSum2(int[] array) {
            int i, j, maxSum = 0;

            for (i = 0; i < array.length; i++) {
                int thisSum = 0;

                for (j = i; j < array.length; j++) {
                    thisSum += array[j];

                    if (thisSum > maxSum) {
                        maxSum = thisSum;
                    }
                }
            }

            return maxSum;
        }
    }
    
    public static class MaxSubSum3 implements Stopwatch.Test {
        private int[] table;
        private int   answer;
        
        MaxSubSum3(int n) {
            Random randomGenerator = new Random();
            table = new int[n];
            
            for (int e: table)
                e = randomGenerator.nextInt(Integer.MAX_VALUE);
            
            answer = maxSubSum3(table);
        }
        
        @Override
        public void test() {
            if (maxSubSum3(table) != answer)
                throw new IllegalStateException("Incorrect function to be tested operation");
        }
    
        /* recursive maximum contiguous subsequence sum algorithm */
        int maxSumRec(int[] array, int left, int right) {
            int center;
            int maxLeftSum, maxLeftBorderSum, leftBorderSum;
            int maxRightSum, maxRightBorderSum, rightBorderSum;
            int i;

            if (left == right) // base case
            {
                if (array[left] > 0) {
                    return array[left];
                } else {
                    return 0;
                }
            }

            center = (left + right) / 2;
            maxLeftSum = maxSumRec(array, left, center);
            maxRightSum = maxSumRec(array, center + 1, right);

            maxLeftBorderSum = 0;
            leftBorderSum = 0;
            for (i = center; i >= left; i--) {
                leftBorderSum += array[i];
                if (leftBorderSum > maxLeftBorderSum) {
                    maxLeftBorderSum = leftBorderSum;
                }
            }

            maxRightBorderSum = 0;
            rightBorderSum = 0;
            for (i = center + 1; i <= right; i++) {
                rightBorderSum += array[i];
                if (rightBorderSum > maxRightBorderSum) {
                    maxRightBorderSum = rightBorderSum;
                }
            }

            return Math.max(Math.max(maxLeftSum, maxRightSum), maxLeftBorderSum + maxRightBorderSum);
        }  
        
        /* divide-and-conquer maximum contiguous subsequence sum algorithm */
        int maxSubSum3(int[] array) {
            return maxSumRec(array, 0, array.length-1);
        }

    }
    
    public static class MaxSubSum4 implements Stopwatch.Test {
        private int[] table;
        private int   answer;
        
        MaxSubSum4(int n) {
            Random randomGenerator = new Random();
            table = new int[n];
            
            for (int e: table)
                e = randomGenerator.nextInt(Integer.MAX_VALUE);
            
            answer = maxSubSum4(table);
        }
        
        @Override
        public void test() {
            if (maxSubSum4(table) != answer)
                throw new IllegalStateException("Incorrect function to be tested operation");
        }
        
        /* linear-time maximum contiguous subsequence sum algorithm */
        int maxSubSum4(int[] array) {
            int maxSum = 0, thisSum = 0;
            int j;

            for (j = 0; j < array.length; j++) {
                thisSum += array[j];

                if (thisSum > maxSum) {
                    maxSum = thisSum;
                } else if (thisSum < 0) {
                    thisSum = 0;
                }
            }

            return maxSum;
        }
    }
    
    private static final int N = 3;        // number of test points
    private static final int STARTN = 1000;
    private static final int INCN = 500;
    private static final String OUTPUTFILE = "data.csv";
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Stopwatch.main(args);
        int arrayN;

        try {
            PrintWriter writer = new PrintWriter(OUTPUTFILE, "UTF-8");

            System.out.println("Number of CPU cores " + Runtime.getRuntime().availableProcessors());
            writer.println("N;N^3;N^2;NlogN;N");
            Stopwatch sw = new Stopwatch();
            arrayN = STARTN;
            for (int i = 0; i < N; i++) {
                System.out.println("N: " + arrayN);
                MaxSubSum1 koe1 = new MaxSubSum1(arrayN); MaxSubSum2 koe2 = new MaxSubSum2(arrayN); MaxSubSum3 koe3 = new MaxSubSum3(arrayN); MaxSubSum4 koe4 = new MaxSubSum4(arrayN);
                StringBuilder result = new StringBuilder(); result.append(arrayN);                

                sw.measure(koe1); result.append(";"); sw.toValue(result);
                sw.measure(koe2); result.append(";"); sw.toValue(result);
                sw.measure(koe3); result.append(";"); sw.toValue(result);
                sw.measure(koe4); result.append(";"); sw.toValue(result);
                writer.println(result.toString().replace(".",","));
                
                arrayN += INCN;
            }
            
            writer.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
}
