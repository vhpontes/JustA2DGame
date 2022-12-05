/*
Code based in RyiSnow youtube channel:
https://www.youtube.com/c/RyiSnow
*/
 
package main;

import entity.Entity;

public class EventHandler{
    
    GamePanel gp;
    EventRect eventRect[][][];
    Entity eventMaster;
    
    int previousEventX, previousEventY;
    boolean canTouchEvent = true;
    int tempMap, tempCol, tempRow;
    
    public EventHandler(GamePanel gp) {
        
        this.gp = gp;
        
        eventMaster = new Entity(gp);
        
        eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
        
        int map = 0;
        int col = 0;
        int row = 0;
        
        while(map < gp.maxMap && col < gp.maxWorldCol && row < gp.maxWorldRow) {
            eventRect[map][col][row] = new EventRect();
            eventRect[map][col][row].x = 23;
            eventRect[map][col][row].y = 23;
            eventRect[map][col][row].width = 2;
            eventRect[map][col][row].height = 2;
            eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;
            
            col++;
            if(col == gp.maxWorldCol){
                col = 0;
                row++;
                if(row == gp.maxWorldCol) {
                    row = 0;
                    map++;
                }
            }
        }
        setDialogue("en");
    }
    
    public void setDialogue(String lang) {
        switch(lang) {
            case "en" -> {
                eventMaster.dialogues[0][0] = "You fell into the well!";
                eventMaster.dialogues[1][0] = "You drank water from this well.\nYour health and mana have been restored.\n\n" +
                            "(The game progress has been saved!)";
                eventMaster.dialogues[1][1] = "Damm,  this is a good water!";
            }
            case "br" -> {
                eventMaster.dialogues[0][0] = "Você caiu dentro do poço!";
                eventMaster.dialogues[1][0] = "Você bebeu água deste poço.\nSua vida e mana foram restauradas.\n\n" +
                            "(O progresso do jogo foi salvo!)";
                eventMaster.dialogues[1][1] = "Caramba, esta é uma boa água!";
            }
        }
    }

    public void checkEvent() {
        
        // Check if the player character is more than 1 tile away from the last event
        int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDistance = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);
        if(distance > gp.tileSize) {
            canTouchEvent = true;
        }
        
        if(canTouchEvent == true) {

            if(hit(0,25,19,"any") == true) {damagePit(0,25,19,"buraco", 1 ,gp.dialogueState);}
            else if(hit(0,23,12,"up") == true) {healingPool(23,12,gp.dialogueState);}
            else if(hit(0, 30, 37,"up") == true) {teleport(2,8,47, gp.dungeon);} // teleport DG
            else if(hit(2, 8, 48,"down") == true) {teleport(0,30,37, gp.outside);} // teleport out DG to world
            //else if(hit(2,20,26,"any") == true) {damagePit(2,20,26, "poço de lava", 1 ,gp.dialogueState);}
            else if(hit(1, 12, 9, "up") == true) {speak(gp.npc[1][0]);}
            
            else if(hit(0,10,39,"any") == true) {teleport(1, 12, 12, gp.indoor);} // to the merchant's
            else if(hit(1, 12, 13,"any") == true) {teleport(0,10,39, gp.outside);} // to outside
            else if(hit(0, 12, 9,"any") == true) {teleport(2,9,41, gp.dungeon);} // to the dungeon
            else if(hit(2, 9, 41,"any") == true) {teleport(0,12,9, gp.outside);} // to outside
            else if(hit(2, 8, 7,"any") == true) {teleport(3,26,41, gp.dungeon);} // to B2
            else if(hit(3, 26, 41,"any") == true) {teleport(2,8,7, gp.dungeon);} // to B1
        }
    }
    
    public boolean hit(int map, int col, int row, String reqDirection) {
        
        boolean hit = false;
        
        if(map == gp.currentMap) {
            gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
            gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
            eventRect[map][col][row].x = col * gp.tileSize + eventRect[map][col][row].x;
            eventRect[map][col][row].y = row * gp.tileSize + eventRect[map][col][row].y;

            if(gp.player.solidArea.intersects(eventRect[map][col][row]) && eventRect[map][col][row].eventDone == false) {
                if(gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
                    hit = true;

                    previousEventX = gp.player.worldX;
                    previousEventY = gp.player.worldY;
                }
            }

            gp.player.solidArea.x = gp.player.solidAreaDefaultX;
            gp.player.solidArea.y = gp.player.solidAreaDefaultY;
            eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
            eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;         
        }
        return hit;
    }

    public void damagePit(int map, int col, int row, String type, int damage, int gameState) {
        
        gp.gameState = gameState;
        gp.playSE(6);
        
        eventMaster.startDialogue(eventMaster, 0);
        gp.player.life -= damage;
        canTouchEvent = false;
    }
    
    public void healingPool(int col, int row, int gameState) {
        
        if(gp.keyH.enterPressed == true) {
            gp.gameState = gameState;
            gp.player.attackCanceled = true;
            gp.playSE(2);
            
            eventMaster.startDialogue(eventMaster, 1);
            gp.player.life = gp.player.maxLife;
            gp.player.mana = gp.player.maxMana;
            gp.saveLoad.save();
        }
    }
    
    public void teleport(int map, int col, int row, int area) {
        
        gp.gameState = gp.transitionState;
        gp.nextArea = area;
        tempMap = map;
        tempCol = col;
        tempRow = row;
        
        canTouchEvent = false;
        gp.playSE(13);
    }
    
    public void speak(Entity entity) {
        
        if(gp.keyH.enterPressed == true) {
            gp.gameState = gp.dialogueState;
            gp.player.attackCanceled = true;
            entity.speak();
        }
    }
}