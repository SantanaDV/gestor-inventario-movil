package com.wul4.paythunder.gestorInventario.utils.interfaces;

import com.wul4.paythunder.gestorInventario.entities.Producto;
import com.wul4.paythunder.gestorInventario.entities.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * clase ApiHome para manejar solicitudes de autenticación, desde la API REST.
 * Para ello utilizamos Retrofit para hacer llamadas a una API REST.
 */

public interface ApiHome {
    @GET("api/producto/productosContados") //tira de mi lista de produtos y los cuenta
    Call<Integer> getproductosContados();

    @GET("api/producto/conExistencias")
    Call<List<Producto>> getlistarConexistencias();

    @GET("api/producto/conFaltantes")
    Call<List<Producto>> getlistarConFaltantes();


    @GET("api/usuario/usuariosActivos")
    Call<List<Usuario>> getlistaTotal_usuarios();


    Call<List<Producto>> getProductos();
}
