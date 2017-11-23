package lab6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author Luong Nguyen
 */
public class Knapsack {
    private static int capacity;
    private int N;
    private static Item[] items;
    private int[][] memo;
    
    public Knapsack() {
        input();
        memo = new int[N][capacity+1];
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
        for (int i = 0; i < N; i++) {
            System.out.println("\t" + items[i]);
        }
    } 
    
    public void bruteForce() {
        System.out.println("################################\tBrute force");        
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
                System.out.println("Half the search space enumerated so far");  
            }
            
        } 
        
        System.out.println("Solution: value =  " + value + " weight = " + weight);
        System.out.println("Items: " + best);
        for (int j = 0; j < best.length(); j++) {
            if (best.charAt(j) == '1') {
                System.out.print(items[j] + "    ");
            }
        }
        System.out.println("");
    }
    
    public void greedy() {
        System.out.println("################################\tGreedy");
        Arrays.sort(items);
        output();
        int w = 0; int v = 0;
        ArrayList<Item> choosen = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            Item item = items[i];
            if (w + item.weight <= capacity) {
                w += item.weight;
                v += item.value;
                choosen.add(item);
            } else { // until 1 doesn't fit, then halt
                break;
            } // could also go through the remaining items
        }
        
        System.out.println("Solution: value =  " + v + " weight = " + w);
        System.out.println("Items: ");
        for (Item item : choosen) {
            System.out.print(item + "    ");
        }
        System.out.println("");
    }
    
    public void greedyHeuristic() {
        System.out.println("################################\tGreedy heuristic");
        Arrays.sort(items);
        
        int best = 0; int weight = 0;
        ArrayList<Item> solution = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            ArrayList<Item> list = new ArrayList<>();
            // put 1 item first to the knapsack
            Item item = items[i];
            if (item.weight > capacity) continue;           
            list.add(item);
            int w = item.weight;
            int v = item.value;
            
            // run greedy on the remaining items
            for (int j = 0; j < N; j++) {
                if (j != i) {
                    Item next = items[j];
                    if (w + next.weight <= capacity) {
                        w += next.weight;
                        v += next.value;
                        list.add(next);
                    } else { 
                        break;
                    }
                }
            }
            
            if (v > best) {
                best = v;
                weight = w;
                solution = list;
            }
        }
        
        System.out.println("Solution: value =  " + best + " weight = " + weight);
        System.out.println("Items: ");
        for (Item item : solution) {
            System.out.print(item + "    ");
        }
        System.out.println("");
    }
    
    public void bruteForceMultithread() throws InterruptedException {
        System.out.println("################################\tMultithread Brute force"); 
        int nThreads = Runtime.getRuntime().availableProcessors();
        int n = (int) Math.pow(2, N);
        int part = n / nThreads;
        ArrayList<BruteForceThread> threads = new ArrayList<>();
        
        for (int i = 0; i < nThreads; i++) {
            int from = i * part;
            int to = (i == (nThreads-1)) ? n :(i+1) * part;
            threads.add(new BruteForceThread(from, to));
//            System.out.println("from " + from + " to " + to);
        }
        
        for (BruteForceThread t : threads) {
            t.start();
        }
        
        for (BruteForceThread t : threads) {
            t.join();
        }
        
        int value = 0;
        int weight = 0;
        String best = "";
        for (BruteForceThread t : threads) {
            if (t.value > value) {
                value = t.value;
                weight = t.weight;
                best = t.best;
            }
        }
        
        System.out.println("Solution: value =  " + value + " weight = " + weight);
        System.out.println("Items: " + best);
        for (int j = 0; j < best.length(); j++) {
            if (best.charAt(j) == '1') {
                System.out.print(items[j] + "    ");
            }
        }
        System.out.println("");
    }
    
    public int dynamic(int i, int c) {
        if (c == 0 || i == N) return 0; // base case
        if (memo[i][c] != 0) return memo[i][c]; // don't have to calculate again
        
        Item item = items[i];
        
        memo[i][c] = (item.weight > c)  ? dynamic(i+1, c) // if item's weight is more than capacity, cannot include
                                        : Math.max(item.value + dynamic(i + 1, c - item.weight), // take this item
                                                    dynamic(i + 1, c)); // not take this item
                
        return memo[i][c];
    }
    
    private final static String FILE = "inputLab6/hard33.txt";
    
    public static void main(String[] args) throws InterruptedException {
        Knapsack ks = new Knapsack();
        ks.output();      

        System.out.println("Which algorithm ?: ");
        System.out.println("\t1. Brute force");
        System.out.println("\t2. Greedy");
        System.out.println("\t3. Greedy heuristic");
        System.out.println("\t4. Multithread brute force");
        System.out.println("\t5. Dynamic programming");
        System.out.print("You select: ");
        Scanner sc = new Scanner(System.in);
        int alg = Integer.parseInt(sc.nextLine());
        
        long startTime = System.currentTimeMillis();
        switch (alg) {
            case 1:
                ks.bruteForce();
                break;
            case 2:
                ks.greedy();
                break;
            case 3:
                ks.greedyHeuristic();
                break;
            case 4:
                ks.bruteForceMultithread();
                break;
            case 5:
                System.out.println("################################\tDynamic Programming");
                int solution = ks.dynamic(0, capacity);
                System.out.println("Solution: " + solution);
                break;
        }
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Running time: " + totalTime + " ms");
    }
    
    public static class BruteForceThread extends Thread {
        int from;
        int to;
        int value;
        int weight;
        String best;
        
        public BruteForceThread(int from, int to) {
            this.from = from;
            this.to = to;
            value = 0;
            weight = 0;
            best = "";
        }
        
        @Override
        public void run() {
            for (int i = from; i < to; i++) {
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
            } 
        }
        
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
                return -1;
            else if (cmp < 0) 
                return 1;
            else
                return 0;
        }
        
        public String toString() {
            return "No." + number + " (" + value + ", " + weight + ")";
        }
    }
}
