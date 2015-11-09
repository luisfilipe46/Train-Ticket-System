package com.example.joao.revisor_app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Vector;

public class Tickets_view extends AppCompatActivity {
    Vector_tickets vecTickets;
    TextView percentageInfo,numberOfTicketsInfo;
    LinearLayout allTickets;
    private String token;
    private ProgressBar progressBar;
    StationsMap stationsMap = StationsMap.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets_view);

        vecTickets = Vector_tickets.getInstance();

        Intent intent = getIntent();
        Bundle info = intent.getExtras();


        token = info.getString("token");

        percentageInfo = (TextView) findViewById(R.id.percentageInfo);
        numberOfTicketsInfo = (TextView) findViewById(R.id.numberOfTicketsInfo);
        allTickets = (LinearLayout) findViewById(R.id.allTickets);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        showTickets();

    }

    private void showTickets() {

        int usedTickets = 0;

        allTickets = (LinearLayout) findViewById(R.id.allTickets);
        percentageInfo = (TextView) findViewById(R.id.percentageInfo);
        numberOfTicketsInfo = (TextView) findViewById(R.id.numberOfTicketsInfo);

        allTickets.removeAllViews();

        for(int i =0;i< vecTickets.tickets.size();i++)
        {
            Ticket ticket = vecTickets.tickets.get(i);

            LinearLayout LL = new LinearLayout(this);
            LL.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams LLParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
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
            originStation.setText(stationsMap.getStationRealName(ticket.startStation) + " " + ticket.hourStart.substring(0,5));
            LinearLayout.LayoutParams originStationParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            originStationParams.weight = 3f;
            originStation.setLayoutParams(originStationParams);


            // "to" text
            TextView toText = new TextView(this);
            toText.setText(" to ");

            toText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);

            //end station
            TextView endStation = new TextView(this);
            endStation.setText(stationsMap.getStationRealName(ticket.endStation) + " " + ticket.hourEnd.substring(0,5));
            LinearLayout.LayoutParams endStationParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            endStationParams.weight = 3f;
            endStation.setLayoutParams(endStationParams);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                endStation.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            }
            Button qrcodeBtn = new Button(this);
            if(!ticket.used) {
                qrcodeBtn.setText("Valid");
                qrcodeBtn.setBackgroundColor(Color.GREEN);
                qrcodeBtn.setClickable(false);

            }
            else
            {
                usedTickets++;
                qrcodeBtn.setText("Used");
                qrcodeBtn.setBackgroundColor(Color.GRAY);
                qrcodeBtn.setClickable(false);
            }
            LinearLayout.LayoutParams qrcodeBtnParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

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

        float percentage = (float)usedTickets/vecTickets.tickets.size() * 100;
        String stringPecentage = usedTickets + " of " + vecTickets.tickets.size() + " (" + String.format("%.2f", percentage) + "%)";
        percentageInfo.setText(stringPecentage);
        numberOfTicketsInfo.setText(Integer.toString(vecTickets.tickets.size()));

    }

    public void lauchQrScan(View view) {
        Intent intent = new Intent(getBaseContext(), QRCodeScan_Activity.class);
        Bundle info = new Bundle();
        info.putString("token", token);

        intent.putExtras(info);

        startActivity(intent);
    }





    private class MarkUsedOnClickListener implements View.OnClickListener {

        public MarkUsedOnClickListener(String qrCodeText) {

        }

        @Override
        public void onClick(View v) {

        }
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
