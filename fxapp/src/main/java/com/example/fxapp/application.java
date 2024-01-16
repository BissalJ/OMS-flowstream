package com.example.fxapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class application {
    private int intValue;
    @FXML
    private Button DONETASKS;

    @FXML
    private Button HOME;

    @FXML
    private Button application2;

    @FXML
    private Button application3;

    @FXML
    private Button application4;

    @FXML
    private Button application5;
    @FXML
    private Button applicaton1;
    @FXML
    private TextArea textareaStatus;
    @FXML
    void BACKHOME(ActionEvent event) {

        Stage stage = (Stage) HOME.getScene().getWindow();
        stage.close();
    }
    private String managerId;
    private int currentAppliactionId= 0;;
    DatabaseConnection connectNow = new DatabaseConnection();
    Connection connectDB = connectNow.getConnection();

    @FXML
    void DONEACTION(ActionEvent event) {
        approvedTasks();
    }

    public void setManagerID(String managerID) {
        this.managerId=managerID;
        // Perform any additional logic needed when setting the manager ID
    }

    private void approvedTasks() {
        if ( currentAppliactionId != 0) {
            String updateStatusQuery = "UPDATE leave_application SET status = 'approved' WHERE leave_id = ?";
            try {
                PreparedStatement preparedStatement = connectDB.prepareStatement(updateStatusQuery);
                preparedStatement.setInt(1,  currentAppliactionId);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Application status updated successfully.");
                    // You can update the displayed tasks again if needed
                    showApplications(1); // Assuming you want to show the next task
                } else {
                    System.out.println("Failed to update Application status.");
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else {
            System.out.println("No task to update.");
        }
    }






    private void showApplications(int application_Index) {
        String Query = "SELECT leave_id, employee_id, reason, start_date, end_date FROM leave_application WHERE manager_id = ? ";
        StringBuilder taskD = new StringBuilder();
        System.out.println("I am inside show application application is" + currentAppliactionId);

        try (PreparedStatement preparedStatement = connectDB.prepareStatement(Query)) {
            preparedStatement.setString(1, managerId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                System.out.println("I am breaking show in resultSet application is" + currentAppliactionId);

                // Move to the desired task index
                int currentTaskIndex = 0;
                System.out.println(resultSet.next());
                while (resultSet.next() && currentTaskIndex < application_Index) {
                    System.out.println("Hello");
                    currentTaskIndex++;
                }
                System.out.println("I am outside if"+currentTaskIndex);
                System.out.println("I am outside if"+application_Index);

                // Check if there is at least one more task
                if (currentTaskIndex == application_Index) {
                    System.out.println("I am inside if"+currentTaskIndex);
                    System.out.println("I am inside if"+application_Index);

                    // Retrieve information about the leave application
                    String employeeId = resultSet.getString("employee_id");
                    System.out.println("The current application is" + employeeId);
                    currentAppliactionId = resultSet.getInt("leave_id"); // Store the leave ID
                    System.out.println("The current application is" + currentAppliactionId);
                    //  String employeeId = resultSet.getString("employee_id");
                    String reason = resultSet.getString("reason");
                    String startDate = resultSet.getString("start_date");
                    String endDate = resultSet.getString("end_date");

                    // Append the information to the StringBuilder
                    taskD.append("\u2705 Leave ID: ").append(currentAppliactionId).append("\n");
                    taskD.append("Employee ID: ").append(employeeId).append("\n");
                    taskD.append("Reason: ").append(reason).append("\n");
                    taskD.append("Start Date: ").append(startDate).append("\n");
                    taskD.append("End Date: ").append(endDate).append("\n");
                } else {
                    // If no more pending leave applications are found
                    taskD.append("No more pending leave applications found for approval.");
                }
            }
            // Assuming textareaStatus is your text area, set the text to the formatted information
            textareaStatus.setText(taskD.toString());

        } catch (SQLException ex) {
            // Handle any SQLException that might occur
            ex.printStackTrace();
        }
    }
    public void t1Action(ActionEvent e) {
        showApplications(1);
    }

    // Handler for t2 button
    public void t2Action(ActionEvent e) {
        showApplications(2);
    }

    // Handler for t3 button
    public void t3Action(ActionEvent e) {
        showApplications(3);
    }

    // Handler for t4 button
    public void t4Action(ActionEvent e) {
        showApplications(4);
    }

    // Handler for t5 button
    public void t5Action(ActionEvent e) {
        showApplications(5);
    }


    public void setManagerId(String managerID) {
    }
}
