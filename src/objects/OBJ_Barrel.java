/*
Code based in RyiSnow youtube channel:
https://www.youtube.com/c/RyiSnow
*/
 
package objects;

import entity.Entity;
import main.GamePanel;

public class OBJ_Barrel extends Entity {
    
    GamePanel gp;
    public static final String objName = "Barrel";
       
    public OBJ_Barrel(GamePanel gp) {
        super(gp);
        this.gp = gp;
        
        type = type_obstacle;
        name = objName;
        width = 37;
        height = 48;
        down1 = setup("objects/Barrel1", this.width, this.height);
        down2 = setup("objects/Barrel2", this.width, this.height);
        description = "["+objName+"]\n";
        price = 999;
        stackable = false;
    }
    
    public boolean use(Entity entity) {
        
        return true;
    }
}