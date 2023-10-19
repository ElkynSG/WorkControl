package com.esilva.equiposunidos.application;

import android.app.Application;
import android.content.Context;
import android.util.Log;

public class UnidosApplication extends Application {
    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("DP_DLOG","onCreate "+"Application");
        appContext = getApplicationContext();

    }
}
