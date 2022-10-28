
package twitch;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import main.GamePanel;
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.cap.EnableCapHandler;
import org.pircbotx.exception.IrcException;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

public class TwitchBot {
//dasdsadsa
    private String caphandler;
    private String url;
    private int port;
    private String oauth;
    private String name;
    private String channel;
    
    public TwitchBot() throws IOException {
        this.loadProperties();
    }
    
//    private static Logger LOGGER = LoggerFactory.getLogger(TWChat.class);
    private void loadProperties () throws FileNotFoundException, IOException {
        Properties props = new Properties();
        InputStream instream = new FileInputStream("twitch.properties");
        props.load(instream);
        this.caphandler = props.getProperty("twitch.caphandler");
        this.url = props.getProperty("twitch.server.url");
        this.port = Integer.parseInt(props.getProperty("twitch.server.port"));
        this.oauth = props.getProperty("twitch.oauth");
        this.name = props.getProperty("twitch.name");
        this.channel = props.getProperty("twitch.channel");
    }
    
    public void startTwitch(GamePanel gp) throws IOException, IrcException {
//        LOGGER.info("test");
        Configuration configuration = new Configuration.Builder()
                .setAutoNickChange(false)
                .setOnJoinWhoEnabled(false)
                .setCapEnabled(true)
                .addCapHandler(new EnableCapHandler("twitch.tv/"+this.caphandler))
                .addServer(this.url, this.port)
                .setServerPassword("oauth:"+this.oauth)
                .setName(this.name)
                .addAutoJoinChannel("#"+this.channel)
                .addListener(new TwitchChatListener(gp))
                .buildConfiguration();

        PircBotX bot = new PircBotX(configuration);
        bot.startBot();
    }
}
