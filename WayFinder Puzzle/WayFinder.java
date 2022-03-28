/*
Author: Oviya Adhan
Date: November 15, 2021
Note: This is my version of the Way Finder puzzle solver. It takes a string input, parses it, and solves the puzzle. 
The final solutions are printed to the console and a final count is given.
*/
package project4;
import java.util.*;

public class WayFinder {
    /** Main method
    Checks if input is solvable and then calls Solver class to solve puzzle
    @param arr - a String object
    */
    public static void main (String[] args){
        //Check if numeric
        if (verify(args)){
            //Convert to integer array
            int[] intArr = convert(args);
            //Pass integer array into Solver object
            if (intArr != null) {
                Solver puzzle = new Solver(intArr);
                //Solve
                puzzle.solve();
            }
        }
    }


    /** Verify method
    Verifies whether input elements are numeric.
    @param arr - a String object
    @return true- if elements are numeric
    @return false- if elements are NOT numeric
    */
    private static boolean verify(String[] arr){
        //Iterate through string and check if each element is numeric
        for (int i = 0; i < arr.length; i++){
            if(!isNumeric(arr[i])){
                System.out.println("Input must be numeric");
                return false;
            }
        } 
        return true;
    }


    /** Convert method
    Converts string input from user into an integer array
    @param arr - a String object
    @return integer array (each element is integer b/w 0 and 99 from String)
    */
    private static int[] convert(String[] arr){
        //Declare and initialize integer array or length of input String
        int[] converted = new int[arr.length];
        //Iterate through string and parse to integer and place into integer array 
        for (int i = 0; i < arr.length; i++){
            int num = Integer.parseInt(arr[i]);
            //Check if numbers in range
            if (num > 99 || num < 0){
                System.out.println("ERROR: the puzzle values have to be positive integers.");
                return null;
            }
            converted[i] = num;
        }

        //Check if last element is 0
        if (converted[converted.length - 1] != 0){
            System.out.println("Last element must equal 0");
            return null;
        }
        return converted;
    }

    /** 
    Check is input is numeric
    @param input string
    @return true or false
    */
    private static boolean isNumeric(String s) {  
        return s != null && s.matches("[-+]?\\d*\\.?\\d+");  
    }
}
