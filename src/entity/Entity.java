/*
Code based in RyiSnow youtube channel:
https://www.youtube.com/c/RyiSnow
*/
 
package entity;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import main.GamePanel;
import main.UtilityTool;

public class Entity {
    
    public GamePanel gp;
    public BufferedImage 
            up1, up2, up3, up4, 
            down1, down3, down4, down2, 
            left1, left2, left3, left4, 
            right1, right2, right3, right4;
    public BufferedImage 
            attackUp1, attackUp2, 
            attackDown1, attackDown2, 
            attackLeft1, attackLeft2, 
            attackRight1, attackRight2;
    public BufferedImage 
            guardUp, guardDown, 
            guardLeft, guardRight;
    public ImageIcon 
            anim;
    public BufferedImage image, image2, image3;
    public Rectangle solidArea = new Rectangle(0,0,48,48);
    public Rectangle attackArea = new Rectangle(0,0,0,0);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public String dialogues[][] = new String[20][20];
    public Entity attacker;
    public Entity linkedEntity;
    
    // VAR NPC TWITCH
    public static final int TWITCH_MESSAGE_MAXSCREEN_TIME = 6;
    public String npcTwitchNick = null;
    public String npcTwitchMessage = null;
    public int npcHashCode = 0;
    public long messageTwitchTimeStamp = 0;
    public boolean npcFireballLaunched = false;

    // VARs STATE
    public int dialogueSet = 0;
    public int dialogueIndex = 0;
    public int worldX, worldY;
    public int spriteNum = 1;
    public String knockBackDirection;
    public boolean hpBarOn = false;
    public boolean collision = false;
    public boolean collisionOn = false;
    public boolean invincible = false;
    public boolean attacking = false;
    public boolean rangedweapon = false;
    public boolean alive = true;
    public boolean dying = false;
    public boolean onPath = false;
    public boolean knockBack = false;
    public boolean guarding = false;
    public boolean transparent = false;
    public boolean offBalance = false;
    public Entity loot;
    public boolean opened = false;
    public boolean inRage = false;
    public boolean canMove = true;
    public boolean boss = false;

    // VARs COUNTER
    public int spriteCounter = 0;
    public int actionLockCounter = 0;
    public int invincibleCounter = 0;
    public int shotAvailableCounter = 0;
    public int guardCounter = 0;
    int dyingCounter = 0;
    public int hpBarCounter = 0;
    int knockBackCounter = 0;
    int offBalanceCounter = 0;
    

    // VARs CHARACTER ATTRIBUTES
    public String name;
    public String direction = "down";
    public int defaultSpeed;
    public int speed;
    public int maxLife;
    public int life;
    public int maxMana;
    public int mana;
    public int maxArrow;
    public int arrow;
    public int ammo;
    public int damage = 0;
    public int level;
    public int strength;
    public int dexterty;
    public int attack;
    public int defense;
    public int exp;
    public int nextLevelExp;
    public int coin;
    public int motion1_duration;
    public int motion2_duration;
    public Entity currentWeapon;
    public Entity currentShield;
    public Entity currentLight;
    public Projectile projectile;
    public Projectile projectileWeapow;
    
    // ITEM ATTRIBUTES
    public ArrayList<Entity> inventory = new ArrayList<>();
    public String description;
    public final int maxInventorySize = 20;
    public int value;
    public int attackValue;
    public int defenseValue;
    public int useCost;
    public int price;
    public int knockBackPower = 0;
    public int amount = 1;
    public int lightRadius;
    public boolean stackable = false;
    public boolean handObject = false;
    public int numDrop;

    // TYPE
    public int type; //0 = player, 1 = npc, 2 = monster
    public final int type_player = 0;
    public final int type_npc = 1;
    public final int type_monster = 2;
    public final int type_sword = 3;
    public final int type_axe = 4;
    public final int type_shield = 5;
    public final int type_consumable = 6;
    public final int type_pickupOnly = 7;
    public final int type_obstacle = 8;
    public final int type_light = 9;
    public final int type_pickaxe = 10;
    public final int type_bow = 11;
    public final int type_npcTwitch = 12;
    
    public Entity(GamePanel gp) {
        this.gp = gp;
    }
    
    public int getScreenX() {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        return screenX;
    }

    public int getScreenY() {
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        return screenY;
    }
    
    public int getLeftX() {
        return worldX + solidArea.x;
    }
    
    public int getRightX() {
        return worldX + solidArea.x + solidArea.width;
    }
    
    public int getTopY() {
        return worldY + solidArea.y;
    }
    
    public int getBottomY() {
        return worldY + solidArea.y + solidArea.height;
    }
    
    public int getCol() {
        return (worldX + solidArea.x)/gp.tileSize;
    }

    public int getRow() {
        return (worldY + solidArea.y)/gp.tileSize;
    }
    
    public int getCenterX() {
        
        int centerX = worldX + left1.getWidth()/2;
        return centerX;
    }

    public int getCenterY() {
        int centerY = worldY + up1.getHeight()/2;
        return centerY;
    }
    
    public int getXDistance(Entity target) {
        
        int xDistance =  Math.abs(getCenterX() - target.getCenterX()); 
        return xDistance;
    }

    public int getYDistance(Entity target) {
        
        int yDistance =  Math.abs(getCenterY() - target.getCenterY()); 
        return yDistance;
    }
    
    public int getTileDistance(Entity target) {
        
        int tileDistance =  ((getXDistance(target) + getYDistance(target)) / gp.tileSize);
        return tileDistance;
    }
    
    public int getGoalCol(Entity target) {
        
        int goalCol = (target.worldX + target.solidArea.x) / gp.tileSize;
        return goalCol;
    }
    
    public int getGoalRow(Entity target) {
        
        int goalRow = (target.worldY + target.solidArea.y) / gp.tileSize;
        return goalRow;
    }
    
    public void resetCounter() {

        spriteCounter = 0;
        actionLockCounter = 0;
        invincibleCounter = 0;
        shotAvailableCounter = 0;
        guardCounter = 0;
        dyingCounter = 0;
        hpBarCounter = 0;
        knockBackCounter = 0;
        offBalanceCounter = 0;        
    }
    
    public void setLoot(Entity loot) {
        
    }

    public void setLootItem(Entity loot, int lootAmount) {
        
    }
    public void setLootItem(Entity loot) {
        setLootItem(loot, 1);
    }

    public void setAction(){}
    
    public void move(String direction) {
        
    }
    
    public void damageReaction() {}
    
    public void speak() {}
    
    public void facePlayer() {
        switch(gp.player.direction) {
            case "up": direction = "down"; break;
            case "down": direction = "up"; break;
            case "left": direction = "right"; break;
            case "right": direction = "left"; break;
        }        
    }
    
    public void startDialogue(Entity entity, int setNum) {
        
        gp.gameState = gp.dialogueState;
        gp.ui.npc = entity;
        dialogueSet = setNum;
    }

    public void drawTwitcChatDialogue(Graphics2D g2, int screenX, int screenY) {
        
        // DRAW Twitch Message
        if(this.npcTwitchMessage != null && !this.npcTwitchMessage.equals("!new")) {
            int width = this.npcTwitchMessage.length();
            int padding = 3;
            
            int x = screenX + gp.tileSize;
            int y = screenY-gp.tileSize;
            
            g2.setFont(g2.getFont().deriveFont(24f));
            
            // draw a backgroud of npc twitch chat 
            Rectangle bounds = g2.getFontMetrics().getStringBounds(this.npcTwitchMessage, g2).getBounds();
            Color c = new Color(0,0,0,170);
            g2.setColor(c);
            g2.fillRoundRect(x - padding, y - padding, bounds.width + padding, bounds.height + padding, 5, 5);
            
            // draw a border of backgroud
            c = new Color(255,255,255); //white
            g2.setColor(c);
            g2.setStroke(new BasicStroke(1));
            g2.drawRoundRect(x - padding, y - padding, bounds.width + padding, bounds.height + padding, 5, 5);            
            
            // draw a string line (message) with shadow
            g2.setColor(Color.black);
            g2.drawString(this.npcTwitchMessage, x + 2, y + 23);
            g2.setColor(Color.green);
            g2.drawString(this.npcTwitchMessage, x, y + 21);
        }
    }    
    
    public void interact() {
        
    }
    
    public boolean use(Entity entity) {
        return false;
    }
    
    public void checkDrop(int X, int Y) {
        
    }
    
    public void dropItem(Entity droppedItem, int wX, int wY) {
    //public void dropItem(Entity droppedItem) {
        
        for(int i = 0; i < gp.obj[1].length; i++) {
            if(gp.obj[gp.currentMap][i] == null) {
//                if(this.worldX == null || worldY == null) {
//                    worldX = wX;
//                    worldY = wY;
//                }
                gp.obj[gp.currentMap][i] = droppedItem;
                gp.obj[gp.currentMap][i].worldX = worldX + wX; // the dead monster's worldX
                gp.obj[gp.currentMap][i].worldY = worldY + wY; // the dead monster's worldY
                break;
            }
        }
    }
    
    public Color getParticleColor() {
        Color color = null;
        return color;
    }
    
    public int getParticleSize() {
        int size = 0; // pixels
        return size;
    }
    
    public int getParticleSpeed() {
        int speed = 0;
        return speed;
    }
    
    public int getMaxLife() {
        int maxLife = 0; // frames count of particle
        return maxLife;
    }    
    
    public void generateParticle(Entity generator, Entity target) {
        
        Color color = generator.getParticleColor();
        int size = generator.getParticleSize();
        int speed = generator.getParticleSpeed();
        int maxLife = generator.getMaxLife();
        
        Particle p1 = new Particle(gp, target, color, size, speed, maxLife, -2, -1);
        Particle p2 = new Particle(gp, target, color, size, speed, maxLife, 2, -1);
        Particle p3 = new Particle(gp, target, color, size, speed, maxLife, -2, 1);
        Particle p4 = new Particle(gp, target, color, size, speed, maxLife, 2, 1);
        gp.particleList.add(p1);
        gp.particleList.add(p2);
        gp.particleList.add(p3);
        gp.particleList.add(p4);
    }
    
    public void generateFirework(Entity generator, Entity target, int pX, int pY, Color c1, Color c2, Color c3) {
        
//        int size = generator.getParticleSize();
//        int speed = generator.getParticleSpeed();
        int size = 6;
        int speed = 2;
        int maxLife = 80; //15
        
        gp.playSE(14);
        
//        Firework f1 = new Firework(gp, target, size, speed, maxLife, -2, -1);
//        Firework f2 = new Firework(gp, target, size, speed, maxLife, 2, -1);
//        Firework f3 = new Firework(gp, target, size, speed, maxLife, -2, 1);
//        Firework f4 = new Firework(gp, target, size, speed, maxLife, 2, 1);
        
//        gp.fireworkList.add(f1);
//        gp.fireworkList.add(f2);
//        gp.fireworkList.add(f3);
//        gp.fireworkList.add(f4);

        gp.fireworkList.add(new Firework(gp, target, pX, pY, size, speed, maxLife, 0, 2, c1));
        gp.fireworkList.add(new Firework(gp, target, pX, pY, size, speed, maxLife, 0, -2, c1));
        gp.fireworkList.add(new Firework(gp, target, pX, pY, size, speed, maxLife, 2, 0, c1));
        gp.fireworkList.add(new Firework(gp, target, pX, pY, size, speed, maxLife, -2, 0, c1));

        gp.fireworkList.add(new Firework(gp, target, pX, pY, size/2, speed/2, maxLife, 0, 2, c2));
        gp.fireworkList.add(new Firework(gp, target, pX, pY, size/2, speed/2, maxLife, 0, -2, c2));
        gp.fireworkList.add(new Firework(gp, target, pX, pY, size/2, speed/2, maxLife, 2, 0, c2));
        gp.fireworkList.add(new Firework(gp, target, pX, pY, size/2, speed/2, maxLife, -2, 0, c2));

        gp.fireworkList.add(new Firework(gp, target, pX, pY, size, speed/2, maxLife/2, 2, 2, c1));
        gp.fireworkList.add(new Firework(gp, target, pX, pY, size, speed/2, maxLife/2, 2, -2, c1));
        gp.fireworkList.add(new Firework(gp, target, pX, pY, size, speed/2, maxLife/2, -2, 2, c1));
        gp.fireworkList.add(new Firework(gp, target, pX, pY, size, speed/2, maxLife/2, -2, -2, c1));

        gp.fireworkList.add(new Firework(gp, target, pX, pY, size/2, speed/2, maxLife/2, 2, 1, c3));
        gp.fireworkList.add(new Firework(gp, target, pX, pY, size/2, speed/2, maxLife/2, 2, -1, c3));
        gp.fireworkList.add(new Firework(gp, target, pX, pY, size/2, speed/2, maxLife/2, -2, 1, c3));
        gp.fireworkList.add(new Firework(gp, target, pX, pY, size/2, speed/2, maxLife/2, -2, -1, c3));
        
        gp.fireworkList.add(new Firework(gp, target, pX, pY, size/2, speed/2, maxLife/2, 1, 2, c3));
        gp.fireworkList.add(new Firework(gp, target, pX, pY, size/2, speed/2, maxLife/2, 1, -2, c3));
        gp.fireworkList.add(new Firework(gp, target, pX, pY, size/2, speed/2, maxLife/2, -1, 2, c3));
        gp.fireworkList.add(new Firework(gp, target, pX, pY, size/2, speed/2, maxLife/2, -1, -2, c3));
    }

    public void makeFireworkShow(int fireworksQuantity) {
        Random rX = new Random();
        Random rY = new Random();
        Random timeBetween = new Random();
        Random colorPatern = new Random();

        Color c1 = Color.WHITE;
        Color c2 = Color.WHITE;
        Color c3 = Color.WHITE;

        for(int i=0; i < fireworksQuantity; i++) {
            int fX = rX.nextInt(600, 1200);
            int fY = rY.nextInt(1400, 1600);

            switch(colorPatern.nextInt(0, 4)){
                case 0 -> { 
                    c1 = Color.decode("#DE5BF8");
                    c2 = Color.decode("#1738B7");
                    c3 = Color.decode("#EFE8FF");
                }
                case 1 -> {
                    c1 = Color.decode("#b7171f");
                    c2 = Color.decode("#ce5072");
                    c3 = Color.decode("#f4bac9");
                }
                case 2 -> {
                    c1 = Color.decode("#0B2D6A");
                    c2 = Color.decode("#C99C9F");
                    c3 = Color.decode("#C11E4B");
                }
                case 3 -> {
                    c1 = Color.decode("#5981B1");
                    c2 = Color.decode("#15273C");
                    c3 = Color.decode("#98BAE3");
                }
                case 4 -> {
                    c1 = Color.decode("#008631");
                    c2 = Color.decode("#5CED73");
                    c3 = Color.decode("#CEFAD0");
                }
            }

            this.generateFirework(this.projectile, null, fX, fY, c1, c2, c3);
            //System.out.println(fX+"-"+fY);
            try {
                //TimeUnit.SECONDS.sleep(1);
                TimeUnit.MILLISECONDS.sleep(timeBetween.nextInt(100, 700));
            } catch (InterruptedException ex) {
                System.out.println(ex);
            }
        }
    }    
    
    public void checkCollision() {
        // CHECK TILE NPC COLLISION
        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkEntity(this, gp.monster);
        gp.cChecker.checkEntity(this, gp.iTile);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);
        
        if(this.type == type_monster && contactPlayer == true) {
            damagePlayer(attack);
        }        
    }

    public void checkPlayerCollision() {
        // CHECK TILE NPC COLLISION
        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkEntity(this, gp.monster);
        gp.cChecker.checkEntity(this, gp.iTile);
    }
    
    public void update(){
        
        if(knockBack == true) {
            
            checkCollision();
            
            if(collisionOn == true) {
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            }
            else if(collisionOn == false) {
                switch(knockBackDirection) {
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;                    
                }
            }
            
            knockBackCounter++;
            if(knockBackCounter == 10) { // distance of knockback
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            }
        }
        else if(attacking == true) {
            attacking();
        }
        else {
            setAction();
            checkCollision();

            // IF COLLISION IS FALSE, NPC CAN MOVE
            if(collisionOn == false) {
                switch(direction) {
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
            }
            
            if(this.canMove) {
                spriteCounter++;
                if(spriteCounter > 10) {
                    if(spriteNum == 1) {
                        spriteNum = 2;
                    }
                    else if(spriteNum == 2) {
                        spriteNum = 3;
                    }
                    else if(spriteNum == 3) {
                        spriteNum = 4;
                    }
                    else if(spriteNum == 4) {
                        spriteNum = 1;
                    }
                    spriteCounter = 0;
                }
            } else {spriteNum = 1;}
        }


        // NPC TWITCH FIREBALL
        if(this.npcFireballLaunched == true && projectile.alive == false && shotAvailableCounter == 30) {

            System.out.println("Fireball launched by " + this.npcTwitchNick);
            //SET DEFAULT COORDINATES, DIRECTION AND USER
            projectile.set(worldX, worldY, direction, true, this);  

            System.out.println(
                    worldX + ":" + 
                    worldY + " - " + 
                    direction);

            for(int i=0; i < gp.projectile[1].length; i++) {
                if(gp.projectile[gp.currentMap][i] == null) {
                    gp.projectile[gp.currentMap][i] = projectile;
                    break;
                }
            }
            
            shotAvailableCounter = 0;
            
            gp.playSE(10);
        }
        
        if(invincible == true) {
            invincibleCounter++;
            if(invincibleCounter > 40) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
        
        // prevent two fireball if close attack
        if(shotAvailableCounter < 30) {
            shotAvailableCounter++;
        }
        else {
            this.npcFireballLaunched = false;
            attacking = false;
        }
        
        if(offBalance == true) {
            offBalanceCounter++;
            if(offBalanceCounter > 60) {
                offBalance = false;
                offBalanceCounter = 0;
            }
        }
    }
    
    public void checkAttackOrNot(int rate, int straight, int horizontal) {
        
        boolean targetInRange = false;
        int xDis = getXDistance(gp.player);
        int yDis = getYDistance(gp.player);
        
        switch(direction) {
            case "up":
                if(gp.player.getCenterY() < getCenterY() && yDis < straight && xDis < horizontal) {
                    targetInRange = true;
                }
                break;
            case "down":
                if(gp.player.getCenterY() > getCenterY() && yDis < straight && xDis < horizontal) {
                    targetInRange = true;
                }
                break;
            case "left":
                if(gp.player.getCenterX() < getCenterX() && xDis < straight && yDis < horizontal) {
                    targetInRange = true;
                }
                break;
            case "right":
                if(gp.player.getCenterX() > getCenterX() && xDis < straight && yDis < horizontal) {
                    targetInRange = true;
                }
                break;
        }
        
        if(targetInRange == true) {
            int i = new Random().nextInt(rate);
            if(i == 0) {
                attacking = true;
                spriteNum = 1;
                spriteCounter = 0;
                shotAvailableCounter = 0;
            }
        }
    }
    
    public void checkShootOrNot(int rate, int shootInterval) {
        
        int i = new Random().nextInt(rate);
        if(i == 0 && projectile.alive == false && shotAvailableCounter == shootInterval) {

            projectile.set(worldX, worldY, direction, true, this);
            
            // Check vacancy in Projectile array
            for(int ii = 0; i < gp.projectile[1].length; ii++) {
                if(gp.projectile[gp.currentMap][ii] == null) {
                    gp.projectile[gp.currentMap][ii] = projectile;
                    break;
                }
            }
            
            shotAvailableCounter = 0;
        }        
    }
    
    public void checkStartChasingOrNot(Entity target, int distance, int rate) {
        
        if(getTileDistance(target) < distance) {
            
            int i = new Random().nextInt(rate);
            if(i == 0) {
                onPath = true;
            }
        }
    }

    public void checkStopChasingOrNot(Entity target, int distance, int rate) {
        
        if(getTileDistance(target) > distance) {
            
            int i = new Random().nextInt(rate);
            if(i == 0) {
                onPath = false;
            }
        }
    }
    
    public void getRandomDirection(int interval) {

        actionLockCounter ++;

        if(actionLockCounter > interval){

            Random random = new Random();
            int i = random.nextInt(100)+1;

            if(i <= 25){direction = "up";}
            if(i > 25 && i <=50){direction = "down";}
            if(i > 50 && i <=75){direction = "left";}
            if(i > 75 && i <=100){direction = "right";}

            actionLockCounter = 0;
        }        
    }
    
    public void moveTowardPlayer(int interval) {
        
        actionLockCounter ++;
        
        if(actionLockCounter > interval) {
            if(getXDistance(gp.player) > getYDistance(gp.player)) {
                if(gp.player.getCenterX() < getCenterX()) {
                    direction = "left";
                }
                else {
                    direction = "right";
                }
            }
            else if(getXDistance(gp.player) < getYDistance(gp.player)) {
                if(gp.player.getCenterY() < getCenterY()) {
                    direction = "up";
                }
                else {
                    direction = "down";
                }
            }
            actionLockCounter = 0;
        }
    }
    
    public String getOppositeDirection(String direction) {
        
        String oppositeDirection = "";
        
        switch(direction) {
            case "up" : oppositeDirection = "down"; break;
            case "down" : oppositeDirection = "up"; break;
            case "left" : oppositeDirection = "right"; break;
            case "right" : oppositeDirection = "left"; break;
        }
        return oppositeDirection;
    }
    
    public void attacking(){
        
        spriteCounter++;

        if (spriteCounter <= motion1_duration) {
            spriteNum = 1;
        }
        if (spriteCounter > motion1_duration && spriteCounter <= motion2_duration){
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

            if(type == type_monster) { // MOB ATTACKING PLAYER

                if(gp.cChecker.checkPlayer(this) == true) {
                    damagePlayer(attack);
                }
            }            
            if(type == type_npcTwitch) { // NPC TWITCH ATTACKING
                int projectileIndex = gp.cChecker.checkEntity(this, gp.projectile);
                gp.npcTW.damageProjectile(projectileIndex);
            }
            else { // PLAYER ATTACKING MOB
                // Check monster collision with update worldX/Y and solidArea
                int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
                gp.player.damageMonster(monsterIndex, this, attack, currentWeapon.knockBackPower);

                int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
                gp.player.damageInteractiveTile(iTileIndex, attack);

                int projectileIndex = gp.cChecker.checkEntity(this, gp.projectile);
                gp.player.damageProjectile(projectileIndex);
            }

            // After checking collision, rollback values of worldX/Y and solidArea
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }

        if (spriteCounter > motion2_duration) {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }    
    
    public void damagePlayer(int attack) {

        if(gp.player.invincible == false) {
            
            int damage = attack - gp.player.defense;
            
            // Get opposite direction of this attacker
            String canGuardDirection = getOppositeDirection(direction);
            
            // Player Guarding
            if(gp.player.guarding == true && gp.player.direction.equals(canGuardDirection)) {
                // Parry
                if(gp.player.guardCounter < 10) { 
                    damage = 0;
                    gp.playSE(16);
                    setKnockBack(this, gp.player, knockBackPower);
                    offBalance = true;
                    spriteCounter =- 60;
                }
                // Normal Guarding
                else {
                    damage /= 3;
                    gp.playSE(16);
                }
            }
            else {
                // Player not Guarding
                gp.playSE(6);

                if(damage < 1){
                    damage = 1;
                }
            }
            
            if(damage != 0) {
                gp.player.transparent = true;
                setKnockBack(gp.player, this , knockBackPower);
            }
            gp.player.life -= damage;                
            gp.player.invincible = true;
        }        
    }

    public void damageNpcTwitch(int attack) {

        int damage = attack - gp.npcTW.defense;

        gp.playSE(6);

        if(damage < 1){
            damage = 1;
        }

        if(damage != 0) {
            gp.npcTW.transparent = true;
            setKnockBack(gp.npcTW, this , knockBackPower);
        }
        gp.npcTW.life -= damage;                
    }
    
    public void setKnockBack(Entity target, Entity attacker, int knockBackPower) {
        
        this.attacker = attacker;
        target.knockBackDirection = attacker.direction;
        target.speed += knockBackPower;
        target.knockBack = true;
    }    
    
    public boolean inCamera() {
        boolean inCamera = false;

        if(worldX + gp.tileSize * 5 > gp.player.worldX - gp.player.screenX &&
            worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
            worldY + gp.tileSize * 5 > gp.player.worldY - gp.player.screenY &&
            worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){
        
            inCamera = true;
        }
        return inCamera;
    }
    
    public void draw(Graphics2D g2){
        
        BufferedImage image = null;

        // CHECK CAMERA IN
        if(inCamera() == true) {
            
            int tempScreenX = getScreenX();
            int tempScreenY = getScreenY();

            switch(direction) {
                case "up": {
                    if(attacking == false){
                        if(spriteNum == 1){ image = up1; break;}
                        if(spriteNum == 2){ image = up2; break;}
                        if(spriteNum == 3){ image = up3; break;}
                        if(spriteNum == 4){ image = up4; break;}
                    }
                    else {
                        tempScreenY = getScreenY() - up1.getHeight();
                        if(spriteNum == 1){ image = attackUp1; break;}
                        if(spriteNum == 2){ image = attackUp2; break;}
                    }
                }
                case "down": {
                    if(attacking == false){
                        if(spriteNum == 1){ image = down1; break;}
                        if(spriteNum == 2){ image = down2; break;}
                        if(spriteNum == 3){ image = down3; break;}
                        if(spriteNum == 4){ image = down4; break;}
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
                        if(spriteNum == 3){ image = left3; break;}
                        if(spriteNum == 4){ image = left4; break;}
                    }
                    else {
                        tempScreenX = getScreenX() - left1.getWidth();
                        if(spriteNum == 1){ image = attackLeft1; break;}
                        if(spriteNum == 2){ image = attackLeft2; break;}
                    }
                }
                case "right": {
                    if(attacking == false){
                        if(spriteNum == 1){ image = right1; break;}
                        if(spriteNum == 2){ image = right2; break;}
                        if(spriteNum == 3){ image = right3; break;}
                        if(spriteNum == 4){ image = right4; break;}
                    }
                    else {
                        if(spriteNum == 1){ image = attackRight1; break;}
                        if(spriteNum == 2){ image = attackRight2; break;}
                    }
                }
            }
            
            // DRAW NPC TWITCH NICK
            if(this.npcTwitchNick != null) {
                g2.setFont(g2.getFont().deriveFont(20f));
                g2.setColor(Color.black);
                g2.drawString(this.npcTwitchNick, getScreenX()-25, getScreenY());
                g2.setColor(Color.white);
                g2.drawString(this.npcTwitchNick, getScreenX()-27, getScreenY()-2);
            }
            
            // DRAW NPC TWITCH MESSAGE ABOVE NPC IN GAME
            if(this.npcTwitchMessage != null) {
                
                if(System.currentTimeMillis() > (this.messageTwitchTimeStamp + TWITCH_MESSAGE_MAXSCREEN_TIME * 1000)) {
                    this.npcTwitchMessage = null;
                }
                drawTwitcChatDialogue(g2, getScreenX(), getScreenY());
            }

            // VISUAL EFFECT TO INVINCIBLE MODE
            if(invincible == true){
                hpBarOn = true;
                hpBarCounter = 0;
                changeAlpha(g2, 0.4F);
            }
            if(dying == true) {
                dyingAnimation(g2);
            }
            
            g2.drawImage(image, tempScreenX, tempScreenY, null);
            changeAlpha(g2, 1F);

            // DEBUG RED SOLID AREA VIEW
//            g2.setColor(new Color(255, 0, 0, 70));
//            g2.fillRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
            
        }           
    }
    
    public void dyingAnimation(Graphics2D g2) {
        
        dyingCounter++;
        
        int i = 5;
        
        if(dyingCounter <= i) {changeAlpha(g2, 0f);}
        if(dyingCounter > i   && dyingCounter <= i*2) {changeAlpha(g2, 1f);}
        if(dyingCounter > i*2 && dyingCounter <= i*3) {changeAlpha(g2, 0f);}
        if(dyingCounter > i*3 && dyingCounter <= i*4) {changeAlpha(g2, 1f);}
        if(dyingCounter > i*4 && dyingCounter <= i*5) {changeAlpha(g2, 0f);}
        if(dyingCounter > i*5 && dyingCounter <= i*6) {changeAlpha(g2, 1f);}
        if(dyingCounter > i*6 && dyingCounter <= i*7) {changeAlpha(g2, 0f);}
        if(dyingCounter > i*7 && dyingCounter <= i*8) {changeAlpha(g2, 1f);}
        if(dyingCounter > i*8) {
            alive = false;
        }
    }
    
    public void changeAlpha(Graphics2D g2, float alphaValue) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }
    
    public BufferedImage setup(String imagePath, int width, int height){

        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/"+imagePath+".png"));
            image = uTool.scaleImage(image, width, height);
            
        }catch(IOException e){
            e.printStackTrace();
        }
        return image;
    }
    
    public ImageIcon setupGIF(String imagePath, int width, int height){

        ImageIcon icon = new ImageIcon(this.getClass().getResource("/res/"+imagePath+".gif"));
        
        return icon;
    }    
    
    public void searchPath(int goalCol, int goalRow, int currentCol, int currentRow) {

        int startCol = (worldX + solidArea.x) / gp.tileSize;
        int startRow = (worldY + solidArea.y) / gp.tileSize;
        
        gp.pFinder.setNodes(startCol, startRow, goalCol, goalRow);
    
        if(gp.pFinder.search() == true) {
            System.out.println("searchPath:["+startCol+"]["+startRow+"] -> ["+goalCol+"]["+goalRow+"]");
            //System.out.println("Entrou searchPath:"+goalCol+"-"+goalRow);

            // Next worldX and worldY
            int nextX = gp.pFinder.pathList.get(0).col * gp.tileSize;
            int nextY = gp.pFinder.pathList.get(0).row * gp.tileSize;
            
            // Entity's solidArea position
            int enLeftX = worldX + solidArea.x;
            int enRightX = worldX + solidArea.x + solidArea.width;
            int enTopY = worldY + solidArea.y;
            int enBottonY = worldY + solidArea.y + solidArea.height;
            
            // GO UP
            if(enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
                direction = "up";
            }
            // GO DOWN
            else if(enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
                direction = "down";
            }
            // GO left or right
            else if(enTopY >= nextY && enBottonY < nextY + gp.tileSize) {
                if(enLeftX > nextX) {
                    direction = "left";
                }
                if(enLeftX < nextX) {
                    direction = "right";
                }
            }
            // GO up or left
            else if(enTopY < nextY && enLeftX > nextX + gp.tileSize) {
                direction = "up";
                checkCollision();
                if(collisionOn == true) {
                    direction = "left";
                }
            }
            // GO up or right - NOT MORE HAVE A PROBLEM HERE, i´m think !
            else if(enTopY > nextY + gp.tileSize && enLeftX < nextX) { 
//            else if(enTopY > nextY && enRightX > nextX) { // original line
                direction = "up";
                checkCollision();
                if(collisionOn == true) {
                    direction = "right";
                }
            }
            // GO down or left
            else if(enTopY < nextY && enLeftX > nextX) {
                direction = "down";
                checkCollision();
                if(collisionOn == true) {
                    direction = "left";
                }
            }
            // GO down or right
            else if(enTopY < nextY && enLeftX < nextX + gp.tileSize ) {
                direction = "down";
                checkCollision();
                if(collisionOn == true) {
                    direction = "right";
                }
            }
            
            int nextCol = gp.pFinder.pathList.get(0).col;
            int nextRow = gp.pFinder.pathList.get(0).row;

            int ppX = this.worldX / gp.tileSize;
            int ppY = this.worldY / gp.tileSize;
            
            if(nextCol == goalCol && nextRow == goalRow && this.type != this.type_player) {
                onPath = false;
            }
            // FOR PLAYER PATH FINDER MOVEMENT
            //else if(nextCol == goalCol && nextRow == goalRow && this.type == this.type_player) {
            else if(ppX == goalCol && ppY == goalRow && this.type == this.type_player) {
                
                canMove = false;
                onPath = false;
                collisionOn = false;
            }
        }
    }
    
    public int getDetected(Entity user, Entity target[][], String targetName) {
        
        int index = 999;
        
        // Check object arround
        int nextWorldX = user.getLeftX();
        int nextWorldY = user.getTopY();
        
        switch(user.direction) {
            case "up":    nextWorldY = user.getTopY()    - gp.player.speed; break;
            case "down":  nextWorldY = user.getBottomY() + gp.player.speed; break;
            case "left":  nextWorldX = user.getLeftX()   - gp.player.speed; break;
            case "right": nextWorldX = user.getRightX()  + gp.player.speed; break;
        }
        
        int col = nextWorldX / gp.tileSize;
        int row = nextWorldY / gp.tileSize;
        
        for(int i = 0; i < target[1].length; i++) {
            if(target[gp.currentMap][i] != null) {
                if(target[gp.currentMap][i].getCol() == col && 
                        target[gp.currentMap][i].getRow() == row && 
                        target[gp.currentMap][i].name.equals(targetName)) {
                    index = i;
                    break;
                }
            }
        }
        return index;
    }
}