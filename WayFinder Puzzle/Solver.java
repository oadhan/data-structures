package project4;

import java.util.*;

public class Solver {
    private List<Integer> puzzle;
    private int lastIndex, count;

    /** Constructor method
    Creates a Solver object by transforming parameter integer array arr into Array List object.

    @param arr - an integer array of puzzle
    */
    public Solver(int[] arr){
        puzzle = new ArrayList<Integer>();
        for (int i = 0; i < arr.length; i++)
            puzzle.add(arr[i]);
        this.lastIndex = puzzle.size() - 1;
        this.count = 0;
    }

    /** Helper solve method
    Solving method that gives solution for puzzle.

    */
    public void solve(){
        // Handles solution for puzzle with just one element of 0
        if (puzzle.size() == 1 && puzzle.get(0) == 0){
            System.out.println("[ 0 ]\nThere is 1 way through the puzzle.");
            return;
        }
        //Create array list to keep track of visited indices
        List<Integer> visited = new ArrayList<Integer>();
        solveRecursive(puzzle, visited, 0);

        if (count == 0) //
            System.out.println("No way through this puzzle.");
        else
            System.out.println("There are " + count + " ways through the puzzle.");
    }


    /** Recursive solving method
    Uses recursion to find all possible solutions to puzzle
    @param puzzle - an array list of input puzzle
    @param visited - an array list to track visited indexes
    @param currIndex - integer to keep track of index after each move
    */
    private void solveRecursive(List<Integer> puzzle, List<Integer> visited, int currIndex){
        //Base Case: If currIndex out of bounds, leave recursion
        if (currIndex > lastIndex || currIndex < 0 || visited.contains(currIndex)){
            return;
        }
        //Base Case: If currIndex reaches final index, puzzle is solved, pass to print
        if (currIndex == lastIndex){
            //Print solution
            printSolution(puzzle, visited);
            count++;
        }
        //Recursion
        else {
            for (int i = 0; i < 2; i++){
                //Try going left
                if (i == 0){
                    visited.add(currIndex);
                    solveRecursive(puzzle, visited, currIndex - puzzle.get(currIndex));
                    //if recursive call returned, invalid move, remove from visited
                    visited.remove(visited.size() - 1);
                }
                //Try going right
                if (i == 1){
                    visited.add(currIndex);
                    solveRecursive(puzzle, visited, currIndex + puzzle.get(currIndex));
                    visited.remove(visited.size() - 1);
                }
            }
        }
    }

    /** Printing solution
    Prints list of steps as arrays to console for solution passed in
    @param puzzle - an array list of input puzzle
    @param visited - an array list to track visited indexes
    */
    private void printSolution(List<Integer> puzzle, List<Integer> visited){
        //Iterate through visited indicies and print each array
        for (int i = 0; i < visited.size() - 1; i++){
            //Check if going right
            if (visited.get(i) < visited.get(i+1)){
                int dir = 1;
                printArray(visited.get(i), puzzle, dir);
            } else{ //Or else direction is left
                int dir = -1;
                printArray(visited.get(i), puzzle, dir);
            }
        }
        //Print final step line
        printArray(visited.get(visited.size()- 1), puzzle, 1);
        System.out.println();
    }


    /** print array
     Prints each individual step as an array

     @param index int
     @param puzzle array list of input puzzle
     @param dir int to show direction (1 = right, -1 = left)
     */
    private void printArray(int index, List<Integer> puzzle, int dir){
        //Declare and initialize final output array atring
        String output = "[ ";
        for (int i = 0; i < puzzle.size() - 1; i++){
            //If index is the index where step is happening
            if (i == index){
                String add;
                //Determine direction and add R or L accordingly
                if (dir == 1)
                    add = puzzle.get(i) + "R,  ";
                else
                    add = puzzle.get(i) + "L,  ";
                output += add;
            } else{
                String add = puzzle.get(i) + " ,  ";
                output += add;
            }
        }

        //Add final step and final bracket
        String add = puzzle.get(puzzle.size() - 1) + " ]";
        output += add;
        System.out.println(output);
    }
}
