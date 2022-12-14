/*
Code based in RyiSnow youtube channel:
https://www.youtube.com/c/RyiSnow
*/
 
package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import main.GamePanel;

public class Particle extends Entity {
    
    Entity generator;
    Color color;
    int size;
    int xd;
    int yd;
    
    public Particle(GamePanel gp, Entity generator, Color color, int size, int speed, int maxLife, int xd, int yd) {
        super(gp);
        
        this.generator = generator;
        this.color = color;
        this.size = size;
        this.speed = speed;
        this.maxLife = maxLife;
        this.xd = xd;
        this.yd = yd;
        
        life = maxLife;
        int offset = (gp.tileSize/2) - (size/2); // to center particles start pos into tile
        worldX = generator.worldX + offset;
        worldY = generator.worldY + offset;
    }
    
    public void update() {
        
        life--;
        
        if(life < maxLife/2) { // simulate gravity in particles
            yd++;
        }
        
        worldX += xd * speed;
        worldY += yd * speed;
        
        if(life == 0) {
            alive = false;
        }
    }
    
    public void draw(Graphics2D g2) {

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        // Stop moving the camera at the edge
        if(gp.player.screenX > gp.player.worldX) {
            screenX = worldX;
        }
        if(gp.player.screenY > gp.player.worldY) {
            screenY = worldY;
        }
        int rightOffset = gp.screenWidth - gp.player.screenX;
        if(rightOffset > gp.worldWidth - gp.player.worldX) {
            screenX = gp.screenWidth - (gp.worldWidth - worldX);
        }
        int bottonOffset = gp.screenHeight - gp.player.screenY;
        if(bottonOffset > gp.worldHeight - gp.player.worldY) {
            screenY = gp.screenHeight - (gp.worldHeight - worldY);
        }
        
        g2.setColor(color);
        g2.fillRect(screenX, screenY, size, size); // particle is a rectangle
    }
}