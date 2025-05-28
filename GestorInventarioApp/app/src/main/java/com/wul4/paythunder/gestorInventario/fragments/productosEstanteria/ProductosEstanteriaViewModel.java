package com.wul4.paythunder.gestorInventario.fragments.productosEstanteria;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.wul4.paythunder.gestorInventario.interfaces.AlmacenApi;
import com.wul4.paythunder.gestorInventario.response.ProductoResponse;
import com.wul4.paythunder.gestorInventario.utils.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductosEstanteriaViewModel extends ViewModel {

    private final MutableLiveData<List<ProductoResponse>> productos = new MutableLiveData<>();
    private final AlmacenApi api;

    public ProductosEstanteriaViewModel() {
        api = ApiClient.getClient().create(AlmacenApi.class);
    }

    /** LiveData con los productos filtrados. */
    public LiveData<List<ProductoResponse>> getProductos() {
        return productos;
    }

    /**
     * Carga todos los productos y, en cliente, filtra por idEstanteria.
     * @param idEstanteria identificador de la estantería.
     */
    public void loadProductos(int idEstanteria) {
        api.getAllProductos().enqueue(new Callback<List<ProductoResponse>>() {
            @Override
            public void onResponse(Call<List<ProductoResponse>> call,
                                   Response<List<ProductoResponse>> response) {
                List<ProductoResponse> filtrados = new ArrayList<>();
                if (response.isSuccessful() && response.body() != null) {
                    for (ProductoResponse p : response.body()) {
                        // Primero comprobamos que la estantería no sea null
                        if (p.getEstanteria() != null && p.getEstanteria().getId() == idEstanteria) {
                            // Opcional: asigna un nombre legible en el producto
                            p.setPosicion("Estantería " + p.getEstanteria().getId());
                            filtrados.add(p);
                        }
                    }
                }
                productos.setValue(filtrados);
            }

            @Override
            public void onFailure(Call<List<ProductoResponse>> call, Throwable t) {
                productos.setValue(new ArrayList<>());
            }
        });
    }
}
