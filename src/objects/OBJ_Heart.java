package objects;

import entity.Entity;
import main.GamePanel;

public class OBJ_Heart extends Entity {
    
    GamePanel gp;
    
    public OBJ_Heart(GamePanel gp) {
        super(gp);
        this.gp = gp;
        
        type = type_pickupOnly;
        name = "Heart";
        value = 2;
        down1 = setup("objects/heart_full", gp.tileSize, gp.tileSize);
        
        image = setup("objects/heart_full", gp.tileSize, gp.tileSize);
        image2 = setup("objects/heart_half", gp.tileSize, gp.tileSize);
        image3 = setup("objects/heart_blank", gp.tileSize, gp.tileSize);
    }    
    
    public void use(Entity entity) {
        
        gp.playSE(2);
        gp.ui.addMessage("Life +" + value);
        gp.player.life += value;
    }   
}
