package com.example.myshopee.MyUtils;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.text.DecimalFormat;

import DAO.AccountDAO;
import Model.User;

public class CommonUtils {
    public static User getCurrentUser(Context context) {
        PreferenceManager preferenceManager = new PreferenceManager(context);
        Gson gson = new Gson();
        String jsonToObject = preferenceManager.getString("current_user");
        User currentUser = gson.fromJson(jsonToObject, User.class);
        // Need to get newest information from database
        AccountDAO accountDAO = new AccountDAO(context);
        User newestInformation = accountDAO.getUserById(currentUser.getUserId());

        return newestInformation;
    }

    public static int getImageId(Context context, String imageName) {
        Log.i("[PETER MESSAGE]", "Image source:  " + imageName);
        Log.i("[PETER MESSAGE]", "Image index:  " + context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName()));
        return context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName());
    }

    public static String getReadableCostFromDouble(double price) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(price);
    }
}
