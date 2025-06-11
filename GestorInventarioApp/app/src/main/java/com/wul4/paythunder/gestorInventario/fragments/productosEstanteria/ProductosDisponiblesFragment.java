package com.wul4.paythunder.gestorInventario.fragments.productosEstanteria;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.wul4.paythunder.gestorInventario.databinding.FragmentProductosDisponiblesBinding;
import com.wul4.paythunder.gestorInventario.response.ProductoResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Fragment que:
 * - muestra productos disponibles,
 * - permite seleccionarlos,
 * - abre un diálogo para pedir “balda” por seleccionado,
 * - llama al ViewModel para asignar.
 */
public class ProductosDisponiblesFragment extends Fragment {

    private FragmentProductosDisponiblesBinding binding;
    private ProductosDisponiblesViewModel viewModel;
    private ProductosDisponiblesAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProductosDisponiblesBinding.inflate(inflater, container, false);

        int idEstanteriaActual = ProductosDisponiblesFragmentArgs
                .fromBundle(getArguments()).getIdEstanteria();

        viewModel = new ViewModelProvider(this).get(ProductosDisponiblesViewModel.class);
        viewModel.setIdEstanteria(idEstanteriaActual);

        adapter = new ProductosDisponiblesAdapter();
        binding.recyclerProductosDisponibles.setLayoutManager(
                new LinearLayoutManager(requireContext())
        );
        binding.recyclerProductosDisponibles.setAdapter(adapter);

        // Observa lista filtrada
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

        // Observa resultado de asignación
        viewModel.getResultadoAsignacion().observe(getViewLifecycleOwner(), exito -> {
            if (exito != null) {
                String msg = exito
                        ? "Productos asignados con éxito"
                        : "Error al asignar productos";
                Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show();
                if (exito) requireActivity().onBackPressed();
                else binding.fabAsignarProductos.setEnabled(true);
            }
        });

        // FAB → abre diálogo de baldas
        binding.fabAsignarProductos.setOnClickListener(v -> {
            List<ProductoResponse> sel = adapter.getProductosSeleccionados();
            if (sel.isEmpty()) {
                Toast.makeText(requireContext(),
                        "Debes seleccionar al menos un producto",
                        Toast.LENGTH_SHORT).show();
            } else {
                mostrarDialogoBalda(sel);
            }
        });

        return binding.getRoot();
    }

    private void mostrarDialogoBalda(List<ProductoResponse> seleccionados) {
        LinearLayout container = new LinearLayout(requireContext());
        container.setOrientation(LinearLayout.VERTICAL);
        int pad = (int)(16 * getResources().getDisplayMetrics().density);
        container.setPadding(pad,pad,pad,pad);

        final Map<Integer,EditText> baldasMap = new HashMap<>();
        for (ProductoResponse p: seleccionados) {
            LinearLayout row = new LinearLayout(requireContext());
            row.setOrientation(LinearLayout.HORIZONTAL);
            row.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            row.setPadding(0,pad/2,0,pad/2);

            TextView tv = new TextView(requireContext());
            tv.setLayoutParams(new LinearLayout.LayoutParams(0,
                    ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
            tv.setText(p.getNombre());
            tv.setTextSize(16f);

            EditText et = new EditText(requireContext());
            et.setLayoutParams(new LinearLayout.LayoutParams(
                    (int)(80*getResources().getDisplayMetrics().density),
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            et.setHint("Balda");
            et.setInputType(InputType.TYPE_CLASS_NUMBER);
            et.setSingleLine(true);

            baldasMap.put(p.getId(), et);
            row.addView(tv);
            row.addView(et);
            container.addView(row);
        }

        AlertDialog.Builder b = new AlertDialog.Builder(requireContext())
                .setTitle("Asignar balda y confirmar")
                .setView(container)
                .setNegativeButton("Cancelar", (d,w)-> {
                    d.dismiss();
                    binding.fabAsignarProductos.setEnabled(true);
                })
                .setPositiveButton("Asignar", null);

        AlertDialog dlg = b.create();
        dlg.setOnShowListener(di -> {
            dlg.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v2->{
                // validación
                for (EditText et: baldasMap.values()){
                    String t=et.getText().toString().trim();
                    if (t.isEmpty()){
                        Toast.makeText(requireContext(),
                                "Debes introducir balda en cada producto",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                // extraer IDs y baldas
                List<Integer> ids = adapter.getIdsSeleccionados();
                Map<Integer,Integer> baldas = new HashMap<>();
                for (Map.Entry<Integer,EditText> e: baldasMap.entrySet()){
                    baldas.put(e.getKey(), Integer.parseInt(e.getValue().getText().toString()));
                }
                // llamar
                viewModel.asignarProductosConBaldas(ids, baldas);
                binding.fabAsignarProductos.setEnabled(false);
                dlg.dismiss();
            });
        });
        dlg.show();
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
