package com.example.joao.revisor_app;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.SimpleTimeZone;
import java.util.Vector;

public class MainMenu extends AppCompatActivity {

    Spinner spinnerStart,spinnerEnd;
    String token,email,pass;
    HashMap<String, String> stationsMap = new HashMap<String, String>();
    Vector<Ticket> tickets;
    Button btnUpdateTickets;
    private RestClient restClient;
    private TextView date,departureTime;
    private String updateTicketsURL = "https://testcake3333.herokuapp.com/api/tickets";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);


        initializeStationsMap();

        Intent intent = getIntent();
        Bundle info = intent.getExtras();

        email = info.getString("email");
        pass = info.getString("password");
        token = info.getString("token");

        tickets = new Vector<>();

        spinnerStart = (Spinner) findViewById(R.id.spinnerStart);
        spinnerEnd = (Spinner) findViewById(R.id.spinnerEnd);
        btnUpdateTickets = (Button) findViewById(R.id.btnUpdateTickets);
        date = (TextView) findViewById(R.id.date);
        departureTime = (TextView) findViewById(R.id.departureTime);

        try {
            restClient = RestClient.getInstance();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.stations_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinnerStart.setAdapter(adapter);
        spinnerEnd.setAdapter(adapter);
    }

    private void initializeStationsMap() {
        stationsMap.put("11", "Station 11");
        stationsMap.put("12", "Station 12");
        stationsMap.put("01", "Station 01");
        stationsMap.put("32", "Station 32");
        stationsMap.put("31", "Station 31");
        stationsMap.put("21", "Station 21");


    }

    public static Object getKeyFromValue(Map hm, String value) {
        for (Object o : hm.keySet()) {
            if (hm.get(o).equals(value)) {
                return o;
            }
        }
        return null;
    }

    public void updateTickets(View view) {

        if(getFields()){
            restClient.setMethod("GET");
            String origin = (String) getKeyFromValue(stationsMap,spinnerStart.getSelectedItem().toString());
            String destiny= (String) getKeyFromValue(stationsMap,spinnerEnd.getSelectedItem().toString());
            String dateText = date.getText().toString();
            String departureTimeText = this.departureTime.getText().toString();
            restClient.setUrl(updateTicketsURL + "/" + origin +"/" + destiny + "/"+ dateText + "/"+ departureTimeText + ".json");
            restClient.addHeader("token", token);

            new updateTicketsTask().execute();

        }


    }

    private boolean getFields() {
        {
            Boolean valid = true;
            String dateString;
            String departureString;
            dateString = date.getText().toString();
            departureString =  departureTime.getText().toString();



            if(TextUtils.isEmpty(date.getText().toString()))
            {
                date.setError(getString(R.string.error_field_required));
                valid = false;
            }
            else if(TextUtils.isEmpty(departureTime.getText().toString()))
            {
                departureTime.setError("Field Required");
                valid = false;
            }

            else {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                dateFormat.setLenient(false);
                try {

                    if(dateFormat.parse(dateString) == null)
                    {
                        valid = false;
                        date.setError("Invalid date");
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                    valid = false;
                    date.setError("Invalid date");

                }

                String timePattern = "\\d{2}:\\d{2}:\\d{2}";
                if(! departureString.matches(timePattern))
                {
                    departureTime.setError("Invalid Time");
                    valid = false;
                }
            }


            return valid;
        }
    }

    private class updateTicketsTask extends AsyncTask<Void,Void,String> {

        @Override
        protected String doInBackground(Void... params) {

            try {
                return restClient.execute();
            } catch (IOException | JSONException | InterruptedException e) {
                e.printStackTrace();
                return "fail";

            }
        }


        protected void onPostExecute(String result) {


            if(result.equals("200"))
            {
                JSONObject json = null;
                try {

                    Log.i("UPDATETickets", "resposta : " + restClient.getReturn());
                    json = new JSONObject(restClient.getReturn());


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            else if(result.equals("401"))
            {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainMenu.this);

                builder.setMessage("Server Errort")
                        .setTitle("Error");


                AlertDialog dialog = builder.create();
                dialog.show();

                // Toast.makeText(MainActivity.this,"Your email/password are incorrect", Toast.LENGTH_SHORT).show();
            }
            else if(result.equals("fail"))
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainMenu.this);

                builder.setMessage("Error connecting to server")
                        .setTitle("Error");


                AlertDialog dialog = builder.create();
                dialog.show();
            }
            else {
                Toast.makeText(MainMenu.this, "Code: " + result, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
