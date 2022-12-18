/*
Code based in RyiSnow youtube channel:
https://www.youtube.com/c/RyiSnow
*/

package main;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import twitch.TwitchBot;

public class Main {
    
    public static final GamePanel gamePanel = new GamePanel();
    
    public static JFrame window;

    public static void main(String[] args) throws IOException {
        
        System.setProperty("sun.java2d.opengl","True");
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(true);
        window.setAlwaysOnTop(false);
        window.setTitle("Just a 2D Game");
        
        window.add(gamePanel);

        gamePanel.config.loadConfig();
        if(gamePanel.fullScreenOn == true) {
            window.setUndecorated(true);
        }

        window.pack();
//        window.setLocationRelativeTo(null);
        window.setLocation(0, 0);
        window.setVisible(true);
        
        gamePanel.setupGame();
        gamePanel.starGameThread();
        
        new Thread(new Runnable(){
            public void run(){
                try {
                    new TwitchBot().startTwitch(Main.gamePanel, null);
                } catch (Exception ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("ERROR");
                    System.exit(0);
                }
            }
        }).start();
    }
}