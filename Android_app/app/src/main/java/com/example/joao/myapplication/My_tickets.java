package com.example.joao.myapplication;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.Vector;

public class My_tickets extends AppCompatActivity implements Serializable {

    AllTickets tickets;
    LinearLayout allTickets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tickets);

        allTickets = (LinearLayout) findViewById(R.id.allTickets);
        //tickets = AllTickets.getInstance();
        if(getTicketsFromFile())
        {
            Log.i("SERIALIZABLE","tickets from file");
        }
        else
        {
            tickets = new AllTickets();
            Log.i("SERIALIZABLE","created New tickets");
            serializeTickets();
        }

        updateTickets();
        showTickets();

    }

    private boolean getTicketsFromFile()
    {
        FileInputStream fileIn = null;
        try {
            fileIn = openFileInput("tickets.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            tickets = (AllTickets) in.readObject();
            in.close();
            fileIn.close();
            return true;
        } catch (ClassNotFoundException | IOException e) {
            //e.printStackTrace();
            return false;
        }

    }

    private void serializeTickets()
    {

        try {
            FileOutputStream fileOut =  openFileOutput("tickets.ser", Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(tickets);
            out.close();
            fileOut.close();
            Log.i("SERIALIZABLE", "tickets saved");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void showTickets() {
        for(int i =0;i< tickets.tickets.size();i++)
        {
            Ticket ticket = tickets.tickets.get(i);

            LinearLayout LL = new LinearLayout(this);
            LL.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams LLParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 150);
            LL.setLayoutParams(LLParams);


            LinearLayout date = new LinearLayout(this);
            date.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams dateParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0);
            dateParams.weight = 5f;
            date.setLayoutParams(dateParams);


            TextView dateText = new TextView(this);
            dateText.setText(ticket.date);
            date.addView(dateText);


            LinearLayout ticketInfo = new LinearLayout(this);
            ticketInfo.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams ticketInfoParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0);
            ticketInfoParams.weight = 9f;
            ticketInfo.setLayoutParams(ticketInfoParams);
            //ticketInfo.setBackgroundColor(Color.GREEN);


            //origin station
            TextView originStation = new TextView(this);
            originStation.setText("Station " + Integer.toString(ticket.startStation) + " " + ticket.hourStart);
            LinearLayout.LayoutParams originStationParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
            originStationParams.weight = 3f;
            originStation.setLayoutParams(originStationParams);

            // "to" text
            TextView toText = new TextView(this);
            toText.setText(" to ");
            toText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);

            //end station
            TextView endStation = new TextView(this);
            endStation.setText("Station " + Integer.toString(ticket.endStation) + " " + ticket.hourEnd);
            LinearLayout.LayoutParams endStationParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
            endStationParams.weight = 3f;
            endStation.setLayoutParams(endStationParams);

            // button to see qrCode

            Button qrcodeBtn = new Button(this);
            if(!ticket.used)
            qrcodeBtn.setText("QrCode");
            else
            {
                qrcodeBtn.setText("Used");
                qrcodeBtn.setBackgroundColor(Color.GRAY);
                qrcodeBtn.setClickable(false);
            }
            LinearLayout.LayoutParams qrcodeBtnParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
            qrcodeBtnParams.weight = 1f;
            qrcodeBtn.setLayoutParams(qrcodeBtnParams);


            ticketInfo.addView(originStation);
            ticketInfo.addView(toText);
            ticketInfo.addView(endStation);
            ticketInfo.addView(qrcodeBtn);



            // Black line on end
            View blackLine = new View(this);
            LinearLayout.LayoutParams blackParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,5);
            blackLine.setLayoutParams(blackParams);
            blackLine.setBackgroundColor(Color.BLACK);



            LL.addView(date);
            LL.addView(ticketInfo);
            LL.addView(blackLine);

            allTickets.addView(LL);
        }

    }

    private void updateTickets() {


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // this takes the user 'back', as if they pressed the left-facing triangle icon on the main android toolbar.
                // if this doesn't work as desired, another possibility is to call `finish()` here.
                this.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
