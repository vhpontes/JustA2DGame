/*
Code based in RyiSnow youtube channel:
https://www.youtube.com/c/RyiSnow
*/
 
package ai;

public class Node {
    
    Node parent;
    
    public int col;
    public int row;
    int gCost;
    int hCost;
    int fCost;
    boolean start;
    boolean goal;
    boolean open;
    boolean solid;
    boolean checked;
    
    public Node(int col, int row) {
        this.col = col;
        this.row = row;
        
    }
}
