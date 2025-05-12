package com.wul4.paythunder.gestorInventario.response;

import androidx.annotation.NonNull;

import com.wul4.paythunder.gestorInventario.entities.Tarea;
import com.wul4.paythunder.gestorInventario.fragments.home.HomeViewModel;
import com.wul4.paythunder.gestorInventario.fragments.tareas.TareaViewModel;
import com.wul4.paythunder.gestorInventario.utils.interfaces.ApiHome;
import com.wul4.paythunder.gestorInventario.utils.interfaces.ApiTarea;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * La clase TareaResponse se encarga de hacer las solicitudes a la API y asignar los datos al ViewModel.
 * Hacer las llamadas a tu API REST usando Retrofit.
 * Actualizar los valores en tu TareaViewModel según la respuesta que reciba.
 */

public class TareaResponse {

    private final TareaViewModel tareaViewModel;
    private final ApiTarea apiTarea;

    public TareaResponse(TareaViewModel tareaViewModel, ApiTarea apiTarea) {
        this.tareaViewModel = tareaViewModel;
        this.apiTarea = apiTarea;
    }

    public void fetchAllData() {
        //hace solicitudes a la API
        fetchAllDataTareas();

    }

    private void fetchAllDataTareas() {

        apiTarea.gettotalTareas().enqueue(new Callback<List<Tarea>>() {
            @Override
            public void onResponse(@NonNull Call<List<Tarea>> call, @NonNull Response<List<Tarea>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    List<Tarea> tareasContadas = response.body();

                    tareaViewModel.getCuentaTareas().setValue(tareasContadas.size());
                    tareaViewModel.getTotalTareas().setValue(tareasContadas);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Tarea>> call, @NonNull Throwable t) {
                tareaViewModel.getTotalTareas().setValue(null);
            }
        });

        apiTarea.gettareasHacer().enqueue(new Callback<List<Tarea>>() {
            @Override
            public void onResponse(@NonNull Call<List<Tarea>> call, @NonNull Response<List<Tarea>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    List<Tarea> tareasContadasHacer = response.body();

                    tareaViewModel.getCuentaTareas().setValue(tareasContadasHacer.size());
                    tareaViewModel.getTareasHacer().setValue(tareasContadasHacer);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Tarea>> call, @NonNull Throwable t) {
                tareaViewModel.getTareasHacer().setValue(null);
            }
        });

        apiTarea.gettareasProceso().enqueue(new Callback<List<Tarea>>() {
            @Override
            public void onResponse(@NonNull Call<List<Tarea>> call, @NonNull Response<List<Tarea>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    List<Tarea> tareasContadasProceso = response.body();

                    tareaViewModel.getCuentaTareas().setValue(tareasContadasProceso.size());
                    tareaViewModel.getTareasHacer().setValue(tareasContadasProceso);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Tarea>> call, @NonNull Throwable t) {
                tareaViewModel.getTareasProceso().setValue(null);
            }
        });

        apiTarea.gettareasRealizadas().enqueue(new Callback<List<Tarea>>() {
            @Override
            public void onResponse(@NonNull Call<List<Tarea>> call, @NonNull Response<List<Tarea>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    List<Tarea> tareasContadasRealizadas = response.body();

                    tareaViewModel.getCuentaTareas().setValue(tareasContadasRealizadas.size());
                    tareaViewModel.getTareasRealizadas().setValue(tareasContadasRealizadas);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Tarea>> call, @NonNull Throwable t) {
                tareaViewModel.getTareasRealizadas().setValue(null);
            }
        });
    }
}
