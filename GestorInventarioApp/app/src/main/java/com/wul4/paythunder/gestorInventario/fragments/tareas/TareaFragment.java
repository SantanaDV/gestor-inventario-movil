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
        LinearLayout layoutHacer = view.findViewById(R.id.tareaHacer);
        categoriaHacer.setOnClickListener(v -> {
            layoutHacer.setVisibility(
                    layoutHacer.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE
            );
        });

        TextView categoriaProceso = view.findViewById(R.id.categoriaProceso);
        LinearLayout layoutProceso = view.findViewById(R.id.tareaProceso);
        categoriaProceso.setOnClickListener(v -> {
            layoutProceso.setVisibility(
                    layoutProceso.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE
            );
        });

        TextView categoriaRealizada = view.findViewById(R.id.categoriaRealizada);
        LinearLayout layoutRealizada = view.findViewById(R.id.tareaRealizada);
        categoriaRealizada.setOnClickListener(v -> {
            layoutRealizada.setVisibility(
                    layoutRealizada.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE
            );
        });

        FragmentManager fragmentManager = getChildFragmentManager();

        // Cargar los fragmentos por defecto
        fragmentManager.beginTransaction()
                .replace(R.id.containerTareasPorHacer, new TareaHacerFragment())
                .commit();

        fragmentManager.beginTransaction()
                .replace(R.id.containerTareasEnProceso, new TareaProcesoFragment())
                .commit();

        fragmentManager.beginTransaction()
                .replace(R.id.containerTareasRealizadas, new TareaRealizadaFragment())
                .commit();

    }
}
