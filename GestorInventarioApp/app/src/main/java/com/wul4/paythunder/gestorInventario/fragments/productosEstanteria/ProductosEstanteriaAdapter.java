// .java
package com.wul4.paythunder.gestorInventario.fragments.productosEstanteria;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.wul4.paythunder.gestorInventario.databinding.ItemProductoEstanteriaBinding;
import com.wul4.paythunder.gestorInventario.response.ProductoResponse;
import com.wul4.paythunder.gestorInventario.utils.ApiClient;
import com.wul4.paythunder.gestorInventario.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ProductosEstanteriaAdapter
        extends RecyclerView.Adapter<ProductosEstanteriaAdapter.ViewHolder> {

    public interface OnItemAction {
        /** La estantería ya estaba asignada; querrá editar balda */
        void onEditarBalda(ProductoResponse producto);
        /** Quitar producto de la estantería */
        void onEliminarDeEstanteria(ProductoResponse producto);
    }

    private final List<ProductoResponse> datos = new ArrayList<>();
    private final OnItemAction actionListener;

    public ProductosEstanteriaAdapter(OnItemAction actionListener) {
        this.actionListener = actionListener;
    }

    public void setDatos(List<ProductoResponse> lista) {
        datos.clear();
        if (lista != null) datos.addAll(lista);
        notifyDataSetChanged();
    }

    @NonNull @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProductoEstanteriaBinding b = ItemProductoEstanteriaBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(b);
    }

    @Override public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(datos.get(position));
    }

    @Override public int getItemCount() {
        return datos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemProductoEstanteriaBinding b;
        ViewHolder(ItemProductoEstanteriaBinding binding) {
            super(binding.getRoot());
            this.b = binding;
        }
        void bind(ProductoResponse p) {
            // Carga de imagen
            String url = ApiClient.getClient().baseUrl() + "imagen/" + p.getUrlImg();
            Utils.cargaDeImagenesConReintento(
                    b.getRoot().getContext(), b.imgProducto, url, 3
            );

            b.tvNombre.setText(p.getNombre());
            b.tvCantidad.setText("Cant: " + p.getCantidad());
            b.chipEstado.setText(p.getEstado());
            int color = "activo".equalsIgnoreCase(p.getEstado())
                    ? android.R.color.holo_green_light
                    : android.R.color.holo_red_dark;
            b.chipEstado.setChipBackgroundColorResource(color);

            b.tvCategoria.setText(
                    p.getCategoria()!=null
                            ? "Cat: "+p.getCategoria().getDescripcion()
                            : "Cat: —"
            );
            b.tvBalda.setText(
                    p.getBalda()!=null
                            ? "Balda: "+p.getBalda()
                            : "Balda: —"
            );

            // **Short-click** en el botón para desasignar completo
            b.btnAccion.setOnClickListener(v ->
                    actionListener.onEliminarDeEstanteria(p)
            );

            // **Long-click** en toda la tarjeta → menú “Editar balda” / “Eliminar”
            ((MaterialCardView)b.getRoot()).setOnLongClickListener(v -> {
                androidx.appcompat.widget.PopupMenu popup =
                        new androidx.appcompat.widget.PopupMenu(v.getContext(), v);
                popup.getMenu().add("Editar balda");
                popup.getMenu().add("Eliminar de estantería");
                popup.setOnMenuItemClickListener(item -> {
                    String title = item.getTitle().toString();
                    if (title.equals("Editar balda")) {
                        actionListener.onEditarBalda(p);
                    } else {
                        actionListener.onEliminarDeEstanteria(p);
                    }
                    return true;
                });
                popup.show();
                return true;
            });
        }
    }
}
