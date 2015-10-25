package com.example.joao.myapplication;

/**
 * Created by joao on 23-10-2015.
 */
public class Ticket

{
    private String QrCodeText;
    private int startStation,endStation;

    public Ticket(String QrCodeText, int startStation, int endStation)

    {
        this.endStation = endStation;
        this.startStation = startStation;
        this.QrCodeText = QrCodeText;

    }
}
