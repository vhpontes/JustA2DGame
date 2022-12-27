/*
Class code: Victor Hugo Manata Pontes 
https://www.twitch.tv/lechuck311
https://www.youtube.com/@victorhugomanatapontes
https://www.youtube.com/@dtudoumporco

Pixel Art Credits:
    Twitch NPC 1: 
        riquecamargo 
         - https://itch.io/queue/c/2008591/npc?game_id=1777552
         - https://www.youtube.com/riquecamargo
*/

package entity;

import java.util.Random;
import main.GamePanel;
import objects.OBJ_Fireball;

public final class NPC_Twitch extends Entity{

    public NPC_Twitch(GamePanel gp){
        super(gp);
        
        this.type = this.type_npcTwitch;
        this.name = "";
        this.direction = "down";
        this.maxLife = 3;
        this.life = this.maxLife;
        this.speed = 1;
        this.alive = true;
        this.canMove = true;
        this.npcFireballLaunched = false;
        this.animation = false;

        this.projectile = new OBJ_Fireball(gp, "twitch_fireball");
        
        getImage();
    }
    
    public String RandomNPCImage() {
        String npcIMGName = "twitch001";
        Random random = new Random();
         int i = random.nextInt(100) + 1;

         if(i <= 5)            {npcIMGName = "twitch001";}
         if(i > 5 && i <= 15)   {npcIMGName = "twitch002";}
         if(i > 15 && i <= 30)   {npcIMGName = "twitch003";}
         if(i > 30 && i <= 45)   {npcIMGName = "twitch004";}
         if(i > 45 && i <= 60)  {npcIMGName = "twitch005";}
         if(i > 60 && i <= 75)  {npcIMGName = "twitch006";}
         if(i > 75 && i <= 90)  {npcIMGName = "twitch007";}
         if(i > 90 && i <= 100)  {npcIMGName = "twitch008";}
         
         return npcIMGName;
    }

    public void getImage() {
        
        String imageNPC = RandomNPCImage();
        
        up1 =    setup("npctwitch/"+imageNPC+"_up_1",    gp.tileSize, gp.tileSize);
        up2 =    setup("npctwitch/"+imageNPC+"_up_2",    gp.tileSize, gp.tileSize);
        up3 =    setup("npctwitch/"+imageNPC+"_up_3",    gp.tileSize, gp.tileSize);
        up4 =    setup("npctwitch/"+imageNPC+"_up_4",    gp.tileSize, gp.tileSize);
        down1 =  setup("npctwitch/"+imageNPC+"_down_1",  gp.tileSize, gp.tileSize);
        down2 =  setup("npctwitch/"+imageNPC+"_down_2",  gp.tileSize, gp.tileSize);
        down3 =  setup("npctwitch/"+imageNPC+"_down_3",  gp.tileSize, gp.tileSize);
        down4 =  setup("npctwitch/"+imageNPC+"_down_4",  gp.tileSize, gp.tileSize);
        left1 =  setup("npctwitch/"+imageNPC+"_left_1",  gp.tileSize, gp.tileSize);
        left2 =  setup("npctwitch/"+imageNPC+"_left_2",  gp.tileSize, gp.tileSize);
        left3 =  setup("npctwitch/"+imageNPC+"_left_3",  gp.tileSize, gp.tileSize);
        left4 =  setup("npctwitch/"+imageNPC+"_left_4",  gp.tileSize, gp.tileSize);
        right1 = setup("npctwitch/"+imageNPC+"_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("npctwitch/"+imageNPC+"_right_2", gp.tileSize, gp.tileSize);
        right3 = setup("npctwitch/"+imageNPC+"_right_3", gp.tileSize, gp.tileSize);
        right4 = setup("npctwitch/"+imageNPC+"_right_4", gp.tileSize, gp.tileSize);
    }

    public void set(int worldX, int worldY, String userID, String nick, String lastMsg) {
        
        this.worldX = worldX;
        this.worldY = worldY;
    }
    
    @Override
    public void setAction(){
        
        actionLockCounter ++;
        
        if(actionLockCounter == 120) {
                  
            Random random = new Random();
            int i = random.nextInt(100) + 1;
            
            if(i <= 25){direction = "up";}
            if(i > 25 && i <=50){direction = "down";}
            if(i > 50 && i <=75){direction = "left";}
            if(i > 75 && i <=100){direction = "right";}
            
            actionLockCounter = 0;
        }     
    }
    
    @Override
    public void speak(){
        
        super.speak();
    }  
    
    public void damageProjectile(int i) {
        System.out.println(
            i + ":" + 
            projectile.alive + " - " + 
            gp.projectile[gp.currentMap]);
        
        if(i != 999) {
            Entity projectile = gp.projectile[gp.currentMap][i];
            projectile.alive = false;
            generateParticle(projectile, projectile);
        }
    }    
/*    public void update() {

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
        
        System.out.println(
                this.npcTwitchNick + "  " + 
                this.npcFireballLaunched + "  " + 
                projectile.alive + "  " + 
                shotAvailableCounter);
        
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
        
        // prevent two fireball if close attack
        if(shotAvailableCounter < 30) {
            shotAvailableCounter++;
        }
        else {
            this.npcFireballLaunched = false;
        }
        
    }
*/
}