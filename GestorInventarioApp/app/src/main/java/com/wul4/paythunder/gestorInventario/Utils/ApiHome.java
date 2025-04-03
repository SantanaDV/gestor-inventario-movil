package com.wul4.paythunder.gestorInventario.Utils;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * clase HomeRequest para manejar estas solicitudes, desde la API REST.
 * Para ello utilizamos Retrofit para hacer llamadas a una API REST.
 */

public interface ApiHome {
    @GET("api/totalProductos")
    Call<Integer>getTotalProductos();

    @GET("api/existencias")
    Call<Integer> getExistencias();

    @GET("api/faltantes")
    Call<Integer> getFaltantes();

    @GET("api/usuariosActivos")
    Call<Integer> getUsuariosActivos();
}
