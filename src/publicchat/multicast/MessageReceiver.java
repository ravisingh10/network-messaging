package publicchat.multicast;

import publicchat.messages.Message;
import publicchat.messages.MessageUtil;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MessageReceiver implements Runnable {
    private static  MessageReceiver reciever;
    private static int DATA_LENGTH = 1024*2;
    MulticastSocket socket;
    private boolean keepReading;
    private Thread thread;

    public static MessageReceiver getInstance(){
        if(reciever == null)
            reciever = new MessageReceiver();
        return  reciever;
    }

    private MessageReceiver(){
        thread = new Thread(this);
    }

    void startRecieving(InetAddress ip, int port) throws IOException{
        keepReading = true;
        this.socket = new MulticastSocket(port);
        socket.joinGroup(ip);
        thread.setDaemon(true);
        thread.start();
    }

    void stopRecieving(){
        keepReading = false;
    }

    @Override
    public void run()  {
        while (keepReading){
            byte[] buffer = new byte[DATA_LENGTH];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            try {
                socket.receive(packet);
                String data = new String(packet.getData(), 0 , packet.getLength());
                Message msg = MessageUtil.getMessageFromString(data);
                msg.setSenderIp(packet.getAddress().getHostAddress().toString());
                MessageQueueHandler.getInstance().addToIncomingMessage(msg);

            } catch (IOException e) {
                e.printStackTrace();
                keepReading = false;
            }
        }
    }
}
