package lab6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author Luong Nguyen
 */
public class BruteForce {
    private int capacity;
    private int weight;
    private int value;
    private int N;
    private int[] values;
    private int[] weights;
    
    public BruteForce() {
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
    
    private final static String FILE = "inputLab6/easy20.txt";
    
    public static void main(String[] args) {
        BruteForce bf = new BruteForce();
        bf.output();
    }
}
