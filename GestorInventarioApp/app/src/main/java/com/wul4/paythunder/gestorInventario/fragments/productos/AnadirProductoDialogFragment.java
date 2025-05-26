package com.wul4.paythunder.gestorInventario.fragments.productos;


import android.app.AlertDialog;
import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.MimeTypeMap;
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
import com.google.gson.Gson;
import com.wul4.paythunder.gestorInventario.R;
import com.wul4.paythunder.gestorInventario.entities.Categoria;
import com.wul4.paythunder.gestorInventario.utils.dto.ProductoCreacionDTO;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class AnadirProductoDialogFragment extends DialogFragment {

    private ImageView imgProducto;
    private EditText etNombre, etCantidad, etCodigoQR;
    private Spinner spinnerCategoria;
    private Button btnSubirFoto, btnEscanearQR, btnLeerNFC;
    private Uri imagenUriSeleccionada;
    private Switch sActivoNoActivo;
    private ActivityResultLauncher<String> galeriaLauncher;
    private List<Categoria> categorias = new ArrayList<>();
    private List<String> categoriasString = new ArrayList<>();
    private ProductosViewModel almacenViewModel;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_anadir_producto, null);

        almacenViewModel = new ViewModelProvider(requireActivity()).get(ProductosViewModel.class);
        imgProducto = view.findViewById(R.id.imgNuevoProducto);
        etNombre = view.findViewById(R.id.etNombreProducto);
        etCantidad = view.findViewById(R.id.etCantidadProducto);
        etCodigoQR = view.findViewById(R.id.etCodigoQRProducto);
        spinnerCategoria = view.findViewById(R.id.spinnerCategoriaProducto);
        btnSubirFoto = view.findViewById(R.id.btnSubirFoto);
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
        almacenViewModel.getCategorias().observe(this, categorias -> {
            // Actualizamos el sppiner de categorias
            cargarCategorias(categorias);
        });

        btnSubirFoto.setOnClickListener(v -> seleccionarImagenDeGaleria());

        builder.setView(view)
                .setTitle("Añadir Producto")
                .setPositiveButton("Guardar", (dialog, id) -> {
                    try {
                        guardarProducto();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
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


    private void guardarProducto() throws IOException {
        // Lectura y validación de campos
        String nombre = etNombre.getText().toString().trim();
        String cantidadStr = etCantidad.getText().toString().trim();
        String codigoQR = etCodigoQR.getText().toString().trim();
        String categoriaSeleccionada = spinnerCategoria.getSelectedItem().toString();
        boolean activado = sActivoNoActivo.isChecked();

        if (nombre.isEmpty() || cantidadStr.isEmpty()) {
            Toast.makeText(getContext(), "Nombre y cantidad son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }
        int cantidad;
        try {
            cantidad = Integer.parseInt(cantidadStr);
            if (cantidad < 0) {
                Toast.makeText(getContext(), "La cantidad debe ser mayor de 0", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (NumberFormatException nfe) {
            Toast.makeText(getContext(), "La cantidad debe ser un número válido", Toast.LENGTH_SHORT).show();
            return;
        }

        // Construir el objeto Producto
        ProductoCreacionDTO producto = new ProductoCreacionDTO();
        producto.setNombre(nombre);
        producto.setCantidad(cantidad);
        producto.setCodigoQr(codigoQR);
        producto.setEstado(activado ? "activo" : "desactivo");
        int categoriaId = categoriasString.lastIndexOf(categoriaSeleccionada);
        producto.setId_categoria(++categoriaId);

        // Una vez que tenemos el producto, lo subimos a la API
        // Primero convertimos a un Json y lo embalamos como un RequestBody con el media-type application/json
        // Todo esto lo hacemos porque Retrofit no convierte automáticamente el objeto Java en JSON
        Gson gson = new Gson();
        String productoJson = gson.toJson(producto);
        RequestBody requestBody = RequestBody.create(
                productoJson,
                MediaType.parse("application/json")
        );

        // Creamos el multipart con la imagen
        MultipartBody.Part imagenPart = null;
        // Copiamos la Uri a un File en cache
        if (imagenUriSeleccionada != null) {
            // Preparar nombre de fichero con extensión obtenida dinámicamente
            String ext = obtenerMIMEtype(imagenUriSeleccionada);
            File imagenFile = new File(
                    requireContext().getCacheDir(),
                    "upload_" + System.currentTimeMillis() + "." + ext
            );

            // Copiar bytes de la URI al fichero temporal
            try (
                    InputStream is = requireContext().getContentResolver().openInputStream(imagenUriSeleccionada);
                    FileOutputStream os = new FileOutputStream(imagenFile)
            ) {
                byte[] buffer = new byte[4096];
                int len;
                while ((len = is.read(buffer)) > 0) {
                    os.write(buffer, 0, len);
                }
            }

            // Crear RequestBody para el binario
            RequestBody requestFile = RequestBody.create(
                    imagenFile,
                    MediaType.parse(
                            requireContext()
                                    .getContentResolver()
                                    .getType(imagenUriSeleccionada)
                    )
            );

            // MultipartBody.Part con el nombre “imagen”
            imagenPart = MultipartBody.Part.createFormData(
                    "imagen",
                    imagenFile.getName(),
                    requestFile
            );
        }


        // Llamamos al ViewModel para hacer la llamada a Retrofit
        almacenViewModel.guardarProductoApi(requestBody, imagenPart);

        // Observamos si ha concluido bien la subida y si no notificamos
        almacenViewModel.getResultadoCreacion().observe(this, creado -> {
            if (creado != null) {
                Toast.makeText(getContext(), "¡Producto creado!", Toast.LENGTH_SHORT).show();
                dismiss();
            } else {
                Toast.makeText(getContext(), "Error al crear producto", Toast.LENGTH_SHORT).show();
            }
        });
    }




    private void cargarCategorias(List<Categoria> categorias) {

     categoriasString = new ArrayList<>();
    for (Categoria c : categorias) {
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

private String obtenerMIMEtype(Uri imagenUri) {
    //Obtenemos el tipo de archivo
    String mimeType = requireContext().getContentResolver().getType(imagenUri);
    String extension = null;
    if (mimeType != null) {
        extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType);
    }
    //En caso de que haya algun fallo devuelve jpg como extensión por defectp
    if (extension != null) {
        return extension;
    } else {
        return "jpg";
    }
}
}
