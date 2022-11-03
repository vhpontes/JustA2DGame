package entity;

import java.awt.Rectangle;
import java.util.Random;
import main.GamePanel;

public class NPC_OldMan extends Entity{
    
    public NPC_OldMan(GamePanel gp){
        super(gp);
        
        direction = "down";
        speed = 1;

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 30;
        solidArea.height = 30;
        
        getImage();
        setDialogue("br");
    }

    public void getImage() {
        
        up1 = setup("npc/oldman_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("npc/oldman_up_2", gp.tileSize, gp.tileSize);
        down1 = setup("npc/oldman_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("npc/oldman_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("npc/oldman_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("npc/oldman_left_2", gp.tileSize, gp.tileSize);
        right1 = setup("npc/oldman_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("npc/oldman_right_2", gp.tileSize, gp.tileSize);
    }
    
    public void setDialogue(String lang){
        switch(lang) {
            case "en" -> {
                dialogues[0] = "Hello!";
                dialogues[1] = "So you've come to this \nisland to find the \ntreasure?";
                dialogues[2] = "I used to be a great \nwizard but now... I'm a bit \ntoo old talking an \nadventure.";
                dialogues[3] = "Well, good luck \nto you.";
            }
            case "br" -> {
                dialogues[0] = "Olá!";
                dialogues[1] = "Então você veio a esta ilha \npara encontrar o tesouro?";
                dialogues[2] = "Eu costumava ser um grande \nmago, mas agora... estou um \npouco velho demais para \nfalar de uma aventura.";
                dialogues[3] = "Bem, boa sorte para você.";
            }
        }
    }
    
    public void setAction(){
        
        if(onPath == true){
//            int goalCol = 10;
//            int goalRow = 9;
            int goalCol = (gp.player.worldX + gp.player.solidArea.x)/gp.tileSize;
            int goalRow = (gp.player.worldY + gp.player.solidArea.y)/gp.tileSize;;
            
            searchPath(goalCol, goalRow);
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
    
    public void speak(){
        
        super.speak();
        
        onPath = true;
    }
    
}
