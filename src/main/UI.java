package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import objects.OBJ_Heart;
import entity.Entity;
import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import objects.OBJ_Coin_Bronze;
import objects.OBJ_ManaCrystal;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    private JComponent uiSnow = null;
    
    
    public Font maruMonica, purisaB, AdventureRequest, Britannian, Anglorunic,
            malveryFreeItalic, malveryFreeOutline, malveryFreeOutlineItalic, malveryFreeRegular;
    
    BufferedImage heart_full, heart_half, heart_blank, crystal_full, crystal_blank, coin_img;

    public boolean messageOn = false;
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
    public boolean gameFinished = false;
    public String currentDialogue = "";
    public int commandNum = 0;
    public int titleScreenState = 0; // 0 for first, 1 for secound screen
    public int playerSlotCol = 0;
    public int playerSlotRow = 0;
    public int npcSlotCol = 0;
    public int npcSlotRow = 0;
    int subState = 0;
    int counter = 0;
    public Entity npc;
    int charIndex = 0;
    String combinedText = "";
    
    public UI(GamePanel gp) {
        this.gp = gp;
        
        try {
            InputStream is = getClass().getResourceAsStream("/res/font/x12y16pxMaruMonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
            
            is = getClass().getResourceAsStream("/res/font/Purisa Bold.ttf");
            purisaB = Font.createFont(Font.TRUETYPE_FONT, is);

            is = getClass().getResourceAsStream("/res/font/AdventureRequest.ttf");
            AdventureRequest = Font.createFont(Font.TRUETYPE_FONT, is);

            is = getClass().getResourceAsStream("/res/font/Britannian.ttf");
            Britannian = Font.createFont(Font.TRUETYPE_FONT, is);
            
            is = getClass().getResourceAsStream("/res/font/Anglorunic.ttf");
            Anglorunic = Font.createFont(Font.TRUETYPE_FONT, is);
            
            is = getClass().getResourceAsStream("/res/font/MalveryFree-Italic.otf");
            malveryFreeItalic = Font.createFont(Font.PLAIN, is);
            is = getClass().getResourceAsStream("/res/font/MalveryFree-Outline.otf");
            malveryFreeOutline = Font.createFont(Font.PLAIN, is);
            is = getClass().getResourceAsStream("/res/font/MalveryFree-OutlineItalic.otf");
            malveryFreeOutlineItalic = Font.createFont(Font.PLAIN, is);
            is = getClass().getResourceAsStream("/res/font/MalveryFree-Regular.otf");
            malveryFreeRegular = Font.createFont(Font.PLAIN, is);
        } catch (IOException | FontFormatException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }

        // HUD OBJECT
        Entity heart = new OBJ_Heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;

        Entity crystal = new OBJ_ManaCrystal(gp);
        crystal_full = crystal.image;
        crystal_blank = crystal.image2;
        
        Entity coin = new OBJ_Coin_Bronze(gp);
        coin_img = coin.down1;
    }
    
    public void addMessage(String text) {

        message.add(text);
        messageCounter.add(0);
    }

    public void draw(Graphics2D g2) throws IOException {
        
        this.g2 = g2;
        
        g2.setFont(purisaB);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setColor(Color.white);
        
        // TITLE STATE
        if(gp.gameState == gp.titleState) {
            drawTitleScreen();
        }
        // PLAY STATE
        if(gp.gameState == gp.playState) {
            drawPlayerLife();
            drawPlayerMana();
            drawMessage();
        }
        // PAUSE STATE
        if(gp.gameState == gp.pauseState) {
            drawPlayerLife();
            drawPlayerMana();
            drawPauseScreen();
        }
        // DIALOGUE STATE
        if(gp.gameState == gp.dialogueState) {
            drawPlayerLife();
            drawPlayerMana();
            drawDialogueScreen();
        }
        // CHARACTER STATS STATE
        if(gp.gameState == gp.characterState) {
            drawCharacterScreen();
            drawInventory(gp.player, true);
        }
        // OPTIONS STATE
        if(gp.gameState == gp.optionState) {
            drawOptionScreen();
        }
        // GAME OVER STATE
        if(gp.gameState == gp.gameOverState) {
            drawGameOverScreen();
        }
        // TRANSITION STATE
        if(gp.gameState == gp.transitionState) {
            drawTransition();
        }
        // TRADE STATE
        if(gp.gameState == gp.tradeState) {
            drawTradeScreen();
        }
        // SLEEP STATE
        if(gp.gameState == gp.sleepState) {
            drawSleepScreen();
        }
        if(gp.gameState == gp.subState) {
            drawFirework(50, 5, 90);
        }
        // DEBUG STATE
        if(gp.gameState == gp.debugState) {
            drawDebug();
        }
    }

    public void drawPlayerLife() {

        int x = gp.tileSize/2;
        int y = gp.tileSize/2;
        int i = 0;

        // DRAW BLANK HEART
        while(i < gp.player.maxLife/2) {
            g2.drawImage(heart_blank, x, y, null);
            i++;
            x += gp.tileSize;
        }
        
        // RESET
        x = gp.tileSize/2;
        y = gp.tileSize/2;
        i = 0;
        
        // DRAW CURRENT LIFE
        while(i < gp.player.life) {
            g2.drawImage(heart_half, x, y, null);
            i++;
            if(i < gp.player.life) {
                g2.drawImage(heart_full, x, y, null);
            }
            i++;
            x += gp.tileSize;
        }
    }
    
    public void drawPlayerMana() {

        int x = (gp.tileSize/2);
        int y = (int)(gp.tileSize*1.5) + 5;
        int i = 0;

        // DRAW BLANK CRYSTAL
        while(i<gp.player.maxMana) {
            g2.drawImage(crystal_blank, x, y, null);
            i++;
            x += 35;
        }
        
        // RESET
        x = (gp.tileSize/2);
        y = (int)(gp.tileSize*1.5) + 5;
        i = 0;
        
        // DRAW CURRENT MANA
        while(i < gp.player.mana) {
            g2.drawImage(crystal_full, x, y, null);
            i++;
            x += 35;
        }
    }

    public void drawMessage() {
        
        g2.setFont(maruMonica);
        int messageX = gp.tileSize;
        int messageY= gp.tileSize*7;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 28));
        Font font = Font.decode("maruMonica");
        
        for(int i = 0; i < message.size(); i++) {
            
            if(message.get(i) != null) {
                Rectangle bounds = g2.getFontMetrics().getStringBounds(message.get(i), g2).getBounds();
                Color c = new Color(0,0,0,150);
                g2.setColor(c);
                g2.fillRoundRect(gp.tileSize, messageY-23, bounds.width, bounds.height, 5, 5);
                
                g2.setColor(Color.black);
                g2.drawString(message.get(i), messageX+2, messageY+2);
                g2.setColor(Color.green);
                g2.drawString(message.get(i), messageX, messageY);
                
                int counter = messageCounter.get(i) + 1;
                messageCounter.set(i, counter);
                messageY += 50;
                
                if(messageCounter.get(i) > 180) {
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
        }
    }
    
    public void drawTwitchSpeak() {

        g2.setFont(maruMonica);

        // WINDOW
        int x = gp.tileSize*2;
        int y = gp.tileSize/2;
        int width = gp.screenWidth - (gp.tileSize*4);
        int height = gp.screenHeight/3;
        
//        drawSubWindow(x, y, width, height);
        
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));
        x += gp.tileSize;
        y += gp.tileSize;
        
        for(String line : currentDialogue.split("\n")) {
            Rectangle bounds = g2.getFontMetrics().getStringBounds(line, g2).getBounds();
            Color c = new Color(0,0,0,150);
            g2.setColor(c);
            g2.fillRoundRect(x, y, bounds.width, bounds.height, 5, 5);
            
            g2.drawString(line, x, y);
            y += 40;
        }        
    }
    
    public void drawTitleScreen() {
        
        if(titleScreenState == 0) {
        
            //g2.setFont(Britannian);
            g2.setFont(Anglorunic);
            g2.setColor(new Color(50, 50, 50));
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

            //TITLE NAME
            g2.setFont(g2.getFont().deriveFont(Font.BOLD,96F));
            String text = "Just a 2D Game";
            int x = getXforCenteredText(text);
            int y = gp.tileSize*3;
            

            // SHADOW
            g2.setColor(Color.BLACK);
            g2.drawString(text, x+5, y+5);

            // MAIN COLOR
            g2.setColor(Color.white);
            g2.drawString(text, x, y);

            // TITLE IMAGE
            x = gp.screenWidth/2 - (gp.tileSize*2)/2;
            y += gp.screenHeight/18;
            g2.drawImage(gp.player.down1, x, y, gp.tileSize*2, gp.tileSize*2, null);

            // MENU
            g2.setFont(AdventureRequest);
            g2.setColor(Color.yellow);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 38F));

            text = "NEW Game";
            x = getXforCenteredText(text);
            y += gp.tileSize*4;
            g2.drawString(text, x, y);
            if(commandNum == 0) {
                g2.drawString(">", x-gp.tileSize, y);
            }

            text = "LOAD Game";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 1) {
                g2.drawString(">", x-gp.tileSize, y);
            }

            text = "QUIT";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 2) {
                g2.drawString(">", x-gp.tileSize, y);
            }
        }
/*        else if(titleScreenState == 1){
            
            g2.setColor(Color.yellow);
            g2.setFont(g2.getFont().deriveFont(42F));

            String text = "Select your class!";
            int x = getXforCenteredText(text);
            int y = gp.tileSize*3;
            g2.drawString(text, x, y);

            g2.setColor(Color.white);
            text = "Fighter";
            x = getXforCenteredText(text);
            y += gp.tileSize*3;
            g2.drawString(text, x, y);
            if(commandNum == 0) {
                g2.drawString(">", x-gp.tileSize, y);
            }
            
            text = "Thief";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 1) {
                g2.drawString(">", x-gp.tileSize, y);
            }
            
            text = "Sorcerer";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 2) {
                g2.drawString(">", x-gp.tileSize, y);
            }
            
            text = "Back";
            x = getXforCenteredText(text);
            y += gp.tileSize*2;
            g2.drawString(text, x, y);
            if(commandNum == 3) {
                g2.drawString(">", x-gp.tileSize, y);
            }
        }*/
    }
    
    public void drawPauseScreen() {

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight/2;
        
        g2.drawString(text, x, y);
    }
    
    public void drawDialogueScreen() {

        g2.setFont(maruMonica);

        // WINDOW
        int x = gp.tileSize*2;
        int y = gp.tileSize/2;
        int width = gp.screenWidth - (gp.tileSize*4);
        int height = gp.screenHeight/3;
        
        drawSubWindow(x, y, width, height);
        
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));
        x += gp.tileSize;
        y += gp.tileSize;
        
        if(npc.dialogues[npc.dialogueSet][npc.dialogueIndex] != null) {
            
            // Without effect letter to letter write in dialogue box
            //currentDialogue = npc.dialogues[npc.dialogueSet][npc.dialogueIndex];
            
            // Effect letter to letter write in dialogue box
            char characters[] = npc.dialogues[npc.dialogueSet][npc.dialogueIndex].toCharArray();
            
            if(charIndex < characters.length) {
                
                gp.playSE(18);
                String s = String.valueOf(characters[charIndex]);
                combinedText = combinedText + s;
                currentDialogue = combinedText;
                charIndex++;
            }
            
            if(gp.keyH.enterPressed == true) {
                
                charIndex = 0;
                combinedText = "";
                
                if(gp.gameState == gp.dialogueState) {
                    
                    npc.dialogueIndex++;
                    gp.keyH.enterPressed = false;
                }
            }
        }
        else { // if text is not in array (trade, twitch)
            npc.dialogueIndex = 0;
            
            if(gp.gameState == gp.dialogueState) {
                gp.gameState = gp.playState;
            }
        }
        
        for(String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 40;
        }
    }
    
    public void drawCharacterScreen() {
        

        
        // CREATE A FRAME
        final int frameX = gp.tileSize*2;
        final int frameY = gp.tileSize;
        final int frameWidth = gp.tileSize*5;
        final int frameHeight = gp.tileSize*10;
        
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);
        
        // TEXT
        g2.setColor(Color.white);
        g2.setFont(AdventureRequest);
        g2.setFont(g2.getFont().deriveFont(22F));
        
        int textX = frameX + 20;
        int textY = frameY + gp.tileSize;
        final int lineHeight = 35;
        
        // NAMES
        g2.drawString("Level", textX, textY); textY += lineHeight;
        g2.drawString("Life", textX, textY); textY += lineHeight;
        g2.drawString("Mana", textX, textY); textY += lineHeight;
        g2.drawString("Strength", textX, textY);textY += lineHeight;
        g2.drawString("Dexerty", textX, textY); textY += lineHeight;
        g2.drawString("Attack", textX, textY);textY += lineHeight;
        g2.drawString("Defense", textX, textY); textY += lineHeight;
        g2.drawString("Exp", textX, textY); textY += lineHeight;
        g2.drawString("Next Level", textX, textY); textY += lineHeight;
        g2.drawString("Coin", textX, textY); textY += lineHeight + 10;
        g2.drawString("Weapon", textX, textY); textY += lineHeight + 15;
        g2.drawString("Shield", textX, textY); textY += lineHeight;
        
        // VALUES
        int tailX = (frameX + frameWidth) - 30;
        textY = frameY + gp.tileSize;
        String value;
        
        value = String.valueOf(gp.player.level);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.life + "/" + gp.player.maxLife);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.mana + "/" + gp.player.maxMana);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.strength);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.dexterty);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.attack);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.defense);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.exp);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.nextLevelExp);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.coin);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize, textY-24, null);
        textY += gp.tileSize;
        g2.drawImage(gp.player.currentShield.down1, tailX - gp.tileSize, textY-24, null);

        // IMAGE
        int posX = gp.screenWidth / 2 - (gp.tileSize*5);
        int posY = gp.tileSize * 2;
        drawSubWindow(posX, posY-gp.tileSize, gp.tileSize * 4, gp.tileSize * 6);
        g2.drawImage(gp.player.down1, posX, posY, gp.tileSize * 4, gp.tileSize * 4, null);
        g2.drawImage(gp.player.left1, posX, posY, gp.tileSize * 4, gp.tileSize * 4, null);
        g2.drawImage(gp.player.up1, posX, posY, gp.tileSize * 4, gp.tileSize * 4, null);
        g2.drawImage(gp.player.right1, posX, posY, gp.tileSize * 4, gp.tileSize * 4, null);
    }
    
    public void drawGameOverScreen() {
        
        g2.setColor(new Color(0,0,0,150));
        g2.fillRect(0,0,gp.screenWidth, gp.screenHeight);
        
        int x;
        int y;
        String text;
        g2.setFont(maruMonica);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));
        
        text = "Game Over";
        // SHADOW TEXT
        g2.setColor(Color.black);
        x = getXforCenteredText(text);
        y = gp.tileSize * 4;
        g2.drawString(text, x + 4, y + 4);
        // MAIN TEXT
        g2.setColor(Color.white);
        g2.drawString(text, x, y);
        
        // OPTIONS ON GAME OVER
        g2.setFont(g2.getFont().deriveFont(50f));
        text = "Retry";
        x = getXforCenteredText(text);
        y += gp.tileSize * 4;
        g2.drawString(text, x, y);
        if(commandNum == 0) {
            g2.drawString(">", x-40, y);
        }
        
        text = "Quit";
        x = getXforCenteredText(text);
        y += 55;
        g2.drawString(text, x, y);
        if(commandNum == 1) {
            g2.drawString(">", x-40, y);
        }
    }
    
    public void drawTradeScreen() {
        
        switch(subState){
            case 0: trade_select();break;
            case 1: trade_buy();break;
            case 2: trade_sell();break;            
        }
        gp.keyH.enterPressed = false;
    }
    
    public void trade_select() {
        
        npc.dialogueSet = 0;
        drawDialogueScreen();
        
        // DRAW WINDOW
        int x = gp.tileSize * 15;
        int y = gp.tileSize * 4;
        int width = gp.tileSize * 3;
        int height = (int)(gp.tileSize * 3.5);
        drawSubWindow(x, y, width, height);
        
        // DRAW TEXTS
        x += gp.tileSize;
        y += gp.tileSize;
        g2.drawString("Buy", x, y);
        if(commandNum == 0) {
            g2.drawString(">", x-25, y);
            if(gp.keyH.enterPressed == true) {
                subState = 1;
            }
        }
        y += gp.tileSize;
        
        g2.drawString("Sell", x, y);
        if(commandNum == 1) {
            g2.drawString(">", x-25, y);
            if(gp.keyH.enterPressed == true) {
                subState = 2;
            }
        }
        y += gp.tileSize;

        g2.drawString("Leave", x, y);
        if(commandNum == 2) {
            g2.drawString(">", x-25, y);
            if(gp.keyH.enterPressed == true) {
                commandNum = 0;
                npc.startDialogue(npc, 1);
            }
        }
        y += gp.tileSize;
        
    }
    
    public void trade_buy() {
        
        // DRAW PLAYER INVENTORY
        drawInventory(gp.player, false);
        
        // DRAW NPC INVENTORY
        drawInventory(npc, true);
        
        // DRAW HINT WINDOW
        int x = gp.tileSize * 2;
        int y = gp.tileSize * 9;
        int width = gp.tileSize * 6;
        int height = gp.tileSize * 2;
        drawSubWindow(x, y , width, height);
        g2.drawString("[ESC] Back", x+24, y+60);
        
        // DRAW PLAYER COIN WINDOW
        x = gp.tileSize * 12;
        y = gp.tileSize * 9;
        width = gp.tileSize * 6;
        height = gp.tileSize * 2;
        drawSubWindow(x, y, width, height);
        g2.drawString("Your Coint: " + gp.player.coin, x+24, y+60);
        
        // DRAW NPC PRICE WINDOW
        int itemIndex = getItemIndexOnSlot(npcSlotCol, npcSlotRow);
        if(itemIndex < npc.inventory.size()){
            
            x = (int)(gp.tileSize * 5.5);
            y = (int)(gp.tileSize * 5.5);
            width = (int)(gp.tileSize * 2.5);
            height = gp.tileSize;
            drawSubWindow(x, y, width, height);
            g2.drawImage(coin_img, x+5, y+3, 40, 40, null);
            
            int price = npc.inventory.get(itemIndex).price;
            String text = "" + price;
            x = getXforAlignToRightText(text, gp.tileSize * 7);
            g2.drawString(text, x+34, y+34);
            
            // BUY A ITEM
            if(gp.keyH.enterPressed==true) {
                if(price > gp.player.coin) {
                    subState = 0;
                    npc.startDialogue(npc, 2);
                }
                else {
                    if(gp.player.canObtainItem(npc.inventory.get(itemIndex)) == true) {
                        gp.player.coin -= npc.inventory.get(itemIndex).price;
                    }
                    else {
                        subState = 0;
                        npc.startDialogue(npc, 3);
                    }
                }
            }
        }
    }
    
    public void trade_sell() {
        
        // DRAW PLAYER INVENTORY
        drawInventory(gp.player, true);
        
        int x;
        int y;
        int width;
        int height;
        
        // DRAW HINT WINDOW
        x = gp.tileSize * 2;
        y = gp.tileSize * 9;
        width = gp.tileSize * 6;
        height = gp.tileSize * 2;
        drawSubWindow(x, y , width, height);
        g2.drawString("[ESC] Back", x+24, y+60);
        
        // DRAW PLAYER COIN WINDOW
        x = gp.tileSize * 12;
        y = gp.tileSize * 9;
        width = gp.tileSize * 6;
        height = gp.tileSize * 2;
        drawSubWindow(x, y, width, height);
        g2.drawString("Your Coint: " + gp.player.coin, x+24, y+60);
        
        // DRAW NPC PRICE WINDOW
        int itemIndex = getItemIndexOnSlot(playerSlotCol, playerSlotRow);
        if(itemIndex < gp.player.inventory.size()){
            
            x = (int)(gp.tileSize * 15.5);
            y = (int)(gp.tileSize * 5.5);
            width = (int)(gp.tileSize * 2.5);
            height = gp.tileSize;
            drawSubWindow(x, y, width, height);
            g2.drawImage(coin_img, x+5, y+3, 40, 40, null);
            
            int price = gp.player.inventory.get(itemIndex).price;
            String text = "" + price;
            x = getXforAlignToRightText(text, gp.tileSize * 17);
            g2.drawString(text, x+34, y+34);
            
            // SELL A ITEM
            if(gp.keyH.enterPressed==true) {
                if(price > npc.coin) {
                    subState = 0;
                    npc.startDialogue(npc, 5);
                }
                else if(npc.inventory.size() == npc.maxInventorySize) {
                    subState = 0;
                    npc.startDialogue(npc, 6);
                }
                else if(gp.player.inventory.get(itemIndex) == gp.player.currentWeapon ||
                        gp.player.inventory.get(itemIndex) == gp.player.currentShield) {
                    subState = 0;
                    npc.startDialogue(npc, 4);
                }
                else {
                    if(gp.player.inventory.get(itemIndex).amount > 1) {
                        gp.player.inventory.get(itemIndex).amount--;
                        npc.inventory.add(gp.player.inventory.get(itemIndex));
                    }
                    else {
                        npc.inventory.add(gp.player.inventory.get(itemIndex));
                        gp.player.inventory.remove(itemIndex);
                    }
                    npc.coin -= price;
                    gp.player.coin += price;
                }
            }
        }
    }
    
    public void drawOptionScreen() throws IOException {
        
        g2.setColor(Color.white);
        g2.setFont(maruMonica);
        g2.setFont(g2.getFont().deriveFont(32F));
        
        // SUB WINDOW
        int frameX = gp.tileSize * 6;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 8;
        int frameHeight = gp.tileSize * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);
        
        switch(subState) {
            case 0: options_top(frameX, frameY); break;
            case 1: options_fullScreenNotification(frameX, frameY); break;
            case 2: controlOptions(frameX, frameY); break;
            case 3: options_endGameConfirmation(frameX, frameY); break;
        }
        
        gp.keyH.enterPressed = false;
    }
    
    public void options_top(int frameX, int frameY) throws IOException {
        
        int textX;
        int textY;
        
        // TITLE
        String text = "Options";
        textX = getXforCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);
        
        // FULL SCREEN ON/OFF
        textX = frameX + gp.tileSize;
        textY += gp.tileSize;
        g2.drawString("Full Screen", textX, textY);
        if(commandNum == 0) {
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed == true) {
                if(gp.fullScreenOn == true) {
                    gp.fullScreenOn = false;
                }
                else if(gp.fullScreenOn == false) {
                    gp.fullScreenOn = true;
                }
                subState = 1;
            }
        }
        // VOLUME MUSIC
        textY += gp.tileSize;
        g2.drawString("Music", textX, textY);
        if(commandNum == 1) {
            g2.drawString(">", textX-25, textY);
        }
        // VOLUME SE
        textY += gp.tileSize;
        g2.drawString("Sound Effects", textX, textY);
        if(commandNum == 2) {
            g2.drawString(">", textX-25, textY);
        }
        // CONTROL
        textY += gp.tileSize;
        g2.drawString("Control", textX, textY);
        if(commandNum == 3) {
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed == true) {
                subState = 2;
                commandNum = 0;
            }
        }
        // END GAME
        textY += gp.tileSize;
        g2.drawString("End Game", textX, textY);
        if(commandNum == 4) {
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed == true) {
                subState = 3;
                commandNum = 0;
                //System.exit(0);
            }
        }
        // BACK
        textY += gp.tileSize*2;
        g2.drawString("Back", textX, textY);
        if(commandNum == 5) {
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed == true) {
                gp.gameState = gp.playState;
            }
        }
        
        // FULL SCREEN CHECKBOX 
        textX = frameX + gp.tileSize * 5;
        textY = frameY + (gp.tileSize * 2) - gp.tileSize/2;
        
        g2.setStroke(new BasicStroke(2));
        g2.drawRect(textX, textY, gp.tileSize/2, gp.tileSize/2);
        if(gp.fullScreenOn == true) {
            g2.fillRect(textX+4, textY+4, 16, 16);
        }
        // MUSIC VOLUME 
        textY += frameY;
        g2.drawRect(textX, textY, gp.tileSize*3-22, gp.tileSize/2);
        int volumeWidth = 24 * gp.music.volumeScale;
        g2.setColor(Color.green);
        g2.fillRect(textX+1, textY+1, volumeWidth, 22);
        g2.setColor(Color.white);
        
        // SE VOLUME
        textY += frameY;
        g2.drawRect(textX, textY, gp.tileSize*3-22, gp.tileSize/2);
        volumeWidth = 24 * gp.se.volumeScale;
        g2.setColor(Color.red);
        g2.fillRect(textX+1, textY+1, volumeWidth, 22);
        g2.setColor(Color.white);
        
        gp.config.saveConfig();
    }
    
    public void options_fullScreenNotification(int frameX, int frameY) {
        
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize * 3;
        
        currentDialogue = "The change will take \neffect after restarting \nthe game.";
        
        for(String line: currentDialogue.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }
        
        // BACK
        textY += frameY + gp.tileSize;
        g2.drawString("Back", textX, textY);
        if(commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPressed == true) {
                subState = 0;
            }
        }
    }
    
    public void controlOptions(int frameX, int frameY) {
        
        int textX;
        int textY;
        
        // TITLE
        String text = "Control";
        textX = getXforCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);
        
        textX = frameX + gp.tileSize;
        textY += gp.tileSize;
        g2.drawString("Move", textX, textY); textY += gp.tileSize;
        g2.drawString("Confirm/Attack", textX, textY); textY += gp.tileSize;
        g2.drawString("Shoot/Cast", textX, textY); textY += gp.tileSize;
        g2.drawString("Character Screen", textX, textY); textY += gp.tileSize;
        g2.drawString("Pause", textX, textY); textY += gp.tileSize;
        g2.drawString("Options", textX, textY); textY += gp.tileSize;
        
        textX = frameX + gp.tileSize * 6;
        textY = frameY + gp.tileSize *2;
        g2.drawString("WASD", textX, textY); textY += gp.tileSize;
        g2.drawString("ENTER", textX, textY); textY += gp.tileSize;
        g2.drawString("SPACE", textX, textY); textY += gp.tileSize;
        g2.drawString("C", textX, textY); textY += gp.tileSize;
        g2.drawString("P", textX, textY); textY += gp.tileSize;
        g2.drawString("ESC", textX, textY); textY += gp.tileSize;

        // BACK
        textX = frameX + gp.tileSize;
        textY += frameY;
        g2.drawString("Back", textX, textY);
        if(commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPressed == true) {
                subState = 0;
            }
        }
    }
    
    public void options_endGameConfirmation(int frameX, int frameY) {
        
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize * 3;        
            
        currentDialogue = "Quit the game and \nreturn to the title screen?";
        
        for(String line: currentDialogue.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }
        
        // YES
        String text = "Yes";
        textX = getXforCenteredText(text);
        textY += gp.tileSize*3;
        g2.drawString(text, textX, textY);
        if(commandNum == 0) {
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed == true) {
                subState = 0;
                gp.gameState = gp.titleState;
                gp.resetGame(true);
                gp.stopMusic();
            }
        }
        // NO
        text = "No";
        textX = getXforCenteredText(text);
        textY += gp.tileSize;
        g2.drawString(text, textX, textY);
        if(commandNum == 1) {
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed == true) {
                subState = 0;
                commandNum = 4;
            }
        }        
    }

    public void drawDebug() {

        g2.setFont(maruMonica);
        int debugX = gp.tileSize;
        int debugY= gp.tileSize*5;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24F));

        drawSubWindow(debugX-20, debugY-40, gp.tileSize*3, gp.tileSize*2);
        g2.setColor(Color.black);
        g2.drawString("Player X:" + gp.player.worldX/gp.tileSize, debugX+2, debugY+2);
        g2.drawString("Player Y:" + gp.player.worldY/gp.tileSize, debugX+2, debugY+36);
        g2.setColor(Color.yellow);
        g2.drawString("Player X:" + gp.player.worldX/gp.tileSize, debugX, debugY);
        g2.drawString("Player Y:" + gp.player.worldY/gp.tileSize, debugX, debugY+34);
    }
    
    public void drawInventory(Entity entity, boolean cursor) {
        
        int frameX;
        int frameY;
        int frameWidth;
        int frameHeight;
        int slotCol;
        int slotRow;
        
        if(entity == gp.player) {
            frameX = gp.tileSize*12;
            frameY = gp.tileSize;
            frameWidth = gp.tileSize*6;
            frameHeight = gp.tileSize*5;
            slotCol = playerSlotCol;
            slotRow = playerSlotRow;
        }
        else {
            frameX = gp.tileSize*2;
            frameY = gp.tileSize;
            frameWidth = gp.tileSize*6;
            frameHeight = gp.tileSize*5;
            slotCol = npcSlotCol;
            slotRow = npcSlotRow;
        }
        
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);
        
        // INVENTORY SLOTS
        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;
        int slotSize = gp.tileSize + 2;
        
        // DRAW PLAYER'S ITEMS
        for(int i = 0; i < entity.inventory.size(); i++) {
            
            // EQUIP CURSOR
            if(entity.inventory.get(i) == entity.currentWeapon ||
                    entity.inventory.get(i) == entity.currentShield ||
                    entity.inventory.get(i) == entity.currentLight) {
                
                g2.setColor(new Color(240, 190, 90)); //soft yellow
                g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
            }
            
            g2.drawImage(entity.inventory.get(i).down1, slotX, slotY, null);
            
            // DISPLAY AMOUNT
            if(entity.inventory.get(i).amount > 1) {
                g2.setFont(maruMonica);
                g2.setFont(g2.getFont().deriveFont(28F));
                int amountX;
                int amountY;
                
                String s = "x" + entity.inventory.get(i).amount;
                amountX = getXforAlignToRightText(s, slotX + 44);
                amountY = slotY + gp.tileSize;
                g2.setColor(new Color(60,60,60));
                g2.drawString(s, amountX, amountY);
                g2.setColor(Color.white);
                g2.drawString(s, amountX-2, amountY-2);
            }
            
            slotX += slotSize;
            
            if(i == 4 || i == 9 || i == 14) {
                slotX = slotXstart;
                slotY += slotSize;
            }
        }
        
        // CURSOR
        if(cursor == true) {
            int cursorX = slotXstart + (slotSize * slotCol);
            int cursorY = slotYstart + (slotSize * slotRow);
            int cursorWidth = gp.tileSize;
            int cursorHeight = gp.tileSize;

            g2.setColor(Color.white);
            g2.setStroke(new BasicStroke(3));
            g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

            // DRAW ITEM DESCRIPTION
            int dFrameX = frameX;
            int dFrameY = frameY + frameHeight;
            int dFrameWidth = frameWidth;
            int dFrameHeight = gp.tileSize * 3;

            // DRAW DESCRIPTION TEXT
            int textX = dFrameX + 20;
            int textY = dFrameY + gp.tileSize;
            g2.setFont(maruMonica);
            g2.setFont(g2.getFont().deriveFont(28F));
            int itemIndex = getItemIndexOnSlot(slotCol, slotRow);

            if(itemIndex < entity.inventory.size()) {

                drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);
                
                if(entity.inventory.get(itemIndex).description == null) {
                    g2.drawString("", textX, textY);
                } else
                for(String line: entity.inventory.get(itemIndex).description.split("\n")) {
                    g2.drawString(line, textX, textY);
                    g2.setColor(Color.green);
                    textY += 32;
                }
            }
        }
    }
    
    public void drawSleepScreen() {
        
        counter++;
        
        if(counter < 120) {
            gp.eManager.lighting.filterAlpha += 0.01f;
            if(gp.eManager.lighting.filterAlpha > 1f) {
                gp.eManager.lighting.filterAlpha = 1f;
            }
        }
        if(counter >= 120) {
            gp.eManager.lighting.filterAlpha -= 0.01f;
            if(gp.eManager.lighting.filterAlpha <= 0f) {
                gp.eManager.lighting.filterAlpha = 0f;
                counter = 0;
                gp.eManager.lighting.dayState = gp.eManager.lighting.day;
                gp.eManager.lighting.dayCounter = 0;
                gp.gameState = gp.playState;
                gp.player.getImage();
            }
        }
    }

    public int getItemIndexOnSlot(int slotCol, int slotRow) {
        
        int intemIndex = slotCol + (slotRow*5);
        return intemIndex;
    }
    
    public void drawSubWindow(int x, int y, int width, int height){
        
        Color c = new Color(0,0,0,200);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);
        
        c = new Color(255,255,255); //white
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
    }
    
    public void drawTransition() {
        
        counter++;
        g2.setColor(new Color(0,0,0,counter*5)); // change alpha value
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        
        if(counter == 50){
            counter = 0;
            gp.gameState = gp.playState;
            gp.currentMap = gp.eHandler.tempMap;
            gp.player.worldX = gp.tileSize * gp.eHandler.tempCol;
            gp.player.worldY = gp.tileSize * gp.eHandler.tempRow;
            gp.eHandler.previousEventX = gp.player.worldX;
            gp.eHandler.previousEventY = gp.player.worldY;
            gp.changeArea();
        }
    }
    
    public int getXforCenteredText(String text){
        
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - length/2;
        return x;
    }
    
    public int getXforAlignToRightText(String text, int tailX){
        
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = tailX - length;
        return x;
    }
    
    public void drawFirework(int time, double speed, int angle) {
        g2.drawRect(200, 200, 40, 40);//check paint component works 
        g2.drawOval(300, 300, 30, 30);

        for (int i = 0; i < time; i++) {

            int x1 = (int) (1.0 * (speed) * (Math.cos(angle)) * i);
            int y1 = (int) ((1.0 * (speed) * (Math.sin(angle)) * i) - ((0.5) * (9.8) * (i*i)));

            int x2 = (int) (1.0 * (speed) * (Math.cos(angle)) * (i+1));
            int y2 = (int) ((1.0 * (speed) * (Math.sin(angle)) * i+1) - ((0.5) * (9.8) * ((i+1)*(i+1))));

            g2.setColor (Color.ORANGE);
            g2.drawLine(x1,y1, x2, y2);  
        }        
    }

}