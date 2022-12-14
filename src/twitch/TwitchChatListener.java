/*
Class code: Victor Hugo Manata Pontes 
https://www.twitch.tv/lechuck311
https://www.youtube.com/@victorhugomanatapontes
https://www.youtube.com/@dtudoumporco
*/

package twitch;

import entity.NPC_Twitch;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import main.GamePanel;
import main.KeyHandler;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.JoinEvent;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.NoticeEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;


public class TwitchChatListener extends ListenerAdapter {
    
    GamePanel gp;
    int i = 0;
    
    public TwitchChatListener() {
    }
    
    public TwitchChatListener(GamePanel gp, KeyHandler keyH) {

        this.gp = gp;
    }
    
    @Override
    public void onNotice(NoticeEvent event)throws InterruptedException {
        int mapNum = 0;
        int userHashCode = 0;
        
        String twitchNotice = event.getNotice();
        userHashCode = event.getUser().hashCode();
        
        DateFormat f = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        System.out.println("NOTICE: "+ twitchNotice);
    }
    
    @Override
    public void onMessage(MessageEvent event) throws InterruptedException {
        // TO-DO:
        //   OK - Limit spawn NPC 1 per chat user 
        //   OK - Exclusive dialogue window with chat user
        //      - Fireworks and Spaw a New MOB on Subscriptions
        //      - Não pegar certos chats de usuarios como o nightbot e etc
        
        int mapNum = 0;
        int userHashCode = 0;
        String twitchMessage = event.getMessage();
        userHashCode = event.getUser().hashCode();
        
        DateFormat f = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        
//        if(event.getMessage().contains("framework")) {
//            event.respondChannel("Java puro amigão! RAIZ");
//        }
//        if(event.getMessage().contains("linguagem")) {
//            event.respondChannel("Programando em Java.. e contando os erros!");
//        }
                
        if(!event.getUser().getNick().equals("nightbot")) {

            NPC_Twitch npcT = gp.getNPCTwitch(userHashCode);

            System.out.println(event.getUser().getUserLevels(event.getChannel())+" "+
                    f.format(event.getTimestamp())+"> "+
                    event.getUser().getNick()+": "+
                    event.getMessage());
        
        
            if(!twitchMessage.startsWith("!") || event.getUser().getNick().equalsIgnoreCase("nightbot")) {

                //gp.ui.addMessage(event.getUser().getNick()+": " + twitchMessage);
            }
            
            // ADD Twitch User NPC in GAME
            if (twitchMessage.equals("!npc") && gp.getNPCTwitch(userHashCode) == null) {

                gp.addNPCTwitch(mapNum, event, gp.player.worldX, gp.player.worldY, event.getUser().getNick());
                //gp.ui.addMessage(event.getUser().getNick()+" now have an NPC in game!");
                npcT = gp.getNPCTwitch(userHashCode);
                npcT.name = event.getUser().getNick();
                gp.ui.addMessage(npcT.name+" now have an NPC in game!");
                //System.out.println("ID: " +event.getUser().getUserId());
            }
            // LAUNCH A FIREBALL
            else if(twitchMessage.equals("!fireball")) {

                npcT = gp.getNPCTwitch(userHashCode);
                if(npcT != null){
                    
                    npcT.npcFireballLaunched = true;
                    npcT.attacking = true;
                }
            }
            // TWITCH NPC MESSAGE ON SCREEN
            else if(!twitchMessage.startsWith("!")) {

                npcT = gp.getNPCTwitch(userHashCode);
                if(npcT!=null){
                    npcT.npcTwitchMessage = twitchMessage;
                    npcT.messageTwitchTimeStamp = System.currentTimeMillis();
                }
            }
            // FIREWORKs WHEN A EVENT (follow, sub, etc..)
            if (event.getUser().getNick().equalsIgnoreCase("own3d")){
                if (twitchMessage.contains(" is now following!") 
                        || twitchMessage.contains(" has gifted ") 
                        || twitchMessage.contains(" donated ")
                        || twitchMessage.contains(" just subscribed!")
                        || twitchMessage.contains(" raiding with ")
                        || twitchMessage.contains(" hosting with ")
                        || twitchMessage.contains(" just resubbed ")
                        || twitchMessage.contains(" cheered!")
                        ) {
                    
                    gp.playSE(14);
                    gp.player.makeFireworkShow(60);
                }
    //            gp.gameState = gp.subState;
    //            System.out.println(twitchMessage);
    //            gp.gameState = gp.playState;
            }

            if(twitchMessage.equals("!firework")) {
                gp.playSE(14);
                gp.player.makeFireworkShow(30);
            }
        }
    }

    @Override
    public void onJoin(JoinEvent eventJ) throws Exception
    {
        int mapNum = 0;
        int userHashCode = 0;
        userHashCode = eventJ.getUser().hashCode();
        
        NPC_Twitch npcT = gp.getNPCTwitch(userHashCode);
        
        gp.addNPCTwitch(mapNum, null, gp.player.worldX, gp.player.worldY, eventJ.getUser().getNick());
        npcT = gp.getNPCTwitch(userHashCode);
        npcT.name = eventJ.getUser().getNick();
        System.out.println(npcT.name + ", JOIN in Twitch and GAME!");
                
    }

    
    @Override
    public void onGenericMessage(GenericMessageEvent event) throws Exception
    {
        System.out.println("Message received from onGenericMessage: " + event.getMessage());
    }    
}