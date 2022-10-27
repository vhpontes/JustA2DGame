package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Config {
    
    GamePanel gp;
    
    public Config(GamePanel gp) {
        this.gp = gp;
    }

    public void saveConfig() throws IOException {
        
        BufferedWriter bw = new BufferedWriter(new FileWriter("config.txt"));
        
        // WRITE FULL SCREEN SETTING
        if(gp.fullScreenOn == true) {
            bw.write("On");
        }
        if(gp.fullScreenOn == false) {
            bw.write("Off");
        }
        bw.newLine();
        
        // WRITE MUSIC AND SOUND EFFECT SETTINGS
        bw.write(String.valueOf(gp.music.volumeScale));
        bw.newLine();
        bw.write(String.valueOf(gp.se.volumeScale));
        bw.newLine();
        
        bw.close();
    }
    
    public void loadConfig() throws FileNotFoundException, IOException {
        
        File configFile = new File("config.txt");
        if(configFile.exists()){
            BufferedReader br = new BufferedReader(new FileReader("config.txt"));

            String str = br.readLine();

            // READ FULL SCREEN SETTING
            if(str.equals("On")) {
                gp.fullScreenOn = true;
            }
            if(str.equals("Off")) {
                gp.fullScreenOn = false;
            }

            // READ MUSIC AND SOUND EFFECT SETTINGS
            str = br.readLine();
            gp.music.volumeScale = Integer.parseInt(str);
            str = br.readLine();
            gp.se.volumeScale = Integer.parseInt(str);

            br.close();            
        }
        else {
            BufferedWriter bw = new BufferedWriter(new FileWriter("config.txt"));

            bw.write("Off");
            bw.newLine();
            bw.write(String.valueOf(3));
            bw.newLine();
            bw.write(String.valueOf(3));
            bw.newLine();

            bw.close();            
        }
    }
}