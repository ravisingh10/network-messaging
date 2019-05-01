package privatechat.unicast;

import privatechat.messageQueue.MessageQueueHandler;
import privatechat.messages.MessageUtil;
import privatechat.messages.PrivateMessage;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ravi
 */
public class DatagramReciever implements Runnable {

    DatagramSocket socket ;
    Thread thread;
    DatagramReciever(int port) throws SocketException{
        this.socket = new DatagramSocket(port);
        System.out.println("Reciever started at " + port);
        thread = new Thread(this);
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void run() {
        DatagramPacket packet;
        byte[] msg = new byte[1024];
        while(true){
            try {
                packet = new DatagramPacket(msg, 0, msg.length);
                this.socket.receive(packet);
                String message = new String(packet.getData(), 0 , packet.getLength());
                System.out.println("Message Rec: "+ message );
                PrivateMessage privMesg = MessageUtil.getMessageInstantFromString(message);
                privMesg.setIp(packet.getAddress().getHostAddress());
                MessageQueueHandler.getInstance().addMessage(privMesg);
            } catch (IOException ex) {
                Logger.getLogger(DatagramReciever.class.getName()).log(Level.SEVERE, null, ex);
                break;
            }
        }
    }

    public static void main(String arg[]) throws SocketException{
        new DatagramReciever(5000);

    }
}

