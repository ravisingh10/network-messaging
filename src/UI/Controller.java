package UI;

import contacts.ContactManager;
import publicchat.delegator.UIDelegator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import privatechat.PrivateChatDelegate;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller  implements Initializable {
    public TextField message;
    public TextArea displayPanel;
    public Button sendButton;
    public ListView contactView;
    public TextField privMessageField;
    public Button privMessageSend;
    private String currentUser;
    public TextArea privMessageArea;

    public Controller(){
        try {
            UIDelegator.getInstance().setController(this);
            PrivateChatDelegate.setController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage() throws IOException {
        String msg = message.getText().trim();
        if(msg == null || msg.length() == 0)
            return;
        UIDelegator.getInstance().sendPublicMessage(msg);
        this.message.setText(null);
    }

    public void onEnterSend(ActionEvent e) throws IOException {
        sendMessage();
    }

    public void displayMessage(String message){
        message = message + "\n";
        displayPanel.appendText(message);
    }

    public void showContacts(){
        ObservableList viewItems = this.contactView.getItems();
        ArrayList contacts = ContactManager.getInstance().viewAllUserNames();
        for(Object contact : contacts)
            viewItems.add(contact.toString());
    }

    public void sendPrivateMessage(ActionEvent event){
        System.out.println("Sending Private Message");
        String msg = this.privMessageField.getText().trim();
        if(msg == null || msg.length() == 0)
            return;
        PrivateChatDelegate.sendMessage(currentUser, msg);
    }

    public void setSelectedUser(){
        this.currentUser = contactView.getSelectionModel().getSelectedItem().toString();
        System.out.println(currentUser);
        clearAndLoadPrivateChat();
    }


    public void updatePrivateMessageWindow(String message){
        privMessageArea.appendText(message + "\n");
    }

    public void updatePrivateMessageWindowIfSameUser(String user, String message){
        if(user.equalsIgnoreCase(currentUser))
            updatePrivateMessageWindow(message);
    }

    private void clearAndLoadPrivateChat(){
        this.privMessageArea.setText("");
        ArrayList<String> chat = PrivateChatDelegate.getAllChatOfUser(currentUser);
        for(String str : chat){
            updatePrivateMessageWindow(str);
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showContacts();
        contactView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                setSelectedUser();
                System.out.println("Selected item: " + newValue);
            }
        });
    }
}
