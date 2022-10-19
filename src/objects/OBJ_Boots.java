package objects;

import entity.Entity;
import main.GamePanel;

public class OBJ_Boots  extends Entity{

    public OBJ_Boots(GamePanel gp) {
        super(gp);
        
        name = "Boots";
        down1 = setup("/res/objects/boots.png");
        
//        try {
//            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/boots.png"));
//            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
//        }catch(IOException e) {
//            e.printStackTrace();
//        }
    }    
}
