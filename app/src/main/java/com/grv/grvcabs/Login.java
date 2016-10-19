package com.grv.grvcabs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity implements
        View.OnClickListener {

    EditText user_number;
    EditText user_pass;


    UserSessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        session = new UserSessionManager(getApplicationContext());

        user_number = (EditText) findViewById(R.id.input_mobile_number);
        user_pass = (EditText) findViewById(R.id.input_password);
        findViewById(R.id.searchUser).setOnClickListener(this);

    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.searchUser:


                if (user_number.getText().toString().length() == 0 && user_pass.getText().toString().length() == 0) {
                    user_number.setError("Phone Number cannot be blank");
                    user_pass.setError("Password cannot be blank");
                } else if (user_number.getText().toString().length() < 10 && user_pass.getText().toString().length() <= 4) {
                    user_number.setError("Invalid Phone Number");
                    user_pass.setError("Atleast 5 letters");
                } else if (user_number.getText().toString().length() == 10 && user_pass.getText().toString().length() <= 4) {
                    user_pass.setError("Atleast 5 letters");
                } else if (user_number.getText().toString().length() > 10 && user_pass.getText().toString().length() <= 4) {
                    user_number.setError("Invalid Phone Number");
                    user_pass.setError("Atleast 5 letters");
                } else if (user_number.getText().toString().length() < 10 && user_pass.getText().toString().length() > 4) {
                    user_number.setError("Invalid Phone Number");
                } else if (user_number.getText().toString().length() > 10 && user_pass.getText().toString().length() >= 4) {
                    user_number.setError("Invalid Phone Number");
                } else if (user_number.getText().toString().length() == 10 && user_pass.getText().toString().length() > 4) {
                    final LoginDBHelper usrdb = new LoginDBHelper(this);
                    String usernu = user_number.getText().toString();
                    String userp = user_pass.getText().toString();
                    Cursor c = usrdb.retrieveUser(usernu);
                    if (c.moveToFirst()) {
                        if (usernu.equals(c.getString(2))) {
                            if (userp.equals(c.getString(3))) {
                                Intent intent = new Intent(Login.this, MainPage.class);
                                startActivity(intent);
                                finish();
                                Toast.makeText(getBaseContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                                session.createUserLoginSession(c.getString(1), c.getString(2));
                            } else {
                                Toast.makeText(getBaseContext(), "Incorrect Password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        Toast.makeText(getBaseContext(), "Record does not exist", Toast.LENGTH_SHORT).show();
                        break;

                    }
                }


        }
    }
}
