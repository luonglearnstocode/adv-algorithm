package lab4;

import java.io.PrintWriter;
import java.security.SecureRandom;

public class Lab4b {
    static final String AB = "0123456789";
    static SecureRandom rnd = new SecureRandom();
    private static final String OUTPUTFILE = "inputOutput/lab4b.csv";

    /* create a random string array with lenght of n */
    static String createTestNumber(int n) {
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            int c;
            if (i > 0) // ensure that the first digit is not '0'
            {
                c = rnd.nextInt(AB.length());
            } else {
                c = 1 + rnd.nextInt(AB.length() - 1);
            }
            sb.append(AB.charAt(c));
        }
        return sb.toString();
    }
    
    public static void main(String[] args) {
        try {
            PrintWriter writer = new PrintWriter(OUTPUTFILE, "UTF-8");
            writer.println("N;mulCounter");
            
            Bignum n1, n2, result;
            int n;
            String s1, s2;
            for (int i = 1; i < 30; i++) {
                s1 = createTestNumber(i);
                n1 = new Bignum(s1);
                System.out.format("%1$30s * ", n1.toString());
                s2 = createTestNumber(i);
                n2 = new Bignum(s2);
                System.out.format("%1$30s = ", n2.toString());
                n1.clrMulCounter();
                n2.clrMulCounter();
                result = n1.mulBigNum(n2);
                n = result.rclMulCounter();
                System.out.format("%1$60s (%2$d)\n", result, n);
                
                StringBuilder toWrite = new StringBuilder(); 
                toWrite.append(i);                
                toWrite.append(";"); 
                toWrite.append(n);
                writer.println(toWrite.toString());
            }
            
            writer.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
//        Bignum n1, n2, result;
//        int n;
//        String s1, s2;
//        try {
//            for (int i = 1; i < 20; i++) {
//                s1 = createTestNumber(i);
//                n1 = new Bignum(s1);
//                System.out.format("%1$30s * ", n1.toString());
//                s2 = createTestNumber(i);
//                n2 = new Bignum(s2);
//                System.out.format("%1$30s = ", n2.toString());
//                result = n1.mulBigNum(n2);
//                n = result.rclMulCounter();
//                System.out.format("%1$60s (%2$d)\n", result, n);
//                
//                
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//        }
    }
    
}
