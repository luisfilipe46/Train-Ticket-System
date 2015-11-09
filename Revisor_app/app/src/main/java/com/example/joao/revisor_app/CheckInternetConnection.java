package com.example.joao.revisor_app;

import java.net.InetAddress;

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
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com"); //You can replace it with your name

            if (ipAddr.equals("")) {
                return false;
            } else {
                return true;
            }

        } catch (Exception e) {
            return false;
        }
    }
}
