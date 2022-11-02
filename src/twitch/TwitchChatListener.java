package twitch;

import entity.Entity;
import entity.NPC_Twitch;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Random;
import main.GamePanel;
import main.KeyHandler;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

public class TwitchChatListener extends ListenerAdapter {
    
    GamePanel gp;
    KeyHandler keyH;
    int i = 0;
    
    public TwitchChatListener() {
    }
    
    public TwitchChatListener(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
    }
    
    @Override
    public void onMessage(MessageEvent event) {
        // TO-DO:
        //   OK - Limit spawn NPC 1 per chat user 
        //   OK - Exclusive dialogue window with chat user
        //      - Fireworks and Spaw a New MOB on Subscriptions
        
        int mapNum = 2;
        int userHashCode = 0;
        
        DateFormat f = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        System.out.println(f.format(event.getTimestamp())+"> "+event.getUser().getNick()+": "+event.getMessage());
        gp.ui.addMessage(event.getUser().getNick()+": "+event.getMessage());
        userHashCode = event.getUser().hashCode();
        
        //System.out.println("userHashCode:"+userHashCode);
        
        if (event.getMessage().equals("!new") && gp.getNPCTwitch(userHashCode) == null) {
            
            gp.addNPCTwitch(mapNum, event);

            gp.ui.addMessage(event.getUser().getNick()+" now have an NPC!");
        }
        else {
            NPC_Twitch npcT = gp.getNPCTwitch(userHashCode);
            if(npcT!=null){
                npcT.npcTwitchMessage = event.getMessage();
                npcT.messageTwitchTimeStamp = System.currentTimeMillis();
            }
        }
        
        if (event.getUser().getNick().equalsIgnoreCase("twitchnotify")){
            gp.gameState = gp.subState;
            System.out.println(event.getMessage());
            gp.gameState = gp.playState;
        }
        
        if(event.getMessage().equals("!fireball")) {
            keyH.shotKeyPressed = true;
            
        }
    }
}