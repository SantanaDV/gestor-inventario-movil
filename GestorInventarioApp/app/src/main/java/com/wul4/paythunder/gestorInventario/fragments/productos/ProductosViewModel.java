package com.wul4.paythunder.gestorInventario.fragments.productos;

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

public class ProductosViewModel extends ViewModel {

    private final ApiAlmacen api = ApiClient.getClient().create(ApiAlmacen.class);

    private final MutableLiveData<List<Producto>> productos = new MutableLiveData<>();
    private final MutableLiveData<List<Categoria>> categorias = new MutableLiveData<>();
    private final MutableLiveData<Producto> productoQR = new MutableLiveData<>();
    private final MutableLiveData<Producto> resultadoCreacion  = new MutableLiveData<>();

    public LiveData<List<Producto>> getProductos() {
        api.getProductos().enqueue(new Callback<List<Producto>>() {
            @Override public void onResponse(Call<List<Producto>> c, Response<List<Producto>> r) {
                productos.setValue(r.isSuccessful() && r.body()!=null
                        ? r.body() : Collections.emptyList());
            }
            @Override public void onFailure(Call<List<Producto>> c, Throwable t) {
                productos.setValue(Collections.emptyList());
            }
        });
        return productos;
    }

    public LiveData<List<Categoria>> getCategorias() {
        api.getCategorias().enqueue(new Callback<List<Categoria>>() {
            @Override public void onResponse(Call<List<Categoria>> c, Response<List<Categoria>> r) {
                categorias.setValue(r.isSuccessful()&&r.body()!=null
                        ? r.body() : Collections.emptyList());
            }
            @Override public void onFailure(Call<List<Categoria>> c, Throwable t) {
                categorias.setValue(Collections.emptyList());
            }
        });
        return categorias;
    }

    public LiveData<Producto> getProductoQR() {
        return productoQR;
    }

    /** Llama al endpoint /obtenerProductoQR/{qr} **/
    public void fetchProductoPorQR(String qr) {
        api.getProductoPorQR(qr).enqueue(new Callback<Producto>() {
            @Override public void onResponse(Call<Producto> c, Response<Producto> r) {
                productoQR.setValue(r.isSuccessful() ? r.body() : null);
            }
            @Override public void onFailure(Call<Producto> c, Throwable t) {
                productoQR.setValue(null);
            }
        });
    }

    public LiveData<Producto> getResultadoCreacion() {
        return resultadoCreacion;
    }

    public void guardarProductoApi(RequestBody productoJson, MultipartBody.Part imagenPart) {
        api.crearProducto(productoJson,
                imagenPart != null
                        ? imagenPart
                        : MultipartBody.Part.createFormData("imagen","")
        ).enqueue(new Callback<Producto>() {
            @Override public void onResponse(Call<Producto> c, Response<Producto> r) {
                resultadoCreacion.setValue(r.isSuccessful() ? r.body() : null);
            }
            @Override public void onFailure(Call<Producto> c, Throwable t) {
                resultadoCreacion.setValue(null);
                // tu código aquí
            }
        });
    }
}




