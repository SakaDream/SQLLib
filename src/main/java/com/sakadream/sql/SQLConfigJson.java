/*
 * Copyright (c) 2017 Ba Hai Phan
 * Licensed under the MIT Licence - https://raw.githubusercontent.com/SakaDream/SQLLib/master/LICENSE.
 */
package com.sakadream.sql;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sakadream.security.Security;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.nio.channels.FileChannel;
import org.hibernate.cfg.Configuration;

/**
 * JSON Utilities for SQLConfig object
 * @author Phan Ba Hai
 */
public class SQLConfigJson {

    /**
     *
     */
    public static String path = "config.json";
    private static Gson gson = new Gson();
    private static Type type = new TypeToken<SQLConfig>() {
    }.getType();

    /**
     * Save SQLConfig to config.json
     * @param sqlConfig SQLConfig object
     * @param encrypt Are you want to encrypt config.json?
     * @throws Exception
     */
    public static void save(SQLConfig sqlConfig, Boolean encrypt) throws Exception {
        File file = new File(path);
        if (!file.exists()) {
            file.createNewFile();
        } else {
            FileChannel outChan = new FileOutputStream(file, true).getChannel();
            outChan.truncate(0);
            outChan.close();
        }
        FileWriter writer = new FileWriter(file);
        String json = gson.toJson(sqlConfig, type);
        if (encrypt) {
            Security security = new Security();
            writer.write(security.encrypt(json));
        } else {
            writer.write(json);
        }
        writer.close();
    }

    /**
     * Open config.json
     * @return SQLConfig
     * @throws Exception
     */
    public static SQLConfig open() throws Exception {
        try {
            File file = new File("config.json");
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String json = "";
            String line = br.readLine();
            while (line != null) {
                json += line;
                line = br.readLine();
            }
            return gson.fromJson(json, type);
        } catch (Exception e) {
            File file = new File("config.json");
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String json = "";
            String line = br.readLine();
            while (line != null) {
                json += line;
                line = br.readLine();
            }
            String decryptedJson = Security.decrypt(json);
            return gson.fromJson(decryptedJson, type);
        }
    }

    /**
     * Get Hibernate Configuration Properties using Hibernate API
     * @param pkgLocation hibernate.cfg.xml's folder location
     * @return
     */
    public static SQLConfig getHibernateCfg(String pkgLocation) {
        Configuration config = new Configuration();
        config.configure(pkgLocation + "hibernate.cfg.xml");
        String username = config.getProperty("hibernate.connection.username");
        String password = config.getProperty("hibernate.connection.password");
        String url = config.getProperty("hibernate.connection.url");

        return new SQLConfig(username, password, url);
    }

    /**
     * Get Hibernate Configuration Properties using Hibernate API 
     * (Default location of hibernate.cfg.xml is the root of java package - src/java)
     * @return SQLConfig
     */
    public static SQLConfig getHibernateCfg() {
        return getHibernateCfg("");
    }

    /**
     * Set hibernate.cfg.xml properties from SQLConfig object
     * @param config SQLConfig object
     * @param pkgLocation hibernate.cfg.xml's folder location
     */
    public static void setHibernateCfg(SQLConfig config, String pkgLocation) {
        String url = "";
        if (config.getUrl() == null) {
            url = config.generateURL();
        } else {
            url = config.getUrl();
        }

        Configuration HBMconfig = new Configuration();
        HBMconfig.configure(pkgLocation + "hibernate.cfg.xml");
        HBMconfig.setProperty("hibernate.connection.username", config.getUsername());
        HBMconfig.setProperty("hibernate.connection.password", config.getPassword());
        HBMconfig.setProperty("hibernate.connection.url", url);
    }

    /**
     * Set hibernate.cfg.xml properties from SQLConfig object 
     * (Default location of hibernate.cfg.xml is the root of java package - src/java)
     * @param config SQLConfig object
     */
    public static void setHibernateCfg(SQLConfig config) {
        setHibernateCfg(config, "");
    }
    
    /**
     * Save from hibernate.cfg.xml to config.json
     * @param pkgLocation hibernate.cfg.xml's folder location
     * @param isEncrypt Are you want to encrypt config.json?
     * @throws Exception
     */
    public static void xmlToJson(String pkgLocation, Boolean isEncrypt) throws Exception {
        SQLConfig config = getHibernateCfg(pkgLocation);
        SQLConfigJson.save(config, isEncrypt);
    }
    
    /**
     * Save from hibernate.cfg.xml to config.json 
     * (Default location of hibernate.cfg.xml is the root of java package - src/java)
     * @param isEncrypt Are you want to encrypt config.json?
     * @throws Exception
     */
    public static void xmlToJson(Boolean isEncrypt) throws Exception {
        SQLConfig config = getHibernateCfg();
        SQLConfigJson.save(config, isEncrypt);
    }
    
    /**
     * Save from config.json to hibernate.cfg.xml
     * @param pkgLocation hibernate.cfg.xml's folder location
     * @throws Exception
     */
    public static void jsonToXml(String pkgLocation) throws Exception {
        SQLConfig config = SQLConfigJson.open();
        setHibernateCfg(config, pkgLocation);
    }
    
    /**
     * Save from config.json to hibernate.cfg.xml 
     * (Default location of hibernate.cfg.xml is the root of java package - src/java)
     * @throws Exception
     */
    public static void jsonToXml() throws Exception {
        SQLConfig config = SQLConfigJson.open();
        setHibernateCfg(config);
    }
}
