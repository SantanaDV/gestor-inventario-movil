package com.wul4.paythunder.gestorInventario.fragments.almacen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.wul4.paythunder.gestorInventario.R;
import com.wul4.paythunder.gestorInventario.databinding.FragmentAlmacenBinding;
import com.wul4.paythunder.gestorInventario.entities.Categoria;
import com.wul4.paythunder.gestorInventario.entities.Producto;
import com.wul4.paythunder.gestorInventario.utils.ApiClient;
import com.wul4.paythunder.gestorInventario.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AlmacenFragment extends Fragment {

    private FragmentAlmacenBinding binding;
    private AlmacenViewModel viewModel;

    // Guardamos la lista completa para filtrar en memoria
    private List<Producto> allProductos = Collections.emptyList();
    private List<Categoria> allCategorias = Collections.emptyList();

    // Parámetros de filtro
    private int cantidadFiltro = -1;
    private String categoriaFiltro = "";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
<<<<<<< Updated upstream
=======
        //Obtenemos la instancia del ViewModel asociado al fragmento
        AlmacenViewModel almacenViewModel =
                new ViewModelProvider(this).get(AlmacenViewModel.class);
        //inflamos el binding con el layout del fragmento
>>>>>>> Stashed changes
        binding = FragmentAlmacenBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(AlmacenViewModel.class);
        viewModel.getProductos().observe(getViewLifecycleOwner(), productos -> {
            allProductos = productos != null ? productos : Collections.emptyList();
            aplicarFiltros();
        });
        viewModel.getCategorias().observe(getViewLifecycleOwner(), categorias -> {
            allCategorias = categorias != null ? categorias : Collections.emptyList();
            inflarSpinner(allCategorias);
        });

        viewModel.getResultadoCreacion().observe(getViewLifecycleOwner(), creado -> {
            if (creado != null) {
                // si se ha creado bien, recargamos
                viewModel.getProductos();
                Toast.makeText(requireContext(), "Producto añadido", Toast.LENGTH_SHORT).show();
            }
        });


        // 3) Botón de filtrar
        binding.btnFiltrar.setOnClickListener(v -> {
            String textoCantidad = binding.etCantidadMaxima.getText().toString().trim();
            cantidadFiltro = textoCantidad.isEmpty() ? -1
                    : Integer.parseInt(textoCantidad);
            categoriaFiltro = binding.spinnerCategoria.getSelectedItem() != null
                    ? binding.spinnerCategoria.getSelectedItem().toString()
                    : "";
            aplicarFiltros();
        });

        // 4) FAB de añadir producto
        binding.fabAnadir.setOnClickListener(v -> {
            AnadirProductoDialogFragment dialog =
                    new AnadirProductoDialogFragment();
            dialog.show(getParentFragmentManager(), "AnadirProducto");
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /** Rellena el Spinner de categorías **/
    private void inflarSpinner(List<Categoria> categorias) {
        List<String> nombres = new ArrayList<>();
        nombres.add(""); // opción “todas”
        for (Categoria c : categorias) {
            nombres.add(c.getDescripcion());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                nombres
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerCategoria.setAdapter(adapter);
    }

    /** Aplica los filtros sobre allProductos y refresca la UI **/
    private void aplicarFiltros() {
        List<Producto> activos = new ArrayList<>();
        List<Producto> inactivos = new ArrayList<>();

        for (Producto p : allProductos) {
            boolean pasaCantidad = (cantidadFiltro == -1) || p.getCantidad() == cantidadFiltro;
            boolean pasaCategoria = categoriaFiltro.isEmpty()
                    || (p.getCategoria() != null
                    && categoriaFiltro.equals(p.getCategoria().getDescripcion()));

            if (pasaCantidad && pasaCategoria) {
                if ("activo".equalsIgnoreCase(p.getEstado())) {
                    activos.add(p);
                } else {
                    inactivos.add(p);
                }
            }
        }

        mostrarProductos(activos, inactivos);
    }

    /** Infla las tarjetas en cada columna **/
    private void mostrarProductos(List<Producto> activos,
                                  List<Producto> inactivos) {
        binding.columnaActivos.removeAllViews();
        binding.columnaInactivos.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(requireContext());

        agregarProductosAColumna(activos, binding.columnaActivos, inflater);
        agregarProductosAColumna(inactivos, binding.columnaInactivos, inflater);
    }

    /** Agrega una lista de productos a una columna concreta **/
    private void agregarProductosAColumna(List<Producto> productos,
                                          LinearLayout columna,
                                          LayoutInflater inflater) {
        for (Producto p : productos) {
            View itemView = inflater.inflate(R.layout.item_producto, columna, false);

            // Fondeado según estado
            if ("activo".equalsIgnoreCase(p.getEstado())) {
                itemView.setBackgroundResource(R.drawable.card_background_activo);
            } else {
                itemView.setBackgroundResource(R.drawable.card_background_inactivo);
            }

            TextView tvNombre   = itemView.findViewById(R.id.tvNombre);
            TextView tvDetalles = itemView.findViewById(R.id.tvDetalles);
            ImageView imgProd   = itemView.findViewById(R.id.imgProducto);

            tvNombre.setText(p.getNombre());
            tvDetalles.setText("Cantidad: " + p.getCantidad()
                    + " / Categoría: " +
                    (p.getCategoria() != null
                            ? p.getCategoria().getDescripcion()
                            : "—"));

            String url = ApiClient.getClient().baseUrl()
                    + "imagen/" + p.getUrl_img();
            Utils.cargaDeImagenesConReintento(
                    itemView.getContext(), imgProd, url, 3
            );

            itemView.setOnClickListener(v -> {
                DetalleProductoDialogFragment dialog =
                        DetalleProductoDialogFragment.newInstance(p);
                dialog.show(getParentFragmentManager(), "DetalleProducto");
            });
            itemView.setOnLongClickListener(v -> {
                showContextMenu(p, v);
                return true;
            });

            columna.addView(itemView);
        }
    }

    /** Menú contextual para editar o borrar **/
    private void showContextMenu(Producto producto, View anchorView) {
        PopupMenu popup = new PopupMenu(requireContext(), anchorView);
        popup.getMenuInflater().inflate(
                R.menu.producto_context_menu, popup.getMenu()
        );
        popup.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            if (id == R.id.action_editar) {
                Toast.makeText(requireContext(),
                        "Editar " + producto.getNombre(),
                        Toast.LENGTH_SHORT).show();
                return true;
            } else if (id == R.id.action_borrar) {
                Toast.makeText(requireContext(),
                        "Borrar " + producto.getNombre(),
                        Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        });
        popup.show();
    }
}
