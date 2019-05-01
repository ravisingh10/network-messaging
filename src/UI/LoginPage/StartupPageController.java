package UI.LoginPage;

import chat.settings.PropertyKeys;
import chat.settings.SettingsManager;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

public class StartupPageController implements Initializable {
    public TextField userNameField;
    public TextField publicIpField;
    public TextField publicPortField;
    public TextField privatePortField;
    public TextArea errorLabel;
    public Button startButton;
    private String IP_Tokenizer = ".";

    public  StartupPageController(){
        System.out.println("In COnstructor");

    }

    public void checkAndConnect() {

        this.errorLabel.setText(null);
        try{
            String userName = userNameField.getText().trim();
            InetAddress publicIp = InetAddress.getByName(publicIpField.getText().trim());
            int publicPort = Integer.parseInt(publicPortField.getText().trim());
            int privatePort = Integer.parseInt(privatePortField.getText().trim());
            checkClassCIp(publicIp.getHostAddress());
            updateSettingsInProperties(PropertyKeys.USER_NAME, userName);
            updateSettingsInProperties(PropertyKeys.PUBLIC_IP, publicIp.getHostAddress());
            updateSettingsInProperties(PropertyKeys.PUBLIC_PORT, String.valueOf(publicPort));
            updateSettingsInProperties(PropertyKeys.PRIVATE_PORT, String.valueOf(privatePort));
            publicchat.delegator.UIDelegator.getInstance().setUserName(userName);
        }
        catch (Exception ex)
        {
            errorLabel.setText(ex.getStackTrace().toString());
            ex.printStackTrace();
        }
    }

    private void updateSettingsInProperties(String key, String value) throws IOException {
        SettingsManager.getInstance().updateOrCreateKeys(key,value);
    }

    private void checkClassCIp(String Ip) throws UnknownHostException {
        StringTokenizer tokenizer = new StringTokenizer(Ip, IP_Tokenizer);
        int ipClass = Integer.parseInt(tokenizer.nextToken());
        if(ipClass >= 224 && ipClass < 239){
            if(ipClass == 224 && Ip.endsWith(".0.0.0"))
                throw new UnknownHostException("Invalid IP, Enter a multicast IP eg. 224.0.0.1");
            while (tokenizer.hasMoreElements()){
                int nextElement = Integer.parseInt(tokenizer.nextToken());
                if(nextElement > 255 || nextElement < 0)
                    throw new UnknownHostException("Invalid IP, Enter a multicast IP eg. 224.0.0.1");
            }
        }
        else
            throw new UnknownHostException("Invalid IP, Enter a multicast IP eg. 224.0.0.1");
        startChatUI();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Initializing");
        try{
            this.userNameField.setText(SettingsManager.getInstance().getValue(PropertyKeys.USER_NAME));
            this.publicIpField.setText(SettingsManager.getInstance().getValue(PropertyKeys.PUBLIC_IP));
            this.privatePortField.setText(SettingsManager.getInstance().getValue(PropertyKeys.PRIVATE_PORT));
            this.publicPortField.setText(SettingsManager.getInstance().getValue(PropertyKeys.PUBLIC_PORT));
        } catch (IOException e) {
            errorLabel.setText(e.getMessage());
        }

    }

    private void startChatUI(){

        try {
            System.out.println("Starting to update");
            Parent root = FXMLLoader.load(getClass().getResource("/UI/sample.fxml"));
            Stage stage = (Stage) this.startButton.getScene().getWindow();
            stage.setTitle("Start...");
            stage.setScene(new Scene(root, 600, 500));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
