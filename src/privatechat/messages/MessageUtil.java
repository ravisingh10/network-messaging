package privatechat.messages;

public class MessageUtil {

    public static PrivateMessage getMessageInstantFromString(String recMesg){
        PrivateMessage msg = getMessageFromType(recMesg);
        msg.fromString(recMesg);
        return msg;
    }

    private static PrivateMessage getMessageFromType(String mesgString){
        if(mesgString.startsWith(PrivateChatMessage.TYPE))
            return new PrivateChatMessage();
        if(mesgString.startsWith(PrivateMessageRequest.TYPE))
            return new PrivateMessageRequest();
        else
            throw new IllegalArgumentException("Type of message not found");

    }

    public static  String getCompleteAddressFromMessage(PrivateMessage msg){
        return (msg.getSender() + "@" + msg.getIp() + ":" + msg.getPort());
    }
}
