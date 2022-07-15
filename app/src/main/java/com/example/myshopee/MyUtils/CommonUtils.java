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

    public static int getImageId(Context context, String imageName) {
        Log.i("[PETER MESSAGE]", "Image source:  " + imageName);
        Log.i("[PETER MESSAGE]", "Image index:  " + context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName()));
        return context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName());
    }
}
