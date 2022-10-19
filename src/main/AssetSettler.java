package main;

import entity.NPC_OldMan;
import objects.OBJ_Boots;
import objects.OBJ_Chest;
import objects.OBJ_Door;
import objects.OBJ_Key;

public class AssetSettler {
    
    GamePanel gp;
    
    public AssetSettler(GamePanel gp) {
        this.gp = gp;
    }
    
    public void setObject() {
        

    }
    
    public void setNPC(){
        
        gp.npc[0] = new NPC_OldMan(gp);
        gp.npc[0].worldX = gp.tileSize*21;
        gp.npc[0].worldY = gp.tileSize*21;
        
        
    }
}
