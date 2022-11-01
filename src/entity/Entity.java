package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.UtilityTool;

public class Entity {
    
    public static final int TWITCH_MESSAGE_MAXSCREEN_TIME = 5;
    
    GamePanel gp;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, 
            attackLeft1, attackLeft2, attackRight1, attackRight2;
    public BufferedImage image, image2, image3;
    public Rectangle solidArea = new Rectangle(0,0,48,48);
    public Rectangle attackArea = new Rectangle(0,0,0,0);
    public int solidAreaDefaultX, solidAreaDefaultY;
    String dialogues[] = new String[20];
    public String npcTwitchNick = null;
    public String npcTwitchMessage = null;
    public int npcHashCode = 0;

    // VARs STATE
    public int worldX, worldY;
    public int spriteNum = 1;
    int dialogueIndex = 0;
    public boolean collision = false;
    public boolean collisionOn = false;
    public boolean invincible = false;
    boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    boolean hpBarOn = false;
    public boolean onPath = false;
    public long messageTwitchTimeStamp = 0;

    // VARs COUNTER
    public int spriteCounter = 0;
    public int actionLockCounter = 0;
    public int invincibleCounter = 0;
    public int shotAvailableCounter = 0;
    int dyingCounter = 0;
    int hpBarCounter = 0;

    // VARs CHARACTER ATTRIBUTES
    public String name;
    public int speed;
    public int maxLife;
    public int life;
    public int maxMana;
    public int mana;
    public int ammo;
    public int damage = 0;
    public String direction = "down";
    public int level;
    public int strength;
    public int dexterty;
    public int attack;
    public int defense;
    public int exp;
    public int nextLevelExp;
    public int coin;
    public Entity currentWeapon;
    public Entity currentShield;
    public Projectile projectile;
    
    // ITEM ATTRIBUTES
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorySize = 20;
    public int value;
    public int attackValue;
    public int defenseValue;
    public String description;
    public int useCost;
    public int price;

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
    public final int type_npcTwitch = 8;
    
    public Entity(GamePanel gp) {
        this.gp = gp;
    }
    
    public void setAction(){}
    
    public void damageReaction() {}
    
    public void speak() {
        
        if(npcTwitchMessage != null) {
            dialogues[0] = npcTwitchMessage;
        }
        else if(dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
        }
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;
        
        switch(gp.player.direction) {
            case "up": direction = "down"; break;
            case "down": direction = "up"; break;
            case "left": direction = "right"; break;
            case "right": direction = "left"; break;
        }
    }

    public void twitchSpeak(Graphics2D g2, int screenX, int screenY) {
        
        // NPC Twitch Message
        if(type_npc == 8 && this.npcTwitchMessage != null && !this.npcTwitchMessage.equals("!new")) {
            g2.setFont(g2.getFont().deriveFont(24f));
            g2.setColor(Color.black);
            g2.drawString(this.npcTwitchMessage, screenX+gp.tileSize, screenY-gp.tileSize);
            g2.setColor(Color.green);
            g2.drawString(this.npcTwitchMessage, screenX+gp.tileSize-2, screenY-gp.tileSize-2);
        }
        
        switch(gp.player.direction) {
            case "up": direction = "down"; break;
            case "down": direction = "up"; break;
            case "left": direction = "right"; break;
            case "right": direction = "left"; break;
        }
    }    
    
    public void use(Entity entity) {
        //System.out.println("entrou use entity");
    }
    
    public void checkDrop() {
        
    }
    
    public void dropItem(Entity droppedItem) {
        
        for(int i = 0; i < gp.obj[1].length; i++) {
            if(gp.obj[gp.currentMap][i] == null) {
                gp.obj[gp.currentMap][i] = droppedItem;
                gp.obj[gp.currentMap][i].worldX = worldX; // the dead monster's worldX
                gp.obj[gp.currentMap][i].worldY = worldY; // the dead monster's worldY
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
    
    public void update(){
        
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

        spriteCounter++;
        if(spriteCounter > 24) {
            if(spriteNum == 1) {
                spriteNum = 2;
            }
            else if(spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
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
    }
    
    public void damagePlayer(int attack) {

        if(gp.player.invincible == false) {
            // We can give damage
            gp.playSE(6);

            int damage = attack - gp.player.defense;
            if(damage < 0){
                damage = 0;
            }
            gp.player.life -= damage;                
            gp.player.invincible = true;
        }        
    }
    
    public void draw(Graphics2D g2){
        
        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
            worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
            worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
            worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){
            
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
            
            // Monster HP Bar
            if(type == 2 && hpBarOn == true) {
                // referencia do tile 48
                double oneScale = (double)gp.tileSize/maxLife;
                double hpBarValue = oneScale * life;

                if(hpBarValue < 0 ){ hpBarValue = 0; } // evita que a barra ultrapasse o limite negativo;
                
                g2.setColor(new Color(35, 35, 35));
                g2.fillRect(screenX-1, screenY - 16, gp.tileSize + 2, 12);
                g2.setColor(new Color(255, 0, 30));
                g2.fillRect(screenX, screenY - 15, (int)hpBarValue, 10);
                
                hpBarCounter++;
                if(hpBarCounter > 600) {
                    hpBarCounter = 0;
                    hpBarOn = false;
                }
            }
            
            // NPC Twitch Nick
            //System.out.println("FORA:"+this.npcTwitchNick+" "+type_npc);
            if(type_npc==8 && this.npcTwitchNick!=null) {
            //System.out.println("dentro:"+this.npcTwitchNick);
                g2.setFont(g2.getFont().deriveFont(20f));
                g2.setColor(Color.black);
                g2.drawString(this.npcTwitchNick, screenX-25, screenY);
                g2.setColor(Color.white);
                g2.drawString(this.npcTwitchNick, screenX-27, screenY-2);
            }
            
            //this.npcTwitchMessage = "TESTE";
            if(type_npc==8 && this.npcTwitchMessage != null) {
                
                if(System.currentTimeMillis() > (this.messageTwitchTimeStamp + TWITCH_MESSAGE_MAXSCREEN_TIME * 1000)) {
                    this.npcTwitchMessage = "";
                }
                twitchSpeak(g2, screenX, screenY);
            }

            // Visual Effect to invencible mode
            if(invincible == true){
                hpBarOn = true;
                hpBarCounter = 0;
                changeAlpha(g2, 0.4F);
            }
            if(dying == true) {
                dyingAnimation(g2);
            }
            
            g2.drawImage(image, screenX, screenY, null);
            changeAlpha(g2, 1F);

            g2.setColor(new Color(255, 0, 0, 70));
            g2.fillRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
            
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
    
    public void searchPath(int goalCol, int goalRow) {

        int startCol = (worldX + solidArea.x) / gp.tileSize;
        int startRow = (worldY + solidArea.y) / gp.tileSize;
        
        gp.pFinder.setNodes(startCol, startRow, goalCol, goalRow);
        
        if(gp.pFinder.search() == true) {
            //System.out.println("Entrou searchPath");

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
                System.out.println("Collision:"+collisionOn + " " + direction);
            }
            // GO DOWN
            else if(enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
                direction = "down";
                System.out.println("Collision:"+collisionOn + " " + direction);
            }
            // GO left or right
            else if(enTopY >= nextY && enBottonY < nextY + gp.tileSize) {
                if(enLeftX > nextX) {
                    direction = "left";
                }
                if(enLeftX < nextX) {
                    direction = "right";
                }
                System.out.println("Collision:"+collisionOn + " " + direction);
            }
            // GO up or left
            else if(enTopY < nextY && enLeftX > nextX) {
                direction = "up";
                checkCollision();
                if(collisionOn == true) {
                    direction = "left";
                }
                System.out.println("Collision:"+collisionOn + " " + direction);
            }
            // GO up or right
            else if(enTopY > nextY && enLeftX < nextX) {
                direction = "up";
                checkCollision();
                if(collisionOn == true) {
                    direction = "right";
                }
                System.out.println("Collision:"+collisionOn + " " + direction);
            }
            // GO down or left
            else if(enTopY < nextY && enLeftX > nextX) {
                direction = "down";
                checkCollision();
                if(collisionOn == true) {
                    direction = "left";
                }
                System.out.println("Collision:"+collisionOn + " " + direction);
            }
            // GO down or right
            else if(enTopY < nextY && enLeftX < nextX) {
                direction = "down";
                checkCollision();
                if(collisionOn == true) {
                    direction = "right";
                }
                System.out.println("Collision:"+collisionOn + " " + direction);
            }
            
            int nextCol = gp.pFinder.pathList.get(0).col;
            int nextRow = gp.pFinder.pathList.get(0).row;
            if(nextCol == goalCol && nextRow == goalRow) {
                onPath = false;
            }
        }
    }
}