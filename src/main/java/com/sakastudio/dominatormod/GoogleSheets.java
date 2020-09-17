package com.sakastudio.dominatormod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GoogleSheets {
    public static String[][] GetStringValue(){
        try {
            //GETでスプレッドシートから値を取得
            URL url = new URL("https://sheets.googleapis.com/v4/spreadsheets/18VilJsHCBHfDW6LDC11MFOVodrY_yWeHkznyqLIZQ5I/values/List!A:D?key=AIzaSyB7YN9ZWPzNQntkYlW0YSStVcxoOjWla4U");
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            http.setRequestMethod("GET");
            http.connect();

            // レスポンスを1つのstringに
            BufferedReader reader = new BufferedReader(new InputStreamReader(http.getInputStream(),"UTF-8"));
            String xml = "", line = "";
            Boolean isStart = false;
            while((line = reader.readLine()) != null){
                xml += line;
            }
            reader.close();

            Gson gson = new Gson();
            Sheet sheet = gson.fromJson(xml, Sheet.class);
            return sheet.values;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String[0][0];
    }

    public class Sheet{
        public String range;
        public String majorDimension;
        public String values[][];
    }
}
