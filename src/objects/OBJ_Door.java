/*
Code based in RyiSnow youtube channel:
https://www.youtube.com/c/RyiSnow
*/
 
package objects;

import entity.Entity;
import main.GamePanel;

public class OBJ_Door extends Entity{
    
    public static final String objName = "Door";
    
    public OBJ_Door(GamePanel gp) {
        super(gp);
        this.gp = gp;
        
        type = type_obstacle;
        name = objName;
        image = setup("objects/door48_2", gp.tileSize, gp.tileSize);
        image2 = setup("objects/door48_2_opened", gp.tileSize, gp.tileSize);
        down1 = image;
        collision = true;

        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        
        setDialogue();
    }
    
    public void setDialogue() {
        
        dialogues[0][0] = "You need a key to open this door";
    }
    
    public void interact() {
        
        if(opened == false) {
            
            startDialogue(this, 0);
        }
        else {

            gp.playSE(3);
            down1 = image2;
            opened = true;
        }
            
    }
}
