package com.wul4.paythunder.gestorInventario.response;

import androidx.annotation.NonNull;

import com.wul4.paythunder.gestorInventario.Utils.ApiHome;
import com.wul4.paythunder.gestorInventario.home.HomeViewModel;
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
        fetchTotalProductos();
        fetchExistencias();
        fetchFaltantes();
        fetchUsuariosActivos();
    }

    private void fetchTotalProductos() {
        apiHome.getTotalProductos().enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(@NonNull Call<Integer> call, @NonNull Response<Integer> response) {
                if (response.isSuccessful() && response.body() != null) {
                    homeViewModel.setTotalProductos(response.body());
                } else {
                    // Manejar el caso en que la respuesta no sea exitosa
                    homeViewModel.setTotalProductos(-1); // Valor por defecto en caso de error
                }
            }

            @Override
            public void onFailure(@NonNull Call<Integer> call, @NonNull Throwable t) {
                // Manejar el error
                homeViewModel.setTotalProductos(-1); // Valor por defecto en caso de error
            }
        });
    }

    private void fetchExistencias() {
        apiHome.getExistencias().enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(@NonNull Call<Integer> call, @NonNull Response<Integer> response) {
                if (response.isSuccessful() && response.body() != null) {
                    homeViewModel.setExistencias(response.body());
                } else {
                    // Manejar el caso en que la respuesta no sea exitosa
                    homeViewModel.setExistencias(-1); // Valor por defecto en caso de error
                }
            }

            @Override
            public void onFailure(@NonNull Call<Integer> call, @NonNull Throwable t) {
                // Manejar el error
                homeViewModel.setExistencias(-1); // Valor por defecto en caso de error
            }
        });
    }

    private void fetchFaltantes() {
        apiHome.getFaltantes().enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(@NonNull Call<Integer> call, @NonNull Response<Integer> response) {
                if (response.isSuccessful() && response.body() != null) {
                    homeViewModel.setFaltantes(response.body());
                } else {
                    // Manejar el caso en que la respuesta no sea exitosa
                    homeViewModel.setFaltantes(-1); // Valor por defecto en caso de error
                }
            }

            @Override
            public void onFailure(@NonNull Call<Integer> call, @NonNull Throwable t) {
                // Manejar el error
                homeViewModel.setFaltantes(-1); // Valor por defecto en caso de error
            }
        });
    }

    private void fetchUsuariosActivos() {
        apiHome.getUsuariosActivos().enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(@NonNull Call<Integer> call, @NonNull Response<Integer> response) {
                if (response.isSuccessful() && response.body() != null) {
                    homeViewModel.setUsuariosActivos(response.body());
                } else {
                    // Manejar el caso en que la respuesta no sea exitosa
                    homeViewModel.setUsuariosActivos(-1); // Valor por defecto en caso de error
                }
            }

            @Override
            public void onFailure(@NonNull Call<Integer> call, @NonNull Throwable t) {
                // Manejar el error
                homeViewModel.setUsuariosActivos(-1); // Valor por defecto en caso de error
            }
        });
    }
}
