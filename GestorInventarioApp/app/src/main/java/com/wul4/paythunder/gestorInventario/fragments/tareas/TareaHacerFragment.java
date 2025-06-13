

package com.wul4.paythunder.gestorInventario.fragments.tareas;

import android.os.Bundle;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wul4.paythunder.gestorInventario.R;
import com.wul4.paythunder.gestorInventario.entities.Tarea;
import com.wul4.paythunder.gestorInventario.response.TareaResponse;
import com.wul4.paythunder.gestorInventario.utils.ApiClient;
import com.wul4.paythunder.gestorInventario.utils.interfaces.ApiTarea;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TareaHacerFragment extends Fragment {

    private RecyclerView recyclerView;
    private TareaAdapter tareaAdapter;
    private TareaViewModel tareaViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tarea_hacer, container, false); // Asegúrate de tener este layout
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tareaViewModel = new ViewModelProvider(requireActivity()).get(TareaViewModel.class);

        recyclerView = view.findViewById(R.id.recyclerViewHacer);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        tareaAdapter = new TareaAdapter(tarea -> {
            // Abrir el formulario con la tarea seleccionada
            Bundle bundle = new Bundle();
            bundle.putSerializable("tarea_seleccionada", tarea);

            Fragment tareaForm = new TareaBaseFragment();
            tareaForm.setArguments(bundle);

            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment_content_main, tareaForm)
                    .addToBackStack(null)
                    .commit();
        });

        recyclerView.setAdapter(tareaAdapter);

        // Cargar datos desde la API
        cargarTareasPorHacer();

        // Observar el ViewModel
        tareaViewModel.getListarTareaHacer().observe(getViewLifecycleOwner(), tareas -> {
            if (tareas != null) {
                tareaAdapter.setTareaList(tareas);
            }
        });

        Button btnCrearTarea = view.findViewById(R.id.btnCrearTarea);
        Button btnEliminarTarea = view.findViewById(R.id.btnEliminarTarea);

// Al pulsar en Crear Tarea, abrimos el formulario vacío
        btnCrearTarea.setOnClickListener(v -> {
            Fragment tareaForm = new TareaBaseFragment();
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment_content_main, tareaForm)
                    .addToBackStack(null)
                    .commit();
        });

// Eliminar Tarea: puedes implementar lógica más adelante si seleccionas una desde el adapter
        btnEliminarTarea.setOnClickListener(v ->
                Toast.makeText(getContext(), "Funcionalidad de eliminar aún no implementada", Toast.LENGTH_SHORT).show());

    }

    private void cargarTareasPorHacer() {
        ApiTarea api = ApiClient.getClient().create(ApiTarea.class);
        Call<List<Tarea>> call = api.getlistarTareaHacer();

        call.enqueue(new Callback<List<Tarea>>() {
            @Override
            public void onResponse(@NonNull Call<List<Tarea>> call, @NonNull Response<List<Tarea>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    tareaViewModel.getListarTareaHacer().setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Tarea>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Error al cargar tareas: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
