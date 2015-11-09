package com.example.joao.revisor_app;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.transform.Source;

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

    public boolean checkConnection() {
        try {
            URLConnection con = new URL("https://testcake3333.herokuapp.com").openConnection();
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            con.connect();
            return true;
        } catch (IOException e) {
            return false;
        }


    }

}
