package contacts;

import publicchat.delegator.UIDelegator;

import java.io.IOException;
import java.util.StringTokenizer;

public class ContactManagerUtil {

    public static void updateListInUI() {
//        UniUIDelegator.getInstance().updateContactsInUI();
        try {
            UIDelegator.getInstance().updateContacts();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getCompleteAddress(String uName, String ip, int port){
        return (uName + "@" + ip + ":" + port);
    }

//    public static String getIpFromCompleteAddress(String completeAddress){
//        StringTokenizer tokenizer = new StringTokenizer(completeAddress , "@");
//        tokenizer.nextToken();
//        String address = tokenizer.nextToken();
//        StringTokenizer tokenizer1 = new StringTokenizer(address, ":");
//        String ip = tokenizer1.nextToken();
//        return ip;
//    }

    public static int getPortFromCompleteAddress(String completeAddress){
        StringTokenizer tokenizer = new StringTokenizer(completeAddress , "@");
        tokenizer.nextToken();
        String address = tokenizer.nextToken();
        StringTokenizer tokenizer1 = new StringTokenizer(address, ":");
        String ip = tokenizer1.nextToken();
        int port = Integer.parseInt(tokenizer1.nextToken());
        return port;
    }

    public static String getIPFromCompleteAddress(String completeAddress){
        StringTokenizer tokenizer = new StringTokenizer(completeAddress , "@");
        tokenizer.nextToken();
        String address = tokenizer.nextToken();
        StringTokenizer tokenizer1 = new StringTokenizer(address, ":");
        String ip = tokenizer1.nextToken();
        return ip;
    }
}
