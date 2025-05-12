package com.wul4.paythunder.gestorInventario.fragments.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * Expone los datos al Fragment usando LiveData.
 */
public class HomeViewModel extends ViewModel {

    //private final MutableLiveData<String> mText = new MutableLiveData<>();
    //public BreakIterator getTotalProductos;
    private final MutableLiveData<String> Text = new MutableLiveData<>();

    // LiveData para cada uno de los datos que quieres mostrar
    private MutableLiveData<Integer> totalProductosContados = new MutableLiveData<>();

    private MutableLiveData<Integer> listarConExistencias = new MutableLiveData<>();
    private MutableLiveData<Integer>listarConFaltantes = new MutableLiveData<>();
    private final MutableLiveData<Integer> listarUsuariosActivos = new MutableLiveData<>();

    public MutableLiveData<String> getText() {
        return Text;
    }







    /*ojo modifica despues del valor asignado*/


    //El constructor vacío, no inicializa los valores de los MutableLiveData.
    //Porque se asignarán más tarde, cuando se obtengan los datos.
    public HomeViewModel() {

    }




    public MutableLiveData<Integer> getListarConFaltantes() {
        return listarConFaltantes;
    }
    public MutableLiveData<Integer> getlistarusuariosactivos() {
        return listarUsuariosActivos;
    }

    public LiveData<Integer> getlistarConExistencias() {
        return listarConExistencias;
    }

    public LiveData<Integer> gettotalProductosContados() {
        return totalProductosContados;
    }

    public void settotalProductosContados(int productosContados) {
        totalProductosContados.setValue(productosContados);
    }

    public void setlistarConExistencias(int conExistencias) {
        this.listarConExistencias.setValue(conExistencias);
    }




    public void setListarConFaltantes(int conFaltantes) {
        this.listarConFaltantes.setValue(conFaltantes);
    }




    public void setlistarusuariosactivos(int total) {
        listarUsuariosActivos.setValue(total);
    }




}