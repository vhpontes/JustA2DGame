/*
Code based in RyiSnow youtube channel:
https://www.youtube.com/c/RyiSnow
*/
 
package objects;

import entity.Entity;
import main.GamePanel;

public class OBJ_Pickaxe extends Entity {

    public static final String objName = "Pickaxe";
    
    public OBJ_Pickaxe(GamePanel gp) {
        super(gp);
        
        type = type_pickaxe;
        name = objName;
        handObject = true;
        down1 = setup("objects/pickaxe", gp.tileSize, gp.tileSize);
        attackValue = 2;
        attackArea.width = 30;
        attackArea.height = 30;
        description = "[" + name + "]\nYou will dig it!";
        price = 75;
        knockBackPower = 10;

        motion1_duration = 10;
        motion2_duration = 20;
    }     
}
