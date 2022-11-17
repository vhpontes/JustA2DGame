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
        
        type = type_consumable;
        name = objName;
        description = "[" + name + "]\nA simple wood \narrow.";
        value = 1;
        speed = 8;
        maxLife = 100;
        life = maxLife;
        attack = 3;
        useCost = 1;
        alive = false;
        knockBackPower = 3;
        stackable = true;

        motion1_duration = 5;
        motion2_duration = 20;
        
        getImage();
    }
    
    public void getImage(){
        
        up1 =    setup("projectile/arrow02_up_1",    gp.tileSize, gp.tileSize);
        up2 =    setup("projectile/arrow02_up_2",    gp.tileSize, gp.tileSize);
        down1 =  setup("projectile/arrow02_down_1",  gp.tileSize, gp.tileSize);
        down2 =  setup("projectile/arrow02_down_2",  gp.tileSize, gp.tileSize);
        left1 =  setup("projectile/arrow02_left_1",  gp.tileSize, gp.tileSize);
        left2 =  setup("projectile/arrow02_left_2",  gp.tileSize, gp.tileSize);
        right1 = setup("projectile/arrow02_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("projectile/arrow02_right_2", gp.tileSize, gp.tileSize);
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
        for(var i = 0; i < gp.player.inventory.size(); i++) {
            if(gp.player.inventory.get(i).name == "Arrow") {
                gp.player.inventory.get(i).amount--;
                if(gp.player.inventory.get(i).amount <= 0) {
                    gp.player.inventory.remove(i);
                }
                break;
            }
        }
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
    
    public boolean use(Entity entity) {
        
        gp.playSE(2);
        gp.ui.addMessage("Arrow +" + value);
        gp.player.arrow += value;
        return true;
    }    
}