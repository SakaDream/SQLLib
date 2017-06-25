/*
 * Copyright (c) 2017 Ba Hai Phan
 * Licensed under the MIT Licence - https://raw.githubusercontent.com/SakaDream/SQLLib/master/LICENSE.
 */
package com.sakadream.sql;

/**
 * Dialect Object
 * @author Phan Ba Hai
 */
public class Dialect {
    private String dialect;
    private String className;
    
    /**
     * Dialect Constructor with no argument
     */
    public Dialect() {
    }

    /**
     * Dialect Constructor
     * @param dialect Dialect name
     * @param className JDBC ClassName
     */
    public Dialect(String dialect, String className) {
        this.dialect = dialect;
        this.className = className;
    }
    
    /**
     * Get dialect name
     * @return String
     */
    public String getDialect() {
        return this.dialect;
    }

    /**
     * Get JDBC classname
     * @return String
     */
    public String getClassName() {
        return this.className;
    }
}
