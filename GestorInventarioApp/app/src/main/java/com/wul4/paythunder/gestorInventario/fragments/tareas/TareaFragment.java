package com.wul4.paythunder.gestorInventario.fragments.tareas;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wul4.paythunder.gestorInventario.R;
import com.wul4.paythunder.gestorInventario.databinding.FragmentTareaBinding;
import com.wul4.paythunder.gestorInventario.response.TareaResponse;
import com.wul4.paythunder.gestorInventario.utils.ApiClient;
import com.wul4.paythunder.gestorInventario.utils.interfaces.ApiTarea;

// La clase Tarea Fragment es un Fragment que representa la pantalla principal de la aplicación.


public class TareaFragment extends Fragment {

    private FragmentTareaBinding binding;

    private RecyclerView categoriaHacer, categoriaproceso, categoriaRealizada;
    private TareaAdapter adapterCategoriaHacer, adapterCategoriaProceso, adapterCategoriaRealizada;
    private TareaViewModel tareaViewModel;



    // Este método es llamado cuando el fragmento necesita crear su vista.

    // Se infla el layout del fragmento utilizando FragmentTareaBinding.
    // Esto permite acceder a las vistas definidas en el layout a través de la variable binding.

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTareaBinding.inflate(inflater, container, false);
        return binding.getRoot(); // Inflamos la vista, pero la lógica se mueve a onViewCreated
    }

    @SuppressLint("WrongViewCast")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Referencias a los TextView de categorías
        categoriaHacer = view.findViewById(R.id.categoriaHacer);
        categoriaproceso = view.findViewById(R.id.categoriaProceso);
        categoriaRealizada = view.findViewById(R.id.categoriaRealizada);


        adapterCategoriaHacer = new TareaAdapter();
        adapterCategoriaProceso = new TareaAdapter();
        adapterCategoriaRealizada = new TareaAdapter();

//       esto hace que los elementos se muestren en una lista lineal, es decir, uno debajo del otro (en vertical)
        categoriaHacer.setLayoutManager(new LinearLayoutManager(getContext()));
        categoriaproceso.setLayoutManager(new LinearLayoutManager(getContext()));
        categoriaRealizada.setLayoutManager(new LinearLayoutManager(getContext()));


        tareaViewModel = new ViewModelProvider(this).get(TareaViewModel.class); // Inicializar ViewModel

        // Crear instancia del servicio API y del Response que maneja las llamadas
        ApiTarea apiTarea = ApiClient.getClient().create(ApiTarea.class);
        TareaResponse tareaResponse= new TareaResponse(tareaViewModel, apiTarea);
        tareaResponse.fetchAllData(); // Llamada para obtener los datos

        tareaViewModel.getListarTareaHacer().observe(getViewLifecycleOwner(), tareasHacer -> {
            adapterCategoriaHacer.setTareaList(tareasHacer);
            categoriaHacer.setAdapter(adapterCategoriaHacer);
        });

        tareaViewModel.getListarTareaProceso().observe(getViewLifecycleOwner(), tareasProceso -> {
            adapterCategoriaProceso.setTareaList(tareasProceso);
            categoriaproceso.setAdapter(adapterCategoriaProceso);
        });

        tareaViewModel.getListarTareaRealizada().observe(getViewLifecycleOwner(), tareasRealizadas -> {
            adapterCategoriaRealizada.setTareaList(tareasRealizadas);
            categoriaRealizada.setAdapter(adapterCategoriaRealizada);
        });




    }
    // Este método es llamado cuando la vista del fragmento es destruida.
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}
