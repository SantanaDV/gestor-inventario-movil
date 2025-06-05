package com.wul4.paythunder.gestorInventario.fragments.productosEstanteria;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.wul4.paythunder.gestorInventario.response.ProductoResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewModel que:
 *  - expone LiveData<List<ProductoResponse>> filtrada (aquellos productos NO asignados a la estantería actual),
 *  - expone LiveData<Boolean> con el resultado de la asignación.
 */
public class ProductosDisponiblesViewModel extends ViewModel {

    private final ProductosDisponiblesRepository repo = new ProductosDisponiblesRepository();

    /** 1) LiveData “crudo” con TODOS los productos devueltos por el endpoint GET /api/producto */
    private final LiveData<List<ProductoResponse>> allProductosLiveData = repo.fetchAllProductos();

    /** 2) LiveData filtrado: solo productos que no estén ya en la estantería actual */
    private final MediatorLiveData<List<ProductoResponse>> productosFiltrados = new MediatorLiveData<>();

    /** 3) LiveData del resultado de la llamada de asignación (true=éxito, false=error) */
    private final MutableLiveData<Boolean> resultadoAsignacion = new MutableLiveData<>();

    /** ID de la estantería a la cual queremos asignar (se fija desde el Fragment) */
    private int idEstanteriaActual = -1;

    public ProductosDisponiblesViewModel() {
        // Cada vez que cambie allProductosLiveData, disparo filtrar(...)
        productosFiltrados.addSource(allProductosLiveData, new Observer<List<ProductoResponse>>() {
            @Override
            public void onChanged(List<ProductoResponse> lista) {
                filtrar(lista);
            }
        });
    }

    /**
     * Debe llamarse desde el Fragment justo después de instanciar el ViewModel
     * (para fijar la estantería que nos interesa).
     */
    public void setIdEstanteria(int id) {
        this.idEstanteriaActual = id;
        filtrar(allProductosLiveData.getValue());
    }

    /**
     * Filtra “listaOriginal” y deja solo aquellos productos cuya estantería sea null
     * o bien distinta de idEstanteriaActual.
     */
    private void filtrar(List<ProductoResponse> listaOriginal) {
        if (listaOriginal == null) {
            productosFiltrados.setValue(new ArrayList<>());
            return;
        }
        List<ProductoResponse> disponibles = new ArrayList<>();
        for (ProductoResponse p : listaOriginal) {
            if (p.getEstanteria() == null
                    || p.getEstanteria().getId() != idEstanteriaActual) {
                disponibles.add(p);
            }
        }
        productosFiltrados.setValue(disponibles);
    }

    /** LiveData público con la lista filtrada de productos disponibles. */
    public LiveData<List<ProductoResponse>> getProductosFiltrados() {
        return productosFiltrados;
    }

    /** LiveData público con el resultado de asignación (true=OK, false=error). */
    public LiveData<Boolean> getResultadoAsignacion() {
        return resultadoAsignacion;
    }

    /**
     * Lanza la asignación de los IDs de producto a la estantería actual.
     * Observa el LiveData<Boolean> devuelto por el repositorio y lo publica en resultadoAsignacion.
     */
    public void asignarProductos(List<Integer> idsProductos) {
        if (idEstanteriaActual < 0) {
            resultadoAsignacion.setValue(false);
            return;
        }
        LiveData<Boolean> llamada = repo.asignarProductos(idEstanteriaActual, idsProductos);
        llamada.observeForever(new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean ok) {
                resultadoAsignacion.setValue(ok);
            }
        });
    }
}
