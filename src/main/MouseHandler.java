/*
Class code: Victor Hugo Manata Pontes 
https://www.twitch.tv/lechuck311
https://www.youtube.com/@victorhugomanatapontes
https://www.youtube.com/@dtudoumporco
*/

package main;

import entity.Player;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import static java.lang.Math.floor;

public class MouseHandler implements MouseListener {

    GamePanel gp;
    Player pl;
    double worldCol = 0;
    double worldRow = 0;
    double screenCol = 0;
    double screenRow = 0;
    double mouseCol = 0;
    double mouseRow = 0;
    int camOffSetX = 0;
    int camOffSetY = 0;
    double camOffSetCol = 0;
    double camOffSetRow = 0;
    int mouseOffCol = 0;
    int mouseOffRow = 0;
    int mouseTileCol = 0;
    int mouseTileRow = 0;
    public int clickedX = 0;
    public int clickedY = 0;
    int tileOffsetCol = 0;
    int tileOffsetRow = 0;
    
    public boolean mouseLeftPressed;
    private final Graphics2D g2;
    boolean showDebugText = false;
            
    public MouseHandler(GamePanel gp, Player pl, Graphics2D g2) {
        this.gp = gp;
        this.pl = pl;
        this.g2 = g2;
    }
    
    public void mouseClicked(MouseEvent e) { 
        gp.player.onPath = false;

        // "Clear" output console
        for(int clear = 0; clear < 1000; clear++) {
            System.out.println("\b") ;
        } 
        
        System.out.println(" -- mouseClicked(MouseEvent e) --");
        
        int screenX = pl.screenX;
        int screenY = pl.screenY;
        System.out.println("Screen XY: " + screenX + ", " + screenY);
        
        int worldX = pl.worldX;
        int worldY = pl.worldY;
        System.out.println("World XY: " + worldX + ", " + worldY);
        
        screenCol = (double)floor(screenX / gp.tileSize);
        screenRow = (double)floor(screenY / gp.tileSize);
        System.out.println("Screen Tile: [" + (int)screenCol + " " + (int)screenRow + "]");

        worldCol = (double)floor(worldX / gp.tileSize);
        worldRow = (double)floor(worldY / gp.tileSize);
        System.out.println("World Tile: [" + (int)worldCol + " " + (int)worldRow + "]");
        
        int mouseX = e.getPoint().x;
        int mouseY = e.getPoint().y;
        System.out.println("Mouse XY: " + mouseX + ", " + mouseY);
            
        mouseCol = (double)floor(mouseX / gp.tileSize);
        mouseRow = (double)floor(mouseY / gp.tileSize);
        System.out.println("Mouse Tile: [" + (int)mouseCol + " " + (int)mouseRow + "]");

//        camOffSetX = worldX - screenX;
//        camOffSetY = worldY - screenY;
        camOffSetX = worldX - mouseX;
        camOffSetY = worldY - mouseY;
        System.out.println("camOffSet XY: " + camOffSetX + ", " + camOffSetY);

        camOffSetCol = worldCol - screenCol;
        camOffSetRow = worldRow - screenRow;
        System.out.println("camOffSet Tile: [" + (int)camOffSetCol + " " + (int)camOffSetRow + "]");
        
        mouseOffCol = (int)(mouseCol + camOffSetCol);
        mouseOffRow = (int)(mouseRow + camOffSetRow);
        System.out.println("Mouse Off Tile: [" + mouseOffCol + " " + mouseOffRow + "]");
        
        if(worldX > screenX) { 
            mouseTileCol = (int)mouseOffCol; 
        }
        else { 
            mouseTileCol = (int)mouseCol; 
        }
        if(worldY > screenY) { 
            mouseTileRow = (int)mouseOffRow; 
        }
        else { 
            mouseTileRow = (int)mouseRow; 
        }
        System.out.println("\b") ;
        System.out.println("-> Mouse Tile: [" + mouseTileCol + " " + mouseTileRow + "]");
        
//        int tileOffsetX = screenX - (mouseTileCol * gp.tileSize) + 8;
//        int tileOffsetY = screenY - (mouseTileRow * gp.tileSize) - 8;
        int tileOffsetX = mouseX - screenX - 8;
        int tileOffsetY = mouseY - screenY + 8;
        System.out.println("-> Tile Off: " + tileOffsetX + "," + tileOffsetY);

        tileOffsetCol = (int) (floor(screenX - (mouseTileCol * gp.tileSize)) / gp.tileSize);
        tileOffsetRow = (int) (floor(screenY - (mouseTileRow * gp.tileSize)) / gp.tileSize);
        System.out.println("-> Tile Off Tile: [" + tileOffsetCol + " " + tileOffsetRow + "]");
        

        clickedX = e.getX();
        clickedY = e.getY();
//        clickedX = e.getPoint().x + (pl.worldX - pl.screenX);
//        clickedY = e.getPoint().y + (pl.worldY - pl.screenY);

        //mouseCol = (int) (mouseX + camOffSetX);
        //mouseRow = (int) (mouseY + camOffSetY);

        if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
            mouseLeftPressed = true;
            pl.collisionOn = false;
            pl.onPath = true;
            pl.canMove = true;
            
//            System.out.println(e.getPoint().x + "   " + e.getPoint().y);
//            System.out.println(pl.worldX + "   " + pl.worldY);
//            System.out.println(mouseCol + "   " + mouseRow);
//            System.out.println("---------------");
//            System.out.println("mouseLeftPressed:"+mouseLeftPressed + "   onPath:" + pl.onPath);
//            System.out.println("collisionOn:"+pl.collisionOn);
//            System.out.println("---------------");
        }
        if (e.getButton() == MouseEvent.BUTTON2) {
            // Show DEBUG Window
            if(showDebugText == false){
                showDebugText = true;
            }
//            else if(showDebugText == true){
//                showDebugText = false;
//            }

            //gp.gameState = gp.characterState;
            //System.out.println("------BUTTON2-------");
        }
        if (e.getButton() == MouseEvent.BUTTON3) {
            if(gp.gameState == gp.playState) {
                gp.gameState = gp.characterState;
            }
            else if(gp.gameState == gp.characterState) {
                gp.gameState = gp.playState;
            }
        }
        
//        if(gp.tileM.inBounds(e.getPoint().x, e.getPoint().y, g2)){
//            System.out.println("INTERSECT");
//        }
//        else {
//            System.out.println("NO INTERSECT");
//        }
        
    }
    
    @Override
    public void mouseEntered(MouseEvent arg0) {}

    @Override
    public void mouseExited(MouseEvent arg0) {}

    @Override
    public void mousePressed(MouseEvent arg0) {}

    @Override
    public void mouseReleased(MouseEvent arg0) {}      
    
    public void setAction() {
        if(pl.onPath == true){
            
            //pl.searchPath((int)mouseCol, (int)mouseRow, pl.worldX, pl.worldY);
            pl.searchPath((int)mouseTileCol, (int)mouseTileRow, pl.worldX, pl.worldY);
        }
    }
    
}
