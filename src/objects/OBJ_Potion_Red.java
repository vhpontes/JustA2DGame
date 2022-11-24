package objects;

import entity.Entity;
import main.GamePanel;

public class OBJ_Potion_Red extends Entity {
    
    GamePanel gp;
    public static final String objName = "Red Potion";
    
    public OBJ_Potion_Red(GamePanel gp) {
        super(gp);
        
        this.gp = gp;
        
        type = type_consumable;
        stackable = true;
        name = objName;
        value = 5;
        down1 = setup("objects/potion_red48", gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nHeals your life by " + value + ".";
        price = 25;
        
        setDialogue();
    }       
    
    public void setDialogue() {
        
        dialogues[0][0] = "You drink the " + name + "!\n"
                + "Your life has bean recovered by " + value + ".";
    }    
    
    public boolean use(Entity entity) {
        
        startDialogue(this, 0);
        entity.life += value;
        gp.playSE(2);
        return true;
    }
}
