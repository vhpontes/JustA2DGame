package monster;

import entity.Entity;
import java.util.Random;
import main.GamePanel;
import objects.OBJ_Coin_Bronze;
import objects.OBJ_Heart;
import objects.OBJ_ManaCrystal;
import objects.OBJ_Rock;

public class MON_GreenSlime extends Entity {
    
    GamePanel gp;
    
    public MON_GreenSlime(GamePanel gp){
        super(gp);
        
        this.gp = gp;
        
        type = type_monster;
        name = "GreenSlime";
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 4;
        life = maxLife;
        damage = 1;
        attack = 5;
        defense = 0;
        exp = 2;
        projectile = new OBJ_Rock(gp);
        
        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        
        getImage();
    }
    
    public void getImage() {
        up1 = setup("monster/greenslime_down_1", gp.tileSize, gp.tileSize);
        up2 = setup("monster/greenslime_down_2", gp.tileSize, gp.tileSize);
        down1 = setup("monster/greenslime_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("monster/greenslime_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("monster/greenslime_down_1", gp.tileSize, gp.tileSize);
        left2 = setup("monster/greenslime_down_2", gp.tileSize, gp.tileSize);
        right1 = setup("monster/greenslime_down_1", gp.tileSize, gp.tileSize);
        right2 = setup("monster/greenslime_down_2", gp.tileSize, gp.tileSize);
    }
    
    public void update() {
        
        super.update();
        
        // AGGRO MOB
        int xDistance =  Math.abs(worldX - gp.player.worldX);
        int yDistance =  Math.abs(worldY - gp.player.worldY);
        int tileDistance = (xDistance + yDistance)/gp.tileSize;
        
        if(onPath == false && tileDistance < 5) {
            int i = new Random().nextInt(100)+1;
            if(i > 50) {
                onPath = true;
            }
        }
        if(onPath == true && tileDistance > 10) {
            onPath = false;
        }
    }
    
    public void setAction() {
        
        if(onPath == true){

            int goalCol = (gp.player.worldX + gp.player.solidArea.x)/gp.tileSize;
            int goalRow = (gp.player.worldY + gp.player.solidArea.y)/gp.tileSize;;
            
            searchPath(goalCol, goalRow);

            int i = new Random().nextInt(200)+1;
            if(i > 197 && projectile.alive == false && shotAvailableCounter == 30) {

                projectile.set(worldX, worldY, direction, true, this);
//                gp.projectileList.add(projectile);
                // CHECK EMPTY SLOT PROJECTILE
                for(int ii=0; ii < gp.projectile[1].length; ii++) {
                    if(gp.projectile[gp.currentMap][ii] == null) {
                        gp.projectile[gp.currentMap][ii] = projectile;
                        break;
                    }
                }
            
                shotAvailableCounter = 0;
            }
        }
        else {
            //System.out.println("Entrou SetAction random NPC move");
            actionLockCounter ++;

            if(actionLockCounter == 120){

                Random random = new Random();
                int i = random.nextInt(100)+1;

                if(i <= 25){direction = "up";}
                if(i > 25 && i <=50){direction = "down";}
                if(i > 50 && i <=75){direction = "left";}
                if(i > 75 && i <=100){direction = "right";}

                actionLockCounter = 0;
            }
        }  
    }
    
    public void damageReaction() {
        actionLockCounter = 0;
//        direction = gp.player.direction;
        onPath = true;
    }
    
    public void checkDrop() {
        
        // CAST A DIE
        int i = new Random().nextInt(100)+1;
        
        // SET THE MONSTER DROP
        if(i < 50) {
            dropItem(new OBJ_Coin_Bronze(gp));
        }
        if(i >= 50 && i < 75) {
            dropItem(new OBJ_Heart(gp));
        }
        if(i >= 75 && i < 100) {
            dropItem(new OBJ_ManaCrystal(gp));
        }
    }
}