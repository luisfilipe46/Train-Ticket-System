package com.example.joao.revisor_app;

/**
 * Created by joao on 07-11-2015.
 */
public class Ticket

{
    public String QrCodeText,date, hourStart, hourEnd;
    public String startStation,endStation, trainNr,id;
    public double price;
    boolean used;


    public Ticket(String QrCodeText, String startStation, String endStation,String date, String hourStart, String hourEnd, String trainNr,boolean used,double price,String id)

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
        this.id = id;

    }
}
