package com.wul4.paythunder.gestorInventario.fragments.productosEstanteria;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.wul4.paythunder.gestorInventario.databinding.FragmentProductosEstanteriaBinding;
import com.wul4.paythunder.gestorInventario.response.ProductoResponse;

public class ProductosEstanteriaFragment extends Fragment {

    private FragmentProductosEstanteriaBinding binding;
    private ProductosEstanteriaViewModel viewModel;
    private ProductosEstanteriaAdapter adapter;
    private int idEstanteria;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProductosEstanteriaBinding.inflate(inflater, container, false);

        // 1) Recuperar idEstanteria vía SafeArgs
        idEstanteria = ProductosEstanteriaFragmentArgs
                .fromBundle(getArguments())
                .getIdEstanteria();

        // 2) Instanciar ViewModel
        viewModel = new ViewModelProvider(this).get(ProductosEstanteriaViewModel.class);

        // 3) Configurar RecyclerView + Adapter
        adapter = new ProductosEstanteriaAdapter(new ProductosEstanteriaAdapter.OnItemAction() {
            @Override
            public void onEditarBalda(ProductoResponse p) {
                showEditarBaldaDialog(p);
            }
            @Override
            public void onEliminarDeEstanteria(ProductoResponse p) {
                viewModel.eliminarDeEstanteria(p.getId(), idEstanteria);
            }
        });
        binding.recyclerProductosEstanteria.setLayoutManager(
                new LinearLayoutManager(requireContext())
        );
        binding.recyclerProductosEstanteria.setAdapter(adapter);

        // 4) Observar lista de productos en esta estantería
        viewModel.getProductos().observe(getViewLifecycleOwner(), lista -> {
            binding.progressProductosEstanteria.setVisibility(View.GONE);
            if (lista != null && !lista.isEmpty()) {
                adapter.setDatos(lista);
                binding.recyclerProductosEstanteria.setVisibility(View.VISIBLE);
                binding.tvSinProductosEstanteria.setVisibility(View.GONE);
            } else {
                binding.recyclerProductosEstanteria.setVisibility(View.GONE);
                binding.tvSinProductosEstanteria.setVisibility(View.VISIBLE);
            }
        });
        viewModel.loadProductos(idEstanteria);

        // 5) Observar resultado de operación (editar/eliminar)
        viewModel.getResultado().observe(getViewLifecycleOwner(), ok -> {
            if (ok == null) return;
            Toast.makeText(requireContext(),
                    ok ? "Operación exitosa" : "Error en la operación",
                    Toast.LENGTH_SHORT
            ).show();
            if (ok) {
                // refrescamos la lista en sitio
                viewModel.loadProductos(idEstanteria);
            } else {
                // reactivar el FAB por si acaso
                binding.fabAAdirProductos.setEnabled(true);
            }
        });

        // 6) FAB “+ Añadir” vuelve a navegar al fragment de ProductosDisponibles
        binding.fabAAdirProductos.setOnClickListener(v -> {
            ProductosEstanteriaFragmentDirections
                    .ActionNavProductosEstanteriaToNavProductosDisponibles action =
                    ProductosEstanteriaFragmentDirections
                            .actionNavProductosEstanteriaToNavProductosDisponibles(idEstanteria);
            NavHostFragment.findNavController(this).navigate(action);
        });

        return binding.getRoot();
    }

    private void showEditarBaldaDialog(ProductoResponse p) {
        EditText et = new EditText(requireContext());
        et.setInputType(InputType.TYPE_CLASS_NUMBER);
        et.setHint("Nueva balda");
        new AlertDialog.Builder(requireContext())
                .setTitle("Editar balda de " + p.getNombre())
                .setView(et)
                .setNegativeButton("Cancelar", null)
                .setPositiveButton("Guardar", (d, i) -> {
                    String s = et.getText().toString().trim();
                    if (s.isEmpty()) {
                        Toast.makeText(requireContext(),
                                "Introduce un número válido",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    int nuevaBalda = Integer.parseInt(s);
                    viewModel.editarBalda(p.getId(), idEstanteria, nuevaBalda);
                })
                .show();
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
