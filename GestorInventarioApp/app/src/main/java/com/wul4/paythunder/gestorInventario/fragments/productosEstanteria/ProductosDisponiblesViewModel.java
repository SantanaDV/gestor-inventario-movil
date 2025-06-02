package com.wul4.paythunder.gestorInventario.fragments.productosEstanteria;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.wul4.paythunder.gestorInventario.entities.Producto;

import java.util.ArrayList;
import java.util.List;

public class ProductosDisponiblesViewModel extends ViewModel {

    private final ProductosDisponiblesRepository repo = new ProductosDisponiblesRepository();

    // LiveData crudo de todos los productos
    private final LiveData<List<Producto>> allProductosLiveData = repo.fetchAllProductos();

    // LiveData de la lista filtrada (sin los que ya están en esta estantería)
    private final MediatorLiveData<List<Producto>> productosFiltrados = new MediatorLiveData<>();

    // LiveData del resultado de la asignación (true=éxito, false=error)
    private final MutableLiveData<Boolean> resultadoAsignacion = new MutableLiveData<>();

    // Id de la estantería actual (se establecerá desde el fragment con setIdEstanteria())
    private int idEstanteriaActual = -1;

    public ProductosDisponiblesViewModel() {
        // Cuando cambian los “allProductosLiveData” o cuando setIdEstanteriaActual cambie, actualizar el filtrado
        productosFiltrados.addSource(allProductosLiveData, new Observer<List<Producto>>() {
            @Override
            public void onChanged(List<Producto> lista) {
                filtrar(lista);
            }
        });
    }

    /**
     * Llamar desde el fragment para fijar el id de la estantería que nos interesa.
     */
    public void setIdEstanteria(int id) {
        this.idEstanteriaActual = id;
        // Forzar filtrado (tomamos valor actual de allProductosLiveData.getValue())
        filtrar(allProductosLiveData.getValue());
    }

    private void filtrar(List<Producto> listaOriginal) {
        if (listaOriginal == null) {
            productosFiltrados.setValue(new ArrayList<>());
            return;
        }
        List<Producto> disponibles = new ArrayList<>();
        for (Producto p : listaOriginal) {
            // Si el producto NO está ya en esta estantería (p.getEstanteria()==null o distinto)
            if (p.getEstanteria() == null || p.getEstanteria().getId_estanteria() != idEstanteriaActual) {
                disponibles.add(p);
            }
        }
        productosFiltrados.setValue(disponibles);
    }

    /** LiveData que expone la lista filtrada de productos disponibles */
    public LiveData<List<Producto>> getProductosFiltrados() {
        return productosFiltrados;
    }

    /** LiveData que expone el resultado de la llamada de asignación */
    public LiveData<Boolean> getResultadoAsignacion() {
        return resultadoAsignacion;
    }

    /**
     * Lanza la asignación de varios IDs de producto a la estantería actual.
     */
    public void asignarProductos(List<Integer> idsProductos) {
        // Llamamos al repositorio y observamos el LiveData devuelto
        LiveData<Boolean> llamada = repo.asignarProductos(idEstanteriaActual, idsProductos);
        // Cuando se complete, volcar el resultado en resultadoAsignacion
        llamada.observeForever(new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean ok) {
                resultadoAsignacion.setValue(ok);
                // Opcional: si fue exitoso, refrescar la lista completa de productos
                if (ok) {
                    // Vuelvo a forzar recarga en el repositorio
                    // (podríamos exponer un método repo.fetchAllProductos() que hojee de nuevo)
                    // Pero aquí podemos hacer:
                    //   1) volcar a null resultadoAsignación para no reenviar la misma señal,
                    //   2) disparar la recarga de productos:
                    //      “allProductosLiveData” está fijado al fetchAllProductos() de constructor,
                    //      así que para forzar recarga podríamos exponer un método en repo que lo haga de nuevo.
                    // Por simplicidad, asumiremos que allProductosLiveData ya recibe nuevos datos automáticamente
                    // (por ejemplo, si el servidor actualiza el campo estantería, y luego refrescamos).
                }
            }
        });
    }
}
