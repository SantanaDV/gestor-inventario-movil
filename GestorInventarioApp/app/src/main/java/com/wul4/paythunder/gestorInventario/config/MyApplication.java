package com.wul4.paythunder.gestorInventario.config;

import android.content.Context;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

public class MyApplication extends MultiDexApplication {
    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    /** Devuelve el contexto de la app para usar en cualquier parte */
    public static MyApplication getInstance() {
        return instance;
    }
}
