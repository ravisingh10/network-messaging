package publicchat.delegator;

import UI.Controller;
import chat.settings.PropertyKeys;
import chat.settings.SettingsManager;
import publicchat.multicast.PublicChatManager;

import java.io.IOException;

public class UIDelegator {

    private String userName ;
    private Controller controller;
    private static UIDelegator delegator;
    private PublicChatManager pubChatManager;

    private UIDelegator() throws IOException {
        startServices();
    }

    public static UIDelegator getInstance() throws IOException {
        if(delegator == null)
            delegator = new UIDelegator();
        return delegator;
    }

    private void startServices() throws IOException {
        userName = SettingsManager.getInstance().getValue(PropertyKeys.USER_NAME);
        pubChatManager = PublicChatManager.getInstance();
    }

    public void setUserName(String uName){
        this.userName = uName;
    }

    public void setController(Controller controller){
        this.controller = controller;
    }

    public void sendPublicMessage(String message) throws IOException {
        pubChatManager.sendMessage(message, this.userName);
    }

    public void displayMessage(String message){
        controller.displayMessage(message);
    }

    public void updateContacts(){
        this.controller.showContacts();
    }

}
