package privatechat.messageQueue;

import privatechat.messages.MessageUtil;
import privatechat.messages.PrivateMessage;

import java.util.ArrayList;

public class MessageQueueHandler {
    private static MessageQueueHandler ourInstance = new MessageQueueHandler();
    ArrayList<PrivateMessage> inMessages;
    ArrayList<PrivateMessage> outMessages;

    public static MessageQueueHandler getInstance() {
        return ourInstance;
    }

    private MessageQueueHandler() {
        inMessages = new ArrayList<>();
        outMessages = new ArrayList<>();
        MessageQueueReader.getInstance().startReadingQueue();
    }


    public void addMessage(PrivateMessage msg){
        this.inMessages.add(msg);
    }


    public PrivateMessage getNextMessage(){
        if(inMessages.size() > 0)
            return inMessages.remove(0);
        return null;
    }

}
