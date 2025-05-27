package com.wul4.paythunder.gestorInventario.fragments.productosEstanteria;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wul4.paythunder.gestorInventario.databinding.ItemProductoEstanteriaBinding;
import com.wul4.paythunder.gestorInventario.response.ProductoResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter para mostrar la lista de productos de una estantería.
 */
public class ProductosEstanteriaAdapter
        extends RecyclerView.Adapter<ProductosEstanteriaAdapter.ViewHolder> {

    private final List<ProductoResponse> datos = new ArrayList<>();

    public ProductosEstanteriaAdapter() { }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProductoEstanteriaBinding binding = ItemProductoEstanteriaBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(datos.get(position));
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    /** Reemplaza los datos actuales y refresca la lista */
    public void setDatos(List<ProductoResponse> lista) {
        datos.clear();
        datos.addAll(lista);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemProductoEstanteriaBinding binding;

        ViewHolder(ItemProductoEstanteriaBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(ProductoResponse producto) {
            binding.tvNombre.setText(producto.getNombre());
            binding.tvDescripcion.setText(producto.getDescripcion());
            binding.tvCantidad.setText(String.valueOf(producto.getCantidad()));
        }
    }
}
