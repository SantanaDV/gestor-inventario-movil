package com.wul4.paythunder.gestorInventario.utils.interfaces;

import com.wul4.paythunder.gestorInventario.entities.Tarea;
import com.wul4.paythunder.gestorInventario.utils.dto.TareaCategoriaDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * clase ApiTarea para manejar solicitudes de autenticación, desde la API REST.
 * Para ello utilizamos Retrofit para hacer llamadas a una API REST.
 */

public interface ApiTarea {

    @GET("api/tarea/totalTareas")
    Call<List<Tarea>> gettotalTareas();



    @POST("api/tarea/eliminarTarea")
    Call<Tarea> eliminarTarea(@Body int id);

//    @POST("api/tarea/actualizarTarea")
//    Call<Tarea> actualizarTarea(Tarea id);

    @PUT("api/tarea/{id}")
    Call<Tarea> actualizarTarea(@Body TareaCategoriaDTO tarea);





    @GET("api/tarea/listarTareaRealizada")
    Call<List<Tarea>> getlistarTareaRealizada();
    @GET("api/tarea/listarTareaHacer")
    Call<List<Tarea>> getlistarTareaHacer();
    @GET("api/tarea/listarTareaProceso")
    Call<List<Tarea>> getListarTareaProceso();



    @POST("api/tarea/crearTarea")
    Call<Tarea> crearTarea(TareaCategoriaDTO nuevaTarea);
//    @POST("api/tarea/crearTarea")
//    Call<Tarea> getCrearTarea(TareaCategoriaDTO tareaDTO);
}
