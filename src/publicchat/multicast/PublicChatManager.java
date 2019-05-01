package publicchat.multicast;

import java.io.IOException;

import chat.settings.PropertyKeys;
import chat.settings.SettingsManager;
import publicchat.messages.*;

public class PublicChatManager {
    private static PublicChatManager manager;
    private MulticastHandler handler;
    private String userName;

    public static PublicChatManager getInstance() throws  IOException{
        if(manager == null)
            manager = new PublicChatManager();
        return manager;
    }

    private PublicChatManager() throws IOException {
        this.handler = MulticastHandler.getInstance();
        this.userName = SettingsManager.getInstance().getValue(PropertyKeys.USER_NAME);
        sendSelfTestMessage();
    }

    private void sendSelfTestMessage() throws IOException {
        SelfMessage msg = new SelfMessage();
        msg.setUserName(userName);
        handler.sendMessage(MessageUtil.messageToString(msg));

    }

    public void sendMessage(String message, String userName) throws IOException {
        PublicMessage msg = new PublicMessage();
        msg.setMessage(message);
        msg.setUserName(userName);
       handler.sendMessage(MessageUtil.messageToString(msg));
    }

    public void stopServices() throws IOException {
        handler.sendMessage(new DisconnectMessage().toStringForm());
        handler.stop();
    }

}
