package twitch;

import entity.NPC_Twitch;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;
import main.GamePanel;
import main.KeyHandler;
import objects.OBJ_Fireball;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.NoticeEvent;


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
        
        if(event.getMessage().contains("framework")) {
            event.respondChannel("Java puro amigão! RAIZ");
        }
        if(event.getMessage().contains("linguagem")) {
            event.respondChannel("Programando em Java.. e contando os erros!");
        }
                
        if(!event.getUser().getNick().equals("nightbot")) {

            System.out.println(event.getUser().getUserLevels(event.getChannel())+" "+
                    f.format(event.getTimestamp())+"> "+
                    event.getUser().getNick()+": "+
                    event.getMessage());
        
        
            if(!twitchMessage.startsWith("!") || event.getUser().getNick().equalsIgnoreCase("nightbot")) {

                gp.ui.addMessage(event.getUser().getNick()+": " + twitchMessage);
            }

            if (twitchMessage.equals("!new") && gp.getNPCTwitch(userHashCode) == null) {

                gp.addNPCTwitch(mapNum, event);
                gp.ui.addMessage(event.getUser().getNick()+" now have an NPC!");
                System.out.println("ID: " +event.getUser().getUserId());
            }
            else if(!twitchMessage.startsWith("!")){

                NPC_Twitch npcT = gp.getNPCTwitch(userHashCode);
                if(npcT!=null){
                    npcT.npcTwitchMessage = twitchMessage;
                    npcT.messageTwitchTimeStamp = System.currentTimeMillis();
                }
            }

            if (event.getUser().getNick().equalsIgnoreCase("twitchnotify")){
    //            gp.gameState = gp.subState;
    //            System.out.println(twitchMessage);
    //            gp.gameState = gp.playState;
                gp.player.generateFirework(gp.player.projectile, gp.player);
                TimeUnit.SECONDS.sleep(1);
                gp.player.generateFirework(gp.player.projectile, gp.player);
                TimeUnit.SECONDS.sleep(1);
                gp.player.generateFirework(gp.player.projectile, gp.player);
                TimeUnit.SECONDS.sleep(2);
                gp.player.generateFirework(gp.player.projectile, gp.player);
                TimeUnit.SECONDS.sleep(1);
                gp.player.generateFirework(gp.player.projectile, gp.player);
                TimeUnit.SECONDS.sleep(1);
                gp.player.generateFirework(gp.player.projectile, gp.player);
                TimeUnit.SECONDS.sleep(1);
                gp.player.generateFirework(gp.player.projectile, gp.player);
                TimeUnit.SECONDS.sleep(3);
                gp.player.generateFirework(gp.player.projectile, gp.player);
                TimeUnit.SECONDS.sleep(3);
                gp.player.generateFirework(gp.player.projectile, gp.player);
            }

            if(twitchMessage.equals("!firework")) {
                gp.playSE(14);
                gp.player.generateFirework(gp.player.projectile, gp.player);
                TimeUnit.SECONDS.sleep(1);
                gp.player.generateFirework(gp.player.projectile, gp.player);
                TimeUnit.SECONDS.sleep(1);
                gp.player.generateFirework(gp.player.projectile, gp.player);
                TimeUnit.SECONDS.sleep(1);
                //gp.projectileList.add(new OBJ_Fireball(gp));
                //keyH.shotKeyPressed = true;
            }
        }
    }
}