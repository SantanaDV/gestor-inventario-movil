package com.wul4.paythunder.gestorInventario.utils;

import okhttp3.OkHttpClient;
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

    // Reemplazar con la URL base la api (actualmente  maquina local)
//    private static final String BASE_URL = "http://10.110.4.196:8080/";
    private static final String BASE_URL = "http://10.110.4.43:8080/";


    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            // Construir el cliente OkHttp con el interceptor agregado
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build();


            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    //Mediante este metodo convertimos datos de JSON a objetos Java utilizando la biblioteca Gson
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}