package com.example.joao.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by joao on 06-11-2015.
 */
public class DialogAddCard extends DialogFragment {
    RestClient restClient;
    String email,pass,token,URL = "https://testcake3333.herokuapp.com/api/credit_cards.json";
    EditText number,validity,type;


    public DialogAddCard() throws MalformedURLException {
        restClient = RestClient.getInstance();


    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        Bundle bundle = this.getArguments();
        this.email = bundle.getString("email");
        this.pass = bundle.getString("pass");
        this.token = bundle.getString("token");



        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View v = inflater.inflate(R.layout.dialog_signin, null);
        builder.setView(v);

        setListeners(v);

        return builder.create();

    }




    private boolean getParameters() {
        Boolean valid = true;

        if(number == null || type == null || validity == null)
        {
            number = (EditText) getDialog().findViewById(R.id.numberCard);
            type = (EditText) getDialog().findViewById(R.id.typeCard);
            validity = (EditText) getDialog().findViewById(R.id.dateCard);
        }


        if(TextUtils.isEmpty(number.getText().toString()))
        {
            number.setError(getString(R.string.error_field_required));
            valid = false;
        }
        else if(TextUtils.isEmpty(type.getText().toString()))
        {
            type.setError("Field Required");
            valid = false;
        }
        else if(TextUtils.isEmpty(validity.getText().toString()))
        {
            validity.setError(getString(R.string.error_field_required));
            valid = false;
        }
        else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false);
            try {

                if(dateFormat.parse(validity.getText().toString()) == null)
                {
                    valid = false;
                    validity.setError("Invalid date");
                }
            } catch (ParseException e) {
                e.printStackTrace();
                valid = false;
                validity.setError("Invalid date");

            }
        }


        return valid;
    }




    public void setListeners(View view) {
        Button add = (Button) view.findViewById(R.id.addCard);
        Button cancel = (Button) view.findViewById(R.id.cancelCard);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getParameters()) {
                    restClient.setUrl(URL);
                    restClient.setMethod("POST");
                    restClient.addParam("number", number.getText().toString());
                    restClient.addParam("type", type.getText().toString());
                    restClient.addParam("validity", validity.getText().toString());
                    restClient.addHeader("token", token);
                    Log.i("Peddido ADDCARD: " , "feito");
                    new AddCardTask().execute();

                }

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();

            }
        });



    }

    private class AddCardTask extends AsyncTask<Void,Void,String> {
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
            if(result.equals("201")) {
                getDialog().dismiss();
                Toast.makeText(getContext(), "Success creating card", Toast.LENGTH_SHORT).show();

            }
            else if (result.equals("400")){
                Toast.makeText(getContext(), "Error creating card", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getContext(), "Error code " + result, Toast.LENGTH_SHORT).show();
                Log.i("CODE 500 ", restClient.getReturn());
            }


        }
    }
}
