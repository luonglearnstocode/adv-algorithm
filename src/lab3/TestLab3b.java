package lab3;

import java.io.PrintWriter;
import java.util.Random;
import static lab3.TestLab3a.generateString;

/**
 *
 * @author Luong nguyen
 */
public class TestLab3b {
    private static final int STARTN = 100;
    private static final int ENDN = 1000;
    private static final int INCN = 100;
    private static final String OUTPUTFILE = "lab3b.csv";
    
    public static void main(String[] args) { 
        /*
        ** Test each sort class
        */
        
//        String[] array = new String[50];
//        for (int i = 0; i < array.length; i++) {
//            array[i] = generateString(32);
//        } 
//        
//        BubbleSort bubble = new BubbleSort();
//        bubble.setArray(array);
//        bubble.setup();
//        System.out.println(TestLab3a.isSorted(array));
//        bubble.sort();
//        System.out.println(TestLab3a.isSorted(array));
//        
//        JavaSort javaSort = new JavaSort();
//        javaSort.setArray(array);
//        javaSort.setup();
//        System.out.println(TestLab3a.isSorted(array));
//        javaSort.sort();
//        System.out.println(TestLab3a.isSorted(array));
//        
//        InsertionSort insertionSort = new InsertionSort();
//        insertionSort.setArray(array);
//        insertionSort.setup();
//        System.out.println(TestLab3a.isSorted(array));
//        insertionSort.sort();
//        System.out.println(TestLab3a.isSorted(array));
  
        
        try {
            PrintWriter writer = new PrintWriter(OUTPUTFILE, "UTF-8");

            System.out.println("Number of CPU cores " + Runtime.getRuntime().availableProcessors());
            writer.println("N;Bubble;Insertion");
            Stopwatch sw = new Stopwatch();
            Random randomGenerator = new Random();

            for (int n = STARTN; n <= ENDN; n += INCN) {
                System.out.println("N: " + n);
                
                BubbleSort bubble = new BubbleSort();
                InsertionSort insertion = new InsertionSort();
              
                String[] array = new String[n];
                for (int i = 0; i < array.length; i++) {
                    array[i] = generateString(32);
                } 
               
                bubble.setArray(array);
                insertion.setArray(array);
                
                StringBuilder result = new StringBuilder(); 
                result.append(n);                
                
                sw.measure(bubble); result.append(";"); sw.toValue(result);
                sw.measure(insertion); result.append(";"); sw.toValue(result);
                
                writer.println(result.toString());
//                writer.println(result.toString().replace(".",","));
            }
            
            writer.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
