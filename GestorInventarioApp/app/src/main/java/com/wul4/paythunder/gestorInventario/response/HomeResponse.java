package com.wul4.paythunder.gestorInventario.response;

import androidx.annotation.NonNull;

import com.wul4.paythunder.gestorInventario.entities.Producto;
import com.wul4.paythunder.gestorInventario.entities.Usuario;
import com.wul4.paythunder.gestorInventario.utils.interfaces.ApiHome;
import com.wul4.paythunder.gestorInventario.fragments.home.HomeViewModel;
//import com.wul4.paythunder.gestorInventario.request.ApiHome;

import java.util.ArrayList;
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

        apiHome.getproductosContados().enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(@NonNull Call<Integer> call, @NonNull Response<Integer> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Integer productosContados = response.body();

                    //Filtro por productosContados
                    //List<Producto>productosContados= new ArrayList<>();
                    int sumaCantidades = 0;


                    sumaCantidades += productosContados;
                    homeViewModel.setProductosContados(sumaCantidades);//cantidad total
                    homeViewModel.getproductosContados().getValue();



                } else {
                    homeViewModel.setProductosContados(-1);
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

                homeViewModel.setProductosContados(-1);
            }
        });

/*
se ha modificado pero aun no sale bien porque no suma las cantidades de los productos activos sino solo los productos en id
 */
        apiHome.getlistarConexistencias().enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(@NonNull Call<List<Producto>> call, @NonNull Response<List<Producto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Producto> productos = response.body();

                    //filtro producto activo
                    List<Producto> productosFiltrados = new ArrayList<>();
                    int sumaCantidades = 0;
                    for (Producto p : productos) {
                        if (p.getEstado() != null && p.getEstado().equalsIgnoreCase("activo")) {
                            productosFiltrados.add(p);


                            //sumamos las cantidades

                            sumaCantidades += p.getCantidad();
                        }
                    }

                    homeViewModel.setConexistencias(sumaCantidades);//cantidad total
                    homeViewModel.getListarConExistencias().setValue(productosFiltrados); //lista contada de productos
                }else {
                    homeViewModel.setConexistencias(-1);
                }

            }

            @Override
            public void onFailure(@NonNull Call<List<Producto>> call, @NonNull Throwable t) {
                homeViewModel.setConexistencias(-1);
            }
        });


        apiHome.getlistarConFaltantes().enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(@NonNull Call<List<Producto>> call, @NonNull Response<List<Producto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Producto> productos = response.body();

                    List<Producto>productosFiltrados = new ArrayList<>();
                    int sumaCantidades = 0;
                    for (Producto p:productos) {
                        if (p.getEstado() != null && p.getEstado().equalsIgnoreCase("desactivado")) {
                            productosFiltrados.add(p);

                            sumaCantidades += p.getCantidad();
                        }
                    }
                    homeViewModel.setConfaltantes(sumaCantidades);//cantidad total
                    homeViewModel.getListarConFaltantes().setValue(productosFiltrados); //lista contada de productos
                } else {
                    homeViewModel.setConfaltantes(-1);
                }

            }

            @Override
            public void onFailure(@NonNull Call<List<Producto>> call, @NonNull Throwable t) {
                homeViewModel.setConfaltantes(-1);
            }
        });

        apiHome.getlistaTotal_usuarios().enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(@NonNull Call<List<Usuario>> call, @NonNull Response<List<Usuario>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Usuario> usuarios = response.body();
                    int sumaUsuarios = usuarios.size();; //contar los productos

                    homeViewModel.setTotal_usuarios(sumaUsuarios);//cantidad total
                    homeViewModel.getListaTotal_usuarios().setValue(usuarios); //lista contada de productos
                } else {
                    //homeViewModel.setListaTotal_usuarios();
                }

            }

            
            @Override
            public void onFailure(@NonNull Call<List<Usuario>> call, @NonNull Throwable t) {
                homeViewModel.setTotal_usuarios(-1);
            }
        });
    }
}

