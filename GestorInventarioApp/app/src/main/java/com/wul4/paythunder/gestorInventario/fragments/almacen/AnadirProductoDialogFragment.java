package com.wul4.paythunder.gestorInventario.fragments.almacen;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.wul4.paythunder.gestorInventario.R;
import com.wul4.paythunder.gestorInventario.entities.Producto;
import com.wul4.paythunder.gestorInventario.entities.Categoria;
import java.util.ArrayList;
import java.util.List;

public class AnadirProductoDialogFragment extends DialogFragment {

    private ImageView imgProducto;
    private EditText etNombre, etCantidad, etCodigoQR;
    private Spinner spinnerCategoria;
    private Button btnSubirFoto, btnEscanearQR, btnLeerNFC;
    private Uri imagenUriSeleccionada;
    private Switch sActivoNoActivo;
    private ActivityResultLauncher<String> galeriaLauncher;
    private List<Categoria> categorias = new ArrayList<>();

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_anadir_producto, null);
         AlmacenViewModel almacenViewModel =  new ViewModelProvider(this).get(AlmacenViewModel.class);


        imgProducto = view.findViewById(R.id.imgNuevoProducto);
        etNombre = view.findViewById(R.id.etNombreProducto);
        etCantidad = view.findViewById(R.id.etCantidadProducto);
        etCodigoQR = view.findViewById(R.id.etCodigoQRProducto);
        spinnerCategoria = view.findViewById(R.id.spinnerCategoriaProducto);
        btnSubirFoto = view.findViewById(R.id.btnSubirFoto);
        btnEscanearQR = view.findViewById(R.id.btnEscanearQR);
        btnLeerNFC = view.findViewById(R.id.btnLeerNFC);
         sActivoNoActivo = view.findViewById(R.id.id_del_switch);
       // Observamos el switch
        sActivoNoActivo.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(getContext(), "Producto activado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Producto desactivado", Toast.LENGTH_SHORT).show();
            }
        });
        sActivoNoActivo.setChecked(true);

        inicializarGaleriaLauncher();
        almacenViewModel.getCategorias().observe(getViewLifecycleOwner(), categorias -> {
            // Actualizamos el sppiner de categorias
            cargarCategorias(categorias);
        });

        btnSubirFoto.setOnClickListener(v -> seleccionarImagenDeGaleria());
        btnEscanearQR.setOnClickListener(v -> escanearCodigo());
        btnLeerNFC.setOnClickListener(v -> leerNFC());

        builder.setView(view)
                .setTitle("Añadir Producto")
                .setPositiveButton("Guardar", (dialog, id) -> guardarProducto())
                .setNegativeButton("Cancelar", (dialog, id) -> dialog.dismiss());

        return builder.create();
    }

    private void inicializarGaleriaLauncher() {
        galeriaLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {
                        if (uri != null) {
                            imagenUriSeleccionada = uri;
                            Glide.with(requireContext()).load(uri).into(imgProducto);
                        }
                    }
                });
    }

    private void seleccionarImagenDeGaleria() {
        galeriaLauncher.launch("image/*");
    }

    private void escanearCodigo() {
        Toast.makeText(getContext(), "Funcionalidad de escaneo QR/Código pendiente", Toast.LENGTH_SHORT).show();
        // Aquí lanzarías tu actividad de escaneo QR
    }

    private void leerNFC() {
        Toast.makeText(getContext(), "Funcionalidad NFC aún no disponible", Toast.LENGTH_SHORT).show();
    }

    private void guardarProducto() {
        String nombre = etNombre.getText().toString().trim();
        String cantidadStr = etCantidad.getText().toString().trim();
        String codigoQR = etCodigoQR.getText().toString().trim();
        String categoriaSeleccionada = spinnerCategoria.getSelectedItem().toString();
        boolean activado = sActivoNoActivo.isChecked();
        int cantidad = -1;

        if (nombre.isEmpty() || cantidadStr.isEmpty()) {
            Toast.makeText(getContext(), "Nombre y cantidad son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
           cantidad = Integer.parseInt(cantidadStr);
        }catch (NumberFormatException n){
            Toast.makeText(getContext(),"La cantidad debe ser un número válido", Toast.LENGTH_SHORT).show();
            return;
        }

        if(cantidad < 0){
            Toast.makeText(getContext(),"La cantidad debe ser mayor de 0", Toast.LENGTH_SHORT).show();
            return;
        }



        Producto producto = new Producto();
        producto.setNombre(nombre);
        producto.setCantidad(cantidad);
        producto.setCodigoQr(codigoQR);
        if(activado){
            producto.setEstado("activo");
        }else{
            producto.setEstado("desactivo");
        }


        Categoria categoria = new Categoria();
        categoria.setDescripcion(categoriaSeleccionada);
        producto.setCategoria(categoria);

        // Imagen: por ahora, podrías guardar el URI local o mandarlo a backend

        // Aquí llamarías a la API para guardar el producto o actualizar el listado
        Toast.makeText(getContext(), "Producto guardado: " + nombre, Toast.LENGTH_SHORT).show();
    }

    private void cargarCategorias(List<Categoria> categorias) {

        List<String> categoriasString = new ArrayList<>();
        for (Categoria c: categorias) {
            categoriasString.add(c.getDescripcion());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                categoriasString
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adapter);
    }
}
