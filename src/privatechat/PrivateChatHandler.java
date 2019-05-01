package privatechat;

import chat.settings.PropertyKeys;
import chat.settings.SettingsManager;
import privatechat.unicast.UnicastHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

class PrivateChatHandler {
    private static PrivateChatHandler ourInstance = new PrivateChatHandler();

    HashMap<String,ArrayList<String>> chats = new HashMap<>();

    public static PrivateChatHandler getInstance() {
        return ourInstance;
    }
    private PrivateChatHandler() {
    }

    public void startServices() throws IOException {
        int port = Integer.parseInt(SettingsManager.getInstance().getValue(PropertyKeys.PRIVATE_PORT));
        UnicastHandler.getInstance().startSocket(port);
    }

    public void stopServices(){
        UnicastHandler.getInstance().stopServices();
    }

    public void addToChat(String user, String message){
        ArrayList list = chats.get(user);
        if(list == null)
            list = new ArrayList();
        list.add(message);
        chats.put(user, list);
    }

    public ArrayList<String> getCompleteChat(String username){
        ArrayList chat = chats.get(username);
        if(chat == null)
            chat = new ArrayList<String>();
        chats.put(username, chat);
        return chat;
    }
}
