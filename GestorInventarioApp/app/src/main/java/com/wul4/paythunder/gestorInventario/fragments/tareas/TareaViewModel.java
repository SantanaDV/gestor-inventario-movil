package com.wul4.paythunder.gestorInventario.fragments.tareas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.wul4.paythunder.gestorInventario.entities.Tarea;

import java.net.HttpCookie;
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

    private final MutableLiveData<List<Tarea>> tareasHacer = new MutableLiveData<>();
    private final MutableLiveData<List<Tarea>> tareasProceso = new MutableLiveData<>();
    private final MutableLiveData<List<Tarea>> tareasRealizadas = new MutableLiveData<>();
    private final MutableLiveData<Integer> cuentaTareas = new MutableLiveData<>();


    public MutableLiveData<String> getText() {
        return Text;
    }




    public TareaViewModel() {
    }

    public MutableLiveData<List<Tarea>> getTotalTareas() {
        return totalTareas;
    }



    public MutableLiveData<List<Tarea>> getTareasHacer() {
        return tareasHacer;
    }

    public void setTareasHacer(MutableLiveData<List<Tarea>> tareasHacer) {
        tareasHacer = tareasHacer;
    }

    public MutableLiveData<List<Tarea>> getTareasProceso() {
        return tareasProceso;
    }

    public void setTareasProceso(MutableLiveData<List<Tarea>> tareasProceso) {
        tareasProceso = tareasProceso;
    }

    public MutableLiveData<List<Tarea>> getCrearTareas() {
        return crearTareas;
    }

    public MutableLiveData<List<Tarea>> getTareasRealizadas() {
        return tareasRealizadas;
    }

    public void setTareasRealizadas(MutableLiveData<List<Tarea>> tareasRealizadas) {
        tareasRealizadas = tareasRealizadas;
    }


    public MutableLiveData<Integer> getCuentaTareas() {
        return cuentaTareas;
    }

    public MutableLiveData<Integer> getTareasCrear() { return tareasCrear; }


}
