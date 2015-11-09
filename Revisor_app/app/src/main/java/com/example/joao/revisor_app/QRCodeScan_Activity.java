package com.example.joao.revisor_app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;

public class QRCodeScan_Activity extends AppCompatActivity {

    private Vector_tickets vecTickets;
    private RestClient restClient;
    private String updateTicketsURL = "https://testcake3333.herokuapp.com/api/tickets";
    private String token;
    private ProgressBar progressBar;
    private CheckInternetConnection connection = CheckInternetConnection.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Bundle info = intent.getExtras();


        token = info.getString("token");

        vecTickets = Vector_tickets.getInstance();
        try {
            restClient = RestClient.getInstance();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_qrcode_scan_);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        IntentIntegrator scanIntegrator = new IntentIntegrator(this);
        scanIntegrator.initiateScan();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null && scanResult.getContents() != null) {
            Log.i("SCAN RESULT", scanResult.getContents().toString());
            validateTicket(scanResult.getContents().toString());

        }
        else {
            this.finish();
        }


    }

    private void validateTicket(String qrCode) {
        final Ticket t = vecTickets.existsTicket(qrCode);

        if(t==null)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(QRCodeScan_Activity.this);

            builder.setMessage("Ticket does not exist")
                    .setTitle("Error");

            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });

            AlertDialog dialog = builder.create();

            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

        }
        else if(t.used)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(QRCodeScan_Activity.this);

            builder.setMessage("Ticket already used")
                    .setTitle("Error");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });

            AlertDialog dialog = builder.create();

            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

        }
        else {
            Log.i("TICKET", "Ticket OK");

            AlertDialog.Builder builder = new AlertDialog.Builder(this);




            builder.setPositiveButton("validate", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    restClient.setMethod("PUT");
                    restClient.setUrl(updateTicketsURL + "/" + t.id + ".json");
                    restClient.addHeader("token", token);

                    new validateTicketTask().execute();

                    progressBar.setVisibility(View.VISIBLE);


                }
            });
            builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    finish();
                }
            });

            LinearLayout LL = generateTicketView(t);


            builder.setView(LL);

            AlertDialog dialog = builder.create();
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();




        }
    }

    private LinearLayout generateTicketView(Ticket ticket) {



        LinearLayout LL = new LinearLayout(this);
        LL.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams LLParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LLParams.setMargins(15,15,15,0);
        LL.setLayoutParams(LLParams);


        LinearLayout date = new LinearLayout(this);
        date.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams dateParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0);
        dateParams.setMargins(15,15,15,0);
        dateParams.weight = 5f;
        date.setLayoutParams(dateParams);


        TextView dateText = new TextView(this);
        dateText.setText(ticket.date);
        date.addView(dateText);


        LinearLayout ticketInfo = new LinearLayout(this);
        ticketInfo.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams ticketInfoParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0);
        ticketInfoParams.setMargins(15,0,15,0);
        ticketInfoParams.weight = 9f;
        ticketInfo.setLayoutParams(ticketInfoParams);
        //ticketInfo.setBackgroundColor(Color.GREEN);


        //origin station
        TextView originStation = new TextView(this);
        originStation.setText("Station " + ticket.startStation + " " + ticket.hourStart);
        LinearLayout.LayoutParams originStationParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        originStationParams.weight = 3f;
        originStation.setLayoutParams(originStationParams);

        // "to" text
        TextView toText = new TextView(this);
        toText.setText(" to ");
        toText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);

        //end station
        TextView endStation = new TextView(this);
        endStation.setText("Station " + ticket.endStation + " " + ticket.hourEnd);
        LinearLayout.LayoutParams endStationParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        endStationParams.weight = 3f;
        endStation.setLayoutParams(endStationParams);

        ticketInfo.addView(originStation);
        ticketInfo.addView(toText);
        ticketInfo.addView(endStation);

        // Black line on end
        View blackLine = new View(this);
        LinearLayout.LayoutParams blackParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,5);
        blackLine.setLayoutParams(blackParams);
        blackLine.setBackgroundColor(Color.BLACK);



        LL.addView(date);
        LL.addView(ticketInfo);
        LL.addView(blackLine);
        return LL;
    }

    // ------------------------------------------------------------------- REST TASKS ---------------------------------------------------------------------------------
    //
    //
    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------

    private class validateTicketTask extends AsyncTask<Void,Void,String> {

        @Override
        protected String doInBackground(Void... params) {

            if (connection.checkConnection()) {
                try {
                    return restClient.execute();
                } catch (IOException | JSONException | InterruptedException e) {
                    e.printStackTrace();
                    return "fail";

                }
            } else {
                return "No Connection";
            }
        }


        protected void onPostExecute(String result) {

            progressBar.setVisibility(View.GONE);

            if (result.equals("No Connection"))
            {
                Toast.makeText(QRCodeScan_Activity.this, "No internet Connection", Toast.LENGTH_SHORT).show();
            } else
            {
                if(result.equals("200"))
                {
                    Toast.makeText(QRCodeScan_Activity.this, "Ticket Validated", Toast.LENGTH_SHORT).show();
                }
                else if(result.equals("fail"))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(QRCodeScan_Activity.this);

                    builder.setMessage("Error connecting to server")
                            .setTitle("Error");


                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else {
                    Toast.makeText(QRCodeScan_Activity.this, "Code: " + result + " (fail)", Toast.LENGTH_SHORT).show();
                }
            }

            finish();
        }
    }
}
