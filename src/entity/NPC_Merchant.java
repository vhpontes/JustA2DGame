package entity;

import main.GamePanel;
import objects.OBJ_Axe;
import objects.OBJ_Key;
import objects.OBJ_Potion_Red;
import objects.OBJ_Shield_Blue;
import objects.OBJ_Shield_Wood;
import objects.OBJ_Sword_Normal;
import objects.OBJ_Tent;

public class NPC_Merchant extends Entity{

    public NPC_Merchant(GamePanel gp){
        super(gp);
        
        direction = "down";
        speed = 1;
        coin = 1000;
        
        getImage();
        setItems();
        setDialogue("br");
    }

    public void getImage() {
        
        up1 = setup("npc/merchant_down_1", gp.tileSize, gp.tileSize);
        up2 = setup("npc/merchant_down_2", gp.tileSize, gp.tileSize);
        down1 = setup("npc/merchant_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("npc/merchant_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("npc/merchant_down_1", gp.tileSize, gp.tileSize);
        left2 = setup("npc/merchant_down_2", gp.tileSize, gp.tileSize);
        right1 = setup("npc/merchant_down_1", gp.tileSize, gp.tileSize);
        right2 = setup("npc/merchant_down_2", gp.tileSize, gp.tileSize);
    }
    
    public void setDialogue(String lang){
        switch(lang) {
            case "en" -> {
                dialogues[0][0] = "So you found me.\nI have some good stuff.\nDo you want trade?";
                dialogues[1][0] = "Come back later, young man!";
                dialogues[2][0] = "You don't have necessary coins to buy this item!";
                dialogues[3][0] = "You don't carry any more! You fool.";
                dialogues[4][0] = "You can´t sell equiped items!";
                dialogues[5][0] = "I haven't necessary coins to buy this item!";
                dialogues[6][0] = "I don't carry any more! Sorry.";
            }
            case "br" -> {
                dialogues[0][0] = "Então você me achou.\nEu tenho algumas coisas boas aqui.\nVocê gostaria de negociar?";
                dialogues[1][0] = "Volte mais tarde, jovem!";
                dialogues[2][0] = "Você não tem moedas necessárias para comprar este item!";
                dialogues[3][0] = "Você não carrega mais! Seu idiota.";
                dialogues[4][0] = "Você não pode vender itens equipados!";
                dialogues[5][0] = "Não tenho moedas necessárias para comprar este item!";
                dialogues[6][0] = "Eu não carrego mais! Desculpe.";
            }
        }
    }
    
    public void setItems() {
        inventory.add(new OBJ_Potion_Red(gp));
        inventory.add(new OBJ_Key(gp));
        inventory.add(new OBJ_Shield_Wood(gp));
        inventory.add(new OBJ_Shield_Blue(gp));
        inventory.add(new OBJ_Sword_Normal(gp));
        inventory.add(new OBJ_Axe(gp));
        inventory.add(new OBJ_Tent(gp));
        inventory.add(new OBJ_Tent(gp));
        inventory.add(new OBJ_Tent(gp));
    }
    
    public void speak() {
        
        facePlayer();
        gp.gameState = gp.tradeState;
        gp.ui.npc = this;
    }
}
