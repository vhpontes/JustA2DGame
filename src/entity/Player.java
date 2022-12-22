/*
Code based in RyiSnow youtube channel:
https://www.youtube.com/c/RyiSnow

Class Code modifications and addons:
Victor Hugo Manata Pontes
https://www.twitch.tv/lechuck311
https://www.youtube.com/@victorhugomanatapontes
https://www.youtube.com/@dtudoumporco
*/

package entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import main.GamePanel;
import main.KeyHandler;
import main.MouseHandler;
import objects.OBJ_Arrow;
import objects.OBJ_Fireball;
import objects.OBJ_Key_Silver;
import objects.OBJ_Lantern;
import objects.OBJ_Shield_Wood;
import objects.OBJ_Sword_Normal;

public class Player extends Entity{
    
    KeyHandler keyH;
    MouseHandler mouseH;
    public final int screenX;
    public final int screenY;
    int standCounter = 0;
    public boolean attackCanceled = false;
    public boolean lightUpdated = false;
    public String imageHeroPlayer = "hero001";

    public Player(GamePanel gp, KeyHandler keyH, MouseHandler mouseH) {

        super(gp);
        this.keyH = keyH;
        this.mouseH = mouseH;
        
        this.screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        this.screenY = gp.screenHeight / 2 - (gp.tileSize / 2);
        
        this.solidArea = new Rectangle();
        this.solidArea.x = 8;
        this.solidArea.y = 16;
        this.solidAreaDefaultX = solidArea.x;
        this.solidAreaDefaultY = solidArea.y;
        this.solidArea.width = 32;
        this.solidArea.height = 32;
                
        setDefaultValues();
    }
    
    public void setInitialPosition() {

        gp.currentMap = 0;
        gp.gameState = gp.transitionState;
        
        switch(gp.currentMap){
            case 0: // WORLD MAP 1
                this.worldX = gp.tileSize * 23;
                this.worldY = gp.tileSize * 21;
                gp.nextArea = gp.outside;
                gp.currentArea = gp.nextArea;
            break;
            case 1: // HUT INTERIOR
                this.worldX = gp.tileSize * 12; 
                this.worldY = gp.tileSize * 12;
                gp.nextArea = gp.indoor;
                gp.currentArea = gp.nextArea;
            break;
            case 2: // DUNGEON 1
                //worldX = gp.tileSize * 8;
                //worldY = gp.tileSize * 48;
                this.worldX = gp.tileSize * 10;
                this.worldY = gp.tileSize * 41;
                gp.nextArea = gp.dungeon;
                gp.currentArea = gp.nextArea;
            break;
            case 3: // DUNGEON 2
                this.worldX = gp.tileSize * 26;
                this.worldY = gp.tileSize * 40;
                gp.nextArea = gp.dungeon;
                gp.currentArea = gp.nextArea;
            break;
            case 4: // ISLAND
                this.worldX = gp.tileSize * 20;
                this.worldY = gp.tileSize * 38;
                gp.nextArea = gp.outside;
                gp.currentArea = gp.nextArea;
            break;
            case 5: // HIGHLAND
                this.worldX = gp.tileSize * 40;
                this.worldY = gp.tileSize * 42;
                gp.nextArea = gp.outside;
                gp.currentArea = gp.nextArea;
            break;
            case 6: // TWITCH 1RENA 01
                this.worldX = gp.tileSize * 25;
                this.worldY = gp.tileSize * 25;
                gp.nextArea = gp.dungeon;
                gp.currentArea = gp.nextArea;
            break;
        } 
    }
    
    public void setDefaultValues() {

        setInitialPosition();
        this.defaultSpeed = 4 * 1; 
        this.speed = defaultSpeed;
        this.direction = "down";
        
        // PLAYER STATUS
        this.level = 1;
        this.maxLife = 6;
        this.life = maxLife;
        this.maxMana = 8;
        this.mana = maxMana;
        this.maxArrow = 0;
        this.arrow = maxArrow;
        this.ammo = 10;
        this.strength = 1; // the more strength he has, the more damage he gives.
        this.dexterty = 1; // the more dexterty he has, the less damage he receives.
        this.exp = 0;
        this.nextLevelExp = 5;
        this.coin = 500;
        this.currentWeapon    = new OBJ_Sword_Normal(gp);
        this.currentShield    = new OBJ_Shield_Wood(gp);
        this.projectile       = new OBJ_Fireball(gp, "fireball");
        this.projectileWeapow = new OBJ_Arrow(gp);
        this.currentLight     = new OBJ_Lantern(gp);
        //this.currentLight = null;
        this.attack = getAttack();  // total attack value is decided by strength and weapon.
        this.defense = getDefense();// total defense value is decided by dexterty and shield.
        
        getGuardImage();
        getImage();
        getAttackImage();
        setItems();
        setDialogue();
    }
    
    public void setDefaultPosition() {
        
        gp.currentMap = 0;
        this.worldX = gp.tileSize * 23;
        this.worldY = gp.tileSize * 21;
        this.direction = "down";
    }
    
    public void setDialogue() {
        
        dialogues[0][0] = "You are level " + level + " now!\n" 
            + "You feel stronger!";        
    }
    
    public void restoreStatus() {
        
        life = maxLife;
        mana = maxMana;
        arrow = maxArrow;
        speed = defaultSpeed;
        invincible = false;
        transparent = false;
        attacking = false;
        guarding = false;
        knockBack = false;
        lightUpdated = true;
    }

    public void setItems() {
        
        inventory.clear();
        inventory.add(currentWeapon);
        inventory.add(currentShield);
        inventory.add(new OBJ_Key_Silver(gp));
        inventory.add(new OBJ_Lantern(gp));
    }
    
    public int getAttack() {
        
        attackArea = currentWeapon.attackArea;
        motion1_duration = currentWeapon.motion1_duration;
        motion2_duration = currentWeapon.motion2_duration;        
        return attack = strength * currentWeapon.attackValue;
    }
    
    public int getDefense() {
        return defense = dexterty * currentShield.defenseValue;
    }
    
    public int getCurrentWeaponSlot() {
        int currentWeaponSlot = 0;
        for(int i=0; i < inventory.size(); i++) {
            if(inventory.get(i) == currentWeapon) {
                currentWeaponSlot = i;
            }
        }
        return currentWeaponSlot;
    }

    public int getCurrentShieldSlot() {
        int currentShieldSlot = 0;
        for(int i=0; i < inventory.size(); i++) {
            if(inventory.get(i) == currentShield) {
                currentShieldSlot = i;
            }
        }
        return currentShieldSlot;
    }
    
    public void getImage() {
        imageHeroPlayer = "man48";
        
        anim = setupGIF("player/"+imageHeroPlayer+"_anim",    gp.tileSize, gp.tileSize);
        
        up1    = setup("player/"+imageHeroPlayer+"_up_1",    gp.tileSize, gp.tileSize);
        up2    = setup("player/"+imageHeroPlayer+"_up_2",    gp.tileSize, gp.tileSize);
        up3    = setup("player/"+imageHeroPlayer+"_up_3",    gp.tileSize, gp.tileSize);
        up4    = setup("player/"+imageHeroPlayer+"_up_4",    gp.tileSize, gp.tileSize);
        down1  = setup("player/"+imageHeroPlayer+"_down_1",  gp.tileSize, gp.tileSize);
        down2  = setup("player/"+imageHeroPlayer+"_down_2",  gp.tileSize, gp.tileSize);
        down3  = setup("player/"+imageHeroPlayer+"_down_3",  gp.tileSize, gp.tileSize);
        down4  = setup("player/"+imageHeroPlayer+"_down_4",  gp.tileSize, gp.tileSize);
        left1  = setup("player/"+imageHeroPlayer+"_left_1",  gp.tileSize, gp.tileSize);
        left2  = setup("player/"+imageHeroPlayer+"_left_2",  gp.tileSize, gp.tileSize);
        left3  = setup("player/"+imageHeroPlayer+"_left_3",  gp.tileSize, gp.tileSize);
        left4  = setup("player/"+imageHeroPlayer+"_left_4",  gp.tileSize, gp.tileSize);
        right1 = setup("player/"+imageHeroPlayer+"_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("player/"+imageHeroPlayer+"_right_2", gp.tileSize, gp.tileSize);
        right3 = setup("player/"+imageHeroPlayer+"_right_3", gp.tileSize, gp.tileSize);
        right4 = setup("player/"+imageHeroPlayer+"_right_4", gp.tileSize, gp.tileSize);
        
        imageHeroPlayer = "hero001";
    }
    
    public void getSleepImage(BufferedImage image) {
        
        up1    = image;
        up2    = image;
        down1  = image;
        down2  = image;
        left1  = image;
        left2  = image;
        right1 = image;
        right2 = image;
    }

    public void getAttackImage() {
        
        int imgDouble = gp.tileSize * 2;
        
        if(currentWeapon.type == type_sword) {
            attackUp1    = setup("player/man48_attack_up_1",   gp.tileSize, imgDouble);
            attackUp2    = setup("player/man48_attack_up_2",   gp.tileSize, imgDouble);
            attackDown1  = setup("player/man48_attack_down_1", gp.tileSize, imgDouble);
            attackDown2  = setup("player/man48_attack_down_2", gp.tileSize, imgDouble);
            attackLeft1  = setup("player/man48_attack_left_1", imgDouble, gp.tileSize);
            attackLeft2  = setup("player/man48_attack_left_2", imgDouble, gp.tileSize);
            attackRight1 = setup("player/man48_attack_right_1",imgDouble, gp.tileSize);
            attackRight2 = setup("player/man48_attack_right_2",imgDouble, gp.tileSize);
        }
        if(currentWeapon.type == type_axe) {
            attackUp1    = setup("player/"+imageHeroPlayer+"_axe_up_1",   gp.tileSize, imgDouble);
            attackUp2    = setup("player/"+imageHeroPlayer+"_axe_up_2",   gp.tileSize, imgDouble);
            attackDown1  = setup("player/"+imageHeroPlayer+"_axe_down_1", gp.tileSize, imgDouble);
            attackDown2  = setup("player/"+imageHeroPlayer+"_axe_down_2", gp.tileSize, imgDouble);
            attackLeft1  = setup("player/"+imageHeroPlayer+"_axe_left_1", imgDouble, gp.tileSize);
            attackLeft2  = setup("player/"+imageHeroPlayer+"_axe_left_2", imgDouble, gp.tileSize);
            attackRight1 = setup("player/"+imageHeroPlayer+"_axe_right_1",imgDouble, gp.tileSize);
            attackRight2 = setup("player/"+imageHeroPlayer+"_axe_right_2",imgDouble, gp.tileSize);
        }
        if(currentWeapon.type == type_pickaxe) {
            attackUp1    = setup("player/"+imageHeroPlayer+"_pick_up_1",   gp.tileSize, imgDouble);
            attackUp2    = setup("player/"+imageHeroPlayer+"_pick_up_2",   gp.tileSize, imgDouble);
            attackDown1  = setup("player/"+imageHeroPlayer+"_pick_down_1", gp.tileSize, imgDouble);
            attackDown2  = setup("player/"+imageHeroPlayer+"_pick_down_2", gp.tileSize, imgDouble);
            attackLeft1  = setup("player/"+imageHeroPlayer+"_pick_left_1", imgDouble, gp.tileSize);
            attackLeft2  = setup("player/"+imageHeroPlayer+"_pick_left_2", imgDouble, gp.tileSize);
            attackRight1 = setup("player/"+imageHeroPlayer+"_pick_right_1",imgDouble, gp.tileSize);
            attackRight2 = setup("player/"+imageHeroPlayer+"_pick_right_2",imgDouble, gp.tileSize);
        }
        if(currentWeapon.type == type_bow) {
            attackUp1    = setup("player/"+imageHeroPlayer+"_bow_up_1",   gp.tileSize, imgDouble);
            attackUp2    = setup("player/"+imageHeroPlayer+"_bow_up_2",   gp.tileSize, imgDouble);
            attackDown1  = setup("player/"+imageHeroPlayer+"_bow_down_1", gp.tileSize, imgDouble);
            attackDown2  = setup("player/"+imageHeroPlayer+"_bow_down_2", gp.tileSize, imgDouble);
            attackLeft1  = setup("player/"+imageHeroPlayer+"_bow_left_1", imgDouble, gp.tileSize);
            attackLeft2  = setup("player/"+imageHeroPlayer+"_bow_left_2", imgDouble, gp.tileSize);
            attackRight1 = setup("player/"+imageHeroPlayer+"_bow_right_1",imgDouble, gp.tileSize);
            attackRight2 = setup("player/"+imageHeroPlayer+"_bow_right_2",imgDouble, gp.tileSize);
        }
    }
    
    public void getGuardImage() {
        
            guardUp    = setup("player/"+imageHeroPlayer+"_guard_up",    gp.tileSize, gp.tileSize);
            guardDown  = setup("player/"+imageHeroPlayer+"_guard_down",  gp.tileSize, gp.tileSize);
            guardLeft  = setup("player/"+imageHeroPlayer+"_guard_left",  gp.tileSize, gp.tileSize);
            guardRight = setup("player/"+imageHeroPlayer+"_guard_right", gp.tileSize, gp.tileSize);
    }
       
    @Override
    public void update() {
        
//        System.out.println("attacking? "+attacking);
//        System.out.println("attackCanceled? "+attackCanceled);
//        System.out.println("rangedweapow? "+rangedweapon);
//        System.out.println("enterPressed? "+keyH.enterPressed);
//        System.out.println("currentWeapon? "+gp.player.currentWeapon.type);
//        System.out.println("keyH.shiftPressed? "+keyH.shiftPressed);
//        System.out.println("guarding? "+guarding);
//        System.out.println("-------------------");
        if(this.knockBack == true) {

            // CHECK TILE COLLISION
            collisionOn = false;
            gp.cChecker.checkTile(this);
            gp.cChecker.checkObject(this, true);
            gp.cChecker.checkEntity(this, gp.npc);
            gp.cChecker.checkEntity(this, gp.monster);
            gp.cChecker.checkEntity(this, gp.iTile);
            
            if(this.collisionOn == true) {
                this.knockBackCounter = 0;
                this.knockBack = false;
                this.speed = this.defaultSpeed;
            }
            else if(this.collisionOn == false) {
                switch(this.knockBackDirection) {
                    case "up" -> this.worldY -= this.speed;
                    case "down" -> this.worldY += this.speed;
                    case "left" -> this.worldX -= this.speed;
                    case "right" -> this.worldX += this.speed;                    
                }
            }
            
            this.knockBackCounter++;
            if(this.knockBackCounter == 10) { // distance of knockback
                this.knockBackCounter = 0;
                this.knockBack = false;
                this.speed = this.defaultSpeed;
            }
        }
        // MELEE ATACK
        if (this.attacking == true && this.rangedweapon == false){
            attacking();
        }
        // RANGED ATACK
        if (this.rangedweapon == true && gp.cChecker.checkObject(this, true) == 999) {
            // PROJECTILE WEAPOW
            if(gp.keyH.enterPressed == true && projectileWeapow.alive == false 
                    && shotAvailableCounter == 30 && projectileWeapow.haveResource(this) == true) {

                //SET DEFAULT COORDINATES, DIRECTION AND USER
                this.projectileWeapow.set(this.worldX, this.worldY, this.direction, true, this);

                // SUBTRACT THE COST (MANA, AMMO, ETC.)
                this.projectileWeapow.subtractResource(this);

                // CHECK EMPTY SLOT PROJECTILE
                for(int i = 0; i < gp.projectileWeapow[1].length; i++) {
                    if(gp.projectileWeapow[gp.currentMap][i] == null) {
                        gp.projectileWeapow[gp.currentMap][i] = this.projectileWeapow;
                        break;
                    }
                }

                this.shotAvailableCounter = 0;

                gp.playSE(23);
            }
            this.attacking = false;
        }
        
        // DEFENSE MODE GUARDING (F PRESSED)
        if(keyH.shiftPressed == true) {
            this.guarding = true;
            this.guardCounter++;
        }
        // ANOTHER ACTIONS MADE WITH ENTER PRESSED
        else if(keyH.upPressed == true || keyH.downPressed == true || 
                keyH.leftPressed == true || keyH.rightPressed == true 
                || keyH.enterPressed == true) {

            if(keyH.upPressed == true) {this.direction = "up";}
            else if(keyH.downPressed == true) {this.direction = "down";}
            else if(keyH.leftPressed == true) {this.direction = "left";}
            else if(keyH.rightPressed == true) {this.direction = "right";}        

            // CHECK TILE COLLISION
            this.collisionOn = false;
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
                
            // IF COLLISION IS FALSE, PLAYER CAN MOVE
            if(collisionOn == false && gp.keyH.enterPressed == false) {
                
                switch(direction) {
                    case "up" -> this.worldY -= this.speed;
                    case "down" -> this.worldY += this.speed;
                    case "left" -> this.worldX -= this.speed;
                    case "right" -> this.worldX += this.speed;
                }
            }
            
            // CHECK IF MELEE OR RANGED WEAPOW
            if(keyH.enterPressed == true && this.attackCanceled == false){
                gp.playSE(7);
                this.attacking = true;
                this.spriteCounter = 0;
            }
            if(keyH.enterPressed == true && this.attackCanceled == false && this.rangedweapon == true) {
                this.attacking = true;
                this.spriteCounter = 0;
            }
    
            // RESET PLAYER STATES
            gp.keyH.enterPressed = false;
            this.attackCanceled = false;
            this.guarding = false;
            this.guardCounter = 0;
            
            if(this.canMove) {
                this.spriteCounter++;
                if(this.spriteCounter > 10) {
                    switch (this.spriteNum) {
                        case 1 -> this.spriteNum = 2;
                        case 2 -> this.spriteNum = 3;
                        case 3 -> this.spriteNum = 4;
                        case 4 -> this.spriteNum = 1;
                        default -> {
                        }
                    }
                    this.spriteCounter = 0;
                }
            } else {this.spriteNum = 1;}

//            spriteCounter++;
//            if(spriteCounter > 12) {
//                if(spriteNum == 1) {
//                    spriteNum = 2;
//                }
//                else if(spriteNum == 2) {
//                    spriteNum = 1;
//                }
//                spriteCounter = 0;
//            }
        }
        else {
            this.standCounter++;

            if(this.standCounter == 20){
                this.spriteNum = 1;
                this.standCounter = 0;
            }
            this.guarding = false;
            this.guardCounter = 0;
        }            
        
        // PROJECTILE FIREBALL
        if(gp.keyH.shotKeyPressed == true && this.projectile.alive == false 
                && this.shotAvailableCounter == 30 && this.projectile.haveResource(this) == true 
                && gp.player.attacking == false) {
            
            //SET DEFAULT COORDINATES, DIRECTION AND USER
            this.projectile.set(this.worldX, this.worldY, this.direction, true, this);
            
            // SUBTRACT THE COST (MANA, AMMO, ETC.)
            this.projectile.subtractResource(this);
            
            // ADD IT TO THE LIST
            //gp.projectileList.add(projectile);
            // CHECK EMPTY SLOT PROJECTILE
            for(int i = 0; i < gp.projectile[1].length; i++) {
                if(gp.projectile[gp.currentMap][i] == null) {
                    gp.projectile[gp.currentMap][i] = this.projectile;
                    break;
                }
            }
            
            this.shotAvailableCounter = 0;
            
            gp.playSE(10);
        }
        
        // This needs to be outside of key if statement
        if(this.invincible == true) {
            this.invincibleCounter++;
            if(this.invincibleCounter > 60) {
                this.invincible = false;
                this.transparent = false;
                this.invincibleCounter = 0;
            }
        }
        
        // prevent two fireball if close attack
        if(this.shotAvailableCounter < 30) {
            this.shotAvailableCounter++;
        }
        if(this.life > this.maxLife) {
            this.life = this.maxLife; 
        }
        if(this.mana > this.maxMana) {
            this.mana = this.maxMana; 
        }
        
        if(keyH.godModeOn == false) {
            // PLAYER GAME OVER
            if(this.life <= 0){
                gp.gameState = gp.gameOverState;
                gp.ui.commandNum = -1;
                gp.playSE(12);
            }            
        }
        //---------------------------------------------------------------------------
        // MOUSE CLICKED
        // PLAYER AUTO MOVE FOR MOUSE CLICK EVENT
        if(gp.mouseH.mouseLeftPressed && this.collisionOn == false && this.onPath == true) {
            gp.mouseH.setAction();
            this.checkPlayerCollision();
//            System.out.println("onPath:"+onPath);

            // CHECK OBJECT COLLISION
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);
            
            // CHECK EVENT
            gp.eHandler.checkEvent();            
            
            switch(this.direction) {
                case "up": this.worldY -= this.speed; break;
                case "down": this.worldY += this.speed; break;
                case "left": this.worldX -= this.speed; break;
                case "right": this.worldX += this.speed; break;
            }
            
            if(this.canMove) {
                this.spriteCounter++;
                if(this.spriteCounter > 10) {
                    switch (this.spriteNum) {
                        case 1 -> this.spriteNum = 2;
                        case 2 -> this.spriteNum = 3;
                        case 3 -> this.spriteNum = 4;
                        case 4 -> this.spriteNum = 1;
                        default -> {
                        }
                    }
                    this.spriteCounter = 0;
                }
            } else {this.spriteNum = 1;}            
        }        
    }
    
    public void pickUpObject(int i) {
        
        if(i != 999) {
            // PICKUP ONLY ITEMS
            if(gp.obj[gp.currentMap][i].type == type_pickupOnly) {
                
                gp.obj[gp.currentMap][i].use(this);
                gp.obj[gp.currentMap][i] = null;
            }
            // OBSTACLE (chest, etc...)
            else if(gp.obj[gp.currentMap][i].type == type_obstacle) {
                if(keyH.enterPressed == true) {
                    attackCanceled = true;
                    gp.obj[gp.currentMap][i].interact();
                }
            }
            // INVENTORY ITEMS
            else {
                String text;

                if(canObtainItem(gp.obj[gp.currentMap][i]) == true) {

                    gp.playSE(1);
                    text = "Pickup a " + gp.obj[gp.currentMap][i].name + "!";
                }
                else {
                    text = "You cannot carry any more!";
                }
                gp.ui.addMessage(text);
                gp.obj[gp.currentMap][i] = null;
            }
        }
    }
    
    public void interactNPC(int i) {
        
        if(i != 999) {
            
            if(gp.keyH.enterPressed == true) {
                attackCanceled = true;
                gp.npc[gp.currentMap][i].speak();
            }  
            
            gp.npc[gp.currentMap][i].move(direction);
        }
    }
    
    public void contactMonster(int i) {

        if(i != 999) {
            
            Entity mob = gp.monster[gp.currentMap][i];
            
            if(invincible == false && mob.dying == false) {
                gp.playSE(6);

                int damage = attack - mob.defense;
                if(damage < 1){
                    damage = 1;
                }
                life -= damage;
                invincible = true;
                transparent = true;
            }
        }
    }
    
    public void damageMonster(int i, Entity attacker, int attack, int knockBackPower) {
        
        if(i != 999) {
            
            Entity mob = gp.monster[gp.currentMap][i]; 
            
            if(mob.invincible == false) {
                 
                gp.playSE(5);
                
                if(knockBackPower > 0) {
                    setKnockBack(mob, attacker, knockBackPower);
                }
                
                if(mob.offBalance == true) {
                    attack *= 5;
                }
                
                int damage = attack - mob.defense;
                
                if(damage < 0){
                    damage = 0;
                }
                
                mob.life -= damage;
                gp.ui.addMessage(damage + " damage !");
                mob.invincible = true;
                mob.damageReaction();

                if(mob.life <= 0) {
                    mob.dying = true;
                    gp.ui.addMessage("killed the " + mob.name);
                    gp.ui.addMessage("Exp + " + mob.exp);
                    exp += mob.exp;
                    checkLevelUp();
                }
             }
         }
    }
    
    public void damageInteractiveTile(int i, int attack) {
        
        if(i != 999 && gp.iTile[gp.currentMap][i].destructible == true 
                && gp.iTile[gp.currentMap][i].isCorrectItem(this) == true && gp.iTile[gp.currentMap][i].invincible == false) {
            
            gp.iTile[gp.currentMap][i].playeSE();
            gp.iTile[gp.currentMap][i].life--;
            gp.iTile[gp.currentMap][i].invincible = true;
            
            generateParticle(gp.iTile[gp.currentMap][i], gp.iTile[gp.currentMap][i]);
            
            if(gp.iTile[gp.currentMap][i].life == 0) {
                gp.iTile[gp.currentMap][i].checkDrop(gp.iTile[gp.currentMap][i].worldX, gp.iTile[gp.currentMap][i].worldY);
                gp.iTile[gp.currentMap][i] = gp.iTile[gp.currentMap][i].getDestroyedForm();
            }
        }
    }
    
    public void damageProjectile(int i) {
        
        if(i != 999) {
            Entity projectile = gp.projectile[gp.currentMap][i];
            projectile.alive = false;
            generateParticle(projectile, projectile);
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

            setDialogue(); // force update level variable
            startDialogue(this, 0);
        }
    }
  
    public void selectItem() {
        
        int itemIndex = gp.ui.getItemIndexOnSlot(gp.ui.playerSlotCol, gp.ui.playerSlotRow);
        
        if(itemIndex < inventory.size()) {
            
            Entity selectedItem = inventory.get(itemIndex);
            
//            if(selectedItem.type == type_sword || selectedItem.type == type_axe 
//                    || selectedItem.type == type_pickaxe || selectedItem.type == type_bow) {
            if(selectedItem.handObject == true) {
                
                rangedweapon = false;
                currentWeapon = selectedItem;
                attack = getAttack();
                getAttackImage();
            }
            if(selectedItem.type == type_shield) {
                
                currentShield = selectedItem;
                defense = getDefense();
            }
            if(selectedItem.type == type_light) {
                if(currentLight == selectedItem) {
                    currentLight = null;
                }
                else {
                    currentLight = selectedItem;
                }
                lightUpdated = true;
            }
            if(selectedItem.type == type_consumable) {
                
                if(selectedItem.use(this) == true) {
                    if(selectedItem.amount > 1) {
                        selectedItem.amount--; 
                    }
                    else {
                        inventory.remove(itemIndex);
                    }
                }
            }
            if(selectedItem.type == type_bow) {
                rangedweapon = true;
            }
        }
    }
    
    public int searchItemInventory(String itemName) {
        
        int itemIndex = 999;
        
        for(int i = 0; i < inventory.size(); i++) {
            if(inventory.get(i).name.equals(itemName)) {
                itemIndex = i;
                break;
            }
        }
        return itemIndex;
    }
    
    public boolean canObtainItem(Entity item) {
        
        boolean canObtain = false;
        
        Entity newItem = gp.eGenerator.getObject(item.name);
        
        // Check if Stackable
        if(newItem.stackable == true) {
            
            int index = searchItemInventory(newItem.name);
            
            if(index != 999) {
                inventory.get(index).amount++;
                if(newItem.name == "Arrow") {
                    this.arrow++;
                }
                canObtain = true;
            }
            else {
                if(inventory.size() != maxInventorySize) {
                    inventory.add(newItem);
                    if(newItem.name == "Arrow") {
                        this.arrow++;
                    }
                    canObtain = true;
                }
            }
        }
        else {
            if(inventory.size() != maxInventorySize) {
                inventory.add(newItem);
                canObtain = true;
            }
        }
        
        return canObtain;
    }
    
    @Override
    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        switch(direction) {
            case "up":
                if(attacking == false) {
                    if(spriteNum == 1){ image = up1;}
                    if(spriteNum == 2){ image = up2;}
                    if(spriteNum == 3){ image = up3;}
                    if(spriteNum == 4){ image = up4;}
                }
                if(attacking == true) {
                    tempScreenY = screenY - gp.tileSize;
                    if(spriteNum == 1){ image = attackUp1;}
                    if(spriteNum == 2){ image = attackUp2;}
                }
                if(guarding == true) {
                    image = guardUp;
                }
                break;
            case "down":
                if(attacking == false) {
                    if(spriteNum == 1){ image = down1;}
                    if(spriteNum == 2){ image = down2;}
                    if(spriteNum == 3){ image = down3;}
                    if(spriteNum == 4){ image = down4;}
                }
                if(attacking == true) {
                    if(spriteNum == 1){ image = attackDown1;}
                    if(spriteNum == 2){ image = attackDown2;}
                }
                if(guarding == true) {
                    image = guardDown;
                }            
                break;
            case "left":
                if(attacking == false) {
                    if(spriteNum == 1){ image = left1;}
                    if(spriteNum == 2){ image = left2;}
                    if(spriteNum == 3){ image = left3;}
                    if(spriteNum == 4){ image = left4;}
                }
                if(attacking == true) {
                    tempScreenX = screenX - gp.tileSize;
                    if(spriteNum == 1){ image = attackLeft1;}
                    if(spriteNum == 2){ image = attackLeft2;}
                }
                if(guarding == true) {
                    image = guardLeft;
                }            
                break;
            case "right":
                if(attacking == false) {
                    if(spriteNum == 1){ image = right1;}
                    if(spriteNum == 2){ image = right2;}
                    if(spriteNum == 3){ image = right3;}
                    if(spriteNum == 4){ image = right4;}
                }
                if(attacking == true) {
                    if(spriteNum == 1){ image = attackRight1;}
                    if(spriteNum == 2){ image = attackRight2;}
                }
                if(guarding == true) {
                    image = guardRight;
                }            
                break;
        }
        
        if(tempScreenX > worldX) {
            tempScreenX = worldX;
        }
        if(tempScreenY > worldY) {
            tempScreenY = worldY;
        }
        int rightOffset = gp.screenWidth - screenX;
        if(rightOffset > gp.worldWidth - worldX) {
            tempScreenX = gp.screenWidth - (gp.worldWidth - worldX);
        }
        int bottonOffset = gp.screenHeight - screenY;
        if(bottonOffset > gp.worldHeight - worldY) {
            tempScreenY = gp.screenHeight - (gp.worldHeight - worldY);
        }  
        
        // Visual Effect to invencible mode
        if(transparent == true){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        }
        if(drawing == true){
    //        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            g2.drawImage(image, tempScreenX, tempScreenY, null);
        }
        
        // Reset alpha
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        
        // RED RECTANGLE FOR VIEW COLLISION AREA
        // * comment line bellow for disable red rectangle
//        g2.setColor(new Color(255, 0, 0, 70));
//        g2.fillRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
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