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
    private final MutableLiveData<Integer> Productos = new MutableLiveData<>();
    private final MutableLiveData<Integer> conexistencias = new MutableLiveData<>();
    private final MutableLiveData<Integer> confaltantes = new MutableLiveData<>();
    //private final MutableLiveData<Integer> usuariosActivos = new MutableLiveData<>();
    private final MutableLiveData<Integer> total_usuarios = new MutableLiveData<>();



    //El constructor vacío, no inicializa los valores de los MutableLiveData.
    //Porque se asignarán más tarde, cuando se obtengan los datos.
    public HomeViewModel() {

    }


    // Métodos para obtener los LiveData
    public LiveData<Integer> getProductos() {
        return Productos;
    }

    public LiveData<Integer> getConexistencias() {
        return conexistencias;
    }

    public MutableLiveData<String> getText() {
        return Text;
    }

    public LiveData<Integer> getConfaltantes() {
        return confaltantes;
    }

    //public LiveData<Integer> getUsuariosActivos() {
      //  return usuariosActivos;
   // }

    public LiveData<Integer> getTotal_usuarios(){ return total_usuarios;}

    // Se agregaron métodos para asignar los valores desde fuera de la clase.
    // Estos métodos pueden ser utilizados por HomeRequest
    // o directamente desde el Fragment para actualizar los datos.

    public void setProductos(int total) {
        Productos.setValue(total);
    }

    public void setConexistencias(int conexistencias) {
        this.conexistencias.setValue(conexistencias);
    }

    public void setConfaltantes(int confaltantes) {
        this.confaltantes.setValue(confaltantes);
    }


    public void setTotal_usuarios(int total_usuarios){
        this.total_usuarios.setValue(total_usuarios);
    }
}



