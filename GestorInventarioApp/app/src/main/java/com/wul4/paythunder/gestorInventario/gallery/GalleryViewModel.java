package com.wul4.paythunder.gestorInventario.gallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

<<<<<<< HEAD:GestorInventarioApp/app/src/main/java/com/wul4/paythunder/gestorInventario/gallery/GalleryViewModel.java
public class GalleryViewModel extends ViewModel {
=======
import java.text.BreakIterator;

public class HomeViewModel extends ViewModel {
>>>>>>> e21d536 (03/04):paythunder-hello-world/app/src/main/java/com/wul4/paythunder/helloworld/home/HomeViewModel.java

    private final MutableLiveData<String> mText = new MutableLiveData<>();
    //public BreakIterator getTotalProductos;
    private final MutableLiveData<String> Text = new MutableLiveData<>();

    // LiveData para cada uno de los datos que quieres mostrar
    private final MutableLiveData<Integer> totalProductos = new MutableLiveData<>();
    private final MutableLiveData<Integer> existencias = new MutableLiveData<>();
    private final MutableLiveData<Integer> faltantes = new MutableLiveData<>();
    private final MutableLiveData<Integer> usuariosActivos = new MutableLiveData<>();

<<<<<<< HEAD:GestorInventarioApp/app/src/main/java/com/wul4/paythunder/gestorInventario/gallery/GalleryViewModel.java
    public GalleryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
=======
    public HomeViewModel() {
        //mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");

        // Inicializar los datos con valores de ejemplo (esto podría venir de una base de datos o API)
        totalProductos.setValue(100); // Ejemplo: Total de productos
        existencias.setValue(80);     // Ejemplo: Productos en existencia
        faltantes.setValue(20);       // Ejemplo: Productos faltantes
        usuariosActivos.setValue(10); // Ejemplo: Usuarios activos
    }
    // Métodos para obtener los LiveData
    public LiveData<Integer> getTotalProductos() {
        return totalProductos;
    }

    public LiveData<Integer> getExistencias() {
        return existencias;
>>>>>>> e21d536 (03/04):paythunder-hello-world/app/src/main/java/com/wul4/paythunder/helloworld/home/HomeViewModel.java
    }

    public MutableLiveData<String> getText() {
        return Text;
    }

    public LiveData<Integer> getFaltantes() {
        return faltantes;
    }

    public LiveData<Integer> getUsuariosActivos() {
        return usuariosActivos;
    }
}



