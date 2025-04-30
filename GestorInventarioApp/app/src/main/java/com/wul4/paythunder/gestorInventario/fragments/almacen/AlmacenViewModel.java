package com.wul4.paythunder.gestorInventario.fragments.almacen;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.wul4.paythunder.gestorInventario.entities.Producto;
import com.wul4.paythunder.gestorInventario.utils.ApiClient;
import com.wul4.paythunder.gestorInventario.utils.interfaces.ApiAlmacen;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class AlmacenViewModel extends ViewModel {

    private final MutableLiveData<List<Producto>> productos = new MutableLiveData<>();
    private ApiAlmacen apiAlmacen = ApiClient.getClient().create(ApiAlmacen.class);
    private final MutableLiveData<Producto> resultadoEdicion = new MutableLiveData<>();
    public AlmacenViewModel() {

    }

    public LiveData<List<Producto>> getProductos() {
        Call<List<Producto>> call = apiAlmacen.getProductos();
        call.enqueue(new retrofit2.Callback<List<Producto>>() {

            @Override
            public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    productos.setValue(response.body());
                }else{
                    productos.setValue(Collections.emptyList());
                }
            }

            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {
                productos.setValue(Collections.emptyList());

            }
        });
        return productos;
    }
    public LiveData<Producto> getResultadoEdicion() {
        return resultadoEdicion;
    }


    public LiveData<Producto> getCategorias() {return getCategorias();
    }
}