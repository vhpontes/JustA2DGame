package objects;

import entity.Entity;
import main.GamePanel;

public class OBJ_Sword_Normal extends Entity{

    public static final String objName = "Normal Sword";
       
    public OBJ_Sword_Normal(GamePanel gp) {
        super(gp);
        
        type = type_sword;
        name = objName;
        handObject = true;
        down1 = setup("objects/sword_normal", gp.tileSize, gp.tileSize);
        attackValue = 1;
        attackArea.width = 36;
        attackArea.height = 36;
        description = "[" + name + "]\nAn old sword.";
        price = 10;
        knockBackPower = 3;
        
        motion1_duration = 2;
        motion2_duration = 10;
    }
}
