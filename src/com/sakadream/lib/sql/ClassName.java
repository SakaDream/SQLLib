/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sakadream.lib.sql;

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
    SQLite("org.sqlite.JDBC");
    
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
