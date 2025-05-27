package com.wul4.paythunder.gestorInventario.fragments.estanterias;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.wul4.paythunder.gestorInventario.response.EstanteriaResponse;
import com.wul4.paythunder.gestorInventario.utils.interfaces.EstanteriaApi;
import com.wul4.paythunder.gestorInventario.utils.ApiClient;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EstanteriaRepository {

    private final EstanteriaApi api = ApiClient
            .getClient()
            .create(EstanteriaApi.class);

    public LiveData<List<EstanteriaResponse>> fetchEstanterias(int idAlmacen) {
        MutableLiveData<List<EstanteriaResponse>> live = new MutableLiveData<>();
        api.getEstanterias(idAlmacen).enqueue(new Callback<List<EstanteriaResponse>>() {
            @Override
            public void onResponse(Call<List<EstanteriaResponse>> call,
                                   Response<List<EstanteriaResponse>> resp) {
                live.postValue(resp.body());
            }
            @Override
            public void onFailure(Call<List<EstanteriaResponse>> call, Throwable t) {
                live.postValue(null);
            }
        });
        return live;
    }
}
