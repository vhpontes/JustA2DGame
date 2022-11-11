package monster;

import entity.Entity;
import java.util.Random;
import main.GamePanel;
import objects.OBJ_Coin_Bronze;
import objects.OBJ_Heart;
import objects.OBJ_ManaCrystal;
import objects.OBJ_Rock;

public class MON_Bat extends Entity {
    
    GamePanel gp;
    public static String monName = "Bat";
    
    public MON_Bat(GamePanel gp){
        super(gp);
        
        this.gp = gp;
        
        type = type_monster;
        name = monName;
        defaultSpeed = 3;
        speed = defaultSpeed;
        maxLife = 2;
        life = maxLife;
        attack = 2;
        defense = 0;
        exp = 2;
        
        solidArea.x = 3;
        solidArea.y = 15;
        solidArea.width = 42;
        solidArea.height = 21;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        
        getImage();
    }
    
    public void getImage() {
        down1 = setup("monster/bat_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("monster/bat_down_2", gp.tileSize, gp.tileSize);
        up1 = down1;
        up2 = down2;
        left1 = down1;
        left2 = down2;
        right1 = down1;
        right2 = down2;
    }

    public void setAction() {

        if(onPath == true){

//            checkStopChasingOrNot(gp.player, 10, 100);
//            searchPath(getGoalCol(gp.player), getGoalRow(gp.player));
        }
        else {

//            checkStartChasingOrNot(gp.player, 5, 100);         
            getRandomDirection(10);
        }
    }
    
    public void damageReaction() {

        actionLockCounter = 0;
//        onPath = true;
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