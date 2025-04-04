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
    private final MutableLiveData<Integer> TotalProductos = new MutableLiveData<>();
    private final MutableLiveData<Integer> existencias = new MutableLiveData<>();
    private final MutableLiveData<Integer> faltantes = new MutableLiveData<>();
    private final MutableLiveData<Integer> usuariosActivos = new MutableLiveData<>();
    private final MutableLiveData<Integer> contarUsuarios = new MutableLiveData<>();


    //El constructor vacío, no inicializa los valores de los MutableLiveData.
    //Porque se asignarán más tarde, cuando se obtengan los datos.
    public HomeViewModel() {

    }

    // Métodos para obtener los LiveData
    public LiveData<Integer> getTotalProductos() {
        return TotalProductos;
    }

    public LiveData<Integer> getExistencias() {
        return existencias;
    }

    public MutableLiveData<String> getText() {
        return Text;
    }

    public LiveData<Integer> getFaltantes() {
        return faltantes;
    }

    //public LiveData<Integer> getUsuariosActivos() {
      //  return usuariosActivos;
   // }

    public LiveData<Integer> getContarUsuarios(){ return contarUsuarios;}

    // Se agregaron métodos para asignar los valores desde fuera de la clase.
    // Estos métodos pueden ser utilizados por HomeRequest
    // o directamente desde el Fragment para actualizar los datos.

    public void setTotalProductos(int total) {
        TotalProductos.setValue(total);
    }

    public void setExistencias(int existencias) {
        this.existencias.setValue(existencias);
    }

    public void setFaltantes(int faltantes) {
        this.faltantes.setValue(faltantes);
    }


    public void setContarUsuarios(int contarUsuarios){
        this.contarUsuarios.setValue(contarUsuarios);
    }
}



