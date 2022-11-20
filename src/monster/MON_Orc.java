package monster;

import entity.Entity;
import java.util.Random;
import main.GamePanel;
import objects.OBJ_Coin_Bronze;
import objects.OBJ_Coin_Gold;
import objects.OBJ_Heart;
import objects.OBJ_ManaCrystal;

public class MON_Orc extends Entity {
    
    GamePanel gp;
    public static String monName = "Orc";
    
    public MON_Orc(GamePanel gp){
        super(gp);
        
        this.gp = gp;
        
        type = type_monster;
        name = monName;
        defaultSpeed = 2;
        speed = defaultSpeed;
        maxLife = 10;
        life = maxLife;
        attack = 8;
        defense = 2;
        exp = 10;
        knockBackPower = 4;
        numDrop = 4;
        
        solidArea.x = 4;
        solidArea.y = 4;
        solidArea.width = 40;
        solidArea.height = 44;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        
        attackArea.width = 48;
        attackArea.height = 48;
        
        motion1_duration = 40;
        motion2_duration = 85;
        
        getImage();
        getAttackImage();
    }
    
    public void getImage() {
        up1 =    setup("monster/orc_up_1",    gp.tileSize, gp.tileSize);
        up2 =    setup("monster/orc_up_2",    gp.tileSize, gp.tileSize);
        down1 =  setup("monster/orc_down_1",  gp.tileSize, gp.tileSize);
        down2 =  setup("monster/orc_down_2",  gp.tileSize, gp.tileSize);
        left1 =  setup("monster/orc_left_1",  gp.tileSize, gp.tileSize);
        left2 =  setup("monster/orc_left_2",  gp.tileSize, gp.tileSize);
        right1 = setup("monster/orc_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("monster/orc_right_2", gp.tileSize, gp.tileSize);
    }
    
    public void getAttackImage() {
        int imgDouble = gp.tileSize * 2;
        
        attackUp1    = setup("monster/orc_attack_up_1",   gp.tileSize, imgDouble);
        attackUp2    = setup("monster/orc_attack_up_2",   gp.tileSize, imgDouble);
        attackDown1  = setup("monster/orc_attack_down_1", gp.tileSize, imgDouble);
        attackDown2  = setup("monster/orc_attack_down_2", gp.tileSize, imgDouble);
        attackLeft1  = setup("monster/orc_attack_left_1", imgDouble, gp.tileSize);
        attackLeft2  = setup("monster/orc_attack_left_2", imgDouble, gp.tileSize);
        attackRight1 = setup("monster/orc_attack_right_1",imgDouble, gp.tileSize);
        attackRight2 = setup("monster/orc_attack_right_2",imgDouble, gp.tileSize);        
    }
    
    public void setAction() {

        if(onPath == true){

            checkStopChasingOrNot(gp.player, 10, 100);
            searchPath(getGoalCol(gp.player), getGoalRow(gp.player));
        }
        else {

            checkStartChasingOrNot(gp.player, 5, 100);         
            getRandomDirection(120);
        }
        
        // Check if it attack
        if(attacking == false) {
            checkAttackOrNot(30, gp.tileSize*4, gp.tileSize);
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
        if(i < 50) {
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