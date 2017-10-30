package lab2;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Luong Nguyen
 */
public class LZW {
    final static String INPUT = "input2c.txt";  
//    final static String INPUT = "input2cOther.txt"; 
    final static String OUTPUT = "output2c.txt"; 
    final static String OUTPUT2 = "decompressed.txt";
    
    public static String readFile(String path) throws IOException{
        String content = new String(Files.readAllBytes(Paths.get(path))); 
        return content;
    }
    
    public static String findLongestPrefix(Map map, String s) {
        String result = "";
        for (int i = 0; i < s.length(); i++) {
            result += s.charAt(i);
            System.out.println("check prefix - " + result);
            if (!map.containsKey(result)) {
                return result.substring(0, result.length() - 1);
            }
        }
        return s;
    }
    
    public static void compress() throws IOException {
        System.out.println("###########################################################");
        System.out.println("Compressing");
        String content = readFile(INPUT);
        System.out.println("Original content: " + content);
        DataOutputStream output = new DataOutputStream(new FileOutputStream(OUTPUT));
        
        // initialize symbol table
        Map<String, Integer> map = new HashMap<>();
        int i;
        for (i = 0; i < 256; i++) {
            map.put("" + (char) i, i);
//            System.out.println("" + (char) i + " " + i);
        }    
//        i++;
  
        while (content.length() > 0) {
            // get longest prefix
            System.out.println("content: " + content);
            String longestPrefix = findLongestPrefix(map, content);
            if (longestPrefix == null) break;            
            System.out.println("Longest prefix: " + longestPrefix + " code: " + map.get(longestPrefix));
//            output.writeInt(map.get(longestPrefix)); //https://cs.wmich.edu/gupta/teaching/cs1120/1120Fall12web/codeJava/advanceFileIO.txt
            output.writeShort(map.get(longestPrefix)); // https://www.tutorialspoint.com/java/java_dataoutputstream.htm
            // add to table
            int len = longestPrefix.length();
            if (len < content.length()) {
                map.put(content.substring(0, len + 1), i++);
                System.out.println("\tPut " + content.substring(0, len + 1) + "->" + i);
            }               
                
            content = content.substring(len); // the remaining of content
//            System.out.println(longestPrefix);
        }
        output.close();
        System.out.println("Compression completed");
    }
    
    public static void decompress() throws FileNotFoundException, IOException {
        System.out.println("###########################################################");
        System.out.println("Decompressing");
        // initialize symbol table
        Map<Integer, String> map = new HashMap<>();
        int i; // next available code value
        for (i = 0; i < 256; i++) {
            map.put(i, "" + (char) i);
//            System.out.println("" + (char) i + " " + i);
        }    
//        map.put(i++, "");
        
        DataInputStream input = new DataInputStream(new BufferedInputStream(new FileInputStream(OUTPUT)));
        DataOutputStream output = new DataOutputStream(new FileOutputStream(OUTPUT2));
        Integer code = (int) input.readShort();
//        Integer code = input.readInt();
        String p = map.get(code);
        
        try {
            while (true) {
                System.out.println(code + " represents " + p);
                output.write(p.getBytes());
//                code = input.readInt();
                code = (int) input.readShort();
                String s = map.get(code);
//                When a code is not in the table, its key is lastP followed by first character of lastP
                if (code == i) s = p + p.charAt(0); 
                System.out.println("Next code " + code + " next string: " + s);
                
                System.out.println("\tPutting " + i + "->" + (p + s.charAt(0)));
                map.put(i++, p + s.charAt(0));
                p = s;
            }
        } catch (EOFException ex) {
            input.close();
            output.close();
        }
        System.out.println("Decompression completed");
    }
    
    public static void main(String[] args) throws IOException {
        compress();
        decompress();
    }
}
