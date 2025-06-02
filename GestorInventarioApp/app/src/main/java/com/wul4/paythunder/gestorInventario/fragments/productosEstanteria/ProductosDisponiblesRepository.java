package com.wul4.paythunder.gestorInventario.fragments.productosEstanteria;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.wul4.paythunder.gestorInventario.entities.Producto;
import com.wul4.paythunder.gestorInventario.utils.ApiClient;
import com.wul4.paythunder.gestorInventario.utils.interfaces.ApiAlmacen;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductosDisponiblesRepository {
    private final ApiAlmacen api;

    public ProductosDisponiblesRepository() {
        api = ApiClient.getClient().create(ApiAlmacen.class);
    }

    /**
     * Devuelve LiveData con la lista de todos los productos (sin filtrar).
     */
    public LiveData<List<Producto>> fetchAllProductos() {
        MutableLiveData<List<Producto>> data = new MutableLiveData<>();
        api.getProductos().enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.postValue(response.body());
                } else {
                    data.postValue(new ArrayList<>());
                }
            }

            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {
                data.postValue(new ArrayList<>());
            }
        });
        return data;
    }

    /**
     * Lanza la llamada de asignación de productos a estantería.
     */
    public LiveData<Boolean> asignarProductos(int idEstanteria, List<Integer> idsProducto) {
        MutableLiveData<Boolean> resultado = new MutableLiveData<>();
        com.wul4.paythunder.gestorInventario.utils.dto.AsignarProductosDTO dto =
                new com.wul4.paythunder.gestorInventario.utils.dto.AsignarProductosDTO(
                        idEstanteria, idsProducto
                );

        api.asignarProductosAEstanteria(dto).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                // Si HTTP 2xx, consideramos éxito
                resultado.postValue(response.isSuccessful());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                resultado.postValue(false);
            }
        });
        return resultado;
    }
}
