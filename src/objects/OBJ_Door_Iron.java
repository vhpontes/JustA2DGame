package objects;

import entity.Entity;
import main.GamePanel;

public class OBJ_Door_Iron extends Entity{
    
    public static final String objName = "Iron Door";
    
    public OBJ_Door_Iron(GamePanel gp) {
        super(gp);
        this.gp = gp;
        
        type = type_obstacle;
        name = objName;
        image = setup("objects/door_iron", gp.tileSize, gp.tileSize);
        //image2 = setup("objects/door_opened", gp.tileSize, gp.tileSize);
        down1 = image;
        collision = true;

        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        
        setDialogue("en");
    }
    
    public void setDialogue(String lang) {
        switch(lang) {
            case "en": dialogues[0][0] = "It won't budge."; break;
            case "br": dialogues[0][0] = "Não vai ceder."; break;
        }
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
