package com.wul4.paythunder.gestorInventario.fragments.tareas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wul4.paythunder.gestorInventario.R;
import com.wul4.paythunder.gestorInventario.entities.Tarea;

import java.util.ArrayList;
import java.util.List;
/*
clase que gestiona los datos y crea cada item de la lista
le dice al RecyclerView que datos mostrar y como inflar cada elemento/
 */
public class TareaAdapter extends RecyclerView.Adapter<TareaAdapter.TareaViewHolder> {

    private List<Tarea> tareas = new ArrayList<>();

    public void setTareas(List<Tarea> tareas) {
        this.tareas = tareas;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TareaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tarea, parent, false);
        return new TareaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TareaViewHolder holder, int position) {
        Tarea tarea = tareas.get(position);

        holder.tvDescripcion.setText(tarea.getDescripcion());
        holder.tvEstado.setText(tarea.getEstado());
        holder.tvEmpleado.setText(tarea.getEmpleadoAsignado());
        holder.tvCategoria.setText(tarea.getId_categoria());
        holder.tvFecha.setText(tarea.getFecha_asignacion());
    }

    @Override
    public int getItemCount() {
        return tareas.size();
    }

    static class TareaViewHolder extends RecyclerView.ViewHolder {

        TextView tvDescripcion, tvEstado, tvEmpleado, tvCategoria, tvFecha;

        public TareaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcion);
            tvEstado = itemView.findViewById(R.id.tvEstado);
            tvEmpleado = itemView.findViewById(R.id.tvEmpleado);
            tvCategoria = itemView.findViewById(R.id.tvCategoria);
            tvFecha = itemView.findViewById(R.id.tvFecha);
        }
    }
}
