package objects;

import entity.Entity;
import main.GamePanel;

public class OBJ_Heart extends Entity {
    
    public OBJ_Heart(GamePanel gp) {
        super(gp);
        
        name = "Heart";
        image = setup("objects/heart_full");
        image2 = setup("objects/heart_half");
        image3 = setup("objects/heart_blank");
    }    
}
