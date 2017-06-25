/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sakadream.sql;

/**
 * Test File Name in com.sakadream.sql.Utilis
 * @author Phan Ba Hai
 */
public class TestFileName {

    /**
     * Main Program
     * @param args Arguments
     */
    public static void main(String[] args) {
        String test = "config.json";
        String result = Utilis.changeFileName(test, "json");
        System.out.println("Test 1:");
        System.out.println("Before: " + test);
        System.out.println("After: " + result);
        System.out.println("-------------------------------------------------");
        
        test = "config.txt";
        result = Utilis.changeFileName(test, "json");
        System.out.println("Test 2:");
        System.out.println("Before: " + test);
        System.out.println("After: " + result);
        System.out.println("-------------------------------------------------");
        
        test = "config";
        result = Utilis.changeFileName(test, "json");
        System.out.println("Test 3:");
        System.out.println("Before: " + test);
        System.out.println("After: " + result);
        System.out.println("-------------------------------------------------");
    }
}
