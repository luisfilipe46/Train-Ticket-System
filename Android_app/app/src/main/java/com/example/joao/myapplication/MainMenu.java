package com.example.joao.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;



public class MainMenu extends AppCompatActivity {

    static String buyTicketsURL = "https://testcake3333.herokuapp.com/api/tickets.json";
    static String getTravelsURL = "https://testcake3333.herokuapp.com/api/timetables_with_final_stations/";

    final Context context = this;
    public String name, pass,email;
    Spinner spinnerStart;
    Spinner spinnerEnd;

    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;
    private RestClient restClient;
    private TextView courseInfo;
    private Button btnGetTrains;
   // private TextView restResult;
    private RelativeLayout progressBar;
    private TableLayout availableTravels;
    HashMap<String, String> stationsMap = new HashMap<String, String>();
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeStationsMap();

        try {
            restClient = RestClient.getInstance();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Intent intent = getIntent();
        Bundle info = intent.getExtras();

        email = info.getString("email");
        pass = info.getString("password");
        token = info.getString("token");



        setContentView(R.layout.activity_main_menu);

        // To allow Resquest http sync
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //restResult = (TextView)findViewById(R.id.restResult);
        mDrawerList = (ListView)findViewById(R.id.navList);
        spinnerStart = (Spinner) findViewById(R.id.spinner_start_station);
        spinnerEnd = (Spinner) findViewById(R.id.spinner_end_station);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        progressBar = (RelativeLayout)findViewById(R.id.loadingPanel);
        mActivityTitle = getTitle().toString();
        setTitle("Choose Travel");
        courseInfo = (TextView) findViewById(R.id.course_info);
        btnGetTrains = (Button) findViewById(R.id.button_getTrains);

        availableTravels = (TableLayout) findViewById(R.id.available_trains);

        //
        progressBar.setVisibility(View.GONE);

        // add side menu items
        addDrawerItems();

        //set handler
        setHandlerDrawer();




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
        stationsMap.put("11","Station 11");
        stationsMap.put("12","Station 12");
        stationsMap.put("01","Station 01");
        stationsMap.put("32", "Station 32");


    }

    private void addDrawerItems() {
        String[] osArray = { "My Tickets", "REST Test GET", "REST Client POST", "Add Credit Card", "Linux" };
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);
    }




    private void setHandlerDrawer()
    {
        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout, R.string.drawer_open, R.string.drawer_close);
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) // My tickets
                {
                    Intent intent = new Intent(getBaseContext(), My_ticketsView.class);
                    Bundle info = new Bundle();
                    info.putString("email", email);
                    info.putString("password", pass);
                    info.putString("token",token);
                    intent.putExtras(info);
                    startActivity(intent);
                } else if (position == 1) { //REST GET TEST

                    String result;
                    try {
                        restClient.setMethod("GET");
                        restClient.setUrl("https://testcake3333.herokuapp.com/api/credit_cards/123456.json");
                        result = restClient.execute();
                        Toast.makeText(MainMenu.this, result, Toast.LENGTH_SHORT).show();

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else if (position == 2) { //REST POST TEST

                    restClient.setMethod("POST");
                    restClient.setUrl("https://httpbin.org/post");
                    restClient.addParam("testeName1", "testeValue1");
                    restClient.addParam("testeName3", "testeValue3");
                    progressBar.setVisibility(View.VISIBLE);

                    new Thread(new Runnable() {
                        public void run() {
                            try {

                                Thread.sleep(2000);
                                restClient.execute();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            availableTravels.post(new Runnable() {
                                public void run() {
                                    progressBar.setVisibility(View.GONE);
                                    updateText();
                                }
                            });
                        }
                    }).start();


                    // restResult.setText(result);

                } else if (position == 3) { //Add credit card
                    DialogAddCard dialogCard = null;
                    try {

                        dialogCard = new DialogAddCard();
                        Bundle bundle = new Bundle();
                        bundle.putString("email", email);
                        bundle.putString("pass", pass);
                        bundle.putString("token",token);
                        dialogCard.setArguments(bundle);
                        dialogCard.show(getFragmentManager(), "Cenas");

                        // dialogCard.setListeners();

                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }


                } else {
                    Toast.makeText(MainMenu.this, "Nothing to do", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void updateText() {
        String str = restClient.getReturn();
        //restResult.setText(str)
        Toast.makeText(MainMenu.this, str, Toast.LENGTH_SHORT).show();
    }

    private void updateAvailableTravels(JSONArray arr, JSONArray arrCourse) throws JSONException {
        availableTravels.removeAllViews();
        String course= "";
        for (int i = 0; i < arrCourse.length(); i++)
        {
            if(i!=0)
            {
                course = course + " - ";
            }

            course = course + arrCourse.get(i).toString();
        }

        courseInfo.setText("Travel route: " + course);

        String originStation = arrCourse.get(0).toString();
        //originStation = stationsMap.get(originStation);

        String destinyStation = arrCourse.get(arrCourse.length()-1).toString();
        //destinyStation = stationsMap.get(destinyStation);

        progressBar.setVisibility(View.GONE);
        btnGetTrains.setEnabled(true);



        for (int i = 0; i < arr.length(); i++)
        {



            String station1 = arr.getJSONObject(i).getString("origin_station");
            String station1Name = stationsMap.get(station1);
            String station2 = arr.getJSONObject(i).getString("destiny_station");
            String station2Name  = stationsMap.get(station2);
            String hour1 = arr.getJSONObject(i).getString("departure_time");
            hour1 = hour1.substring(11,19);
            String hour2 = arr.getJSONObject(i).getString("arrival_time");
            hour2 = hour2.substring(11,19);
            String line = "";
            if(station1.equals(originStation))
            {
                line = "Line 1";
            }
            else {
                line = "Line 2";
            }


            TableRow row= new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0,0,0,0);

            row.setLayoutParams(lp);
            Button addBtn = new Button(this);
            addBtn.setText("Buy");
            addBtn.setOnClickListener(new BuyButtonOnClickListener(hour1,hour2,station1,station2));

            TableLayout innerTable = new TableLayout(this);
            TableRow innerRow1 = new TableRow(this);
            TableRow innerRow2 = new TableRow(this);


            TextView station1Text = new TextView(this);
            station1Text.setText(Html.fromHtml(station1Name + "(" + hour1 + ")"));

            TextView toText = new TextView(this);
            toText.setText(" to ");
            toText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);

            TextView station2Text = new TextView(this);
            station2Text.setText(Html.fromHtml(station2Name + "(" + hour2 + ")"));

            innerRow1.addView(station1Text);
            innerRow2.addView(station2Text);

            innerTable.addView(innerRow1);
            innerTable.addView(toText);
            innerTable.addView(innerRow2);
            TableRow.LayoutParams param = new TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT, 1.0f);

            innerTable.setLayoutParams(param);

            TextView lineText = new TextView(this);
            lineText.setText(line);



            // Black line on end
            View endLine = new View(this);
            LinearLayout.LayoutParams lineParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,5);
            endLine.setLayoutParams(lineParams);
            if(line.equals("Line 1"))
            {
                endLine.setBackgroundColor(Color.BLACK);
            }
            else {
                endLine.setBackgroundColor(Color.BLUE);
            }

            // White line on end
            View whiteLine = new View(this);
            LinearLayout.LayoutParams whiteParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,15);
            whiteLine.setLayoutParams(whiteParams);
            whiteLine.setBackgroundColor(Color.WHITE);



            row.addView(innerTable);
            row.addView(lineText);
            row.addView(addBtn);
            if(line.equals("Line 1"))
                {
                    row.setBackgroundResource(R.drawable.table_line1);
                }
                else
                {
                    row.setBackgroundResource(R.drawable.table_line2);
                }


            availableTravels.addView(row);
            //availableTravels.addView(endLine);
            //availableTravels.addView(whiteLine);



        }
    }

    public void getTravels(View view) throws InterruptedException {
        if(spinnerEnd.getSelectedItemPosition() == spinnerStart.getSelectedItemPosition())
        {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    context);

            // set title
            alertDialogBuilder.setTitle("Same Stations");

            // set dialog message
            alertDialogBuilder.setMessage("Destiny station should be different than start station");

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();

        }
        else // make request To DB and show available options
        {
            progressBar.setVisibility(View.VISIBLE);
            btnGetTrains.setEnabled(false);

            String idStartStation = (String) getKeyFromValue(stationsMap, spinnerStart.getSelectedItem().toString());
            String idEndStation = (String) getKeyFromValue(stationsMap, spinnerEnd.getSelectedItem().toString());
            restClient.setMethod("GET");
            restClient.setUrl(getTravelsURL + idStartStation + "/" + idEndStation + ".json");
            Log.i("URL GENERATED", "https://testcake3333.herokuapp.com/api/timetables_with_final_stations/" + idStartStation + "/" + idEndStation + ".json");

            new GetTravelsTask().execute();







        }


    }

    private void buyTicket(String station1, String station2, String hour1, String hour2) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);


        //restClient.setUrl("https://httpbin.org/post");
        restClient.setUrl(buyTicketsURL);
        restClient.setMethod("POST");
        restClient.addParam("origin_station", station1);
        restClient.addParam("destiny_station",station2);
        //restClient.addParam("arrival_time",hour1);
        restClient.addParam("departure_time",hour1);
        String day = "2015-10-06";
        restClient.addParam("day",day);
        addtoken();
        /*restClient.addHeader("email",email);
        restClient.addHeader("password",pass);*/

        new BuyTicketTask().execute();





    }

    private void addtoken() {

        restClient.addHeader("token",token);
    }

    private class GetTravelsTask extends AsyncTask<Void,Void,String> {
        /** The system calls this to perform work in a worker thread and
         * delivers it the parameters given to AsyncTask.execute() */
        @Override
        protected String doInBackground(Void... params) {

            try {
                return restClient.execute();
            } catch (IOException | JSONException | InterruptedException e) {
                return "fail";
                //e.printStackTrace();
            }
        }



        /** The system calls this to perform work in the UI thread and delivers
         * the result from doInBackground() */
        protected void onPostExecute(String result) {
             if(result.equals("200")) {
                 JSONArray arr = null;
                 JSONArray arrCourse = null;
                 try {
                     JSONObject json = new JSONObject(restClient.getReturn());
                     arr = json.getJSONArray("timetables");
                     arrCourse = json.getJSONArray("routes");
                 } catch (JSONException e) {
                     e.printStackTrace();
                 }

                 if (arr == null) {
                     Toast.makeText(MainMenu.this, "can't form json from server response", Toast.LENGTH_SHORT).show();
                 } else {
                     try {
                         updateAvailableTravels(arr,arrCourse);
                     } catch (JSONException e) {
                         e.printStackTrace();
                     }

                 }
             }
            else {
                 Toast.makeText(MainMenu.this, "Error getting Travels", Toast.LENGTH_SHORT).show();
             }


        }
    }

    private class BuyTicketTask extends AsyncTask<Void,Void,String> {
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

            if(result.equals("200"))
            {
                Toast.makeText(MainMenu.this, "Bilhete Comprado", Toast.LENGTH_SHORT).show();
                Log.i("Retorno: ","cenas 200: "+  restClient.getReturn());
            }
            else if(result.equals("401"))
            {
                Log.e("COMPRA BILHETE", "Email/pass errados");
            }
            else if(result.equals("400"))
            {
                Log.i("Retorno: ","cenas 400: "+  restClient.getReturn());
                /*try {

                    JSONObject json = new JSONObject(restClient.getReturn());
                    String erro = json.get("error").toString();
                    Toast.makeText(MainMenu.this, erro, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/

            }
            else
            {
                Log.e("COMPRA BILHETE", "Retorno nao esperado: " + result);

            }





        }
    }

    public class BuyButtonOnClickListener implements View.OnClickListener
    {

        String hour1,hour2,startStation,endStation;

        public BuyButtonOnClickListener(String h1,String h2, String startStation,String endStation) {
            this.startStation = startStation;
            this.endStation = endStation;
            hour1 = h1;
            hour2 = h2;
        }

        @Override
        public void onClick(View v)
        {
            buyTicket(startStation,endStation,hour1,hour2);
        }

    };

    public static Object getKeyFromValue(Map hm, String value) {
        for (Object o : hm.keySet()) {
            if (hm.get(o).equals(value)) {
                return o;
            }
        }
        return null;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
}
