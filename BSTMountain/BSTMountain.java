/***
 * This class represents the Binary Search Tree (BST) mountain
 * @author: Oviya Adhan 
 * Reference: GeeksForGeeks AVL Tree
 */

package project5;

import java.util.ArrayList;

public class BSTMountain {
    BSTNode root;
    boolean added;

    // Construct an empty tree
    public BSTMountain () {
        root = null; 
    }

    // Returns the height of BSTNode node
    private int height(BSTNode node){
        if (node == null)
            return 0;
        return node.height;
    }

    // Returns the height of the highest node in the tree
    // which is also the height of the tree 
    public int maxHeight(){
        return root.height;
    }

    // AVL - does what it says on the can
    int getBalanceFactor(BSTNode node) {
        if (node == null) 
            return 0;
        if (node.right == null)
            return node.height;
        if (node.left == null)
            return -node.height;
        return height(node.left) - height(node.right);
    }

        // LL Rotation
    // returns reference to the subtree's new root from after LL rotation
    BSTNode balanceLL (BSTNode A) {
        BSTNode B = A.left;
        A.left = B.right;
        B.right = A;
        return B;
    }

    // RR Rotation
    // returns reference to the subtree's new root from after RR rotation
    BSTNode balanceRR (BSTNode A) {
        BSTNode B = A.right;
        A.right = B.left;
        B.left = A;
        return B;
    }
    
    // LR Rotation
    // returns reference to the subtree's new root from after LR rotation
    BSTNode balanceLR (BSTNode A){
        BSTNode B = A.left;
        BSTNode C = B.right;
        A.left = C.right;
        B.right = C.left;
        C.left = B;
        C.right = A;
        return C;
    }
    
    // RL Rotation
    // returns reference to the subtree's new root from after RL rotation
    BSTNode balanceRL (BSTNode A){
        BSTNode B = A.right;
        BSTNode C = B.left;
        A.right = C.left;
        B.left = C.right;
        C.right = B;
        C.left = A;
        return C;
    }

    // // AVL - does what it says on the can
    // // returns the node after rotating
    // BSTNode rightRotate(BSTNode n) {
    //     BSTNode l = n.left;
    //     BSTNode lr = l.right;

    //     l.right = n;
    //     n.left = lr;

    //     n.height = Math.max(height(n.left), height(n.right));
    //     l.height = Math.max(height(l.left), height(l.right));

    //     return l;
    // }

    // // AVL - does what it says on the can
    // // returns the node after rotating
    // BSTNode leftRotate(BSTNode n) {
    //     BSTNode r = n.right;
    //     BSTNode rl = r.left;

    //     r.left = n;
    //     n.right = rl;

    //     n.height = Math.max(height(n.left), height(n.right));
    //     r.height = Math.max(height(r.left), height(r.right));

    //     return r;
    // }

    // add a RestStop to the Tree
    // returns true if added, else returns false
    public boolean add (RestStop data) { 
        added = false; 
        root = add(data, root); // calling the helper method
        return added; 
    }

   // helper method
   // add RestStop data after node
   // returns the new node
    private BSTNode add (RestStop data, BSTNode node) {
        if (node == null) {
            added = true; 
            return new BSTNode(data); 
        }
        int comp = data.compareTo(node.data);
        
        //find the location to add the new value 
        if (comp > 0 ) { // add to the right subtree 
            node.right = add(data, node.right); 
        }
        else if (comp < 0 ) { // add to the left subtree
            node.left = add(data, node.left); 
        }
        else { // case equals, not added
            added = false; 
            return node; 
        }

        // update the height of the ancestor node
        node.height = 1 + Math.max(height(node.left), height(node.right));

        int balanceFactor = getBalanceFactor(node);

        if (balanceFactor > 1) {
            if (node.compareTo(node.left) < 0) // case LL
                return balanceLL(node);
            if (node.compareTo(node.left) > 0) // case LR
                return balanceLR(node);
        }

        if (balanceFactor < -1) {
            if (node.compareTo(node.right) > 0) // case RR
                return balanceRR(node);
            if (node.compareTo(node.left) < 0) // case RL
                return balanceRL(node);
        }
        return node; 
    }

    // Calling method to solve the BSTMountain
    public void solve(){
        Hiker hiker = new Hiker();
        ArrayList<String> solution = new ArrayList<String>();
        if (root != null)
            solution.add(root.getRestStop().getLabel());
        ArrayList<String> obstacles = new ArrayList<String>();
        // insight: height goes down by 1 every level, reach 1 if at bottom
        int currHeight = maxHeight(); 
        // insight: eats one food every time goes down, (which is equivalent to number of edges traveled)
        int pathLen = 0;
        solveRec(root, currHeight, solution, hiker, obstacles, pathLen); // calling helper method
    }

    // Helper method
    // Prints out the solutions to the tree if applicable
    void solveRec(BSTNode node, int currHeight, ArrayList<String> solution, Hiker hiker, ArrayList<String> obstacles, 
                    int pathLen){
        if (node == null) 
            return;

        // check if we have enough food
        if (hiker.getFood() < pathLen) 
            return;

        // check if we have axe for fallen tree
        if (hiker.getAxe() < count(obstacles, "fallen tree")) 
            return;

        // check if we have raft for river
        if (hiker.getRaft() < count(obstacles, "river"))
            return;
        
        // reach the bottom of the mountain
        if (currHeight == 1) {
            printSolution(solution);
            return;
        }

        if (node == root) {
            hiker.collect(root.getRestStop().getSupplies());
        }


        // explore the left path of the current node
        if (node.left != null) {
            ArrayList<String> rsSupplies = node.left.getRestStop().getSupplies();
            ArrayList<String> rsObstacles = node.left.getRestStop().getObstacles();
            //choose option
            hiker.collect(rsSupplies); // add supplies
            obstacles = appendTo(obstacles, rsObstacles); // add obstacles

            solution.add(node.left.getRestStop().getLabel()); 
            // explore option
            solveRec(node.left, currHeight - 1, solution, hiker, obstacles, pathLen+1);
            //revert option
            solution.remove(solution.size()-1);
            hiker.revertCollect(rsSupplies); // revert supplies
            obstacles = rm(obstacles, rsObstacles.size()); // revert obstacles
            
        }
        // explore the right path of the current node
        if (node.right != null) {
            ArrayList<String> rsSupplies = node.right.getRestStop().getSupplies();
            ArrayList<String> rsObstacles = node.right.getRestStop().getObstacles();
            //choose option
            hiker.collect(rsSupplies); // add supplies
            obstacles = appendTo(obstacles, rsObstacles); // add obstacles

            solution.add(node.right.getRestStop().getLabel());
            // explore option
            solveRec(node.right, currHeight - 1, solution, hiker, obstacles, pathLen+1);

            solution.remove(solution.size()-1);
            hiker.revertCollect(rsSupplies); // revert supplies
            obstacles = rm(obstacles, rsObstacles.size()); // revert obstacles
        }
    }

    // utility method
    // count the occurences of element e in list 
    // return an int - count
    int count(ArrayList<String> list, String e) {
        int count = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(e)) 
                count++;
        }
        return count;
    }

    // utility method to append list2 to list1 element by element
    // returns list1 after appending
    ArrayList<String> appendTo(ArrayList<String> list1, ArrayList<String> list2) {
        for (int i = 0; i < list2.size(); i++) {
            list1.add(list2.get(i));
        }
        return list1;
    }

    // utility method to remove last num elements from list
    // returns the list after removing
    ArrayList<String> rm(ArrayList<String> list, int num) {
        for (int i = 0; i < num; i++) {
            list.remove(list.size()-1);
        }
        return list;
    }

    // does what it says on the can
    void printSolution(ArrayList<String> sol) {
        for (int i = 0; i < sol.size(); i++) {
            System.out.print(sol.get(i) + " ");
        }
        System.out.println();
    }

    // internal BSTNode class
    class BSTNode implements Comparable <BSTNode> {
        RestStop data;
        BSTNode left;
        BSTNode right;
        int height;
    
        BSTNode (RestStop data) {
            this.data = data;
            this.height = 1;
        }
    
        // returns an int larger than zero if this node larger than the other
        // returns 0 if equals
        // returns an int smaller than zero if this is smaller than other
        public int compareTo ( BSTNode other ) {
            return this.data.compareTo(other.data);
        }
    
        // does what it say on the can
        RestStop getRestStop(){
            return this.data;
        }
    }

}

