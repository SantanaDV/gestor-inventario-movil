package com.wul4.paythunder.gestorInventario.fragments.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.wul4.paythunder.gestorInventario.entities.Producto;
import com.wul4.paythunder.gestorInventario.entities.Usuario;

import java.util.List;

/**
 * Expone los datos al Fragment usando LiveData.
 */
public class HomeViewModel extends ViewModel {

    //private final MutableLiveData<String> mText = new MutableLiveData<>();
    //public BreakIterator getTotalProductos;
    private final MutableLiveData<String> Text = new MutableLiveData<>();

    // LiveData para cada uno de los datos que quieres mostrar
    private final MutableLiveData<Integer> Productos = new MutableLiveData<>();
    private MutableLiveData<Integer> productosContados = new MutableLiveData<>();

    private final MutableLiveData<Integer> conexistencias = new MutableLiveData<>();
    private MutableLiveData<List<Producto>>listarConExistencias = new MutableLiveData<>();
    private MutableLiveData<List<Producto>>listarConFaltantes = new MutableLiveData<>();
    private final MutableLiveData<Integer> confaltantes = new MutableLiveData<>();
    //private final MutableLiveData<Integer> usuariosActivos = new MutableLiveData<>();
    private final MutableLiveData<Integer> total_usuarios = new MutableLiveData<>();
    private MutableLiveData<List<Usuario>> listaTotal_usuarios = new MutableLiveData<>();

    public MutableLiveData<String> getText() {
        return Text;
    }

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

    public LiveData<Integer> getproductosContados(){
        return productosContados;
    }
    public LiveData<Integer> getProductos() {
        return Productos;
    }

    public LiveData<Integer> getConexistencias() {
        return conexistencias;
    }


    public LiveData<Integer> getConfaltantes() {
        return confaltantes;
    }

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
    }


    public void setTotal_usuarios(int total_usuarios){
        this.total_usuarios.setValue(total_usuarios);
    }



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
}
