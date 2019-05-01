package publicchat.multicast;

import publicchat.messages.Message;

import java.io.IOException;
import java.util.ArrayList;

class MessageQueueHandler  {
    private  static MessageQueueHandler handler;
    ArrayList<Message> inMessages;

    public static MessageQueueHandler getInstance(){
        if(handler == null)
            handler = new MessageQueueHandler();
        return handler;
    }

    private MessageQueueHandler(){
        inMessages = new ArrayList<>();
    }

    public Message getNextIncomingMessage(){
        if(inMessages.size() > 0)
            return inMessages.remove(0);
        else
            return null;
    }

    public void addToIncomingMessage(Message msg) throws IOException {
        this.inMessages.add(msg);
    }

}
