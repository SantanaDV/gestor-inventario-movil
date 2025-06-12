package com.wul4.paythunder.gestorInventario.fragments.productos;

import android.app.AlertDialog;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.annotation.*;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.wul4.paythunder.gestorInventario.R;
import com.wul4.paythunder.gestorInventario.databinding.FragmentProductosBinding;
import com.wul4.paythunder.gestorInventario.entities.Categoria;
import com.wul4.paythunder.gestorInventario.entities.Producto;
import com.wul4.paythunder.gestorInventario.activities.ScannerActivity;
import com.wul4.paythunder.gestorInventario.utils.ApiClient;
import com.wul4.paythunder.gestorInventario.utils.Utils;
import java.util.*;

public class ProductosFragment extends Fragment {
    private static final int REQUEST_SCAN_QR = 1001;
    private FragmentProductosBinding binding;
    private ProductosViewModel vm;
    private List<Producto> allProductos = Collections.emptyList();

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inf,
                             @Nullable ViewGroup ct,
                             @Nullable Bundle bs) {
        binding = FragmentProductosBinding.inflate(inf,ct,false);
        vm = new ViewModelProvider(requireActivity()).get(ProductosViewModel.class);

        // Observers
        vm.getProductos().observe(getViewLifecycleOwner(), lista -> {
            allProductos = lista != null ? lista : Collections.emptyList();
            aplicarFiltros();
        });
        vm.getCategorias().observe(getViewLifecycleOwner(), this::inflarSpinner);
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

        // Scanner QR
        binding.btnBuscarQR.setOnClickListener(v ->
                startActivityForResult(
                        new Intent(requireContext(), ScannerActivity.class),
                        REQUEST_SCAN_QR
                )
        );
        binding.btnBuscarNFC.setOnClickListener(v ->
                Toast.makeText(requireContext(),
                        "Funcionalidad NFC pendiente",
                        Toast.LENGTH_SHORT).show()
        );

        // Filtrar
        binding.btnFiltrar.setOnClickListener(v -> aplicarFiltros());

        // Añadir producto
        binding.fabAnadir.setOnClickListener(v ->
                ProductoDialogFragment
                        .newInstance(null)
                        .show(getParentFragmentManager(), "ProductoDialog")
        );

        return binding.getRoot();
    }

    @Override public void onActivityResult(int req,int res,@Nullable Intent d) {
        super.onActivityResult(req,res,d);
        if (req==REQUEST_SCAN_QR && res==Activity.RESULT_OK && d!=null) {
            String qr = d.getStringExtra(ScannerActivity.EXTRA_SCAN);
            vm.fetchProductoPorQR(qr);
        }
    }

    private void inflarSpinner(List<Categoria> cats) {
        List<String> items = new ArrayList<>();
        items.add("");
        for (Categoria c:cats) items.add(c.getDescripcion());
        binding.spinnerCategoria.setAdapter(
                new ArrayAdapter<>(
                        requireContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        items
                )
        );
    }

    private void aplicarFiltros() {
        Object sel = binding.spinnerCategoria.getSelectedItem();
        String catSel = sel != null ? sel.toString() : "";

        String txt = binding.etCantidadMaxima.getText().toString().trim();
        int max = -1;
        if (!txt.isEmpty()) {
            try {
                max = Integer.parseInt(txt);
            } catch (NumberFormatException e) {
                // por si meten algo raro
                max = -1;
            }
        }

        List<Producto> act = new ArrayList<>(), ina = new ArrayList<>();
        for (Producto p : allProductos) {
            boolean okC = max < 0 || p.getCantidad() == max;
            boolean okT = catSel.isEmpty()
                    || (p.getCategoria() != null
                    && catSel.equals(p.getCategoria().getDescripcion()));
            if (okC && okT) {
                if ("activo".equalsIgnoreCase(p.getEstado())) act.add(p);
                else ina.add(p);
            }
        }
        mostrarProductos(act, ina);
    }

    private void mostrarProductos(List<Producto> act,
                                  List<Producto> ina) {
        binding.columnaActivos.removeAllViews();
        binding.columnaInactivos.removeAllViews();
        LayoutInflater inf = LayoutInflater.from(requireContext());
        for (Producto p: act) addItem(p, binding.columnaActivos, inf);
        for (Producto p: ina) addItem(p, binding.columnaInactivos, inf);
    }

    private void addItem(Producto p,
                         ViewGroup col,
                         LayoutInflater inf) {
        View v = inf.inflate(R.layout.item_producto, col, false);
        v.setBackgroundResource(
                "activo".equalsIgnoreCase(p.getEstado())
                        ? R.drawable.card_background_activo
                        : R.drawable.card_background_inactivo
        );
        ((TextView)v.findViewById(R.id.tvNombre)).setText(p.getNombre());
        ((TextView)v.findViewById(R.id.tvDetalles)).setText(
                "Cant:" + p.getCantidad() +
                        " | Cat:" +
                        (p.getCategoria()!=null
                                ? p.getCategoria().getDescripcion()
                                : "—")
        );
        ImageView iv = v.findViewById(R.id.imgProducto);
        Utils.cargaDeImagenesConReintento(
                v.getContext(),
                iv,
                ApiClient.getClient().baseUrl() + "imagen/" + p.getUrl_img(),
                3
        );

        v.setOnClickListener(x ->
                DetalleProductoDialogFragment
                        .newInstance(p)
                        .show(getParentFragmentManager(), "Detalle")
        );
        v.setOnLongClickListener(x -> {
            PopupMenu popup = new PopupMenu(requireContext(), x);
            popup.inflate(R.menu.producto_context_menu);
            popup.setOnMenuItemClickListener(mi -> {
                if (mi.getItemId()==R.id.action_editar) {
                    // Editar
                    ProductoDialogFragment
                            .newInstance(p)
                            .show(getParentFragmentManager(), "ProductoDialog");
                } else if (mi.getItemId()==R.id.action_borrar) {
                    // Confirmación antes de borrar
                    new AlertDialog.Builder(requireContext())
                            .setTitle("Confirmar eliminación")
                            .setMessage("¿Eliminar “"+p.getNombre()+"”?")
                            .setPositiveButton("Borrar", (d,i)-> {
                                vm.deleteProduct(p.getId_producto());
                            })
                            .setNegativeButton("Cancelar", null)
                            .show();
                }
                return true;
            });
            popup.show();
            return true;
        });

        col.addView(v);
    }
}
