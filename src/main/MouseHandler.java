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
        mouseLeftPressed = true;
        pl.onPath = true;

        //int mouseX = (int) (MouseInfo.getPointerInfo().getLocation().getX() + pl.worldX - 27);
        //int mouseY = (int) (MouseInfo.getPointerInfo().getLocation().getY() + pl.worldY - 12);
        int camOffSetX = (int)(pl.worldX / gp.tileSize) - 7;
        int camOffSetY = (int)(pl.worldY / gp.tileSize) - 4;

        //camOffSetX = camOffSetX * gp.tileSize;

        int mouseX = (int)(e.getPoint().x / gp.tileSize);//+ pl.worldX;
        int mouseY = (int)(e.getPoint().y / gp.tileSize);//+ pl.worldY;

        mouseCol = mouseX + camOffSetX;
        mouseRow = mouseY + camOffSetY;

        System.out.println(e.getPoint().x + "   " + e.getPoint().y);
        System.out.println(pl.worldX + "   " + pl.worldY);
        System.out.println(mouseCol + "   " + mouseRow);
        System.out.println("---------------");
        System.out.println("mouseLeftPressed:"+mouseLeftPressed + "   onPath:" + pl.onPath);
        System.out.println("collisionOn:"+pl.collisionOn);
        System.out.println("---------------");

        
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
            pl.searchPath(mouseCol, mouseRow);
        }
    }
}
