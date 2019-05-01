package publicchat.messages;

public class DisconnectMessage implements Message
{

    public static final String type = "kDisconnectMessage";
    private static final String message = " has disconnected";
    private String ip ;

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toStringForm() {
        return null;
    }

    @Override
    public void setSenderIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String getSenderIp() {
        return this.ip;
    }

    @Override
    public int getSenderPort() {
        return -1;
    }

    @Override
    public Message fromString(String string) {
        return null;
    }

    @Override
    public String getUserName() {
        return null;
    }
}
