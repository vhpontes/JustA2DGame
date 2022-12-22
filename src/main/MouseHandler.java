/*
Class code: Victor Hugo Manata Pontes 
https://www.twitch.tv/lechuck311
https://www.youtube.com/@victorhugomanatapontes
https://www.youtube.com/@dtudoumporco
*/

package main;

import entity.Player;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener {

    GamePanel gp;
    Player pl;
    
    public int clickedX;
    public int clickedY;
    public int searchMouseCol = 0;
    public int searchMouseRow = 0;
    
    public boolean mouseLeftPressed;
    boolean showDebugText = false;
            
    public MouseHandler(GamePanel gp, Player pl) {
        this.gp = gp;
        this.pl = pl;
    }

    @Override
    public void mouseClicked(MouseEvent e) { 
        
        gp.player.onPath = false;

        // "CLEAR" output console
        for(int clear = 0; clear < 1000; clear++) {
            System.out.println("\b") ;
        } 
        
        clickedX = e.getPoint().x;
        clickedY = e.getPoint().y;
        
        int resultTilesColRow[] = gp.tileM.getTileXY(e, clickedX, clickedY);
        searchMouseCol = resultTilesColRow[0];
        searchMouseRow = resultTilesColRow[1];


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

            pl.searchPath(searchMouseCol, searchMouseRow, pl.worldX, pl.worldY);
        }
    }
    
}
