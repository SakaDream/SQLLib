/*
 * Copyright (c) 2017 Ba Hai Phan
 * Licensed under the MIT Licence - https://raw.githubusercontent.com/SakaDream/SQLLib/master/LICENSE.
 */
package com.sakadream.sql;

/**
 * SQLConfig object
 * @author Phan Ba Hai
 */
public final class SQLConfig {

    private static String message = "This database type is not supported, "
            + "this library supports SQL Server, SQLite and MySQL. "
            + "Other databases comming soon";
    private Dialect dialect = new Dialect();
    private Boolean hibernateConfig = false;
    private String host = "";
    private String port = "";
    private String dbName = "";
    private String username = "";
    private String password = "";
    private String url = "";

    /**
     * SQLConfig with no argument (for getter / setter)
     */
    public SQLConfig() {
    }

    /**
     * Default values for SQL Server Database: host = localhost, port = 1433
     * Default values for MySQL Database: host = localhost, port = 3306 Note:
     * SQLite JDBC not support password
     * @param dbType Database type
     * @param dbName Database name
     * @param username Server username
     * @param password Server password
     */
    public SQLConfig(String dbType, String dbName, String username, String password) {
        DialectsList dl = new DialectsList();
        this.dialect = new Dialect(dbType, dl.getDialectByDBType(dbType).getClassName());
        switch (dbType) {
            case "SQL Server":
                this.host = "localhost";
                this.port = "1433";
                this.username = username;
                this.dbName = dbName;
                this.password = password;
                break;
            case "SQLite":
                this.dbName = dbName;
                optimizeDbName();
                break;
            case "MySQL":
                this.host = "localhost";
                this.port = "3306";
                this.username = username;
                this.dbName = dbName;
                this.password = password;
            default:
                throw new AssertionError(message);
        }
        this.url = generateURL();
    }

    /**
     * SQLConfig object for SQL Server / MySQL Database
     * @param dbType Database type
     * @param host Server host name
     * @param port Server port
     * @param dbName Database name
     * @param username Server username
     * @param password Server password
     */
    public SQLConfig(String dbType, String host, String port, String dbName,
            String username, String password) {
        DialectsList dl = new DialectsList();
        this.dialect = dl.getDialectByDBType(dbType);
        this.host = host;
        this.port = port;
        this.dbName = dbName;
        this.username = username;
        this.password = password;
        this.url = generateURL();
    }

    /**
     * SQLConfig object for Hibernate Configuration XML file
     * @param username Server username
     * @param password Server password
     * @param url The Connection String (hibernate.hibernate.connection.url
     * property in hibernate.cfg.xml) or JDBC URL
     */
    public SQLConfig(String username, String password, String url) {
        DialectsList dl = new DialectsList();
        this.dialect = dl.getDialectByUrl(url);
        this.username = username;
        this.password = password;
        this.url = url;
    }

    /**
     * SQLConfig object for URL Connection only
     * @param url The Connection String
     */
    public SQLConfig(String url) {
        DialectsList dl = new DialectsList();
        this.dialect = dl.getDialectByUrl(url);
        this.url = url;
    }

    /**
     * Generate URL using database configuration
     * @param host Database host
     * @param port Database port
     * @param dbName Database / SQLite file name
     * @param username Database username
     * @param password Database password
     * @return String JDBC URL
     */
    public String generateURL(String host, String port, String dbName,
            String username, String password) {
        switch (dialect.getDialect()) {
            case "SQL Server":
                return "jdbc:sqlserver://" + host + ":" + port
                        + ";databaseName=" + dbName
                        + ";user=" + username
                        + ";password=" + password + ";";
            case "SQLite":
                return "jdbc:sqlite:" + dbName;
            case "MySQL":
                return "jdbc:mysql://" + host + ":" + port
                        + "/" + dbName + "?user=" + username
                        + "&password=" + password;
            default:
                return "";
        }
    }

    /**
     * Generate URL using SQLConfig object
     * @return String URL String
     */
    public String generateURL() {
        switch (dialect.getDialect()) {
            case "SQL Server":
                return "jdbc:sqlserver://" + this.host + ":" + this.port
                        + ";databaseName=" + this.dbName
                        + ";user=" + this.username
                        + ";password=" + this.password + ";";
            case "SQLite":
                return "jdbc:sqlite:" + this.dbName;
            case "MySQL":
                return "jdbc:mysql://" + this.host + ":" + this.port
                        + "/" + this.dbName + "?user=" + this.username
                        + "&password=" + this.password;
            default:
                return "";
        }
    }

    /**
     * Get DB Type
     * @return String
     */
    public String getDbType() {
        return this.dialect.getDialect();
    }

    /**
     * Get JDBC Classname
     * @return String
     */
    public String getClassName() {
        return this.dialect.getClassName();
    }

    /**
     * Check hibernateConfig value is true
     * @return Boolean
     */
    public Boolean isHibernateConfig() {
        return this.hibernateConfig;
    }

    /**
     * Get Server Host
     * @return String
     */
    public String getHost() {
        return host;
    }

    /**
     * Get Server Port
     * @return String
     */
    public String getPort() {
        return port;
    }

    /**
     * Get Database name
     * @return String
     */
    public String getDbName() {
        return dbName;
    }

    /**
     * Get username
     * @return String
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get password
     * @return String
     */
    public String getPassword() {
        return password;
    }

    /**
     * Get JDBC URL
     * @return String
     */
    public String getUrl() {
        return url;
    }

    /**
     * Set Dialect
     * @param dialect
     */
    public void setDialect(Dialect dialect) {
        this.dialect = dialect;
    }

    /**
     * Set Hibernate Config
     * @param hibernateConfig
     */
    public void setHibernateConfig(Boolean hibernateConfig) {
        this.hibernateConfig = hibernateConfig;
    }

    /**
     * Set Server Host
     * @param host Database Host name
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * Set Server Port
     * @param port Server port
     */
    public void setPort(String port) {
        this.port = port;
    }

    /**
     * Set Database name
     * @param dbName Database name
     */
    public void setDbName(String dbName) {
        this.dbName = dbName;
        optimizeDbName();
    }

    /**
     * Optimize SQLite Database Name
     */
    public void optimizeDbName() {
        if ("SQLite".equals(this.dialect.getDialect())) {
            if (!this.dbName.contains(".db")
                    | !this.dbName.substring(this.dbName.lastIndexOf(".") + 1).equalsIgnoreCase("db")) {
                this.dbName = this.dbName + ".db";
            }
        }
    }

    /**
     * Set Server username
     * @param username Server username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Set Server password
     * @param password Server password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Set JDBC URL
     * @param url The Connection String (hibernate.hibernate.connection.url
     * property in hibernate.cfg.xml) or JDBC URL
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Set Generated JDBC URL using SQLConfig object
     */
    public void setGeneratedUrl() {
        this.url = generateURL();
    }
}
