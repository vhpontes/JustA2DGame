package main;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import twitch.TwitchBot;

public class Main {
    
    public static final GamePanel gamePanel = new GamePanel();
    
    public static JFrame window;

    public static void main(String[] args) throws IOException {
        
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setAlwaysOnTop(true);
        window.setTitle("Just a 2D Game");
        
        //GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        gamePanel.config.loadConfig();
        if(gamePanel.fullScreenOn == true) {
            window.setUndecorated(true);
        }

        window.pack();
        window.setLocationRelativeTo(null);
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