package com.grv.grvcabs;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MyProfile extends AppCompatActivity {
    public static final String MY_PREFS_NAME = "MyPrefsFile";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String restoredText = prefs.getString("number", null);
        if (restoredText != null) {
            String name = prefs.getString("number", "No name defined");//"No name defined" is the default value.

            Toast.makeText(getBaseContext(),name,Toast.LENGTH_LONG).show();
        }
    }
}
