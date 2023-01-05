/*
Code based in RyiSnow youtube channel:
https://www.youtube.com/c/RyiSnow
*/
 
package objects;

import entity.Entity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import main.GamePanel;

public class OBJ_Chest extends Entity{
    
    GamePanel gp;
    public static final String objName = "Chest";
    int total = 0;
    
    
    public OBJ_Chest(GamePanel gp) {
        super(gp);
        this.gp = gp;
        
        type = type_obstacle;
        name = objName;
        image = setup("objects/chest01_01", gp.tileSize, gp.tileSize);        
        image2 = setup("objects/chest01_02", gp.tileSize, gp.tileSize);
        image3 = setup("objects/chest01_03", gp.tileSize, gp.tileSize);
        down1 = image;
        collision = true;
    
        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 40;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
                
    }

    public void setLootItem(Entity loot, int lootAmount) {
        
        for(var i = 0; i < lootAmount; i++) {
            this.inventory.add(loot);
        }
        empty = false;
        setDialogue();
    }
    
    public String getInventoryList() {
        
       String stringLootItens = "";
       
       for(int i = 0; i < this.inventory.size(); i++) {
          
            if(this.inventory.size() == 1) {

                stringLootItens = this.inventory.get(i).name;
            }
            else {

                if (i < this.inventory.size() - 1) {
                    
                        stringLootItens += this.inventory.get(i).name + ", ";
                }
                else

                    stringLootItens += this.inventory.get(i).name;
            }
        }
        
        List<String> list = new ArrayList<>(Arrays.asList(stringLootItens.split(",")));
        Map<String, Long> result = list.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        
        stringLootItens = "";
        
        for (Iterator<Map.Entry<String, Long>> it = result.entrySet().iterator(); it.hasNext();) {
            Map.Entry<String, Long> entry = it.next();
            if(!it.hasNext()) {
                if(entry.getValue() > 1)
                    stringLootItens += entry.getKey()+"(x"+entry.getValue()+")";
                else 
                    stringLootItens += entry.getKey();
            }
            else {
                if(entry.getValue() > 1)
                    stringLootItens += entry.getKey()+"(x"+entry.getValue()+")" + ", ";
                else 
                    stringLootItens += entry.getKey() + ", ";
            }
        }
        
        return stringLootItens;
    }
    
    public void setDialogue() {

        dialogues[0][0] = "You open the chest and find "+ getInventoryList() + "!\n...But you not carry any more!";
        dialogues[1][0] = "You open the chest and find "+ getInventoryList() + "!\nYou obtain the "+ getInventoryList() +"!";
        dialogues[2][0] = "It's empty";
    }    

    public void interact() {
        
        if(empty == false) {
            down1 = image2;
        }
        else {
            down1 = image3;
        }

        if(opened == false) {
            
            gp.playSE(3);
            
            for(int i = 0; i < this.inventory.size(); i++) {
                if(gp.player.canObtainItem(gp.eGenerator.getObject(this.inventory.get(i).name)) == false) {

                    startDialogue(this, 0);
                }
                else {
                    
                    startDialogue(this, 1);
                    opened = true;
                    empty = true;
                }
            }
        }
        else {
            
            startDialogue(this, 2);
        }
    }
}
