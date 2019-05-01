package privatechat.messages;

import chat.settings.PropertyKeys;
import chat.settings.SettingsManager;

import java.io.IOException;

public abstract class PrivateMessage {
    private String sender;
    private String reciever;
    private String message;
    private String ip;
    private String port;

    public static String SEPARATOR = "#:#@#:#";

    protected PrivateMessage() {
        try {
            port = SettingsManager.getInstance().getValue(PropertyKeys.PRIVATE_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReciever() {
        return reciever;
    }

    public void setReciever(String reciever) {
        this.reciever = reciever;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public abstract String toStringForm();

    public abstract PrivateMessage fromString(String recMessage);

    public abstract String getType();

    public String getDisplayMessage(){
        return getSender() + " : " + getMessage();
    }


}
