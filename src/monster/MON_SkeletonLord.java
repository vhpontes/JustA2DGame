/*
Code based in RyiSnow youtube channel:
https://www.youtube.com/c/RyiSnow
*/
 
package monster;

import entity.Entity;
import java.util.Random;
import main.GamePanel;
import objects.OBJ_Coin_Gold;
import objects.OBJ_Heart;
import objects.OBJ_ManaCrystal;

public class MON_SkeletonLord extends Entity {
    
    GamePanel gp;
    public static String monName = "Skeleton Lord";
    static int bossMultiplier = 5;
    
    public MON_SkeletonLord(GamePanel gp){
        super(gp);
        
        this.gp = gp;
        
        type = type_monster;
        name = monName;
        boss = true;
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 50;
        life = maxLife;
        attack = 10;
        defense = 2;
        exp = 50;
        knockBackPower = 5;
        numDrop = 10;
        
        int size = gp.tileSize * bossMultiplier;
        solidArea.x = 48;
        solidArea.y = 48;
        solidArea.width = size - gp.tileSize * 2;
        solidArea.height = size - gp.tileSize;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        attackArea.width = 170;
        attackArea.height = 170;
        
        motion1_duration = 25;
        motion2_duration = 50;
        
        getImage();
        getAttackImage();
    }
    
    public void getImage() {
        
        if(inRage == false) {
            down1 =  setup("monster/skeletonlord_down_1",  gp.tileSize * bossMultiplier, gp.tileSize * bossMultiplier);
            down2 =  setup("monster/skeletonlord_down_2",  gp.tileSize * bossMultiplier, gp.tileSize * bossMultiplier);
            down3 =  setup("monster/skeletonlord_down_1",  gp.tileSize * bossMultiplier, gp.tileSize * bossMultiplier);
            down4 =  setup("monster/skeletonlord_down_2",  gp.tileSize * bossMultiplier, gp.tileSize * bossMultiplier);
            up1 =    setup("monster/skeletonlord_up_1",    gp.tileSize * bossMultiplier, gp.tileSize * bossMultiplier);
            up2 =    setup("monster/skeletonlord_up_2",    gp.tileSize * bossMultiplier, gp.tileSize * bossMultiplier);
            up3 =    setup("monster/skeletonlord_up_1",    gp.tileSize * bossMultiplier, gp.tileSize * bossMultiplier);
            up4 =    setup("monster/skeletonlord_up_2",    gp.tileSize * bossMultiplier, gp.tileSize * bossMultiplier);
            left1 =  setup("monster/skeletonlord_left_1",  gp.tileSize * bossMultiplier, gp.tileSize * bossMultiplier);
            left2 =  setup("monster/skeletonlord_left_2",  gp.tileSize * bossMultiplier, gp.tileSize * bossMultiplier);
            left3 =  setup("monster/skeletonlord_left_1",  gp.tileSize * bossMultiplier, gp.tileSize * bossMultiplier);
            left4 =  setup("monster/skeletonlord_left_2",  gp.tileSize * bossMultiplier, gp.tileSize * bossMultiplier);
            right1 = setup("monster/skeletonlord_right_1", gp.tileSize * bossMultiplier, gp.tileSize * bossMultiplier);
            right2 = setup("monster/skeletonlord_right_2", gp.tileSize * bossMultiplier, gp.tileSize * bossMultiplier);
            right3 = setup("monster/skeletonlord_right_1", gp.tileSize * bossMultiplier, gp.tileSize * bossMultiplier);
            right4 = setup("monster/skeletonlord_right_2", gp.tileSize * bossMultiplier, gp.tileSize * bossMultiplier);
        }
        if(inRage == true) {
            down1 =  setup("monster/skeletonlord_phase2_down_1",  gp.tileSize * bossMultiplier, gp.tileSize * bossMultiplier);
            down2 =  setup("monster/skeletonlord_phase2_down_2",  gp.tileSize * bossMultiplier, gp.tileSize * bossMultiplier);
            down3 =  setup("monster/skeletonlord_phase2_down_1",  gp.tileSize * bossMultiplier, gp.tileSize * bossMultiplier);
            down4 =  setup("monster/skeletonlord_phase2_down_2",  gp.tileSize * bossMultiplier, gp.tileSize * bossMultiplier);
            up1 =    setup("monster/skeletonlord_phase2_up_1",    gp.tileSize * bossMultiplier, gp.tileSize * bossMultiplier);
            up2 =    setup("monster/skeletonlord_phase2_up_2",    gp.tileSize * bossMultiplier, gp.tileSize * bossMultiplier);
            up3 =    setup("monster/skeletonlord_phase2_up_1",    gp.tileSize * bossMultiplier, gp.tileSize * bossMultiplier);
            up4 =    setup("monster/skeletonlord_phase2_up_2",    gp.tileSize * bossMultiplier, gp.tileSize * bossMultiplier);
            left1 =  setup("monster/skeletonlord_phase2_left_1",  gp.tileSize * bossMultiplier, gp.tileSize * bossMultiplier);
            left2 =  setup("monster/skeletonlord_phase2_left_2",  gp.tileSize * bossMultiplier, gp.tileSize * bossMultiplier);
            left3 =  setup("monster/skeletonlord_phase2_left_1",  gp.tileSize * bossMultiplier, gp.tileSize * bossMultiplier);
            left4 =  setup("monster/skeletonlord_phase2_left_2",  gp.tileSize * bossMultiplier, gp.tileSize * bossMultiplier);
            right1 = setup("monster/skeletonlord_phase2_right_1", gp.tileSize * bossMultiplier, gp.tileSize * bossMultiplier);
            right2 = setup("monster/skeletonlord_phase2_right_2", gp.tileSize * bossMultiplier, gp.tileSize * bossMultiplier);
            right3 = setup("monster/skeletonlord_phase2_right_1", gp.tileSize * bossMultiplier, gp.tileSize * bossMultiplier);
            right4 = setup("monster/skeletonlord_phase2_right_2", gp.tileSize * bossMultiplier, gp.tileSize * bossMultiplier);
        }
    }
    
    public void getAttackImage() {
        
        int imgDouble = gp.tileSize * 2 * bossMultiplier;
        
        if(inRage == false) {
            attackUp1    = setup("monster/skeletonlord_attack_up_1",   gp.tileSize * bossMultiplier, imgDouble);
            attackUp2    = setup("monster/skeletonlord_attack_up_2",   gp.tileSize * bossMultiplier, imgDouble);
            attackDown1  = setup("monster/skeletonlord_attack_down_1", gp.tileSize * bossMultiplier, imgDouble);
            attackDown2  = setup("monster/skeletonlord_attack_down_2", gp.tileSize * bossMultiplier, imgDouble);
            attackLeft1  = setup("monster/skeletonlord_attack_left_1", imgDouble, gp.tileSize * bossMultiplier);
            attackLeft2  = setup("monster/skeletonlord_attack_left_2", imgDouble, gp.tileSize * bossMultiplier);
            attackRight1 = setup("monster/skeletonlord_attack_right_1",imgDouble, gp.tileSize * bossMultiplier);
            attackRight2 = setup("monster/skeletonlord_attack_right_2",imgDouble, gp.tileSize * bossMultiplier);        
        }
        if(inRage == true) {
            attackUp1    = setup("monster/skeletonlord_phase2_attack_up_1",   gp.tileSize * bossMultiplier, imgDouble);
            attackUp2    = setup("monster/skeletonlord_phase2_attack_up_2",   gp.tileSize * bossMultiplier, imgDouble);
            attackDown1  = setup("monster/skeletonlord_phase2_attack_down_1", gp.tileSize * bossMultiplier, imgDouble);
            attackDown2  = setup("monster/skeletonlord_phase2_attack_down_2", gp.tileSize * bossMultiplier, imgDouble);
            attackLeft1  = setup("monster/skeletonlord_phase2_attack_left_1", imgDouble, gp.tileSize * bossMultiplier);
            attackLeft2  = setup("monster/skeletonlord_phase2_attack_left_2", imgDouble, gp.tileSize * bossMultiplier);
            attackRight1 = setup("monster/skeletonlord_phase2_attack_right_1",imgDouble, gp.tileSize * bossMultiplier);
            attackRight2 = setup("monster/skeletonlord_phase2_attack_right_2",imgDouble, gp.tileSize * bossMultiplier);        
        }
    }
    
    public void setAction() {

        if(inRage == false && life < maxLife/2) {
            inRage = true;
            getImage();
            getAttackImage();
            defaultSpeed++;
            speed = defaultSpeed;
            attack *= 2;
        }
        
        if(getTileDistance(gp.player) < 10) {

            moveTowardPlayer(60);
        }
        else {

            getRandomDirection(120);
        }
        
        // Check if it attack
        if(attacking == false) {
            checkAttackOrNot(60, gp.tileSize * 7, gp.tileSize * bossMultiplier);
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