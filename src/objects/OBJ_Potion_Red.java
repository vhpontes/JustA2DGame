package objects;

import entity.Entity;
import main.GamePanel;

public class OBJ_Potion_Red extends Entity {
    
    GamePanel gp;

    public OBJ_Potion_Red(GamePanel gp) {
        super(gp);
        
        this.gp = gp;
        
        type = type_consumable;
        name = "Red Potion";
        value = 5;
        down1 = setup("objects/potion_red", gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nHeals your life by " + value + ".";
    }       
    
    public void use(Entity entity) {
        
        gp.gameState = gp.dialogueState;
        gp.ui.currentDialogue = "You drink the " + name + "!\n"
                + "Your life has bean recovered by " + value + ".";
        entity.life += value;
        gp.playSE(2);
    }
}
