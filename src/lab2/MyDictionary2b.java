package lab2;

/**
 *
 * @author Luong Nguyen
 */
public class MyDictionary2b {
    private static final double UPPER_LIMIT = 0.5;
    private static final double LOWER_LIMIT = 0.25;
    
    private int n; // number of buckets
    private int size; // number of key-value pairs
    private String[] keys;
    private Object[] values;
    
    public MyDictionary2b(int n) {
        this.n = n;
        size = 0;
        keys = new String[n];
        values = new Object[n];
    }
    
    public Object get(String key) {
        for (int i = hash(key); keys[i] != null; i = (i+1) % n) {
            if (keys[i].equals(key))
                return values[i];
        }
        
        return null;
    }
    
    public void put(Object item, String key) {
        if (size >= n * UPPER_LIMIT) // arrays are half full
            resize(nextPrime(n * 2));
        
        int i;
        for (i = hash(key); keys[i] != null; i = (i + 1) % n) { // next available bucket
            if (keys[i].equals(key)) { // already exists
                values[i] = item; // update value
                return;
            }
        }
        
        // now i is at empty bucket
        keys[i] = key;
        values[i] = item;
        size++;
    }
    
    public void del(String key) {
        if (get(key) == null) return;
        
        int i;
        // find position of the key
        for (i = hash(key); !keys[i].equals(key); i = (i + 1) % n) {
        }
        // now keys[i] equals key
        keys[i] = null;
        values[i] = null;
        
        // http://www.cs.rmit.edu.au/online/blackboard/chapter/05/documents/contribute/chapter/05/linear-probing.html
        // https://algs4.cs.princeton.edu/34hash/LinearProbingHashST.java.html
        i = (i + 1) % n;
        while (keys[i] != null) {
            String keyToRehash = keys[i];
            Object valueToRehash = values[i];
            keys[i] = null;
            values[i] = null;
            size--;
            put(valueToRehash, keyToRehash);
            i = (i + 1) % n;
        }
        
        size--;
        
        if (size <= n * LOWER_LIMIT) 
            resize(nextPrime(n / 2));
    }
    
    // print content of the dictionary
    public void printDictionary() {
        String toPrint = "";
        for (int i = 0; i < n; i++) {
            toPrint += i + ": ";
            if (keys[i] == null) {
                toPrint += "[empty]";
            } else {
                toPrint += keys[i];
            }

            toPrint += "\n";
        }
        System.out.println(toPrint);
    }
    
    public void resize(int n) {
        System.out.println("Resizing to size " + n);
        MyDictionary2b newDict = new MyDictionary2b(n);

        for (int i = 0; i < this.keys.length; i++) {
            if (keys[i] != null) {
                newDict.put(values[i],keys[i]);
            }
        }
        
        this.n = newDict.n;
        this.keys = newDict.keys;
        this.values = newDict.values;
    }
    
    // from Princeton algorithm course on Coursera
    private int hash(String key) {
        return (key.hashCode() & 0x7fffffff) % n;
    }
    
    /* find next prime number */
    private static int nextPrime(int n) {
        int prime = 0, i, nextPrime;
        /* check first if this is a prime number */
        for (i = 2; i < n / 2; i++) {
            if (n % i == 0) {
                prime = 1;
                break;
            }
        }
        if (prime == 1) {
            /* no, try to find next one */
            nextPrime = n;
            prime = 1;
            while (prime != 0) {
                nextPrime++;
                prime = 0;
                for (i = 2; i < nextPrime / 2; i++) {
                    if (nextPrime % i == 0) {
                        prime = 1;
                        break;
                    }
                }
            }
            return (nextPrime);
        } else /* yes, return this as is */ {
            return (n);
        }
    }
}
