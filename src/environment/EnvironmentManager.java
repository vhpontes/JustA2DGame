/*
Code based in RyiSnow youtube channel:
https://www.youtube.com/c/RyiSnow
*/
 
package environment;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import main.GamePanel;

public class EnvironmentManager {
    
    GamePanel gp;
    public Lighting lighting;
    public SnowFall snow;
    public Rain rain;
    Rain[] rainDrops = new Rain[1000];
    long timeToCheckEnvironment;
    int environmentRandomNumber = new Random().nextInt(100);
    String environmentState;
    BufferedImage puddleFilter;
    
    public EnvironmentManager(GamePanel gp) {
        this.gp = gp;
        timeToCheckEnvironment = System.currentTimeMillis();
    }
    
    public void setup() {
        lighting = new Lighting(gp);
        snow = new SnowFall(gp);
        
        for(int i = 0; i < rainDrops.length; i++) {

            rainDrops[i] = new Rain(gp);
        }
    }
    
    public void update() {
        lighting.update();
    }

    public static Rectangle2D[] rectangles = new Rectangle[]{
            new Rectangle(0, 0, 350, 50),
//            new Rectangle(0, 0, 50, 225),
//            new Rectangle(0, 175, 350, 50)
    };    

    public void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {

        FontMetrics metrics = g.getFontMetrics(font);
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        int y = rect.y + ((rect.height - metrics.getHeight())) + metrics.getAscent();
        g.setFont(font);
        g.drawString(text, x, y);
    }     
    
    public void draw(Graphics2D g2) throws IOException {
        
//        if(gp.currentArea == gp.outside || gp.currentArea == gp.dungeon) {
        if(gp.currentArea == gp.outside) {

            if((timeToCheckEnvironment+60000) < System.currentTimeMillis()) {

                environmentRandomNumber = new Random().nextInt(100);
                timeToCheckEnvironment = System.currentTimeMillis();
            }

            if (environmentRandomNumber < 5 
                    || snow.onState == true 
                    && rainDrops[1].onState == false) {

                    environmentState = "SnowFall";

                    snow.draw(g2);
            }
            if(environmentRandomNumber >= 5 
                    && environmentRandomNumber < 30 
                    || rainDrops[1].onState == true 
                    && snow.onState == false) {

                environmentState = "Rain";

                for(int i = 0; i < rainDrops.length; i++) {

                    rainDrops[i].draw(g2, gp);
                    rainDrops[i].update();
                }            
            }
            if(environmentRandomNumber >= 30 
                    && environmentRandomNumber < 100 ) {
                environmentState = "Clean";
            }
            
//            lighting.draw(g2);
            
            // Screen DEBUG
            String debugTXT1 = Integer.toString(environmentRandomNumber)+ "% sun probability";
            g2.setColor(Color.white);
            g2.setFont(new Font("Arial", Font.PLAIN, 20));

            g2.setColor(Color.black);
            drawCenteredString(g2, debugTXT1, new Rectangle((gp.screenWidth / 2) + 1, (gp.tileSize / 2) + 1, 0, 0) {} , g2.getFont().deriveFont(20f));
            g2.setColor(Color.white);
            drawCenteredString(g2, debugTXT1, new Rectangle(gp.screenWidth / 2, gp.tileSize / 2, 0, 0) {} , g2.getFont().deriveFont(20f));
            
            g2.setColor(Color.black);
            drawCenteredString(g2, environmentState, new Rectangle((gp.screenWidth / 2) + 2, (gp.tileSize * 2 + 2) + gp.tileSize / 4, 0, 0) {} , g2.getFont().deriveFont(30f));
            g2.setColor(Color.white);
            drawCenteredString(g2, environmentState, new Rectangle(gp.screenWidth / 2, (gp.tileSize * 2) + gp.tileSize / 4, 0, 0) {} , g2.getFont().deriveFont(30f));
        }
        
        lighting.draw(g2);
    }
}