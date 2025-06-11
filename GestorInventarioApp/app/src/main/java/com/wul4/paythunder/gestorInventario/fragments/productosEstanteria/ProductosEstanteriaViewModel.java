package com.wul4.paythunder.gestorInventario.fragments.productosEstanteria;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.wul4.paythunder.gestorInventario.response.ProductoResponse;

import java.util.List;

public class ProductosEstanteriaViewModel extends ViewModel {
    private final ProductosEstanteriaRepository repo = new ProductosEstanteriaRepository();

    private final MutableLiveData<List<ProductoResponse>> productos = new MutableLiveData<>();
    private final MutableLiveData<Boolean> resultado   = new MutableLiveData<>();

    public LiveData<List<ProductoResponse>> getProductos() { return productos; }
    public LiveData<Boolean> getResultado()           { return resultado; }

    public void loadProductos(int idEstanteria) {
        repo.fetchProductosPorEstanteria(idEstanteria)
                .observeForever(list -> productos.setValue(list));
    }

    public void editarBalda(int idProd, int idEstanteria, int balda) {
        repo.actualizarBalda(idProd, idEstanteria, balda)
                .observeForever(ok -> {
                    resultado.setValue(ok);
                    if (ok) loadProductos(idEstanteria);
                });
    }

    public void eliminarDeEstanteria(int idProd, int idEstanteria) {
        repo.desasignarProducto(idProd)
                .observeForever(ok -> {
                    resultado.setValue(ok);
                    if (ok) loadProductos(idEstanteria);
                });
    }
}
