package objects;

import entity.Entity;
import main.GamePanel;

public class OBJ_Key extends Entity{
    
    GamePanel gp;
    
    public OBJ_Key(GamePanel gp) {
        super(gp);
        this.gp = gp;
        
        type = type_consumable;
        stackable = true;
        name = "Key";
        down1 = setup("objects/key", gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nIt opens a door.";
        price = 100;
    }
    
    public boolean use(Entity entity) {
        
        gp.gameState = gp.dialogueState;
        
        int objIndex = getDetected(entity, gp.obj, "Door");
        
        if(objIndex != 999) {
            gp.ui.currentDialogue = "You use the " + name + " and open the door";
            gp.playSE(3);
            gp.obj[gp.currentMap][objIndex] = null;
            return true;
        }
        else {
            gp.ui.currentDialogue = "You do nothing with this " + name;
            return false;
        }
    }
}
