/*
Code based in RyiSnow youtube channel:
https://www.youtube.com/c/RyiSnow
*/
 
package tile;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.UtilityTool;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][][];
    boolean drawPath = true; // Draw a red path in tiles 
    ArrayList<String> fileNames = new ArrayList<>();
    ArrayList<String> collisionStatus = new ArrayList<>();
    String areaName = "outside";

    
    public TileManager(GamePanel gp) {
        
        this.gp = gp;
        
        loadTileData();

    }

    public void loadTileData() {
        
        //System.out.println("loadTileData getResourceAsStream: " + "/res/tiles/"+getCurrentArea()+"/tiledata.txt");
        // READ TILE DATA FILE
        InputStream is = getClass().getResourceAsStream("/res/tiles/"+getCurrentArea()+"/tiledata.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        
        // CLEAR Lists Informations
        fileNames.clear();
        collisionStatus.clear();
        
        // GETTING TILE NAMES AND COLLISION INFO FROM FILE
        String line;
        
        try {
            while((line = br.readLine()) != null) {
                fileNames.add(line);
                collisionStatus.add(br.readLine());
            }
            br.close();
        } catch (IOException e) {
            System.out.println(e);
        }
        
        // INITIALIZE THE TITLE ARRAY BASED ON THE FileNames size
        tile = new Tile[fileNames.size()];        
        this.getTileImage();
        
        // GET THE maxWorldCol & Row
        is = getClass().getResourceAsStream("/res/maps/worldV2.txt");
        br = new BufferedReader(new InputStreamReader(is));
        
        try {
            String line2 = br.readLine();
            String maxTile[] = line2.split(" ");
            
            gp.maxWorldCol = maxTile.length;
            gp.maxWorldRow = maxTile.length;
            
            mapTileNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
            
            br.close();
        } catch(IOException e) {
            System.out.print("Exception TileManager " + e);
        }

        loadMap("/res/maps/worldmap.txt", 0);
        loadMap("/res/maps/indoor01.txt", 1);
        loadMap("/res/maps/dungeon01.txt", 2);
        loadMap("/res/maps/dungeon02.txt", 3);
        loadMap("/res/maps/islands.txt", 4);
        loadMap("/res/maps/highlands01.txt", 5);
//        tile = new Tile[70];
//        mapTileNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

//        getTileImage();
//        loadMap("/res/maps/worldV2.txt", 0);
//        loadMap("/res/maps/interior01.txt", 1);
//        loadMap("/res/maps/dg_twitch01.txt", 2);
//        loadMap("/res/maps/dungeon01.txt", 3);
    }
    
    public void getTileImage() {
//System.out.println("getTileImage areaName: " + getCurrentArea());
//System.out.println("fileNames.size(): " + fileNames.size());

        for(int i = 0; i < fileNames.size(); i++) {
            
            String fileName;
            boolean collision;
            
            // Get a file name
            fileName = fileNames.get(i);
            
            // Get a collision status
            if(collisionStatus.get(i).equals("true")) {
                collision = true;
            }
            else {
                collision = false;
            }
            
            this.setup(i,  fileName, this.getCurrentArea(), collision);
        }
//        System.out.println("-------------------------------------------------");
    }
    
    public String getCurrentArea() {

        switch(gp.nextArea) {
            case 50 -> areaName = "outside";
            case 51 -> areaName = "indoor";
            case 52 -> areaName = "dungeon";
        }        
        
        return areaName;
    }

    public void setup(int index, String imagePath, String areaName, boolean collision) {
        
        UtilityTool uTool = new UtilityTool();
        
        try {
            //System.out.println("setup: " + "/res/tiles/"+areaName+"/"+imagePath+" "+index+":"+collision);
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/"+areaName+"/"+imagePath));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;
            
        }catch(IOException e) {
            System.out.println("ERROR: " + e);
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

    public boolean inBounds(int x, int y, Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;
        
        int worldX = worldCol * gp.tileSize;
        int worldY = worldRow * gp.tileSize;        

//        int screenX = worldX - gp.player.worldX + gp.player.screenX;
//        int screenY = worldY - gp.player.worldY + gp.player.screenY;        
        int screenX = gp.player.screenX;
        int screenY = gp.player.screenY;        
        
        //int oX = gp.player.worldX - (gp.player.screenX / 2);
        int oX = (int)(gp.player.worldX / gp.tileSize) - (gp.player.screenX / gp.tileSize);
        int oY = (int)(gp.player.worldY / gp.tileSize) - (gp.player.screenY / gp.tileSize);

        int mouseCol = (x / gp.tileSize) + oX;
        int mouseRow = (y / gp.tileSize) + oY;        
        
        int tileX = (int) (x - oX) / gp.tileSize;
        int tileY = (int) (y - oY) / gp.tileSize;
        
        Rectangle bounds = new Rectangle();
        bounds.setBounds(screenX, screenY, gp.tileSize, gp.tileSize);
        
//        g2.setColor(new Color(0, 255, 0, 70));
//        g2.setColor(Color.green);
//        g2.fillRect(x, y, gp.tileSize, gp.tileSize);
//
//        System.out.println("-------------------");
//        System.out.println("Offset (X:" + oX + " Y:" + oY + ")");
//        System.out.println("Tile POS (X:" + tileX + " Y:" + tileY + ")");
//        System.out.println("Clicked at (" + x + ", " + y + ")");
//        System.out.println("Screen  at (" + screenX + ", " + screenY + ")");
//        System.out.println("Mouse at (R" + mouseCol + ", C" + mouseRow + ")");
//        System.out.println(gp.player.worldX + " "+ gp.player.worldY);
//        System.out.println("-------------------");
        
        return bounds.intersects(x, y, 1, 1);
    }    
    
    public void drawRectPath(Graphics2D g2) {
        
        g2.setColor(new Color(255, 0, 0, 70));

        for(int i = 0; i < gp.pFinder.pathList.size(); i++){
            int worldX = gp.pFinder.pathList.get(i).col * gp.tileSize;
            int worldY = gp.pFinder.pathList.get(i).row * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            // Stop moving the camera at the edge
            if(gp.player.screenX > gp.player.worldX) {
                screenX = worldX;
            }
            if(gp.player.screenY > gp.player.worldY) {
                screenY = worldY;
            }                    
            int rightOffset = gp.screenWidth - gp.player.screenX;
            if(rightOffset > gp.worldWidth - gp.player.worldX) {
                screenX = gp.screenWidth - (gp.worldWidth - worldX);
            }
            int bottonOffset = gp.screenHeight - gp.player.screenY;
            if(bottonOffset > gp.worldHeight - gp.player.worldY) {
                screenY = gp.screenHeight - (gp.worldHeight - worldY);
            }

            g2.fillRect(screenX, screenY, gp.tileSize, gp.tileSize);
        }        
    }
    
    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;
        
        if(gp.gameState != gp.transitionState) {

            while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

                int tileNum = mapTileNum[gp.currentMap][worldCol][worldRow];

                int worldX = worldCol * gp.tileSize;
                int worldY = worldRow * gp.tileSize;
                int screenX = worldX - gp.player.worldX + gp.player.screenX;
                int screenY = worldY - gp.player.worldY + gp.player.screenY;

                // Stop moving the camera at the edge
                if(gp.player.screenX > gp.player.worldX) {
                    screenX = worldX;
                }
                if(gp.player.screenY > gp.player.worldY) {
                    screenY = worldY;
                }
                int rightOffset = gp.screenWidth - gp.player.screenX;
                if(rightOffset > gp.worldWidth - gp.player.worldX) {
                    screenX = gp.screenWidth - (gp.worldWidth - worldX);
                }
                int bottonOffset = gp.screenHeight - gp.player.screenY;
                if(bottonOffset > gp.worldHeight - gp.player.worldY) {
                    screenY = gp.screenHeight - (gp.worldHeight - worldY);
                }
                
                if( worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                    worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                    worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                    worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){

//                    System.out.println("worldCol       :"+worldCol + ", "+worldRow);
//                    System.out.println("drawImage      :"+screenX + ", "+screenY);
//                    System.out.println("gp.player.world:"+gp.player.worldX + ", " +gp.player.worldY);
//                    System.out.println(" ");
                    
                    g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);

                    if(gp.keyH.showRedRectangleTile == true) {
                        // DRAW A RED RECTANCLE FOR EACH TILE
                        g2.setStroke(new BasicStroke(1));
                        g2.setFont(new Font("Arial", Font.BOLD, 17));
                        g2.setColor(Color.red);
                        g2.drawRect(screenX, screenY, gp.tileSize, gp.tileSize);

                        // DRAW A COODS x y IN EACH TILE
                        g2.setColor(Color.white);
                        g2.setFont(g2.getFont().deriveFont(16F));
                        g2.drawString(""+worldCol + ":" + worldRow +"", screenX+5, screenY + gp.tileSize / 2);
                    }
                }
                else if(gp.player.screenX > gp.player.worldX ||
                        gp.player.screenY > gp.player.worldY ||
                        rightOffset > gp.worldWidth - gp.player.worldX ||
                        bottonOffset > gp.worldHeight - gp.player.worldY) {
                    
                    g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
                }
                    
                
                worldCol++;

                if(worldCol == gp.maxWorldCol) {
                    worldCol = 0;
                    worldRow++;
                    //System.exit(0);
                }
            }

            if(drawPath == true) {
                drawRectPath(g2);
                //inBounds(0,0,g2);
            }
        }
    }
}
