package com.wul4.paythunder.gestorInventario.fragments.productosEstanteria;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.wul4.paythunder.gestorInventario.interfaces.AlmacenApi;
import com.wul4.paythunder.gestorInventario.response.ProductoResponse;
import com.wul4.paythunder.gestorInventario.utils.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * ViewModel para ProductosEstanteriaFragment.
 * Encapsula la lógica de cargar desde la API los productos de una estantería concreta.
 */
public class ProductosEstanteriaViewModel extends ViewModel {

    private final MutableLiveData<List<ProductoResponse>> productos = new MutableLiveData<>();
    private final AlmacenApi api;

    public ProductosEstanteriaViewModel() {
        // Creamos la instancia del API retrofit
        api = ApiClient.getClient().create(AlmacenApi.class);
    }

    /** LiveData que expone la lista de productos cargados. */
    public LiveData<List<ProductoResponse>> getProductos() {
        return productos;
    }

    /**
     * Lanza la petición a la API para obtener los productos de la estantería indicada.
     * @param idEstanteria identificador de la estantería
     */
    public void loadProductos(int idEstanteria) {
        api.getProductos(idEstanteria).enqueue(new Callback<List<ProductoResponse>>() {
            @Override
            public void onResponse(Call<List<ProductoResponse>> call,
                                   Response<List<ProductoResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    productos.setValue(response.body());
                } else {
                    // En caso de error de respuesta devolvemos lista vacía
                    productos.setValue(List.of());
                }
            }

            @Override
            public void onFailure(Call<List<ProductoResponse>> call, Throwable t) {
                // En caso de fallo de red devolvemos lista vacía
                productos.setValue(List.of());
            }
        });
    }
}
