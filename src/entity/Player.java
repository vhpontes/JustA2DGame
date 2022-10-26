package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import main.GamePanel;
import main.KeyHandler;
import objects.OBJ_Boots;
import objects.OBJ_Fireball;
import objects.OBJ_Key;
import objects.OBJ_Rock;
import objects.OBJ_Shield_Wood;
import objects.OBJ_Sword_Normal;

public class Player extends Entity{
    
    KeyHandler keyH;
    public final int screenX;
    public final int screenY;
    int standCounter = 0;
    public boolean attackCanceled = false;
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorySize = 20;

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
        
//        attackArea.width = 36;
//        attackArea.height = 36;
        
        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
        setItems();
    }
    
    public void setDefaultValues() {
        
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";
        
        // PLAYER STATUS
        level = 1;
        maxLife = 6;
        life = maxLife;
        maxMana = 4;
        mana = maxMana;
        ammo = 10;
        strength = 1; // the more strength he has, the more damage he gives.
        dexterty = 1; // the more dexterty he has, the less damage he receives.
        exp = 0;
        nextLevelExp = 5;
        coin = 0;
        currentWeapon = new OBJ_Sword_Normal(gp);
        currentShield = new OBJ_Shield_Wood(gp);
        projectile = new OBJ_Fireball(gp);
//        projectile = new OBJ_Rock(gp);
        attack = getAttack();  // total attack value is decided by strength and weapon.
        defense = getDefense();// total defense value is decided by dexterty and shield.
    }

    public void setItems() {
        inventory.add(currentWeapon);
        inventory.add(currentShield);
    }
    
    public int getAttack() {
        attackArea = currentWeapon.attackArea;
        return attack = strength * currentWeapon.attackValue;
    }
    
    public int getDefense() {
        return defense = dexterty * currentShield.defenseValue;
    }
    
    public void getPlayerImage() {
        
        up1 = setup("player/boy_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("player/boy_up_2", gp.tileSize, gp.tileSize);
        down1 = setup("player/boy_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("player/boy_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("player/boy_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("player/boy_left_2", gp.tileSize, gp.tileSize);
        right1 = setup("player/boy_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("player/boy_right_2", gp.tileSize, gp.tileSize);
    }

    public void getPlayerAttackImage() {
        
        int imgDouble = gp.tileSize*2;
        
        if(currentWeapon.type == type_sword) {
            attackUp1    = setup("player/boy_attack_up_1",   gp.tileSize, imgDouble);
            attackUp2    = setup("player/boy_attack_up_2",   gp.tileSize, imgDouble);
            attackDown1  = setup("player/boy_attack_down_1", gp.tileSize, imgDouble);
            attackDown2  = setup("player/boy_attack_down_2", gp.tileSize, imgDouble);
            attackLeft1  = setup("player/boy_attack_left_1", imgDouble, gp.tileSize);
            attackLeft2  = setup("player/boy_attack_left_2", imgDouble, gp.tileSize);
            attackRight1 = setup("player/boy_attack_right_1",imgDouble, gp.tileSize);
            attackRight2 = setup("player/boy_attack_right_2",imgDouble, gp.tileSize);
        }
        if(currentWeapon.type == type_axe) {
            attackUp1    = setup("player/boy_axe_up_1",   gp.tileSize, imgDouble);
            attackUp2    = setup("player/boy_axe_up_2",   gp.tileSize, imgDouble);
            attackDown1  = setup("player/boy_axe_down_1", gp.tileSize, imgDouble);
            attackDown2  = setup("player/boy_axe_down_2", gp.tileSize, imgDouble);
            attackLeft1  = setup("player/boy_axe_left_1", imgDouble, gp.tileSize);
            attackLeft2  = setup("player/boy_axe_left_2", imgDouble, gp.tileSize);
            attackRight1 = setup("player/boy_axe_right_1",imgDouble, gp.tileSize);
            attackRight2 = setup("player/boy_axe_right_2",imgDouble, gp.tileSize);
        }
    }
       
    public void update() {
        
        if (attacking == true){
            attacking();
        }
        else if(keyH.upPressed == true || keyH.downPressed == true || 
                keyH.leftPressed == true || keyH.rightPressed == true 
                || keyH.enterPressed == true) {

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
            
            // CHECK INTERACTIVE TILE COLLISION
            int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
            contactMonster(monsterIndex);
            
            
            // CHECK EVENT
            gp.eHandler.checkEvent();
            //gp.keyH.enterPressed = false;
                
            // IF COLLISION IS FALSE, PLAYER CAN MOVE
            if(collisionOn == false && gp.keyH.enterPressed == false) {
                
                switch(direction) {
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
            }
            
            if(keyH.enterPressed == true && attackCanceled == false){
                gp.playSE(7);
                attacking = true;
                spriteCounter = 0;
            }
            
            attackCanceled = false;
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
        
        // PROJECTILE
        if(gp.keyH.shotKeyPressed == true && projectile.alive == false 
                && shotAvailableCounter == 30 && projectile.haveResource(this) == true) {
            
            //SET DEFAULT COORDINATES, DIRECTION AND USER
            projectile.set(worldX, worldY, direction, true, this);
            
            // SUBTRACT THE COST (MANA, AMMO, ETC.)
            projectile.subtractResource(this);
            
            // ADD IT TO THE LIST
            gp.projectileList.add(projectile);
            
            shotAvailableCounter = 0;
            
            gp.playSE(10);
        }
        
        // This needs to be outside of key if statement
        if(invincible == true) {
            invincibleCounter++;
            if(invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
        
        // prevent two fireball if close attack
        if(shotAvailableCounter < 30) {
            shotAvailableCounter++;
        }
        if(life > maxLife) {
            life = maxLife; 
        }
        if(mana > maxMana) {
            mana = maxMana; 
        }       
    }
    
    public void pickUpObject(int i) {
        
        if(i != 999) {
        // PICKUP ONLY ITEMS
            if(gp.obj[i].type == type_pickupOnly) {
                
                gp.obj[i].use(this);
                gp.obj[i] = null;
            }
            // INVENTORY ITEMS
            else {
                String text;

                if(inventory.size() != maxInventorySize) {

                    inventory.add(gp.obj[i]);
                    gp.playSE(1);
                    text = "Pickup a " + gp.obj[i].name + "!";
                }
                else {
                    text = "You cannot carry any more!";
                }
                gp.ui.addMessage(text);
                gp.obj[i] = null;
            }
        }
    }
    
    public void attacking(){
        
        spriteCounter++;
        
        if (spriteCounter <= 5) {
            spriteNum = 1;
        }
        if (spriteCounter > 5 && spriteCounter <=25){
            spriteNum = 2;
            
            // Save positions and areas
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;
            
            // Adjust player's worldX/Y for the attackArea
            switch(direction){
                case "up": worldY -= attackArea.height; break;
                case "down": worldY += attackArea.height; break;
                case "left": worldX -= attackArea.width; break;
                case "right": worldX += attackArea.width; break;
            }
            
            // attackArea -> solidArea
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;
            
            // Check monster collision with update worldX/Y and solidArea
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            damageMonster(monsterIndex, attack);
            
            int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
            damageInteractiveTile(iTileIndex, attack);
            
            // After checking collision, rollback values of worldX/Y and solidArea
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }
        
        if (spriteCounter > 25) {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }
    
    public void interactNPC(int i) {
        
        if(gp.keyH.enterPressed == true) {
            if(i != 999) {
                attackCanceled = true;
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
            }        
//            else {
//                gp.playSE(7);
//                attacking = true;
//            }            
        }
    }
    
    public void contactMonster(int i) {

        if(i != 999) {
            if(invincible == false && gp.monster[i].dying == false) {
                gp.playSE(6);

                int damage = attack - gp.monster[i].defense;
                if(damage < 0){
                    damage = 0;
                }
                life -= damage;
                invincible = true;
            }
        }
    }
    
    public void damageMonster(int i, int attack) {
        
        if(i != 999) {
             
            if(gp.monster[i].invincible == false) {
                 
                gp.playSE(5);
                
                int damage = attack - gp.monster[i].defense;
                if(damage < 0){
                    damage = 0;
                }
                gp.monster[i].life -= damage;
                gp.ui.addMessage(damage + "damage !");
                gp.monster[i].invincible = true;
                gp.monster[i].damageReaction();

                if(gp.monster[i].life <= 0) {
                    gp.monster[i].dying = true;
                    gp.ui.addMessage("killed the "+gp.monster[i].name);
                    gp.ui.addMessage("Exp + "+gp.monster[i].exp);
                    exp += gp.monster[i].exp;
                    checkLevelUp();
                }
             }
         }
    }
    
    public void damageInteractiveTile(int i, int attack) {
        
        if(i != 999 && gp.iTile[i].destructible == true 
                && gp.iTile[i].isCorrectItem(this) == true && gp.iTile[i].invincible == false) {
            
            gp.iTile[i].playeSE();
            gp.iTile[i].life--;
            gp.iTile[i].invincible = true;
            
            generateParticle(gp.iTile[i], gp.iTile[i]);
            
            if(gp.iTile[i].life == 0) {
                gp.iTile[i] = gp.iTile[i].getDestroyedForm();
            }
        }
    }
    
    public void checkLevelUp() {
        
        if(exp >= nextLevelExp) {
            
            level++;
            nextLevelExp = nextLevelExp*2;
            maxLife += 2;
            strength++;
            dexterty++;
            attack = getAttack();
            defense = getDefense();
            gp.playSE(8);
            gp.gameState = gp.dialogueState;
            gp.ui.currentDialogue = "You are level " + level + " now!\n" + "You feel stronger!";
        }
    }
  
    public void selectItem() {
        
        int itemIndex = gp.ui.getItemIndexOnSlot();
        
        if(itemIndex < inventory.size()) {
            
            Entity selectedItem = inventory.get(itemIndex);
            
            if(selectedItem.type == type_sword || selectedItem.type == type_axe) {
                
                currentWeapon = selectedItem;
                attack = getAttack();
                getPlayerAttackImage();
            }
            if(selectedItem.type == type_shield) {
                
                currentShield = selectedItem;
                defense = getDefense();
            }
            if(selectedItem.type == type_consumable) {
                
                selectedItem.use(this);
                inventory.remove(itemIndex);
            }
        }
    }
    
    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;
        
        switch(direction) {
            case "up": {
                if(attacking == false){
                    if(spriteNum == 1){ image = up1; break;}
                    if(spriteNum == 2){ image = up2; break;}
                }
                else {
                    tempScreenY = screenY - gp.tileSize;
                    if(spriteNum == 1){ image = attackUp1; break;}
                    if(spriteNum == 2){ image = attackUp2; break;}
                }
            }
            case "down": {
                if(attacking == false){
                    if(spriteNum == 1){ image = down1; break;}
                    if(spriteNum == 2){ image = down2; break;}
                }
                else {
                    if(spriteNum == 1){ image = attackDown1; break;}
                    if(spriteNum == 2){ image = attackDown2; break;}
                }
            }
            case "left": {
                if(attacking == false){
                    if(spriteNum == 1){ image = left1; break;}
                    if(spriteNum == 2){ image = left2; break;}
                }
                else {
                    tempScreenX = screenX - gp.tileSize;
                    if(spriteNum == 1){ image = attackLeft1; break;}
                    if(spriteNum == 2){ image = attackLeft2; break;}
                }
            }
            case "right": {
                if(attacking == false){
                    if(spriteNum == 1){ image = right1; break;}
                    if(spriteNum == 2){ image = right2; break;}
                }
                else {
                    if(spriteNum == 1){ image = attackRight1; break;}
                    if(spriteNum == 2){ image = attackRight2; break;}
                }
            }
        }
        // Visual Effect to invencible mode
        if(invincible ==true){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        }
//        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        g2.drawImage(image, tempScreenX, tempScreenY, null);
        
        // Reset alpha
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        
        // RED RECTANGLE FOR VIEW COLLISION AREA
        // * comment line bellow for disable red rectangle
//        g2.setColor(Color.red);
//        g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
        
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