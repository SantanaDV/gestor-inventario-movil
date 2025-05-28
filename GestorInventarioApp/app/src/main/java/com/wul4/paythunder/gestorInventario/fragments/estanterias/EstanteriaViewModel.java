package com.wul4.paythunder.gestorInventario.fragments.estanterias;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.wul4.paythunder.gestorInventario.response.EstanteriaResponse;
import java.util.List;

public class EstanteriaViewModel extends ViewModel {
    private final EstanteriaRepository repo = new EstanteriaRepository();
    private final MutableLiveData<List<EstanteriaResponse>> _estanterias = new MutableLiveData<>();

    /** LiveData público para que el fragment observe los cambios. */
    public LiveData<List<EstanteriaResponse>> getEstanterias() {
        return _estanterias;
    }

    /**
     * Lanza la carga de estanterías para el almacén indicado.
     * @param idAlmacen identificador del almacén.
     */
    public void loadEstanterias(int idAlmacen) {
        repo.fetchEstanterias(idAlmacen).observeForever(lista -> {
            _estanterias.postValue(lista);
        });
    }
}
