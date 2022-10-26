package objects;

import entity.Entity;
import main.GamePanel;

public class OBJ_Key extends Entity{
    
    public OBJ_Key(GamePanel gp) {
        super(gp);
        
        type = type_consumable;
        name = "Key";
        down1 = setup("objects/key", gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nIt opens a door.";
    }
}
