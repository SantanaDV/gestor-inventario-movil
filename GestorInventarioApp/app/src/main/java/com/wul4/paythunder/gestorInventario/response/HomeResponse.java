package com.wul4.paythunder.gestorInventario.response;

import android.util.Log;

import androidx.annotation.NonNull;

import com.wul4.paythunder.gestorInventario.entities.Producto;
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

    public void fetchAllData(){
        // Hacer las solicitudes a la API
        fetchProductos(); //total de productos
        fetchconexistencias(); // productos con existencias
        fetchconfaltantes(); // productos sin existencia
        fetchtotal_usuarios(); //cantidad total de usuarios
    }

    private void fetchAllDataProductos(){
        Call <ListProductos>>
    }

//    public void fetchData() {
//        // Hacer las solicitudes a la API
//        fetchProductos(); //total de productos
//        fetchconexistencias(); // productos con existencias
//        fetchconfaltantes(); // productos sin existencia
//        fetchtotal_usuarios(); //cantidad total de usuarios
//    }
//
//
//    //aquí cuento cuantos productos llegaron
//
//    private void fetchProductos() {
//        apiHome.getProductos().enqueue(new Callback<List<Producto>>() {
//            @Override
//            public void onResponse(@NonNull Call<List<Producto>> call, @NonNull Response<List<Producto>> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    homeViewModel.setProductos(response.body().size());
//                } else {
//                    homeViewModel.setProductos(-1);
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<List<Producto>> call, @NonNull Throwable t) {
//                homeViewModel.setProductos(-1);
//            }
//        });
//    }
//
//
//
//    private void fetchconexistencias() {
//        apiHome.getConexistencias().enqueue(new Callback<List<Producto>>() {
//            @Override
//            public void onResponse(@NonNull Call<List<Producto>> call, @NonNull Response<List<Producto>> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    int cantidad = response.body().size(); // Contás los productos
//                    homeViewModel.setConexistencias(cantidad); // Pasás solo el número al ViewModel
//                } else {
//                    // Manejar el caso en que la respuesta no sea exitosa
//                    homeViewModel.setConexistencias(-1); // Valor por defecto en caso de error
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<List<Producto>> call, @NonNull Throwable t) {
//                homeViewModel.setConexistencias(-1);
//            }
//        });
//    }
//
//
//    private void fetchconfaltantes() {
//        apiHome.getConfaltantes().enqueue(new Callback<Integer>() {
//            @Override
//            public void onResponse(@NonNull Call<Integer> call, @NonNull Response<Integer> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    homeViewModel.setConfaltantes(response.body());
//                } else {
//                    // Manejar el caso en que la respuesta no sea exitosa
//                    homeViewModel.setConfaltantes(-1); // Valor por defecto en caso de error
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<Integer> call, @NonNull Throwable t) {
//                // Manejar el error
//                homeViewModel.setConfaltantes(-1); // Valor por defecto en caso de error
//            }
//        });
//    }
//
//    private void fetchtotal_usuarios() {
//        apiHome.getTotal_usuarios().enqueue(new Callback<Integer>() {
//            @Override
//            public void onResponse(@NonNull Call<Integer> call, @NonNull Response<Integer> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    homeViewModel.setTotal_usuarios(response.body());
//                } else {
//                    // Manejar el caso en que la respuesta no sea exitosa
//                    homeViewModel.setTotal_usuarios(-1); // Valor por defecto en caso de error
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<Integer> call, @NonNull Throwable t) {
//                // Manejar el error
//                homeViewModel.setTotal_usuarios(-1); // Valor por defecto en caso de error
//            }
//        });
//    }
}
