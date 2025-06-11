package com.wul4.paythunder.gestorInventario.fragments.productosEstanteria;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.wul4.paythunder.gestorInventario.response.ProductoResponse;
import com.wul4.paythunder.gestorInventario.utils.dto.AsignarProductosDTO;
import com.wul4.paythunder.gestorInventario.utils.interfaces.ApiAlmacen;
import com.wul4.paythunder.gestorInventario.utils.ApiClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductosEstanteriaRepository {
    private final ApiAlmacen api = ApiClient.getClient().create(ApiAlmacen.class);

    public LiveData<List<ProductoResponse>> fetchProductosPorEstanteria(int idEstanteria) {
        MutableLiveData<List<ProductoResponse>> data = new MutableLiveData<>();
        api.getAllProductosResponse().enqueue(new Callback<List<ProductoResponse>>() {
            @Override
            public void onResponse(Call<List<ProductoResponse>> call,
                                   Response<List<ProductoResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ProductoResponse> filtrados = new ArrayList<>();
                    for (ProductoResponse p : response.body()) {
                        if (p.getEstanteria() != null
                                && p.getEstanteria().getId() == idEstanteria) {
                            filtrados.add(p);
                        }
                    }
                    data.postValue(filtrados);
                } else {
                    data.postValue(Collections.emptyList());
                }
            }
            @Override public void onFailure(Call<List<ProductoResponse>> call, Throwable t) {
                data.postValue(Collections.emptyList());
            }
        });
        return data;
    }

    public LiveData<Boolean> actualizarBalda(int idProd, int idEstanteria, int balda) {
        MutableLiveData<Boolean> res = new MutableLiveData<>();
        Map<Integer,Integer> baldas = new HashMap<>();
        baldas.put(idProd, balda);
        api.asignarProductosAEstanteria(
                new AsignarProductosDTO(idEstanteria, Collections.singletonList(idProd), baldas)
        ).enqueue(new Callback<Void>() {
            @Override public void onResponse(Call<Void> c, Response<Void> r) {
                res.postValue(r.isSuccessful());
            }
            @Override public void onFailure(Call<Void> c, Throwable t) {
                res.postValue(false);
            }
        });
        return res;
    }

    public LiveData<Boolean> desasignarProducto(int idProd) {
        MutableLiveData<Boolean> res = new MutableLiveData<>();
        // Enviamos id_estanteria=null para quitar también la relación
        Map<Integer,Integer> baldas = new HashMap<>();
        baldas.put(idProd, null);
        AsignarProductosDTO dto = new AsignarProductosDTO(null,
                Collections.singletonList(idProd),
                baldas
        );
        api.asignarProductosAEstanteria(dto).enqueue(new Callback<Void>() {
            @Override public void onResponse(Call<Void> c, Response<Void> r) {
                res.postValue(r.isSuccessful());
            }
            @Override public void onFailure(Call<Void> c, Throwable t) {
                res.postValue(false);
            }
        });
        return res;
    }
}
