package com.wul4.paythunder.gestorInventario.fragments.productos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.annotation.*;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.wul4.paythunder.gestorInventario.R;
import com.wul4.paythunder.gestorInventario.entities.Categoria;
import com.wul4.paythunder.gestorInventario.entities.Producto;
import com.wul4.paythunder.gestorInventario.utils.ApiClient;
import com.wul4.paythunder.gestorInventario.utils.dto.ProductoCreacionDTO;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import java.util.*;

public class ProductoDialogFragment extends DialogFragment {
    private static final String ARG_PRODUCTO = "producto";
    private Producto existing;
    private ProductosViewModel vm;

    public static ProductoDialogFragment newInstance(@Nullable Producto p) {
        ProductoDialogFragment f = new ProductoDialogFragment();
        Bundle b = new Bundle();
        b.putSerializable(ARG_PRODUCTO, p);
        f.setArguments(b);
        return f;
    }

    @Override public void onCreate(Bundle s) {
        super.onCreate(s);
        existing = getArguments()!=null
                ? (Producto)getArguments().getSerializable(ARG_PRODUCTO)
                : null;
    }

    @NonNull @Override public Dialog onCreateDialog(Bundle s) {
        vm = new ViewModelProvider(requireActivity()).get(ProductosViewModel.class);
        View v = requireActivity().getLayoutInflater()
                .inflate(R.layout.dialog_anadir_producto, null);

        EditText etN = v.findViewById(R.id.etNombreProducto);
        EditText etC = v.findViewById(R.id.etCantidadProducto);
        EditText etQ = v.findViewById(R.id.etCodigoQRProducto);
        Spinner spC  = v.findViewById(R.id.spinnerCategoriaProducto);
        Switch sw    = v.findViewById(R.id.id_del_switch);
        ImageView iv = v.findViewById(R.id.imgNuevoProducto);
        Button btnF  = v.findViewById(R.id.btnSubirFoto);

        // Precarga categorías
        vm.getCategorias().observe(this, cats -> {
            List<String> list = new ArrayList<>();
            for (Categoria c:cats) list.add(c.getDescripcion());
            ArrayAdapter<String> ad = new ArrayAdapter<>(
                    requireContext(),
                    android.R.layout.simple_spinner_dropdown_item,
                    list
            );
            spC.setAdapter(ad);
            if (existing!=null && existing.getCategoria()!=null) {
                int idx = list.indexOf(existing.getCategoria().getDescripcion());
                if (idx>=0) spC.setSelection(idx);
            }
        });

        // Si editamos, precargamos valores
        if (existing!=null) {
            etN.setText(existing.getNombre());
            etC.setText(String.valueOf(existing.getCantidad()));
            etQ.setText(existing.getCodigoQr());
            sw.setChecked("activo".equalsIgnoreCase(existing.getEstado()));
            Glide.with(this)
                    .load(ApiClient.getClient().baseUrl()+"imagen/"+existing.getUrl_img())
                    .into(iv);
        }

        // Aquí puedes reutilizar tu galería… btnF.setOnClickListener(...)

        AlertDialog dlg = new AlertDialog.Builder(requireContext())
                .setTitle(existing==null ? "Añadir producto" : "Editar producto")
                .setView(v)
                .setNegativeButton("Cancelar", null)
                .setPositiveButton("Guardar", null)
                .create();

        dlg.setOnShowListener(dial -> {
            Button ok = dlg.getButton(AlertDialog.BUTTON_POSITIVE);
            ok.setOnClickListener(z -> {
                // Validaciones (idénticas a creación)
                String nombre = etN.getText().toString().trim();
                String cantS  = etC.getText().toString().trim();
                if (nombre.isEmpty() || cantS.isEmpty()) {
                    Toast.makeText(requireContext(),
                            "Nombre y cantidad obligatorios",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                int cantidad;
                try {
                    cantidad = Integer.parseInt(cantS);
                } catch (NumberFormatException e) {
                    Toast.makeText(requireContext(),
                            "Cantidad inválida",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                String qr   = etQ.getText().toString().trim();
                String estado = sw.isChecked() ? "activo" : "desactivado";
                int catId = spC.getSelectedItemPosition() + 1;

                // DTO y JSON
                ProductoCreacionDTO dto = new ProductoCreacionDTO();
                dto.setNombre(nombre);
                dto.setCantidad(cantidad);
                dto.setCodigoQr(qr);
                dto.setEstado(estado);
                dto.setId_categoria(catId);
                if (existing != null) {
                    dto.setId_producto(existing.getId_producto());
                    dto.setFechaCreacion(existing.getFechaCreacion());
                    dto.setUrl_img(existing.getUrl_img());
                }
                String json = new Gson().toJson(dto);
                RequestBody rb = RequestBody.create(
                        json, MediaType.parse("application/json")
                );
                MultipartBody.Part imagenPart = null; // según tu lógica

                if (existing==null) {
                    vm.guardarProductoApi(rb, imagenPart);
                } else {
                    vm.updateProductApi(rb, imagenPart);
                }
                dlg.dismiss();
            });
        });

        return dlg;
    }
}
