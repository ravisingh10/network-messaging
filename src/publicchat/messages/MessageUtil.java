package publicchat.messages;

import java.util.StringTokenizer;

public class MessageUtil {
    private static String SEPARATOR = "@#@#$$#%^#^^#&*";

    public static Message getMessageFromString(String msg) {
        StringTokenizer tokenizer = new StringTokenizer(msg, SEPARATOR);
        String type = tokenizer.nextToken();
        String message = msg.substring((type.length() + SEPARATOR.length()));
//                tokenizer.nextToken();

        Message msgObject = getMessageFromType(type);
        msgObject.fromString(message);
        return msgObject;
    }

    public static String messageToString(Message msg) {
        StringBuffer buff = new StringBuffer();
        buff.append(msg.getType());
        buff.append(SEPARATOR);
        buff.append(msg.toStringForm());
        return buff.toString();
    }

    private static Message getMessageFromType(String type) {
        Message msg = null;
        if (type.equalsIgnoreCase(EmptyMessage.type)) {
            msg = new EmptyMessage();
        } else if (type.equalsIgnoreCase(PublicMessage.type)) {
            msg = new PublicMessage();
        }else if(type.equalsIgnoreCase(DisconnectMessage.type)){
            msg = new DisconnectMessage();
        }else if(type.equalsIgnoreCase(SelfMessage.type)){
            msg = new SelfMessage();
        }
        return msg;
    }
}
