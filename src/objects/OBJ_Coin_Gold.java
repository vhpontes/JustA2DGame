package objects;

import entity.Entity;
import main.GamePanel;

public class OBJ_Coin_Gold extends Entity {
    
    GamePanel gp;
    public static final String objName = "Gold Coin";
    
    public OBJ_Coin_Gold(GamePanel gp) {
        super(gp);
        this.gp = gp;
        
        type = type_pickupOnly;
        name = objName;
        down1 = setup("objects/coin_gold48", gp.tileSize, gp.tileSize);
        value = 1;
        stackable = true;
    }
    
    public boolean use(Entity entity) {
        
        gp.playSE(1);
        gp.ui.addMessage("Gold Coin +" + value);
        gp.player.coin += value;
        return true;
    }    
}