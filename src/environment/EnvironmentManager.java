package environment;

import java.awt.Graphics2D;
import main.GamePanel;

public class EnvironmentManager {
    
    GamePanel gp;
    public Lighting lighting;
    public SnowFall snow;
    public Rain rain;
    Rain[] rainDrops = new Rain[1000];
    
    public EnvironmentManager(GamePanel gp) {
        this.gp = gp;
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
    
    public void draw(Graphics2D g2) {
//        if(gp.currentMap!=2) {
//          snow.draw(g2);
        for(int i = 0; i < rainDrops.length; i++) {

            rainDrops[i].draw(g2, gp);
            rainDrops[i].update();
        }            
        lighting.draw(g2);
//        }
    }
}
