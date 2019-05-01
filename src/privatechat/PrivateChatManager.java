package privatechat;

import chat.settings.PropertyKeys;
import chat.settings.SettingsManager;
import contacts.Contact;
import contacts.ContactManager;
import contacts.ContactManagerUtil;
import privatechat.messageQueue.MessageQueueHandler;
import privatechat.messages.PrivateChatMessage;
import privatechat.unicast.UnicastHandler;

import java.io.IOException;
import java.net.InetAddress;

public class PrivateChatManager {
    private static PrivateChatManager ourInstance = new PrivateChatManager();
    private PrivateChatHandler handler;

    public static PrivateChatManager getInstance() {
        return ourInstance;
    }

    private PrivateChatManager() {
        try {
            handler = PrivateChatHandler.getInstance();
            handler.startServices();
            MessageQueueHandler.getInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopReading(){
        handler.stopServices();
    }

    public void sendMessage(String message, String recName) throws IOException {
        String senderName = SettingsManager.getInstance().getValue(PropertyKeys.USER_NAME);
        PrivateChatMessage msg = new PrivateChatMessage();
        msg.setSender(senderName);
        msg.setMessage(message);
        Contact recContact = ContactManager.getInstance().getFullContactOfCompleteAddress(recName);
        String fullAddress = recContact.getCompleteAddress();
        InetAddress ip = InetAddress.getByName(ContactManagerUtil.getIPFromCompleteAddress(fullAddress));
        int port = ContactManagerUtil.getPortFromCompleteAddress(fullAddress);
        UnicastHandler.getInstance().sendMessage(msg.toStringForm(), ip, port);
        addMessageToChat(fullAddress, msg.getDisplayMessage());
    }

    public void addMessageToChat(String completeAddress, String message){
        handler.addToChat(completeAddress, message);
        PrivateChatDelegate.updateMessageOnScreen(completeAddress, message);
    }


}
