// File: app/src/main/java/com/wul4/paythunder/gestorInventario/fragments/productosEstanteria/ProductosEstanteriaAdapter.java
package com.wul4.paythunder.gestorInventario.fragments.productosEstanteria;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wul4.paythunder.gestorInventario.databinding.ItemProductoEstanteriaBinding;
import com.wul4.paythunder.gestorInventario.response.ProductoResponse;
import com.wul4.paythunder.gestorInventario.utils.ApiClient;
import com.wul4.paythunder.gestorInventario.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ProductosEstanteriaAdapter
        extends RecyclerView.Adapter<ProductosEstanteriaAdapter.ViewHolder> {

    private final List<ProductoResponse> datos = new ArrayList<>();
    private final OnDesasignarListener listener;

    public interface OnDesasignarListener {
        void onDesasignar(ProductoResponse producto);
    }

    public ProductosEstanteriaAdapter(OnDesasignarListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProductoEstanteriaBinding binding = ItemProductoEstanteriaBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(datos.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    /** Actualiza la lista de datos y refresca el RecyclerView */
    public void setDatos(List<ProductoResponse> lista) {
        datos.clear();
        if (lista != null) datos.addAll(lista);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemProductoEstanteriaBinding binding;

        ViewHolder(ItemProductoEstanteriaBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(ProductoResponse producto, OnDesasignarListener listener) {
            // Construimos la URL completa igual que en ProductosFragment
            String base = ApiClient.getClient().baseUrl().toString(); // p.ej. "http://10.110.4.43:8080/"
            String url = base + "imagen/" + producto.getUrlImg();

            // Carga con reintento
            Utils.cargaDeImagenesConReintento(
                    binding.getRoot().getContext(),
                    binding.imgProducto,
                    url,
                    3
            );

            binding.tvNombre.setText(producto.getNombre());
            binding.tvCantidad.setText("Cantidad: " + producto.getCantidad());
            binding.chipEstado.setText(producto.getEstado());
            int color = "activo".equalsIgnoreCase(producto.getEstado())
                    ? android.R.color.holo_green_light
                    : android.R.color.holo_red_dark;
            binding.chipEstado.setChipBackgroundColorResource(color);

            if (producto.getCategoria() != null) {
                binding.tvCategoria.setText("Categoría: " + producto.getCategoria().getDescripcion());
            } else {
                binding.tvCategoria.setText("Categoría: —");
            }

            if (producto.getBalda() != null) {
                binding.tvBalda.setText("Balda: " + producto.getBalda());
            } else {
                binding.tvBalda.setText("Balda: —");
            }

            binding.btnAccion.setOnClickListener(v -> listener.onDesasignar(producto));
        }
    }
}
