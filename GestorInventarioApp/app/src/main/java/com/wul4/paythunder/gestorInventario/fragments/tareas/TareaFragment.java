package com.wul4.paythunder.gestorInventario.fragments.tareas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.wul4.paythunder.gestorInventario.R;

public class TareaFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tarea, container, false); // Este es tu layout con los 3 bloques
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Referencias a secciones
        TextView categoriaHacer = view.findViewById(R.id.categoriaHacer);
        categoriaHacer.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
            navController.navigate(R.id.action_tareaFragment_to_tareaHacerFragment);

        });

        TextView categoriaProceso = view.findViewById(R.id.categoriaProceso);
        categoriaProceso.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
            navController.navigate(R.id.action_tareaFragment_to_tareaProcesoFragment);

        });

        TextView categoriaRealizada = view.findViewById(R.id.categoriaRealizada);
        categoriaRealizada.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
            navController.navigate(R.id.action_tareaFragment_to_tareaRealizadaFragment);

        });

        FragmentManager fragmentManager = getChildFragmentManager();

//        // Cargar los fragmentos por defecto
//        fragmentManager.beginTransaction()
//                .replace(R.id.containerTareasPorHacer, new TareaHacerFragment())
//                .commit();
//
//        fragmentManager.beginTransaction()
//                .replace(R.id.containerTareasEnProceso, new TareaProcesoFragment())
//                .commit();
//
//        fragmentManager.beginTransaction()
//                .replace(R.id.containerTareasRealizadas, new TareaRealizadaFragment())
//                .commit();

    }
}
