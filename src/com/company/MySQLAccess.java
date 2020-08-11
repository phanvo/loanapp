package com.company;

import java.sql.*;

public class MySQLAccess {
    private Connection connection;
    private Statement statement;

    public MySQLAccess(String dbname) throws Exception{
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost/" + dbname,"root","");
        statement = connection.createStatement();
    }

    public ResultSet executeQuery(String query) throws Exception{
        return statement.executeQuery(query);
    }

    public void executeUpdate(String query, String[] strs) throws Exception{
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        for (int i = 0; i < strs.length; i++) {
            preparedStatement.setString(i + 1, strs[i]);
        }
        preparedStatement.executeUpdate();
    }

    public void closeConnection(){
        try {
            connection.close();
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}
