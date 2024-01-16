package com.example.fxapp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class MeetingController implements Initializable {

    @FXML
    private Button CLOSEMEETing;
    @FXML
    private Button HOMEMEETING;
    @FXML
    private Button LOGOUTMEETING;
    @FXML
    private TextField MeetingText;
    @FXML
    private Button NOTICEBOARDTEXT;
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    Statement statement = null;

    // Replace this with the actual manager ID obtained from your authentication system
     int managerId = 1; // Replace '1' with the actual manager ID

    @FXML
    void NOTICEBOARDBUTTON(ActionEvent event) {
        String meetingText = MeetingText.getText();
        System.out.println("Hello");

        if (meetingText.isEmpty()) {
            // Show an alert if MeetingText is empty
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Please enter text in the Meeting Text field.");
            alert.showAndWait();
        } else {
            conn = DatabaseConnection.getConnection();

            String sql = "INSERT INTO meeting (MANAGER_ID, MEETING_TEXT) VALUES (?, ?)";

            try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                preparedStatement.setInt(1, managerId);
                preparedStatement.setString(2, meetingText);

                // Execute the SQL statement
                preparedStatement.executeUpdate();

                // Optional: Show a confirmation message
                Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
                confirmation.setTitle("Meeting Saved");
                confirmation.setHeaderText(null);
                confirmation.setContentText("Meeting saved successfully.");
                confirmation.showAndWait();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                // Close the connection
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Any initialization code can go here
    }

    @FXML
    void CLOSE_SYSTEM(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void HOMEBUTTONMEETING(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("HOME.fxml"));
            Parent root = loader.load();

            Stage loginStage = new Stage();
            loginStage.setTitle("HOME Screen");
            loginStage.setScene(new Scene(root));
            Stage currentStage = (Stage) LOGOUTMEETING.getScene().getWindow();
            currentStage.close();
            loginStage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }

    @FXML
    void LOGOUTBUTTOBMEETING(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = loader.load();

            Stage loginStage = new Stage();
            loginStage.setTitle("Login Screen");
            loginStage.setScene(new Scene(root));

            Stage currentStage = (Stage) LOGOUTMEETING.getScene().getWindow();
            currentStage.close();

            loginStage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }



}
