package contacts;

public class Contact {
    private String username;
    private String completeAddress;

    public Contact(String username, String completeAddress) {
        this.username = username;
        this.completeAddress = completeAddress;
    }

    public Contact(String username, String ip, int port){
        this.username = username;
        this.completeAddress = getCompleteAddress(ip, port);
    }

    private String getCompleteAddress(String ip , int port){
        return (username + "@" + ip + ":"+ port);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCompleteAddress() {
        return completeAddress;
    }

    public void setCompleteAddress(String completeAddress) {
        this.completeAddress = completeAddress;
    }
}
