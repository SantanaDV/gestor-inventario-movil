// src/main/java/com/wul4/paythunder/gestorInventario/fragments/almacen/AlmacenFragment.java
package com.wul4.paythunder.gestorInventario.fragments.almacen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.wul4.paythunder.gestorInventario.R;
import com.wul4.paythunder.gestorInventario.databinding.FragmentAlmacenBinding;
import com.wul4.paythunder.gestorInventario.entities.Categoria;
import com.wul4.paythunder.gestorInventario.entities.Producto;
import com.wul4.paythunder.gestorInventario.activities.ScannerActivity;
import com.wul4.paythunder.gestorInventario.utils.ApiClient;
import com.wul4.paythunder.gestorInventario.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AlmacenFragment extends Fragment {
    private static final int REQUEST_SCAN_QR = 1001;

    private FragmentAlmacenBinding binding;
    private AlmacenViewModel       vm;

    private List<Producto> allProductos   = new ArrayList<>();
    private List<Categoria> allCategorias = new ArrayList<>();

    private int    cantidadFiltro  = -1;
    private String categoriaFiltro = "";

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentAlmacenBinding.inflate(inflater, container, false);
        vm      = new ViewModelProvider(requireActivity()).get(AlmacenViewModel.class);

        // 1) Observers de lista y categorías
        vm.getProductos().observe(getViewLifecycleOwner(), productos -> {
            allProductos = productos != null
                    ? productos
                    : Collections.emptyList();
            aplicarFiltros();
        });
        vm.getCategorias().observe(getViewLifecycleOwner(), categorias -> {
            allCategorias = categorias != null
                    ? categorias
                    : Collections.emptyList();
            inflarSpinner(allCategorias);
        });

        // 2) Lanzar ScannerActivity para QR
        binding.btnBuscarQR.setOnClickListener(v ->
                startActivityForResult(
                        new Intent(requireContext(), ScannerActivity.class),
                        REQUEST_SCAN_QR
                )
        );
        // NFC placeholder
        binding.btnBuscarNFC.setOnClickListener(v ->
                Toast.makeText(requireContext(),
                        "Funcionalidad NFC pendiente",
                        Toast.LENGTH_SHORT).show()
        );

        // 3) Filtrar
        binding.btnFiltrar.setOnClickListener(v -> {
            String txt = binding.etCantidadMaxima.getText().toString().trim();
            cantidadFiltro  = txt.isEmpty() ? -1 : Integer.parseInt(txt);
            // Leemos la selección del Spinner nativo:
            categoriaFiltro = (String) binding.spinnerCategoria.getSelectedItem();
            aplicarFiltros();
        });

        // 4) Añadir producto
        binding.fabAnadir.setOnClickListener(v ->
                new AnadirProductoDialogFragment()
                        .show(getParentFragmentManager(), "AnadirProducto")
        );

        // 5) Resultado de búsqueda por QR
        vm.getProductoQR().observe(getViewLifecycleOwner(), prod -> {
            if (prod != null) {
                DetalleProductoDialogFragment
                        .newInstance(prod)
                        .show(getParentFragmentManager(), "DetalleQR");
            } else {
                Toast.makeText(requireContext(),
                        "No se encontró producto con ese código",
                        Toast.LENGTH_SHORT).show();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode,
                                 int resultCode,
                                 @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SCAN_QR
                && resultCode == Activity.RESULT_OK
                && data != null) {
            String qr = data.getStringExtra(ScannerActivity.EXTRA_SCAN);
            if (qr != null) {
                vm.fetchProductoPorQR(qr);
            }
        }
    }

    private void inflarSpinner(List<Categoria> categorias) {
        List<String> items = new ArrayList<>();
        items.add("");  // opción “todas”
        for (Categoria c : categorias) {
            items.add(c.getDescripcion());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                items
        );
        binding.spinnerCategoria.setAdapter(adapter);
    }

    private void aplicarFiltros() {
        List<Producto> activos  = new ArrayList<>(),
                inactivos = new ArrayList<>();
        for (Producto p : allProductos) {
            boolean okCant = cantidadFiltro == -1
                    || p.getCantidad() == cantidadFiltro;
            boolean okCat  = categoriaFiltro.isEmpty()
                    || (p.getCategoria() != null
                    && categoriaFiltro.equals(
                    p.getCategoria().getDescripcion()
            ));
            if (okCant && okCat) {
                if ("activo".equalsIgnoreCase(p.getEstado())) {
                    activos.add(p);
                } else {
                    inactivos.add(p);
                }
            }
        }
        mostrarProductos(activos, inactivos);
    }

    private void mostrarProductos(List<Producto> activos,
                                  List<Producto> inactivos) {
        binding.columnaActivos.removeAllViews();
        binding.columnaInactivos.removeAllViews();
        LayoutInflater inf = LayoutInflater.from(requireContext());
        agregarProductosAColumna(activos,  binding.columnaActivos,  inf);
        agregarProductosAColumna(inactivos, binding.columnaInactivos, inf);
    }

    private void agregarProductosAColumna(List<Producto> lista,
                                          LinearLayout columna,
                                          LayoutInflater inf) {
        for (Producto p : lista) {
            View v = inf.inflate(R.layout.item_producto, columna, false);
            v.setBackgroundResource(
                    "activo".equalsIgnoreCase(p.getEstado())
                            ? R.drawable.card_background_activo
                            : R.drawable.card_background_inactivo
            );
            TextView tvN = v.findViewById(R.id.tvNombre);
            TextView tvD = v.findViewById(R.id.tvDetalles);
            ImageView iv = v.findViewById(R.id.imgProducto);

            tvN.setText(p.getNombre());
            tvD.setText("Cant: " + p.getCantidad()
                    + " | Cat: "
                    + (p.getCategoria() != null
                    ? p.getCategoria().getDescripcion()
                    : "—")
            );

            String url = ApiClient.getClient().baseUrl()
                    + "imagen/" + p.getUrl_img();
            Utils.cargaDeImagenesConReintento(
                    v.getContext(), iv, url, 3
            );

            v.setOnClickListener(x ->
                    DetalleProductoDialogFragment
                            .newInstance(p)
                            .show(getParentFragmentManager(), "Detalle")
            );
            v.setOnLongClickListener(x -> {
                showContextMenu(p, v);
                return true;
            });

            columna.addView(v);
        }
    }

    private void showContextMenu(Producto prod, View anchor) {
        PopupMenu popup = new PopupMenu(requireContext(), anchor);
        popup.inflate(R.menu.producto_context_menu);
        popup.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_editar) {
                Toast.makeText(requireContext(),
                        "Editar " + prod.getNombre(),
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(requireContext(),
                        "Borrar " + prod.getNombre(),
                        Toast.LENGTH_SHORT).show();
            }
            return true;
        });
        popup.show();
    }
}
