/***
 * This class represents the hiker
 * Usage: keeps track of whatever we picked up
 * @author: Oviya Adhan 
 */
package project5;

import java.util.*;

public class Hiker {
    int raftCount, axeCount, foodCount;

    // constructor
    public Hiker(){
        raftCount = axeCount = foodCount = 0;
    }

    // collect supplies from the list of supplies
    public void collect(ArrayList<String> supplies){
        for(int i = 0; i < supplies.size(); i++){
            switch (supplies.get(i)) {
                case "raft": raftCount += 1;
                    break;
                case "axe": axeCount += 1;
                    break;
                case "food": foodCount += 1;
                    break;
            }
        }
    }

    // drop whatever indicated in supplies
    public void revertCollect(ArrayList<String> supplies){
        for(int i = 0; i < supplies.size(); i++){
            switch (supplies.get(i)) {
                case "raft": raftCount -= 1;
                    break;
                case "axe": axeCount -= 1;
                    break;
                case "food": foodCount -= 1;
                    break;
            }
        }
    }

    // these are pretty straightforward
    public int getFood() {
        return foodCount;
    }

    public int getAxe() {
        return axeCount;
    }

    public int getRaft() {
        return raftCount;
    }
}
