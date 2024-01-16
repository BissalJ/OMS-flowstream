package com.example.fxapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.sql.*;

public class HelloController {
    @FXML
    private Button cancelButton;
    @FXML
    private Label warningmsg;

    @FXML
    private TextField namefield;

    @FXML
    private PasswordField passwordField;

    public void LoginButtononAction(ActionEvent e) {
        if (!namefield.getText().isBlank() && !passwordField.getText().isBlank()) {
            validateLogin();
        } else {
            warningmsg.setText("Enter username and password.");
        }
    }

    public void cancelButtononAction(ActionEvent e) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void validateLogin() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String verifyLogin = "SELECT id, role FROM login WHERE username = ? AND password = ?";

        try {
            PreparedStatement preparedStatement = connectDB.prepareStatement(verifyLogin);
            preparedStatement.setString(1, namefield.getText());
            preparedStatement.setString(2, passwordField.getText());
            ResultSet queryResult = preparedStatement.executeQuery();

            while (queryResult.next()) {
                String id = queryResult.getString("id");
                String role = queryResult.getString("role");

                if (role.equals("employee")) {
                    warningmsg.setText("Welcome");
                    openEmployeeScreen(id);
                } else if (role.equals("manager")) {
                    warningmsg.setText("Welcome");
                    HOME(id);
                } else {
                    warningmsg.setText("Invalid Login. Please login again");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    void openEmployeeScreen(String employeeID) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("employee.fxml"));
            Parent root = loader.load();

            Employee employeeController = loader.getController();
            employeeController.setEmployeeID(employeeID);

            Stage employeeStage = new Stage();
            employeeStage.setTitle("Employee Screen");
            employeeStage.setScene(new Scene(root));

            Stage loginStage = (Stage) cancelButton.getScene().getWindow();
            loginStage.close();

            employeeStage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public String getNameFieldText() {
        return namefield.getText();
    }



    void HOME(String managerID) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("HOME.fxml"));
            Parent root = loader.load();

            HOMECONTROLLER homeController = loader.getController();
            homeController.setManagerID(managerID);

            Stage homeStage = new Stage();
            homeStage.setTitle("HOME Screen");
            homeStage.setScene(new Scene(root));

            Stage loginStage = (Stage) cancelButton.getScene().getWindow();
            loginStage.close();

            homeStage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    public String getPasswordFieldText() {
        return passwordField.getText();
    }
}
