package com.wul4.paythunder.gestorInventario.fragments.productosEstanteria;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
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
 * Fragment que muestra un listado de productos “disponibles” (filtrados por estantería),
 * permite seleccionarlos con checkbox y, al pulsar el FAB “Asignar productos”,
 * abre un diálogo para pedir “balda” únicamente de los productos marcados.
 */
public class ProductosDisponiblesFragment extends Fragment {

    private static final String TAG = "ProductosDisp";

    private FragmentProductosDisponiblesBinding binding;
    private ProductosDisponiblesViewModel viewModel;
    private ProductosDisponiblesAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProductosDisponiblesBinding.inflate(inflater, container, false);

        // Recuperar el ID de la estantería (via Safe Args)
        int idEstanteriaActual = ProductosDisponiblesFragmentArgs
                .fromBundle(getArguments())
                .getIdEstanteria();
        Log.d(TAG, "onCreateView: idEstanteriaActual=" + idEstanteriaActual);

        // Instanciar ViewModel y asignarle el ID de la estantería
        viewModel = new ViewModelProvider(this).get(ProductosDisponiblesViewModel.class);
        viewModel.setIdEstanteria(idEstanteriaActual);

        // Configurar RecyclerView y su Adapter
        adapter = new ProductosDisponiblesAdapter();
        binding.recyclerProductosDisponibles.setLayoutManager(
                new LinearLayoutManager(requireContext())
        );
        binding.recyclerProductosDisponibles.setAdapter(adapter);

        // 4) Observar la lista filtrada de productos
        viewModel.getProductosFiltrados().observe(getViewLifecycleOwner(), lista -> {
            Log.d(TAG, "ViewModel entregó productosFiltrados: " +
                    (lista != null ? lista.size() : "null"));
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

        // Observar el resultado de la asignación (true=éxito, false=error)
        viewModel.getResultadoAsignacion().observe(getViewLifecycleOwner(), exito -> {
            Log.d(TAG, "getResultadoAsignacion(): exito=" + exito);
            if (exito == null) return;
            if (exito) {
                Toast.makeText(requireContext(),
                        "Productos asignados con éxito", Toast.LENGTH_SHORT).show();
                // Regresamos a la pantalla anterior (pop del NavController)
                requireActivity().onBackPressed();
            } else {
                Toast.makeText(requireContext(),
                        "Error al asignar productos", Toast.LENGTH_SHORT).show();
                // Reactivar el FAB para intentar nuevamente
                binding.fabAsignarProductos.setEnabled(true);
            }
        });

        // Pulsar el FAB: abrimos el diálogo para introducir “balda” solo de los marcados
        binding.fabAsignarProductos.setOnClickListener(v -> {
            List<ProductoResponse> seleccionadosObjeto = adapter.getProductosSeleccionados();
            Log.d(TAG, "FAB click: seleccionadosObjeto.size()=" + seleccionadosObjeto.size());
            if (seleccionadosObjeto.isEmpty()) {
                Toast.makeText(requireContext(),
                        "Debes seleccionar al menos un producto", Toast.LENGTH_SHORT).show();
                return;
            }
            mostrarDialogoBalda(seleccionadosObjeto);
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * Muestra un AlertDialog en el que, para cada producto de la lista “productosSeleccionados”,
     * se pide un número de balda. Solo se puede confirmar si TODOS los campos están completos
     * con un número válido. Al confirmar, llama a viewModel.asignarProductos(...) con la lista de IDs.
     */
    private void mostrarDialogoBalda(List<ProductoResponse> productosSeleccionados) {
        Log.d(TAG, "mostrarDialogoBalda(): recibidos " +
                productosSeleccionados.size() + " productos seleccionados");
        for (ProductoResponse p : productosSeleccionados) {
            Log.d(TAG, "   • ID=" + p.getId() + " | Nombre=" + p.getNombre());
        }

        // 1) Creamos un LinearLayout vertical que contendrá tantas filas como productosSeleccionados
        LinearLayout container = new LinearLayout(requireContext());
        container.setOrientation(LinearLayout.VERTICAL);
        int padding = (int) (16 * getResources().getDisplayMetrics().density);
        container.setPadding(padding, padding, padding, padding);

        // 2) Mapa (idProducto → EditText) para validar y luego leer cada “balda”
        Map<Integer, EditText> mapBaldaPorProducto = new HashMap<>();

        // 3) Por cada ProductoResponse p en productosSeleccionados, añadimos una fila:
        for (ProductoResponse p : productosSeleccionados) {
            // 3a) Fila horizontal
            LinearLayout fila = new LinearLayout(requireContext());
            fila.setOrientation(LinearLayout.HORIZONTAL);
            fila.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            fila.setPadding(0, padding / 2, 0, padding / 2);

            // 3b) TextView con el nombre del producto
            TextView tvNombre = new TextView(requireContext());
            tvNombre.setLayoutParams(new LinearLayout.LayoutParams(
                    0,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    1f
            ));
            tvNombre.setText(p.getNombre());
            tvNombre.setTextSize(16f);

            // 3c) EditText para que el usuario ingrese un número de “balda”
            EditText etBalda = new EditText(requireContext());
            etBalda.setLayoutParams(new LinearLayout.LayoutParams(
                    (int) (80 * getResources().getDisplayMetrics().density),
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            etBalda.setHint("Balda");
            etBalda.setInputType(InputType.TYPE_CLASS_NUMBER);
            etBalda.setSingleLine(true);

            // 3d) Añadimos ambos elementos a la fila
            fila.addView(tvNombre);
            fila.addView(etBalda);

            // 3e) Guardamos en el mapa: clave = p.getId(), valor = etBalda
            mapBaldaPorProducto.put(p.getId(), etBalda);

            // 3f) Añadimos la fila completa al contenedor principal
            container.addView(fila);
        }

        // 4) Construimos el AlertDialog con ese “container” como contenido
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Asignar balda y confirmar");
        builder.setView(container);

        // Botón “Cancelar”
        builder.setNegativeButton("Cancelar", (dialog, which) -> {
            dialog.dismiss();
            binding.fabAsignarProductos.setEnabled(true);
        });

        // Botón “Asignar” (sin listener aquí para validar antes de cerrar)
        builder.setPositiveButton("Asignar", null);

        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(dialogInterface -> {
            // Cuando el diálogo está en pantalla, capturamos el botón Positivo
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
                // 5) Validar que TODOS los EditText contengan un número válido
                boolean todosValidos = true;
                for (Map.Entry<Integer, EditText> entry : mapBaldaPorProducto.entrySet()) {
                    String texto = entry.getValue().getText().toString().trim();
                    if (texto.isEmpty()) {
                        todosValidos = false;
                        break;
                    }
                    try {
                        Integer.parseInt(texto);
                    } catch (NumberFormatException ex) {
                        todosValidos = false;
                        break;
                    }
                }
                if (!todosValidos) {
                    Toast.makeText(requireContext(),
                            "Debes introducir un número de balda en cada producto", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 6) Si todo es correcto, extraemos SOLO los IDs seleccionados y llamamos a ViewModel
                List<Integer> idsSeleccionados = adapter.getIdsSeleccionados();
                Log.d(TAG, "Asignar clic → IDs enviados a ViewModel: " + idsSeleccionados);
                viewModel.asignarProductos(idsSeleccionados);

                // Deshabilitamos el FAB mientras esperamos la respuesta
                binding.fabAsignarProductos.setEnabled(false);
                dialog.dismiss();
            });
        });

        dialog.show();
    }
}
