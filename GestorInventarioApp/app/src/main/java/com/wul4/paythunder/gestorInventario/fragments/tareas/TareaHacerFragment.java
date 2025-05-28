

package com.wul4.paythunder.gestorInventario.fragments.tareas;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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

public class TareaHacerFragment extends Fragment {

    private RecyclerView recyclerView;
    private TareaViewModel tareaViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tarea_hacer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerViewTareas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // ViewModel
        tareaViewModel = new ViewModelProvider(this).get(TareaViewModel.class);
        ApiTarea apiTarea = ApiClient.getClient().create(ApiTarea.class);
        TareaResponse tareaResponse = new TareaResponse(tareaViewModel, apiTarea);
        tareaResponse.fetchAllData();

        // Adapter con clic por tarea
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

        tareaViewModel.getListarTareaHacer().observe(getViewLifecycleOwner(), tareas -> {
            if (tareas != null) {
                Log.d("TAREAS", "Cantidad: " + tareas.size());
                adapter.setTareaList(tareas);
            }
        });
    }
}

//
//public class TareaHacerFragment extends Fragment {
//
//    private FragmentTareaBinding binding;
//    private RecyclerView recyclerView;
//
//    private RecyclerView recyclerHacer, recyclerProceso, recyclerRealizada;
//    private TareaAdapter adapterHacer, adapterProceso, adapterRealizada;
//    private TareaViewModel tareaViewModel;
//    private View btnPorHacer;
//    private View btnProceso;
//    private View btnRealizada;
//
//
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_tarea_hacer, container, false);
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//
//        recyclerView = view.findViewById(R.id.recyclerViewTareas);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//
//        tareaViewModel = new ViewModelProvider(this).get(TareaViewModel.class);
//        ApiTarea apiTarea = ApiClient.getClient().create(ApiTarea.class);
//        TareaResponse tareaResponse = new TareaResponse(tareaViewModel, apiTarea);
//        tareaResponse.fetchAllData();
//
//
//        // Adapter con acción al hacer clic en tarea
//        TareaAdapter adapter = new TareaAdapter(tareaSeleccionada -> {
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("tarea_seleccionada", tareaSeleccionada);
//
//            Fragment detalleFragment = new TareaBaseFragment();
//            detalleFragment.setArguments(bundle);
//
//            requireActivity().getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.containerBaseTarea, detalleFragment)
//                    .addToBackStack(null)
//                    .commit();
//
//            // Crear nueva tarea
//            Button btnCrear = view.findViewById(R.id.btnCrearNuevaTarea);
//            btnCrear.setOnClickListener(v -> {
//                Fragment crearFragment = new TareaBaseFragment();
//
//                requireActivity().getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.nav_host_fragment_content_main, crearFragment)
//                        .addToBackStack(null)
//                        .commit();
//            });
//
//            recyclerView.setAdapter(adapter);// Observar tareas por hacer
//            tareaViewModel.getListarTareaHacer().observe(getViewLifecycleOwner(), tareas -> {
//                if (tareas != null) {
//                    Log.d("TAREAS", "Cantidad: " + tareas.size()); // AÑADE ESTO
//                    adapter.setTareaList(tareas);
//                }
//            });
//
//
//            Button btnEliminar = view.findViewById(R.id.btnCrearNuevaTarea);
//            btnCrear.setOnClickListener(v -> {
//                Fragment crearFragment = new TareaBaseFragment();
//
//                requireActivity().getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.nav_host_fragment_content_main, crearFragment)
//                        .addToBackStack(null)
//                        .commit();
//            });
//        });
//    }
//}
//
