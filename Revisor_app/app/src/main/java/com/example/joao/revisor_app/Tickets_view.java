package com.example.joao.revisor_app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
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
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Vector;

public class Tickets_view extends AppCompatActivity {
    Vector_tickets vecTickets;
    TextView percentageInfo,numberOfTicketsInfo;
    LinearLayout allTickets;
    private String token;
    private ProgressBar progressBar;
    StationsMap stationsMap = StationsMap.getInstance();
    private RestClient restClient;
    private CheckInternetConnection connection = CheckInternetConnection.getInstance();
    private String url = "https://testcake3333.herokuapp.com/api/tickets_use.json";
    private String publicKey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets_view);

        vecTickets = Vector_tickets.getInstance();

        Intent intent = getIntent();
        Bundle info = intent.getExtras();

        try {
            restClient = RestClient.getInstance();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        token = info.getString("token");
        publicKey = info.getString("public_key");

        percentageInfo = (TextView) findViewById(R.id.percentageInfo);
        numberOfTicketsInfo = (TextView) findViewById(R.id.numberOfTicketsInfo);
        allTickets = (LinearLayout) findViewById(R.id.allTickets);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //showTickets();

    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first


        showTickets();
    }

    private void showTickets() {
        Log.i("TICKETS VIEW", "Show tickets()");

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
        info.putString("public_key",publicKey);

        intent.putExtras(info);

        startActivity(intent);
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

    public void comunicateServer(View view) {

        Vector<Integer> vec_Int = vecTickets.ticketsForValidation;

        String allTickets= "";

        for(int i = 0;i<vec_Int.size();i++)
        {
           if(i==0)
           {
               allTickets = Integer.toString(vec_Int.get(i));
           }
            else
           {
               allTickets = allTickets + "," + vec_Int.get(i);
           }
        }

        Log.i("URL ALL TICKETS", allTickets);

        restClient.setMethod("PUT");
        restClient.setUrl(url);
        restClient.addParam("tickets", allTickets);

        progressBar.setVisibility(View.VISIBLE);
        new comunicateServerTask().execute();


    }


    private class comunicateServerTask extends AsyncTask<Void,Void,String> {

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
                Toast.makeText(Tickets_view.this, "Can't connect to server", Toast.LENGTH_SHORT).show();
            } else
            {


                if(result.equals("200"))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Tickets_view.this);

                    builder.setMessage("Tickets communicated to server")
                            .setTitle("Success");


                    AlertDialog dialog = builder.create();
                    dialog.show();
                    vecTickets.ticketsForValidation.clear();
                }
                else if(result.equals("401"))
                {

                    AlertDialog.Builder builder = new AlertDialog.Builder(Tickets_view.this);

                    builder.setMessage("Can not comunicate")
                            .setTitle("Error");


                    AlertDialog dialog = builder.create();
                    dialog.show();

                    // Toast.makeText(MainActivity.this,"Your email/password are incorrect", Toast.LENGTH_SHORT).show();
                }
                else if(result.equals("fail"))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Tickets_view.this);

                    builder.setMessage("Error connecting to server")
                            .setTitle("Error");


                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Tickets_view.this);

                    builder.setMessage("CODE: " + result)
                            .setTitle("Error");


                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }


        }
    }

}
