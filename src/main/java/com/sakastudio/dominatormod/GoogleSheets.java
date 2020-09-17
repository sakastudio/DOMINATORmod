package com.sakastudio.dominatormod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class GoogleSheets {
    public static String[][] GetStringValue(String sheetID,String sheetName){
        try {
            //GETでスプレッドシートから値を取得
            URL url = new URL("https://docs.google.com/spreadsheets/d/" + sheetID);
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            http.setRequestMethod("GET");
            http.connect();

            // レスポンスを1つのstringに
            BufferedReader reader = new BufferedReader(new InputStreamReader(http.getInputStream(),"UTF-8"));
            String xml = "", line = "";
            Boolean isStart = false;
            while((line = reader.readLine()) != null){
                if(isStart && line.indexOf("\"") != -1){
                    break;
                }
                if(isStart){
                    xml += line + "'";
                }
                if(!isStart && line.indexOf("content=\"" + sheetName) != -1){
                    isStart = true;
                }
            }
            System.out.println(xml.substring(1));
            reader.close();

            //配列に格納
            String[] lineArray = xml.substring(1).split("'");
            String[][] resultSheet = new String[lineArray.length][0];
            for (int i = 0;i<lineArray.length;i++) {
                resultSheet[i] = lineArray[i].split(",");
            }

            return resultSheet;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
