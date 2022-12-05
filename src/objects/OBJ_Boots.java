/*
Code based in RyiSnow youtube channel:
https://www.youtube.com/c/RyiSnow
*/
 
package objects;

import entity.Entity;
import main.GamePanel;

public class OBJ_Boots extends Entity{

    public static final String objName = "Boots";

    public OBJ_Boots(GamePanel gp) {
        super(gp);
        
        type = type_consumable;
        stackable = true;
        name = objName;
        value = 2;
        down1 = setup("objects/boots", gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nThis boots increase your speed.";
        price = 50;
        
        setDialogue("en");
    }

    public void setDialogue(String lang) {
        switch(lang) {
            case "en": dialogues[0][0] = "You wore the " + name + "!\n"
                + "Your speed has bean increased by " + value + "."; break;
            case "br": dialogues[0][0] = "Você vestiu a " + name + "!\n"
                + "Sua velocidade aumentou em " + value + "."; break;

        }
    }   
    
    public boolean use(Entity entity) {
        
        startDialogue(this, 0);
        entity.speed += value;
        gp.playSE(2);
        return true;
    }    
}