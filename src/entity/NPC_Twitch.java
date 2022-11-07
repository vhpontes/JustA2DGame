package entity;

import java.util.Random;
import main.GamePanel;

public class NPC_Twitch extends Entity{
    
    public NPC_Twitch(GamePanel gp){
        super(gp);
        
        direction = "down";
        speed = 2;
        alive = true;

        getImage();
    }

    public void getImage() {
        
        up1 =    setup("npctwitch/twitch002_up_1",    gp.tileSize, gp.tileSize);
        up2 =    setup("npctwitch/twitch002_up_2",    gp.tileSize, gp.tileSize);
        down1 =  setup("npctwitch/twitch002_down_1",  gp.tileSize, gp.tileSize);
        down2 =  setup("npctwitch/twitch002_down_2",  gp.tileSize, gp.tileSize);
        left1 =  setup("npctwitch/twitch002_left_1",  gp.tileSize, gp.tileSize);
        left2 =  setup("npctwitch/twitch002_left_2",  gp.tileSize, gp.tileSize);
        right1 = setup("npctwitch/twitch002_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("npctwitch/twitch002_right_2", gp.tileSize, gp.tileSize);
    }

    public void set(int worldX, int worldY, String userID, String nick, String lastMsg) {
        
        this.worldX = worldX;
        this.worldY = worldY;
    }
    
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
    
    public void speak(){
        
        super.speak();
    }  
}