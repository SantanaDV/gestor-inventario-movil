package com.wul4.paythunder.gestorInventario.fragments.tareas;

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
import com.wul4.paythunder.gestorInventario.entities.Tarea;
import com.wul4.paythunder.gestorInventario.utils.ApiClient;
import com.wul4.paythunder.gestorInventario.utils.interfaces.ApiTarea;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class TareaHacerFragment extends Fragment {

    private EditText etDescripcion, etFechaAsignacion, etFechaFinalizacion;
    private Spinner spEmpleadoAsignado, spEstado, spCategoria;
    private Button btnGuardar, btnEliminar;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tarea, container, false);

        etDescripcion = view.findViewById(R.id.tvDescripcion);
        spEstado = view.findViewById(R.id.spEstado);
        spEmpleadoAsignado = view.findViewById(R.id.spEmpleadoAsignado);
        spCategoria = view.findViewById(R.id.spCategoria);
        etFechaAsignacion = view.findViewById(R.id.etFechaAsignacion);
        etFechaFinalizacion = view.findViewById(R.id.etFechaFinalizacion);
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

    private void guardarTarea() throws ParseException {
        String descripcion = etDescripcion.getText().toString();
        String fecha_asignacion = etFechaAsignacion.getText().toString();
        String fecha_finalizacion = etFechaFinalizacion.getText().toString();
        String empleado_asignado = spEmpleadoAsignado.getSelectedItem().toString();
        String estado = spEstado.getSelectedItem().toString();
        String categoria = spCategoria.getSelectedItem().toString();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        fecha_asignacion = String.valueOf(dateFormat.parse(fecha_asignacion));
        fecha_finalizacion = String.valueOf(dateFormat.parse(fecha_finalizacion));

        Tarea nuevaTarea = new Tarea(descripcion, fecha_asignacion, empleado_asignado, estado, categoria);

        ApiTarea apiTarea = ApiClient.getClient().create(ApiTarea.class);
        Call<List<Tarea>> call = apiTarea.crearTarea(nuevaTarea);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Tarea guardada con éxito", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Error al guardar tarea", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(), "Fallo de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void eliminarTarea() {
        // Lógica para borrar por ID o algún identificador
        Toast.makeText(getContext(), "Tarea eliminada", Toast.LENGTH_SHORT).show();
    }
}
