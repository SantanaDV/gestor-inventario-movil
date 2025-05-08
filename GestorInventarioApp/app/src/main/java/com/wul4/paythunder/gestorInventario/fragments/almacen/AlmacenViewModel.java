package com.wul4.paythunder.gestorInventario.fragments.almacen;


import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.wul4.paythunder.gestorInventario.entities.Categoria;
import com.wul4.paythunder.gestorInventario.entities.Producto;
import com.wul4.paythunder.gestorInventario.utils.ApiClient;
import com.wul4.paythunder.gestorInventario.utils.interfaces.ApiAlmacen;

import java.util.Collections;
import java.util.List;

//import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Callback;

public class AlmacenViewModel extends ViewModel {

    private final MutableLiveData<List<Producto>> productos = new MutableLiveData<>();
    private final MutableLiveData<List<Categoria>> categorias = new MutableLiveData<>();
    private ApiAlmacen apiAlmacen = ApiClient.getClient().create(ApiAlmacen.class);
    private final MutableLiveData<Producto> resultadoEdicion = new MutableLiveData<>();
    private final MutableLiveData<Producto> resultadoCreacion  = new MutableLiveData<>();
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

    public LiveData<List<Categoria>> getCategorias(){
        Call<List<Categoria>> call = apiAlmacen.getCategorias();
        call.enqueue(new retrofit2.Callback<List<Categoria>>() {

            @Override
            public void onResponse(Call<List<Categoria>> call, Response<List<Categoria>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    categorias.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Categoria>> call, Throwable t) {
                categorias.setValue(Collections.emptyList());
            }
        });

        return categorias;
    }
    public LiveData<Producto> getResultadoEdicion() {
        return resultadoEdicion;
    }
    public LiveData<Producto> getResultadoCreacion() {
        return resultadoCreacion;
    }


    /**
     * Lanza la llamada a la API para crear un producto.
     * @param productoJson cuerpo JSON ya empaquetado en RequestBody
     * @param imagenPart parte con la imagen, o null si no hay
     */
    public void guardarProductoApi(RequestBody productoJson,
                                   @androidx.annotation.Nullable MultipartBody.Part imagenPart) {
        Call<Producto> call = apiAlmacen.createOrUpdateProducto(productoJson,
                imagenPart != null ? imagenPart : MultipartBody.Part.createFormData("imagen",""));
        call.enqueue(new Callback<Producto>() {
            @Override
            public void onResponse(Call<Producto> call, Response<Producto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    resultadoCreacion.setValue(response.body());
                } else {
                    // podrías setear null o lanzar un evento de error
                    resultadoCreacion.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<Producto> call, Throwable t) {
                resultadoCreacion.setValue(null);
                // tu código aquí
            }
        });
    }


    public void onResponse(Call<Producto> call, Response<Producto> response) {
        if (response.isSuccessful() && response.body() != null) {
            resultadoCreacion.setValue(response.body());
        } else {
            // podrías setear null o lanzar un evento de error
            resultadoCreacion.setValue(null);
        }
    }

    public void onFailure(Call<Producto> call, Throwable t) {
        resultadoCreacion.setValue(null);
    }
}




