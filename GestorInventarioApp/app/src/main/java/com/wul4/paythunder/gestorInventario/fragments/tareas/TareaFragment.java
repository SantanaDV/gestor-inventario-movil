package com.wul4.paythunder.gestorInventario.fragments.tareas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.wul4.paythunder.gestorInventario.R;
import com.wul4.paythunder.gestorInventario.databinding.FragmentTareaBinding;
import com.wul4.paythunder.gestorInventario.fragments.tareas.TareaViewModel;
import com.wul4.paythunder.gestorInventario.response.TareaResponse;
import com.wul4.paythunder.gestorInventario.utils.ApiClient;
import com.wul4.paythunder.gestorInventario.utils.interfaces.ApiTarea;

// La clase Tarea Fragment es un Fragment que representa la pantalla principal de la aplicación.


public class TareaFragment extends Fragment {

    private FragmentTareaBinding binding;
    private RecyclerView tvCategoriaHacer, tvCategoriaProceso, tvCategoriaRealizada;
    private TareaAdapter adaptertvCategoriaHacer, adaptertvCategoriaProceso, adaptertvCategoriaRealizada;
    private TareaViewModel tareaViewModel;


//    private TextView tvCategoriaHacer;
//    private TextView tvCategoriaProceso;
//    private TextView tvCategoriaRealizada;

    // Este método es llamado cuando el fragmento necesita crear su vista.

    // Se infla el layout del fragmento utilizando FragmentTareaBinding.
    // Esto permite acceder a las vistas definidas en el layout a través de la variable binding.

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTareaBinding.inflate(inflater, container, false);
        return binding.getRoot(); // Inflamos la vista, pero la lógica se mueve a onViewCreated
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Referencias a los TextView de categorías
        tvCategoriaHacer = view.findViewById(R.id.tvCategoriaHacer);
        tvCategoriaProceso = view.findViewById(R.id.tvCategoriaProceso);
        tvCategoriaRealizada = view.findViewById(R.id.tvCategoriaRealizada);

        /*
        ojo a partir de aqui se modifica para que se pueda ver la lista de las otras fragment
         */

        adaptertvCategoriaHacer = new tvCategoriaAdapter();
        adaptertvCategoriaProceso = new tvCategoriaAdapter();
        adaptertvCategoriaRealizada = new tvCategoriaAdapter();


        TareaViewModel tareaViewModel = new ViewModelProvider(this).get(TareaViewModel.class); // Inicializar ViewModel

        // Crear instancia del servicio API y del Response que maneja las llamadas
        ApiTarea apiTarea = ApiClient.getClient().create(ApiTarea.class);
        TareaResponse tareaResponse= new TareaResponse(tareaViewModel, apiTarea);
        tareaResponse.fetchAllData(); // Llamada para obtener los datos


        tareaViewModel.getTareasHacer().observe(getViewLifecycleOwner(), categoriaHacer ->{
            this.tvCategoriaHacer.setText(String.valueOf(categoriaHacer));
        });

        tareaViewModel.getTareasProceso().observe(getViewLifecycleOwner(), categoriaProceso ->{
            this.tvCategoriaProceso.setText(String.valueOf(categoriaProceso));
        });

        tareaViewModel.getTareasRealizadas().observe(getViewLifecycleOwner(), categoriaRealizada ->{
            this.tvCategoriaRealizada.setText(String.valueOf(categoriaRealizada));
        });



    }
    // Este método es llamado cuando la vista del fragmento es destruida.
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}
