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
 * SQL Utilities (connect db, select query, update query, echo query)
 * @author Phan Ba Hai
 */
public class SQL {

    private static Connection con;

    /**
     * Set Connection using SQLConfig object
     * @param config SQLConfig object
     */
    public static void createConnection(SQLConfig config) {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            if (config.getUrl() == null & config.getHost() != null
                    & config.getPort() != null & config.getDbName() != null) {
                con = DriverManager.getConnection("jdbc:sqlserver://" + config.getHost() 
                        + ":" + config.getPort() 
                        + ";databaseName=" + config.getDbName(), 
                        config.getUsername(), config.getPassword());
            } else {
                con = DriverManager.getConnection(config.getUrl(), 
                        config.getUsername(), config.getPassword());
            }
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Microsoft JDBC Driver can not be found!");
        } catch (SQLException sqle) {
            System.out.println("Can not connect to SQL Server!");
            System.out.println(sqle.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Set connection using config.json
     * @throws Exception
     */
    public static void createConnection() throws Exception {
        SQLConfig config = SQLConfigJson.open();
        createConnection(config);
    }

    /**
     *
     * @return java.sql.Connection
     */
    public static Connection getConnection() {
        return con;
    }

    /**
     *
     * @throws SQLException
     */
    public static void closeConnection() throws SQLException {
        con.close();
    }

    /**
     * Execute SQL SELECT Query
     * @param sql SQL Query
     * @return java.sql.ResultSet
     * @throws Exception
     */
    public static ResultSet selectQuery(String sql) throws Exception {
        createConnection();
        Statement stmt = con.createStatement();
        return stmt.executeQuery(sql);
    }

    /**
     * Execute SQL CREATE / DROP / ALTER / INSERT / UPDATE Query
     * @param sql SQL Query
     * @throws Exception
     */
    public static void updateQuery(String sql) throws Exception {
        createConnection();
        Statement stmt = con.createStatement();
        stmt.executeUpdate(sql);
    }

    /**
     * Print SQL query into command line
     * @param sql SQL Query
     */
    public static void echoQuery(String sql) {
        System.out.println(sql);
    }
}
