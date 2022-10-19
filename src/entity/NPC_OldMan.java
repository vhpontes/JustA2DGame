package entity;

import java.util.Random;
import main.GamePanel;

public class NPC_OldMan extends Entity{
    
    public NPC_OldMan(GamePanel gp){
        super(gp);
        
        direction = "down";
        speed = 1;
        
        getImage();
        setDialogue("br");
    }

    public void getImage() {
        
        up1 = setup("npc/oldman_up_1");
        up2 = setup("npc/oldman_up_2");
        down1 = setup("npc/oldman_down_1");
        down2 = setup("npc/oldman_down_2");
        left1 = setup("npc/oldman_left_1");
        left2 = setup("npc/oldman_left_2");
        right1 = setup("npc/oldman_right_1");
        right2 = setup("npc/oldman_right_2");
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
    
    public void speak(){
        
        super.speak();
    }
}
