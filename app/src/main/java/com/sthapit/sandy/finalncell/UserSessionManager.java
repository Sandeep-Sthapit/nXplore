package com.sthapit.sandy.finalncell;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by Dell on 11/23/2015.
 */
public class UserSessionManager {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;
    private int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "login";
    private static final String KEY_USER_NAME = "userName";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_IS_LOGIN = "isLogin";

    // Constructor
    @SuppressLint("CommitPrefEdits")
    public UserSessionManager(Context context) {
        this.context = context;
        pref = this.context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    //save user name to SharedPref
    public void setSavedUserName(String userName) {
        editor.putString(KEY_USER_NAME, userName);
        editor.commit();
    }

    //retrieve username frome pref
    public String getSavedUserName() {
        return pref.getString(KEY_USER_NAME, "");
    }

    public void setSavedPassword(String pass) {
        editor.putString(KEY_PASSWORD, pass);
        editor.commit();
    }

    public boolean isUserLogin() {
        return pref.getBoolean(KEY_IS_LOGIN, false);
    }

    public void setUserLoggedIn(boolean isLogin) {
        editor.putBoolean(KEY_IS_LOGIN, isLogin);
        editor.commit();
    }

    public void clearSession() {
        editor.clear();
        editor.commit();
    }
}
