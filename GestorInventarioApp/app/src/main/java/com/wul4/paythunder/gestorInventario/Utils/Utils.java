package com.wul4.paythunder.gestorInventario.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;


import androidx.annotation.Nullable;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.fragment.app.Fragment;
import androidx.multidex.BuildConfig;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.wul4.paythunder.gestorInventario.R;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Utils {
    public static final int LOG_PRINTLN = 0, LOG_WTF = 1, LOG_VERBOSE = 2, LOG_DEBUG = 3,
            LOG_INFO = 4, LOG_WARN = 5, LOG_ERROR = 6;

    public static SharedPreferences preferences = null;
    public static SharedPreferences.Editor prefEditor = null;

    public static void write(String tag, String message) {
        /** Write clásico. */
        write(null, null, tag, message, null);
    }

    public static void write(String tag, String message, Throwable tr) {
        /** Write clásico con Throwable. */
        write(null, null, tag, message, tr);
    }

    public static void write(int logType, String tag, String message) {
        /** Write con tipo de Log. */
        write(logType, null, tag, message, null);
    }

    public static void write(int logType, String tag, String message, Throwable tr) {
        /** Write con tipo de Log y Throwable. */
        write(logType, null, tag, message, tr);
    }

    public static void write(int logType, int priority, String tag, String message) {
        /** Write con tipo de Log println. */
        write(logType, priority, tag, message, null);
    }

    public static void write(Integer logType, Integer priority, String tag, String message, Throwable tr) {
        /** Write completo. */
        if (BuildConfig.DEBUG) {
            try {
                if (tag == null) {
                    tag = "___";
                }
                if (message == null) {
                    message = "Message null";
                }
                if (logType != null) {
                    switch (logType) {
                        case LOG_PRINTLN: {
                            Log.println(priority, tag, message);
                        }
                        break;
                        case LOG_WTF: {
                            if (tr != null) {
                                Log.wtf(tag, message, tr);
                                tr.printStackTrace();
                            } else {
                                Log.wtf(tag, message);
                            }
                        }
                        break;
                        case LOG_VERBOSE: {
                            if (tr != null) {
                                Log.v(tag, message, tr);
                                tr.printStackTrace();
                            } else {
                                Log.v(tag, message);
                            }
                        }
                        break;
                        case LOG_DEBUG: {
                            if (tr != null) {
                                Log.d(tag, message, tr);
                                tr.printStackTrace();
                            } else {
                                Log.d(tag, message);
                            }
                        }
                        break;
                        case LOG_INFO: {
                            if (tr != null) {
                                Log.i(tag, message, tr);
                                tr.printStackTrace();
                            } else {
                                Log.i(tag, message);
                            }
                        }
                        break;
                        case LOG_WARN: {
                            if (tr != null) {
                                Log.w(tag, message, tr);
                                tr.printStackTrace();
                            } else {
                                Log.w(tag, message);
                            }
                        }
                        break;
                        case LOG_ERROR: {
                            if (tr != null) {
                                Log.e(tag, message, tr);
                                tr.printStackTrace();
                            } else {
                                Log.e(tag, message);
                            }
                        }
                        break;
                        default: {
                            if (tr != null) {
                                Log.e(tag, message, tr);
                                tr.printStackTrace();
                            } else {
                                Log.e(tag, message);
                            }
                        }
                        break;
                    }
                } else {
                    // Log clásico...
                    if (tr != null) {
                        // ...con Throwable.
                        Log.e(tag, message, tr);
                        tr.printStackTrace();
                    } else {
                        // ...sin Throwable.
                        Log.e(tag, message);
                    }
                }
                if (tr != null) {
                    writeInLogFile(tag, message + "Throwable: " + tr.getMessage());
                } else {
                    writeInLogFile(tag, message);
                }
            } catch (Throwable th) {
                Log.e("Error", th.getMessage());
            }
        }
    }

    public static void writeInLogFile(String tag, String message) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        Date fechaD = new Date();
        String fechaS = dateFormat.format(fechaD);
        String logWritter = fechaD.toString() + ": " + tag + " --> " + message + "\n";
        try {
//            File root = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), com.wul4.paythunder.gestorInventario.utils.Constantes.LOGS_FOLFER_NAME);
            File root = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), Constantes.LOGS_FOLFER_NAME);
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, fechaS + ".txt");
            FileWriter writer = null;
            if (gpxfile.exists()) {
                writer = new FileWriter(gpxfile, true);
            } else {
                writer = new FileWriter(gpxfile);
            }
            writer.append(logWritter);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * return ArrayList<String>
     */
    public static ArrayList<String> getAllRunningApps(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Activity.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(Integer.MAX_VALUE);
        ArrayList<String> allRunningApps = new ArrayList<String>();
        for (int i = 0; i < list.size(); i++) {
            allRunningApps.add(list.get(i).baseActivity.getPackageName());
        }
        return allRunningApps;
    }

    /**
     * return ArrayList<String>
     */
    public static ArrayList<String> getAllRunningServices(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Activity.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> list = am.getRunningServices(Integer.MAX_VALUE);
        ArrayList<String> allRunningServices = new ArrayList<String>();
        for (int i = 0; i < list.size(); i++) {
            allRunningServices.add(list.get(i).service.getPackageName());
        }
        return allRunningServices;
    }

    public static boolean isAppRunning(String packageName, Context context) {
        return getAllRunningApps(context).contains(packageName);
    }

    public static boolean isServiceRunning(String packageName, Context context) {
        return getAllRunningServices(context).contains(packageName);
    }

    public static GradientDrawable getPTGradientDrawable() {
        int[] gradientColors = new int[]{
                Color.parseColor("#D60B51") /*ptRed*/,
                Color.parseColor("#662482") /*ptPurple*/,
                Color.parseColor("#662482") /*ptPurple*/,
                Color.parseColor("#009EE2") /*ptBlue*/,
                Color.parseColor("#009EE2") /*ptBlue*/,
                Color.parseColor("#D60B51") /*ptRed*/
        };

        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.OVAL);
        gradientDrawable.setGradientType(GradientDrawable.SWEEP_GRADIENT);
        gradientDrawable.setColors(gradientColors);
        return gradientDrawable;
    }

    public static void setFullscreen(Activity activity) {
        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    /** PARA LA PANTALLA COMPLETA **/
                    Window window = activity.getWindow();
                    window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

                    View decorView = window.getDecorView();
                    decorView.setSystemUiVisibility(com.wul4.paythunder.gestorInventario.utils.Constantes.uiOptions);
                    decorView.setSystemUiVisibility(Constantes.uiOptions);

                    // https://developer.android.com/develop/ui/views/layout/immersive?hl=es-419#java

                    WindowInsetsControllerCompat windowInsetsController =
                            WindowCompat.getInsetsController(activity.getWindow(), activity.getWindow().getDecorView());
                    // Configure the behavior of the hidden system bars.
                    windowInsetsController.setSystemBarsBehavior(
                            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                    );

                    // Add a listener to update the behavior of the toggle fullscreen button when
                    // the system bars are hidden or revealed.
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
                        decorView.setOnApplyWindowInsetsListener((view, windowInsets) -> {
                            // You can hide the caption bar even when the other system bars are visible.
                            // To account for this, explicitly check the visibility of navigationBars()
                            // and statusBars() rather than checking the visibility of systemBars().
                            windowInsetsController.hide(WindowInsetsCompat.Type.systemBars());

                            return view.onApplyWindowInsets(windowInsets);
                        });
                    }
                    /** **/
                }
            });
        }
    }

    /**
     * Sobrecarga de metodo para establecer la pantalla completa en un fragmento
     * validando que el fragmento no sea nulo y que la actividad no sea nula
     *
     * @param fragment
     */

    public static void setFullscreen(Fragment fragment) {
        if (fragment != null && fragment.getActivity() != null) {
            setFullscreen(fragment.getActivity());
        }
    }

    public static void cargaDeImagenesConReintento(Fragment fragment, ImageView imageView, String url, int intentosRestantes) {

        Glide.with(fragment)
                .load(url)
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        if (intentosRestantes > 0) {
                            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                                cargaDeImagenesConReintento(fragment, imageView, url, intentosRestantes - 1);
                            }, 3000);
                        }
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(imageView);
    }

    public static void cargaDeImagenesConReintento(Context context, ImageView imageView, String url, int intentosRestantes) {

        Glide.with(context)
                .load(url)
                .override(Target.SIZE_ORIGINAL) // o puedes omitirlo si ya defines tamaño por layout
                .fitCenter()
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        if (intentosRestantes > 0) {
                            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                                cargaDeImagenesConReintento(context, imageView, url, intentosRestantes - 1);
                            }, 3000);
                        }
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(imageView);
    }
}
