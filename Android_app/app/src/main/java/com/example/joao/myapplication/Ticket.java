package com.example.joao.myapplication;

import java.io.Serializable;

/**
 * Created by joao on 23-10-2015.
 */
public class Ticket implements Serializable

{
    public String QrCodeText,date, hourStart, hourEnd;
    public String startStation,endStation, trainNr;
    public double price;
    boolean used;


    public Ticket(String QrCodeText, String startStation, String endStation,String date, String hourStart, String hourEnd, String trainNr,boolean used,double price)

    {
        this.endStation = endStation;
        this.startStation = startStation;
        this.QrCodeText = QrCodeText;
        this.hourEnd = hourEnd;
        this.hourStart = hourStart;
        this.date = date;
        this.trainNr = trainNr;
        this.used = used;
        this.price = price;

    }
}
