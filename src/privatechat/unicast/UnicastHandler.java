package privatechat.unicast;

import java.io.IOException;
import java.net.*;

public class UnicastHandler {
    private static UnicastHandler ourInstance = new UnicastHandler();

    public static UnicastHandler getInstance() {
        return ourInstance;
    }

    private DatagramSocket socket;
    private  DatagramReciever reciever;

    private UnicastHandler() {
    }

    public void stopServices(){
//        reciever.stopRecieving();
        socket.disconnect();
    }

    public void startSocket(int port) throws SocketException {
        this.socket = new DatagramSocket();
        System.out.println("Starting receiver at " + port);
        reciever = new DatagramReciever(port);
    }

    public void sendMessage(String message, InetAddress ip, int port) throws IOException {
        System.out.println("Sending: " + message );
        DatagramPacket packet = new DatagramPacket(message.getBytes(), message.getBytes().length, ip, port);
        System.out.println("Sent to" + packet.getAddress().getHostAddress() + packet.getPort());
        socket.send(packet);
    }
}
