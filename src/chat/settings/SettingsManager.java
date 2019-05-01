package chat.settings;

import java.io.*;
import java.util.*;

public class SettingsManager {
    private static SettingsManager manager;
    private String fileLocation = "/settings.properties";
    private Properties properties;

    public static SettingsManager getInstance() throws IOException{
        if(manager == null)
            manager = new SettingsManager();
        return manager;
    }

    private SettingsManager() throws IOException{
        initSettings();
    }

    private void initSettings() throws IOException {
        String fName = new File(getClass().getResource(fileLocation).getPath()).getPath();
        File f = new File(fName);
        if(f.exists() == false)
            throw new FileNotFoundException("File Not Found : " + f.getPath());
        FileReader reader = new FileReader(f);
        properties = new Properties();
        properties.load(getClass().getResourceAsStream(fileLocation));
    }

    public Map getSettings(){
        Enumeration<Object> keys = properties.keys();
        Iterator<Object> iterator = keys.asIterator();
        HashMap<String,String> map = new HashMap<>();
        while(iterator.hasNext())
        {
            String key = iterator.next().toString();
            map.put(key, properties.getProperty(key));
        }
        return map;
    }
//
//    public  static void main(String args[]) throws Exception {
//        getInstance();
//        System.out.println(manager.getSettings());
//        System.out.println(manager.getValue(PropertyKeys.PUBLIC_IP));
//        manager.updateOrCreateKeys(PropertyKeys.PUBLIC_PORT, "5511");
//        System.out.println(manager.getSettings());
//    }

    public String getValue(String key) throws IOException {
        if(properties.get(key) != null)
            return properties.get(key).toString();
        else
            throw new IllegalArgumentException("Settings Not Found for key " + key);
    }

    public void updateOrCreateKeys(String key, String value) throws  IOException{
        if(key == null || value == null)
            throw new IllegalArgumentException("Key or Value cannot be null");
        properties.put(key,value);
        FileWriter writer = new FileWriter(getClass().getResource(fileLocation).getFile());
        properties.store(writer, null);
        initSettings();
    }

}
