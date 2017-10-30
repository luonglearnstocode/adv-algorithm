package lab2;

/**
 *
 * @author Luong Nguyen
 */
public class MyDictionary {
    private static final int UPPER_LIMIT = 5;
    
    private int n; // number of buckets
    private int size; // number of key-value pairs
    private MyNode[] table;
    
    public MyDictionary(int n) {
        this.n = n;
        size = 0;
        table = new MyNode[n];
    }   
    
    public Object get(String key) {
        int i = hash(key); // get the bucket index
        MyNode node = table[i]; // first node of the chain
        
        while (node != null) {
            if (node.getKey().equals(key))
                return node.getValue();
            node = node.getNext();
        }
        
        // not found 
        return null;
    }
    
    public void put(Object item, String key) {
        if (size >= UPPER_LIMIT*n) 
            resize(nextPrime(n*2)); // ~ double the size of the table array, but stay as prime number
        
        int i = hash(key);
        MyNode node = this.table[i];
        
        while (node != null) {
            if (node.getKey().equals(key)) {// already exists
                node.setValue(item); // update value
                return;
            }    
            node = node.getNext();
        }
        
        // put newNode at the front of the chain
        node = new MyNode(key, item, table[i]); 
        table[i] = node;        
        size++;
    }
    
    public void del(String key) {
        int i = hash(key);
        MyNode node = this.table[i];
        
        if (node == null) return; // no node at this bucket
        
        if (node.getKey().equals(key)) { // first node of the chain
            table[i] = node.getNext();
            size--;
            return;
        }
        
        while (node.getNext() != null) { // from 2nd node 
            if (node.getNext().getKey().equals(key)) {
                node.setNext(node.getNext().getNext());
                size--;
                return;
            }
            node = node.getNext();
        }      
    }
    
    // print content of the dictionary
    public void printDictionary() {
        String toPrint = "";
        for (int i = 0; i < n; i++) {
            toPrint += i + ": ";
            MyNode node = this.table[i];
            if (node == null) {
                toPrint += "[empty]";
            } else {
                String chain = "";
                while (node != null) {
//                    toPrint += node.getValue().toString();
                    chain += node.getKey() + ",";
                    node = node.getNext();
                }
                chain = chain.substring(0, chain.length() - 1);
                toPrint += chain;
            }
            
            toPrint += "\n";
        }
        System.out.println(toPrint);
    }
    
    public void resize(int n) {
        MyDictionary newDict = new MyDictionary(n);

        for (int i = 0; i < this.table.length; i++) {
            MyNode node = this.table[i];
            
            while (node != null) {
                newDict.put(node.getValue(), node.getKey());
                node = node.getNext();
            }
        }
        
        this.n = newDict.n;
        this.size = newDict.size;
        this.table = newDict.table;
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
