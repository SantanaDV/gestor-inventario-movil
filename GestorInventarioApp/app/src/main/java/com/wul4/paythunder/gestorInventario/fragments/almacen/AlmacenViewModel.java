package com.wul4.paythunder.gestorInventario.fragments.almacen;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.wul4.paythunder.gestorInventario.response.AlmacenResponse;
import java.util.List;

public class AlmacenViewModel extends ViewModel {

    private final AlmacenRepository repository;
    private LiveData<List<AlmacenResponse>> almacenes;

    public AlmacenViewModel() {
        repository = new AlmacenRepository();
    }

    /**
     * Devuelve un LiveData con la lista de almacenes.
     * La primera vez que se llama, dispara la petición al repositorio.
     */
    public LiveData<List<AlmacenResponse>> getAlmacenes() {
        if (almacenes == null) {
            almacenes = repository.fetchAlmacenes();
        }
        return almacenes;
    }
}
