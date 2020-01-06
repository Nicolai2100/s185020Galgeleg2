package com.s185020.Galgeleg_3.logic;

import android.app.Application;
import android.util.Log;

public class Helper extends Application {


    private static final String TAG = "AD HELPER";
    public static Helper instance = null;

    public void onCreate() {
        super.onCreate();
        instance = this;
        Log.i(TAG, "onCreate");
    }

    public static Helper getInstance() {
        return instance;
    }
}