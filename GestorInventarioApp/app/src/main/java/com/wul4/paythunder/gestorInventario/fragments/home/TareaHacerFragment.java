package com.wul4.paythunder.gestorInventario.fragments.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.wul4.paythunder.gestorInventario.R;

public class TareaHacerFragment extends Fragment {

    private EditText etDescripcion, etFechaAsignacion;
    private Spinner spEmpleadoAsignado, spEstado, spCategoria;
    private Button btnGuardar, btnEliminar;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tarea, container, false);

        etDescripcion = view.findViewById(R.id.tvDescripcion);
        etFechaAsignacion = view.findViewById(R.id.etFechaAsignacion);
        spEmpleadoAsignado = view.findViewById(R.id.spEmpleadoAsignado);
        spEstado = view.findViewById(R.id.spEstado);
        spCategoria = view.findViewById(R.id.spCategoria);
        btnGuardar = view.findViewById(R.id.btnGuardar);
        btnEliminar = view.findViewById(R.id.btnEliminar);

        cargarSpinners();

        btnGuardar.setOnClickListener(v -> guardarTarea());
        btnEliminar.setOnClickListener(v -> eliminarTarea());

        return view;
    }

    private void cargarSpinners() {
        // Carga los datos desde una lista o base de datos
        // Por ejemplo:
        ArrayAdapter<String> empleadosAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, new String[]{"Empleado 1", "Empleado 2"});
        spEmpleadoAsignado.setAdapter(empleadosAdapter);

        ArrayAdapter<String> estadoAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, new String[]{"Pendiente", "En progreso", "Completado"});
        spEstado.setAdapter(estadoAdapter);

        ArrayAdapter<String> categoriaAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, new String[]{"Urgente", "Normal", "Baja"});
        spCategoria.setAdapter(categoriaAdapter);
    }

    private void guardarTarea() {
        String descripcion = etDescripcion.getText().toString();
        String fecha = etFechaAsignacion.getText().toString();
        String empleado = spEmpleadoAsignado.getSelectedItem().toString();
        String estado = spEstado.getSelectedItem().toString();
        String categoria = spCategoria.getSelectedItem().toString();

        // Aquí haces la lógica para guardar en base de datos
        Toast.makeText(getContext(), "Tarea guardada", Toast.LENGTH_SHORT).show();
    }

    private void eliminarTarea() {
        // Lógica para borrar por ID o algún identificador
        Toast.makeText(getContext(), "Tarea eliminada", Toast.LENGTH_SHORT).show();
    }
}
