package main;

import javax.swing.JFrame;

public class Main {
    
    public static JFrame window;

    public static void main(String[] args) {

        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Just a 2D Game");
//        window.setUndecorated(false);
//        window.setAlwaysOnTop(true);
        
        GamePanel gamePanel = new GamePanel();

        window.add(gamePanel);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        
        gamePanel.setupGame();
        gamePanel.starGameThread();
    }
    
}
