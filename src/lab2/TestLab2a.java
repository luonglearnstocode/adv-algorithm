package lab2;

import java.security.SecureRandom;

/**
 *
 * @author Luong Nguyen
 */
public class TestLab2a {
    private final static int STRLEN = 32;
    private final static int N = 30;
    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static SecureRandom rnd = new SecureRandom();

    private static String randomString(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        }
        return sb.toString();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MyDictionary dict = new MyDictionary(N / 2);
        String[] tableStr;
        String search_element;
        int j = 0;
        Object empty, result;
        int r;
        /* first create sorted collection of strings */
        tableStr = new String[N];
        for (int i = 0; i < tableStr.length; i++) {
            tableStr[i] = randomString(STRLEN);
        }
        /* then store it to the dictionary */
        empty = new Object();
        for (int i = 0; i < tableStr.length; i++) {
            dict.put(empty, tableStr[i]);
        }
        System.out.println("Content of the dictionary:");
        dict.printDictionary();
        /* try to search half of the strings from the dictionary */
        System.out.println("\n\nHalf of the searches should succeed, half fail:");
        for (int i = 0; i < N / 2; i++) {
            j = rnd.nextInt(N);
            search_element = tableStr[j];
            if (i > N / 4) {
                search_element += "#"; // quater of the strings should not be found
            }
            result = dict.get(search_element);
            System.out.format("%1$2d: element %2$s (%3$02d), search result %4$s\n", i,
                    search_element, j, result != null ? "F" : "N");
        }
        /* then delete first and the middle element from the dictionary */
        dict.del(tableStr[0]);
        dict.del(tableStr[N / 2 - 1]);
        /* try to search again strings from the dictionary, first and the last should not be found
         */
        System.out.println("\n\nFirst and last search should fail, other should succeed:");
        for (int i = 0; i < N / 2; i++) {
            j = i;
            search_element = tableStr[i];
            result = dict.get(search_element);
            System.out.format("%1$2d: element %2$s (%3$02d), search result %4$s\n", i,
                    search_element, j, result != null ? "F" : "N");
        }
    }
}

