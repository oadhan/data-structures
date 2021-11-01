package project3;
import java.lang.IllegalArgumentException;
import java.lang.NullPointerException;

public class Number {
    // Make private 
    // Class Node
    class Node{
        Node next;
        int data;

        Node (int d){
            this.data = d;
            this.next = null;
        }

    }

    // Declare head
    Node head;
    int length;

    // Constructor method
    public Number(String number) throws IllegalArgumentException, NullPointerException{
        //Throws NullPointerException if number is null
        if (number == null){
            throw new NullPointerException("Input is null");
        }

        // Throws IllegalArgumentException if any character in string is not numeric
        if (!isNumeric(number)){
            throw new IllegalArgumentException("Input is not a number");
        }

        //Head set to null initially
        this.head = null;
        this.length = 0;

        //Iterates through string and creates new Node for each character
        for (int i = 0; i < number.length(); i++){
            char tmp = number.charAt(i);
            // Convert ASCII to integer
            int t = tmp - 48;
            Node current = new Node(t);
            current.next = head;
            head = current;
            this.length++;
        }
    }
    
    //Check is input is numeric
    public boolean isNumeric(String s) {  
        return s != null && s.matches("[-+]?\\d*\\.?\\d+");  
    }

    //length method
    public int length(){
        return length;
    }

    //toString method
    public String toString(){
        Node ptr = head;
        String str = "head -> ";
        while (ptr != null){
            str += ptr.data;
            str += " -> ";
            ptr = ptr.next;
        }
        str += " null";
        return str;
    }

    //add method
    public Number add(Number other) throws NullPointerException, IllegalArgumentException{
        if (other == null){
            throw new NullPointerException("Other is null");
        }

        if (!(other instanceof Number)) {
            throw new IllegalArgumentException("Not a Number object");
        }

        int maxLength;
        int flag = -1;
        if (other.length() >= this.length()){
            maxLength = other.length() + 1;
            flag = 0;
        }
        else{
            maxLength = this.length() + 1;
            flag = 1;
        }
        String s = "";
        for (int i = 0; i < maxLength; i++)
            s += "0";
        
        Number result = new Number(s);

        int remainder = 0;
        Node this_ptr = this.head;
        Node other_ptr = other.head;
        Node result_ptr = result.head;
        while (this_ptr != null && other_ptr != null){
            int tmp = ((this_ptr.data + other_ptr.data) % 10) + remainder;
            remainder = Math.floorDiv(this_ptr.data + other_ptr.data, 10);
            result_ptr.data = tmp;
            this_ptr = this_ptr.next;
            other_ptr = other_ptr.next;
            result_ptr = result_ptr.next;
        }
        // if (this.length == other.length){
        //     return result;
        // }
        result_ptr.next = null;
        return result;

    }


}