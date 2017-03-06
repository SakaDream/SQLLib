/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sakadream.lib.sql;

/**
 * SQLConfig object
 *
 * @author Phan Ba Hai
 */
public final class SQLConfig {

    private static String message = "This database type is not supported, "
            + "this library supports SQL Server and SQLite. "
            + "Other databases comming soon";

    private DbType dbType;
    private ClassName className;
    private String host;
    private String port;
    private String dbName;
    private String username;
    private String password;
    private String url;

    /**
     * Default values for SQL Server Database: host = localhost, port = 1433,
     * username = sa Note: SQLite JDBC not support password
     *
     * @param dbType Database type
     * @param dbName Database name
     * @param password Database password
     */
    public SQLConfig(DbType dbType, String dbName, String password) {
        this.setDBType(dbType);
        switch (dbType) {
            case SQLServer:
                this.host = "localhost";
                this.port = "1433";
                this.username = "sa";
                this.dbName = dbName;
                this.password = password;
                break;
            case SQLite:
                this.dbName = dbName;
                break;
            default:
                throw new AssertionError(message);
        }
        setClassNameByDBType();
        optimizeDbName();
        this.url = generateURL();
    }

    /**
     * SQLConfig object for SQL Server Database
     *
     * @param host SQL Server host name
     * @param port SQL Server port
     * @param dbName Database name
     * @param username SQL Server username
     * @param password SQL Server password
     */
    public SQLConfig(String host, String port, String dbName, String username, String password) {
        this.dbType = dbType.SQLServer;
        setClassNameByDBType();
        this.host = host;
        this.port = port;
        this.dbName = dbName;
        this.username = username;
        this.password = password;
        this.url = generateURL();
    }

    /**
     * SQLConfig object for Hibernate Configuration XML file
     *
     * @param dbName Database name
     * @param password SQL Server password
     * @param url The Connection String (hibernate.hibernate.connection.url
     * property in hibernate.cfg.xml)
     */
    public SQLConfig(String dbName, String password, String url) {
        setClassNameByURL(url);
        this.dbName = dbName;
        this.password = password;
        this.url = url;
    }

    /**
     *
     * @return String
     */
    public String generateURL() {
        switch (this.dbType) {
            case SQLServer:
                return "jdbc:sqlserver://" + this.host + ":" + this.port + ";databaseName=" + this.dbName;
            case SQLite:
                return "jdbc:sqlite:" + this.dbName;
            default:
                throw new AssertionError(message);
        }
    }

    /**
     *
     * @return DbType
     */
    public DbType getDBType() {
        return dbType;
    }

    /**
     *
     * @return ClassName
     */
    public ClassName getClassName() {
        return className;
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
     * @param dbType The Database Type
     */
    public void setDBType(DbType dbType) {
        this.dbType = dbType;
    }

    /**
     *
     * @param className The Class Name
     */
    public void setDBTypeByClassName(ClassName className) {
        switch (className) {
            case SQLServer:
                this.dbType = DbType.SQLServer;
                break;
            case SQLite:
                this.dbType = DbType.SQLite;
                break;
            default:
                throw new AssertionError(message);
        }
    }

    /**
     *
     * @param className The Class Name
     */
    public void setClassName(String className) {
        if (className.equals(ClassName.SQLServer.getUrl())) {
            this.className = ClassName.SQLServer;
        } else if (className.equals(ClassName.SQLite.getUrl())) {
            this.className = ClassName.SQLite;
        } else {
            this.className = null;
        }
    }

    /**
     *
     */
    public void setClassNameByDBType() {
        switch (dbType) {
            case SQLServer:
                this.className = ClassName.SQLServer;
                break;
            case SQLite:
                this.className = ClassName.SQLite;
                break;
            default:
                throw new AssertionError(message);
        }
    }

    /**
     *
     * @param url The URL String
     */
    public void setClassNameByURL(String url) {
        if (url.indexOf("jdbc:sqlserver") == 0) {
            this.className = ClassName.SQLServer;
        } else if (url.indexOf("jdbc:sqlite") == 0) {
            this.className = ClassName.SQLite;
        }
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
        optimizeDbName();
    }

    /**
     * Optimize The Database Name
     */
    public void optimizeDbName() {
        if (this.dbType == DbType.SQLite) {
            if (!this.dbName.contains(".db") | 
                    !this.dbName.substring(this.dbName.lastIndexOf(".") + 1).equalsIgnoreCase("db")) {
                this.dbName = this.dbName + ".db";
            }
        }
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
     * @param url The Connection String (hibernate.hibernate.connection.url
     * property in hibernate.cfg.xml)
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     *
     */
    public void setGeneratedUrl() {
        this.url = generateURL();
    }
}
