/*
Class code: Victor Hugo Manata Pontes 
https://www.twitch.tv/lechuck311
https://www.youtube.com/@victorhugomanatapontes
https://www.youtube.com/@dtudoumporco
*/


package environment;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.Random;
import main.GamePanel;

public class Rain {
    
    GamePanel gp;
    Color rainColor = new Color(0,200,255);
//    Color rainColor = new Color(240,40,120);
    static Random random = new Random();
    int x = 0;
    float y = 0;
    int h;
    float z = 0;
    float rainDropSpeed;
    float dropHSize;
    long environmentTimeStart, environmentTimeEnd;
    public boolean onState = false;
    
    static float map(float value, float in_min, float in_max, float out_min, float out_max) {
        
        return (value - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }    
    
    public Rain(GamePanel gp){
        x = random.nextInt(gp.screenWidth);
        y = random.nextInt(-(gp.screenHeight/2), 0);
        z = random.nextInt(0, 20);
        //rainDropSpeed = random.nextInt(3, 10);
        //dropHSize = random.nextInt(2, 10);
        rainDropSpeed = map(z, 0, 20, 3, 10);
        dropHSize = map(z, 0, 20, 2, 10);
        h = gp.screenHeight;
        
        environmentTimeEnd = System.currentTimeMillis() + (10 + new Random().nextInt(10)) * 1000;
    }
    
    public void update() {
        y = y + rainDropSpeed;
        rainDropSpeed = rainDropSpeed + 0.05f;
        
        if (y > h) {
            y = 0;
            rainDropSpeed = map(z, 0, 20, 3, 10);
        }
        
        if(System.currentTimeMillis() > environmentTimeEnd) {
            onState = false;
        }
        else {
            onState = true;
        }
    }
    
    public void draw(Graphics2D g2, GamePanel gp) {
        
        if(gp.currentArea == gp.outside) {
            float strokeSize = map(z, 0, 15, 1, 3);

            g2.setColor(rainColor);
            g2.setStroke(new BasicStroke(strokeSize));

            g2.draw(new Line2D.Float(x, y, x, y + dropHSize));
        }
    }
}
