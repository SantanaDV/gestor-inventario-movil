package com.wul4.paythunder.gestorInventario.fragments.productosEstanteria;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.wul4.paythunder.gestorInventario.interfaces.AlmacenApi;
import com.wul4.paythunder.gestorInventario.response.ProductoResponse;
import com.wul4.paythunder.gestorInventario.utils.ApiClient;
import com.wul4.paythunder.gestorInventario.utils.dto.AsignarProductosDTO;
import com.wul4.paythunder.gestorInventario.utils.interfaces.ApiAlmacen;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Repositorio que:
 * 1) Obtiene todos los productos (como ProductoResponse) vía AlmacenApi.getAllProductos()
 * 2) Llama a ApiAlmacen.asignarProductosAEstanteria(...) para asignarlos a una estantería.
 */
public class ProductosDisponiblesRepository {

    // Para leer “todos los productos” (ProductoResponse)
    private final AlmacenApi prodApi;

    // Para INVOCAR el endpoint de “asignarEstanteria”
    private final ApiAlmacen apiAlmacen;

    public ProductosDisponiblesRepository() {
        prodApi     = ApiClient.getClient().create(AlmacenApi.class);
        apiAlmacen  = ApiClient.getClient().create(ApiAlmacen.class);
    }

    /**
     * Devuelve un LiveData<List<ProductoResponse>> con TODO el catálogo.
     */
    public LiveData<List<ProductoResponse>> fetchAllProductos() {
        MutableLiveData<List<ProductoResponse>> data = new MutableLiveData<>();
        prodApi.getAllProductos().enqueue(new Callback<List<ProductoResponse>>() {
            @Override
            public void onResponse(Call<List<ProductoResponse>> call,
                                   Response<List<ProductoResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.postValue(response.body());
                } else {
                    data.postValue(new ArrayList<>());
                }
            }
            @Override
            public void onFailure(Call<List<ProductoResponse>> call, Throwable t) {
                data.postValue(new ArrayList<>());
            }
        });
        return data;
    }

    /**
     * Asigna varios IDs de producto a la estantería dada.
     * Devuelve LiveData<Boolean> que será true si la llamada HTTP fue 2xx, false en error.
     */
    public LiveData<Boolean> asignarProductos(int idEstanteria, List<Integer> idsProductos) {
        MutableLiveData<Boolean> resultado = new MutableLiveData<>();
        AsignarProductosDTO dto = new AsignarProductosDTO(idEstanteria, idsProductos);

        apiAlmacen.asignarProductosAEstanteria(dto).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
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
