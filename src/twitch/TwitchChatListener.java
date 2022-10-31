package twitch;

import entity.Entity;
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
        
        int mapNum = 2;
        
        DateFormat f = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        System.out.println(f.format(event.getTimestamp())+"> "+event.getUser().getNick()+": "+event.getMessage());
        
        if (event.getMessage().equals("!new")) {
            
            Random random = new Random();
            int X = random.nextInt(50)+1;
            int Y = random.nextInt(50)+1;

            gp.npc[mapNum][i] = new NPC_OldMan(gp);
            gp.npc[mapNum][i].worldX = gp.tileSize*30;
            gp.npc[mapNum][i].worldY = gp.tileSize*17;
            gp.npc[mapNum][i].npcTwitchNick = event.getUser().getNick();
            gp.ui.addMessage(gp.npc[mapNum][i].npcTwitchNick+" created an NPC!");
//            System.out.println(gp.npc[mapNum][i].npcTwitchNick+" created an NPC!");
            i++;   
        }
        else {
            for (Entity item : gp.npc[mapNum]) {
                if (item != null) {
                    item.npcTwitchMessage = event.getMessage();
                    item.messageTwitchTimeStamp = System.currentTimeMillis();
                    //System.out.println("pegou msg:"+item.npcTwitchMessage);
                }
            }
        }
    }
}
