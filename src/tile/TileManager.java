package tile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.UtilityTool;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][][];
    
    public TileManager(GamePanel gp) {
        
        this.gp = gp;
        
        tile = new Tile[50];
        mapTileNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap("/res/maps/worldV2.txt", 0);
        loadMap("/res/maps/interior01.txt", 1);
    }

    public void getTileImage() {

        setup(0,  "tiles/grass00", false);
        setup(1,  "tiles/grass00", false);
        setup(2,  "tiles/grass00", false);
        setup(3,  "tiles/grass00", false);
        setup(4,  "tiles/grass00", false);
        setup(5,  "tiles/grass00", false);
        setup(6,  "tiles/grass00", false);
        setup(7,  "tiles/grass00", false);
        setup(8,  "tiles/grass00", false);
        setup(9,  "tiles/grass00", false);
        setup(10, "tiles/grass00", false);
        setup(11, "tiles/grass01", false);
        setup(12, "tiles/water00", true);
        setup(13, "tiles/water01", true);
        setup(14, "tiles/water02", true);
        setup(15, "tiles/water03", true);
        setup(16, "tiles/water04", true);
        setup(17, "tiles/water05", true);
        setup(18, "tiles/water06", true);
        setup(19, "tiles/water07", true);
        setup(20, "tiles/water08", true);
        setup(21, "tiles/water09", true);
        setup(22, "tiles/water10", true);
        setup(23, "tiles/water11", true);
        setup(24, "tiles/water12", true);
        setup(25, "tiles/water13", true);
        setup(26, "tiles/road00", false);
        setup(27, "tiles/road01", false);
        setup(28, "tiles/road02", false);
        setup(29, "tiles/road03", false);
        setup(30, "tiles/road04", false);
        setup(31, "tiles/road05", false);
        setup(32, "tiles/road06", false);
        setup(33, "tiles/road07", false);
        setup(34, "tiles/road08", false);
        setup(35, "tiles/road09", false);
        setup(36, "tiles/road10", false);
        setup(37, "tiles/road11", false);
        setup(38, "tiles/road12", false);
        setup(39, "tiles/earth", false);
        setup(40, "tiles/wall", true);
        setup(41, "tiles/tree", true);
        setup(42, "tile_interactive/trunk", true);
        setup(43, "tiles/pit", false);
        setup(44, "tiles/hut", false);
        setup(45, "tiles/floor01", false);
        setup(46, "tiles/table01", true);
        
    }

    public void setup(int index, String imagePath, boolean collision){
        
        UtilityTool uTool = new UtilityTool();
        
        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/res/"+imagePath+".png"));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;
            
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public void loadMap(String filePath, int map) {
        
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            
            int col = 0;
            int row = 0;
            
            while(col < gp.maxWorldCol && row < gp.maxWorldRow){
                
                String line = br.readLine();
                
                while(col < gp.maxWorldCol) {
                    String numbers[] = line.split(" ");
                    
                    int num = Integer.parseInt(numbers[col]);
                    
                    mapTileNum[map][col][row] = num;
                    col++;
                }
                if(col == gp.maxWorldCol) {
                    col = 0;
                    row ++;
                }
            }
            br.close();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public void draw(Graphics2D g2) {
        
        int worldCol = 0;
        int worldRow = 0;
        
        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
    
            int tileNum = mapTileNum[gp.currentMap][worldCol][worldRow];
            
            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;
            
            if(worldX + gp.tileSize> gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){
                
                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);

//                    g2.setColor(Color.yellow);
//                    g2.setFont(g2.getFont().deriveFont(16F));
//                    g2.drawString(worldCol+"/"+worldRow, screenX, screenY);
            }
            worldCol++;
            
            if(worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
            
            
        }
    }
}
