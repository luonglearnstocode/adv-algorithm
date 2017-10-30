package lab2;

/**
 *
 * @author Luong Nguyen
 */
public class MyNode {
    private String key;
    private Object value;
    private MyNode next;
    
    public MyNode(String key, Object value) {
        this(key, value, null);
    }
    
    public MyNode(String key, Object value, MyNode next) {
        this.key = key;
        this.value = value;
        this.next = next;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public MyNode getNext() {
        return next;
    }

    public void setNext(MyNode next) {
        this.next = next;
    }
 
}
