package com.wul4.paythunder.gestorInventario.response;

import android.util.Log;

import androidx.annotation.NonNull;

import com.wul4.paythunder.gestorInventario.entities.Producto;
import com.wul4.paythunder.gestorInventario.entities.Usuario;
import com.wul4.paythunder.gestorInventario.utils.interfaces.ApiHome;
import com.wul4.paythunder.gestorInventario.fragments.home.HomeViewModel;
//import com.wul4.paythunder.gestorInventario.request.ApiHome;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * La clase HomeResponse se encarga de hacer las solicitudes a la API y asignar los datos al ViewModel.
 * Hacer las llamadas a tu API REST usando Retrofit.
 * Actualizar los valores en tu HomeViewModel según la respuesta que reciba.
 */
public class HomeResponse {

    private final HomeViewModel homeViewModel;
    private final ApiHome apiHome;


    public HomeResponse(HomeViewModel homeViewModel, ApiHome apiHome) {
        this.homeViewModel = homeViewModel;
        this.apiHome = apiHome;
    }

    public void fetchAllData() {
        // Hacer las solicitudes a la API
        fetchAllDataProductos();

    }

    private void fetchAllDataProductos() {

        apiHome.getProductos().enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(@NonNull Call<List<Producto>> call, @NonNull Response<List<Producto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    int sumaProductos = 0;
                    for (Producto producto : response.body()) {
                        sumaProductos++;
                    }
                    homeViewModel.setProductos(sumaProductos);
                } else {
                    homeViewModel.setProductos(-1);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Producto>> call, @NonNull Throwable t) {
                homeViewModel.setProductos(-1);
            }
        });

        apiHome.getConexistencias().enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(@NonNull Call<List<Producto>> call, @NonNull Response<List<Producto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    int sumaProductos = 0;
                    for (Producto producto : response.body()) {
                        if ("activo".equalsIgnoreCase(producto.getEstado())) {
                            sumaProductos++;

                        }
                        homeViewModel.setConexistencias(sumaProductos); // Pasás solo el número al ViewModel
                    }
                } else {
                    // Manejar el caso en que la respuesta no sea exitosa
                    homeViewModel.setConexistencias(-1); // Valor por defecto en caso de error
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Producto>> call, @NonNull Throwable t) {
                homeViewModel.setConexistencias(-1);
            }
        });

        apiHome.getConfaltantes().enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(@NonNull Call<List<Producto>> call, @NonNull Response<List<Producto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    int sumaProductos = 0;
                    for (Producto producto : response.body()) {
                        if ("desactivado".equalsIgnoreCase(producto.getEstado())) {
                            sumaProductos++;
                        }
                        homeViewModel.setConfaltantes(sumaProductos);
                    }
                } else {
                    homeViewModel.setConfaltantes(-1);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Producto>> call, @NonNull Throwable t) {
                homeViewModel.setConfaltantes(-1);
            }
        });

        apiHome.getTotal_usuarios().enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(@NonNull Call<List<Usuario>> call, @NonNull Response<List<Usuario>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    int sumaUsuarios = 0;
                    for (Usuario usuario : response.body()) {
                        if ("null".equalsIgnoreCase(usuario.getFecha_baja())) {
                            sumaUsuarios++;
                        }
                        homeViewModel.setConfaltantes(sumaUsuarios);
                    }
                } else {
                    homeViewModel.setConfaltantes(-1);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Usuario>> call, @NonNull Throwable t) {
                homeViewModel.setTotal_usuarios(-1);
            }
        });
    }
}











