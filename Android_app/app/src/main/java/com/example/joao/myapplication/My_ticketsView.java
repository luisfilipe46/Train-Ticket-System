package com.example.joao.myapplication;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.android.Contents;
import com.google.zxing.integration.android.IntentIntegrator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.net.MalformedURLException;
import java.util.Vector;

public class My_ticketsView extends AppCompatActivity implements Serializable {

    AllTickets tickets;
    LinearLayout allTickets;
    String email, pass;
    RestClient restClient;
    Button btnUpdate;
    private String URL = "https://testcake3333.herokuapp.com/api/tickets.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tickets);

        try {
            restClient = RestClient.getInstance();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        Intent intent = getIntent();
        Bundle info = intent.getExtras();
        email = info.getString("email");
        pass = info.getString("password");


        btnUpdate = (Button) findViewById(R.id.updateTickets);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTickets();
            }
        });


        allTickets = (LinearLayout) findViewById(R.id.allTickets);
        //tickets = AllTickets.getInstance();
        if(getTicketsFromFile())
        {
            Log.i("SERIALIZABLE","tickets from file");
        }
        else
        {
            try {
                tickets = new AllTickets();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Log.i("SERIALIZABLE","created New tickets");
            serializeTickets();
        }

        //updateTickets();
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
        allTickets.removeAllViews();

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
            originStation.setText("Station " + ticket.startStation + " " + ticket.hourStart);
            LinearLayout.LayoutParams originStationParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
            originStationParams.weight = 3f;
            originStation.setLayoutParams(originStationParams);

            // "to" text
            TextView toText = new TextView(this);
            toText.setText(" to ");
            toText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);

            //end station
            TextView endStation = new TextView(this);
            endStation.setText("Station " + ticket.endStation + " " + ticket.hourEnd);
            LinearLayout.LayoutParams endStationParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
            endStationParams.weight = 3f;
            endStation.setLayoutParams(endStationParams);

            // button to see qrCode

            Button qrcodeBtn = new Button(this);
            if(!ticket.used) {
                qrcodeBtn.setText("QrCode");
                qrcodeBtn.setOnClickListener(new QrCodeOnClickListener(ticket.QrCodeText));
            }
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

    public void updateTickets()
    {
        restClient.setUrl(URL);
        restClient.setMethod("GET");
        restClient.addHeader("email", email);
        restClient.addHeader("password",pass);

        btnUpdate.setEnabled(false);
        new updateTicketsTask().execute();


    }

    public class QrCodeOnClickListener implements View.OnClickListener
    {

        String qrCode;

        public QrCodeOnClickListener(String qrCode) {
            this.qrCode = qrCode;

        }

        @Override
        public void onClick(View v)
        {
            //new IntentIntegrator(My_ticketsView.this).initiateScan();
            //Encode with a QR Code image

            int qrCodeDimention = 256;

            QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(qrCode, null,
                    Contents.Type.TEXT, BarcodeFormat.QR_CODE.toString(), qrCodeDimention);

            try {
                Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();


                Dialog builder = new Dialog(My_ticketsView.this);
                builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
                builder.getWindow().setBackgroundDrawable(
                        new ColorDrawable(android.graphics.Color.TRANSPARENT));
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        //nothing;
                    }
                });

                ImageView imageView = new ImageView(My_ticketsView.this);
                imageView.setImageBitmap(bitmap);
                builder.addContentView(imageView, new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
                builder.show();


            } catch (WriterException e) {
                e.printStackTrace();
            }
        }

    };

    private class updateTicketsTask extends AsyncTask<Void,Void,String> {
        /** The system calls this to perform work in a worker thread and
         * delivers it the parameters given to AsyncTask.execute() */
        @Override
        protected String doInBackground(Void... params) {

            try {
                return restClient.execute();
            } catch (IOException | JSONException | InterruptedException e) {
                e.printStackTrace();
                return "fail";

            }
        }



        /** The system calls this to perform work in the UI thread and delivers
         * the result from doInBackground() */
        protected void onPostExecute(String result) {
            btnUpdate.setEnabled(true);

            if(result.equals("200"))
            {
                Toast.makeText(My_ticketsView.this, "Sucesso", Toast.LENGTH_SHORT).show();
                String retorno = restClient.getReturn();
                Log.i("TICKETS", retorno);

                JSONArray arr = null;

                try {
                    JSONObject json = new JSONObject(retorno);
                    arr = json.getJSONArray("tickets");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(arr == null)
                {
                    Log.e("TICKETS", "can't form json from server response");
                }
                else
                {
                    try {
                        tickets.updateTickets(arr);
                        serializeTickets();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    showTickets();
                }
            }
            else if(result.equals("400"))
            {

                Toast.makeText(My_ticketsView.this, "Your email/password are incorrect", Toast.LENGTH_SHORT).show();

                // Toast.makeText(MainActivity.this,"Your email/password are incorrect", Toast.LENGTH_SHORT).show();
            }
            else if(result.equals("401"))
            {

            }
            else if(result.equals("fail"))
            {

            }
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
