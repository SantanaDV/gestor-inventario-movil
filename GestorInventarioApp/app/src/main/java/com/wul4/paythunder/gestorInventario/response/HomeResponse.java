package com.wul4.paythunder.gestorInventario.response;

import androidx.annotation.NonNull;

import com.wul4.paythunder.gestorInventario.entities.Producto;
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

        apiHome.gettotalProductosContados().enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(@NonNull Call<Integer> call, @NonNull Response<Integer> response) {
                if (response.isSuccessful() && response.body() != null) {
                    int productosContados = response.body();
                    homeViewModel.settotalProductosContados(productosContados);
                    homeViewModel.gettotalProductosContados().getValue();
                } else {
                    homeViewModel.settotalProductosContados(-1);
                }
            }

            @Override
<<<<<<< HEAD
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
=======
            public void onFailure(@NonNull Call<Integer> call, @NonNull Throwable t) {
                homeViewModel.settotalProductosContados(-1);
            }
        });



        apiHome.getlistarConExistencias().enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful() && response.body() != null) {
                    int conexistencias = response.body();

                    homeViewModel.setlistarConExistencias(conexistencias);
                    homeViewModel.getlistarConExistencias().getValue();


                } else {
                    homeViewModel.setlistarConExistencias(-1);
>>>>>>> 36aeb1e (En fase de implementación del boton añadir y su dialog)
                }

            }

            @Override
            public void onFailure(@NonNull Call<Integer> call, @NonNull Throwable t) {
                homeViewModel.setlistarConExistencias(-1);
            }
        });


<<<<<<< HEAD
        apiHome.getlistarConFaltantes().enqueue(new Callback<List<Producto>>() {
=======

        apiHome.getlistarConFaltantes().enqueue(new Callback<Integer>() {
>>>>>>> 36aeb1e (En fase de implementación del boton añadir y su dialog)
            @Override
            public void onResponse(@NonNull Call<Integer> call, @NonNull Response<Integer> response) {
                if (response.isSuccessful() && response.body() != null) {
<<<<<<< HEAD
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
=======
                    int conFaltantes = response.body();

                    homeViewModel.setListarConFaltantes(conFaltantes);//cantidad total
                    homeViewModel.getListarConFaltantes().getValue(); //lista contada de productos

>>>>>>> 36aeb1e (En fase de implementación del boton añadir y su dialog)
                } else {
                    homeViewModel.setListarConFaltantes(-1);
                }

            }

            @Override
            public void onFailure(@NonNull Call<Integer> call, @NonNull Throwable t) {
                homeViewModel.setListarConFaltantes(-1);

            }
        });

<<<<<<< HEAD
        apiHome.getlistaTotal_usuarios().enqueue(new Callback<List<Usuario>>() {
=======
        apiHome.getlistarUsuariosActivos().enqueue(new Callback<Integer>() {
>>>>>>> 36aeb1e (En fase de implementación del boton añadir y su dialog)
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful() && response.body() != null) {
<<<<<<< HEAD
                    List<Usuario> usuarios = response.body();
                    int sumaUsuarios = usuarios.size();; //contar los productos

                    homeViewModel.setTotal_usuarios(sumaUsuarios);//cantidad total
                    homeViewModel.getListaTotal_usuarios().setValue(usuarios); //lista contada de productos
                } else {
                    //homeViewModel.setListaTotal_usuarios();
=======
                    Integer usuariosFiltrados = response.body();

                    //no es necesario hacer suma adicional aquí ya que el backend ya nos da la suma total

                    homeViewModel.setlistarusuariosactivos(usuariosFiltrados);
                    homeViewModel.getlistarusuariosactivos().getValue();


                } else {
                    //homeViewModel.setlistarUsuariosActivos(-1);
>>>>>>> 36aeb1e (En fase de implementación del boton añadir y su dialog)
                }

            }

            
            @Override
            public void onFailure(@NonNull Call<Integer> call, @NonNull Throwable t) {
                homeViewModel.setlistarusuariosactivos(-1);
            }

        });
    }
}
<<<<<<< HEAD
=======





>>>>>>> 36aeb1e (En fase de implementación del boton añadir y su dialog)
