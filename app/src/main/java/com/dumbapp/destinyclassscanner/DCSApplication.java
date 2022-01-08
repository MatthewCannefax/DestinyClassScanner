package com.dumbapp.destinyclassscanner;

import android.app.Application;
import android.content.Context;

public class DCSApplication extends Application {
    private DCSApplicationComponent dcsApplicationComponent;
    private static DCSApplication appContext;
    private static DCSApplication tInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        tInstance = this;
        appContext = this;
        dcsApplicationComponent = DaggerDCSApplicationComponent.builder().build();
    }

    public DCSApplicationComponent getDCSApplicationComponent() {
        return dcsApplicationComponent;
    }

    public static DCSApplication getInstance() {
        return tInstance;
    }

    public static Context getDCSApplicationContext() {
        return appContext.getApplicationContext();
    }
}
