package environment;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.UI;

public class EnvironmentManager {
    
    GamePanel gp;
    public Lighting lighting;
    public SnowFall snow;
    public Rain rain;
    Rain[] rainDrops = new Rain[1000];
    long timeToCheckEnvironment;
    int environmentRandomNumber = new Random().nextInt(100);
    String environmentState;
    
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
            new Rectangle(0, 0, 50, 225),
            new Rectangle(0, 175, 350, 50)
    };    
    
    public void draw(Graphics2D g2) throws IOException {
        
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

        lighting.draw(g2);
        
        BufferedImage imageMask = ImageIO.read(new File("mask_puddle.jpg"));
        for (Rectangle2D rect : rectangles){
            int x = (int) rect.getX();
            int y = (int) rect.getY();
            int width = (int) rect.getWidth();
            int height = (int) rect.getHeight();
            
            TexturePaint paint = new TexturePaint(imageMask, rect);
            g2.setPaint(paint);
            g2.fill(rect);
        }
        g2.drawImage(imageMask, null, 400, 0);

        // Screen DEBUG
        String debugTXT1 = Integer.toString(environmentRandomNumber);
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(30f));

        int length = (int)g2.getFontMetrics().getStringBounds(debugTXT1, g2).getWidth();
        int x = gp.screenWidth/2 - length/2;
        g2.drawString(debugTXT1 + "% sun probability" , x-gp.tileSize, 50);              

        
        g2.setColor(Color.black);
        g2.setFont(g2.getFont().deriveFont(30f));
        g2.drawString(environmentState, (gp.screenWidth/2)+2, 82);  

        g2.setColor(Color.cyan);
        g2.setFont(g2.getFont().deriveFont(30f));
        g2.drawString(environmentState, gp.screenWidth/2, 80);  
    }
}