/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sakadream.lib.sql;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sakadream.lib.sql.security.Encryption;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.nio.channels.FileChannel;

/**
 *
 * @author Phan Ba Hai
 */
public class SQLConfigJson {

    public static String path = "config.json";
    private static Gson gson = new Gson();
    private static Type type = new TypeToken<SQLConfig>() {
    }.getType();

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
            Encryption encryption = new Encryption();
            writer.write(encryption.encrypt(json));
        } else {
            writer.write(json);
        }
        writer.close();
    }

    public static SQLConfig open() throws Exception {
        try {
            File file = new File("config.json");
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String json = "";
            String line = br.readLine();
            while(line != null) {
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
            while(line != null) {
                json += line;
                line = br.readLine();
            }
            String decryptedJson = Encryption.decrypt(json);
            return gson.fromJson(decryptedJson, type);
        }
    }
}
