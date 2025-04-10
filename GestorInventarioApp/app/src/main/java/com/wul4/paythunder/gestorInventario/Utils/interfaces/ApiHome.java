package com.wul4.paythunder.gestorInventario.utils.interfaces;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * clase HomeRequest para manejar estas solicitudes, desde la API REST.
 * Para ello utilizamos Retrofit para hacer llamadas a una API REST.
 */

public interface ApiHome {
    @GET("api/producto")
    Call<Integer> getProductos();

    //@GET("api/existencias")
    @GET("/obtenerProductoQR/{codigo_qr}")
    Call<Integer> getConexistencias();

    @GET("api/faltantes")
    Call<Integer> getConfaltantes();

   // @GET("api/usuariosA")
   // Call<Integer> getUsuariosActivos();

    @GET("api/usuario/contarUsuarios")
    Call<Integer> getTotal_usuarios();
}
