// app/src/main/java/com/wul4/paythunder/gestorInventario/utils/interfaces/ApiAlmacen.java
package com.wul4.paythunder.gestorInventario.utils.interfaces;

import com.wul4.paythunder.gestorInventario.entities.Categoria;
import com.wul4.paythunder.gestorInventario.entities.Producto;
import com.wul4.paythunder.gestorInventario.response.AlmacenResponse;
import com.wul4.paythunder.gestorInventario.response.EstanteriaResponse;
import com.wul4.paythunder.gestorInventario.response.ProductoResponse;
import com.wul4.paythunder.gestorInventario.utils.dto.AsignarProductosDTO;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.*;

/**
 * Interfaz única para todas las llamadas a tu backend:
 * - Gestión de almacenes y estanterías
 * - CRUD de productos (incluyendo subida de imagenes)
 * - Búsqueda por QR
 * - Asignación de productos a estanterías
 */
public interface ApiAlmacen {

    // --- ALMACENES ---
    @GET("api/almacen")
    Call<List<AlmacenResponse>> getAlmacenes();

    @POST("api/almacen")
    Call<AlmacenResponse> crearAlmacen(@Body AlmacenResponse almacen);

    @PUT("api/almacen")
    Call<AlmacenResponse> actualizarAlmacen(@Body AlmacenResponse almacen);

    @DELETE("api/almacen/{id}")
    Call<Void> borrarAlmacen(@Path("id") int idAlmacen);


    // --- ESTANTERÍAS ---
    @GET("api/estanteria")
    Call<List<EstanteriaResponse>> getTodasEstanterias();

    @GET("api/estanteria/{idAlmacen}")
    Call<List<EstanteriaResponse>> getEstanteriasPorAlmacen(@Path("idAlmacen") int idAlmacen);

    @POST("api/estanteria")
    Call<EstanteriaResponse> crearEstanteria(@Body EstanteriaResponse estanteria);

    @PUT("api/estanteria")
    Call<EstanteriaResponse> actualizarEstanteria(@Body EstanteriaResponse estanteria);

    @DELETE("api/estanteria/{id}")
    Call<Void> borrarEstanteria(@Path("id") int idEstanteria);


    // --- CATEGORÍAS Y PRODUCTOS (Java entities) ---
    @GET("api/categoria")
    Call<List<Categoria>> getCategorias();

    @GET("api/producto")
    Call<List<Producto>> getProductos();

    @GET("api/producto/obtenerProductoQR/{codigo_qr}")
    Call<Producto> getProductoPorQR(@Path("codigo_qr") String qr);


    // --- CRUD PRODUCTO CON IMAGEN (Multipart) ---
    @Multipart
    @POST("api/producto")
    Call<Producto> crearProducto(
            @Part("producto") RequestBody productoJson,
            @Part MultipartBody.Part imagen
    );

    @Multipart
    @PUT("api/producto")
    Call<Producto> actualizarProducto(
            @Part("producto") RequestBody productoJson,
            @Part MultipartBody.Part imagen
    );

    @DELETE("api/producto/{id}")
    Call<Void> borrarProducto(@Path("id") int idProducto);


    // --- PRODUCTOS (DTO response) para listados con filtros client-side ---
    @GET("api/producto")
    Call<List<ProductoResponse>> getAllProductosResponse();


    // --- ASIGNAR PRODUCTOS A ESTANTERÍA ---
    /**
     * Recibe { "id_estanteria": X, "ids_producto": [a,b,c] }
     */
    @PUT("api/producto/asignarEstanteria")
    Call<Void> asignarProductosAEstanteria(@Body AsignarProductosDTO dto);



}
