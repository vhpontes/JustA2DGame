package tile;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.GamePanel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class TileManagerJSON {
    
    GamePanel gp;  
    
    // referency:
    // loadJSONTiledMap("untitled.tmj");
    public TileManagerJSON(GamePanel gp) {

        this.gp = gp;
    }
    
    public void loadJSONTiledMap(String mapfile) throws FileNotFoundException, IOException, ParseException  {
        
        JSONParser parser = new JSONParser();
        
        String userDirectory = Paths.get("").toAbsolutePath().toString();
        
        try (
            Reader reader = new FileReader(userDirectory+"\\src\\res\\maps\\"+mapfile)) {

            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            
            /*
            layers
                data
                objects
            tilesets
            
            JSONObject jsonObject 
                JSONArray layers 
                    JSONObject data get(0)
                        JSONArray mapData .get("data")
                    JSONObject data get(1)
                        JSONArray mapObjData .get("objects")
                        JSONObject dataObj .get(0)
            */
            
            // LAYERS
            JSONArray layers = (JSONArray) jsonObject.get("layers"); 

            JSONObject data = (JSONObject) layers.get(0);   
            JSONArray mapData = (JSONArray) data.get("data");   
            int[] mapTile = new int[mapData.size()];
            for (int i = 0; i < mapData.size(); i++) {
                mapTile[i] = ((Long)mapData.get(i)).intValue();
            }

            String name =  data.get("name").toString();
            int widthMap = Integer.valueOf(data.get("width").toString());
            int heightMap = Integer.valueOf(data.get("height").toString());
            
            System.out.println("layers >>");
            System.out.println("data:" + Arrays.toString(mapTile));
            System.out.println("name:" + name);
            System.out.println("widthMap:" + widthMap);
            System.out.println("widthMap:" + heightMap);
            
            data = (JSONObject) layers.get(1);  
            name =  data.get("name").toString();
            JSONArray mapObjData = (JSONArray) data.get("objects");
            JSONObject dataObj = (JSONObject) mapObjData.get(0);   
            int objX = Integer.valueOf(dataObj.get("x").toString());
            int objY = Integer.valueOf(dataObj.get("y").toString());
            
            System.out.println("objects >>");
            System.out.println("x:" + objX);
            System.out.println("y:" + objY);

            //System.exit(0);
            
        } catch (ParseException ex) {
            Logger.getLogger(TileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
