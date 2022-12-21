/*
Class code: Victor Hugo Manata Pontes 
https://www.twitch.tv/lechuck311
https://www.youtube.com/@victorhugomanatapontes
https://www.youtube.com/@dtudoumporco
*/

package main;

import entity.Player;
import java.awt.Graphics2D;
import java.awt.Rectangle;
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
    public int searchMouseCol = 0;
    public int searchMouseRow = 0;
    
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

        // "CLEAR" output console
        for(int clear = 0; clear < 1000; clear++) {
            System.out.println("\b") ;
        } 
        
        int screenX = pl.screenX;
        int screenY = pl.screenY;

        int rightOffset = gp.screenWidth - pl.screenX;
        if(rightOffset > gp.worldWidth - pl.worldX) {
            screenX = gp.screenWidth - (gp.worldWidth - pl.worldX);
        }
        int bottonOffset = gp.screenHeight - pl.screenY;
        if(bottonOffset > gp.worldHeight - pl.worldY) {
            screenY = gp.screenHeight - (gp.worldHeight - pl.worldY);
        }        
        
        int worldX = pl.worldX;
        int worldY = pl.worldY;
        
        screenCol = (double)floor(screenX / gp.tileSize);
        screenRow = (double)floor(screenY / gp.tileSize);

        worldCol = (double)floor(worldX / gp.tileSize);
        worldRow = (double)floor(worldY / gp.tileSize);
        
        int mouseX = e.getPoint().x;
        int mouseY = e.getPoint().y;
            
        mouseCol = (double)floor(mouseX / gp.tileSize);
        mouseRow = (double)floor(mouseY / gp.tileSize);

        camOffSetX = worldX - mouseX;
        camOffSetY = worldY - mouseY;

        camOffSetCol = worldCol - screenCol;
        camOffSetRow = worldRow - screenRow;
        
        mouseOffCol = (int)(mouseCol + camOffSetCol);
        mouseOffRow = (int)(mouseRow + camOffSetRow);
        
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
        
        // Check Tiles arround to get correct mouse clicked tile
        Rectangle bounds = new Rectangle();
        int tempTileX = 0;
        int tempTileY = 0;
        int mouseWorldX = (gp.player.worldX - gp.player.screenX) + mouseX;
        int mouseWorldY = (gp.player.worldY - gp.player.screenY) + mouseY;
        
        // IF Camera at the edge
        if(gp.player.screenX > gp.player.worldX) {
            mouseWorldX = mouseX;
        }
        if(gp.player.screenY > gp.player.worldY) {
            mouseWorldY = mouseY;
        }
        rightOffset = gp.screenWidth - gp.player.screenX;
        if(rightOffset > gp.worldWidth - gp.player.worldX) {
            mouseWorldX = (gp.worldWidth - (gp.player.screenX * 2)) + mouseX - gp.tileSize;
        }        
        bottonOffset = gp.screenHeight - gp.player.screenY;
        if(bottonOffset > gp.worldHeight - gp.player.worldY) {
            mouseWorldY = (gp.worldHeight - (gp.player.screenY * 2)) + mouseY - gp.tileSize;
        }        
        
        for(int col = -1; col < 2; col++){
            for(int row = -1; row < 2; row++){
                int tempMouseCol = mouseTileCol + col; 
                int tempMouseRow = mouseTileRow + row;

                // PEGA x, y iniciais dos tiles
                tempTileX = gp.tileM.tileInfo[tempMouseCol][tempMouseRow].tileX;
                tempTileY = gp.tileM.tileInfo[tempMouseCol][tempMouseRow].tileY;

                bounds.setBounds(tempTileX, tempTileY, gp.tileSize, gp.tileSize);
                
                if(bounds.intersects(mouseWorldX, mouseWorldY, 1, 1)){
                    searchMouseCol = tempMouseCol;
                    searchMouseRow = tempMouseRow;
                }
            }
        }

        tileOffsetCol = (int) (floor(screenX - (mouseTileCol * gp.tileSize)) / gp.tileSize);
        tileOffsetRow = (int) (floor(screenY - (mouseTileRow * gp.tileSize)) / gp.tileSize);
        
        clickedX = e.getX();
        clickedY = e.getY();

        // Search Path to mouse click position
        if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
            mouseLeftPressed = true;
            pl.collisionOn = false;
            pl.onPath = true;
            pl.canMove = true;
        }
        // Show DEBUG Window
        if (e.getButton() == MouseEvent.BUTTON2) {
            
            if(showDebugText == false){
                showDebugText = true;
            }
            else if(showDebugText == true){
                showDebugText = false;
            }
        }
        // Show Character State
        if (e.getButton() == MouseEvent.BUTTON3) {
            if(gp.gameState == gp.playState) {
                gp.gameState = gp.characterState;
            }
            else if(gp.gameState == gp.characterState) {
                gp.gameState = gp.playState;
            }
        }
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
//            pl.searchPath((int)mouseTileCol, (int)mouseTileRow, pl.worldX, pl.worldY);
            pl.searchPath(searchMouseCol, searchMouseRow, pl.worldX, pl.worldY);
        }
    }
    
}
