package publicchat.multicast;

import contacts.ContactManager;
import publicchat.delegator.UIDelegator;
import publicchat.messages.Message;
import publicchat.messages.SelfMessage;

import java.io.IOException;

public class MessageQueueReader implements  Runnable {

    private boolean keepRunning;
    private Thread thread;
    private static int sleepTime = 150;
    private static MessageQueueReader reader;

    public static MessageQueueReader getInstance(){
        if(reader == null)
            reader = new MessageQueueReader();
        return reader;
    }

    private MessageQueueReader(){
        this.keepRunning = true;
        thread = new Thread(this);
        thread.setDaemon(true);
        thread.start();
    }

    public void stopReading(){
        this.keepRunning = false;
    }

    @Override
    public void run() {
        Message msg;
        while(keepRunning){
            while((msg = MessageQueueHandler.getInstance().getNextIncomingMessage()) != null){
                try {
                    if(msg.getType().equalsIgnoreCase(SelfMessage.type)){
                        SelfMessage sMsg = (SelfMessage) msg;
                        if(sMsg.isSelf())
                            ContactManager.getInstance().addContactToBlocklist(msg.getSenderIp(), msg.getSenderPort(), msg.getUserName());
                    }else{
                        ContactManager.getInstance().addContactIfNotExisting(msg.getUserName(), msg.getSenderIp(), msg.getSenderPort());
                        UIDelegator.getInstance().displayMessage(msg.getMessage());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    keepRunning = false;
                    break;
                }
            }
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }
}
