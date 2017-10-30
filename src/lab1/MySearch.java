/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab1;

import java.util.Arrays;

/**
 *
 * @author Luong Nguyen
 */
public class MySearch {
    private Comparable[] array;
    
    public void setArray(Comparable[] array) {
        this.array = array;
    }
    
    /*
    * Returns the index of the element if the element is found in the array
    * Return -1 otherwise
    */
    public int LinearSearch(Comparable elem) {
        for (int i = 0; i < this.array.length; i++) {
            if (array[i].compareTo(elem) == 0) {
                return i;
            }
        }
        
        return -1;
    }
    
    /*
    * Assume the array is already sorted
    * Returns the index of the element if the element is found in the array
    * Return -1 otherwise
    */
    public int BinarySearch(Comparable elem) {
        int low = 0;
        int high = this.array.length - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            int compareResult = this.array[mid].compareTo(elem);
            
            if (compareResult == 0) {
                return mid;
            } 
            if (compareResult < 0) { 
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }
    
    public static void main(String[] args) {
        int N = 50;
        Integer[] array = new Integer[20];
        for (int i = 0; i < array.length; i++) {
            array[i] = (int)(N*Math.random());
        }

        Arrays.sort(array);
        MySearch mySearch = new MySearch();
        mySearch.setArray(array);
        System.out.println(Arrays.toString(array));
        
        System.out.println("");
        System.out.println("############################################################");

        System.out.println("\tTest LinearSearch:");
        for (int i = 0; i < N; i++) {
            int index = mySearch.LinearSearch(i);
            System.out.print("Number " + i);
            if (index == -1) {
                System.out.println(" not found");
            } else {
                System.out.println(" found at index " + index);
            }
        }
        
        System.out.println("");
        System.out.println("############################################################");
        
        System.out.println("\tTest BinarySearch:");
        for (int i = 0; i < N; i++) {
            int index = mySearch.BinarySearch(i);
            System.out.print("Number " + i);
            if (index == -1) {
                System.out.println(" not found");
            } else {
                System.out.println(" found at index " + index);
            }
        }
    }
}
