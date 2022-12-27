/*
Code based in RyiSnow youtube channel:
https://www.youtube.com/c/RyiSnow

Class Code modifications and addons:
Victor Hugo Manata Pontes
https://www.twitch.tv/lechuck311
https://www.youtube.com/@victorhugomanatapontes
https://www.youtube.com/@dtudoumporco
*/
 
package tile;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import static java.lang.Math.floor;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.UtilityTool;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public Tile[][] tileInfo;
//    public int mapTileNum[][][];
    public int mapTileNum[][][];
    public int mapTileInfo[][];
    boolean drawPath = true; // Draw a red path in tiles 
    ArrayList<String> fileNames = new ArrayList<>();
    ArrayList<String> collisionStatus = new ArrayList<>();
    String areaName = "outside";
    String fileName = null;

    // POSITION VARS
    double screenCol = 0;
    double screenRow = 0;
    double worldCol = 0;
    double worldRow = 0;
    double mouseCol = 0;
    double mouseRow = 0;
    int camOffSetX = 0;
    int camOffSetY = 0;
    double camOffSetCol = 0;
    double camOffSetRow = 0;
    int mouseOffCol = 0;
    int mouseOffRow = 0;
    int mouseTileCol = 0;
    int mouseTileRow = 0;
    public int searchMouseCol = 0;
    public int searchMouseRow = 0;
    public boolean tempTileXYCollision = false;

    
    public TileManager(GamePanel gp) {
        
        this.gp = gp;
        
        loadTileData();
    }

    public void loadJSONTiledMap(String mapfile) throws IOException {
        
        String newLine = System.getProperty("line.separator");
        InputStream is = getClass().getResourceAsStream("/res/maps/"+mapfile);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder result = new StringBuilder();
        for (String line; (line = br.readLine()) != null; ) {
             if (result.length() > 0) {
                 result.append(newLine);
             }
             result.append(line);
        } 
        
        //String jsonMap = "{"name":"user","id":1234,"marks":[{"english":85,"physics":80,"chemistry":75}]}";
        String jsonMap = result.toString();
        String s="[0,{\"1\":{\"2\":{\"3\":{\"4\":[5,{\"6\":7}]}}}}]";
        Object obj = JSONValue.parse(s); 
        JSONArray array=(JSONArray)obj; 
        System.out.println("======the 2nd element of array======"); 
        System.out.println(array.get(1)); 
        System.out.println();

        JSONObject obj2=(JSONObject)array.get(1); 
        System.out.println("======field \"1\"=========="); 
        System.out.println(obj2.get("1"));
        
        
        
//        String jsonMap = result.toString();
//        
//        JSONObject jsonOBJmap = new JSONObject(jsonMap);
//        
//        JSONArray marks = jsonOBJmap.
//        
//        JSONObject data = marks.getJSONObject(0);
//        
//        
//        System.out.println(String.format("Name %s", data.getValue("physics")));
//        System.out.println(String.format("Data %s", data.getValue("data")));
//        System.out.println(String.format("Height %s", data.getValue("height")));
    }
    
    public void loadTileData() {
        try {
            loadJSONTiledMap("untitled.tmj");
            //loadJSONTiledMap("novo1.txt");
        } catch (IOException ex) {
            System.out.println(ex);
            System.exit(0);
        }
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
        tile = new Tile[fileNames.size()]; // ORIGINAL LINE
        this.getTileImage();
        
        tileInfo = new Tile[gp.maxWorldCol][gp.maxWorldRow];
        this.getTileInfo();
        
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
        loadMap("/res/maps/twitch_arena01.txt", 6);
        loadMap("/res/maps/twitch_arena01.txt", 6);
//        tile = new Tile[70];
//        mapTileNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

//        getTileImage();
//        loadMap("/res/maps/worldV2.txt", 0);
//        loadMap("/res/maps/interior01.txt", 1);
//        loadMap("/res/maps/dg_twitch01.txt", 2);
//        loadMap("/res/maps/dungeon01.txt", 3);
    }

    public void getTileImage() {
  
        for(int i = 0; i < fileNames.size(); i++) {
            fileName = fileNames.get(i);
            this.setup(i,  fileName, this.getCurrentArea(), getTileCollision(fileName));
        }
    }

    public void getTileInfo() {

        int col = 0;
        int row = 0;
        
        while(col < gp.maxWorldCol && row < gp.maxWorldRow){
            
            int tileX = col * gp.tileSize;
            int tileY = row * gp.tileSize;

            this.tileInfo[col][row] = new Tile();
            this.tileInfo[col][row].tileX = tileX;
            this.tileInfo[col][row].tileY = tileY;
            
            col++;
            if(col == gp.maxWorldCol) {
                col = 0;
                row++;
            }
        }
        mapTileInfo = new int[gp.maxWorldCol][gp.maxWorldRow];
    }
    
    public int[] getTileXY(MouseEvent e, int x, int y) {
        
        int screenX = gp.player.screenX;
        int screenY = gp.player.screenY;

        int rightOffset = gp.screenWidth - gp.player.screenX;
        if(rightOffset > gp.worldWidth - gp.player.worldX) {
            screenX = gp.screenWidth - (gp.worldWidth - gp.player.worldX);
        }
        int bottonOffset = gp.screenHeight - gp.player.screenY;
        if(bottonOffset > gp.worldHeight - gp.player.worldY) {
            screenY = gp.screenHeight - (gp.worldHeight - gp.player.worldY);
        }        
        
        int worldX = gp.player.worldX;
        int worldY = gp.player.worldY;
        
        screenCol = (double)floor(screenX / gp.tileSize);
        screenRow = (double)floor(screenY / gp.tileSize);

        worldCol = (double)floor(worldX / gp.tileSize);
        worldRow = (double)floor(worldY / gp.tileSize);
        
        int mouseX = e.getPoint().x;
        int mouseY = e.getPoint().y;
            
        mouseCol = (double)floor(mouseX / gp.tileSize);
        mouseRow = (double)floor(mouseY / gp.tileSize);

        camOffSetX = worldX - mouseX;
        camOffSetY = worldY - mouseY;

        camOffSetCol = worldCol - screenCol;
        camOffSetRow = worldRow - screenRow;
        
        mouseOffCol = (int)(mouseCol + camOffSetCol);
        mouseOffRow = (int)(mouseRow + camOffSetRow);
        
        if(worldX > screenX) { 
            mouseTileCol = (int)mouseOffCol; 
        }
        else { 
            mouseTileCol = (int)mouseCol; 
        }
        if(worldY > screenY) { 
            mouseTileRow = (int)mouseOffRow; 
        }
        else { 
            mouseTileRow = (int)mouseRow; 
        }
        
        // Check Tiles arround to get correct mouse clicked tile
        Rectangle bounds = new Rectangle();
        int tempTileX = 0;
        int tempTileY = 0;
        int mouseWorldX = (gp.player.worldX - gp.player.screenX) + mouseX;
        int mouseWorldY = (gp.player.worldY - gp.player.screenY) + mouseY;
        
        // IF Camera at the edge
        if(gp.player.screenX > gp.player.worldX) {
            mouseWorldX = mouseX;
        }
        if(gp.player.screenY > gp.player.worldY) {
            mouseWorldY = mouseY;
        }
        rightOffset = gp.screenWidth - gp.player.screenX;
        if(rightOffset > gp.worldWidth - gp.player.worldX) {
            mouseWorldX = (gp.worldWidth - (gp.player.screenX * 2)) + mouseX - gp.tileSize;
        }        
        bottonOffset = gp.screenHeight - gp.player.screenY;
        if(bottonOffset > gp.worldHeight - gp.player.worldY) {
            mouseWorldY = (gp.worldHeight - (gp.player.screenY * 2)) + mouseY - gp.tileSize;
        }        
        
        for(int col = -1; col < 2; col++){
            for(int row = -1; row < 2; row++){
                int tempMouseCol = mouseTileCol + col; 
                int tempMouseRow = mouseTileRow + row;

                // PEGA x, y iniciais dos tiles
                tempTileX = gp.tileM.tileInfo[tempMouseCol][tempMouseRow].tileX;
                tempTileY = gp.tileM.tileInfo[tempMouseCol][tempMouseRow].tileY;

                bounds.setBounds(tempTileX, tempTileY, gp.tileSize, gp.tileSize);
                
                if(bounds.intersects(mouseWorldX, mouseWorldY, 1, 1)){
                    searchMouseCol = tempMouseCol;
                    searchMouseRow = tempMouseRow;
                    tempTileXYCollision = gp.tileM.tileInfo[searchMouseCol][searchMouseRow].collision;
                    break;
                }
            }
        }
        return new int[] {searchMouseCol, searchMouseRow};
    }

    public boolean getTileCollision(String fileName) {

        boolean collision = false;

        for(int i = 0; i < fileNames.size(); i++) {
            // Get a collision status
            if(fileNames.get(i).equals(fileName) && collisionStatus.get(i).equals("true")) {
                collision = true;
            }
        } 
        return collision;
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
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/"+areaName+"/"+imagePath));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].tileX = index;
            tile[index].tileY = index;
            tile[index].filename = imagePath;
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

                tile[tileNum].tileX = worldX;
                tile[tileNum].tileY = worldY;
                //tileInfo[worldCol][worldRow].filename = tile[tileNum].filename;
                tileInfo[worldCol][worldRow].collision = tile[tileNum].collision;

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
                        g2.drawString("" + worldCol + ":" + worldRow +"", screenX+5, screenY + gp.tileSize / 2);
                        g2.setColor(Color.black);
                        g2.setFont(g2.getFont().deriveFont(10F));
                        g2.drawString("" + tile[tileNum].tileX + ":" + tile[tileNum].tileY +"", screenX+5, screenY + gp.tileSize-2);
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
