package com.wul4.paythunder.gestorInventario.response;

import android.util.Log;

import androidx.annotation.NonNull;

import com.wul4.paythunder.gestorInventario.entities.Tarea;
import com.wul4.paythunder.gestorInventario.fragments.tareas.TareaAdapter;
import com.wul4.paythunder.gestorInventario.fragments.tareas.TareaViewModel;
import com.wul4.paythunder.gestorInventario.utils.interfaces.ApiTarea;

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

//        apiTarea.getCrearTareas().enqueue(new Callback<List<Tarea>>() {
//
//            @Override
//            public void onResponse(@NonNull Call<List<Tarea>> call, @NonNull Response<List<Tarea>> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    List<Tarea> tareasCrear = response.body();
////                    tareaViewModel.setCrearTareas(tareasCrear.size());
//                    tareaViewModel.getCrearTareas().postValue(tareasCrear);
//                } else {
//
//                        tareaViewModel.getTareasCrear().postValue(null);
//                    }
//                }
//
//
//            @Override
//            public void onFailure(Call<List<Tarea>> call, Throwable t) {
//
//            }
//        });

            apiTarea.gettotalTareas().enqueue(new Callback<List<Tarea>>() {
            @Override
            public void onResponse(@NonNull Call<List<Tarea>> call, @NonNull Response<List<Tarea>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    List<Tarea> tareasContadas = response.body();

//                    tareaViewModel.settotalTareas(tareasContadas.size());
                    tareaViewModel.getTotalTareas().postValue(tareasContadas);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Tarea>> call, @NonNull Throwable t) {
                tareaViewModel.getTotalTareas().postValue(null);
            }
        });

        apiTarea.getlistarTareaHacer().enqueue(new Callback<List<Tarea>>() {
            @Override
            public void onResponse(@NonNull Call<List<Tarea>> call, @NonNull Response<List<Tarea>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    List<Tarea> tareasContadasHacer = response.body();

                    Log.d("API", "Tareas Hacer recibidas: " + tareasContadasHacer.size());

                    //tareaViewModel.setListarTareaHacer(tareasContadasHacer.size());
                    tareaViewModel.getListarTareaHacer().postValue(tareasContadasHacer);
                    if (tareasContadasHacer != null) TareaAdapter.setTareas(tareasContadasHacer);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Tarea>> call, @NonNull Throwable t) {
                tareaViewModel.getListarTareaHacer().postValue(null);
            }
        });

        apiTarea.getListarTareaProceso().enqueue(new Callback<List<Tarea>>() {
            @Override
            public void onResponse(@NonNull Call<List<Tarea>> call, @NonNull Response<List<Tarea>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    List<Tarea> tareasContadasProceso = response.body();

//                    tareaViewModel.setListarTareaProceso(tareasContadasProceso.size());
                    tareaViewModel.getListarTareaProceso().postValue(tareasContadasProceso);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Tarea>> call, @NonNull Throwable t) {
                tareaViewModel.getListarTareaProceso().postValue(null);
            }
        });

        apiTarea.getlistarTareaRealizada().enqueue(new Callback<List<Tarea>>() {
            @Override
            public void onResponse(@NonNull Call<List<Tarea>> call, @NonNull Response<List<Tarea>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    List<Tarea> tareasContadasRealizadas = response.body();

//                    tareaViewModel.setListarTareaRealizada(tareasContadasRealizadas.size());
                    tareaViewModel.getListarTareaRealizada().postValue(tareasContadasRealizadas);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Tarea>> call, @NonNull Throwable t) {
                tareaViewModel.getListarTareaRealizada().postValue(null);
            }
        });
    }
}
