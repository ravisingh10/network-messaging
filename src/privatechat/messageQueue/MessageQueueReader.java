package privatechat.messageQueue;

import contacts.ContactManager;
import privatechat.PrivateChatManager;
import privatechat.messages.MessageUtil;
import privatechat.messages.PrivateMessage;

public class MessageQueueReader implements Runnable{
    private static MessageQueueReader ourInstance = new MessageQueueReader();
    private Thread thread;
    private boolean keepReading;

    public static MessageQueueReader getInstance() {
        return ourInstance;
    }

    private MessageQueueReader() {
        thread = new Thread(this);
        thread.setDaemon(true);
    }

    public void startReadingQueue(){
        this.keepReading = true;
        thread.start();
    }

    @Override
    public void run() {
        PrivateMessage msg;
        while (keepReading){
            while ((msg = MessageQueueHandler.getInstance().getNextMessage()) != null)
            {
                System.out.println("Read Message " + msg.getDisplayMessage());
                String completeAddress = MessageUtil.getCompleteAddressFromMessage(msg);
                ContactManager.getInstance().addContactIfNotExisting(msg.getSender(), msg.getIp(), Integer.parseInt(msg.getPort()));
                PrivateChatManager.getInstance().addMessageToChat(completeAddress, msg.getDisplayMessage());
            }
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
