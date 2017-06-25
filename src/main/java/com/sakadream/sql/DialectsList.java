/*
 * Copyright (c) 2017 Ba Hai Phan
 * Licensed under the MIT Licence - https://raw.githubusercontent.com/SakaDream/SQLLib/master/LICENSE.
 */
package com.sakadream.sql;

import java.util.ArrayList;

/**
 * Dialect List
 * @author Phan Ba Hai
 */
public class DialectsList {
    private final ArrayList<Dialect> list = new ArrayList<>();
    private final Dialect mssql = new Dialect("SQL Server", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
    private final Dialect sqlite = new Dialect("SQLite", "org.sqlite.JDBC");
    private final Dialect mysql = new Dialect("MySQL", "com.mysql.jdbc.Driver");
    private final Dialect unknown = new Dialect("Unknown Dialect", "");

    /**
     * DialectsList Constructor
     */
    public DialectsList() {
        list.add(mssql);
        list.add(sqlite);
        list.add(mysql);
        list.add(unknown);
    }
    
    /**
     * Get Dialect by Db Type
     * @param dbType Database type
     * @return Dialect
     */
    public Dialect getDialectByDBType(String dbType) {
        for(Dialect dialect : list) {
            if(dbType.equalsIgnoreCase(dialect.getDialect())) return dialect;
        }
        return unknown;
    }
    
    /**
     * Get Dialect by JDBC Classname
     * @param className JDBC Classname
     * @return Dialect
     */
    public Dialect getDialectByClassName(String className) {
        for(Dialect dialect : list) {
            if(className.equalsIgnoreCase(dialect.getClassName())) return dialect;
        }
        return unknown;
    }
    
    /**
     * Get Dialect by Database URL
     * @param url Database URL
     * @return Dialect
     */
    public Dialect getDialectByUrl(String url) {
        if (url.indexOf("jdbc:sqlserver") == 0) {
            return mssql;
        } else if (url.indexOf("jdbc:sqlite") == 0) {
            return sqlite;
        } else if (url.indexOf("jdbc:mysql") == 0) {
            return mysql;
        } else {
            return unknown;
        }
    }
}
