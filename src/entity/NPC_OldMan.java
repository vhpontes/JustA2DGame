/*
Code based in RyiSnow youtube channel:
https://www.youtube.com/c/RyiSnow
*/

package entity;

import java.awt.Rectangle;
import java.util.Random;
import main.GamePanel;

public class NPC_OldMan extends Entity{
    
    public static final String npcName = "Old Man";
    
    public NPC_OldMan(GamePanel gp){
        super(gp);
        
        name = npcName;
        direction = "down";
        speed = 1;

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 30;
        solidArea.height = 30;
        
        dialogueSet = -1;
        
        getImage();
        setDialogue("en");
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
                dialogues[0][0] = "Hello!";
                dialogues[0][1] = "So you've come to this \nisland to find the \ntreasure?";
                dialogues[0][2] = "I used to be a great \nwizard but now... I'm a bit \ntoo old talking an \nadventure.";
                dialogues[0][3] = "Well, good luck \nto you.";

                dialogues[1][0] = "If you become tired, rest at the water!";
                dialogues[1][1] = "However, the monster reappear if you rest.\nI don't know why, but that's how it works.";
                dialogues[1][2] = "In any case, don't push yourself too hard";
                
                dialogues[2][0] = "I wonder how to open that door...";
            }
            case "br" -> {
                dialogues[0][0] = "Olá!";
                dialogues[0][1] = "Então você veio a esta ilha \npara encontrar o tesouro?";
                dialogues[0][2] = "Eu costumava ser um grande \nmago, mas agora... estou um \npouco velho demais para \nfalar de uma aventura.";
                dialogues[0][3] = "Bem, boa sorte para você.";

                dialogues[1][0] = "Se você ficar cansado, descanse perto da água!";
                dialogues[1][1] = "No entanto, o monstro reaparece se você descansar.\nNão sei porque, mas é assim que funciona.";
                dialogues[1][2] = "De qualquer forma, não se esforce demais.";
                
                dialogues[2][0] = "Gostaria de saber como abrir essa porta...";
            }
        }
    }
    
    public void setAction(){
        
        if(onPath == true){
//            int goalCol = 10;
//            int goalRow = 9;
            int goalCol = (gp.player.worldX + gp.player.solidArea.x)/gp.tileSize;
            int goalRow = (gp.player.worldY + gp.player.solidArea.y)/gp.tileSize;
            
            searchPath(goalCol, goalRow, this.worldX, this.worldY);
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
        
        facePlayer();
        startDialogue(this, dialogueSet);
        
        dialogueSet++;
        
        if(dialogues[dialogueSet][0] == null) {

            dialogueSet = 0;
            //dialogueSet--;
        }
        
//        if(gp.player.life < gp.player.maxLife/3) {
//            dialogueSet = 1;
//        }
        
//        onPath = true;
    }
    
}
