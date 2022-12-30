/*
Code based in RyiSnow youtube channel:
https://www.youtube.com/c/RyiSnow
*/
package objects;

import entity.Entity;
import main.GamePanel;

public class OBJ_BlueHeart extends Entity{

    public static final String objName = "Blue Heart";

    public OBJ_BlueHeart(GamePanel gp) {
        super(gp);
        
        this.gp = gp;
        
        this.type = type_pickupOnly;
        this.name = objName;
        this.down1 = setup("objects/blueheart", gp.tileSize, gp.tileSize);
        this.description = "[" + name + "]\nLegendary Blue Heart Treasure.";
        
        setDialogue();
    }

    public void setDialogue() {
        switch(gp.language) {
            case "en": 
                dialogues[0][0] = "You picked up a beatiful blue gem."; 
                dialogues[0][1] = "You finded the " + this.name + ", the legendary treasure!";
                break;
            case "cn": 
                dialogues[0][0] = "你捡到了一颗美丽的蓝色宝石。"; 
                dialogues[0][1] = "你找到了 蓝心, 传说中的宝物!";
                break;
            case "br": 
                dialogues[0][0] = "Você pegou uma linda joia azul."; 
                dialogues[0][1] = "Você encontrou a " + this.name + ", o lendário tesouro!";
                break;

        }
    }
    
    public boolean use(Entity entity) {
        
        gp.gameState = gp.cutsceneState;
        gp.csManager.sceneNum = gp.csManager.ending;
        return true;
    }
}
