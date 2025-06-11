package com.wul4.paythunder.gestorInventario.fragments.productosEstanteria;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.wul4.paythunder.gestorInventario.response.ProductoResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ViewModel que:
 *  - expone LiveData<List<ProductoResponse>> filtrada (productos *no* asignados a la estantería)
 *  - expone LiveData<Boolean> con el resultado de la asignación
 *  - permite asignar productos con su número de balda
 */
public class ProductosDisponiblesViewModel extends ViewModel {

    private final ProductosDisponiblesRepository repo = new ProductosDisponiblesRepository();

    /** “Crudo” con TODOS los productos de GET /api/producto */
    private final LiveData<List<ProductoResponse>> allProductosLiveData = repo.fetchAllProductos();

    /** Filtrado: solo productos no asignados a la estantería actual */
    private final MediatorLiveData<List<ProductoResponse>> productosFiltrados = new MediatorLiveData<>();

    /** Resultado de la llamada de asignación (true=OK, false=error) */
    private final MutableLiveData<Boolean> resultadoAsignacion = new MutableLiveData<>();

    /** ID de la estantería a la que pretendemos asignar */
    private int idEstanteriaActual = -1;

    public ProductosDisponiblesViewModel() {
        productosFiltrados.addSource(allProductosLiveData, new Observer<List<ProductoResponse>>() {
            @Override public void onChanged(List<ProductoResponse> lista) {
                filtrar(lista);
            }
        });
    }

    /** Fija la estantería y fuerza un filtrado inmediato **/
    public void setIdEstanteria(int id) {
        this.idEstanteriaActual = id;
        filtrar(allProductosLiveData.getValue());
    }

    /** Filtra los productos cuya estantería sea null o distinta de la actual **/
    private void filtrar(List<ProductoResponse> listaOriginal) {
        if (listaOriginal == null) {
            productosFiltrados.setValue(new ArrayList<>());
            return;
        }
        List<ProductoResponse> disponibles = new ArrayList<>();
        for (ProductoResponse p : listaOriginal) {
            if (p.getEstanteria() == null || p.getEstanteria().getId() != idEstanteriaActual) {
                disponibles.add(p);
            }
        }
        productosFiltrados.setValue(disponibles);
    }

    public LiveData<List<ProductoResponse>> getProductosFiltrados() {
        return productosFiltrados;
    }
    public LiveData<Boolean> getResultadoAsignacion() {
        return resultadoAsignacion;
    }

    /**
     * Ejecuta la asignación de productos + baldas.
     * @param idsProductos lista de IDs seleccionados
     * @param baldas      mapa ID→balda
     */
    public void asignarProductosConBaldas(List<Integer> idsProductos,
                                          Map<Integer,Integer> baldas) {
        if (idEstanteriaActual < 0) {
            resultadoAsignacion.setValue(false);
            return;
        }
        repo.asignarProductos(idEstanteriaActual, idsProductos, baldas)
                .observeForever(new Observer<Boolean>() {
                    @Override public void onChanged(Boolean ok) {
                        resultadoAsignacion.setValue(ok);
                    }
                });
    }
}
