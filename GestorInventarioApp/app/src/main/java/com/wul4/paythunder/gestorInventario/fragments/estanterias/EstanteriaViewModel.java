package com.wul4.paythunder.gestorInventario.fragments.estanterias;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.wul4.paythunder.gestorInventario.response.EstanteriaResponse;
import java.util.List;

public class EstanteriaViewModel extends ViewModel {
    private final EstanteriaRepository repo = new EstanteriaRepository();
    private final MutableLiveData<List<EstanteriaResponse>> _estanterias = new MutableLiveData<>();

    public LiveData<List<EstanteriaResponse>> getEstanterias() {
        return _estanterias;
    }

    public void loadEstanterias(int idAlmacen) {
        repo.fetchEstanterias(idAlmacen).observeForever(lista -> {
            _estanterias.postValue(lista);
        });
    }
}
