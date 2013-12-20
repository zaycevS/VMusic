package com.zaycev.vmusic.entity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.zaycev.vmusic.config.VMusicApp;

public class Account {

    private static final String PREFERENCES_ACCOUNT = "account";

    private static String accessToken;
    private static long userID;

    private static void save() {
        SharedPreferences sharedPreferences = VMusicApp.getContext().getSharedPreferences(PREFERENCES_ACCOUNT, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putString("accessToken", accessToken);
        editor.putLong("userID", userID);
        editor.commit();
    }

    private static void restore() {
        SharedPreferences sharedPreferences = VMusicApp.getContext().getSharedPreferences(PREFERENCES_ACCOUNT, Context.MODE_PRIVATE);
        accessToken = sharedPreferences.getString("accessToken", null);
        userID = sharedPreferences.getLong("userID", 0);
    }

    public static boolean isLogged() {
        restore();
        return (accessToken != null && userID != 0);
    }

    public static void saveAccount(long id, String token) {
        userID = id;
        accessToken = token;
        save();
    }

    public static String getAccessToken() {
        restore();
        return accessToken;
    }

    public static long getUserID() {
        restore();
        return userID;
    }

}