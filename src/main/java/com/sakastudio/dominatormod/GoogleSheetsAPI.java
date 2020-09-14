package com.sakastudio.dominatormod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class GoogleSheetsAPI {
    public static String GetStringValue(){
        try {
            //https://sheets.googleapis.com/v4/spreadsheets/18VilJsHCBHfDW6LDC11MFOVodrY_yWeHkznyqLIZQ5I/values/test!A1:B3?key=AIzaSyD4T5cCM2YttKV-Sw58DXojcanV6E9bu70
            //APIキーはGCPから取ってくる
            //一回ブラウザでアクセスしてスプレッドシートと関連付けする
            URL url = new URL("https://sheets.googleapis.com/v4/spreadsheets/18VilJsHCBHfDW6LDC11MFOVodrY_yWeHkznyqLIZQ5I/values/test!A1:B3?key=AIzaSyD4T5cCM2YttKV-Sw58DXojcanV6E9bu70");
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            http.setRequestMethod("GET");
            http.connect();

            // サーバーからのレスポンスを標準出力へ出す
            BufferedReader reader = new BufferedReader(new InputStreamReader(http.getInputStream()));
            String st = "", line = "";
            while((line = reader.readLine()) != null)
                st += line;

            System.out.println("googlesheet "+st);
            reader.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "null";
    }
}
