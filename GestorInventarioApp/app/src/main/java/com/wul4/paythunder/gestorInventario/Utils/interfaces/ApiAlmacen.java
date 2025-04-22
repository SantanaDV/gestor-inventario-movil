package com.wul4.paythunder.gestorInventario.utils.interfaces;

import com.wul4.paythunder.gestorInventario.entities.Producto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Interfaz ApiAlmacen para manejar solicitudes de autenticación, desde la API REST.
 * Para ello utilizamos Retrofit para hacer llamadas a una API REST.
 */
public interface ApiAlmacen {
    @GET("api/producto")
    Call<List<Producto>> getProductos();
}
