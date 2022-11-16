package objects;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

public class OBJ_Bow_Normal extends Entity{

    public static final String objName = "Normal Bow";
       
    public OBJ_Bow_Normal(GamePanel gp) {
        super(gp);
        
        type = type_bow;
        name = objName;
        handObject = true;
        down1 = setup("objects/bow_normal", gp.tileSize, gp.tileSize);
        attackValue = 1;
        attackArea.width = 36;
        attackArea.height = 36;
        description = "[" + name + "]\nAn wooden bow.";
        price = 50;
        knockBackPower = 1;
        
        motion1_duration = 5;
        motion2_duration = 20;
    }

    public void setProjectile(Projectile projectileWeapow) {
        
        this.projectileWeapow = projectileWeapow;
    }
    
    public boolean use(Entity entity) {
        
        //gp.playSE(23);
        gp.ui.addMessage("Arrow +" + value);
        gp.player.arrow += value;
        return true;
    }      
}
