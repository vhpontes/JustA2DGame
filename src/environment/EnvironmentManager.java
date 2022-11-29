package environment;

import java.awt.Graphics2D;
import main.GamePanel;

public class EnvironmentManager {
    
    GamePanel gp;
    public Lighting lighting;
    public SnowFall snow;
    
    public EnvironmentManager(GamePanel gp) {
        this.gp = gp;
    }
    
    public void setup() {
        lighting = new Lighting(gp);
        snow = new SnowFall(gp);
    }
    
    public void update() {
        lighting.update();
    }
    
    public void draw(Graphics2D g2) {
//        if(gp.currentMap!=2) {
            snow.draw(g2);
            lighting.draw(g2);
//        }
    }
}
