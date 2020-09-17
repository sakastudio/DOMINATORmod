package com.sakastudio.dominatormod;

public class CustomObjects {
    public static CustomObjects Instance = new CustomObjects();
    public String[][] killLogList;

    public String GetCustomKilllog(String name){
        for (String[] item:killLogList) {
            if (item[0].equals(name)){
                return item[2];
            }
        }
        return "";
    }
    public String GetCustomBanlog(String name){
        for (String[] item:killLogList) {
            if (item[0].equals(name)){
                return item[3];
            }
        }
        return "";
    }
    public int GetBaseCrimeCoefficient(String name){
        for (String[] item:killLogList) {
            if (item[0].equals(name)){
                return Integer.parseInt(item[1]);
            }
        }
        return 0;
    }
}
