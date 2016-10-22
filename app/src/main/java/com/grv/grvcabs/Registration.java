package com.grv.grvcabs;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Registration extends AppCompatActivity {

    TextView add_user;
    EditText username, usermobile, password, repassword;


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        final LoginDBHelper userdbhelper = new LoginDBHelper(this);

        username = (EditText) findViewById(R.id.username);
        usermobile = (EditText) findViewById(R.id.usernumber);
        password = (EditText) findViewById(R.id.password);
        repassword = (EditText) findViewById(R.id.repassword);
        add_user = (TextView) findViewById(R.id.addUser);
        add_user.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //username usermobile password
                if (username.getText().toString().length() == 0 || username.getText().toString().length() <= 4) {
                    username.setError("Username cannot be blank or less then 5 letters");
                } else {

                    if (usermobile.getText().toString().length() <= 9 || usermobile.getText().toString().length() > 10) {
                        usermobile.setError("Invalid Mobile Number");
                    } else if (usermobile.getText().length() == 10) {
                        if (password.getText().length() <= 4) {
                            password.setError("Atleast 5 characters");
                        } else {
                            if (password.getText().toString().equals(repassword.getText().toString())) {
                                Log.d("1", "3");
                                String usernu = usermobile.getText().toString();
                                String userp = password.getText().toString();
                                Log.d("1", "4");
                                Cursor c = userdbhelper.retrieveUser(usernu);
                                if (c.moveToFirst()) {
                                    Log.d("1", "2");
                                    if (usernu.equals(c.getString(2))) {
                                        Log.d("1", "1");
                                        Toast.makeText(getBaseContext(), "Number Already Registered", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    String usernamee;
                                    usernamee = username.getText().toString();
                                    long id = userdbhelper.insertUser(usernu, usernamee, userp);
                                    Toast.makeText(getBaseContext(), "You have been SuccessFully Registered: " + usernamee,
                                            Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            } else {
                                repassword.setError("Password do not match");
                            }
                        }

                    }


                }
            }
        });
    }
}