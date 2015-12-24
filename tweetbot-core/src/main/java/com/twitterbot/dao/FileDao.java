package com.twitterbot.dao;

import java.io.*;

/**
 * Created by fckey on 15/05/24.
 */
public class FileDao {

    public FileDao(){

    }

   public static void writeMsg(String fileName, String msg){
       File file = new File(fileName);
       try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)))) {
           pw.println(msg);
       } catch (IOException e) {
           e.printStackTrace();
       }
   }

}
