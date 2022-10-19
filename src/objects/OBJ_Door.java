package objects;

import entity.Entity;
import main.GamePanel;

public class OBJ_Door extends Entity{
    
    public OBJ_Door(GamePanel gp) {
        super(gp);
        
        name = "Door";
        down1 = setup("/res/objects/door.png");
        collision = true;
        
//        try {
//            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/door.png"));
//            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
//        }catch(IOException e) {
//            e.printStackTrace();
//        }
    }
}
