/***
 * This class represents a reststop.
 * @author: Oviya Adhan 
 */
package project5;
import java.util.*;

public class RestStop implements Comparable<RestStop>{
    String label;
    
    // this is not very good habit but it works for now
    ArrayList<String> supplies = new ArrayList<>();
    ArrayList<String> obstacles = new ArrayList<>();
    
    // construct a reststop from the String array parsed from the txt file
    public RestStop(String[] stop) {
        label = stop[0]; 
        for (int i = 1; i < stop.length; i++){ // get the list of obstacles and supplies
            if (stop[i].equals("raft") || stop[i].equals("axe") || stop[i].equals("food"))
                supplies.add(stop[i]);
            
            if(stop[i].equals("river")){
                obstacles.add(stop[i]);
            }

            if(stop[i].equals("fallen")){
                if(stop[i+1].equals("tree")){
                    obstacles.add("fallen tree");
                    i++;
                }
            }
        }
    }

    // these methods are pretty straightforward
    public String getLabel(){
        return label;
    }

    public ArrayList<String> getSupplies(){
        return supplies;
    }

    public ArrayList<String> getObstacles(){
        return obstacles;
    }
    
    // compare two reststops based on the label alphabetically (or asciily)
    // returns positive int if this > other, 0 if this == other and negative int if this < other
    public int compareTo(RestStop other){
        return this.label.compareTo(other.label);
    }
}
