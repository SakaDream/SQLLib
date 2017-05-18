/*
 * Copyright (c) 2017 Ba Hai Phan
 * Licensed under the MIT Licence - https://raw.githubusercontent.com/SakaDream/SQLLib/master/LICENSE.
 */
package com.sakadream.sql;

import static com.sakadream.sql.DbType.SQLServer;
import static com.sakadream.sql.DbType.SQLite;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * SQL Utilities (connect db, select query, update query, echo query)
 *
 * @author Phan Ba Hai
 */
public class SQL {

    private static Connection con;
    private static String message = "This database type is not supported, "
            + "this library supports SQL Server and SQLite. "
            + "Other databases comming soon";

    /**
     * Set Connection using SQLConfig object
     *
     * @param config SQLConfig object
     */
    public static void createConnection(SQLConfig config) {
        try {
            Class.forName(config.getClassName().getUrl());
            switch (config.getDBType()) {
                case SQLServer:
                    con = DriverManager.getConnection(config.getUrl(), config.getUsername(), config.getPassword());
                    break;
                case SQLite:
                    try {
                        con = DriverManager.getConnection(config.getUrl());
                    } catch (SQLException sqle) {
                        File file = new File(config.getDbName());
                        file.createNewFile();
                        con = DriverManager.getConnection(config.getUrl());
                    }
                    break;
                case MySQL:
                    con = DriverManager.getConnection(config.getUrl(), config.getUsername(), config.getPassword());
                    break;
                default:
                    throw new AssertionError(message);
            }
        } catch (ClassNotFoundException cnfe) {
            System.out.println("JDBC Driver can not be found!");
        } catch (SQLException sqle) {
            System.err.println("Can not connect to the database!");
            System.out.println(sqle.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Set connection using config.json
     *
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
     *
     * @param sql SQL Query
     * @return java.sql.ResultSet
     */
    public static ResultSet selectQuery(String sql) {
        try {
            try {
                createConnection();
            } catch (FileNotFoundException fnfe) {
                createConnection(SQLConfigJson.getHibernateCfg());
            }
            Statement stmt = con.createStatement();
            return stmt.executeQuery(sql);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * Execute SQL CREATE / DROP / ALTER / INSERT / UPDATE Query
     *
     * @param sql SQL Query
     */
    public static void updateQuery(String sql) {
        try {
            try {
                createConnection();
            } catch (FileNotFoundException fnfe) {
                createConnection(SQLConfigJson.getHibernateCfg());
            }
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql);
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Print SQL query into command line
     *
     * @param sql SQL Query
     */
    public static void echoQuery(String sql) {
        System.out.println(sql);
    }
}
