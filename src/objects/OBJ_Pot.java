/*
Code based in RyiSnow youtube channel:
https://www.youtube.com/c/RyiSnow

Class code: Victor Hugo Manata Pontes 
https://www.twitch.tv/lechuck311
https://www.youtube.com/@victorhugomanatapontes
https://www.youtube.com/@dtudoumporco
*/
 
package objects;

import entity.Entity;
import main.GamePanel;

public class OBJ_Pot extends Entity {
    
    GamePanel gp;
    public static final String objName = "Pot";
       
    public OBJ_Pot(GamePanel gp) {
        super(gp);
        this.gp = gp;
        
        type = type_obstacle;
        name = objName;
        width = 35;
        height = gp.tileSize;
        image = setup("objects/pot_full", this.width, this.height);
        image2 = setup("objects/pot_empty", this.width, this.height);
        //down1 = image;
        description = "["+objName+"]\n";
        price = 999;
        collision = true;
        stackable = false;
        animation = false;
    }
    
    public boolean use(Entity entity) {
        
        return true;
    }
}