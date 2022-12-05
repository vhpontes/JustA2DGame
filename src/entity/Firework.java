/*
Class code: Victor Hugo Manata Pontes 
https://www.twitch.tv/lechuck311
https://www.youtube.com/@victorhugomanatapontes
https://www.youtube.com/@dtudoumporco
*/

package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;
import main.GamePanel;

public class Firework extends Entity {
    
    Entity generator;
    Color color;
    int size;
    int xd;
    int yd;
    
    private Random r = new Random();
//    private static Color[] COLORS = new Color[] {Color.red, Color.green, Color.blue, Color.cyan, Color.magenta, Color.yellow};
    private static Color[] COLORS = new Color[] {Color.yellow};
    int width = 960;
    int height = 576;
    int groundHeight = height-(height/12);	
    double fwY = groundHeight;
    double fwX = width/2 + 1000;
    int rand = (int) ((groundHeight - fwY - 140) * r.nextGaussian());
    int colorIndex = 0; //Starting color index

    
    public Firework(GamePanel gp, Entity generator, int size, int speed, int maxLife, int xd, int yd, Color color) {
    //public Firework(GamePanel gp, int pX, int pY, int size, int speed, int maxLife, int xd, int yd, Color color) {
        super(gp);
        
        this.generator = generator;
        this.size = size;
        this.speed = speed;
        this.maxLife = maxLife;
        this.xd = xd;
        this.yd = yd;
        this.color = color;
        
        life = maxLife;
        int offset = (gp.tileSize/2) - (size/2); // to center particles start pos into tile
        worldX = generator.worldX + offset;
        worldY = generator.worldY + offset;
        //worldX = pX + offset;
        //worldY = pY + offset;
    }
    
    public void bloodDropAnim() {
         
        life--;
        
        if(life < maxLife/2) { // simulate gravity in particles
            yd++;
        }
        
        worldX += xd * speed;
        worldY += yd * speed;
        
        if(life == 0) {
            alive = false;
        }
        
        rand = (int) ((groundHeight - fwY - 140) * r.nextGaussian());
               
    }
    
    public void starAnim() {
        life--;
        
        worldX += xd * speed;
        worldY += yd * speed;
        
        if(life == 0) {
            alive = false;
        }
        
        rand = (int) ((groundHeight - fwY - 140) * r.nextGaussian());
    }
    
    public void update() {
        //bloodDropAnim();
        starAnim();
    }
    
    public void draw(Graphics2D g2) {
        
        //System.out.println("Firework!!! draw-> X:"+fwX/48+" Y:"+fwY/48);

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
//      
        //g2.setColor(COLORS[colorIndex]);
        g2.setColor(color);
        //g2.fillRect(screenX, screenY, size, size); // particle is a rectangle
        g2.fillRect((int)(screenX + 3*r.nextGaussian()), (int)(screenY + 3*r.nextGaussian()), size, size); // particle is a rectangle
        colorIndex++;
        if(colorIndex >= COLORS.length) colorIndex = 0;

        // "Radial"
//        g2.setColor(COLORS[colorIndex]);
//        g2.drawOval(screenX, screenY, rand, rand);
//        colorIndex++;
//        if(colorIndex >= COLORS.length) colorIndex = 0;

        // "Confetti"        
//        int radius = r.nextInt(20) - 10;
//        g2.setColor(COLORS[colorIndex]);
//        g2.fillOval((int)(screenX + 55*r.nextGaussian()), (int)(screenY + 55*r.nextGaussian()), radius, radius);
//        colorIndex++;
//        if(colorIndex >= COLORS.length) colorIndex = 0;
//
//        radius = r.nextInt(25) - 10;
//        g2.setColor(COLORS[colorIndex]);
//        g2.fillOval((int)(screenX + 65*r.nextGaussian()), (int)(screenY + 65*r.nextGaussian()), radius, radius);
//        colorIndex++;
//        if(colorIndex >= COLORS.length) colorIndex = 0;
//
//        radius = r.nextInt(30) - 10;
//        g2.setColor(COLORS[colorIndex]);
//        g2.fillOval((int)(screenX + 75*r.nextGaussian()), (int)(screenY + 75*r.nextGaussian()), radius, radius);
//        colorIndex++;
//        if(colorIndex >= COLORS.length) colorIndex = 0;
//
//        radius = r.nextInt(35) - 10;
//        g2.setColor(COLORS[colorIndex]);
//        g2.fillOval((int)(screenX + 85*r.nextGaussian()), (int)(screenY + 85*r.nextGaussian()), radius, radius);
//        colorIndex++;
//        if(colorIndex >= COLORS.length) colorIndex = 0;
        
//        for(int i = 1; i <= 100; i++) {
//            g2.setColor(COLORS[colorIndex]);
//            rand = (int) ((groundHeight - fwY - 140) * r.nextGaussian());
//            g2.drawOval((int)(fwX - (0.5*rand)), (int)(fwY - (0.5*rand)), rand, rand);
//        }
    }
}
