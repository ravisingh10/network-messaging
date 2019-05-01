package publicchat.messages;

import java.util.StringTokenizer;

public class SelfMessage extends PublicMessage {
    public static String type = "kSelfTest";
    private static long session_number = System.nanoTime();

    private long sessionId ;
    private boolean isSelf;

    public String getType(){
        return type;
    }

    private void checkIsSelf(){
        if(sessionId == session_number)
            isSelf = true;
        else
            isSelf = false;
    }

    public boolean isSelf() {
         return isSelf;
    }

    public String toStringForm(){
        StringBuilder builder = new StringBuilder();
        builder.append(super.getUserName());
        builder.append(SEPARATOR);
        builder.append(getSenderPort());
        builder.append(SEPARATOR);
        builder.append(session_number);
        return builder.toString();
    }

    public Message fromString(String string) {
        StringTokenizer tokenizer = new StringTokenizer(string, SEPARATOR);
        this.setUserName(tokenizer.nextToken());
        String pPort = tokenizer.nextToken();
        this.privatePort = Integer.parseInt(pPort.trim());
        sessionId = Long.parseLong(tokenizer.nextToken());
        checkIsSelf();
        return this;
    }

}
