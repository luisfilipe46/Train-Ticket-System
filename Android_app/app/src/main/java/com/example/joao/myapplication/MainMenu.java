package com.example.joao.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.StrictMode;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Vector;

public class MainMenu extends AppCompatActivity {

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
   // private TextView restResult;
    private RelativeLayout progressBar;
    private TableLayout availableTravels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        try {
            restClient = RestClient.getInstance();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Intent intent = getIntent();
        Bundle info = intent.getExtras();

        email = info.getString("email");
        pass = info.getString("password");



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

    private void addDrawerItems() {
        String[] osArray = { "My Tickets", "REST Test GET", "REST Client POST", "Clear Text", "Linux" };
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
                    Intent intent = new Intent(getBaseContext(), My_tickets.class);
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
                            }catch (InterruptedException e) {
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

                } else if (position == 3) {

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

    private void updateAvailableTravels()
    {
        progressBar.setVisibility(View.GONE);


        for (int i = 0; i <2; i++) {

            String station1 = "Station 1";
            String station2 = "Station 2";
            String hour1 = "14:15";
            String hour2 = "15:30";

            TableRow row= new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0,0,0,0);

            row.setLayoutParams(lp);
            Button addBtn = new Button(this);
            addBtn.setText("Buy");
            addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buyTicket();
                }
            });

            TableLayout innerTable = new TableLayout(this);
            TableRow innerRow1 = new TableRow(this);
            TableRow innerRow2 = new TableRow(this);


            TextView station1Text = new TextView(this);
            station1Text.setText(Html.fromHtml(station1 + "(" + hour1 + ")"));

            TextView toText = new TextView(this);
            toText.setText(" to ");
            toText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);

            TextView station2Text = new TextView(this);
            station2Text.setText(Html.fromHtml(station2 + "(" + hour2 + ")"));

            innerRow1.addView(station1Text);
            innerRow2.addView(station2Text);

            innerTable.addView(innerRow1);
            innerTable.addView(toText);
            innerTable.addView(innerRow2);
            TableRow.LayoutParams param = new TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT, 1.0f);

            innerTable.setLayoutParams(param);

            row.addView(innerTable);
            row.addView(addBtn);
            row.setBackgroundResource(R.drawable.table);
            availableTravels.addView(row, i);


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

            new Thread(new Runnable() {
                public void run() {
                    try {

                        Thread.sleep(500);
                        //restClient.execute();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    availableTravels.post(new Runnable() {
                        public void run() {
                            updateAvailableTravels();
                        }
                    });
                }
            }).start();





        }


    }

    private void buyTicket() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set title
        alertDialogBuilder.setTitle("Buying");

        // set dialog message
        alertDialogBuilder.setMessage("Ticket sad sadas 10 pm" );

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();


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
