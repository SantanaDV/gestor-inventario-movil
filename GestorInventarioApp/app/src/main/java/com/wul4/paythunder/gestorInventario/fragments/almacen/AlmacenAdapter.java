package com.wul4.paythunder.gestorInventario.fragments.almacen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.wul4.paythunder.gestorInventario.R;
import com.wul4.paythunder.gestorInventario.response.AlmacenResponse;
import java.util.List;

public class AlmacenAdapter
        extends RecyclerView.Adapter<AlmacenAdapter.ViewHolder> {

    private List<AlmacenResponse> datos;
    private final OnClickListener listener;

    public interface OnClickListener {
        void onAlmacenClick(AlmacenResponse almacen);
    }

    public AlmacenAdapter(List<AlmacenResponse> datos,
                          OnClickListener listener) {
        this.datos = datos;
        this.listener = listener;
    }

    // Nuevo método para actualizar datos
    public void updateData(List<AlmacenResponse> nuevosDatos) {
        this.datos = nuevosDatos;
        notifyDataSetChanged();
    }

    @NonNull @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_almacen, parent, false);
        return new ViewHolder(v);
    }

    @Override public void onBindViewHolder(@NonNull ViewHolder h, int pos) {
        AlmacenResponse a = datos.get(pos);
        h.tvUbicacion.setText(a.getUbicacion());
        h.itemView.setOnClickListener(v -> listener.onAlmacenClick(a));
    }

    @Override public int getItemCount() {
        return datos != null ? datos.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvUbicacion;
        ViewHolder(View itemView) {
            super(itemView);
            tvUbicacion = itemView.findViewById(R.id.tvUbicacion);
        }
    }
}
