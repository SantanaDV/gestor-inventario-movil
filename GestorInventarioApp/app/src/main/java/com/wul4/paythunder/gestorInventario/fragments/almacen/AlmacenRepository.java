package com.wul4.paythunder.gestorInventario.fragments.almacen;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.wul4.paythunder.gestorInventario.interfaces.AlmacenApi;
import com.wul4.paythunder.gestorInventario.response.AlmacenResponse;
import com.wul4.paythunder.gestorInventario.utils.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlmacenRepository {

    private final AlmacenApi api = ApiClient
            .getClient()
            .create(AlmacenApi.class);

    public LiveData<List<AlmacenResponse>> fetchAlmacenes() {
        MutableLiveData<List<AlmacenResponse>> live = new MutableLiveData<>();
        api.getAlmacenes().enqueue(new Callback<List<AlmacenResponse>>() {
            @Override
            public void onResponse(Call<List<AlmacenResponse>> call,
                                   Response<List<AlmacenResponse>> resp) {
                live.postValue(resp.body());
            }
            @Override
            public void onFailure(Call<List<AlmacenResponse>> call, Throwable t) {
                live.postValue(null);
            }
        });
        return live;
    }
}
