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
    private boolean[] took;
    
    public Knapsack() {
        input();
        memo = new int[N+1][capacity+1];
        took = new boolean[N];
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
        long n = (long) Math.pow(2, N); // number of subsets
        long half = n/2;
        for (long i = 0; i < n; i++) { // for each subset 
            String bin = Long.toBinaryString(i);
            int len = bin.length();
            int w = 0; int v = 0;
            for (int j = 0; j < len; j++) { // get total value and weight 
                if (bin.charAt(j) == '1') {
                    w += items[len-1-j].weight;
                    v += items[len-1-j].value;
                }
            }
            if (w <= capacity && v > value) { // update solution
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
        for (int j = best.length() - 1 ; j >= 0; j--) {
            if (best.charAt(j) == '1') {
                System.out.print(items[best.length() - 1 - j] + "    ");
            }
        }
        System.out.println("");
    }
    
    public void greedy() {
        System.out.println("################################\tGreedy");
        Arrays.sort(items); // sort on value density in descending order
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
        Arrays.sort(items); // sort on value density in descending order
        
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
        int nThreads = Runtime.getRuntime().availableProcessors(); // get number of effective threads can be created 
        long n = (long) Math.pow(2, N);
        long part = n / nThreads; // number of subsets each thread will handle
        
        ArrayList<BruteForceThread> threads = new ArrayList<>();
        for (int i = 0; i < nThreads; i++) {
            long from = i * part;
            long to = (i == (nThreads-1)) ? n :(i+1) * part;
            threads.add(new BruteForceThread(from, to));
        }
        
        for (BruteForceThread t : threads) {
            t.start();
        }
        
        for (BruteForceThread t : threads) {
            t.join();
        } // now all threads are finished
        
        int value = 0;
        int weight = 0;
        String best = "";
        for (BruteForceThread t : threads) { // compare solutions from each thread
            System.out.println(t.from + " " + t.to + " : " + t.value + " " + t.best);
            if (t.value > value) {
                value = t.value;
                weight = t.weight;
                best = t.best;
            }
        }
        
        System.out.println("Solution: value =  " + value + " weight = " + weight);
        System.out.println("Items: " + best);
        for (int j = best.length() - 1 ; j >= 0; j--) {
            if (best.charAt(j) == '1') {
                System.out.print(items[best.length() - 1 - j] + "    ");
            }
        }
        System.out.println("");        
    }
    
    public int recurse(int i, int c) {
        if (c == 0 || i == N) return 0; // base case
        
        Item item = items[i];
        
        return (item.weight > c) ? recurse(i + 1, c) // if item's weight is more than capacity, cannot take it
                                : Math.max(item.value + recurse(i + 1, c - item.weight), // take items[i]
                                           recurse(i + 1, c));                           // not take items[i]
    }
    
    public int dynamic(int i, int c) {
        if (c == 0 || i == N) return 0; // base case
        if (memo[i][c] != 0) return memo[i][c]; // don't have to calculate again
        
        Item item = items[i];
        if (item.weight > c) { // if item's weight is more than capacity, cannot include
            memo[i][c] = dynamic(i+1, c);
        } else {
            int ifTake = item.value + dynamic(i + 1, c - item.weight);
            int ifNot = dynamic(i + 1, c);
            memo[i][c] = ifTake > ifNot ? ifTake : ifNot;
        }
        return memo[i][c];
    }
        
    public void dynamic() {
        System.out.println("################################\tDynamic Programming");
        int solution = dynamic(0, capacity);
        
        // backtrack the solution to get items list
        int j = capacity;
        for (int i = 0; i < N; i++) {
            if (memo[i][j] != memo[i+1][j]) { // means that items[i] is included
                j -= items[i].weight;
                took[i] = true;
            }
        }
        
        // print out included items
        int w = 0; int v = 0;
        for (int i = 0; i < N; i++) {
            if (took[i]) {
                v += items[i].value;
                w += items[i].weight;
                System.out.print(items[i] + "    ");
            }
        }
        
        System.out.println("\nSolution: value = " + solution + " weight = " + w);
    }
    
//    private final static String FILE = "inputLab6/easy4.txt"; // for testing backtracking of dynamic programming solution
    private final static String FILE = "inputLab6/easy20.txt";
//    private final static String FILE = "inputLab6/easy200.txt";
//    private final static String FILE = "inputLab6/hard33.txt";
//    private final static String FILE = "inputLab6/hard200.txt";
    
    public static void main(String[] args) throws InterruptedException {
        Knapsack ks = new Knapsack();
        ks.output();      

        System.out.println("Which algorithm ?: ");
        System.out.println("\t1. Brute force");
        System.out.println("\t2. Greedy");
        System.out.println("\t3. Greedy heuristic");
        System.out.println("\t4. Multithread brute force");
        System.out.println("\t5. Naive recurse");
        System.out.println("\t6. Dynamic programming");
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
                System.out.println("################################\tNaive Recurse");
                int solution = ks.recurse(0, capacity);
                System.out.println("Solution: " + solution);
                break;
            case 6:
                ks.dynamic();
                break;
        }
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Running time: " + totalTime + " ms");
    }
    
    public static class BruteForceThread extends Thread {
        long from;
        long to;
        int value;
        int weight;
        String best;
        
        public BruteForceThread(long from, long to) {
            this.from = from;
            this.to = to;
            value = 0;
            weight = 0;
            best = "";
        }
        
        @Override
        public void run() { // each thread find solution from the given subset
            for (long i = from; i < to; i++) {
                String bin = Long.toBinaryString(i);
                int len = bin.length();
                int w = 0;
                int v = 0;
                for (int j = 0; j < len; j++) {
                    if (bin.charAt(j) == '1') {
                        w += items[len-1-j].weight;
                        v += items[len-1-j].value;
                    }
                }

                if (w <= capacity && v > value) {
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
