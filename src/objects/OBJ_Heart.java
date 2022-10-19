package objects;

import entity.Entity;
import main.GamePanel;

public class OBJ_Heart extends Entity {
    
    public OBJ_Heart(GamePanel gp) {
        super(gp);
        
        name = "Heart";
        image = setup("/res/objects/heart_full.png");
        image2 = setup("/res/objects/heart_half.png");
        image3 = setup("/res/objects/heart_blank.png");
        
//        try {
//            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/heart_full.png"));
//            image2 = ImageIO.read(getClass().getResourceAsStream("/res/objects/heart_half.png"));
//            image3 = ImageIO.read(getClass().getResourceAsStream("/res/objects/heart_blank.png"));
//            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
//            image2 = uTool.scaleImage(image2, gp.tileSize, gp.tileSize);
//            image3 = uTool.scaleImage(image3, gp.tileSize, gp.tileSize);
//        }catch(IOException e) {
//            e.printStackTrace();
//        }
    }    
}
