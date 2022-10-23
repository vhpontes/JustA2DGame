package objects;

import entity.Entity;
import main.GamePanel;

public class OBJ_Potion_Red extends Entity {
    
    GamePanel gp;
    int value = 5;
    
    public OBJ_Potion_Red(GamePanel gp) {
        super(gp);
        
        type = type_consumable;
        name = "Red Potion";
        down1 = setup("objects/potion_red", gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nHeals your life by " + value + ".";
    }       
    
    public void use(Entity entity) {
        
twi        System.out.println("entrou use");
        gp.gameState = gp.dialogueState;
        gp.ui.currentDialogue = "You drink the " + name + "!\n"
                + "Your life has bean recovered by " + value + ".";
        entity.life += value;
        if(gp.player.life > gp.player.maxLife) {
            gp.player.life = gp.player.maxLife;
        }
        gp.playSE(2);
    }
}
