/*
Code based in RyiSnow youtube channel:
https://www.youtube.com/c/RyiSnow
*/
 
package entity;

import java.awt.Rectangle;
import java.util.ArrayList;
import main.GamePanel;
import objects.OBJ_Door_Iron;
import tile_interactive.IT_MetalPlate;
import tile_interactive.InteractiveTile;

public class NPC_BigRock extends Entity{
    
    public static final String npcName = "Big Rock";
    
    public NPC_BigRock(GamePanel gp) {
        super(gp);
        
        name = npcName;
        direction = "down";
        speed = 4;

        solidArea = new Rectangle();
        solidArea.x = 2;
        solidArea.y = 6;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 44;
        solidArea.height = 40;
        
        dialogueSet = -1;
        
        getImage();
        setDialogue("en");
    }

    public void getImage() {
        
        up1 =    setup("npc/bigrock",    gp.tileSize, gp.tileSize);
        up2 =    up1;
        down1 =  up1;
        down2 =  up1;
        left1 =  up1;
        left2 =  up1;
        right1 = up1;
        right2 = up1;
    }
    
    public void setDialogue(String lang){
        switch(lang) {
            case "en" -> {
                dialogues[0][0] = "It's giant rock!";
            }
            case "br" -> {
                dialogues[0][0] = "Isso é uma rocha gigante!";
            }
        }
    }
    
    public void setAction(){

    }
    
    public void update() {
        
    }
    
    public void speak(){
        
        facePlayer();
        startDialogue(this, dialogueSet);
        
        dialogueSet++;
        
        if(dialogues[dialogueSet][0] == null) {

            dialogueSet--;
        }
        
    }
    
    public void move(String d) {
        
        this.direction = d;
        
        checkCollision();
        
        if(collisionOn == false) {
            switch(direction){
                case "up": worldY -= speed; break;
                case "down": worldY += speed; break;
                case "left": worldX -= speed; break;
                case "right": worldX += speed; break;
            }
        }
        
        detectPlate();
    }
    
    public void detectPlate() {
        
        ArrayList<InteractiveTile> plateList = new ArrayList<>();
        ArrayList<Entity> rockList = new ArrayList<>();
        
        for(int i = 0; i < gp.iTile[1].length; i++) {
            if(gp.iTile[gp.currentMap][i] != null &&
                    gp.iTile[gp.currentMap][i].name.equals(IT_MetalPlate.itName)) {
                plateList.add(gp.iTile[gp.currentMap][i]);
            }
        }
        
        for(int i = 0; i < gp.npc[1].length; i++) {
            if(gp.npc[gp.currentMap][i] != null &&
                    gp.npc[gp.currentMap][i].name.equals(NPC_BigRock.npcName)) {
                rockList.add(gp.npc[gp.currentMap][i]);
            }
        }
        
        int count = 0;
        
        // Scan the plate list
        for(int i = 0; i < plateList.size(); i++) {
            
            int xDistance = Math.abs(worldX - plateList.get(i).worldX);
            int yDistance = Math.abs(worldY - plateList.get(i).worldY);
            int distance = Math.max(xDistance, yDistance);
            
            if(distance < 8) {
                
                if(linkedEntity == null) {
                    linkedEntity = plateList.get(i);
                    gp.playSE(3);
                }
            }
            else {
                if(linkedEntity == plateList.get(i)) {
                    linkedEntity = null;
                }
            }
        }
        // Scan the rock list
        for(int i = 0; i < plateList.size(); i++) {
            
            if(rockList.get(i).linkedEntity != null) {
                count++;
            }
        } 
        
        if(count == rockList.size()) {
            
            for(int i = 0; i < gp.obj[1].length; i++) {
                
                if(gp.obj[gp.currentMap][i] != null && 
                        gp.obj[gp.currentMap][i].name.equals(OBJ_Door_Iron.objName)) {
                    
                    gp.obj[gp.currentMap][i] = null;
                    gp.playSE(22);
                }
            }
        }
    }
}
