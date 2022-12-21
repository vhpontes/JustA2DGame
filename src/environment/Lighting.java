/*
Code based in RyiSnow youtube channel:
https://www.youtube.com/c/RyiSnow
*/

package environment;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import main.GamePanel;

public final class Lighting{
    
    GamePanel gp;
    BufferedImage darknessFilter;
    public int dayCounter;
    public float filterAlpha = 0f;
    public static int oneMinuteMills = 3600;
    
    public final int day = 0;
    public final int dusk = 1;
    public final int night = 2;
    public final int dawn = 3;
    public int dayState = day;
    public int dayTimeMinutes = 5 * oneMinuteMills;
    public Thread lUpdate;

    
    public Lighting(GamePanel gp) {
        this.gp = gp;
            
        setLightSource();
    }
    
    public void setLightSource() {
        
        darknessFilter = new BufferedImage(gp.screenWidth, gp.screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D)darknessFilter.getGraphics();
        
        if(gp.player.currentLight == null) {
            
            g2.setColor(new Color(0,0,0.1f, 0.96f));
        }
        else {
            
            // update values of light source in screen
            int centerLightX = gp.player.screenX + (gp.tileSize) / 2;
            int centerLightY = gp.player.screenY + (gp.tileSize) / 2;
            int worldX = gp.player.worldX;
            int worldY = gp.player.worldY;
            if(gp.player.screenX > gp.player.worldX) {
                centerLightX = worldX;
                centerLightX = gp.player.screenX;
                System.out.println("worldX:"+worldX);
                System.out.println("gp.player.screenX:"+gp.player.screenX);
            }
            if(gp.player.screenY > gp.player.worldY) {
                centerLightY = worldY;
            }
            int rightOffset = gp.screenWidth - gp.player.screenX;
            if(rightOffset > gp.worldWidth - gp.player.worldX) {
                centerLightX = gp.screenWidth - (gp.worldWidth - worldX);
            }
            int bottonOffset = gp.screenHeight - gp.player.screenY;
            if(bottonOffset > gp.worldHeight - gp.player.worldY) {
                centerLightY = gp.screenHeight - (gp.worldHeight - worldY);
            }

            Color color[] = new Color[12];
            float fraction[] = new float[12];
            color[0] = new Color(0, 0, 0.1f, 0.1f);
            color[1] = new Color(0, 0, 0.1f, 0.42f);
            color[2] = new Color(0, 0, 0.1f, 0.52f);
            color[3] = new Color(0, 0, 0.1f, 0.61f);
            color[4] = new Color(0, 0, 0.1f, 0.69f);
            color[5] = new Color(0, 0, 0.1f, 0.76f);
            color[6] = new Color(0, 0, 0.1f, 0.82f);
            color[7] = new Color(0, 0, 0.1f, 0.87f);
            color[8] = new Color(0, 0, 0.1f, 0.91f);
            color[9] = new Color(0, 0, 0.1f, 0.92f);
            color[10] = new Color(0, 0, 0.1f, 0.93f);
            color[11] = new Color(0, 0, 0.1f, 0.94f);

            fraction[0] = 0f;
            fraction[1] = 0.40f;
            fraction[2] = 0.50f;
            fraction[3] = 0.60f;
            fraction[4] = 0.65f;
            fraction[5] = 0.7f;
            fraction[6] = 0.75f;
            fraction[7] = 0.80f;
            fraction[8] = 0.85f;
            fraction[9] = 0.90f;
            fraction[10] = 0.95f;
            fraction[11] = 1f;

            RadialGradientPaint gPaint = new RadialGradientPaint(centerLightX, centerLightY, gp.player.currentLight.lightRadius, fraction, color);

            g2.setPaint(gPaint);
        }
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        g2.dispose();
    }
    
    public void resetDay() {
        
        dayState = day;
        filterAlpha = 0f;
    }

    public void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {

        FontMetrics metrics = g.getFontMetrics(font);
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        int y = rect.y + ((rect.height - metrics.getHeight())) + metrics.getAscent();
        g.setFont(font);
        g.drawString(text, x, y);
    }     
    
    public void update() {
        
        if(gp.player.lightUpdated == true) {
            setLightSource();
            gp.player.lightUpdated = false;
        }
        
        if(dayState == day) {
            dayCounter++;
            
            if(dayCounter > dayTimeMinutes) { // 1min = 3600, 10min = 36000
                dayState = dusk;
                dayCounter = 0;
            }
        }
        if(dayState == dusk) {
            
            filterAlpha += 0.001f; // 0.0001f x 10000 = 1f -> 10000/60 = 166 seconds
            
            if(filterAlpha > 1f) {
                filterAlpha = 1f;
                dayState = night;
            }
        }
        if(dayState == night) {
            
            dayCounter++;
            
            if(dayCounter > 3600) {
                dayState = dawn;
                dayCounter = 0;
            }
        }
        if(dayState == dawn) {
            
            filterAlpha -= 0.001f;
            
            if(filterAlpha < 0f) {
                filterAlpha = 0;
                dayState = day;
            }
        }
    }
    
    public void draw(Graphics2D g2) {
        
        if(gp.currentArea == gp.outside) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, filterAlpha));
        }
        if(gp.currentArea == gp.outside || gp.currentArea == gp.dungeon) {
            g2.drawImage(darknessFilter, 0, 0, null);
        }
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        // DEBUG DAY CICLE
        String situation = "";
        switch(dayState){
            case day -> situation = "Day";
            case dusk -> situation = "Sunset";
            case night -> situation = "Night";
            case dawn -> situation = "Dawn";
        }
        
        g2.setFont(new Font("Arial", Font.PLAIN, 35));
        g2.setColor(Color.black);
        drawCenteredString(g2, situation, new Rectangle((gp.screenWidth / 2) + 2, (gp.tileSize / 2 * 3) + 2, 0, 0) {} , g2.getFont().deriveFont(40f));
        g2.setColor(Color.yellow);
        drawCenteredString(g2, situation, new Rectangle(gp.screenWidth / 2, gp.tileSize / 2 * 3, 0, 0) {} , g2.getFont().deriveFont(40f));
    }        
}
