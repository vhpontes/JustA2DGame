/*
Code based in RyiSnow youtube channel:
https://www.youtube.com/c/RyiSnow
*/
 
package tile_interactive;

import main.GamePanel;


public class IT_Trunk extends InteractiveTile {
    
    GamePanel gp;
    public static final String itName = "Trunk";
    
    public IT_Trunk(GamePanel gp, int col, int row) {
        super(gp, col, row);
        this.gp = gp;
        
        name = itName;
        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;
        
        down1 = setup("tile_interactive/trunk_obj", 29, 48);

        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 0;
        solidArea.height = 0;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;        
    }
}