/*
Code based in RyiSnow youtube channel:
https://www.youtube.com/c/RyiSnow
*/
 
package objects;

import entity.Entity;
import main.GamePanel;

public class OBJ_Banner extends Entity {
    
    GamePanel gp;
    public static final String objName = "Banner";
       
    public OBJ_Banner(GamePanel gp, int numImg) {
        super(gp);
        this.gp = gp;
        
        type = type_obstacle;
        name = objName;
        width = 48;
        height = 69;
        down1 = setup("objects/banner"+numImg, this.width, this.height);
        description = "["+objName+"]\n";
        price = 999;
        stackable = false;
    }
    
    public boolean use(Entity entity) {
        
        return true;
    }
}