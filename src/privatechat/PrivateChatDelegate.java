package privatechat;

import UI.Controller;
import privatechat.messageQueue.MessageQueueHandler;

import java.io.IOException;
import java.util.ArrayList;

public class PrivateChatDelegate {
    private static Controller controller;

    public static void setController(Controller cntrl){
        controller = cntrl;
    }

    public static void updateMessageOnScreen(String completeAddress, String message){
        controller.updatePrivateMessageWindowIfSameUser(completeAddress, message);
    }

    public static void sendMessage(String uName, String message){
        try {
            PrivateChatManager.getInstance().sendMessage(message, uName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> getAllChatOfUser(String completeAddress){
        return PrivateChatHandler.getInstance().getCompleteChat(completeAddress);
    }
}
