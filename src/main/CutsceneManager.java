/*
Code based in RyiSnow youtube channel:
https://www.youtube.com/c/RyiSnow

Class Code modifications and addons:
Victor Hugo Manata Pontes
https://www.twitch.tv/lechuck311
https://www.youtube.com/@victorhugomanatapontes
https://www.youtube.com/@dtudoumporco
*/

package main;

import entity.PlayerDummy;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import monster.MON_SkeletonLord;
import objects.OBJ_BlueHeart;
import objects.OBJ_Door_Iron;

public class CutsceneManager {
    
    GamePanel gp;
    Graphics2D g2;
    
    public int sceneNum;
    public int scenePhase;
    int counter = 0;
    float alpha = 0f;
    int y;
    String endCredit;
    
    // Scene Number
    public final int NA = 0;
    public final int skeletonLord = 1;
    public final int ending = 2;
    
    public CutsceneManager(GamePanel gp) {
        
        this.gp = gp;
        
        endCredit = "Credits\n\n\n"
                + "Game Code based on RyiSnow Youtube Channel\n"
                + "https://www.youtube.com/c/RyiSnow\n\n"
                + "Music:\nRyiSnow\n\n"
                + "PixelArt:\nVictor Hugo Manata Pontes\nZuhria A\n"
                + "\n\n\n\n\n\n\n\n\n\n\n"
                + "Special Thanks to:\n\n\n"
                + "Marcus Vinícius Gouveia Loureço (Java Code Helper)\n\n\n"
                + "Joao Vitor Marcilli Bertho (Tester, Twitch Sub)\n\n\n"
                + "wldomiciano (Ideas, Twitch Sub)\n\n\n"
                + "aspcontroler (Ideas, Twitch Sub)\n\n\n"
                + "Rogério Guedes 'foda-se' (Twitch Sub)\n\n\n"
                + "Marcus Vinícius Ribeiro (Twitch Sub)\n\n\n"
                + "Danilo Muniz (Twitch Sub)\n\n\n"
                + "Bahianinho 'depois troco o nome!!!' (Twitch Sub)\n\n\n"
                ;
    }
    
    public void draw(Graphics2D g2) {
        
        this.g2 = g2;
        
        switch(sceneNum) {
            case skeletonLord -> scene_skeletonLord();
            case ending -> scene_ending();
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
    
    public void scene_ending() {
        
        String text = "";
        

        if(scenePhase == 0) {
            
            gp.stopMusic();
            gp.ui.npc = new OBJ_BlueHeart(gp);
            scenePhase++;
        }
        if(scenePhase == 1) {
            
            gp.ui.drawDialogueScreen();
        }
        if(scenePhase == 2) {
            
            gp.playSE(4);
            scenePhase++;
        }
        if(scenePhase == 3) {
            
            if(counterReached(100) == true) {
                scenePhase++;
            }
        }
        if(scenePhase == 4) {
            
            alpha += 0.005f;
            if(alpha > 1f) {
                alpha = 1f;
            }
            drawBlackBackground(alpha);
            
            if(alpha == 1f) {
                alpha = 0;
                scenePhase++;
            }
        }
        if(scenePhase == 5) {
            
            drawBlackBackground(1f);
            
            alpha += 0.005f;
            if(alpha > 1f) {
                alpha = 1f;
            }
            
            switch(gp.language) {
                case "en":
                    text = "After the fierce battle with the Skeleton Lord,\n"
                            + "the Hero finally found the legendary treasure.\n"
                            + "But this is not the end of his jorney.\n"
                            + "The Hero's adventure has just begun.";
                    break;
                case "cn":
                    text = "在与骷髅领主激战之后,\n"
                            + "英雄终于找到了传说中的宝藏。\n"
                            + "但这不是他旅程的终点。\n"
                            + "英雄的冒险才刚刚开始。";
                    break;
                case "br":
                    text = "Após a feroz batalha com o Skeleton Lord,\n"
                            + "o Herói finalmente encontrou o lendário tesouro.\n"
                            + "Mas este não é o fim de sua jornada.\n"
                            + "A aventura do Herói apenas começou..";                
                    break;
            }
            
            drawString(alpha, 38f, 200, text, 70);
            
            if(counterReached(600) == true) {
                gp.playMusic(0);
                scenePhase++;
            }
        }
        if(scenePhase == 6) {
            
            drawBlackBackground(1f);
            
            drawString(1f, 120f, gp.screenHeight / 2, "Just a 2D Game", 40);
            
            if(counterReached(300) == true) {
                gp.playMusic(0);
                scenePhase++;
            }            
        }
        if(scenePhase == 7) {
            
            y = gp.screenHeight / 2;
            
            drawBlackBackground(1f);
            
            drawString(1f, 38, y, endCredit, 40);
            
            if(counterReached(300) == true) {
                scenePhase++;
            }             
        }
        if(scenePhase == 8) {
            
            drawBlackBackground(1f);
            
            y--;
            drawString(1f, 38, y, endCredit, 40);
        }
    }
    
    public boolean counterReached(int target) {
        
        boolean counterReached = false;
        
        counter++;
        if(counter > target) {
            counterReached = true;
            counter = 0;
        }
        
        return counterReached;
    }
    
    public void drawBlackBackground(float alpha) {
        
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2.setColor(Color.black);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
    
    public void drawString(float alpha, float fontSize, int y, String text, int lineHeight) {
        
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(fontSize));
        
        for(String line: text.split("\n")) {
            int x = gp.ui.getXforCenteredText(line);
            g2.drawString(line, x, y);
            y += lineHeight;
        }
        
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
}
