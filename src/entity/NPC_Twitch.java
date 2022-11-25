package entity;

/*
Pixel Art Credits:
    Twitch NPC: 
        riquecamargo 
         - https://itch.io/queue/c/2008591/npc?game_id=1777552
         - https://www.youtube.com/riquecamargo
*/

import java.util.Random;
import main.GamePanel;

public final class NPC_Twitch extends Entity{
    
    public NPC_Twitch(GamePanel gp){
        super(gp);
        
        direction = "down";
        speed = 1;
        alive = true;
        canMove = true;

        getImage();
    }

    public void getImage() {
        
        String imageNPC = "twitch003";
        
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
}