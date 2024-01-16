package com.example.fxapp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class meeting {

    @FXML
    private Button SendInfoMeeting;

    @FXML
    private Button closeButtonMeeting;

    @FXML
    private ImageView closeMeeting;

    @FXML
    private Button homeMeeting;

    @FXML
    private TextField meetingtextfield;

public void SendInfoMeeting(ActionEvent e)
{

    String meeting_details=meetingtextfield.getText();

}


}
