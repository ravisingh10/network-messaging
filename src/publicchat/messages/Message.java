package publicchat.messages;

public interface Message {
    String getType();
    String getMessage();
    String toStringForm();
    void setSenderIp(String ip);
    String getSenderIp();
    int getSenderPort();
    Message fromString(String string);
    String getUserName();
}
