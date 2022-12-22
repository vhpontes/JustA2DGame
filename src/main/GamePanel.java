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

import ai.PathFinder;
import data.SaveLoad;
import entity.Entity;
import entity.NPC_Twitch;
import entity.Player;
import environment.EnvironmentManager;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.IOException;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import org.pircbotx.hooks.events.MessageEvent;
import tile.Map;
import tile.TileManager;
import tile_interactive.InteractiveTile;


public class GamePanel extends JPanel implements Runnable{
    
    final double PI = 3.1415926535;
    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16x16 original tile size
    final int scale = 3;
    
    // FPS CALC
    private static long lastFPSCheck = 0;
    private static int currentFPS = 0;
    private static int totalFrames = 0;
    
    
    public final int tileSize = originalTileSize * scale; // 48x48 tile size
    
    // 768 x 576 resolution
//    public final int maxScreenCol = 16; // Width 768 pixels
//    public final int maxScreenCol = 20; // Width 960 pixels
//    public final int maxScreenRow = 12; // Height 576 pixels

    // 1280 x 1024 resolution
//    public final int maxScreenCol = 22; // Width 1280 pixels
//    public final int maxScreenRow = 14; // Height 1024 pixels

    // 1920x1080 resolution
    public final int maxScreenCol = 24; // 40*48 Width 1920 pixels
    public final int maxScreenRow = 20; // 22*48 Height 1080 pixels

    // WORLD SETTINGS
    public int maxWorldCol = 50;
    public int maxWorldRow = 50;
    public int maxMap = 10;
    public int currentMap = 0;
    public final int maxUserTwitch = 10;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;
    
    // FULL SCREEN
        // GET MONITOR DEVICE DIMENSIONS
    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    int monitorWidth = gd.getDisplayMode().getWidth();
    int monitorHeight = gd.getDisplayMode().getHeight();

//    public int screenWidth = tileSize * maxScreenCol; 
//    public int screenHeight = tileSize * maxScreenRow;

    public int screenWidth = monitorWidth;
    public int screenHeight = monitorHeight;
    
    public boolean fullScreenOn;
    BufferedImage tempScreen;
    Graphics2D g2;
        
    // FPS
    int FPS = 60;
    
    // SYSTEM
    public KeyHandler keyH = new KeyHandler(this);
    public MouseHandler mouseH;
    
    Sound music = new Sound();
    Sound se = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSettler aSetter = new AssetSettler(this);
    public UI ui = new UI(this);
    public EventHandler eHandler = new EventHandler(this);
    Config config = new Config(this);
    public PathFinder pFinder = new PathFinder(this);
    EnvironmentManager eManager = new EnvironmentManager(this);
    Map map = new Map(this);
    SaveLoad saveLoad = new SaveLoad(this);
    public EntityGenerator eGenerator = new EntityGenerator(this);
    public CutsceneManager csManager = new CutsceneManager(this);
    Thread gameThread;

    // ENTITY AND OBJECT
    public Player player = new Player(this, keyH, mouseH);
    public NPC_Twitch npcTW = new NPC_Twitch(this);
    public Entity obj[][] = new Entity[maxMap][40]; 
    public Entity npc[][] = new Entity[maxMap][20];
    public Entity npcTwitch[][] = new Entity[maxMap][50];
    public Entity monster[][] = new Entity[maxMap][30];
    public InteractiveTile iTile[][] = new InteractiveTile[maxMap][60];
//    public ArrayList<Entity> projectileList = new ArrayList();
    public Entity projectile[][] = new Entity[maxMap][40];
    public Entity projectileWeapow[][] = new Entity[maxMap][40];
    public ArrayList<Entity> particleList = new ArrayList();
    public ArrayList<Entity> fireworkList = new ArrayList();
    ArrayList<Entity> entityList = new ArrayList();
    public ArrayList<Entity> npcTwitchList = new ArrayList();

    public TileManager tileM = new TileManager(this);
    
    // GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;
    public final int debugState = 5;
    public final int optionState = 6;
    public final int gameOverState = 7;
    public final int transitionState = 8;
    public final int tradeState = 9;
    public final int subState = 10;
    public final int sleepState = 11;
    public final int mapState = 12;
    public final int cutsceneState = 13;
    
    // OTHERS
    public boolean bossBattleOn = false;
    
    // GAME AREAS
    public int currentArea;
    public int nextArea;
    public final int outside = 50;
    public final int indoor = 51;
    public final int dungeon = 52;
    
    // INDEX NPC TWITCH
    private int npcTwitchIndex = 0;
    
   
    public GamePanel() {
        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D)tempScreen.getGraphics();

        this.mouseH = new MouseHandler(this, player);
        
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.addMouseListener (mouseH);
        this.setFocusable(true);
    }

    public void setupGame() {

        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        aSetter.setInteractiveTile();
        
        eManager.setup();
        
        gameState = titleState;
        //currentArea = outside;


        if(fullScreenOn == true) {
            setFullScreen();
        }
    }
    
    public void resetGame(boolean restart) {
        
        //currentArea = outside;
        gameState = transitionState;
        nextArea = currentArea;
        removeTempEntity();
        bossBattleOn = false;
        player.setDefaultPosition();
        player.restoreStatus();
        player.resetCounter();
        aSetter.setNPC();
        aSetter.setMonster();
        
        if(restart == true) {
            player.setDefaultValues();
            aSetter.setObject();
            aSetter.setInteractiveTile();
            eManager.lighting.resetDay();
        }
    }
    
    public void setFullScreen() {
        
        // GET LOCAL SCREEN DEVICE RESOLUTION
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(Main.window);
        
        // GET FULL SCREEN WIDTH AND HEIGHT
//        screenWidth2 = Main.window.getWidth();
//        screenHeight2 = Main.window.getHeight();
        screenWidth = Main.window.getWidth();
        screenHeight = Main.window.getHeight();
    }
    
    public void starGameThread() {
        
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void run() {
        
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;
        
        while(gameThread !=null) {
            // Calculate FPS
            totalFrames++;
            if(System.nanoTime() > lastFPSCheck + 1000000000) {
                lastFPSCheck = System.nanoTime();
                currentFPS = totalFrames;
                totalFrames = 0;
            }
            
            currentTime = System.nanoTime();
            
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            
            if (delta >= 1) {
                update();
                try {
                    drawToTempScreen(); // buffered
                } catch (IOException ex) {
                    Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                drawToScreen();     // buffered to screen
                delta--;
                drawCount++;
            }
            
            if (timer >= 1000000000) {
                drawCount = 0;
                timer = 0;
            }
        }
    }
    
    public void update() {

        if(gameState == playState) {
            // PLAYER
            player.update();
            
            // ENVIRONMENT
            eManager.update();

            // NPC
            for(int i = 0; i < npc[1].length; i++) {
                if(npc[currentMap][i] != null){
                    npc[currentMap][i].update();
                }
            }
            // NPC TWITCH
            for(int i = 0; i < npcTwitchList.size(); i++) {
                if(npcTwitchList.get(i) != null) {
                    if(npcTwitchList.get(i).alive == true) {
                        npcTwitchList.get(i).update();
                    }
                    if(npcTwitchList.get(i).alive == false) {
                        npcTwitchList.remove(i);
                    }
                }
            }
            // MONSTER
            for(int i = 0; i < monster[1].length; i++) {
                if(monster[currentMap][i] != null) {
                    if(monster[currentMap][i].alive == true && monster[currentMap][i].dying == false) {
                        monster[currentMap][i].update();
                    }
                    if(monster[currentMap][i].alive == false) {
                        int circle = 0;
                        int radius = 10 * monster[currentMap][i].numDrop;
                        for(int k=0; k < monster[currentMap][i].numDrop; k++) {
                                int angle = circle;
                                double x1 = radius * cos(angle * PI / 180);
                                double y1 = radius * sin(angle * PI / 180);
                                int lX = monster[currentMap][i].worldX/48 + (int)x1;
                                int lY = monster[currentMap][i].worldY/48 + (int)y1;
                                monster[currentMap][i].checkDrop(lX, lY);
                                circle += 360 / monster[currentMap][i].numDrop;
                                if(circle > 360) {
                                    circle = 0;
                                }
                            //monster[currentMap][i].checkDrop();
                        }
                        monster[currentMap][i] = null;
                    }
                }
            }
            // PROJECTILE
//            for(int i = 0; i < projectileList.size(); i++) {
//                if(projectileList.get(i) != null) {
//                    if(projectileList.get(i).alive == true) {
//                        projectileList.get(i).update();
//                    }
//                    if(projectileList.get(i).alive == false) {
//                        projectileList.remove(i);
//                    }
//                }
//            }
            for(int i = 0; i < projectileWeapow[1].length; i++) {
                if(projectileWeapow[currentMap][i] != null) {
                    if(projectileWeapow[currentMap][i].alive == true) {
                        projectileWeapow[currentMap][i].update();
                    }
                    if(projectileWeapow[currentMap][i].alive == false) {
                        projectileWeapow[currentMap][i] = null;
                    }
                }
            }
            for(int i = 0; i < projectile[1].length; i++) {
                if(projectile[currentMap][i] != null) {
                    if(projectile[currentMap][i].alive == true) {
                        projectile[currentMap][i].update();
                    }
                    if(projectile[currentMap][i].alive == false) {
                        projectile[currentMap][i] = null;
                    }
                }
            }
            // PARTICLES
            for(int i = 0; i < particleList.size(); i++) {
                if(particleList.get(i) != null) {
                    if(particleList.get(i).alive == true) {
                        particleList.get(i).update();
                    }
                    if(particleList.get(i).alive == false) {
                        particleList.remove(i);
                    }
                }
            }
            // FIREWORKS
            for(int i = 0; i < fireworkList.size(); i++) {
                if(fireworkList.get(i) != null) {
                    if(fireworkList.get(i).alive == true) {
                        fireworkList.get(i).update();
                    }
                    if(fireworkList.get(i).alive == false) {
                        fireworkList.remove(i);
                    }
                }
            }
            // INTERACTIVE TILES
            for(int i = 0; i < iTile[1].length; i++) {
                if(iTile[currentMap][i] != null) {
                    iTile[currentMap][i].update();
                }
            }
            
        }
        if(gameState == pauseState) {
            
        }
    }
    
    public void drawToTempScreen() throws IOException {
        
        switch (gameState) {
            // TITLE SCREEN
            case titleState:
                ui.draw(g2);
                break;
            // FULL MAP
            case mapState:
                map.drawFullMapScreen(g2);
                break;
            default:
                // TILE
                tileM.draw(g2);
                // INTERACTIVE TILE
                for(int i = 0; i < iTile[1].length; i++) {
                        if(iTile[currentMap][i] != null) {
                                iTile[currentMap][i].draw(g2);
                                }
                        }   
                // ADD ENTITIES TO THE LIST
                entityList.add(player);
                // NPC LIST
                for(int i = 0; i < npc[1].length; i++) {
                        if(npc[currentMap][i] != null) {
                                entityList.add(npc[currentMap][i]);
                                }
                        }   
                // OBJ LIST
                for(int i = 0; i < obj[1].length; i++) {
                        if(obj[currentMap][i] != null) {
                                entityList.add(obj[currentMap][i]);
                                }
                        }   
                // MONSTER LIST
                for(int i = 0; i < monster[1].length; i++) {
                        if(monster[currentMap][i] != null) {
                                entityList.add(monster[currentMap][i]);
                                }
                        }   
                // NPC TWITCH LIST
                for(int i = 0; i < npcTwitchList.size(); i++) {
                        if(npcTwitchList.get(i) != null) {
                                entityList.add(npcTwitchList.get(i));
                                }
                        }   
                // PARTICLE LIST
                for(int i = 0; i < particleList.size(); i++) {
                        if(particleList.get(i) != null) {
                                entityList.add(particleList.get(i));
                                }
                        }   
                // FIREWORK LIST
                for(int i = 0; i < fireworkList.size(); i++) {
                        if(fireworkList.get(i) != null) {
                                entityList.add(fireworkList.get(i));
                                }
                        }   
                // PROJECTILE WEAPOW LIST
                for(int i = 0; i < projectileWeapow[1].length; i++) {
                        if(projectileWeapow[currentMap][i] != null) {
                                entityList.add(projectileWeapow[currentMap][i]);
                                }
                        }   
                // PROJECTILE LIST
                for(int i = 0; i < projectile[1].length; i++) {
                    if(projectile[currentMap][i] != null) {
                        entityList.add(projectile[currentMap][i]);
                    }
                }   
                // SORT
                Collections.sort(entityList, new Comparator<Entity>() {
                        
                    @Override
                    public int compare(Entity e1, Entity e2) {

                        int result = Integer.compare(e1.worldY, e2.worldY);
                        return result;
                    }
                }); 
                // DRAW ENTITIES
                for(int i = 0; i < entityList.size(); i++) {
                    entityList.get(i).draw(g2);
                }   
                // EMPTY ENTITIES LIST
                entityList.clear();
                // ENVIRONMENT
                eManager.draw(g2);
                // MINI MAP
                map.drawMiniMap(g2);
                // CUTSCENE
                csManager.draw(g2);
                // UI
                ui.draw(g2);
                break;
        }
        // DEBUG START
        if(keyH.showDebugText == true || mouseH.showDebugText == true){
            drawDebugInfos();
        }
    }
    
    public void drawDebugInfos(){
        
        long drawStart = System.nanoTime();
        long drawEnd = System.nanoTime();
        long passed = drawEnd - drawStart;

        int x = this.tileSize / 4;
        int y = this.screenHeight / 4;
        int lineHeight = 20;

        ui.drawSubWindow(x-10, y-30, this.tileSize * 6, lineHeight * 27);
        
        g2.setFont(new Font("Arial", Font.PLAIN, 20));
        g2.setColor(Color.green);
        g2.drawString("Debug Infos", x, y); y += lineHeight;

        g2.setFont(new Font("Arial", Font.PLAIN, 18));
        g2.setColor(Color.white);
        g2.drawString("World X: "+ player.worldX, x, y); y += lineHeight;
        g2.drawString("World Y: "+ player.worldY, x, y); y += lineHeight;
        g2.drawString("Screen X: "+ player.screenX, x, y); y += lineHeight;
        g2.drawString("Screen Y: "+ player.screenY, x, y); y += lineHeight;
        g2.drawString("Mouse X: "+ mouseH.clickedX, x, y); y += lineHeight;
        g2.drawString("Mouse Y: "+ mouseH.clickedY, x, y); y += lineHeight;
        g2.drawString(" ", x, y); y += lineHeight;
        
        g2.drawString("Mouse Col: ", x, y);
        g2.setColor(Color.green);
        g2.drawString(""+ tileM.searchMouseCol, x+120, y); y += lineHeight;
        g2.setColor(Color.white);
        g2.drawString("Mouse Row: ", x, y);
        g2.setColor(Color.green);
        g2.drawString(""+ tileM.searchMouseCol, x+120, y); y += lineHeight;
        g2.setColor(Color.white);
        g2.drawString(" ", x, y); y += lineHeight;

        g2.setFont(new Font("Arial", Font.PLAIN, 20));
        g2.setColor(Color.green);
        g2.drawString("Path Finder", x, y); y += lineHeight;
        g2.setFont(new Font("Arial", Font.PLAIN, 18));
        g2.setColor(Color.white);
        g2.drawString("Start: "+ pFinder.startColVAR+":"+ pFinder.startRowVAR, x, y); y += lineHeight;
        //g2.drawString("Start Row    : "+ pFinder.startRowVAR, x, y); y += lineHeight;
        g2.drawString("Goal: "+ pFinder.goalColVAR+":"+ pFinder.goalRowVAR, x, y); y += lineHeight;
        g2.drawString("Reached: "+ pFinder.goalReached, x, y); y += lineHeight;
        
//            g2.drawString("Col: "+ (player.worldX * player.solidArea.x) / tileSize, x, y); y += lineHeight;
//            g2.drawString("Row: "+ (player.worldY * player.solidArea.y) / tileSize, x, y); y += lineHeight;
        g2.drawString(" ", x, y); y += lineHeight;
        g2.drawString("Draw Time: " + passed, x, y); y += lineHeight;

        g2.setColor(Color.red);
        g2.drawString("FPS: " + currentFPS, x, y); y += lineHeight; 

        g2.setColor(Color.white);
        g2.drawString("God Mode: " + keyH.godModeOn, x, y);        
    }
    
    public void drawToScreen() {
        
        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, screenWidth, screenHeight, null);
        g.dispose();
    }
    
    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }
    
    public void stopMusic() {
        music.stop();
    }
    
    public void playSE(int i) {
        se.setFile(i);
        se.play();
    }
    
    public void changeArea() {
        
        if(nextArea != currentArea) {
            
            stopMusic();
            
            if(nextArea == outside) {
                playMusic(0);
            }
            if(nextArea == indoor) {
                playMusic(20);
            }
            if(nextArea == dungeon) {
                playMusic(19);
            }
            
            aSetter.setNPC();
        }
        
        currentArea = nextArea;
        aSetter.setMonster();
    }
    
    public void addNPCTwitch(int mapNum, MessageEvent event, int pX, int pY, String name) {
//        Random random = new Random();
//        int X = random.nextInt(50)+1;
//        int Y = random.nextInt(50)+1;
    
        npcTwitch[mapNum][npcTwitchIndex] = new NPC_Twitch(this);
        
//        npcTwitch[mapNum][npcTwitchIndex].worldX = tileSize*pX;
//        npcTwitch[mapNum][npcTwitchIndex].worldY = tileSize*pY;
        npcTwitch[mapNum][npcTwitchIndex].worldX = pX; 
        npcTwitch[mapNum][npcTwitchIndex].worldY = pY;
        npcTwitch[mapNum][npcTwitchIndex].npcTwitchNick = name;
        if (event != null) {
            npcTwitch[mapNum][npcTwitchIndex].npcHashCode = event.getUser().hashCode();
        }
        npcTwitchList.add(npcTwitch[mapNum][npcTwitchIndex]);
        
        npcTwitchIndex++;
    }
    
    public NPC_Twitch getNPCTwitch(int npcHashCode) {
        
        for(Entity nt: npcTwitchList) {
            if(nt.npcHashCode == npcHashCode) {
                return (NPC_Twitch) nt;
            }
        }
        return null;
    }
    
    public void removeTempEntity() {
       
        for(int mapNum = 0; mapNum < maxMap; mapNum++) {
            
            for(int i = 0; i < obj[1].length; i++) {
                
                if(obj[mapNum][i] != null && obj[mapNum][i].temp == true) {
                    
                    obj[mapNum][i] = null;
                }
            }
        }
    }
    
}