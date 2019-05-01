package publicchat.messages;

public class EmptyMessage implements Message {
    public static String type = "kEmptyMessage";

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getMessage() {
        throw new UnsupportedOperationException("Empty Messages donot have any message");
    }

    @Override
    public String toStringForm() {
        throw new UnsupportedOperationException("Empty Messages donot have this feature");
    }

    @Override
    public void setSenderIp(String ip) {
        throw new UnsupportedOperationException("Empty Messages donot have this feature");
    }

    @Override
    public String getSenderIp() {
        throw new UnsupportedOperationException("Empty Messages donot have this feature");
    }

    @Override
    public int getSenderPort() {
        throw new UnsupportedOperationException("Empty Messages donot have this feature");
    }

    @Override
    public Message fromString(String string) {
        throw new UnsupportedOperationException("Empty Messages donot have this feature");
    }

    @Override
    public String getUserName() {
        throw new UnsupportedOperationException("Empty Messages donot have this feature");

    }

}
