package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
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

//    boolean moving = false;
//    int pixelCounter = 0;
    
    public Player(GamePanel gp, KeyHandler keyH) {

        super(gp);
        this.keyH = keyH;
        
        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);
        
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;
        
        setDefaultValues();
        getPlayerImage();
    }
    
    public void setDefaultValues() {
        
//        worldX = gp.tileSize * 10;
//        worldY = gp.tileSize * 13;
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
        
        if(keyH.upPressed == true || keyH.downPressed == true || 
                keyH.leftPressed == true || keyH.rightPressed == true) {

            if(keyH.upPressed == true) {direction = "up";}
            else if(keyH.downPressed == true) {direction = "down";}
            else if(keyH.leftPressed == true) {direction = "left";}
            else if(keyH.rightPressed == true) {direction = "right";}        

            // CHECK TILE COLLISION
            collisionOn = false;
            gp.cChecker.checkTile(this);

            // CHECK OBJECT COLLISION
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            // CHECK NPC COLLISION
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            // CHECK MONSTER COLLISION
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);
            
            // CHECK EVENT
            gp.eHandler.checkEvent();
            gp.keyH.enterPressed = false;
                
            // IF COLLISION IS FALSE, PLAYER CAN MOVE
            if(collisionOn == false && gp.keyH.enterPressed == false) {
                
                switch(direction) {
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
            }
            
            gp.keyH.enterPressed = false;
            
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
        }
        else {
            standCounter++;

            if(standCounter == 20){
                spriteNum = 1;
                standCounter = 0;
            }
        }            
        
        // This needs to be outside of key if statement
        if(invincible == true) {
            invincibleCounter++;
            if(invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }
    
    public void pickUpObject(int i) {
        
        if(i != 999) {
        }
    }
    
    public void interactNPC(int i) {
        
        if(i != 999) {
            if(gp.keyH.enterPressed == true) {
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
            }
        }        
    }
    
    public void contactMonster(int i) {

        if(i != 999) {
            if(invincible == false) {
//                life -= gp.monster[i].damage;
                life -= 1;
                invincible = true;
            }
        }
    }    
  
    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        
        switch(direction) {
            case "up": {
                if(spriteNum == 1){ image = up1; break;}
                if(spriteNum == 2){ image = up2; break;}
            }
            case "down": {
                if(spriteNum == 1){ image = down1; break;}
                if(spriteNum == 2){ image = down2; break;}
            }
            case "left": {
                if(spriteNum == 1){ image = left1; break;}
                if(spriteNum == 2){ image = left2; break;}
            }
            case "right": {
                if(spriteNum == 1){ image = right1; break;}
                if(spriteNum == 2){ image = right2; break;}
            }
        }
        // Visual Effect to invencible mode
        if(invincible ==true){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        }
//        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        g2.drawImage(image, screenX, screenY, null);
        
        // Reset alpha
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        
        // RED RECTANGLE FOR VIEW COLLISION AREA
        // * comment line bellow for disable red rectangle
        g2.setColor(Color.red);
        g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
        
        // DEBUG
//        Color c = new Color(0,0,0,150);
//        g2.setColor(c);
//        g2.fillRoundRect(5, 370, 135, 40, 5, 5);
//
//        g2.setFont(new Font("Arial", Font.PLAIN, 26));
//        g2.setColor(Color.black);
//        g2.drawString("Invincible:"+invincibleCounter, 12, 402);
//        g2.setColor(Color.white);
//        g2.drawString("Invincible:"+invincibleCounter, 10, 400);
        
    }
    
}
