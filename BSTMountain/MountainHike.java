/***
 * This class represents the main program.
 * Usage: MountainHike <path to txt file>
 * @author: Oviya Adhan 
 */
package project5;

import java.util.*;
import java.io.*;

public class MountainHike {
    public static void main (String[] args){
        // Wrong usage. Program expected 1 arg
        if (args.length != 1){
            System.err.println("Program expects 1 argument.");
            System.exit(1);
        }

        try {
            // Get file path from input arg
            File file = new File(args[0]);

            // Pretty straightforward, does what it say on the can
            if (!file.exists()){ 
                System.err.println("File does not exist.");
                System.exit(1);
            }
            if (file.isDirectory()){
                System.err.println("File is directory.");
                System.exit(1);
            }
            if (!file.canRead()){
                System.err.println("File cannot be read.");
                System.exit(1);
            }

            // After making sure our file is there and readable
            // returns a scanner object of file
            Scanner sc = new Scanner(file);

            // Construct the BSTMountain
            BSTMountain mtn = process(sc);
            
            // Solve the moutain
            mtn.solve();

        } catch (Exception e){ // Some simple error handling
            System.err.println(e);
            System.exit(1);
        }
    }

    // this method takes the input from the scanner 
    // returns the BSTMountain
    static BSTMountain process(Scanner sc) throws FileNotFoundException{
        List<String[]> stops = new ArrayList<>();

        // go over line by line in the scanner
        while(sc.hasNextLine()){
            String line = sc.nextLine();
            stops.add(line.split(" "));
        }

        BSTMountain mtn = new BSTMountain();

        // add stops to the mountain
        for (int i = 0; i < stops.size(); i++){
            RestStop newStop = new RestStop(stops.get(i));
            mtn.add(newStop);
        }

        return mtn;
    }
}
