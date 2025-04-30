package com.wul4.paythunder.gestorInventario.fragments.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.wul4.paythunder.gestorInventario.entities.Producto;

import java.util.List;

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

    public void setProductosContados(MutableLiveData<Integer> productosContados) {
        this.productosContados = productosContados;
    }

    public MutableLiveData<List<Usuario>> getListaTotal_usuarios() {
        return listaTotal_usuarios;
    }

    public void setListaTotal_usuarios(MutableLiveData<List<Usuario>> listaTotal_usuarios) {
        this.listaTotal_usuarios = listaTotal_usuarios;
    }

    public Integer getProductosContados() {
        return productosContados.getValue();
    }

    //El constructor vacío, no inicializa los valores de los MutableLiveData.
    //Porque se asignarán más tarde, cuando se obtengan los datos.
    public HomeViewModel() {

    }

<<<<<<< HEAD
    public LiveData<Integer> getproductosContados(){
        return productosContados;
    }
    public LiveData<Integer> getProductos() {
        return Productos;
=======



    public MutableLiveData<Integer> getListarConFaltantes() {
        return listarConFaltantes;
    }
    public MutableLiveData<Integer> getlistarusuariosactivos() {
        return listarUsuariosActivos;
>>>>>>> 36aeb1e (En fase de implementación del boton añadir y su dialog)
    }

    public LiveData<Integer> getlistarConExistencias() {
        return listarConExistencias;
    }

<<<<<<< HEAD
=======
    public LiveData<Integer> gettotalProductosContados() {
        return totalProductosContados;
    }
>>>>>>> 36aeb1e (En fase de implementación del boton añadir y su dialog)

    public void settotalProductosContados(int productosContados) {
        totalProductosContados.setValue(productosContados);
    }

<<<<<<< HEAD
    public LiveData<Integer> getTotal_usuarios(){ return total_usuarios;}
    public LiveData<List<Usuario>> listaTotal_usuarios(){ return listaTotal_usuarios;}

    // Se agregaron métodos para asignar los valores desde fuera de la clase.
    // Estos métodos pueden ser utilizados por HomeRequest
    // o directamente desde el Fragment para actualizar los datos.



    public void setConexistencias(int conexistencias) {
        this.conexistencias.setValue(conexistencias);
    }

    public void setConfaltantes(int confaltantes) {
        this.confaltantes.setValue(confaltantes);
=======
    public void setlistarConExistencias(int conExistencias) {
        this.listarConExistencias.setValue(conExistencias);
>>>>>>> 36aeb1e (En fase de implementación del boton añadir y su dialog)
    }




    public void setListarConFaltantes(int conFaltantes) {
        this.listarConFaltantes.setValue(conFaltantes);
    }



<<<<<<< HEAD
    public void setProductosContados(int productosContados) {
        this.productosContados.setValue(productosContados);
    }

    public MutableLiveData<List<Producto>> getListarConExistencias() {
        return listarConExistencias;
    }

    public void setListarConExistencias(MutableLiveData<List<Producto>> listarConExistencias) {
        this.listarConExistencias = listarConExistencias;
    }

    public MutableLiveData<List<Producto>> getListarConFaltantes() {
        return listarConFaltantes;
    }

    public void setListarConFaltantes(MutableLiveData<List<Producto>> listarConFaltantes) {
        this.listarConFaltantes = listarConFaltantes;
    }
    public MutableLiveData<List<Usuario>> getListarTotal_Usuarios(){
        return listaTotal_usuarios;
    }
=======

    public void setlistarusuariosactivos(int total) {
        listarUsuariosActivos.setValue(total);
    }




>>>>>>> 36aeb1e (En fase de implementación del boton añadir y su dialog)
}
