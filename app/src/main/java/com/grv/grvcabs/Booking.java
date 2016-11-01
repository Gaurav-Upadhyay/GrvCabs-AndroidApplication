package com.grv.grvcabs;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Booking extends AppCompatActivity {

    public static final String MY_PREFS_NAME = "MyPrefsFile";

    public static final String DATA_URL = "http://gov.net16.net/GrvCabs/getBooking.php?id=";
    public static final String DATA_URL2 = "http://gov.net16.net/GrvCabs/deleteBooking.php?id=";
    public static final String KEY_FROM = "from";
    public static final String KEY_TO = "to";
    public static final String KEY_TIME_BOOKING = "time";
    public static final String KEY_STATUS = "status";
    public static final String JSON_ARRAY = "result";

    private ProgressDialog loading;

    TextView tv_status;
    TextView tv_from;
    TextView tv_to;
    TextView tv_time;
    String contact;

    private void getData() {

        loading = ProgressDialog.show(this, "Please wait...", "Fetching...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, DATA_URL + contact.trim(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Booking.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response) {
        String from = "";
        String to = "";
        String time = "";
        String status = "";

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(JSON_ARRAY);
            JSONObject bookingData = result.getJSONObject(0);
            from = bookingData.getString(KEY_FROM);
            to = bookingData.getString(KEY_TO);
            time = bookingData.getString(KEY_TIME_BOOKING);
            status = bookingData.getString(KEY_STATUS);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        tv_time.setText(time);
        tv_status.setText(status);
        tv_to.setText(to);
        tv_from.setText(from);
    }

    public void deleteData(View view) {

        loading = ProgressDialog.show(this, "Please wait...", "Fetching...", false, false);
        StringRequest stringRequest2 = new StringRequest(Request.Method.GET,DATA_URL2 + contact.trim(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                Toast.makeText(getBaseContext(), "Booking Canceled", Toast.LENGTH_LONG).show();
                finish();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Booking.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue requestQueue2 = Volley.newRequestQueue(this);
        requestQueue2.add(stringRequest2);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        tv_status = (TextView) findViewById(R.id.tv_status);
        tv_from = (TextView) findViewById(R.id.tv_from);
        tv_to = (TextView) findViewById(R.id.tv_to);
        tv_time = (TextView) findViewById(R.id.tv_time);


        SharedPreferences sd = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        contact = sd.getString("number", "");

        getData();
    }
}