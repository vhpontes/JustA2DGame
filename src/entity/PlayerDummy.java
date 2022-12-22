/*
Code based in RyiSnow youtube channel:
https://www.youtube.com/c/RyiSnow

Class Code modifications and addons:
Victor Hugo Manata Pontes
https://www.twitch.tv/lechuck311
https://www.youtube.com/@victorhugomanatapontes
https://www.youtube.com/@dtudoumporco
*/
 
package entity;

import main.GamePanel;

public class PlayerDummy extends Entity {
    
    public static final String npcName = "Dummy";
    private String imageHeroPlayer;
    
    public PlayerDummy(GamePanel gp) {
        
        super(gp);
        
        name = npcName;
        
        getImage();
    }

    private void getImage() {

        imageHeroPlayer = "man48";
        
        anim = setupGIF("player/"+imageHeroPlayer+"_anim",    gp.tileSize, gp.tileSize);
        
        up1    = setup("player/"+imageHeroPlayer+"_up_1",    gp.tileSize, gp.tileSize);
        up2    = setup("player/"+imageHeroPlayer+"_up_2",    gp.tileSize, gp.tileSize);
        up3    = setup("player/"+imageHeroPlayer+"_up_3",    gp.tileSize, gp.tileSize);
        up4    = setup("player/"+imageHeroPlayer+"_up_4",    gp.tileSize, gp.tileSize);
        down1  = setup("player/"+imageHeroPlayer+"_down_1",  gp.tileSize, gp.tileSize);
        down2  = setup("player/"+imageHeroPlayer+"_down_2",  gp.tileSize, gp.tileSize);
        down3  = setup("player/"+imageHeroPlayer+"_down_3",  gp.tileSize, gp.tileSize);
        down4  = setup("player/"+imageHeroPlayer+"_down_4",  gp.tileSize, gp.tileSize);
        left1  = setup("player/"+imageHeroPlayer+"_left_1",  gp.tileSize, gp.tileSize);
        left2  = setup("player/"+imageHeroPlayer+"_left_2",  gp.tileSize, gp.tileSize);
        left3  = setup("player/"+imageHeroPlayer+"_left_3",  gp.tileSize, gp.tileSize);
        left4  = setup("player/"+imageHeroPlayer+"_left_4",  gp.tileSize, gp.tileSize);
        right1 = setup("player/"+imageHeroPlayer+"_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("player/"+imageHeroPlayer+"_right_2", gp.tileSize, gp.tileSize);
        right3 = setup("player/"+imageHeroPlayer+"_right_3", gp.tileSize, gp.tileSize);
        right4 = setup("player/"+imageHeroPlayer+"_right_4", gp.tileSize, gp.tileSize);
        
        imageHeroPlayer = "hero001";
    }
}
