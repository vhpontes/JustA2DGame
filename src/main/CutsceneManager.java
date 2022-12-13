/*
Code based in RyiSnow youtube channel:
https://www.youtube.com/c/RyiSnow
*/

package main;

import entity.PlayerDummy;
import java.awt.Graphics2D;
import monster.MON_SkeletonLord;
import objects.OBJ_Door_Iron;

public class CutsceneManager {
    
    GamePanel gp;
    Graphics2D g2;
    
    public int sceneNum;
    public int scenePhase;
    
    // Scene Number
    public final int NA = 0;
    public final int skeletonLord = 1;
    
    public CutsceneManager(GamePanel gp) {
        
        this.gp = gp;
    }
    
    public void draw(Graphics2D g2) {
        
        this.g2 = g2;
        
        switch(sceneNum) {
            case skeletonLord -> scene_skeletonLord();
        }
    }
    
    public void scene_skeletonLord() {
        
        if(scenePhase == 0) {
            
            gp.bossBattleOn = true;
            
            // Shut the iron door
            for(int i = 0; i < gp.obj[1].length; i++) {
                
                if(gp.obj[gp.currentMap][i] == null) {
                    gp.obj[gp.currentMap][i] = new OBJ_Door_Iron(gp);
                    gp.obj[gp.currentMap][i].worldX = gp.tileSize * 25;
                    gp.obj[gp.currentMap][i].worldY = gp.tileSize * 28;
                    gp.obj[gp.currentMap][i].temp = true;
                    gp.playSE(22);
                    break;
                }
            }
            
            // Search a vacant slot for the npc Dummy
            for(int i = 0; i < gp.npc[1].length; i++) {
                
                if(gp.npc[gp.currentMap][i] == null) {
                    gp.npc[gp.currentMap][i] = new PlayerDummy(gp);
                    gp.npc[gp.currentMap][i].worldX = gp.player.worldX;
                    gp.npc[gp.currentMap][i].worldY = gp.player.worldY;
                    gp.npc[gp.currentMap][i].direction = gp.player.direction;
                    break;
                }
            }
            
            gp.player.drawing = false;
            
            scenePhase++;
        }
        
        if(scenePhase == 1) {
            
            // Move camera to Boss
            gp.player.worldY -= 4;
            
            if(gp.player.worldY < gp.tileSize * 17) {
                scenePhase++;
            }
        }

        if(scenePhase == 2) {
            
            // Search the Boss
            for(int i = 0; i < gp.monster[1].length; i++) {
                
                if(gp.monster[gp.currentMap][i] != null && 
                        gp.monster[gp.currentMap][i].name == MON_SkeletonLord.monName) {
                    
                    gp.monster[gp.currentMap][i].sleep = false;
                    gp.ui.npc = gp.monster[gp.currentMap][i];
                    
                    scenePhase++;
                    break;
                }
            }
        }
        
        if(scenePhase == 3) {
            
            // The boss speak
            gp.ui.drawDialogueScreen();
        }
        
        if(scenePhase == 4) {
            
            // Return camera to player
            for(int i = 0; i < gp.npc[1].length; i++) {
                
                if(gp.npc[gp.currentMap][i] != null &&
                       gp.npc[gp.currentMap][i].name.equals(PlayerDummy.npcName)) {
                    
                    // Restore the player position
                    gp.player.worldX = gp.npc[gp.currentMap][i].worldX;
                    gp.player.worldY = gp.npc[gp.currentMap][i].worldY;
                    // Delete Dummy
                    gp.npc[gp.currentMap][i] = null;
                    break;
                }
            }
            
            // Restart the player draw
            gp.player.drawing = true;
            
            // Reset
            sceneNum = 0;
            scenePhase = 0;
            gp.gameState = gp.playState;
            
            // Change the music
            gp.stopMusic();
            gp.playMusic(24);
        }
    }
    
}
