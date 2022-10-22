package main;

import entity.NPC_OldMan;
import monster.MON_GreenSlime;
import objects.OBJ_Door;
import objects.OBJ_Sword_Normal;

public class AssetSettler {
    
    GamePanel gp;
    
    public AssetSettler(GamePanel gp) {
        this.gp = gp;
    }
    
    public void setObject() {

//        gp.obj[0] = new OBJ_Sword_Normal(gp);
//        gp.obj[0].worldX = gp.tileSize*22;
//        gp.obj[0].worldY = gp.tileSize*22;
    }
    
    public void setNPC(){
        
        gp.npc[0] = new NPC_OldMan(gp);
        gp.npc[0].worldX = gp.tileSize*21;
        gp.npc[0].worldY = gp.tileSize*21;
    }
    
    public void setMonster() {

        gp.monster[0] = new MON_GreenSlime(gp);
        gp.monster[0].worldX = gp.tileSize*23;
        gp.monster[0].worldY = gp.tileSize*36;

        gp.monster[1] = new MON_GreenSlime(gp);
        gp.monster[1].worldX = gp.tileSize*23;
        gp.monster[1].worldY = gp.tileSize*37;
    }
}
