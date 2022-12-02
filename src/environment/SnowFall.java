package environment;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;
import main.GamePanel;

public class SnowFall {

    GamePanel gp;
    SnowFlake[] farFlakes = new SnowFlake[200];
    SnowFlake[] midFlakes = new SnowFlake[150];
    SnowFlake[] closeFlakes = new SnowFlake[75];
    Color farColor = new Color(100,100,255);
    Color midColor = new Color(150,150,255);
    Color closeColor = new Color(255,255,255);
    int snowFlakeSize = 2;
    int snowFlakeSpeed = 1;
    long environmentTimeStart, environmentTimeEnd;
    public boolean onState = false;    

    public SnowFall(GamePanel gp) {
        this.gp = gp;

        for (int ii = 0; ii < farFlakes.length; ii++) {
            farFlakes[ii] = new SnowFlake(gp.screenHeight-((gp.screenHeight/3)*2), snowFlakeSize, snowFlakeSpeed, gp);
        }
        for (int ii = 0; ii < midFlakes.length; ii++) {
            midFlakes[ii] = new SnowFlake(gp.screenHeight-(gp.screenHeight/3), snowFlakeSize+1, snowFlakeSpeed+1, gp);
        }
        for (int ii = 0; ii < closeFlakes.length; ii++) {
            closeFlakes[ii] = new SnowFlake(gp.screenHeight-200, snowFlakeSize+2, snowFlakeSpeed+2, gp);
        }   

        environmentTimeEnd = System.currentTimeMillis() + (10 + new Random().nextInt(10)) * 1000;        
    }
    
    public void draw(Graphics2D g2) {

        if(gp.currentArea == gp.outside) {
            g2.setColor(farColor);
            for (SnowFlake snowFlake : farFlakes) {
                snowFlake.draw(g2);
            }

            g2.setColor(midColor);
            for (SnowFlake snowFlake : midFlakes) {
                snowFlake.draw(g2);
            }

            g2.setColor(closeColor);
            for (SnowFlake snowFlake : closeFlakes) {
                snowFlake.draw(g2);
            }
        }

        if(System.currentTimeMillis() > environmentTimeEnd) {
            onState = false;
        }
        else {
            onState = true;
        }
        
        // DEBUG SNOW
        String situation = "SnowFall";
        g2.setColor(Color.cyan);
        g2.setFont(g2.getFont().deriveFont(30f));
        g2.drawString(situation, gp.screenWidth/2, 80);        
    }
}