package com.wul4.paythunder.gestorInventario.fragments.tareas;

import android.os.Bundle;
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

public class TareaProcesoFragment extends Fragment {

    private RecyclerView recyclerView;
    private TareaViewModel tareaViewModel;
    private Button btnCrearTarea, btnEliminarTarea;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tarea_en_proceso, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerViewProceso);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        tareaViewModel = new ViewModelProvider(this).get(TareaViewModel.class);

        ApiTarea apiTarea = ApiClient.getClient().create(ApiTarea.class);
        TareaResponse tareaResponse = new TareaResponse(tareaViewModel, apiTarea);
        tareaResponse.fetchAllData();

        TareaAdapter adapter = new TareaAdapter(tareaSeleccionada -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("tarea_seleccionada", tareaSeleccionada);

            Fragment detalleFragment = new TareaBaseFragment();
            detalleFragment.setArguments(bundle);

            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment_content_main, detalleFragment)
                    .addToBackStack(null)
                    .commit();
        });

        recyclerView.setAdapter(adapter);

        tareaViewModel.getListarTareaProceso().observe(getViewLifecycleOwner(), tareas -> {
            if (tareas != null) {
                adapter.setTareaList(tareas);
            }
        });

        // Botones
        btnCrearTarea = view.findViewById(R.id.btnCrearTarea);
        btnEliminarTarea = view.findViewById(R.id.btnEliminarTarea);

        btnCrearTarea.setOnClickListener(v -> {
            Fragment tareaForm = new TareaBaseFragment();
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment_content_main, tareaForm)
                    .addToBackStack(null)
                    .commit();
        });

        btnEliminarTarea.setOnClickListener(v ->
                Toast.makeText(getContext(), "Funcionalidad de eliminar aún no implementada", Toast.LENGTH_SHORT).show());
    }
}
