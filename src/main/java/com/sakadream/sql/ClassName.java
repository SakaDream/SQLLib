/*
 * Copyright (c) 2017 Ba Hai Phan
 * Licensed under the MIT Licence - https://raw.githubusercontent.com/SakaDream/SQLLib/master/LICENSE.
 */
package com.sakadream.sql;

/**
 * Class Name Enum
 * @author Phan Ba Hai
 */
public enum ClassName {

    /**
     * Class Name for SQL Server
     */
    SQLServer("com.microsoft.sqlserver.jdbc.SQLServerDriver"),

    /**
     * Class Name for SQLite
     */
    SQLite("org.sqlite.JDBC"),
    
    /**
     * Class Name for MySQL
     */
    MySQL("com.mysql.jdbc.Driver");
    
    private String url;

    private ClassName(String url) {
        this.url = url;
    }

    /**
     *
     * @return String
     */
    public String getUrl() {
        return url;
    }
}
