package com.grv.grvcabs;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.sql.Time;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MainPage extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    boolean doubleBackToExitPressedOnce = false;

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    NavigationView navigation;
    TextView fare, dist, et1, et2;
    LatLng latLng, latLng3;
    static final LatLng delhilatLng = new LatLng(28.7041, 77.1025);
    private GoogleMap mMap;
    double a, b, c, d, e;
    int x, z;

    String contact, rate, pdd1, pdd2;

    public static final String MY_PREFS_NAME = "MyPrefsFile";
    private ProgressDialog loading;
    private static final String REGISTER_URL = "http://gov.net16.net/GrvCabs/Booking.php";

    public static final String KEY_ID = "id";
    public static final String KEY_CONTACT = "Customer";
    public static final String KEY_FROM = "Source";
    public static final String KEY_TO = "Destination";
    public static final String KEY_TIME = "TimeOfBooking";
    public static final String key_STATUS="Status";

    String Booked= "Booked";

    public void BookNow(View view) {
        loading = ProgressDialog.show(this, "Please wait...", "Processing...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.contains("Successfully")){
                            loading.dismiss();
                            Intent intent= new Intent(MainPage.this, Booking.class);
                            startActivity(intent);
                        }else if(response.contains("Unsuccessfull")){
                            loading.dismiss();
                            Toast.makeText(getBaseContext(), "Only One Booking allowed at a Time...",Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        Toast.makeText(MainPage.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(KEY_ID, "1");
                params.put(KEY_CONTACT, contact);
                params.put(KEY_FROM, pdd1);
                params.put(KEY_TO, pdd2);
                params.put(KEY_TIME, DateFormat.getDateTimeInstance().format(new Date()));
                params.put(key_STATUS, Booked);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }



    public void CheckFare(View view) {
        e = distance(a, b, c, d);
        x = (int) e;
        String i = Integer.toString(x);
        dist.setText(i + "Km");
        fare.setText(checkFare(x) + "INR");
    }

    public String checkFare(int y) {
        if (z == 0) {

            int fare = (y * 12) + 100;
            rate = Integer.toString(fare);
        } else if (z == 1) {

            int fare = (y * 10) + 100;
            rate = Integer.toString(fare);
        } else if (z == 2) {

            int fare = 100 + (y * 8);
            rate = Integer.toString(fare);
        }
        return rate;
    }


    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }


    public void findPlace(View view) {
        try {
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).build(this);
            startActivityForResult(intent, 1);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
    }

    public void findPlace2(View view) {
        try {
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).build(this);
            startActivityForResult(intent, 2);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
    }


    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        parent.getItemAtPosition(pos);
        if (pos == 0) {
            z = 0;
        }
        if (pos == 1) {
            z = 1;
        }
        if (pos == 2) {
            z = 2;
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }


    private void initInstances() {

        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(tb);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        ab.setHomeButtonEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);


        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawerToggle = new ActionBarDrawerToggle(MainPage.this, drawerLayout, R.string.app_name, R.string.app_name);
        drawerLayout.setDrawerListener(drawerToggle);

        navigation = (NavigationView) findViewById(R.id.navigation_view);


        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {

                    case R.id.FeedBack:
                        Intent intent3 = new Intent(MainPage.this, Feedback.class);
                        startActivity(intent3);
                        break;
                    case R.id.MyBookings:
                        Intent il = new Intent(MainPage.this, Booking.class);
                        startActivity(il);
                        break;
                    case R.id.Logout:
                        finish();
                        Intent intent2 = new Intent(MainPage.this, MainActivity.class);
                        startActivity(intent2);
                        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                        editor.clear();
                        editor.commit();
                        finish();
                        break;
                    case R.id.RateUs:
                        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                        } catch (android.content.ActivityNotFoundException anfe) {

                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                        }
                        break;
                }
                return false;
            }
        });

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        initInstances();



        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.cars_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        View v = navigationView.getHeaderView(0);
        View v2 = navigationView;

        et1 = (TextView) findViewById(R.id.edt1);
        et2 = (TextView) findViewById(R.id.edt2);
        fare = (TextView) findViewById(R.id.fare);
        dist = (TextView) findViewById(R.id.dist);
        // Session class instance

        try {
            if (mMap == null) {
                mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.the_map)).getMap();
                UiSettings mapsettings;
                mapsettings = mMap.getUiSettings();
                mapsettings.setAllGesturesEnabled(true);
                ;
                CameraPosition cameraPosition = new CameraPosition.Builder().target(delhilatLng).zoom(10).build();
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        SharedPreferences sd = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        contact = sd.getString("number", "");

    }



    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finish();

            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press BACK again to Logout...", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }


    // A place has been received; use requestCode to track the request.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                // retrive the data by using getPlace() method.
                Place place = PlaceAutocomplete.getPlace(this, data);
                pdd1 = place.getName().toString().trim();
                latLng = place.getLatLng();
                try {
                    if (mMap == null) {
                        mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.the_map)).getMap();
                        UiSettings mapsettings;
                        mapsettings = mMap.getUiSettings();
                        mapsettings.setAllGesturesEnabled(false);
                        mapsettings.setZoomControlsEnabled(false);
                    }
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    Marker TP = mMap.addMarker(new MarkerOptions().position(latLng).title(place.getName().toString()));
                    CameraPosition cameraPosition = new CameraPosition.Builder().
                            target(latLng).zoom(17).build();
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    et1.setText("Source: " + place.getName().toString());
                    a = place.getLatLng().latitude;
                    b = place.getLatLng().longitude;


                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.e("Tag", status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }

        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                Log.d("Tag", "Tag");
                // retrive the data by using getPlace() method.
                Place place2 = PlaceAutocomplete.getPlace(this, data);
                pdd2 = place2.getName().toString().trim();
                latLng3 = place2.getLatLng();
                et2.setText("Destination: " + place2.getName().toString());
                c = place2.getLatLng().latitude;
                d = place2.getLatLng().longitude;
                Marker TP2 = mMap.addMarker(new MarkerOptions().position(latLng3).title(place2.getName().toString()));
                CameraPosition cameraPosition = new CameraPosition.Builder().
                        target(latLng3).zoom(17).build();
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            }
        } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
            Status status = PlaceAutocomplete.getStatus(this, data);
            // TODO: Handle the error.
            Log.e("Tag", status.getStatusMessage());
        } else if (resultCode == RESULT_CANCELED) {
            // The user canceled the operation.
        }
    }
}


