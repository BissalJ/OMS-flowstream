package com.example.fxapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Todo {
    @FXML
    private Button t1;
    @FXML
    private Button t2;
    @FXML
    private Button t3;
    @FXML
    private Button t4;
    @FXML
    private Button t5;
    @FXML
    private Button t6;
    @FXML
    private Button back;
    @FXML
    private Button done;
    @FXML
    private TextArea taskArea;
    @FXML
    private Label deadline;
    private String employeeID;
    private int currentTaskId = 0; // Store the task ID of the currently displayed task

    DatabaseConnection connectNow = new DatabaseConnection();
    Connection connectDB = connectNow.getConnection();

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public void backAction(ActionEvent e) {
        Stage stage = (Stage) back.getScene().getWindow();
        stage.close();
    }

    private void showTask(int taskIndex) {
        String getTodoListQuery = "SELECT task_description, task_id, due_date FROM TodoList WHERE assigned_to_employee_id = ?";
        StringBuilder taskD = new StringBuilder();

        try {
            PreparedStatement preparedStatement = connectDB.prepareStatement(getTodoListQuery);
            preparedStatement.setString(1, employeeID);

            ResultSet resultSet = preparedStatement.executeQuery();

            // Move to the desired task index
            int currentTaskIndex = 0;
            while (resultSet.next() && currentTaskIndex < taskIndex) {
                currentTaskIndex++;
            }

            // Check if there is at least one more task
            if (currentTaskIndex == taskIndex) {
                currentTaskId = resultSet.getInt("task_id"); // Store the task ID
                String task = resultSet.getString("task_description");
                String taskDeadline = resultSet.getString("due_date");

                taskD.append("\u2705 Task Description: ").append(task).append("\n");

                // Set the deadline label
                deadline.setText("Deadline: " + taskDeadline);

            } else {
                taskD.append("No more tasks found for the employee.");
                deadline.setText("No deadline available.");
            }

            // Assuming taskArea is your text area
            taskArea.setText(taskD.toString());

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    // Handler for t1 button
    public void t1Action(ActionEvent e) {
        showTask(1);
    }

    // Handler for t2 button
    public void t2Action(ActionEvent e) {
        showTask(2);
    }

    // Handler for t3 button
    public void t3Action(ActionEvent e) {
        showTask(3);
    }

    // Handler for t4 button
    public void t4Action(ActionEvent e) {
        showTask(4);
    }

    // Handler for t5 button
    public void t5Action(ActionEvent e) {
        showTask(5);
    }

    // Handler for Done button
    public void doneAction(ActionEvent e) {
        updateTaskStatus();
    }

    // Method to update the task status based on the stored task ID
    private void updateTaskStatus() {
        if (currentTaskId != 0) {
            String updateStatusQuery = "UPDATE todolist SET task_status = 1 WHERE assigned_to_employee_id = ? AND task_id = ?";

            try {
                PreparedStatement preparedStatement = connectDB.prepareStatement(updateStatusQuery);
                preparedStatement.setString(1, employeeID);
                preparedStatement.setInt(2, currentTaskId);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Task status updated successfully.");
                    // You can update the displayed tasks again if needed
                    showTask(1); // Assuming you want to show the next task
                } else {
                    System.out.println("Failed to update task status.");
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else {
            System.out.println("No task to update.");
        }
    }
}
