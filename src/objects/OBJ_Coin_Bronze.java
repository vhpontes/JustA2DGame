package objects;

import entity.Entity;
import main.GamePanel;

public class OBJ_Coin_Bronze extends Entity {
    
    GamePanel gp;
    
    public OBJ_Coin_Bronze(GamePanel gp) {
        super(gp);
        this.gp = gp;
        
        type = type_pickupOnly;
        name = "Bronze Coin";
        down1 = setup("objects/coin_bronze", gp.tileSize, gp.tileSize);
        value = 1;
        stackable = true;
    }
    
    public boolean use(Entity entity) {
        
        gp.playSE(1);
        gp.ui.addMessage("Coin +" + value);
        gp.player.coin += value;
        return true;
    }    
}