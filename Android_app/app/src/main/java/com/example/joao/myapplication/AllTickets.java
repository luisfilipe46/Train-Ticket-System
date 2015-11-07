package com.example.joao.myapplication;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Vector;

/**
 * Created by joao on 03-11-2015.
 */
public class AllTickets implements Serializable {

    //private static AllTickets allTickets = new AllTickets( );


    public Vector<Ticket> tickets;


    public AllTickets() throws MalformedURLException {



        tickets = new Vector<>();
        //updateTicketsFromServer();
        /*
        for(int i = 0; i<20; i++)
        {
            Ticket t;
            if(i == 3 || i==5 || i==1) {
                t = new Ticket("One Not used", i, i + 1, "2015-26-6", i + ":30", (i + 1) + ":30", 2, false);
            }
            else
            {
                t = new Ticket("One Used", i, i + 1, "2015-26-6", i + ":30", (i + 1) + ":30", 3, true);
            }


            tickets.add(t);
        }*/


    }


    public void updateTickets(JSONArray arr) throws JSONException {
        tickets.clear();
        for(int i = 0; i<arr.length(); i++)
        {
            Ticket t;
            String origin = arr.getJSONObject(i).getString("origin_station");
            String destiny = arr.getJSONObject(i).getString("destiny_station");
            String qrCode = arr.getJSONObject(i).getString("qr_code");

            String hourStart = arr.getJSONObject(i).getString("departure_time");
            String date = hourStart.substring(0,10);
            hourStart = hourStart.substring(11,19);

            String hourEnd = arr.getJSONObject(i).getString("arrival_time");
            hourEnd = hourEnd.substring(11,19);

            boolean used = arr.getJSONObject(i).getBoolean("used");
            double price = arr.getJSONObject(i).getDouble("price");


            t = new Ticket(qrCode,origin,destiny,date,hourStart,hourEnd,"1",used,price);

            tickets.add(t);
        }



    }
}
