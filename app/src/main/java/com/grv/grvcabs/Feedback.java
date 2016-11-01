package com.grv.grvcabs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
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



public class Feedback extends AppCompatActivity {

    EditText etv11, etv21, etv31;

    LinearLayout lt;
    public static final String REGISTER_URL2 = "http://gov.net16.net/GrvCabs/feedback.php";
    public static final String KEY_1 = "detail0";
    public static final String KEY_2 = "detail1";
    public static final String KEY_3 = "detail2";
    public static final String KEY_4 = "detail3";



    private void registerfeedback() {
        final String one=etv11.getText().toString().trim();
        final String two = etv11.getText().toString().trim();
        final String three = etv21.getText().toString().trim();
        final String four = etv31.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                          finish();
                        Toast.makeText(getBaseContext(),"Feedback Registered", Toast.LENGTH_SHORT).show();
                        }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Feedback.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_1,one);
                params.put(KEY_2, two);
                params.put(KEY_3, three);
                params.put(KEY_4, four);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        etv11 = (EditText) findViewById(R.id.etv1);
        etv21 = (EditText) findViewById(R.id.etv2);
        etv31 = (EditText) findViewById(R.id.etv3);
        lt = (LinearLayout) findViewById(R.id.linearLayout2);
        lt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerfeedback();
            }
        });
    }
}
