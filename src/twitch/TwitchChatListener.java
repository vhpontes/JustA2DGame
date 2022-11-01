package twitch;

import entity.Entity;
import entity.NPC_Twitch;
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
        int userHashCode = 0;
        
        DateFormat f = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        System.out.println(f.format(event.getTimestamp())+"> "+event.getUser().getNick()+": "+event.getMessage());
        gp.ui.addMessage(event.getUser().getNick()+": "+event.getMessage());
        userHashCode = event.getUser().hashCode();
        
        System.out.println("userHashCode:"+userHashCode);
        
        if (event.getMessage().equals("!new")) {
            
            gp.addNPCTwitch(mapNum, event);
            
            //gp.npcTwitchList.add(gp.npcTwitch[mapNum][i]);
            //gp.npcTwitchList.s
//            Random random = new Random();
//            int X = random.nextInt(50)+1;
//            int Y = random.nextInt(50)+1;

//            projectile.set(worldX, worldY, direction, true, this);
//            gp.npcTwitch[mapNum][userHashCode][i] = new NPC_Twitch(gp);
//            gp.npcTwitch[mapNum][userHashCode][i].worldX = gp.tileSize*30;
//            gp.npcTwitch[mapNum][userHashCode][i].worldY = gp.tileSize*17;
//            gp.npcTwitch[mapNum][userHashCode][i].npcTwitchNick = event.getUser().getNick();
//            gp.ui.addMessage(gp.npcTwitch[mapNum][userHashCode][i].npcTwitchNick+" created an NPC!");
            gp.ui.addMessage(event.getUser().getNick()+" now have an NPC!");
//            System.out.println(gp.npc[mapNum][userHashCode][i].npcTwitchNick+" created an NPC!");
//            i++;   
        }
        else {
            NPC_Twitch npcT = gp.getNPCTwitch(userHashCode);
            if(npcT!=null){
                npcT.npcTwitchMessage = event.getMessage();
            }
//            for (Entity item : gp.npcTwitch[mapNum][userHashCode]) {
//            for(int j = 0; j < gp.npcTwitch[2].length; j++) {
//                for(int k = 0; k < gp.npcTwitch[1].length; k++) {
//                //gp.npcTwitch[mapNum][userHashCode][].
//                    if (gp.npcTwitch[mapNum][userHashCode] != null) {
//                        gp.npcTwitch[mapNum][userHashCode][i].npcTwitchMessage = event.getMessage();
//                        gp.npcTwitch[mapNum][userHashCode][i].messageTwitchTimeStamp = System.currentTimeMillis();
                        //System.out.println("pegou msg:"+item.npcTwitchMessage);
//                    }
//                }
//            }
        }
    }
}
