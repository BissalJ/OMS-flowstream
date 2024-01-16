package com.example.fxapp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.*;
import java.util.Date;

public class manager_control implements Initializable {
    @FXML
    private TableColumn<employeedata, Integer> DeptIDColo;
    @FXML
    private TableColumn<employeedata, String> phoneNumberColo;

    @FXML
    private TableColumn<employeedata, String> EmailCOLO;
    @FXML
    private TableColumn<employeedata, Integer> managerIDCOLO;
    @FXML
    private TableColumn<employeedata, String> GenderTable;
    @FXML
    private TableColumn<employeedata, Integer> enployeeIDDCOLO;

    @FXML
    private TableView<employeedata> tableView_;
    @FXML
    private TableColumn<employeedata, Integer> ProjectIDCOLO;
    @FXML
    private TableColumn<employeedata, String> PositionColo;
    @FXML
    private TableColumn<employeedata, Integer> salary_COlo;

    @FXML
    private TableColumn<employeedata, String> FirstNameColo;

    @FXML
    private TableColumn<employeedata, String> LastNameColo;


    @FXML
    private TextField ProjectIDFIELD;
    @FXML
    private TextField DepartmentIDField;
    @FXML
    private TextField EmployeeIdField;
    @FXML
    private TextField emailIDField;
    @FXML
    private TextField FirstNameField;
    @FXML
    private ComboBox<String> field__chooseGender;
    @FXML
    private TextField lastNameField;
    @FXML
    private ComboBox<String> position_field;
    @FXML
    private TextField managerIDField;
    @FXML
    private TextField salaryField1;
    @FXML
    private TextField numberField;


    @FXML
    private Button MeetingsButton;

    @FXML
    private Button AddEmployeeButton;

    @FXML
    private Button UpdateButoonEmloyee;

    @FXML
    private Button clearEmployeeBtn;
    @FXML
    private Button LOGOUTEMPLOYEEINFORMATION;
    @FXML
    private Button deleteEmployeeFieldsButton1;
    private String managerID;

    // Other fields and methods

    public void setManagerID(String managerID) {
        this.managerID = managerID;
        // Perform any additional logic needed when setting the manager ID
    }
    Connection conn =null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    Statement statement=null;
ObservableList<employeedata> listM;
ObservableList<employeedata> data;


   // This observable list can be used in a JavaFX application for displaying or manipulating the employee data.


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> list = FXCollections.observableArrayList("Female","Male");
        ObservableList<String> list1 = FXCollections.observableArrayList("Web Developer","HR","APP Developer","Manager");

        field__chooseGender.setItems(list);

        position_field.setItems(list1);
    }
    @FXML
   public void actionE(ActionEvent event) {
        Object selectedItem = field__chooseGender.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            String s = selectedItem.toString();

        }
    }

    public void Actionposition(ActionEvent event)
    {
        Object selectedItem = position_field.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            String s = selectedItem.toString();

        }
    }


    public void addEmployeeShowListData() {
   //     ObservableList<employeedata> addEmployeeList = addEmployeeListData();
        System.out.println("BYE");
        enployeeIDDCOLO.setCellValueFactory(new PropertyValueFactory<>("employeeid"));
        FirstNameColo.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        LastNameColo.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        GenderTable.setCellValueFactory(new PropertyValueFactory<>("gender"));
        phoneNumberColo.setCellValueFactory(new PropertyValueFactory<>("PhoneNumber"));
        DeptIDColo.setCellValueFactory(new PropertyValueFactory<>("DepartmentId"));
        managerIDCOLO.setCellValueFactory(new PropertyValueFactory<>("managerid"));
        EmailCOLO.setCellValueFactory(new PropertyValueFactory<>("email"));
        ProjectIDCOLO.setCellValueFactory(new PropertyValueFactory<>("Project_id"));
        PositionColo.setCellValueFactory(new PropertyValueFactory<>("position"));
        salary_COlo.setCellValueFactory(new PropertyValueFactory<>("salary"));

        listM = DatabaseConnection.addEmployeeListData();
        tableView_.setItems(listM);
        System.out.println("Size of listM: " + listM.size());
    }

    public void addEmployeeReset() {
        EmployeeIdField.setText("");
        FirstNameField.setText("");
        lastNameField.setText("");
        field__chooseGender.getSelectionModel().clearSelection();
        numberField.setText("");
        managerIDField.setText("");
        DepartmentIDField.setText("");
        emailIDField.setText("");
        ProjectIDFIELD.setText("");
        salaryField1.setText("");
        position_field.getSelectionModel().clearSelection();

    }

    public void employee_update() {
        conn =DatabaseConnection.getConnection();
        int employeeid = Integer.parseInt(EmployeeIdField.getText());
        String firstName = FirstNameField.getText();
        String lastName = lastNameField.getText();
        String gender = field__chooseGender.getSelectionModel().getSelectedItem();
        String position = position_field.getSelectionModel().getSelectedItem();
        BigDecimal salary = new BigDecimal(salaryField1.getText());
        String phoneNumber = numberField.getText();
        int departmentId = Integer.parseInt(DepartmentIDField.getText());
        int managerid = Integer.parseInt(managerIDField.getText());
        String email = emailIDField.getText();
        int projectId = Integer.parseInt(ProjectIDFIELD.getText());


        // Call the stored procedure
        try {
            CallableStatement callableStatement = conn.prepareCall("{call UpdateEmployee(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            callableStatement.setInt(1, employeeid);
            callableStatement.setString(2, firstName);
            callableStatement.setString(3, lastName);
            callableStatement.setString(4, gender);
            callableStatement.setString(5, position);
            callableStatement.setBigDecimal(6, salary);
            callableStatement.setString(7, phoneNumber);
            callableStatement.setInt(8, departmentId);
            callableStatement.setInt(9, managerid);
            callableStatement.setString(10, email);
            callableStatement.setInt(11, projectId);
            boolean hasResults = callableStatement.execute();

            System.out.println("Stored Procedure Executed Successfully");
            callableStatement.close();
            addEmployeeShowListData();
            addEmployeeReset();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addButtonEmployeeMethod() {

            String sql = "INSERT INTO employee"
                    + "(employeeid,firstName,lastName,gender,PhoneNumber,DepartmentId,Project_id,managerid,email,salary,position) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?)";

            conn = DatabaseConnection.getConnection();

            try {
                Alert alert;
                if (EmployeeIdField.getText().isEmpty()
                        || FirstNameField.getText().isEmpty()
                        || lastNameField.getText().isEmpty()
                        ||  field__chooseGender.getSelectionModel().getSelectedItem() == null
                        ||  numberField.getText().isEmpty()
                        ||  DepartmentIDField.getText().isEmpty()
                        || managerIDField.getText().isEmpty()
                        || emailIDField.getText().isEmpty()
                        ||  position_field.getSelectionModel().getSelectedItem() == null
                        || ProjectIDFIELD.getText().isEmpty()
                        || salaryField1.getText().isEmpty()
                ) {
                    alert("Please fill all the fields!");
                } else {
                    System.out.println("Hello");
                    String check = "SELECT employeeid FROM employee WHERE employeeid = '"
                            + EmployeeIdField.getText() + "'";

                    statement = conn.createStatement();
                    rs = statement.executeQuery(check);

                    if (rs.next()) {
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Employee ID: " + EmployeeIdField.getText() + " was already exist!");
                        addEmployeeReset();
                        alert.showAndWait();
                    } else {

                        pst = conn.prepareStatement(sql);
                        pst.setString(1, EmployeeIdField.getText());
                        pst.setString(2, FirstNameField.getText());
                        pst.setString(3, lastNameField.getText());
                        pst.setString(4,(String) field__chooseGender.getSelectionModel().getSelectedItem());
                        pst.setString(5, numberField.getText());
                        pst.setString(6, DepartmentIDField.getText());
                        pst.setString(7, ProjectIDFIELD.getText());
                        pst.setString(8, managerIDField.getText());
                        pst.setString(9, emailIDField.getText());
                        pst.setString(10, salaryField1.getText());
                        pst.setString(11, (String) position_field.getSelectionModel().getSelectedItem());
                        pst.execute();
                        alert_message("Successfully added!");
                        addEmployeeShowListData();
                        addEmployeeReset();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    public void alert(String text)
    {
        Alert alert;

        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();

    }

    public void alert_message(String info)
    {
        Alert alert;
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Message");
        alert.setHeaderText(null);
        alert.setContentText(info);
        alert.showAndWait();
    }

    public void employee_delete() {
        String sql = "DELETE FROM employee WHERE employeeid = '"
                + EmployeeIdField.getText() + "'";

        conn = DatabaseConnection.getConnection();

        try {

            Alert alert;
            if (EmployeeIdField.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Cofirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to DELETE Employee ID: " + EmployeeIdField.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    statement = conn.createStatement();
                    statement.executeUpdate(sql);

                    String deleteInfo = "DELETE FROM employee_info WHERE employeeid = '"
                            + EmployeeIdField.getText() + "'";

                    pst = conn.prepareStatement(deleteInfo);
                    pst.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();

                    addEmployeeShowListData();
                    addEmployeeReset();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();}    }

    public void logoutAction(ActionEvent e) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = loader.load();

            Stage loginStage = new Stage();
            loginStage.setTitle("Login Screen");
            loginStage.setScene(new Scene(root));

            Stage currentStage =(Stage) LOGOUTEMPLOYEEINFORMATION.getScene().getWindow();
            currentStage.close();

            loginStage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void CLOSEWINDOW(ActionEvent event) {
    System.exit(0);
    }

}


