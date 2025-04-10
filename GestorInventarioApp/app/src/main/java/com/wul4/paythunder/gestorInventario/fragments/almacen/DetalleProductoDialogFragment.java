package com.wul4.paythunder.gestorInventario.fragments.almacen;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wul4.paythunder.gestorInventario.R;
import com.wul4.paythunder.gestorInventario.entities.Producto;
import com.wul4.paythunder.gestorInventario.utils.ApiClient;
import com.wul4.paythunder.gestorInventario.utils.Utils;

import java.io.Serializable;

public class DetalleProductoDialogFragment extends androidx.fragment.app.DialogFragment {

    private static final String ARG_PRODUCTO = "producto";
    private Producto producto;

    public static DetalleProductoDialogFragment newInstance(Producto producto) {
        DetalleProductoDialogFragment fragment = new DetalleProductoDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PRODUCTO, (Serializable) producto); //La clase es importante que implemente serializable
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            producto = (Producto) getArguments().getSerializable(ARG_PRODUCTO);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_detalle_producto, null);

        ImageView img = view.findViewById(R.id.imgDetalleProducto);
        TextView nombre = view.findViewById(R.id.tvNombreProducto);
        TextView detalles = view.findViewById(R.id.tvDetallesProducto);

        nombre.setText(producto.getNombre());
        detalles.setText("ID: " + producto.getId_producto()
                + "\nCategoría: " + producto.getCategoria()
                + "\nCantidad: " + producto.getCantidad()
                + "\nEstado: " + producto.getEstado());

        String urlImagen = ApiClient.getClient().baseUrl() + "imagen/" + producto.getUrl_img();
        Utils.cargaDeImagenesConReintento(getContext(), img, urlImagen, 3);

        builder.setView(view)
                .setPositiveButton("Cerrar", (dialog, id) -> dialog.dismiss());

        return builder.create();
    }
}

