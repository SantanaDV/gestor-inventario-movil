package com.wul4.paythunder.gestorInventario.interfaces;

import com.wul4.paythunder.gestorInventario.response.AlmacenResponse;
import com.wul4.paythunder.gestorInventario.response.EstanteriaResponse;
import com.wul4.paythunder.gestorInventario.response.ProductoResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import java.util.List;

public interface AlmacenApi {

    /** Obtiene todos los almacenes **/
    @GET("api/almacen")
    Call<List<AlmacenResponse>> getAlmacenes();

    /** Obtiene las estanterías de un almacén dado **/
    @GET("api/estanteria/{idAlmacen}")
    Call<List<EstanteriaResponse>> getEstanterias(
            @Path("idAlmacen") int idAlmacen
    );

    @GET("api/producto")
    Call<List<ProductoResponse>> getAllProductos();
}
