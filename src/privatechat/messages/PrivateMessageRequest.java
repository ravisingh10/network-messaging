package privatechat.messages;

import java.util.StringTokenizer;

public class PrivateMessageRequest extends PrivateMessage {

    public static final String TYPE = "kPrivateMessageRequest";
    private static String MESSAGE = " has requested to message you.";

    @Override
    public String toStringForm() {
        StringBuilder builder = new StringBuilder();
        builder.append(getType());
        builder.append(SEPARATOR);
        builder.append(getSender());
        builder.append(SEPARATOR);
        builder.append(getPort());
        builder.append(SEPARATOR);
        builder.append(getMessage());

        return builder.toString();
    }

    @Override
    public PrivateMessage fromString(String recMessage) {
        StringTokenizer tokenizer = new StringTokenizer(recMessage, SEPARATOR);
        tokenizer.nextToken();
        this.setSender(tokenizer.nextToken());
        String tPort = tokenizer.nextToken();
        this.setPort(tPort);
        this.setMessage((recMessage.substring((TYPE.length() + SEPARATOR.length() + getSender().length()+ SEPARATOR.length() +  tPort.length() + SEPARATOR.length()))));
        return this;
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public String getMessage(){
        return this.getSender() + MESSAGE;
    }
}
