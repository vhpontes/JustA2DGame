/*
Class code: Victor Hugo Manata Pontes 
https://www.twitch.tv/lechuck311
https://www.youtube.com/@victorhugomanatapontes
https://www.youtube.com/@dtudoumporco
*/

package main;

import entity.Player;
import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener {

    GamePanel gp;
    Player pl;
    int mouseCol = 0;
    int mouseRow = 0;
    public boolean mouseLeftPressed;
            
    public MouseHandler(GamePanel gp, Player pl) {
        this.gp = gp;
        this.pl = pl;
    }
    
    public void mouseClicked(MouseEvent e) { 
        gp.player.onPath = false;

        if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
            mouseLeftPressed = true;
            pl.collisionOn = false;
            pl.onPath = true;
            pl.canMove = true;

            int screenX = pl.screenX / gp.tileSize;
            int screenY = pl.screenY / gp.tileSize;

            int camOffSetX = (int)(pl.worldX / gp.tileSize) - screenX;
            int camOffSetY = (int)(pl.worldY / gp.tileSize) - screenY;
            
            int mouseX = (int)(e.getPoint().x / gp.tileSize);
            int mouseY = (int)(e.getPoint().y / gp.tileSize);

            mouseCol = mouseX + camOffSetX;
            mouseRow = mouseY + camOffSetY;

//            System.out.println(e.getPoint().x + "   " + e.getPoint().y);
//            System.out.println(pl.worldX + "   " + pl.worldY);
//            System.out.println(mouseCol + "   " + mouseRow);
//            System.out.println("---------------");
//            System.out.println("mouseLeftPressed:"+mouseLeftPressed + "   onPath:" + pl.onPath);
//            System.out.println("collisionOn:"+pl.collisionOn);
//            System.out.println("---------------");
        }
        if (e.getButton() == MouseEvent.BUTTON2) {
            //gp.gameState = gp.characterState;
            System.out.println("------BUTTON2-------");
        }
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
            
            pl.searchPath(mouseCol, mouseRow, pl.worldX, pl.worldY);
        }
    }
}
