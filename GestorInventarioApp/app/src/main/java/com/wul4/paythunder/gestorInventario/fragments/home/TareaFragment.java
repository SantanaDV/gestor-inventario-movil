package com.wul4.paythunder.gestorInventario.fragments.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.wul4.paythunder.gestorInventario.R;

public class TareaFragment extends Fragment {

    private TextView tvCategoria, tvCategoria2, tvCategoria3;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tarea, container, false);

        // Referencias a los TextView de categorías
        tvCategoria = view.findViewById(R.id.tvCategoria);
        tvCategoria2 = view.findViewById(R.id.tvCategoria2);
        tvCategoria3 = view.findViewById(R.id.tvCategoria3);

        // Navegación al hacer clic en cada sección
        tvCategoria.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.tarea_fragmentPorHacer));

        tvCategoria2.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.tarea_fragmentProceso));

        tvCategoria3.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.tarea_fragmentRealizadas));

        return view;
    }
}
