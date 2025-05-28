package com.wul4.paythunder.gestorInventario.utils;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import com.wul4.paythunder.gestorInventario.config.MyApplication;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Clase que representa el cliente Retrofit para interactuar con la API.
 * Esta clase se utiliza para configurar y crear una instancia de Retrofit
 * que se puede utilizar para realizar solicitudes HTTP a la API
 * mediante el patron de diseño singleton (Solo creamos una unica instancia durante la ejecución del programa.
 */
public class ApiClient {

    private static final String BASE_URL   = "http://10.110.4.43:8080/";
    private static final String PREFS_NAME = "MyPrefs";
    private static final String TOKEN_KEY  = "token";

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            // 1) Logging
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            // 2) Interceptor que añade el token de SharedPreferences
            Interceptor authInterceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    // Aquí ya usamos el contexto de la aplicación garantizado no-nulo
                    String token = MyApplication
                            .getInstance()
                            .getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
                            .getString(TOKEN_KEY, "");

                    Request original = chain.request();
                    Request.Builder builder = original.newBuilder();

                    if (!token.isEmpty()) {
                        builder.addHeader("Authorization", "Bearer " + token);
                    }

                    Request modified = builder.build();
                    return chain.proceed(modified);
                }
            };

            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor(logging)
                    .addInterceptor(authInterceptor)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}