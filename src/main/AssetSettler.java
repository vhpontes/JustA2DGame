/*
Code based in RyiSnow youtube channel:
https://www.youtube.com/c/RyiSnow

Class Code modifications and addons:
Victor Hugo Manata Pontes
https://www.twitch.tv/lechuck311
https://www.youtube.com/@victorhugomanatapontes
https://www.youtube.com/@dtudoumporco
*/
 
package main;

import data.Progress;
import entity.NPC_BigRock;
import entity.NPC_Merchant;
import environment.Lighting;
import monster.MON_Bat;
import monster.MON_GreenSlime;
import monster.MON_Orc;
import monster.MON_RedSlime;
import monster.MON_SkeletonLord;
import objects.OBJ_Arrow;
import objects.OBJ_Axe;
import objects.OBJ_Banner;
import objects.OBJ_Barrel;
import objects.OBJ_BlueHeart;
import objects.OBJ_Boots;
import objects.OBJ_Bow_Normal;
import objects.OBJ_Chest;
import objects.OBJ_Door;
import objects.OBJ_Door_Iron;
import objects.OBJ_Heart;
import objects.OBJ_Key_Silver;
import objects.OBJ_Lantern;
import objects.OBJ_Pickaxe;
import objects.OBJ_Pot;
import objects.OBJ_Potion_Red;
import objects.OBJ_Shield_Blue;
import objects.OBJ_Tent;
import objects.OBJ_Torch;
import objects.OBJ_WallTorch;
import tile_interactive.IT_DestructableWall;
import tile_interactive.IT_DryTree;
import tile_interactive.IT_MetalPlate;

public class AssetSettler {
    
    GamePanel gp;
    
    public AssetSettler(GamePanel gp) {
        this.gp = gp;
    }
    
    public void setObject() {

        int mapNum = 0;
        int i = 0;
        gp.obj[mapNum][i] = new OBJ_Axe(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*32;
        gp.obj[mapNum][i].worldY = gp.tileSize*21;
        i++;
        gp.obj[mapNum][i] = new OBJ_Barrel(gp);
        gp.obj[mapNum][i].worldX = (gp.tileSize * 21)  + (gp.tileSize - gp.obj[mapNum][i].width) / 2;
        gp.obj[mapNum][i].worldY = gp.tileSize*18;
        i++;
        gp.obj[mapNum][i] = new OBJ_Potion_Red(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*22;
        gp.obj[mapNum][i].worldY = gp.tileSize*26;
        i++;
        gp.obj[mapNum][i] = new OBJ_Heart(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*22;
        gp.obj[mapNum][i].worldY = gp.tileSize*29;
        i++;
        gp.obj[mapNum][i] = new OBJ_Door(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*13;
        gp.obj[mapNum][i].worldY = gp.tileSize*23;
        i++;
        gp.obj[mapNum][i] = new OBJ_Door(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*12;
        gp.obj[mapNum][i].worldY = gp.tileSize*12;
        i++;
        gp.obj[mapNum][i] = new OBJ_Chest(gp);
        gp.obj[mapNum][i].setLootItem(new OBJ_Bow_Normal(gp));
        gp.obj[mapNum][i].setLootItem(new OBJ_Arrow(gp), 10);
        gp.obj[mapNum][i].worldX = gp.tileSize*35;
        gp.obj[mapNum][i].worldY = gp.tileSize*43;
        i++;
        gp.obj[mapNum][i] = new OBJ_Chest(gp);
        gp.obj[mapNum][i].setLootItem(new OBJ_Key_Silver(gp));
        gp.obj[mapNum][i].setLootItem(new OBJ_Lantern(gp));
        gp.obj[mapNum][i].setLootItem(new OBJ_Tent(gp));
        gp.obj[mapNum][i].setLootItem(new OBJ_Arrow(gp), 10);
        gp.obj[mapNum][i].worldX = gp.tileSize*18;
        gp.obj[mapNum][i].worldY = gp.tileSize*20;
        
        // MAP DUNGEON LVL 1
        mapNum = 2;
        i = 0;
        // Objects Torch
        gp.obj[mapNum][i] = new OBJ_WallTorch(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 12;
        gp.obj[mapNum][i].worldY = (gp.tileSize * 35) + 15;      
        i++;
        gp.obj[mapNum][i] = new OBJ_Torch(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 16;
        gp.obj[mapNum][i].worldY = (gp.tileSize * 33) + 20;      
        i++;
        gp.obj[mapNum][i] = new OBJ_Torch(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 19;
        gp.obj[mapNum][i].worldY = (gp.tileSize * 33) + 20;      
        i++;
        gp.obj[mapNum][i] = new OBJ_Torch(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 22;
        gp.obj[mapNum][i].worldY = (gp.tileSize * 38) + 20;      
        i++;
        gp.obj[mapNum][i] = new OBJ_Torch(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 29;
        gp.obj[mapNum][i].worldY = (gp.tileSize * 38) + 20;      
        i++;
        
        // Objects Pot
        gp.obj[mapNum][i] = new OBJ_Pot(gp);
        gp.obj[mapNum][i].down1 = gp.obj[mapNum][i].image;
        gp.obj[mapNum][i].worldX = (gp.tileSize * 20) + (gp.tileSize - gp.obj[mapNum][i].width) / 2;
        gp.obj[mapNum][i].worldY = gp.tileSize * 35;      
        i++;
        gp.obj[mapNum][i] = new OBJ_Pot(gp);
        gp.obj[mapNum][i].down1 = gp.obj[mapNum][i].image2;
        gp.obj[mapNum][i].worldX = (gp.tileSize * 15) + (gp.tileSize - gp.obj[mapNum][i].width) / 2;
        gp.obj[mapNum][i].worldY = gp.tileSize * 35;      
        i++;
        
        // Objects Chest
        gp.obj[mapNum][i] = new OBJ_Chest(gp);
        gp.obj[mapNum][i].setLootItem(new OBJ_Pickaxe(gp));
        gp.obj[mapNum][i].worldX = gp.tileSize*40;
        gp.obj[mapNum][i].worldY = gp.tileSize*41;      
        i++;
        gp.obj[mapNum][i] = new OBJ_Chest(gp);
        gp.obj[mapNum][i].setLootItem(new OBJ_Shield_Blue(gp));
        gp.obj[mapNum][i].worldX = gp.tileSize*13;
        gp.obj[mapNum][i].worldY = gp.tileSize*16;      
        i++;
        gp.obj[mapNum][i] = new OBJ_Chest(gp);
        gp.obj[mapNum][i].setLootItem(new OBJ_Boots(gp));
        gp.obj[mapNum][i].worldX = gp.tileSize*26;
        gp.obj[mapNum][i].worldY = gp.tileSize*34;      
        i++;
        gp.obj[mapNum][i] = new OBJ_Chest(gp);
        gp.obj[mapNum][i].setLootItem(new OBJ_Potion_Red(gp));
        gp.obj[mapNum][i].worldX = gp.tileSize*27;
        gp.obj[mapNum][i].worldY = gp.tileSize*15;      
        i++;
        
        // Objects Iron Door
        gp.obj[mapNum][i] = new OBJ_Door_Iron(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*18;
        gp.obj[mapNum][i].worldY = gp.tileSize*23;      
        i++;

        // MAP DUNGEON BOSS 
        mapNum = 3;
        i = 0;
        gp.obj[mapNum][i] = new OBJ_Door_Iron(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*25;
        gp.obj[mapNum][i].worldY = gp.tileSize*15;      
        i++;
        
        gp.obj[mapNum][i] = new OBJ_BlueHeart(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 25;
        gp.obj[mapNum][i].worldY = gp.tileSize * 8;      
        i++;
        
        mapNum = 6;
        i = 0;
        gp.obj[mapNum][i] = new OBJ_Barrel(gp);
        gp.obj[mapNum][i].worldX = (gp.tileSize * 29)  + (gp.tileSize - gp.obj[mapNum][i].width) / 2;
        gp.obj[mapNum][i].worldY = (gp.tileSize * 29) - 10;
        i++;
        gp.obj[mapNum][i] = new OBJ_Pot(gp);
        gp.obj[mapNum][i].down1 = gp.obj[mapNum][i].image;
        gp.obj[mapNum][i].worldX = (gp.tileSize * 17) + (gp.tileSize - gp.obj[mapNum][i].width) / 2;
        gp.obj[mapNum][i].worldY = (gp.tileSize * 21) - 20;      
        i++;
        gp.obj[mapNum][i] = new OBJ_Pot(gp);
        gp.obj[mapNum][i].down1 = gp.obj[mapNum][i].image2;
        gp.obj[mapNum][i].worldX = (gp.tileSize * 23) + 15 + (gp.tileSize - gp.obj[mapNum][i].width) / 2;
        gp.obj[mapNum][i].worldY = (gp.tileSize * 23) - 20;      
        i++;
        gp.obj[mapNum][i] = new OBJ_Torch(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 18;
        gp.obj[mapNum][i].worldY = (gp.tileSize * 19) + 20;
        i++;
        gp.obj[mapNum][i] = new OBJ_Torch(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 25;
        gp.obj[mapNum][i].worldY = (gp.tileSize * 19) + 20;      
        i++;
        gp.obj[mapNum][i] = new OBJ_Torch(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 32;
        gp.obj[mapNum][i].worldY = (gp.tileSize * 19) + 20;      
        i++;
        gp.obj[mapNum][i] = new OBJ_Banner(gp, 1);
        gp.obj[mapNum][i].worldX = (gp.tileSize * 24);
        gp.obj[mapNum][i].worldY = (gp.tileSize * 19) + 20;      
        i++;
        gp.obj[mapNum][i] = new OBJ_Banner(gp, 2);
        gp.obj[mapNum][i].worldX = (gp.tileSize * 26);
        gp.obj[mapNum][i].worldY = (gp.tileSize * 19) + 20;      
        i++;
        
    }
    
    public void setNPC(){
        
        int mapNum = 0;
        int i = 0;

//        gp.npc[mapNum][i] = new NPC_OldMan(gp);
//        gp.npc[mapNum][i].worldX = gp.tileSize*21;
//        gp.npc[mapNum][i].worldY = gp.tileSize*21;
//        i++;
        
        // MAP 1 INDOOR
        mapNum = 1;
        i = 0;
        gp.npc[mapNum][i] = new NPC_Merchant(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize*12;
        gp.npc[mapNum][i].worldY = gp.tileSize*7;
        i++;

        // MAP 2 DUNGEON
        mapNum = 2;
        i = 0;
        gp.npc[mapNum][i] = new NPC_BigRock(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize*20;
        gp.npc[mapNum][i].worldY = gp.tileSize*25;
        i++;
        gp.npc[mapNum][i] = new NPC_BigRock(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize*11;
        gp.npc[mapNum][i].worldY = gp.tileSize*18;
        i++;
        gp.npc[mapNum][i] = new NPC_BigRock(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize*23;
        gp.npc[mapNum][i].worldY = gp.tileSize*14;
        i++;
        
        //gp.addNPCTwitch(4, null, 16, 39);
        //i++;
    }
    
    public void setMonster() {

        int mapNum = 0;
        int i = 0;
/*        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize*21;
        gp.monster[mapNum][i].worldY = gp.tileSize*38;
        i++;
        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize*23;
        gp.monster[mapNum][i].worldY = gp.tileSize*42;
        i++;
        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize*34;
        gp.monster[mapNum][i].worldY = gp.tileSize*42;
        i++;
        gp.monster[mapNum][i] = new MON_RedSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize*38;
        gp.monster[mapNum][i].worldY = gp.tileSize*42;
        i++;
        gp.monster[mapNum][i] = new MON_RedSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize*26;
        gp.monster[mapNum][i].worldY = gp.tileSize*7;
        i++;
        gp.monster[mapNum][i] = new MON_RedSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize*37;
        gp.monster[mapNum][i].worldY = gp.tileSize*8;
        i++;
        gp.monster[mapNum][i] = new MON_Orc(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize*12;
        gp.monster[mapNum][i].worldY = gp.tileSize*33;
        
        // MAP 2
        mapNum = 2;
        i = 0;
        gp.monster[mapNum][i] = new MON_Bat(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize*34;
        gp.monster[mapNum][i].worldY = gp.tileSize*39;
        i++;
        gp.monster[mapNum][i] = new MON_Bat(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize*36;
        gp.monster[mapNum][i].worldY = gp.tileSize*25;
        i++;
        gp.monster[mapNum][i] = new MON_Bat(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize*39;
        gp.monster[mapNum][i].worldY = gp.tileSize*26;
        i++;
        gp.monster[mapNum][i] = new MON_Bat(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize*28;
        gp.monster[mapNum][i].worldY = gp.tileSize*11;
        i++;
        gp.monster[mapNum][i] = new MON_Bat(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize*10;
        gp.monster[mapNum][i].worldY = gp.tileSize*19;
        i++;
*/
        // MAP 3
        mapNum = 3;
        i = 0;
        if(Progress.skeletonLordDefeated == false) {
            gp.monster[mapNum][i] = new MON_SkeletonLord(gp);
            gp.monster[mapNum][i].worldX = gp.tileSize*23;
            gp.monster[mapNum][i].worldY = gp.tileSize*16;
            i++;
        }
    }
    
    public void setInteractiveTile() {
        
        int mapNum = 0;
        int i = 0;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 27, 12); i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 28, 12); i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 29, 12); i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 30, 12); i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 31, 12); i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 32, 12); i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 33, 12); i++;

        gp.iTile[mapNum][i] = new IT_DryTree(gp, 16, 40); i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 15, 40); i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 14, 40); i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 13, 40); i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 13, 39); i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 13, 41); i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 12, 41); i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 11, 41); i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 10, 41); i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 10, 38); i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 10, 39); i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 10, 40); i++;

        // MAP 2 DUNGEON
        mapNum = 2;
        i = 0;
        gp.iTile[mapNum][i] = new IT_DestructableWall(gp, 18, 30); i++;
        gp.iTile[mapNum][i] = new IT_DestructableWall(gp, 17, 31); i++;
        gp.iTile[mapNum][i] = new IT_DestructableWall(gp, 17, 32); i++;
        gp.iTile[mapNum][i] = new IT_DestructableWall(gp, 18, 34); i++;
        gp.iTile[mapNum][i] = new IT_DestructableWall(gp, 18, 33); i++;
        gp.iTile[mapNum][i] = new IT_DestructableWall(gp, 10, 22); i++;
        gp.iTile[mapNum][i] = new IT_DestructableWall(gp, 10, 24); i++;
        gp.iTile[mapNum][i] = new IT_DestructableWall(gp, 38, 18); i++;
        gp.iTile[mapNum][i] = new IT_DestructableWall(gp, 38, 19); i++;
        gp.iTile[mapNum][i] = new IT_DestructableWall(gp, 38, 20); i++;
        gp.iTile[mapNum][i] = new IT_DestructableWall(gp, 38, 21); i++;
        gp.iTile[mapNum][i] = new IT_DestructableWall(gp, 18, 13); i++;
        gp.iTile[mapNum][i] = new IT_DestructableWall(gp, 18, 14); i++;
        gp.iTile[mapNum][i] = new IT_DestructableWall(gp, 22, 28); i++;
        gp.iTile[mapNum][i] = new IT_DestructableWall(gp, 30, 28); i++;
        gp.iTile[mapNum][i] = new IT_DestructableWall(gp, 32, 28); i++;
        
        gp.iTile[mapNum][i] = new IT_MetalPlate(gp, 20, 22); i++;
        gp.iTile[mapNum][i] = new IT_MetalPlate(gp, 8, 17); i++;
        gp.iTile[mapNum][i] = new IT_MetalPlate(gp, 39, 31); i++;
    }
}