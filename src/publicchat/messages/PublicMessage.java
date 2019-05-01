package publicchat.messages;

import chat.settings.PropertyKeys;
import chat.settings.SettingsManager;

import java.io.IOException;
import java.util.StringTokenizer;

public class PublicMessage implements Message {
    public static final String type = "kPublicMessage";
    protected static String SEPARATOR = "%%*@*@*$$";
    private String senderIp;

    private String userName;
    private String message;
    protected int privatePort;

    public PublicMessage(){
        try {
            privatePort = Integer.parseInt(SettingsManager.getInstance().getValue(PropertyKeys.PRIVATE_PORT));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setMessage(String msg){
        this.message = msg;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getMessage() {
        return  (this.userName + ": "+ this.message);
    }

    @Override
    public String toStringForm() {
        StringBuilder builder = new StringBuilder();
        builder.append(userName);
        builder.append(SEPARATOR);
        builder.append(privatePort);
        builder.append(SEPARATOR);
        builder.append(message);
        return builder.toString();
    }

    @Override
    public void setSenderIp(String ip) {
        this.senderIp = ip;
    }

    @Override
    public String getSenderIp() {
        return this.senderIp;
    }

    @Override
    public int getSenderPort() {
        return this.privatePort;
    }

    @Override
    public Message fromString(String string) {
        StringTokenizer tokenizer = new StringTokenizer(string, SEPARATOR);
        this.userName = tokenizer.nextToken();
        String pPort = tokenizer.nextToken();
        this.privatePort = Integer.parseInt(pPort.trim());
        this.message = string.substring((userName.length()+SEPARATOR.length()+pPort.length() + SEPARATOR.length()));
        return this;
    }

    public String getCompleteAddress(){
        return (this.senderIp + ":" + this.privatePort);
    }
}
