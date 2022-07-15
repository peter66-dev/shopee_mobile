package com.example.myshopee.MyUtils;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import Model.User;

public class CommonUtils {
    public static User getCurrentUser(Context context) {
        PreferenceManager preferenceManager = new PreferenceManager(context);
        Gson gson = new Gson();
        String jsonToObject = preferenceManager.getString("current_user");
        User currentUser = gson.fromJson(jsonToObject, User.class);
        return currentUser;
    }
}
