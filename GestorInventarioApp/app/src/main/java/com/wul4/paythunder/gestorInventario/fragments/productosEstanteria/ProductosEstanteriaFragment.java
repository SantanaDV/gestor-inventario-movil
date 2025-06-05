// ProductosEstanteriaFragment.java
package com.wul4.paythunder.gestorInventario.fragments.productosEstanteria;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wul4.paythunder.gestorInventario.databinding.FragmentProductosEstanteriaBinding;
import com.wul4.paythunder.gestorInventario.response.ProductoResponse;

public class ProductosEstanteriaFragment extends Fragment {

    private FragmentProductosEstanteriaBinding binding;
    private ProductosEstanteriaViewModel viewModel;
    private ProductosEstanteriaAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProductosEstanteriaBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(ProductosEstanteriaViewModel.class);

        // Safe args — asegúrate de haber rebuild para que exista ésta clase
        int idEstanteria = ProductosEstanteriaFragmentArgs
                .fromBundle(getArguments())
                .getIdEstanteria();

        setupRecyclerView();
        observeProductos(idEstanteria);

        binding.fabAAdirProductos.setOnClickListener(v -> {
            // Creamos la acción SafeArgs:
            ProductosEstanteriaFragmentDirections
                    .ActionNavProductosEstanteriaToNavProductosDisponibles action =
                    ProductosEstanteriaFragmentDirections
                            .actionNavProductosEstanteriaToNavProductosDisponibles(idEstanteria);

            // Ejecutamos la navegación:
            NavHostFragment.findNavController(this).navigate(action);
        });

        return binding.getRoot();
    }

    private void setupRecyclerView() {
        adapter = new ProductosEstanteriaAdapter(producto -> {
            // Aquí gestionas la desasignación; por ejemplo:
            Toast.makeText(
                    requireContext(),
                    "Desasignar " + producto.getNombre(),
                    Toast.LENGTH_SHORT
            ).show();
            // luego podrás llamar a tu ViewModel para el delete…
        });
        binding.recyclerProductosEstanteria.setAdapter(adapter);
        binding.recyclerProductosEstanteria.setLayoutManager(
                new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        );

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
