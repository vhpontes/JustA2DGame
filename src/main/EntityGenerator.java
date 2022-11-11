package main;

import entity.Entity;
import objects.OBJ_Axe;
import objects.OBJ_Boots;
import objects.OBJ_Chest;
import objects.OBJ_Coin_Bronze;
import objects.OBJ_Door;
import objects.OBJ_Door_Iron;
import objects.OBJ_Fireball;
import objects.OBJ_Heart;
import objects.OBJ_Key;
import objects.OBJ_Lantern;
import objects.OBJ_ManaCrystal;
import objects.OBJ_Pickaxe;
import objects.OBJ_Potion_Red;
import objects.OBJ_Rock;
import objects.OBJ_Shield_Blue;
import objects.OBJ_Shield_Wood;
import objects.OBJ_Sword_Normal;
import objects.OBJ_Tent;

public class EntityGenerator {
    
    GamePanel gp;
    
    public EntityGenerator(GamePanel gp) {
        this.gp = gp;
    }
    
    public Entity getObject(String itemName) {
        
        Entity obj = null;
        
        switch(itemName) {
            case OBJ_Axe.objName: obj = new OBJ_Axe(gp); break; 
            case OBJ_Boots.objName: obj = new OBJ_Boots(gp); break; 
            case OBJ_Chest.objName: obj = new OBJ_Chest(gp); break; 
            case OBJ_Coin_Bronze.objName: obj = new OBJ_Coin_Bronze(gp); break; 
            case OBJ_Door.objName: obj = new OBJ_Door(gp); break;
            case OBJ_Door_Iron.objName: obj = new OBJ_Door_Iron(gp); break;
            case OBJ_Fireball.objName: obj = new OBJ_Fireball(gp); break;
            case OBJ_Heart.objName: obj = new OBJ_Heart(gp); break;
            case OBJ_Key.objName: obj = new OBJ_Key(gp); break; 
            case OBJ_Lantern.objName: obj = new OBJ_Lantern(gp); break; 
            case OBJ_ManaCrystal.objName: obj = new OBJ_ManaCrystal(gp); break; 
            case OBJ_Pickaxe.objName: obj = new OBJ_Pickaxe(gp); break; 
            case OBJ_Potion_Red.objName: obj = new OBJ_Potion_Red(gp); break; 
            case OBJ_Rock.objName: obj = new OBJ_Rock(gp); break; 
            case OBJ_Shield_Blue.objName: obj = new OBJ_Shield_Blue(gp); break; 
            case OBJ_Shield_Wood.objName: obj = new OBJ_Shield_Wood(gp); break; 
            case OBJ_Sword_Normal.objName: obj = new OBJ_Sword_Normal(gp); break; 
            case OBJ_Tent.objName: obj = new OBJ_Tent(gp); break;
        }
        
        return obj;
    }    
}
