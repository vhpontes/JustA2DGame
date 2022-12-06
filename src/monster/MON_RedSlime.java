/*
Code based in RyiSnow youtube channel:
https://www.youtube.com/c/RyiSnow
*/
 
package monster;

import entity.Entity;
import java.util.Random;
import main.GamePanel;
import objects.OBJ_Coin_Bronze;
import objects.OBJ_Coin_Gold;
import objects.OBJ_Heart;
import objects.OBJ_ManaCrystal;
import objects.OBJ_Rock;

public class MON_RedSlime extends Entity {
    
    GamePanel gp;
    public static String monName = "Red Slime";
    
    public MON_RedSlime(GamePanel gp){
        super(gp);
        
        this.gp = gp;
        
        type = type_monster;
        name = monName;
        boss = false;
        defaultSpeed = 2;
        speed = defaultSpeed;
        maxLife = 8;
        life = maxLife;
        attack = 5;
        defense = 0;
        exp = 3;
        projectile = new OBJ_Rock(gp);
        numDrop = 2;
        
        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        
        getImage();
    }
    
    public void getImage() {
        down1 =  setup("monster/redslime48_down_1", gp.tileSize, gp.tileSize);
        down2 =  setup("monster/redslime48_down_2", gp.tileSize, gp.tileSize);
        down3 = down1;
        down4 = down2;
        up1 = down1;
        up2 = down2;
        up3 = down1;
        up4 = down2;
        left1 = down1;
        left2 = down2;
        left3 = down3;
        left4 = down4;
        right1 = down1;
        right2 = down2;
        right3 = down3;
        right4 = down4;
    }

    public void setAction() {

        if(onPath == true){

            checkStopChasingOrNot(gp.player, 10, 100);
            searchPath(getGoalCol(gp.player), getGoalRow(gp.player), this.worldX, this.worldY);
            checkShootOrNot(200, 30);
        }
        else {

            checkStartChasingOrNot(gp.player, 5, 100);         
            getRandomDirection(120);
        }
    }
    
    public void damageReaction() {
        
        actionLockCounter = 0;
        onPath = true;
    }
    
    public void checkDrop(int X, int Y) {
        
        // CAST A DIE
        int i = new Random().nextInt(100)+1;
        
        // SET THE MONSTER DROP
        if(i < 35) {
            dropItem(new OBJ_Coin_Bronze(gp), X, Y);
        }
        if(i >= 35 && i < 50) {
            dropItem(new OBJ_Coin_Gold(gp), X, Y);
        }
        if(i >= 50 && i < 75) {
            dropItem(new OBJ_Heart(gp), X, Y);
        }
        if(i >= 75 && i < 100) {
            dropItem(new OBJ_ManaCrystal(gp), X, Y);
        }
    }
}    

