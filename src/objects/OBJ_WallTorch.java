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

public class OBJ_WallTorch extends Entity{

    public static final String objName = "Wall Torch";
       
    public OBJ_WallTorch(GamePanel gp) {
        super(gp);
        
        type = type_obstacle;
        name = objName;
        anim = setupGIF("objects/wallTorch_anim2", gp.tileSize, gp.tileSize);
        description = "[Torch]\nWall Torch, the fire illuminate\n surroundings.";
        price = 20;
        lightRadius = 300;
        animation = true;
    }
}
