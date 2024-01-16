package com.example.fxapp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class HOMECONTROLLER {

    @FXML
    private Button EMPLOYYEENTERPAGE;

    @FXML
    private Button LOGOUTENTERPAGE;

    @FXML
    private Button MEETINGSENTERPAGE;

    @FXML
    private Button closeENterPAGE;
    @FXML
    private Button applications;
    @FXML
    void CLOSEPAGE(ActionEvent event) {
    System.exit(0);
    }

    private String managerID;
    public void OpenEmployeePage(ActionEvent event) {
        try {
            // Load the FXML file for the Employee screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("manager_.fxml"));
            Parent root = loader.load();

            // Create a new stage for the Meeting screen
            Stage meetingStage = new Stage();
            meetingStage.setTitle("EmployeeInformation Screen");
            meetingStage.setScene(new Scene(root));

            // Show the Meeting screen
            meetingStage.show();

            // Close the current stage (assuming the button is in the login screen)
            Stage currentStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            currentStage.close();



        }   catch (IOException e) {
            e.printStackTrace();
        }



    }

    @FXML
    void OpenLOGINPAGE(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = loader.load();

            Stage loginStage = new Stage();
            loginStage.setTitle("Login Screen");
            loginStage.setScene(new Scene(root));

            Stage currentStage = (Stage) LOGOUTENTERPAGE.getScene().getWindow();
            currentStage.close();

            loginStage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void OpenMeetingsPage(ActionEvent event) {
        try {
            // Load the FXML file for the Employee screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Meeting.fxml"));
            Parent root = loader.load();

            // Create a new stage for the Meeting screen
            Stage meetingStage = new Stage();
            meetingStage.setTitle("Meeting Screen");
            meetingStage.setScene(new Scene(root));

            // Show the Meeting screen
            meetingStage.show();

            // Close the current stage (assuming the button is in the login screen)
            Stage currentStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            currentStage.close();

        }   catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void taskAction(ActionEvent event) throws SQLException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("tasks.fxml"));
            Parent root = loader.load();

            Tasks tasksController = loader.getController();
            tasksController.setManagerId(managerID);

            Stage tasksStage = new Stage();
            tasksStage.setTitle("Task Assignment");
            tasksStage.setScene(new Scene(root));

            // Optional: Close the current stage if needed
            // Stage currentStage = (Stage) assign.getScene().getWindow();
            // currentStage.close();

            tasksStage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void applicationAction(ActionEvent event) throws SQLException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("application.fxml"));
            Parent root = loader.load();

            application application = loader.getController();
            application.setManagerId(managerID);

            Stage tasksStage = new Stage();
            tasksStage.setTitle("Task Assignment");
            tasksStage.setScene(new Scene(root));

            // Optional: Close the current stage if needed
            // Stage currentStage = (Stage) assign.getScene().getWindow();
            // currentStage.close();

            tasksStage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void setManagerID(String managerID) {
        this.managerID = managerID;
    }
}
