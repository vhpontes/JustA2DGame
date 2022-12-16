/*
Code based in RyiSnow youtube channel:
https://www.youtube.com/c/RyiSnow
*/

package main;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.TimeUnit;

public class KeyHandler implements KeyListener{

    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed, 
            enterPressed, shotKeyPressed, shiftPressed;
    
    public boolean godModeOn = false;
            
    // DEBUG
    boolean showDebugText = false;
    
    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
        int code = e.getKeyCode();
        
        // TITLE STATE
        if(gp.gameState == gp.titleState) {
            titleState(code);
        }        
        // PLAY STATE
        else if(gp.gameState == gp.playState){
            playState(code);
        }
        // PAUSE STATE
        else if(gp.gameState == gp.pauseState) {
            pauseState(code);
        }
        // DIALOGUE STATE
        else if(gp.gameState == gp.dialogueState || gp.gameState == gp.cutsceneState) {
            dialogueState(code);
        }
        // CHARACTER STATE
        else if(gp.gameState == gp.characterState) {
            characterState(code);
        }
        // OPTIONS STATE
        else if(gp.gameState == gp.optionState) {
            optionState(code);
        }
        // GAME OVER STATE
        else if(gp.gameState == gp.gameOverState) {
            gameOverState(code);
        }
        // TRADE STATE
        else if(gp.gameState == gp.tradeState) {
            tradeState(code);
        }
        // MAP STATE
        else if(gp.gameState == gp.mapState) {
            mapState(code);
        }
        else if(gp.debugState == gp.debugState) {
            debugState(code);
        }
    }
    
    public void titleState(int code) {
        if(gp.ui.titleScreenState == 0) {
            if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                gp.ui.commandNum--;
                gp.playSE(9);
            }
            if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                gp.ui.commandNum++;
                gp.playSE(9);
            }
            if(code == KeyEvent.VK_ENTER) {
                if(gp.ui.commandNum == 0) {
//                    gp.ui.titleScreenState = 1; // Class choose screen
                    gp.gameState = gp.playState;
                    gp.playMusic(0);
                }
                if(gp.ui.commandNum == 1) {
                    gp.saveLoad.load();
                    gp.gameState = gp.playState;
                    gp.playMusic(0);
                }
                if(gp.ui.commandNum == 2) {
                    System.exit(0);
                }
            }
            if(gp.ui.commandNum > 2) {gp.ui.commandNum = 0;}
            if(gp.ui.commandNum < 0) {gp.ui.commandNum = 2;}
        }
/*        else if(gp.ui.titleScreenState == 1) {
            if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                gp.ui.commandNum--;
                if(gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 3;
                }
            }
            if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                gp.ui.commandNum++;
                if(gp.ui.commandNum > 3) {
                    gp.ui.commandNum = 0;
                }
            }
            if(code == KeyEvent.VK_ENTER) {
                if(gp.ui.commandNum == 0) {
                    gp.gameState = gp.playState;
                    gp.playMusic(0);
                }
                if(gp.ui.commandNum == 1) {
                    gp.gameState = gp.playState;
                    gp.playMusic(0);
                }
                if(gp.ui.commandNum == 2) {
                    gp.gameState = gp.playState;
                    gp.playMusic(0);
                }
                if(gp.ui.commandNum == 3) {
                    gp.ui.titleScreenState = 0;
                }
            }
        }*/
    }
    
    public void playState(int code) {
        if(code == KeyEvent.VK_0) {
        }
        if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            upPressed = true;
            gp.player.onPath = false;
        }
        if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            downPressed = true;         
            gp.player.onPath = false;
        }
        if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            leftPressed = true;            
            gp.player.onPath = false;
        }
        if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
            rightPressed = true;            
            gp.player.onPath = false;
        }
        if(code == KeyEvent.VK_P) {
            gp.gameState = gp.pauseState;
        }
        if(code == KeyEvent.VK_C) {
            gp.gameState = gp.characterState;
        }
        if(code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.optionState;
        }
        if(code == KeyEvent.VK_B) {
            gp.gameState = gp.debugState;
        }
        if(code == KeyEvent.VK_M) {
            gp.gameState = gp.mapState;
        }
        if(code == KeyEvent.VK_X) {
            if(gp.map.miniMapOn == false) {
                gp.map.miniMapOn = true;
            }
            else {
                gp.map.miniMapOn = false;
            }
        }
        if(code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
        if(code == KeyEvent.VK_SPACE) {
            shotKeyPressed = true;  
        }
        if(code == KeyEvent.VK_SHIFT) {
            shiftPressed = true;
        }

        //DEBUG
        if(code == KeyEvent.VK_T) {
            if(showDebugText == false){
                showDebugText = true;
            }
            else if(showDebugText == true){
                showDebugText = false;
            }
        }
        if(code == KeyEvent.VK_R) {
            //gp.currentMap = 1;
            switch(gp.currentMap) {
                case 0 -> gp.tileM.loadMap("/maps/worldV2.txt", 0);
                case 1 -> gp.tileM.loadMap("/maps/interior01.txt", 1);
                case 2 -> gp.tileM.loadMap("/maps/dg_twitch01.txt", 2);
            }
        }
        if(code == KeyEvent.VK_G) {
            if(godModeOn == false){
                godModeOn = true;
            }
            else if(godModeOn == true){
                godModeOn = false;
            }
        }
        if(code == KeyEvent.VK_F) {
            //gp.player.generateFirework(gp.player.projectile, gp.player, 0, 0);
            
            gp.player.makeFireworkShow(20);
        }
        if(code == KeyEvent.VK_ADD) {
            gp.player.speed++;
        }       
        if(code == KeyEvent.VK_SUBTRACT) {
            if(gp.player.speed > 0) {
                gp.player.speed--;
            }
        }       
                
        if(code == KeyEvent.VK_N) {
            gp.addNPCTwitch(gp.currentMap, null, gp.player.screenX + gp.tileSize, gp.player.screenY);
            try {
                TimeUnit.SECONDS.sleep(60);
            } catch (InterruptedException ex) {
                System.out.println(ex);
            }
        }
    }
    
    public void pauseState(int code) {

        if(code == KeyEvent.VK_P) {
            gp.gameState = gp.playState;
        }
    }
    
    public void dialogueState(int code) {
        
        if(code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }        
    }
    
    public void characterState(int code) {

        if(code == KeyEvent.VK_C) {
            gp.gameState = gp.playState;
        }
        if(code == KeyEvent.VK_ENTER) {
            gp.player.selectItem();
        }
        playerInventory(code);
    }
    
    public void optionState(int code) {
        
        if(code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.playState;
        }
        if(code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
        
        int maxCommandNum = 0;
        switch(gp.ui.subState){
            case 0: maxCommandNum = 5; break;
            case 3: maxCommandNum = 1; break;
        }
        // ARROW
        if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            gp.ui.commandNum--;
            gp.playSE(9);
            if(gp.ui.commandNum < 0) {
                gp.ui.commandNum = maxCommandNum;
            }
        }
        if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            gp.ui.commandNum++;
            gp.playSE(9);
            if(gp.ui.commandNum > maxCommandNum) {
                gp.ui.commandNum = 0;
            }
        }
        // MUSIC VOLUME 
        if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            if(gp.ui.subState == 0) {
                if(gp.ui.commandNum == 1 && gp.music.volumeScale > 0) {
                    gp.music.volumeScale--;
                    gp.music.checkVolume();
                    gp.playSE(9);
                }
                if(gp.ui.commandNum == 2 && gp.se.volumeScale > 0) {
                    gp.se.volumeScale--;
                    gp.playSE(9);
                }
            }
        }
        if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
            if(gp.ui.subState == 0) {
                if(gp.ui.commandNum == 1 && gp.music.volumeScale < 5) {
                    gp.music.volumeScale++;
                    gp.music.checkVolume();
                    gp.playSE(9);
                }
                if(gp.ui.commandNum == 2 && gp.se.volumeScale < 5) {
                    gp.se.volumeScale++;
                    gp.playSE(9);
                }
            }
        }   
    }
    
    public void gameOverState(int code) {
        
        if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            gp.ui.commandNum--;
            if(gp.ui.commandNum < 0) {
                gp.ui.commandNum = 1;
            }
            gp.playSE(9);
        }
        if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            gp.ui.commandNum++;
            if(gp.ui.commandNum > 1) {
                gp.ui.commandNum = 0;
            }
            gp.playSE(9);
        }
        if(code == KeyEvent.VK_ENTER) {
            if(gp.ui.commandNum == 0) {
                gp.gameState = gp.playState;
                gp.resetGame(false);
            }
            else if(gp.ui.commandNum == 1) {
                gp.gameState = gp.titleState;
                gp.resetGame(true);
            }
        }
    }

    public void tradeState(int code) {
        
        if(code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
        
        if(gp.ui.subState == 0) {
            if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                gp.ui.commandNum--;
                if(gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 2;
                }
                gp.playSE(9);
            }
            if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                gp.ui.commandNum++;
                if(gp.ui.commandNum > 2) {
                    gp.ui.commandNum = 0;
                }
                gp.playSE(9);
            }
        }
        if(gp.ui.subState == 1) {
            npcInventory(code);
            if(code == KeyEvent.VK_ESCAPE) {
                gp.ui.subState = 0;
            }
        }
        if(gp.ui.subState == 2) {
            playerInventory(code);
            if(code == KeyEvent.VK_ESCAPE) {
                gp.ui.subState = 0;
            }
        }
    }
    
    public void mapState(int code) {
        
        if(code == KeyEvent.VK_M) {
            gp.gameState = gp.playState;
        }
    }
    
    public void playerInventory(int code) {
        
        if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            if(gp.ui.playerSlotRow > 0) {
                gp.ui.playerSlotRow--;
                gp.playSE(9);
            }
        }
        if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            if(gp.ui.playerSlotRow < 3) {
                gp.ui.playerSlotRow++;
                gp.playSE(9);
            }
        }
        if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            if(gp.ui.playerSlotCol > 0) {
                gp.ui.playerSlotCol--;
                gp.playSE(9);
            }
        }
        if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
            if(gp.ui.playerSlotCol < 4) {
                gp.ui.playerSlotCol++;
                gp.playSE(9);
            }
        }        
    }
    
    public void npcInventory(int code) {
        
        if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            if(gp.ui.npcSlotRow > 0) {
                gp.ui.npcSlotRow--;
                gp.playSE(9);
            }
        }
        if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            if(gp.ui.npcSlotRow < 3) {
                gp.ui.npcSlotRow++;
                gp.playSE(9);
            }
        }
        if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            if(gp.ui.npcSlotCol > 0) {
                gp.ui.npcSlotCol--;
                gp.playSE(9);
            }
        }
        if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
            if(gp.ui.npcSlotCol < 4) {
                gp.ui.npcSlotCol++;
                gp.playSE(9);
            }
        }        
    }    
    
    public void debugState(int code) {

        if(code == KeyEvent.VK_B) {
            gp.gameState = gp.playState;
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        
        int code = e.getKeyCode();
        
         if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            upPressed = false;
        }
        if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            downPressed = false;         
        }
        if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            leftPressed = false;            
        }
        if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
           rightPressed = false;            
        }       
        if(code == KeyEvent.VK_ENTER) {
            enterPressed = false;
        }    
        if(code == KeyEvent.VK_SPACE) {
           shotKeyPressed = false;            
        }
        if(code == KeyEvent.VK_SHIFT) {
            shiftPressed = false;
        }        
    }
}