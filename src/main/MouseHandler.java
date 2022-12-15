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
    int mouseCol = 0;
    int mouseRow = 0;
    double mouseX = 0;
    double mouseY = 0;
    double camOffSetX = 0;
    double camOffSetY = 0;
    public int clickedX = 0;
    public int clickedY = 0;
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

        double screenX = (double)floor(pl.screenX / gp.tileSize);
        double screenY = (double)floor(pl.screenY / gp.tileSize);

        mouseX = (double)floor(e.getPoint().x / gp.tileSize);
        mouseY = (double)floor(e.getPoint().y / gp.tileSize);
        System.out.println("floor mouseX: " + mouseX);
        System.out.println("floor mouseY: " + mouseY);

        camOffSetX = (double)floor(pl.worldX / gp.tileSize) - screenX;
        camOffSetY = (double)floor(pl.worldY / gp.tileSize) - screenY;
        System.out.println("camOffSetX: " + camOffSetX);
        System.out.println("camOffSetY: " + camOffSetY);

        clickedX = e.getX();
        clickedY = e.getY();
//        clickedX = e.getPoint().x + (pl.worldX - pl.screenX);
//        clickedY = e.getPoint().y + (pl.worldY - pl.screenY);

        mouseCol = (int) (mouseX + camOffSetX);
        mouseRow = (int) (mouseY + camOffSetY);

        if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
            mouseLeftPressed = true;
            pl.collisionOn = false;
            pl.onPath = true;
            pl.canMove = true;
            
            System.out.println(e.getPoint().x + "   " + e.getPoint().y);
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
        
        if(gp.tileM.inBounds(e.getPoint().x, e.getPoint().y, g2)){
            System.out.println("INTERSECT");
        }
        else {
            System.out.println("NO INTERSECT");
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
