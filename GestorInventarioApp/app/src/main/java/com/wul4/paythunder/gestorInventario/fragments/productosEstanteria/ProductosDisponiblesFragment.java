package com.wul4.paythunder.gestorInventario.fragments.productosEstanteria;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.wul4.paythunder.gestorInventario.databinding.FragmentProductosDisponiblesBinding;

import java.util.List;

public class ProductosDisponiblesFragment extends Fragment {

    private FragmentProductosDisponiblesBinding binding;
    private ProductosDisponiblesViewModel viewModel;
    private ProductosDisponiblesAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProductosDisponiblesBinding.inflate(inflater, container, false);

        // 1) Recuperar idEstanteria vía SafeArgs
        int idEstanteriaActual = ProductosDisponiblesFragmentArgs
                .fromBundle(getArguments())
                .getIdEstanteria();

        // 2) Instanciar ViewModel y pasarle el id de la estantería
        viewModel = new ViewModelProvider(this).get(ProductosDisponiblesViewModel.class);
        viewModel.setIdEstanteria(idEstanteriaActual);

        // 3) Configurar RecyclerView
        adapter = new ProductosDisponiblesAdapter();
        binding.recyclerProductosDisponibles.setLayoutManager(
                new LinearLayoutManager(requireContext())
        );
        binding.recyclerProductosDisponibles.setAdapter(adapter);

        // 4) Observar lista filtrada de ProductoResponse
        viewModel.getProductosFiltrados().observe(getViewLifecycleOwner(), lista -> {
            binding.progressProductosDisponibles.setVisibility(View.GONE);
            if (lista != null && !lista.isEmpty()) {
                adapter.setDatos(lista);
                binding.recyclerProductosDisponibles.setVisibility(View.VISIBLE);
                binding.tvSinProductosDisponibles.setVisibility(View.GONE);
            } else {
                binding.recyclerProductosDisponibles.setVisibility(View.GONE);
                binding.tvSinProductosDisponibles.setVisibility(View.VISIBLE);
            }
        });

        // 5) Observar resultado de la asignación
        viewModel.getResultadoAsignacion().observe(getViewLifecycleOwner(), exito -> {
            if (exito == null) return;
            if (exito) {
                Toast.makeText(requireContext(),
                        "Productos asignados con éxito", Toast.LENGTH_SHORT).show();
                requireActivity().onBackPressed();
            } else {
                Toast.makeText(requireContext(),
                        "Error al asignar productos", Toast.LENGTH_SHORT).show();
                binding.fabAsignarProductos.setEnabled(true);
            }
        });

        // 6) Pulsar el FAB: recojo IDs marcados y pido la asignación
        binding.fabAsignarProductos.setOnClickListener(v -> {
            List<Integer> ids = adapter.getIdsSeleccionados();
            if (ids.isEmpty()) {
                Toast.makeText(requireContext(),
                        "Debes seleccionar al menos un producto", Toast.LENGTH_SHORT).show();
                return;
            }
            binding.fabAsignarProductos.setEnabled(false);
            viewModel.asignarProductos(ids);
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
