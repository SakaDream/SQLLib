/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sakadream.sql;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;

/**
 * Utility for library
 * @author Phan Ba Hai
 */
public class Utilis {

    /**
     * Change config file name
     * @param fileName Config file name
     * @param extension Config file extension
     * @return String
     */
    public static String changeFileName(String fileName, String extension) {
        String[] parsedFileName = fileName.split(Pattern.quote("."));
        if (parsedFileName.length == 2) {
            if (!parsedFileName[1].equals("json")) {
                return parsedFileName[0] + ".json";
            } else {
                return fileName;
            }
        } else {
            return parsedFileName[0] + ".json";
        }
    }
}
