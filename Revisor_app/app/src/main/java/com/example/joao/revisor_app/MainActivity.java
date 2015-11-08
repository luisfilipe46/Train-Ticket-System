package com.example.joao.revisor_app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private EditText username, password;

    private EditText email;
    private RestClient restClient;
    MessageDigest digester;

    byte[] passEncrypted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



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





        // bypass login, for test purposes
       // bypassLogin();
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


            if(getFields(false))
            {
                //try login
                restClient.setMethod("POST");
                restClient.setUrl("https://testcake3333.herokuapp.com/api/login_revisor.json");
                //restClient.setUrl("https://httpbin.org/post");
                restClient.addParam("email", email.getText().toString());

                //encript password
                String tmpPass = password.getText().toString();
                passEncrypted = digester.digest(tmpPass.getBytes());
                restClient.addParam("password", Arrays.toString(passEncrypted));
                //restClient.addParam("password", "test");

                new LoginTask().execute();



            }




    }

    private class LoginTask extends AsyncTask<Void,Void,String> {
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

            }
            else if(result.equals("400"))
            {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setMessage("Your email/password are incorrect")
                        .setTitle("Failed login");


                AlertDialog dialog = builder.create();
                dialog.show();

                // Toast.makeText(MainActivity.this,"Your email/password are incorrect", Toast.LENGTH_SHORT).show();
            }
            else if(result.equals("fail"))
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setMessage("Error connecting to server")
                        .setTitle("Failed login");


                AlertDialog dialog = builder.create();
                dialog.show();
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



   /* public void initializeBarcodeScan(View view) {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.initiateScan();
    }
}*/
