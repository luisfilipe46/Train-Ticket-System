package com.example.joao.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.username);
        password= (EditText) findViewById(R.id.password);
    }

    private boolean getFields()
    {
        Boolean valid = true;
        String usernameString;
        String passwordString;
        usernameString = username.getText().toString();
        passwordString =  password.getText().toString();


        if(TextUtils.isEmpty(usernameString) )
        {
            username.setError(getString(R.string.error_field_required));
            valid = false;
        }
        else if(TextUtils.isEmpty(passwordString))
        {
            password.setError(getString(R.string.error_field_required));
            valid = false;
        }

        return valid;
    }

    public void login(View view)
    {

        if(getFields() == true)
        {
            //try login

            Intent intent = new Intent(this, MainMenu.class);
            Bundle info = new Bundle();
            info.putString("username",username.getText().toString());
            info.putString("password", password.getText().toString());

            intent.putExtras(info);

            startActivity(intent);

        }
    }

    public void register(View view) {
        if(getFields() == true)
        {
            //do register
        }


    }

}
