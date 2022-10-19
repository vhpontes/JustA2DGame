package objects;

import entity.Entity;
import main.GamePanel;

public class OBJ_Key extends Entity{
    
    public OBJ_Key(GamePanel gp) {
        super(gp);
        
        name = "Key";
        down1 = setup("/res/objects/key.png");
        
//        try {
//            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/key.png"));
//            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
//        }catch(IOException e) {
//            e.printStackTrace();
//        }
    }
}
