package com.example.fxapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Tasks {

    @FXML
    private Button assign;
    @FXML
    private Button back;
    @FXML
    private Label status;
    @FXML
    private DatePicker due_date;
    @FXML
    private TextArea description;
    @FXML
    private TextField id;

    DatabaseConnection connectNow = new DatabaseConnection();
    Connection connectDB = connectNow.getConnection();

    public void backAction(ActionEvent e) {
        Stage stage = (Stage) back.getScene().getWindow();
        stage.close();
    }

    public void assignAction(ActionEvent e) {
        // Get values from the UI components
        String taskDescription = description.getText();
        String employeeIdText = id.getText();
        String dueDate = due_date.getValue().toString();

        // Validate input
        if (taskDescription.isBlank() || employeeIdText.isBlank()) {
            // Show an error message or handle invalid input
            status.setText("Please provide task description and employee ID.");
            return;
        }

        try {
            // Convert employee ID to integer
            int assignedEmployeeId = Integer.parseInt(employeeIdText);

            // Insert task assignment into the database
            String insertTaskQuery = "INSERT INTO todolist (task_id, task_description, task_status, assigned_to_employee_id, due_date, created_at) VALUES (DEFAULT, ?, 0, ?, ?, CURRENT_TIMESTAMP)";

            PreparedStatement preparedStatement = connectDB.prepareStatement(insertTaskQuery);
            preparedStatement.setString(1, taskDescription);
            preparedStatement.setInt(2, assignedEmployeeId);
            preparedStatement.setString(3, dueDate);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                status.setText("Task assigned successfully.");
            } else {
                status.setText("Failed to assign task.");
            }

        } catch (NumberFormatException | SQLException ex) {
            ex.printStackTrace();
            status.setText("Invalid employee ID. Please enter a valid integer.");
        }
    }


    public void setEmployeeID(String employeeID) {
    }

    public void setManagerId(String managerID) {
    }
}
