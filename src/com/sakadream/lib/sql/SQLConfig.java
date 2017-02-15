/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sakadream.lib.sql;

/**
 * SQLConfig object
 * @author Phan Ba Hai
 */
public class SQLConfig {
    private String host = "localhost";
    private String port = "1433";
    private String dbName;
    private String username = "sa";
    private String password;
    private String url;

    /**
     * default values: host = localhost, port = 1433, username = sa
     * @param dbName Database name
     * @param password SQL Server password
     */
    public SQLConfig(String dbName, String password) {
        this.dbName = dbName;
        this.password = password;
    }

    /**
     *
     * @param host SQL Server host name
     * @param port SQL Server port
     * @param dbName Database name
     * @param username SQL Server username
     * @param password SQL Server password
     */
    public SQLConfig(String host, String port, String dbName, String username, String password) {
        this.host = host;
        this.port = port;
        this.dbName = dbName;
        this.username = username;
        this.password = password;
    }

    /**
     * SQLConfig object for Hibernate Configuration XML file
     * @param dbName Database name
     * @param password SQL Server password
     * @param url The Connection String (hibernate.hibernate.connection.url property in hibernate.cfg.xml)
     */
    public SQLConfig(String dbName, String password, String url) {
        this.dbName = dbName;
        this.password = password;
        this.url = url;
    }

    /**
     *
     * @return String
     */
    public String getHost() {
        return host;
    }

    /**
     *
     * @return String
     */
    public String getPort() {
        return port;
    }

    /**
     *
     * @return String
     */
    public String getDbName() {
        return dbName;
    }

    /**
     *
     * @return String
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @return String
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @return String
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @param host SQL Server host name
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     *
     * @param port SQL Server port
     */
    public void setPort(String port) {
        this.port = port;
    }

    /**
     *
     * @param dbName Database name
     */
    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    /**
     *
     * @param username SQL Server username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @param password SQL Server password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @param url The Connection String (hibernate.hibernate.connection.url property in hibernate.cfg.xml)
     */
    public void setUrl(String url) {
        this.url = url;
    }
}
