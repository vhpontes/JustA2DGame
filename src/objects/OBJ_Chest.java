package objects;

import entity.Entity;
import main.GamePanel;

public class OBJ_Chest extends Entity{
    
    GamePanel gp;
    Entity loot;
    boolean opened = false;

    public OBJ_Chest(GamePanel gp, Entity loot) {
        super(gp);
        this.gp = gp;
        this.loot = loot;
        
        type = type_obstacle;
        name = "Chest";
        image = setup("objects/chest", gp.tileSize, gp.tileSize);        
        image2 = setup("objects/chest_opened", gp.tileSize, gp.tileSize);
        down1 = image;
        collision = true;
    
        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 40;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    public void interact() {
        
        gp.gameState = gp.dialogueState;
        
        if(opened == false) {
            gp.playSE(3);
            
            StringBuilder sb = new StringBuilder();
            sb.append("You open the chest and find a "+ loot.name + "!");
            
            if(gp.player.canObtainItem(loot) == false) {
                sb.append("\n...But you not carry any more!");
            }
            else {
                sb.append("\nYou obtain the "+ loot.name +"!");
                down1 = image2;
                opened = true;
            }
            gp.ui.currentDialogue = sb.toString();
        }
        else {
            gp.ui.currentDialogue = "It's empty";
        }
    }
}
