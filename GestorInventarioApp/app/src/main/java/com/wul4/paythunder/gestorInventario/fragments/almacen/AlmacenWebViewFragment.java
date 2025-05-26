package com.wul4.paythunder.gestorInventario.fragments.almacen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.wul4.paythunder.gestorInventario.R;

import java.util.HashMap;
import java.util.Map;

public class AlmacenWebViewFragment extends Fragment {
    private static final String TAG        = "AWVFragment";
    private static final String PREFS_NAME = "MyPrefs";
    private static final String TOKEN_KEY  = "token";

    private WebView webView;
    private String authToken;
    private boolean tokenInjected = false;

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_almacen_webview, container, false);
        webView = root.findViewById(R.id.webview_almacen);

        // 1) Leer token desde SharedPreferences
        SharedPreferences prefs = requireContext()
                .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        authToken = prefs.getString(TOKEN_KEY, "");
        Log.d(TAG, "Token leído: " + authToken);

        setupWebView();

        // 2) Cargar el HTML estático desde assets/dist/index.html
        //    Esto mostrará tu SPA compilada, con CSS y JS incluidos
        webView.loadUrl("file:///android_asset/dist/index.html");

        return root;
    }

    @SuppressLint({"SetJavaScriptEnabled", "NewApi"})
    private void setupWebView() {
        WebSettings ws = webView.getSettings();

        // 3.1 – Permitir acceso a ficheros y a contenido de file://
        ws.setAllowFileAccess(true);
        ws.setAllowContentAccess(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ws.setAllowFileAccessFromFileURLs(true);
            ws.setAllowUniversalAccessFromFileURLs(true);
        }

        // 3.2 – JavaScript y DOM Storage
        ws.setJavaScriptEnabled(true);
        ws.setDomStorageEnabled(true);

        // 3.3 – Opcional: Mixed content (si tu API es HTTPS/HTTP mixto)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ws.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        webView.setWebViewClient(new WebViewClient() {
            // Errores legacy
            @Override @SuppressWarnings("deprecation")
            public void onReceivedError(WebView view, int code, String desc, String failingUrl) {
                Log.e(TAG, "Error (" + code + ") en " + failingUrl + ": " + desc);
            }
            // Errores modernos
            @Override
            public void onReceivedError(WebView view,
                                        WebResourceRequest req,
                                        WebResourceError err) {
                Log.e(TAG, "Error moderno en " + req.getUrl() + ": " + err.getDescription());
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap fav) {
                Log.d(TAG, "Página iniciada: " + url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d(TAG, "Página terminada: " + url);

                // 4) Inyectar token en localStorage UNA SOLA VEZ
                if (!tokenInjected && url.endsWith("dist/index.html")) {
                    tokenInjected = true;
                    String js = "javascript:(function(){" +
                            "try{ localStorage.setItem('authToken','" + authToken + "'); }" +
                            "catch(e){ console.error(e); }" +
                            "})()";

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        view.evaluateJavascript(js, value ->
                                Log.d(TAG, "Token inyectado en localStorage"));
                    } else {
                        view.loadUrl(js);
                    }
                }
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onConsoleMessage(ConsoleMessage cm) {
                Log.d(TAG + "_JS",
                        cm.message() +
                                " -- línea " + cm.lineNumber() +
                                " origen " + cm.sourceId());
                return super.onConsoleMessage(cm);
            }
        });
    }
}
