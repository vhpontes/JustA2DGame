/*
Code based in RyiSnow youtube channel:
https://www.youtube.com/c/RyiSnow
*/
 
package tile_interactive;

import entity.Entity;
import java.awt.Color;
import main.GamePanel;

public class IT_DestructableWall extends InteractiveTile {
    
    GamePanel gp;
    public static final String itName = "Destructable Wall";
    
    public IT_DestructableWall(GamePanel gp, int col, int row){
        super(gp, col, row);
        this.gp = gp;
        
        name = itName;
        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;
        
        life = 3;
        down1 = setup("tile_interactive/destructiblewall", gp.tileSize, gp.tileSize);
        destructible = true;
    }
    
    public boolean isCorrectItem(Entity entity) {
        boolean isCorrectItem = false;
        
        if(entity.currentWeapon.type == type_pickaxe) {
            isCorrectItem = true;
        }
            
        return isCorrectItem;
    }
    
    public void playeSE() {
        gp.playSE(21);
    }
    
    public InteractiveTile getDestroyedForm() {
        InteractiveTile tile = null;
        return tile;
    }
    
    public Color getParticleColor() {
        Color color = new Color(65, 65, 65);
        return color;
    }
    
    public int getParticleSize() {
        int size = 6; // pixels
        return size;
    }
    
    public int getParticleSpeed() {
        int speed = 1;
        return speed;
    }
    
    public int getMaxLife() {
        int maxLife = 20; // frames count of particle
        return maxLife;
    }

//    public void checkDrop() {
//        
//        int i = new Random().nextInt(100)+1;
//        
//        if(i < 50) {
//            dropItem(new null);
//        }
//        if(i >= 50 && i < 75) {
//            dropItem(new OBJ_ManaCrystal(gp));
//        }
//        if(i >= 75 && i < 100) {
//            dropItem(new OBJ_ManaCrystal(gp));
//        }
//    }    
}