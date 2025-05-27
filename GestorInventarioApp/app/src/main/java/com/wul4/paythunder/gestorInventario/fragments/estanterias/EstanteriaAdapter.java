package com.wul4.paythunder.gestorInventario.fragments.estanterias;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.wul4.paythunder.gestorInventario.databinding.ItemEstanteriaBinding;
import com.wul4.paythunder.gestorInventario.response.EstanteriaResponse;
import java.util.List;

public class EstanteriaAdapter
        extends RecyclerView.Adapter<EstanteriaAdapter.ViewHolder> {

    public interface OnClick {
        void onClick(EstanteriaResponse estanteria);
    }

    private List<EstanteriaResponse> datos;
    private final OnClick listener;

    public EstanteriaAdapter(List<EstanteriaResponse> datos, OnClick listener) {
        this.datos = datos;
        this.listener = listener;
    }

    public void setDatos(List<EstanteriaResponse> nuevosDatos) {
        this.datos = nuevosDatos;
        notifyDataSetChanged();
    }

    @NonNull @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemEstanteriaBinding binding = ItemEstanteriaBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EstanteriaResponse est = datos.get(position);
        holder.binding.textNombre.setText(est.getNombre());
        holder.binding.getRoot().setOnClickListener(v -> listener.onClick(est));
    }

    @Override public int getItemCount() {
        return datos != null ? datos.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final ItemEstanteriaBinding binding;
        ViewHolder(ItemEstanteriaBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
