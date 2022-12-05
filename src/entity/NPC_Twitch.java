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

public final class NPC_Twitch extends Entity{
    
    public NPC_Twitch(GamePanel gp){
        super(gp);
        
        direction = "down";
        speed = 1;
        alive = true;
        canMove = true;

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
}