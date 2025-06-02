package com.wul4.paythunder.gestorInventario.utils.interfaces;

import com.wul4.paythunder.gestorInventario.entities.Categoria;
import com.wul4.paythunder.gestorInventario.entities.Producto;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Interfaz ApiAlmacen para manejar solicitudes de autenticación, desde la API REST.
 * Para ello utilizamos Retrofit para hacer llamadas a una API REST.
 */
public interface ApiAlmacen {
    @GET("api/producto")
    Call<List<Producto>> getProductos();

    @GET("/api/categoria")
    Call<List<Categoria>> getCategorias();

    /**
     * Implementamos un Multipart ya que enviamos un json y imagen
     * @param productoJson
     * @param imagen
     * @return
     */
    @Multipart
    @POST("api/producto")
    Call<Producto> createOrUpdateProducto(
            @Part("producto") RequestBody productoJson,
            @Part MultipartBody.Part imagen  // si no hay imagen, pasaremos null
    );

    /**
     * Llamada al controlador para la busqueda por QR
     * @param qr
     * @return
     */
        @GET("api/producto/obtenerProductoQR/{codigo_qr}")
        Call<Producto> getProductoPorQR(@Path("codigo_qr") String qr);


    /**
     * Asignar varios productos a la misma estantería.
     * Recibe un JSON con { id_estanteria, ids_producto: [...] }.
     */
    @PUT("api/producto/asignarEstanteria")
    Call<Void> asignarProductosAEstanteria(@Body com.wul4.paythunder.gestorInventario.utils.dto.AsignarProductosDTO dto);
}


