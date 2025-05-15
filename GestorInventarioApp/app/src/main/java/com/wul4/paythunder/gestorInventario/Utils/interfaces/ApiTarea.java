package com.wul4.paythunder.gestorInventario.utils.interfaces;

import com.wul4.paythunder.gestorInventario.entities.Tarea;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * clase ApiTarea para manejar solicitudes de autenticación, desde la API REST.
 * Para ello utilizamos Retrofit para hacer llamadas a una API REST.
 */

public interface ApiTarea {
//    @POST("api/tarea/guardarTarea")
//    Call<Tarea> getguardarTarea();
    @GET("api/tarea/totalTareas")
    Call<List<Tarea>> gettotalTareas();

    @GET("api/tarea/tareasHacer")
    Call<List<Tarea>> gettareasHacer();

    @GET("api/tarea/tareasProceso")
    Call<List<Tarea>> gettareasProceso();

    @GET("api/tarea/tareasRealizadas")
    Call<List<Tarea>> gettareasRealizadas();


    @POST("api/tarea/crearTarea")  // Endpoint para crear una nueva tarea, por ello utilizamos @POST en vez de @GET
    Call<Tarea>crearTarea(@Body Tarea nuevaTarea);  // Usamos @Body para enviar el objeto en el cuerpo

    @POST("api/tarea/eliminarTarea")
    Call<Tarea> eliminarTarea(int id);

    @POST("api/tarea/actualizarTarea")
    Call<Tarea> actualizarTarea(Tarea id);

    @POST("api/tarea/crearTareas")
    Call<List<Tarea>> getCrearTareas();
}
