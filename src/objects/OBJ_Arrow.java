package objects;

import entity.Entity;
import entity.Projectile;
import java.awt.Color;
import main.GamePanel;

public class OBJ_Arrow extends Projectile{
    
    GamePanel gp;
    public static final String objName = "Arrow";
    
    /* IDEAS TO-DO
     - wldomiciano: Quando vc atira uma flecha contra uma parede próxima, há uma
            chance de ela ficar cravada na parede. Se ficar cravada, 
            vc pode ir e coletá-la novamente.
    */
        
    public OBJ_Arrow(GamePanel gp){
        super(gp);
        this.gp = gp;
        
        name = objName;
        description = "[" + name + "]\nA simple wood \narrow.";
        speed = 8;
        maxLife = 100;
        life = maxLife;
        attack = 3;
        useCost = 1;
        alive = false;
        knockBackPower = 3;
        stackable = true;
        
        getImage();
    }
    
    public void getImage(){
        
        up1 =    setup("projectile/arrow_up_1",    gp.tileSize, gp.tileSize);
        up2 =    setup("projectile/arrow_up_2",    gp.tileSize, gp.tileSize);
        down1 =  setup("projectile/arrow_down_1",  gp.tileSize, gp.tileSize);
        down2 =  setup("projectile/arrow_down_2",  gp.tileSize, gp.tileSize);
        left1 =  setup("projectile/arrow_left_1",  gp.tileSize, gp.tileSize);
        left2 =  setup("projectile/arrow_left_2",  gp.tileSize, gp.tileSize);
        right1 = setup("projectile/arrow_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("projectile/arrow_right_2", gp.tileSize, gp.tileSize);
    }
    
    public boolean haveResource(Entity user) {
        
        boolean haveResource = false;
        if(user.arrow >= useCost) {
            haveResource = true;
        }
        return haveResource;
    }
    
    public void subtractResource(Entity user) {
        user.arrow -= useCost;
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