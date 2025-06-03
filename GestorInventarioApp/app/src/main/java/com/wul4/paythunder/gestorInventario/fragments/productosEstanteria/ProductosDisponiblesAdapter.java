package com.wul4.paythunder.gestorInventario.fragments.productosEstanteria;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wul4.paythunder.gestorInventario.R;
import com.wul4.paythunder.gestorInventario.response.ProductoResponse;
import com.wul4.paythunder.gestorInventario.utils.ApiClient;
import com.wul4.paythunder.gestorInventario.utils.Utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProductosDisponiblesAdapter
        extends RecyclerView.Adapter<ProductosDisponiblesAdapter.ViewHolder> {

    private final List<ProductoResponse> datos = new ArrayList<>();
    private final Set<Integer> seleccionados = new HashSet<>();

    /** Devuelve los IDs de los productos marcados */
    public List<Integer> getIdsSeleccionados() {
        return new ArrayList<>(seleccionados);
    }

    /** Pone la nueva lista y limpia cualquier selección previa */
    public void setDatos(List<ProductoResponse> nuevosDatos) {
        datos.clear();
        seleccionados.clear();
        if (nuevosDatos != null) datos.addAll(nuevosDatos);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_producto_disponible, parent, false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductoResponse p = datos.get(position);
        holder.bind(p, seleccionados.contains(p.getId()));
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView img;
        private final TextView tvNombre, tvDetalle;
        private final CheckBox check;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            img        = itemView.findViewById(R.id.imgProductoDisponible);
            tvNombre   = itemView.findViewById(R.id.tvNombreDisponible);
            tvDetalle  = itemView.findViewById(R.id.tvDetalleDisponible);
            check      = itemView.findViewById(R.id.checkBoxSeleccionar);
        }

        void bind(ProductoResponse producto, boolean marcado) {
            tvNombre.setText(producto.getNombre());
            tvDetalle.setText("Cant: " + producto.getCantidad()
                    + (producto.getCategoria() != null
                    ? " / Cat: " + producto.getCategoria().getDescripcion()
                    : ""));

            // Carga de imagen con reintentos
            String base = ApiClient.getClient().baseUrl().toString();
            String url  = base + "imagen/" + producto.getUrlImg();
            Utils.cargaDeImagenesConReintento(
                    itemView.getContext(), img, url, 3
            );

            check.setChecked(marcado);

            // Pulsar en toda la tarjeta alterna la selección
            itemView.setOnClickListener(v -> {
                boolean nuevo = !seleccionados.contains(producto.getId());
                toggleSeleccion(producto.getId(), nuevo);
                check.setChecked(nuevo);
            });

            check.setOnClickListener(v -> {
                boolean nuevo = check.isChecked();
                toggleSeleccion(producto.getId(), nuevo);
            });
        }

        private void toggleSeleccion(int idProducto, boolean marcado) {
            if (marcado) seleccionados.add(idProducto);
            else          seleccionados.remove(idProducto);
        }
    }
}
