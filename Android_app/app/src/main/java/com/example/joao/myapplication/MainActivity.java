package com.example.joao.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private EditText username, password;
    private RadioGroup selectionGroup;
    private EditText email;
    private RestClient restClient;
    MessageDigest digester;

    byte[] passEncrypted;
    private ProgressBar progressBar;

    private CheckInternetConnection connection = CheckInternetConnection.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        try {
            restClient = RestClient.getInstance();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        try {
            digester = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        username = (EditText) findViewById(R.id.name);
        password= (EditText) findViewById(R.id.type);
        email = (EditText) findViewById(R.id.email);
        selectionGroup = (RadioGroup) findViewById(R.id.radioGroup);

        selectionGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if( R.id.radioSignIn == checkedId)
                {
                    username.setVisibility(View.INVISIBLE);

                }
                else if(R.id.radioRegister == checkedId)
                {
                    username.setVisibility(View.VISIBLE);
                    username.setAlpha(0.0f);

                    username.animate()
                            .translationY(username.getHeight())
                            .alpha(1.0f);

                }
            }
        });



        // bypass login, for test purposes
        //bypassLogin();
    }

    private Context getContext()
    {
        return getBaseContext();
    }

    private boolean getFields(boolean nameVerification)
    {
        Boolean valid = true;
        String usernameString;
        String passwordString;
        usernameString = username.getText().toString();
        passwordString =  password.getText().toString();



        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches())
        {
            email.setError("Invalid email address");
            valid = false;
        }
        else if(TextUtils.isEmpty(passwordString))
        {
            password.setError(getString(R.string.error_field_required));
            valid = false;
        }
        else if(nameVerification)
        {
            if(TextUtils.isEmpty(usernameString) )
            {
                username.setError(getString(R.string.error_field_required));
                valid = false;
            }
        }


        return valid;
    }

    public void confirm(View view)
    {

        if(selectionGroup.getCheckedRadioButtonId() == R.id.radioRegister)
        {
            //do register
            if(getFields(true))
            {
                restClient.setMethod("POST");
                restClient.setUrl("https://testcake3333.herokuapp.com/api/users.json");
                restClient.addParam("email", email.getText().toString());
                restClient.addParam("name", username.getText().toString());

                //encript password
                String tmpPass = password.getText().toString();
                passEncrypted = digester.digest(tmpPass.getBytes());
                //restClient.addParam("password", passEncrypted.toString());
                restClient.addParam("password", Arrays.toString(passEncrypted));

                //execute
                progressBar.setVisibility(View.VISIBLE);
                new RegisterTask().execute();

            }

        }
        else if (selectionGroup.getCheckedRadioButtonId() == R.id.radioSignIn)
        {
            // do login
            if(getFields(false))
            {
                //try login
                restClient.setMethod("POST");
                restClient.setUrl("https://testcake3333.herokuapp.com/api/login.json");
                //restClient.setUrl("https://httpbin.org/post");
                restClient.addParam("email", email.getText().toString());

                //encript password
                String tmpPass = password.getText().toString();
                passEncrypted = digester.digest(tmpPass.getBytes());
                restClient.addParam("password", Arrays.toString(passEncrypted));
                //restClient.addParam("password", "test");

                progressBar.setVisibility(View.VISIBLE);
                new LoginTask().execute();



            }

        }


    }

    // ------------------------------------------------------------------- REST TASKS ---------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //-------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //-------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //-------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------

    private class LoginTask extends AsyncTask<Void,Void,String> {
        /** The system calls this to perform work in a worker thread and
         * delivers it the parameters given to AsyncTask.execute() */
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



        /** The system calls this to perform work in the UI thread and delivers
         * the result from doInBackground() */
        protected void onPostExecute(String result) {

            progressBar.setVisibility(View.GONE);

            if (result.equals("No Connection"))
            {
                Toast.makeText(MainActivity.this, "Can't connect to server", Toast.LENGTH_SHORT).show();
            } else {
                if (result.equals("200")) {
                    JSONObject json = null;
                    try {
                        json = new JSONObject(restClient.getReturn());
                        String token = json.get("token").toString();
                        Intent intent = new Intent(getBaseContext(), MainMenu.class);
                        Bundle info = new Bundle();
                        info.putString("email", email.getText().toString());
                        info.putString("password", Arrays.toString(passEncrypted));
                        info.putString("token", token);

                        intent.putExtras(info);

                        startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else if (result.equals("400")) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                    builder.setMessage("Your email/password are incorrect")
                            .setTitle("Failed login");


                    AlertDialog dialog = builder.create();
                    dialog.show();

                    // Toast.makeText(MainActivity.this,"Your email/password are incorrect", Toast.LENGTH_SHORT).show();
                } else if (result.equals("fail")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                    builder.setMessage("Error connecting to server")
                            .setTitle("Failed login");


                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }


        }
    }

    private class RegisterTask extends AsyncTask<Void,Void,String> {
        /** The system calls this to perform work in a worker thread and
         * delivers it the parameters given to AsyncTask.execute() */
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



        /** The system calls this to perform work in the UI thread and delivers
         * the result from doInBackground() */
        protected void onPostExecute(String result) {
            progressBar.setVisibility(View.GONE);

            if (result.equals("No Connection"))
            {
                Toast.makeText(MainActivity.this, "Can't connect to server", Toast.LENGTH_SHORT).show();
            } else {
                if (result.equals("201")) {
                    Toast.makeText(MainActivity.this, "User account registed", Toast.LENGTH_SHORT).show();
                } else if (result.equals("400")) {
                    Toast.makeText(MainActivity.this, "Error creating User", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

    public void bypassLogin()
    {
        Intent intent = new Intent(this, MainMenu.class);
        Bundle info = new Bundle();
        info.putString("email","luminosity@cs.com");
        info.putString("password", "pass");

        intent.putExtras(info);

        startActivity(intent);
    }



}
