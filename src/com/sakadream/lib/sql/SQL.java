/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sakadream.lib.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Phan Ba Hai
 */

public class SQL {
    private static Connection con;
    
    public static void createConnection(SQLConfig config){
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://" + config.getHost() + 
                    ":" + config.getPort() + 
                    ";databaseName=" + config.getDbName(), config.getUsername(), config.getPassword());
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Microsoft JDBC Driver can not be found!");
        } catch (SQLException sqle) {
            System.out.println("Can not connect to SQL Server!");
            System.out.println(sqle.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static void createConnection() throws Exception {
        SQLConfig config = SQLConfigJson.open();
        createConnection(config);
    }
    
    public static Connection getConnection() {
        return con;
    }
    
    public static void closeConnection() throws SQLException {
        con.close();
    }
    
    public static ResultSet selectQuery(String sql) throws Exception {
        createConnection();
        Statement stmt = con.createStatement();
        return stmt.executeQuery(sql);
    }
    
    public static void updateQuery(String sql) throws Exception {
        createConnection();
        Statement stmt = con.createStatement();
        stmt.executeUpdate(sql);
    }
    
    public static void echoQuery(String sql) {
        System.out.println(sql);
    }
}