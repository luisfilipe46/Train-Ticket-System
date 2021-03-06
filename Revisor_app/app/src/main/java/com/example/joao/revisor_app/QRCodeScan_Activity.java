package com.example.joao.revisor_app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
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
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

public class QRCodeScan_Activity extends AppCompatActivity {

    private Vector_tickets vecTickets;
    private RestClient restClient;
    private String updateTicketsURL = "https://testcake3333.herokuapp.com/api/tickets";
    private String token;
    private ProgressBar progressBar;
    private CheckInternetConnection connection = CheckInternetConnection.getInstance();
    private StationsMap stationsMap = StationsMap.getInstance();
    private String publicKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Bundle info = intent.getExtras();


        token = info.getString("token");
        publicKey = info.getString("public_key");

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

    private static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    private void validateTicket(String qrCode) {
        String[] parts = qrCode.split(" ");

        String data = new String();
        String signature = new String();

        for (int i= 0; i < 8; i++)
            data += parts[i] + " ";

        for (int i = 8; i < parts.length; i++)
            signature = parts[i] + " ";

        data = data.substring(0, data.length()-1);
        signature = signature.substring(0, signature.length()-1);

        byte [] dataByteArray = data.getBytes();
        byte [] signatureByteArray = QRCodeScan_Activity.hexStringToByteArray(signature);


        Signature sig = null;
        boolean verifiesSignature = false;
        String publicK = "MEowDQYJKoZIhvcNAQEBBQADOQAwNgIvAKp9Su+TSBa8PMpsoSMzq9ohkykfe/bm GU9MGo+h0p8bHSooLN5NUYyD+ShpGZ0CAwEAAQ==";
        //String publicK = publicKey;
        Log.i("PUBLIC KEY", publicKey);
        Log.i("PUBLIC KEY", token);

        try {

            byte[] keyBytes = Base64.decode(publicK.getBytes("utf-8"), Base64.DEFAULT);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = null;
            keyFactory = KeyFactory.getInstance("RSA");


            PublicKey pubKey = keyFactory.generatePublic(spec);
            sig = Signature.getInstance("SHA1withRSA");
            sig.initVerify(pubKey);
            sig.update(dataByteArray);
            verifiesSignature = sig.verify(signatureByteArray);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        int i = 0;
        final Ticket t = vecTickets.existsTicket(qrCode);

        if(verifiesSignature == false)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(QRCodeScan_Activity.this);

            builder.setMessage("Ticket not signed by Server")
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
        else if(t==null)
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

            t.used = true;

            AlertDialog.Builder builder = new AlertDialog.Builder(this);




            builder.setPositiveButton("validate", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    restClient.setMethod("PUT");
                    restClient.setUrl(updateTicketsURL + "/" + t.id + ".json");
                    restClient.addHeader("token", token);

                    new validateTicketTask(t).execute();

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

        Ticket t;
        public validateTicketTask(Ticket t) {
            this.t = t;
        }

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

            if(!result.equals("200"))
            {
                vecTickets.ticketsForValidation.add(t.id);
            }

            progressBar.setVisibility(View.GONE);


            if (result.equals("No Connection"))
            {
                Toast.makeText(QRCodeScan_Activity.this, "Not validated on server, in the end of travel comunicate", Toast.LENGTH_SHORT).show();

            } else
            {
                if(result.equals("200"))
                {
                    Toast.makeText(QRCodeScan_Activity.this, "Ticket Validated on Server", Toast.LENGTH_SHORT).show();

                }
                else if(result.equals("fail"))
                {
                    Toast.makeText(QRCodeScan_Activity.this, "Not validated on server, in the end of travel comunicate", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(QRCodeScan_Activity.this, "Not validated on server, in the end of travel comunicate", Toast.LENGTH_SHORT).show();
                }
            }

            finish();
        }
    }
}
