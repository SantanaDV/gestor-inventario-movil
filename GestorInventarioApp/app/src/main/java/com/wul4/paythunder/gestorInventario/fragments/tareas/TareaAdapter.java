package com.wul4.paythunder.gestorInventario.fragments.tareas;

import android.annotation.SuppressLint;
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

public class TareaAdapter extends RecyclerView.Adapter<TareaAdapter.TareaViewHolder> {

    private List<Tarea> tareaList = new ArrayList<>();
    private final OnTareaClickListener listener;


    public interface OnTareaClickListener {
        void onClick(Tarea tarea);
    }



    public TareaAdapter(OnTareaClickListener listener) {
        this.listener = listener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setTareaList(List<Tarea> tareaList) {
        this.tareaList = tareaList;
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
        Tarea tarea = tareaList.get(position);

        holder.descripcion.setText(tarea.getDescripcion());
        holder.estado.setText(tarea.getEstado());
        holder.empleado.setText(tarea.getEmpleadoAsignado());
        holder.categoria.setText(tarea.getId_categoria());
        holder.fecha.setText(tarea.getFecha_asignacion());

        // Lógica de clic
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onClick(tarea);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tareaList != null ? tareaList.size() : 0;
    }

    static class TareaViewHolder extends RecyclerView.ViewHolder {

        final TextView descripcion, estado, empleado, categoria, fecha;

        public TareaViewHolder(@NonNull View itemView) {
            super(itemView);
            descripcion = itemView.findViewById(R.id.descripcion);
            estado = itemView.findViewById(R.id.estado);
            empleado = itemView.findViewById(R.id.empleado);
            categoria = itemView.findViewById(R.id.categoria);
            fecha = itemView.findViewById(R.id.fecha);
        }
    }
}
