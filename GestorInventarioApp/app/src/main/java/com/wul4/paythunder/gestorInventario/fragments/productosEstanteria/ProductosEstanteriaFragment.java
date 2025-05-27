package com.wul4.paythunder.gestorInventario.fragments.productosEstanteria;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.wul4.paythunder.gestorInventario.databinding.FragmentProductosEstanteriaBinding;
import com.wul4.paythunder.gestorInventario.response.ProductoResponse;
import java.util.List;

public class ProductosEstanteriaFragment extends Fragment {

    private FragmentProductosEstanteriaBinding binding;
    private ProductosEstanteriaViewModel viewModel;
    private ProductoAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProductosEstanteriaBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(ProductosEstanteriaViewModel.class);

        // Obtén el argumento idEstanteria
        int idEstanteria = ProductosEstanteriaFragmentArgs
                .fromBundle(getArguments())
                .getIdEstanteria();

        setupRecyclerView();
        observeProductos(idEstanteria);

        return binding.getRoot();
    }

    private void setupRecyclerView() {
        adapter = new ProductoAdapter(List.of(), producto -> {
            // Aquí podrías manejar clic en un producto, si se desea
        });
        binding.recyclerProductosEstanteria.setAdapter(adapter);
    }

    private void observeProductos(int idEstanteria) {
        binding.progressProductosEstanteria.setVisibility(View.VISIBLE);
        binding.recyclerProductosEstanteria.setVisibility(View.GONE);
        binding.tvSinProductosEstanteria.setVisibility(View.GONE);

        viewModel.getProductos().observe(getViewLifecycleOwner(), lista -> {
            binding.progressProductosEstanteria.setVisibility(View.GONE);
            if (lista != null && !lista.isEmpty()) {
                adapter.setDatos(lista);
                binding.recyclerProductosEstanteria.setVisibility(View.VISIBLE);
            } else {
                binding.tvSinProductosEstanteria.setVisibility(View.VISIBLE);
            }
        });

        viewModel.loadProductos(idEstanteria);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
