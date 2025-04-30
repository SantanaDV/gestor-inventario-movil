package com.wul4.paythunder.gestorInventario.activities;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.ProgressBar;


import com.wul4.paythunder.gestorInventario.R;
import com.wul4.paythunder.gestorInventario.utils.Constantes;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreenActivity extends AppCompatActivity {

    public final String TAG = SplashScreenActivity.class.getSimpleName();
    public SharedPreferences preferences = null;
    public SharedPreferences.Editor prefEditor = null;
    ProgressBar progressBar;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setBackground(com.wul4.paythunder.gestorInventario.utils.Utils.getPTGradientDrawable());

        preferences = getSharedPreferences(Constantes.PREFERENCES_NAME, MODE_PRIVATE);
        prefEditor = preferences.edit();



        /** PARA QUE TARDE EN PASAR A LA MAIN ACTIVITY (CAMBIAR CUANDO REALMENTE HAGA COSAS) */
        start();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    private void start() {
        long time = 2 * 1000L;
        Timer timer = new Timer(true);
        timer.schedule(new FinishTask(), time);
    }


    private class FinishTask extends TimerTask {
        @Override
        public void run() {
            Intent activity = new Intent(SplashScreenActivity.this, MainActivity.class);
            startActivity(activity);
            SplashScreenActivity.this.finish();
        }
    }

    public boolean requestPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (
                    checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                            checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
            ) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }
}