package com.wul4.paythunder.gestorInventario.fragments.estanterias;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.wul4.paythunder.gestorInventario.interfaces.AlmacenApi;
import com.wul4.paythunder.gestorInventario.response.EstanteriaResponse;
import com.wul4.paythunder.gestorInventario.utils.ApiClient;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Pequeño repositorio que hace la llamada Retrofit y expone un LiveData.
 */
public class EstanteriaRepository {

    private final AlmacenApi api;

    public EstanteriaRepository() {
        api = ApiClient.getClient().create(AlmacenApi.class);
    }

    /**
     * Devuelve un LiveData con la lista de estanterías.
     */
    public LiveData<List<EstanteriaResponse>> fetchEstanterias(int idAlmacen) {
        MutableLiveData<List<EstanteriaResponse>> data = new MutableLiveData<>();
        api.getEstanterias(idAlmacen).enqueue(new Callback<List<EstanteriaResponse>>() {
            @Override
            public void onResponse(Call<List<EstanteriaResponse>> call,
                                   Response<List<EstanteriaResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.postValue(response.body());
                } else {
                    data.postValue(List.of());
                }
            }
            @Override
            public void onFailure(Call<List<EstanteriaResponse>> call, Throwable t) {
                data.postValue(List.of());
            }
        });
        return data;
    }
}
