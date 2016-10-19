package com.grv.grvcabs;

/**
 * Created by Gaurav on 24/08/2016.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashMap;

public class UserSessionManager {

    // Shared Preferences reference
    SharedPreferences pref;
    SharedPreferences pref2;
    // Editor reference for Shared preferences
    Editor editor;
    Editor editor2;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREFER_NAME = "AndroidExamplePref";

    // All Shared Preferences Keys
    private static final String IS_USER_LOGIN = "IsUserLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";

    // Email address (make variable public to access from outside)
    public static final String KEY_PHONE_NUMBER = "number";
    public static final String KEY_PASSWORD = "password";


    // Constructor
    public UserSessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    //Create login session
    public void createUserLoginSession(String name, String number) {
        // Storing login value as TRUE
        editor.putBoolean(IS_USER_LOGIN, true);

        // Storing name in pref
        editor.putString(KEY_NAME, name);

        // Storing email in pref
        editor.putString(KEY_PHONE_NUMBER, number);

        // commit changes
        editor.commit();
    }


    /**
     * Check login method will check user login status
     * If false it will redirect user to login page
     * Else do anything
     */
    public boolean checkLogin() {

        if (this.isUserLoggedIn()) {

            return true;
        }
        return false;
    }

    public String retrieveName() {

        return pref.getString(KEY_NAME, "");
    }

    public String retrieveNumber() {

        return pref.getString(KEY_PHONE_NUMBER, "");
    }

    /**
     * Get stored session data
     */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        pref.getString(KEY_NAME, null);
        pref.getString(KEY_PHONE_NUMBER, null);

        return user;


    }


    public void clearUser() {
        editor.clear();
        editor.commit();
    }


    // Check for login
    public boolean isUserLoggedIn() {
        return pref.getBoolean(IS_USER_LOGIN, false);
    }
}
