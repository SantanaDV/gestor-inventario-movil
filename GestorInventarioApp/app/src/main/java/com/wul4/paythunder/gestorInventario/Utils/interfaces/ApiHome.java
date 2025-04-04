package com.wul4.paythunder.gestorInventario.Utils.interfaces;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * clase HomeRequest para manejar estas solicitudes, desde la API REST.
 * Para ello utilizamos Retrofit para hacer llamadas a una API REST.
 */

public interface ApiHome {
    @GET("api/producto")
    Call<Integer>getTotalProductos();

    @GET("api/existencias")
    Call<Integer> getExistencias();

    @GET("api/faltantes")
    Call<Integer> getFaltantes();

   // @GET("api/usuariosA")
   // Call<Integer> getUsuariosActivos();

    @GET("api/usuario/contarUsuarios")
    Call<Integer> getContarUsuarios();
}
