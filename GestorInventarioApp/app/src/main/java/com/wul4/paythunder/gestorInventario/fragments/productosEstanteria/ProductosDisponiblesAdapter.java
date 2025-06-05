package com.wul4.paythunder.gestorInventario.fragments.productosEstanteria;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wul4.paythunder.gestorInventario.R;
import com.wul4.paythunder.gestorInventario.response.ProductoResponse;
import com.wul4.paythunder.gestorInventario.utils.ApiClient;
import com.wul4.paythunder.gestorInventario.utils.Utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Adapter que muestra los productos “disponibles” (filtrados por ViewModel).
 * Cada ítem lleva un CheckBox para seleccionar/desmarcar.
 * getProductosSeleccionados() devuelve la lista de ProductoResponse marcados.
 */
public class ProductosDisponiblesAdapter
        extends RecyclerView.Adapter<ProductosDisponiblesAdapter.ViewHolder> {

    private static final String TAG = "ProductosDisp";

    // Lista completa de elementos que muestra el RecyclerView
    private final List<ProductoResponse> datos = new ArrayList<>();

    // Set de IDs de productos marcados
    private final Set<Integer> seleccionados = new HashSet<>();

    /**
     * Devuelve la lista de ProductoResponse que están seleccionados (checkbox marcado).
     */
    public List<ProductoResponse> getProductosSeleccionados() {
        List<ProductoResponse> lista = new ArrayList<>();
        for (ProductoResponse p : datos) {
            if (seleccionados.contains(p.getId())) {
                lista.add(p);
            }
        }
        // Log para verificar qué productos se devuelven como “seleccionados”
        Log.d(TAG, "getProductosSeleccionados(): " + lista.size() + " elementos marcados");
        for (ProductoResponse p : lista) {
            Log.d(TAG, "   • ID=" + p.getId() + " | Nombre=" + p.getNombre());
        }
        return lista;
    }

    /**
     * Devuelve una lista de IDs (enteros) de los productos seleccionados.
     */
    public List<Integer> getIdsSeleccionados() {
        List<Integer> listaIds = new ArrayList<>(seleccionados);
        Log.d(TAG, "getIdsSeleccionados(): " + listaIds.size() + " IDs => " + listaIds);
        return listaIds;
    }

    /**
     * Pone la nueva lista completa (datos) y limpia cualquier selección previa.
     */
    public void setDatos(List<ProductoResponse> nuevosDatos) {
        datos.clear();
        seleccionados.clear();
        if (nuevosDatos != null) {
            datos.addAll(nuevosDatos);
        }
        Log.d(TAG, "setDatos(): recibió " + datos.size() + " productos. Selección reiniciada.");
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
        boolean marcado = seleccionados.contains(p.getId());
        holder.bind(p, marcado);
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
            img       = itemView.findViewById(R.id.imgProductoDisponible);
            tvNombre  = itemView.findViewById(R.id.tvNombreDisponible);
            tvDetalle = itemView.findViewById(R.id.tvDetalleDisponible);
            check     = itemView.findViewById(R.id.checkBoxSeleccionar);
        }

        void bind(ProductoResponse producto, boolean marcado) {
            tvNombre.setText(producto.getNombre());
            tvDetalle.setText(
                    "Cant: " + producto.getCantidad() +
                            (producto.getCategoria() != null
                                    ? " / Cat: " + producto.getCategoria().getDescripcion()
                                    : "")
            );

            // Carga de imagen con reintentos (si tenías Glide configurado):
            String base = ApiClient.getClient().baseUrl().toString();
            String url  = base + "imagen/" + producto.getUrlImg();
            Utils.cargaDeImagenesConReintento(
                    itemView.getContext(), img, url, 3
            );

            check.setChecked(marcado);

            // Al pulsar en toda la tarjeta, se alterna el estado del checkbox
            itemView.setOnClickListener(v -> {
                boolean nuevo = !seleccionados.contains(producto.getId());
                toggleSeleccion(producto.getId(), nuevo);
                check.setChecked(nuevo);
                Log.d(TAG, "onClickItem id=" + producto.getId() +
                        " -> ahora seleccionado=" + nuevo);
            });

            // También al pulsar directamente en el CheckBox
            check.setOnClickListener(v -> {
                boolean nuevo = check.isChecked();
                toggleSeleccion(producto.getId(), nuevo);
                Log.d(TAG, "onCheckClick id=" + producto.getId() +
                        " -> ahora seleccionado=" + nuevo);
            });
        }

        private void toggleSeleccion(int idProducto, boolean marcado) {
            if (marcado) {
                seleccionados.add(idProducto);
            } else {
                seleccionados.remove(idProducto);
            }
            Log.d(TAG, "toggleSeleccion() -> ID=" + idProducto +
                    (marcado ? " añadido." : " eliminado."));
        }
    }
}
