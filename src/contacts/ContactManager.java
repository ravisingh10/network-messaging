package contacts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class ContactManager {

    private static ContactManager manager;

    private HashMap<String,Contact> contactmap;
    private HashMap<String,Contact> blockMap;

    public static ContactManager getInstance(){
        if(manager == null)
            manager = new ContactManager();
        return manager;
    }

    private ContactManager(){
        contactmap = new HashMap<>();
        blockMap = new HashMap<>();
    }

    public void addContact(String completeAddress, String userName){
        if( (contactmap.get(completeAddress)== null || !contactmap.get(completeAddress).getUsername().equalsIgnoreCase(userName)) && !isBlocked(completeAddress))
        {
            contactmap.put(completeAddress, new Contact(userName, completeAddress));
            System.out.println("Added " + userName);
        }

        ContactManagerUtil.updateListInUI();
    }

    private boolean isBlocked(String completeAddrs){
        if(blockMap.get(completeAddrs) == null)
            return false;
        return true;
    }

    public void addContact(String ip, int port, String userName){
        String completeAddress = ContactManagerUtil.getCompleteAddress(userName, ip, port);
        addContact(completeAddress ,userName);
    }

    public ArrayList viewAllUserNames(){
        ArrayList<String> userNames = new ArrayList<>();
        Set<String> keySet = contactmap.keySet();
        for(String str: keySet){
            userNames.add(contactmap.get(str).getCompleteAddress());
        }
        return userNames;
    }

    public void removeContactWithFullAddress(String completeAddress){
        contactmap.remove(completeAddress);
    }

    public void  addContactToBlocklist(String ip, int port, String userName){
        String completeAddress = ContactManagerUtil.getCompleteAddress(userName, ip, port);
        if( blockMap.get(completeAddress)== null || !blockMap.get(completeAddress).getUsername().equalsIgnoreCase(userName))
        {
            blockMap.put(completeAddress, new Contact(userName, completeAddress));
            System.out.println("Blocked :" + userName);
        }
        removeContactWithFullAddress(completeAddress);
        ContactManagerUtil.updateListInUI();;
    }


    public Contact getFullContactOfCompleteAddress(String uName){
        Set<String> keySet = contactmap.keySet();
        for(String str: keySet){
            if(contactmap.get(str).getCompleteAddress().equalsIgnoreCase(uName)){
                return contactmap.get(str);
            }

        }
        return null;
    }

    public void addContactIfNotExisting(String uName, String ip, int port){
        if(getFullContactOfCompleteAddress(ContactManagerUtil.getCompleteAddress(uName,ip,port)) == null)
            addContact(ip,port,uName);
    }


}
