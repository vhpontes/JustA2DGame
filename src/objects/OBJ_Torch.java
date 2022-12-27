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

public class OBJ_Torch extends Entity{

    public static final String objName = "Torch";
       
    public OBJ_Torch(GamePanel gp) {
        super(gp);
        
        type = type_obstacle;
        name = objName;
        anim = setupGIF("objects/torch_anim", gp.tileSize, gp.tileSize);
        description = "[Torch]\nTorch, the fire illuminate your\n surroundings.";
        price = 10;
        lightRadius = 200;
        animation = true;
    }
}
