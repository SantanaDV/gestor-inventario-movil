package com.wul4.paythunder.gestorInventario.fragments.estanterias;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import com.wul4.paythunder.gestorInventario.databinding.FragmentEstanteriasBinding;
import com.wul4.paythunder.gestorInventario.response.EstanteriaResponse;
import java.util.List;

public class EstanteriasFragment extends Fragment {

    private FragmentEstanteriasBinding binding;
    private EstanteriaViewModel viewModel;
    private EstanteriaAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEstanteriasBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(EstanteriaViewModel.class);

        // Recuperar idAlmacen de los argumentos
        int idAlmacen = EstanteriasFragmentArgs
                .fromBundle(getArguments())
                .getIdAlmacen();

        setupRecycler();
        observeEstanterias(idAlmacen);

        return binding.getRoot();
    }

    private void setupRecycler() {
        // Inicializamos el adapter con lista vacía
        adapter = new EstanteriaAdapter(List.of(), est -> {
            // Navegar a ProductosEstanteriaFragment pasando idEstanteria
            NavHostFragment.findNavController(this)
                    .navigate(
                            EstanteriasFragmentDirections
                                    .actionNavEstanteriasToNavProductosEstanteria(est.getId())
                    );
        });
        binding.recyclerEstanterias.setAdapter(adapter);
    }

    private void observeEstanterias(int idAlmacen) {
        // Mostramos progreso
        binding.progressEstanterias.setVisibility(View.VISIBLE);
        binding.recyclerEstanterias.setVisibility(View.GONE);
        binding.textSinEstanterias.setVisibility(View.GONE);

        viewModel.getEstanterias().observe(getViewLifecycleOwner(), lista -> {
            binding.progressEstanterias.setVisibility(View.GONE);
            if (lista != null && !lista.isEmpty()) {
                adapter.setDatos(lista);
                binding.recyclerEstanterias.setVisibility(View.VISIBLE);
                binding.textSinEstanterias.setVisibility(View.GONE);
            } else {
                // Mostrar mensaje “sin estanterías”
                binding.recyclerEstanterias.setVisibility(View.GONE);
                binding.textSinEstanterias.setVisibility(View.VISIBLE);
            }
        });

        viewModel.loadEstanterias(idAlmacen);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
