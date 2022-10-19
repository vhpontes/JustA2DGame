package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity{
    
    KeyHandler keyH;
    public final int screenX;
    public final int screenY;
    int standCounter = 0;

    boolean moving = false;
    int pixelCounter = 0;
    
    public Player(GamePanel gp, KeyHandler keyH) {

        super(gp);
        this.keyH = keyH;
        
        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);
        
        solidArea = new Rectangle();
        solidArea.x = 1; //8
        solidArea.y = 1; //16
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 46; //32
        solidArea.height = 46; //32
        
        setDefaultValues();
        getPlayerImage();
    }
    
    public void setDefaultValues() {
        
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";
        
        // PLAYER STATUS
        maxLife = 6;
        life = maxLife;
    }
    public void getPlayerImage() {
        
        up1 = setup("player/boy_up_1");
        up2 = setup("player/boy_up_2");
        down1 = setup("player/boy_down_1");
        down2 = setup("player/boy_down_2");
        left1 = setup("player/boy_left_1");
        left2 = setup("player/boy_left_2");
        right1 = setup("player/boy_right_1");
        right2 = setup("player/boy_right_2");
    }
       
    public void update() {
        if(moving==false) {
            if(keyH.upPressed == true || keyH.downPressed == true || 
                    keyH.leftPressed == true || keyH.rightPressed == true) {

                if(keyH.upPressed == true) {
                    direction = "up";
                }
                else if(keyH.downPressed == true) {
                    direction = "down";
                }
                else if(keyH.leftPressed == true) {
                    direction = "left";
                }
                else if(keyH.rightPressed == true) {
                    direction = "right";
                }        
                
                moving = true;

                // CHECK TILE COLLISION
                collisionOn = false;
                gp.cChecker.checkTile(this);

                // CHECK OBJECT COLLISION
                int objIndex = gp.cChecker.checkObject(this, true);
                pickUpObject(objIndex);

                // CHECK NPC COLLISION
                int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
                interactNPC(npcIndex);
                
                // CHECK EVENT
                gp.eHandler.checkEvent();
                gp.keyH.enterPressed = false;
                
            }
            else {
                standCounter++;

                if(standCounter == 20){
                    spriteNum = 1;
                    standCounter = 0;
                }
            }            
        }
        
        // IF COLLISION IS FALSE, PLAYER CAN MOVE
        if(moving == true) {
            if(collisionOn == false) {
                switch(direction) {
                    case "up" -> worldY -= speed;
                    case "down" -> worldY += speed;
                    case "left" -> worldX -= speed;
                    case "right" -> worldX += speed;
                }
            }
            
            spriteCounter++;
            if(spriteCounter > 12) {
                if(spriteNum == 1) {
                    spriteNum = 2;
                }
                else if(spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
            pixelCounter += speed;
            
            
            // TILE BASE MOVEMENT (continue move until complete all 48 pixels) **more easy to gamer
            if(pixelCounter == 48) {
                moving = false;
                pixelCounter = 0;
            }
        }
    }
    
    public void pickUpObject(int i) {
        if(i != 999) {
        }
    }
    
    public void interactNPC(int i){
        if(i != 999) {
            if(gp.keyH.enterPressed == true) {
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
            }
        }        
    }
    
    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        
        switch(direction) {
            case "up" -> {
                if(spriteNum == 1){ image = up1;
                }
                if(spriteNum == 2){ image = up2;
                }
            }
            case "down" -> {
                if(spriteNum == 1){ image = down1;
                }
                if(spriteNum == 2){ image = down2;
                }
            }
            case "left" -> {
                if(spriteNum == 1){ image = left1;
                }
                if(spriteNum == 2){ image = left2;
                }
            }
            case "right" -> {
                if(spriteNum == 1){ image = right1;
                }
                if(spriteNum == 2){ image = right2;
                }
            }
        }
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        
        // RED RECTANGLE FOR VIEW COLLISION AREA
        // * comment line bellow for disable red rectangle
        //g2.setColor(Color.red);
        //g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
        
    }
    
}
