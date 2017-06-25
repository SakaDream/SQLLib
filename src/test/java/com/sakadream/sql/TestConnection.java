/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sakadream.sql;

import java.io.IOException;

/**
 * Test Connection
 * @author Phan Ba Hai
 */
public class TestConnection {

    /**
     * Check Connection
     * @return Boolean
     */
    public boolean Connect() {
        try {
            ClassLoader classloader = getClass().getClassLoader();
            SQLConfig config = SQLConfigJson.open(classloader.getResourceAsStream("tests.json"));
            SQL.createConnection(config);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Main Program
     * @param args Arguments
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        TestConnection tc = new TestConnection();
        if (tc.run(args)) {
            System.out.println("Test Connection Passed!");
        } else {
            System.err.println("Test Connection Failed!");
        }
    }

    /**
     * Run method
     * @param args Arguments
     * @return Boolean
     * @throws IOException
     */
    public boolean run(String[] args) throws IOException {
        return (Connect()) ? true : false;
    }
}
