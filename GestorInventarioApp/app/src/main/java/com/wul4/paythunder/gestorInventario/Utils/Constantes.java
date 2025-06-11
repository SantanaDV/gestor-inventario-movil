package com.wul4.paythunder.gestorInventario.utils;

import android.view.View;

public class Constantes {
    public static final String PREFERENCES_NAME = "preference";
    public static final String TOKEN_KEY        = "token";
    public static final String MODO_KIOSCO = "modoKiosko";
    /** CAMBIAR EL NOMBRE DEL DIRECTORIO EN FUNCIÓN DE LA APP */
    public static final String LOGS_FOLFER_NAME = "PTHelloWorldLogs";

    public static final int uiOptions =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE
                    | View.SYSTEM_UI_FLAG_LOW_PROFILE;
}