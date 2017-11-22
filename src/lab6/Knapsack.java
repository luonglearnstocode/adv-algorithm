package lab6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author Luong Nguyen
 */
public class Knapsack {
    private int capacity;
    private int weight;
    private int value;
    private int N;
    private int[] values;
    private int[] weights;
    
    public Knapsack() {
        weight = 0;
        value = 0;
        input();
    }
    
    public boolean input() {
        try {
            Scanner sc = new Scanner(new File(FILE));
            N = sc.nextInt();
            values = new int[N];
            weights = new int[N];
            
            for (int i = 0; i < N; i++) {
                sc.nextInt();
                values[i] = sc.nextInt();
                weights[i] = sc.nextInt();
            }
            capacity = sc.nextInt();
            sc.close();  
            return true;
        } catch (FileNotFoundException ex) {
            return false;
        }
    }
    
    public void output() {
        System.out.println("Capacity: " + capacity);
        System.out.format("%20s%10s%n", "Value", "Weight");
        
        for (int i = 0; i < N; i++) {
            System.out.format("%10d%10d%10d%n", i+1, values[i], weights[i]);
        }
    } 
    
    public String bruteForce() {
        long startTime = System.currentTimeMillis();
        String best = "";
        int n = (int) Math.pow(2, N);
        int half = n/2;
        for (int i = 0; i < n; i++) {
            String bin = Integer.toBinaryString(i);
            int len = bin.length();
            int w = 0;
            int v = 0;
            for (int j = 0; j < len; j++) {
                if (bin.charAt(j) == '1') {
                    w += weights[j];
                    v += values[j];
                }
            }
            if (w < capacity && v > value) {
                weight = w;
                value = v;
                best = bin;
            }
            if (i == half) {
                long halfTime   = System.currentTimeMillis();
                System.out.println("Half the search space enumerated so far: " + (halfTime-startTime) + " ms");  
            }
            
        }
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Running time: " + totalTime + " ms");  
        return best;
    }
    
    private final static String FILE = "inputLab6/easy20.txt";
    
    public static void main(String[] args) {
        Knapsack bf = new Knapsack();
        bf.output();
        
        String best = bf.bruteForce();
        System.out.println("Total value: " + bf.value + " Weight: " + bf.weight);
        System.out.println("Items: " + best);
        for (int j = 0; j < best.length(); j++) {
            if (best.charAt(j) == '1') {
                System.out.format("%6d(%d,%d)", j+1, bf.values[j], bf.weights[j]);
            }
        }
        
    }
}
