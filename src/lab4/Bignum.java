package lab4;

import java.util.Arrays;

/**
 *
 * @author Jarkko
 */
public class Bignum {
    private byte[] number;          // least significand digit first (index 0), most significand last (index length-1)
    private static int mulCounter;  // variable to count the number of multiplications


    public Bignum(int n) {
        number = new byte[n];
    }
    
    public Bignum(String s) {
        int     n = s.length();
        number = new byte[n];

        for (int i = n-1; i >= 0; i--)
            number[n-i-1] = (byte)Character.getNumericValue(s.charAt(i));
    }

    
    /* print out the number to the string s */
    public String toString() {
        boolean flag = true;
        StringBuilder s = new StringBuilder();
        
        for (int i = number.length-1; i >= 0; i--) {
            if (flag) {
                if (number[i] != 0) {
                    flag = false;
                    s.append(number[i]);
                }
            } else {
                s.append(number[i]);
            }
        }
            
        
        return (s.toString());
    }


    /* print out the given number (for debug only) */
    public void printBigNum(String s) {
        System.out.println(s + ": " + toString());
    }


    /* create a new number whose digits are x[from, to) */
    public Bignum selectBigNum(int from, int to) {
        Bignum r = new Bignum(to-from);

        for (int i = from; i < to; i++)
            r.number[i - from] = number[i];

        return r;
    }


    /* subtract two numbers this - y */
    public Bignum subBigNum(Bignum y) throws Exception {
            Bignum r = new Bignum(number.length);
            int    carry;

            // sub digits, starting from the least significant digit
            carry = 0;
            for (int i = 0; i < number.length; i++) {
                r.number[i] = (byte)(number[i] - (i < y.number.length ? y.number[i] : 0) - carry);
                if (r.number[i] < 0) {
                    carry = 1;
                    r.number[i] += 10;
                } else
                    carry = 0;
        }

        if (carry > 0) {
            throw new Exception("Overflow in subtraction\n");
        }
//        System.out.println("sub " + y + " to " + this);
        return r;
    }


    /* add two numbers together this + y */
    public Bignum addBigNum(Bignum y) {
        Bignum r, a, b;
        int    carry;

        // a is the larger number, b is the smaller
        if (number.length > y.number.length) {
            a = this; b = y;
        } else {
            a = y; b = this;
        }

        r = new Bignum(a.number.length);

        // add digits, starting from the least significant digit
        carry = 0;
        for (int i = 0; i < a.number.length; i++) {
            r.number[i] = (byte)(a.number[i] + (i < b.number.length ? b.number[i] : 0) + carry);
            if (r.number[i] > 9) {
                carry = 1;
                r.number[i] -= 10;
            } else
                carry = 0;
        }

        if (carry > 0) {
            r.number = Arrays.copyOf(r.number, r.number.length+1);
            r.number[r.number.length-1] = 1;
        }
//        System.out.println("add " + y + " to " + this);
        return r;
    }


    /* multiply two numbers (this * y) together using divide-and-conquer technique */
    public Bignum mulBigNum(Bignum y) throws Exception {
		// you work is to be done here!!!
//        System.out.println("\tMultiply " + this + " vs " + y);
        int cmp = number.length - y.number.length;
        if (cmp > 0)
            y.number = y.addZerosBefore(cmp);
        else if (cmp < 0) {
            number = addZerosBefore(Math.abs(cmp));
        }
        
        int N = number.length;

        if (N == 1) {
            int result = number[0] * y.number[0];
            return new Bignum(result + ""); // convert to String to get BigNum  
        }
        
        N = (N % 2 == 0) ? N : N - 1; 
//        System.out.println("N = " + N);
        Bignum b = selectBigNum(0, number.length / 2);
        Bignum a = selectBigNum(number.length / 2, number.length);
        Bignum d = y.selectBigNum(0, y.number.length / 2);
        Bignum c = y.selectBigNum(y.number.length / 2, y.number.length);
//        System.out.println("a = " + a +
//                            " b = " + b +
//                            " c = " + c +
//                            " d = " + d);

        
        Bignum p1 = a.mulBigNum(c);
        Bignum p2 = b.mulBigNum(d);
        Bignum p3 = (a.addBigNum(b)).mulBigNum((c.addBigNum(d)));
//        System.out.println("p1 = " + p1 +
//                            " p2 = " + p2 +
//                            " p3 = " + p3);
        
        return p1.multiply10powerN(N).addBigNum( // p1*10^n + 
                        ((p3.subBigNum(p1).subBigNum(p2)).multiply10powerN(N/2))) // (p3 - p1 - p2)*10^(n/2)
                        .addBigNum(p2); // + p2
    }
    
    public Bignum multiply10powerN(int n) {
//        System.out.println("Multiply " + this + " to 10^" + n);
        Bignum r = new Bignum(number.length + n);
        for (int i = 0; i < r.number.length; i++) {
            r.number[i] = (i < n) ? 0 : number[i - n];
        }
        return r;
    }
    
    public byte[] addZerosBefore(int n) {
//        System.out.println("Add " + n + " zeros to " + this);
        byte[] arr = new byte[number.length + n];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (i < number.length) ? number[i] : 0;
        }
        return arr;
    }


    public void clrMulCounter() {
        mulCounter = 0;
    }


    public int rclMulCounter() {
        return (mulCounter);
    }
}
