package com.example.joao.myapplication;

import java.net.URL;
import java.net.URLConnection;

/**
 * Created by joao on 09-11-2015.
 */
public class CheckInternetConnection {
    private static CheckInternetConnection ourInstance = new CheckInternetConnection();

    public static CheckInternetConnection getInstance() {
        return ourInstance;
    }

    private CheckInternetConnection() {

    }

    public boolean checkConnection()
    {
        try{
            URL myUrl = new URL("https://testcake3333.herokuapp.com/");
            URLConnection connection = myUrl.openConnection();
            connection.setConnectTimeout(5000);
            connection.connect();
            return true;
        } catch (Exception e) {
            // Handle your exceptions
            return false;
        }
    }
}
