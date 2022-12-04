package main;

import entity.Player;
import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener {

    GamePanel gp;
    Player pl;
    
    public MouseHandler(GamePanel gp, Player pl) {
        this.gp = gp;
        this.pl = pl;
    }
    
    public void mouseClicked(MouseEvent e) { 
         int mouseX = (int) (MouseInfo.getPointerInfo().getLocation().getX() + pl.worldX - 27);
         int mouseY = (int) (MouseInfo.getPointerInfo().getLocation().getY() + pl.worldY - 12);
         //int mouseX = e.getPoint().x + pl.screenX;
         //int mouseY = e.getPoint().y + pl.screenY;
         mouseX = (mouseX / gp.tileSize) - 26;
         mouseY = (mouseY / gp.tileSize) - 12;
                 
         System.out.println(mouseX + "   " + mouseY);
    }
    
    @Override
    public void mouseEntered(MouseEvent arg0) {}

    @Override
    public void mouseExited(MouseEvent arg0) {}

    @Override
    public void mousePressed(MouseEvent arg0) {}

    @Override
    public void mouseReleased(MouseEvent arg0) {}       
}
