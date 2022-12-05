/*
Class code: Victor Hugo Manata Pontes 
https://www.twitch.tv/lechuck311
https://www.youtube.com/@victorhugomanatapontes
https://www.youtube.com/@dtudoumporco
*/

package environment;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.util.Random;
import main.GamePanel;

public class SnowFlake {

    int w;
    int h;
    int x;
    double x1, z, o;
    int y;
    int size;
    int speed;
    static Random r = new Random();
    static int count=0;
    Random angle = new Random((long)(Math.PI*2));

    SnowFlake(int h, int size, int speed, GamePanel gp) {
        this.w = gp.screenWidth;
        this.h = h;
        x = r.nextInt(w);
        y = r.nextInt(h);
        this.size = size;
        this.speed = speed;
        //this.width = gp.screenWidth;

        count++;

        this.x = 0;
        this.x1 = Math.random() * w;
        this.z = Math.random() * 0.8 + 0.2;
        this.o = Math.random() * Math.PI;
    }
    
    public void reset(){
        this.x = 0;
        this.x1 = Math.random() * w;
        this.y = -5;
        this.z = Math.random() * 0.8 + 0.2;
        this.o = Math.random() * Math.PI;
    }
    
    public void draw(Graphics g) {
        
        AffineTransform at = new AffineTransform();
       
        y += speed;
        if (y > h) {
            x = r.nextInt(w);
            y = 0;
            reset();
        }
        
        int xOff = (int) (Math.cos(this.o + this.y * (1 - this.z) * 0.05) * this.z * 20 + this.x1);
        at.translate(x + xOff, y);

        g.fillOval((int) at.getTranslateX(), y, size, size);
    }
}