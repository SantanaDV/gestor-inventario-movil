package com.wul4.paythunder.gestorInventario.utils.interfaces;

import com.wul4.paythunder.gestorInventario.response.EstanteriaResponse;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface EstanteriaApi {
    /**
     * Devuelve todas las estanterías de un almacen dado.
     * @param idAlmacen el id del almacén
     */
    @GET("api/estanteria/{idAlmacen}")
    Call<List<EstanteriaResponse>> getEstanterias(
            @Path("idAlmacen") int idAlmacen
    );
}
