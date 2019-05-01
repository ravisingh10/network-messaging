package publicchat.multicast;

import chat.settings.PropertyKeys;
import chat.settings.SettingsManager;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastHandler {
    private static MulticastHandler hander;

    private MulticastSocket socket;
    private InetAddress publicIp;
    private int port;

    public static MulticastHandler getInstance() throws IOException{
        if(hander == null)
            hander = new MulticastHandler();
        return hander;
    }

    private MulticastHandler() throws IOException {

        String ip = SettingsManager.getInstance().getValue(PropertyKeys.PUBLIC_IP);
        publicIp = InetAddress.getByName(ip);
        port = Integer.parseInt(SettingsManager.getInstance().getValue(PropertyKeys.PUBLIC_PORT));
//        System.out.println("IP : " + ip);
//        System.out.println("PORT : " + port);
        init();
    }

    private void init() throws IOException {
        socket = new MulticastSocket(port);
        socket.joinGroup(publicIp);
        MessageReceiver.getInstance().startRecieving(publicIp, port);
        MessageQueueReader.getInstance();

    }

    public void stop(){
        MessageReceiver.getInstance().stopRecieving();
        MessageQueueReader.getInstance().stopReading();
        socket.close();
    }

    public void sendMessage(String message) throws IOException {
        byte[] msg = message.getBytes();
        DatagramPacket packet = new DatagramPacket(message.getBytes(), message.length(), publicIp ,port);
        socket.send(packet);
        System.out.println("publicchat/messages " + message + "  sent !");
    }

}
