package objects;

import entity.Entity;
import java.util.ArrayList;
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
        image = setup("objects/chest", gp.tileSize, gp.tileSize);        
        image2 = setup("objects/chest_opened", gp.tileSize, gp.tileSize);
        down1 = image;
        collision = true;
    
        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 40;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
                
    }
    
    public void setLoot(Entity loot) {
        
        this.loot = loot;
        setItems(loot);
        setDialogue();
    }

    public void setItems(Entity loot_p) {
        this.inventory.add(loot_p);
    }
    
    public void setDialogue() {
        
        dialogues[0][0] = "You open the chest and find a "+ loot.name + "!\n...But you not carry any more!";
        dialogues[1][0] = "You open the chest and find a "+ loot.name + "!\nYou obtain the "+ loot.name +"!";
        dialogues[2][0] = "It's empty";
    }    

    public void interact() {
        ArrayList<Entity> inventoryTemp = new ArrayList<>();
        
        if(opened == false) {
            
            gp.playSE(3);

            System.out.println("Player Inventory Size: "+gp.player.inventory.size());
            System.out.println("Chest  Inventory Size: "+this.inventory.size());
            System.out.println("---------------------------------------");
            
            int chestSize = this.inventory.size();
            for(int i = 0; i < this.inventory.size(); i++) {
                if(gp.player.canObtainItem(this.inventory.get(i)) == false) {

                    startDialogue(this, 0);
                }
                else {
                    gp.player.inventory.add(this.inventory.get(i));
                    //gp.player.inventory.add(gp.eGenerator.getObject(this.inventory.get(0).name));
                    
                    //this.inventory.remove(0);
                    //this.inventory.remove(i);
                    //this.inventory.remove(gp.eGenerator.getObject(this.inventory.get(i).name));
                    //gp.player.inventory.get(i).amount = this.inventory.get(i).amount;
                    startDialogue(this, 1);
                    down1 = image2;
                    opened = true;

                    System.out.println(" - Add item  : "+this.inventory.get(i).name+" ("+this.inventory.get(i).amount+")");
                    System.out.println("Chest Size: "+inventory.size());
                    System.out.println("Player Inventory Size: "+gp.player.inventory.size());
                }
                total ++;
            }
            System.out.println("Chest  Inventory Size: "+this.inventory.size());
            System.out.println("---------------------------------------");
            System.out.println("Total Itens Add: " + total);
        }
        else {
            
            startDialogue(this, 2);
        }
    }

/*    
    public void interact_old() {
        
        if(opened == false) {
            
            gp.playSE(3);
            
            if(gp.player.canObtainItem(loot) == false) {
                
                startDialogue(this, 0);
            }
            else {
                
                startDialogue(this, 1);
                down1 = image2;
                opened = true;
            }
        }
        else {
            
            startDialogue(this, 2);
        }
    }  
    */  
}
