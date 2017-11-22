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
    private int N;
    private Item[] items;
    
    public Knapsack() {
        input();
    }
    
    public boolean input() {
        try {
            Scanner sc = new Scanner(new File(FILE));
            N = sc.nextInt();
            items = new Item[N];
            
            for (int i = 0; i < N; i++) {
                sc.nextInt();
                items[i] = new Item(i+1, sc.nextInt(), sc.nextInt());
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
            System.out.format("%10d%10d%10d%n", i+1, items[i].value, items[i].weight);
        }
    } 
    
    public void bruteForce() {
        System.out.println("################################\tBrute force");
        long startTime = System.currentTimeMillis();
        
        String best = "";
        int weight = 0;
        int value = 0;
        int n = (int) Math.pow(2, N);
        int half = n/2;
        for (int i = 0; i < n; i++) {
            String bin = Integer.toBinaryString(i);
            int len = bin.length();
            int w = 0;
            int v = 0;
            for (int j = 0; j < len; j++) {
                if (bin.charAt(j) == '1') {
                    w += items[j].weight;
                    v += items[j].value;
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
        
        System.out.println("Solution: value =  " + value + " weight = " + weight);
        System.out.println("Items: " + best);
        for (int j = 0; j < best.length(); j++) {
            if (best.charAt(j) == '1') {
                System.out.print(items[j] + "\t");
            }
        }
        System.out.println("");
    }
    
    public void greedy() {
        
    }
    
    private final static String FILE = "inputLab6/easy20.txt";
    
    public static void main(String[] args) {
        Knapsack bf = new Knapsack();
        bf.output();
        bf.bruteForce();
           
        
    }
    
    public static class Item implements Comparable<Item> {
        private int number;
        private int value;
        private int weight;
        
        public Item(int number, int value, int weight) {
            this.number = number;
            this.value = value;
            this.weight = weight;
        }        

        @Override
        public int compareTo(Item o) {
            double cmp = 1.0*this.value / this.weight - 1.0*o.value / o.weight;
            if (cmp > 0) 
                return 1;
            else if (cmp < 0) 
                return -1;
            else
                return 0;
        }
        
        public String toString() {
            return number + " (" + value + ", " + weight + ")";
        }
    }
}
