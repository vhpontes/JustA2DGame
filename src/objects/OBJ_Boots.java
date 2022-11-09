package objects;

import entity.Entity;
import main.GamePanel;

public class OBJ_Boots extends Entity{

    public static final String objName = "Boots";

    public OBJ_Boots(GamePanel gp) {
        super(gp);
        
        type = type_consumable;
        stackable = true;
        name = objName;
        down1 = setup("objects/boots", gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nThis boots increase your speed.";
        price = 50;
    }    
}
