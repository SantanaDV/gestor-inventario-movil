package com.wul4.paythunder.gestorInventario.utils.interfaces;

import com.wul4.paythunder.gestorInventario.entities.Tarea;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * clase ApiTarea para manejar solicitudes de autenticación, desde la API REST.
 * Para ello utilizamos Retrofit para hacer llamadas a una API REST.
 */

public interface ApiTarea {

    @GET("api/tarea/totalTareas")
    Call<List<Tarea>> gettotalTareas();

    @GET("api/tarea/tareasHacer")
    Call<List<Tarea>> gettareasHacer();

    @GET("api/tarea/tareasProceso")
    Call<List<Tarea>> gettareasProceso();

    @GET("api/tarea/tareasRealizadas")
    Call<List<Tarea>> gettareasRealizadas();
}
