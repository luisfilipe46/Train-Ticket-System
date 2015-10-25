package com.example.joao.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.StrictMode;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Vector;

import javax.net.ssl.HttpsURLConnection;

public class MainMenu extends AppCompatActivity {

    final Context context = this;
    public String name;
    Spinner spinnerStart;
    Spinner spinnerEnd;
    public Vector<Ticket> tickets;

    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;
    private RestClient restClient;
    private TextView restResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            restClient = new RestClient("https://httpbin.org/get");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Intent intent = getIntent();
        Bundle info = intent.getExtras();

        String user = info.getString("username");
        String pass = info.getString("password");
        name = user;

        setContentView(R.layout.activity_main_menu);

        // To allow Resquest http sync
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        restResult = (TextView)findViewById(R.id.restResult);
        mDrawerList = (ListView)findViewById(R.id.navList);
        spinnerStart = (Spinner) findViewById(R.id.spinner_start_station);
        spinnerEnd = (Spinner) findViewById(R.id.spinner_end_station);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        // add side menu items
        addDrawerItems();

        //set handler
        setHandlerDrawer();

        TextView msg = (TextView) findViewById(R.id.displayName);

        if(msg != null)
        {
            Log.i("MSG_CONTENT", (String) msg.getText());
            msg.setText("Hello " + user + " your pass is: " + pass);
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
                    Toast.makeText(MainMenu.this, "Show tickets", Toast.LENGTH_SHORT).show();
                } else if(position ==1){

                    String result;
                    try {
                        restClient.setMethod("GET");
                        restClient.setUrl("https://httpbin.org/get");
                        result = restClient.execute();
                        restResult.setText(result);

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else if(position == 2)
                {
                    String result;
                    try {

                        restClient.setMethod("POST");
                        restClient.setUrl("https://httpbin.org/post");
                        restClient.addParam("testeName1", "testeValue1");
                        restClient.addParam("testeName3","testeValue3");
                        result = restClient.execute();
                        restResult.setText(result);

                    } catch (ProtocolException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else if(position == 3)
                {
                    restResult.setText("");
                }
                else
                {
                    Toast.makeText(MainMenu.this, "Nothing to do", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void getTravels(View view)
    {
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
