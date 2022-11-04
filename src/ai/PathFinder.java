package ai;

import entity.Entity;
import java.util.ArrayList;
import main.GamePanel;

public class PathFinder {

    GamePanel gp;
    Node[][] node;
    ArrayList<Node> openList = new ArrayList<>();
    public ArrayList<Node> pathList = new ArrayList<>();
    Node startNode, goalNode, currentNode;
    boolean goalReached = false;
    int step = 0;
    
    public PathFinder(GamePanel gp) {
        this.gp = gp;
        instantiateNodes();
    }
    
    public void instantiateNodes() {
        
        node = new Node[gp.maxWorldCol][gp.maxWorldRow];
        
        int col = 0;
        int row = 0;
        
        while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
            
            node[col][row] = new Node(col,row);
            
            col++;
            if(col == gp.maxWorldCol){
                col = 0;
                row++;
            }
        }
    }
    
    public void resetNodes() {
        
        int col = 0;
        int row = 0;
        
        while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
            node[col][row].open = false;
            node[col][row].checked = false;
            node[col][row].solid = false;
            
            col++;
            if(col == gp.maxWorldCol){
                col = 0;
                row++;
            }        
        }
        
        openList.clear();
        pathList.clear();
        goalReached = false;
        step = 0;
    }
    
    public void setNodes(int startCol, int startRow, int goalCol, int goalRow) {
        
        resetNodes();
                
        startNode = node[startCol][startRow];
        currentNode = startNode;
        goalNode = node[goalCol][goalRow];
        openList.add(currentNode);
        
        int col = 0;
        int row = 0;
        
        while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
            // SET SOLID NODES CHECKING TILES
            int tileNum = gp.tileM.mapTileNum[gp.currentMap][col][row];
            if(gp.tileM.tile[tileNum].collision == true) {
                node[col][row].solid = true;
            }
            // SET INTERACTIVE TILES
            for(int i = 0; i < gp.iTile[1].length; i++) {
                if(gp.iTile[gp.currentMap][i] != null && gp.iTile[gp.currentMap][i].destructible == true) {
                    int iCol = gp.iTile[gp.currentMap][i].worldX/gp.tileSize;
                    int iRow = gp.iTile[gp.currentMap][i].worldY/gp.tileSize;
                    node[iCol][iRow].solid = true;
                }
            }
            // SET COST
            getCost(node[col][row]);

            col++;
            if(col == gp.maxWorldCol) {
                col = 0;
                row++;
            }
        }
    }
    
    public void getCost(Node node) {
        // G COST (Distance from the start node)
        int xDistance = Math.abs(node.col - startNode.col);
        int yDistance = Math.abs(node.row - startNode.row);
        node.gCost = xDistance + yDistance;

        // H COST (Distance from the goal node)
        xDistance = Math.abs(node.col - goalNode.col);
        yDistance = Math.abs(node.row - goalNode.row);
        node.hCost = xDistance + yDistance;
        
        // F COST (Sum of G Cost and H Cost)
        node.fCost = node.gCost + node.hCost;
    }
    
    public boolean search() {

        while(goalReached == false && step < 500) {

            int col = currentNode.col;
            int row = currentNode.row;
            
            // Check the current node
            currentNode.checked = true;
            openList.remove(currentNode);
            
            // Open the UP node row - 1
            if(row - 1 >= 0) {
                openNode(node[col][row-1]);
            }
            // Open the LEFT node col - 1
            if(col - 1 >= 0) {
                openNode(node[col-1][row]);
            }
            // Open the DOWN node row + 1
            if(row + 1 < gp.maxWorldRow) {
                openNode(node[col][row+1]);
            }
            // Open the RIGHT node col + 1
            if(col + 1 < gp.maxWorldCol) {
                openNode(node[col+1][row]);
            }
        
            // FIND THE BEST NODE
            int bestNodeIndex = 0;
            int bestNodefCost = 999;
            
            for(int i = 0; i < openList.size(); i++) {
            
                // Check if this node's F cost is better
                if(openList.get(i).fCost < bestNodefCost) {
                    bestNodeIndex = i;
                    bestNodefCost = openList.get(i).fCost;
                }
                // If F cost is equal, check the G cost
                else if(openList.get(i).fCost == bestNodefCost) {
                     if(openList.get(i).gCost < openList.get(bestNodeIndex).gCost){
                        bestNodeIndex = i;
                    }
                }
            }        
            // if there is no node in the openList, end the loop
            if(openList.isEmpty()) { 
                break;
            }
            
            // After the loop, openList[bestNodeIndex] is the next step (= currentNode)
            currentNode = openList.get(bestNodeIndex);
            
            
            if(currentNode == goalNode) {
                goalReached = true;
                trackThePath();
            }
            step++;
        }
        return goalReached;
    }
    
    public void openNode(Node node) {
        
        if(node.open == false && node.checked == false && node.solid == false) {
            
            node.open = true;
            node.parent = currentNode;
            openList.add(node);
        }
    }
    
    public void trackThePath() {
        
        Node current = goalNode;
        
        while(current != startNode) {
            pathList.add(0,current);
            current = current.parent;
        }
    }
}
