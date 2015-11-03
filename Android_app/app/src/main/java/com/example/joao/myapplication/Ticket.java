package com.example.joao.myapplication;

import java.io.Serializable;

/**
 * Created by joao on 23-10-2015.
 */
public class Ticket implements Serializable

{
    public String QrCodeText,date, hourStart, hourEnd;
    public int startStation,endStation, trainNr;
    boolean used;


    public Ticket(String QrCodeText, int startStation, int endStation,String date, String hourStart, String hourEnd, int trainNr,boolean used)

    {
        this.endStation = endStation;
        this.startStation = startStation;
        this.QrCodeText = QrCodeText;
        this.hourEnd = hourEnd;
        this.hourStart = hourStart;
        this.date = date;
        this.trainNr = trainNr;
        this.used = used;

    }
}
