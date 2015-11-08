package com.example.joao.revisor_app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
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

    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

    Spinner spinnerStart,spinnerEnd;
    String token,email,pass;
    HashMap<String, String> stationsMap = new HashMap<String, String>();
    Vector_tickets vecTickets;
    Button btnUpdateTickets;
    private RestClient restClient;
    private TextView date,departureTime;
    private String updateTicketsURL = "https://testcake3333.herokuapp.com/api/tickets";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        vecTickets = Vector_tickets.getInstance();

        initializeStationsMap();

        Intent intent = getIntent();
        Bundle info = intent.getExtras();

        email = info.getString("email");
        pass = info.getString("password");
        token = info.getString("token");



        spinnerStart = (Spinner) findViewById(R.id.spinnerStart);
        spinnerEnd = (Spinner) findViewById(R.id.spinnerEnd);
        btnUpdateTickets = (Button) findViewById(R.id.btnUpdateTickets);
        date = (TextView) findViewById(R.id.date);
        departureTime = (TextView) findViewById(R.id.departureTime);





        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView)findViewById(R.id.navList);


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

        // add side menu items
        addDrawerItems();

        //set handler
        setHandlerDrawer();

    }

    private void setHandlerDrawer()
    {
        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout, R.string.drawer_open, R.string.drawer_close);
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) // View tickets
                {
                    startTicketActivity();
                } else if (position == 1) //Qr code Launch
                {
                        launchQrCodeScan(MainMenu.this);
                } else {
                    Toast.makeText(MainMenu.this, "Nothing to do", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void launchQrCodeScan(Activity activity)
    {
        Intent intent = new Intent(getBaseContext(), QRCodeScan_Activity.class);
        Bundle info = new Bundle();
        info.putString("email", email);
        info.putString("password", pass);
        info.putString("token", token);

        intent.putExtras(info);

        startActivity(intent);
    }

    private void addDrawerItems() {
        String[] osArray = { "View Tickets",  "Launch QrCode Validator"};

        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, osArray));;
    }

    private void initializeStationsMap() {
        stationsMap.put("01","Trindade");
        stationsMap.put("11","S. Joao");
        stationsMap.put("12","IPO");
        stationsMap.put("21","Aliados");
        stationsMap.put("22","Faria Guimaraes");
        stationsMap.put("31", "Azurara");
        stationsMap.put("32", "Vila do Conde");




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

    private void addTickets(JSONArray arr) throws JSONException {

        vecTickets.tickets.clear();

        for (int i =0; i < arr.length();i++)
        {
            Ticket t;
            JSONObject ticketJson = arr.getJSONObject(i);
            int id = ticketJson.getInt("id");
            int idUser = ticketJson.getInt("id_users");

            String origin = ticketJson.getString("origin_station");
            String destiny = ticketJson.getString("destiny_station");
            String qrCode = ticketJson.getString("qr_code");

            String hourStart = ticketJson.getString("departure_time");
            String date = hourStart.substring(0, 10);
            hourStart = hourStart.substring(11,19);

            String hourEnd = ticketJson.getString("arrival_time");
            hourEnd = hourEnd.substring(11,19);

            boolean used = ticketJson.getBoolean("used");
            double price = ticketJson.getDouble("price");


            t = new Ticket(qrCode,origin,destiny,date,hourStart,hourEnd,"1",used,price,id,idUser);

            vecTickets.tickets.add(t);

        }
    }






    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Log.i("ITEM CLIQUED","Clicado id: " + item.getItemId() + " Home id: " + android.R.id.home);
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }






    // ------------------------------------------------------------------- REST TASKS ---------------------------------------------------------------------------------
    //
    //
    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------

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
                JSONArray arr = null;
                try {

                    Log.i("UPDATETickets", "resposta : " + restClient.getReturn());
                    json = new JSONObject(restClient.getReturn());
                    arr = json.getJSONArray("tickets");


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(arr == null)
                {
                    Log.i("UPDATETickets", "Array de bilhetes nao formado");
                }
                else{
                    try {
                        addTickets(arr);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    startTicketActivity();

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



    private void startTicketActivity() {


        Intent intent = new Intent(getBaseContext(), Tickets_view.class);
        Bundle info = new Bundle();
        info.putString("email", email);
        info.putString("password", pass);
        info.putString("token", token);

        intent.putExtras(info);

        startActivity(intent);
    }


}
