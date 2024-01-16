package com.example.fxapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class DatabaseConnection {
    public Connection databaselink;
 //   Connection conn=null;
    public static Connection getConnection() {
        String databaseName="oms";
        String databaseUser="root";
        String databasePassword="bj@sql";
        String url="jdbc:mysql://localhost/"+databaseName;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
           Connection conn=DriverManager.getConnection(url,databaseUser,databasePassword);
            return conn;
        } catch (SQLException | ClassNotFoundException e) {
            return null;
        }

    }


    public static ObservableList<employeedata> addEmployeeListData() {

        Connection connectDB=getConnection();
        ObservableList<employeedata> listData = FXCollections.observableArrayList();

        System.out.println("Size of listM: " + listData.size());

        String sql = "SELECT * FROM employee";
        try {
         PreparedStatement       prepare = connectDB.prepareStatement(sql);
        ResultSet    result = prepare.executeQuery();
            employeedata employeeD;

            while (result.next()) {
                employeeD = new employeedata(
                        result.getInt("employee_id"),
                        result.getString("firstName"),
                        result.getString("lastName"),
                        result.getString("gender"),
                        result.getString("PhoneNumber"),
                        result.getInt("DepartmentId"),
                        result.getInt("Project_id"),
                        result.getInt("manager_id"),
                        result.getString("email"),
                        result.getInt("salary"),
                        result.getString("position")

                );
                listData.add(employeeD);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }



}
