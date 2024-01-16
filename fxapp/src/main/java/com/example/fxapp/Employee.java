package com.example.fxapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
public class Employee {
    @FXML
    private Button applicationStatus;
    @FXML
    private Button application;
    @FXML
    private TextArea applicationS;
    @FXML
    private Button todoB;
    @FXML
    private Button sendButton;
    @FXML
    private Button logout;
    @FXML
    private DatePicker from;
    @FXML
    private DatePicker to;
    @FXML
    private TextArea reason;
    @FXML
    private Button info;
    @FXML
    private Button project;
    @FXML
    private Label btnS;
    @FXML
    private Label status;
    @FXML
    private Label Ename;
    @FXML
    private Pane todoP;
    @FXML
    private Pane app;
    @FXML
    private Pane noticepane;

    @FXML
    private TextArea noticeBoardTextArea;

    private String employeeID;

    DatabaseConnection connectNow = new DatabaseConnection();
    Connection connectDB = connectNow.getConnection();

    public void logoutAction(ActionEvent e) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = loader.load();

            Stage loginStage = new Stage();
            loginStage.setTitle("Login Screen");
            loginStage.setScene(new Scene(root));

            Stage currentStage = (Stage) logout.getScene().getWindow();
            currentStage.close();

            loginStage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public void todoAction(ActionEvent event) throws SQLException {
//
//
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("tasks.fxml"));
//            Parent root = loader.load();
//
//            Tasks tasksController = loader.getController();
//            tasksController.setEmployeeID(employeeID);
//
//            Stage tasksStage = new Stage();
//            tasksStage.setTitle("Task Assignment");
//            tasksStage.setScene(new Scene(root));
//
//            // Optional: Close the current stage if needed
//            // Stage currentStage = (Stage) assign.getScene().getWindow();
//            // currentStage.close();
//
//            tasksStage.show();
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("todo.fxml"));
            Parent root = loader.load();

            Todo todoController = loader.getController();
            todoController.setEmployeeID(employeeID);

            Stage todo = new Stage();
            todo.setTitle("TO-DO LIST");
            todo.setScene(new Scene(root));


            todo.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
//        app.setVisible(false);
//        btnS.setText("TO-DO LIST");
//
//        // Fetch tasks from the database based on the employee ID
//        String getTodoListQuery = "SELECT task_description,task_id FROM TodoList WHERE assigned_to_employee_id = ?";
//        StringBuilder taskD = new StringBuilder();
//
//        try {
//            PreparedStatement preparedStatement = connectDB.prepareStatement(getTodoListQuery);
//            preparedStatement.setString(1, employeeID);
//
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//            while (resultSet.next()) {
//                String task = resultSet.getString("task_description");
//                int id=resultSet.getInt("task_id");
//                taskD.append("\u2705 Task no : ").append(id).append("\nTask Description: ").append(task).append("\n");
//                applicationS.setText(taskD.toString());
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    private void displayMeetingsForManager(int managerId) {
        noticepane.setVisible(true);
        noticeBoardTextArea.setVisible(true);

        // SQL query to retrieve meetings for a specific manager
        String getMeetingsQuery = "SELECT * FROM meeting WHERE manager_id = ?";

        try (
             PreparedStatement preparedStatement = connectDB.prepareStatement(getMeetingsQuery)) {
            // Set the manager ID parameter in the query
            preparedStatement.setInt(1, managerId);

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Display meeting information in the text area
            while (resultSet.next()) {
                int meetingId = resultSet.getInt("meeting_id");
                String meetingText = resultSet.getString("meeting_text");
                String meetingDate = resultSet.getString("meeting_date");

                // Display meeting information in the text area
                noticeBoardTextArea.appendText("Meeting ID: " + meetingId + "\n");
                noticeBoardTextArea.appendText("Meeting Text: " + meetingText + "\n");
                noticeBoardTextArea.appendText("Meeting Date: " + meetingDate + "\n\n");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Your existing noticeBoardAction method
    public void noticeBoardAction(ActionEvent e) {

        applicationS.setVisible(false);
        //app.setVisible(false);
        noticepane.setVisible(true);
        noticeBoardTextArea.setVisible(true);
        btnS.setText("NOTICEBOARD");
        int managerId = getEmployeeManagerId();
        displayMeetingsForManager(managerId); // Replace 'managerId' with the actual manager ID
    }

    public void projectdAction(ActionEvent e) {
        noticeBoardTextArea.setVisible(false);
        noticepane.setVisible(false);
        app.setVisible(false);
        applicationS.setVisible(true);
        btnS.setText("PROJECT DETAILS");

        // Display project details for the currently logged-in employee
        displayEmployeeProjects();
    }

    private void displayEmployeeProjects() {
        // SQL query to retrieve project details for the given employee ID
        String getProjectsQuery = "SELECT * FROM project WHERE PROJECT_HEAD = ?";

        StringBuilder projectDetails = new StringBuilder();
        int managerId = getEmployeeManagerId();

        try (PreparedStatement preparedStatement = connectDB.prepareStatement(getProjectsQuery)) {
            preparedStatement.setInt(1, managerId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int projectId = resultSet.getInt("projectid");
                int departmentId = resultSet.getInt("DEPARTMENT_ID");
                String projectHead = resultSet.getString("PROJECT_HEAD");
                int duration = resultSet.getInt("DURATION");
                double budget = resultSet.getDouble("BUDGET");
                String projectName = resultSet.getString("P_NAME");

                // Append project details to the StringBuilder
                projectDetails.append("Project ID: ").append(projectId).append("\n");
                projectDetails.append("Department ID: ").append(departmentId).append("\n");
                projectDetails.append("Project Head: ").append(projectHead).append("\n");
                projectDetails.append("Duration: ").append(duration).append(" months\n");
                projectDetails.append("Budget: $").append(budget).append("\n");
                projectDetails.append("Project Name: ").append(projectName).append("\n\n");
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }

        // Set the text in the TextArea
        applicationS.setText(projectDetails.toString());
    }


    public void infoAction(ActionEvent e) {
        noticepane.setVisible(false);
        app.setVisible(false);
        noticeBoardTextArea.setVisible(false);
        applicationS.setVisible(true);
        btnS.setText("PERSONAL INFORMATION");
        displayEmployeeInfo();
    }
    public void appAction(ActionEvent e) {
        noticepane.setVisible(false);
        applicationS.setVisible(false);
        app.setVisible(true);
        btnS.setText("LEAVE APPLICATION");
    }

    public void sendAction(ActionEvent e) {
        // Get values from the UI components
        LocalDate startDate = from.getValue();
        LocalDate endDate = to.getValue();
        String leaveReason = reason.getText();

        // Validate input
        if (startDate == null || endDate == null || leaveReason.isBlank()) {
            // Show an error message or handle invalid input
            return;
        }
        String insertLeaveQuery = "INSERT INTO leave_application (employeeid, reason, start_date, end_date) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connectDB.prepareStatement(insertLeaveQuery, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, employeeID);
            preparedStatement.setString(2, leaveReason);
            preparedStatement.setDate(3, Date.valueOf(startDate));
            preparedStatement.setDate(4, Date.valueOf(endDate));

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                // Get the generated leave_id
                var generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int leaveID = generatedKeys.getInt(1);

                    status.setText("Leave application submitted successfully. Leave ID: " + leaveID);
                }
            } else {
                status.setText("Failed to submit leave application.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }



    public void StatusAction(ActionEvent e) throws SQLException {
        noticepane.setVisible(false);
        app.setVisible(false);
        applicationS.setVisible(true);
        btnS.setText("APPLICATION STATUS");

        String query = "SELECT leave_id, status FROM leave_application WHERE employeeid = ?";
        StringBuilder statusText = new StringBuilder();
        try {
            PreparedStatement preparedStatement = connectDB.prepareStatement(query);
            preparedStatement.setString(1, employeeID);

            ResultSet queryResult2 = preparedStatement.executeQuery();

            while (queryResult2.next()) {
                int leaveID = queryResult2.getInt("leave_id");
                String status = queryResult2.getString("status");

                // Append the leave information to the statusText
                statusText.append("\u2705 Leave ID: ").append(leaveID).append(", Status: ").append(status).append("\n");

                applicationS.setText(statusText.toString());
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }



    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
        // Call a method to fetch and display employee information using the employee ID
        employeeinfo();
    }

    // In EmployeeController
    public void employeeinfo() {
        String getEmployeeInfo = "SELECT * FROM employee WHERE employeeid = ?";

        try {
            PreparedStatement preparedStatement = connectDB.prepareStatement(getEmployeeInfo);
            preparedStatement.setString(1, employeeID);

            ResultSet queryResult = preparedStatement.executeQuery();

            while (queryResult.next()) {
                // Populate the labels with employee information
                Ename.setText(queryResult.getString("firstname"));

                // Additional attributes
                int employeeID = Integer.parseInt(queryResult.getString("employeeid"));
                String managerID = queryResult.getString("managerid");
                int departmentID = Integer.parseInt(queryResult.getString("DepartmentId"));
                String phoneNumber = queryResult.getString("PhoneNumber");
                String email = queryResult.getString("Email");
                String position = queryResult.getString("Position");

                // You can use these attributes as needed
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void displayEmployeeInfo() {
        String getEmployeeInfo = "SELECT * FROM employee WHERE employeeid = ?";

        try {
            PreparedStatement preparedStatement = connectDB.prepareStatement(getEmployeeInfo);
            preparedStatement.setString(1, employeeID);

            ResultSet queryResult = preparedStatement.executeQuery();

            StringBuilder infoText = new StringBuilder();

            while (queryResult.next()) {
                // Append employee information to the infoText
                infoText.append("Name: ").append(queryResult.getString("firstname"))
                        .append(" ").append(queryResult.getString("lastname")).append("\n")
                        .append("Employee ID: ").append(queryResult.getString("employeeid")).append("\n")
                        .append("Manager ID: ").append(queryResult.getString("managerid")).append("\n")
                        .append("Department ID: ").append(queryResult.getString("DepartmentId")).append("\n")
                        .append("Phone Number: ").append(queryResult.getString("PhoneNumber")).append("\n")
                        .append("Email: ").append(queryResult.getString("email")).append("\n")
                        .append("Position: ").append(queryResult.getString("position")).append("\n");
            }

            // Set the text in the TextArea
            applicationS.setText(infoText.toString());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // This method is called when the "Info" button is pressed
    private void handleSQLException(SQLException e) {
        // Handle SQLException (e.g., log the error, show a user-friendly message)
        e.printStackTrace();
    }


    private int getEmployeeManagerId() {
        int managerId = -1; // Default value if no manager is found

        // SQL query to retrieve the manager ID for the given employee ID
        String getManagerIdQuery = "SELECT managerid FROM employee WHERE employeeid = ?";

        try (PreparedStatement preparedStatement = connectDB.prepareStatement(getManagerIdQuery)) {
            preparedStatement.setString(1, employeeID);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                managerId = resultSet.getInt("managerid");
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }

        return managerId;
    }


}
