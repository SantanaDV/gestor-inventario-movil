package com.wul4.paythunder.gestorInventario.utils.interfaces;

import com.wul4.paythunder.gestorInventario.entities.Producto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * clase HomeRequest para manejar estas solicitudes, desde la API REST.
 * Para ello utilizamos Retrofit para hacer llamadas a una API REST.
 */

public interface ApiHome {
    @GET("/listar") //tira de mi lista de produtos y los cuenta
    Call<List<Producto>> getProductos();

    @GET("/listar")
    Call<List<Producto>> getConexistencias();


//    @GET("//obtenerProductoQR/{codigo_qr}")
//    Call<List<Producto>> getConexistencias();


    @GET("api/faltantes")
    Call<Integer> getConfaltantes();

   // @GET("api/usuariosA")
   // Call<Integer> getUsuariosActivos();

    @GET("api/usuario/contarUsuarios")
    Call<Integer> getTotal_usuarios();


}
