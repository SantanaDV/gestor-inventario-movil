package com.wul4.paythunder.gestorInventario.response;

import android.util.Log;

import androidx.annotation.NonNull;

import com.wul4.paythunder.gestorInventario.entities.Producto;
import com.wul4.paythunder.gestorInventario.utils.interfaces.ApiHome;
import com.wul4.paythunder.gestorInventario.fragments.home.HomeViewModel;
//import com.wul4.paythunder.gestorInventario.request.ApiHome;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * La clase HomeResponse se encarga de hacer las solicitudes a la API y asignar los datos al ViewModel.
 */
public class HomeResponse {

    private final HomeViewModel homeViewModel;
    private final ApiHome apiHome;


    public HomeResponse(HomeViewModel homeViewModel, ApiHome apiHome) {
        this.homeViewModel = homeViewModel;
        this.apiHome = apiHome;
    }

    public void fetchData() {
        // Hacer las solicitudes a la API
        fetchProductos();
        fetchconexistencias();
        fetchconfaltantes();
        fetchtotal_usuarios();
    }


    private void fetchProductos() {
        apiHome.getProductos().enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(@NonNull Call<Integer> call, @NonNull Response<Integer> response) {
                if (response.isSuccessful() && response.body() != null) {
                    homeViewModel.setProductos(response.body());
                } else {
                    // Manejar el caso en que la respuesta no sea exitosa
                    //Log.e("HomeResponse", "Fallo: " + fetchProductos().getMessage());
                    homeViewModel.setProductos(-1); // Valor por defecto en caso de error
                }
            }

            @Override
            public void onFailure(@NonNull Call<Integer> call, @NonNull Throwable t) {
                // Manejar el error
                homeViewModel.setProductos(-1); // Valor por defecto en caso de error
            }
        });
    }

    private void fetchconexistencias() {
        apiHome.getConexistencias().enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(@NonNull Call<Integer> call, @NonNull Response<Integer> response) {
                if (response.isSuccessful() && response.body() != null) {
                    homeViewModel.setConexistencias(response.body());
                } else {
                    // Manejar el caso en que la respuesta no sea exitosa
                    homeViewModel.setConexistencias(-1); // Valor por defecto en caso de error
                }
            }

            @Override
            public void onFailure(@NonNull Call<Integer> call, @NonNull Throwable t) {
                // Manejar el error
                homeViewModel.setConexistencias(-1); // Valor por defecto en caso de error
            }
        });
    }

    private void fetchconfaltantes() {
        apiHome.getConfaltantes().enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(@NonNull Call<Integer> call, @NonNull Response<Integer> response) {
                if (response.isSuccessful() && response.body() != null) {
                    homeViewModel.setConfaltantes(response.body());
                } else {
                    // Manejar el caso en que la respuesta no sea exitosa
                    homeViewModel.setConfaltantes(-1); // Valor por defecto en caso de error
                }
            }

            @Override
            public void onFailure(@NonNull Call<Integer> call, @NonNull Throwable t) {
                // Manejar el error
                homeViewModel.setConfaltantes(-1); // Valor por defecto en caso de error
            }
        });
    }

    private void fetchtotal_usuarios() {
        apiHome.getTotal_usuarios().enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(@NonNull Call<Integer> call, @NonNull Response<Integer> response) {
                if (response.isSuccessful() && response.body() != null) {
                    homeViewModel.setTotal_usuarios(response.body());
                } else {
                    // Manejar el caso en que la respuesta no sea exitosa
                    homeViewModel.setTotal_usuarios(-1); // Valor por defecto en caso de error
                }
            }

            @Override
            public void onFailure(@NonNull Call<Integer> call, @NonNull Throwable t) {
                // Manejar el error
                homeViewModel.setTotal_usuarios(-1); // Valor por defecto en caso de error
            }
        });
    }
}
