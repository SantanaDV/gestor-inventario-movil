package com.wul4.paythunder.gestorInventario.fragments.tareas;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.wul4.paythunder.gestorInventario.entities.Tarea;

import java.util.List;

/*
Expone los datos al fragment usando LiveData.
 */
public class TareaViewModel extends ViewModel {

    // LiveData para cada uno de los datos que quieres mostrar

    private final MutableLiveData<String> Text = new MutableLiveData<>();
    private final MutableLiveData<List<Tarea>> crearTareas = new MutableLiveData<>();
    private final MutableLiveData<Integer> tareasCrear = new MutableLiveData<>();

    private final MutableLiveData<List<Tarea>> totalTareas = new MutableLiveData<>();

    private final MutableLiveData<List<Tarea>> listarTareaHacer = new MutableLiveData<>();
    private final MutableLiveData<List<Tarea>> listarTareaProceso = new MutableLiveData<>();
    private final MutableLiveData<List<Tarea>> listarTareaRealizada = new MutableLiveData<>();
    private final MutableLiveData<Integer> cuentaTotalTareas = new MutableLiveData<>();


    public MutableLiveData<String> getText() {
        return Text;
    }


    public MutableLiveData<Integer> setCuentaTotalTareas(int size) {
        this.cuentaTotalTareas.setValue(size);
        return cuentaTotalTareas;
    }


    public TareaViewModel() {
    }

    public MutableLiveData<List<Tarea>> getTotalTareas() {
        return totalTareas;
    }



    public MutableLiveData<List<Tarea>> getListarTareaHacer() {
        return listarTareaHacer;
    }


    public MutableLiveData<List<Tarea>> getListarTareaProceso() {
        return listarTareaProceso;
    }

    public MutableLiveData<List<Tarea>> getCrearTareas() {
        return crearTareas;
    }

    public MutableLiveData<List<Tarea>> getListarTareaRealizada() {
        return listarTareaRealizada;
    }


    public MutableLiveData<Integer> getTareasCrear() { return tareasCrear; }

    public void setCrearTareas(int size) {
    }

    public void settotalTareas(int size) {
    }


    public void setListarTareaHacer(int size) {
    }

    public void setListarTareaProceso(int size) {
        
    }

    public void setListarTareaRealizada(int size) {
    }
}

