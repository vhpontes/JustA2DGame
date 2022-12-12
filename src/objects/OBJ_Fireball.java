/*
Code based in RyiSnow youtube channel:
https://www.youtube.com/c/RyiSnow
*/
 
package objects;

import entity.Entity;
import entity.Projectile;
import java.awt.Color;
import main.GamePanel;

public class OBJ_Fireball extends Projectile{
    
    GamePanel gp;
    public static final String objName = "Fireball";
        
    public OBJ_Fireball(GamePanel gp, String sufix){
        super(gp);
        this.gp = gp;
        
        name = objName;
        speed = 5;
        maxLife = 80;
        life = maxLife;
        attack = 1;
        useCost = 1;
        alive = false;
        knockBackPower = 5;
        
        getImage(sufix);
    }
    
    public void getImage(String sufix){
        
        up1 = setup("projectile/"+sufix+"_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("projectile/"+sufix+"_up_2", gp.tileSize, gp.tileSize);
        down1 = setup("projectile/"+sufix+"_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("projectile/"+sufix+"_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("projectile/"+sufix+"_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("projectile/"+sufix+"_left_2", gp.tileSize, gp.tileSize);
        right1 = setup("projectile/"+sufix+"_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("projectile/"+sufix+"_right_2", gp.tileSize, gp.tileSize);
    }
    
    public boolean haveResource(Entity user) {
        
        boolean haveResource = false;
        if(user.mana >= useCost) {
            haveResource = true;
        }
        return haveResource;
    }
    
    public void subtractResource(Entity user) {
        user.mana -= useCost;
    }
    
    public Color getParticleColor() {
        Color color = new Color(240, 50, 0);
        return color;
    }
    
    public int getParticleSize() {
        int size = 10; // pixels
        return size;
    }
    
    public int getParticleSpeed() {
        int speed = 1;
        return speed;
    }
    
    public int getMaxLife() {
        int maxLife = 20; // frames count of particle
        return maxLife;
    }    
}