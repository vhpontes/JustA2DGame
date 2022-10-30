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
        value = 1;
        image = setup("objects/coin_bronze", gp.tileSize, gp.tileSize);
    }
    
    public void use(Entity entity) {
        
        gp.playSE(1);
        gp.ui.addMessage("Coin +" + value);
        gp.player.coin += value;
    }    
}
