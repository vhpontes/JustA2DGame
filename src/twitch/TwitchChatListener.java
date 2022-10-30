package twitch;

import entity.NPC_OldMan;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Random;
import main.GamePanel;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

public class TwitchChatListener extends ListenerAdapter {
    
    GamePanel gp;
    int i = 0;
    //private static Logger LOGGER = LoggerFactory.getLogger(TwitchChatListener.class);
    
    public TwitchChatListener() {
    }
    
    public TwitchChatListener(GamePanel gp) {
        this.gp = gp;
    }
    
    @Override
    public void onMessage(MessageEvent event) {
        // TO-DO:
        //      - Limit spawn NPC 1 per chat user
        //      - Exclusive dialogue window with chat user
        
        DateFormat f = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        System.out.println(f.format(event.getTimestamp())+"> "+event.getUser().getNick()+": "+event.getMessage());
//        LOGGER.info(event.getMessage());

        Random random = new Random();
        int X = random.nextInt(50)+1;
        int Y = random.nextInt(50)+1;
        if (event.getMessage().equals("!new")) {
            int mapNum = 2;
            gp.npc[mapNum][i] = new NPC_OldMan(gp);
            gp.npc[mapNum][i].worldX = gp.tileSize*X;
            gp.npc[mapNum][i].worldY = gp.tileSize*Y ;
            gp.npc[mapNum][i].npcTwitchNick = event.getUser().getNick();
            i++;            
            //System.out.println("New NPC created!");
        }
    }
}