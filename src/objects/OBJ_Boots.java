package objects;

import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;

public class OBJ_Boots  extends SuperObject{
    
    GamePanel gp;

    public OBJ_Boots(GamePanel gp) {
        
        name = "Boots";
        
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/boots.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch(IOException e) {
            e.printStackTrace();
        }
    }    
}
