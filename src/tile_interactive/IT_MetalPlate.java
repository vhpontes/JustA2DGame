package tile_interactive;

import entity.Entity;
import java.awt.Color;
import java.util.Random;
import main.GamePanel;
import objects.OBJ_Coin_Bronze;
import objects.OBJ_Heart;
import objects.OBJ_ManaCrystal;

public class IT_MetalPlate extends InteractiveTile {
    
    GamePanel gp;
    public static final String itName = "Metal Plate";
    
    public IT_MetalPlate(GamePanel gp, int col, int row){
        super(gp, col, row);
        this.gp = gp;
        
        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;
        
        name = itName;
        down1 = setup("tile_interactive/metalplate", gp.tileSize, gp.tileSize);
        
        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 0;
        solidArea.height = 0;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;            
    }
}