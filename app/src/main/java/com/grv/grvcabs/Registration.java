package com.grv.grvcabs;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity implements View.OnClickListener {

    TextView add_user;
    EditText username1, usermobile1, password1, repassword1;


    public static final String REGISTER_URL = "http://gov.net16.net/GrvCabs/Register.php";

    public static final String KEY_USERNAME = "name";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_NUMBER = "number";
    private ProgressDialog loading;

    private void registerUser() {
        Log.e("Tag", "Tag4");

        if (username1.getText().toString().length() == 0 || username1.getText().toString().length() <= 4) {
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

// Vibrate for 400 milliseconds
            v.vibrate(400);
            username1.setError("Username cannot be blank or less then 5 letters");
        } else {

            if (usermobile1.getText().toString().length() <= 9 || usermobile1.getText().toString().length() > 10) {
                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

// Vibrate for 400 milliseconds
                v.vibrate(400);
                usermobile1.setError("Invalid Mobile Number");
            } else if (usermobile1.getText().length() == 10) {
                if (password1.getText().length() <= 4) {
                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

// Vibrate for 400 milliseconds
                    v.vibrate(400);
                    password1.setError("Atleast 5 characters");
                } else {
                    if (password1.getText().toString().equals(repassword1.getText().toString())) {


                        /// our server side code goes here
                        final String username = username1.getText().toString().trim();
                        final String password = password1.getText().toString().trim();
                        final String usernumber = usermobile1.getText().toString().trim();
                        loading = ProgressDialog.show(this, "Please wait...", "Registering...", false, false);
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                        if (response.trim().contains("Successfully")) {
                                            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                                            loading.dismiss();
// Vibrate for 400 milliseconds
                                            v.vibrate(400);
                                            finish();
                                            Toast.makeText(Registration.this, "Registration Successfull", Toast.LENGTH_LONG).show();
                                        }

                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

// Vibrate for 400 milliseconds
                                        v.vibrate(400);
                                        Toast.makeText(Registration.this, error.toString(), Toast.LENGTH_LONG).show();
                                    }
                                }) {
                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put(KEY_USERNAME, username);
                                params.put(KEY_PASSWORD, password);
                                params.put(KEY_NUMBER, usernumber);
                                return params;
                            }
                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(this);
                        requestQueue.add(stringRequest);

                    } else {
                        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

// Vibrate for 400 milliseconds
                        v.vibrate(400);
                        repassword1.setError("Password do not match");
                    }
                }
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        username1 = (EditText) findViewById(R.id.username);
        usermobile1 = (EditText) findViewById(R.id.usernumber);
        password1 = (EditText) findViewById(R.id.password);
        repassword1 = (EditText) findViewById(R.id.repassword);
        add_user = (TextView) findViewById(R.id.addUser);
        add_user.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if (v == add_user) {
            registerUser();
        }
    }


}