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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.wul4.paythunder.gestorInventario.R;
import com.wul4.paythunder.gestorInventario.entities.Tarea;
import com.wul4.paythunder.gestorInventario.utils.ApiClient;
import com.wul4.paythunder.gestorInventario.utils.interfaces.ApiTarea;

import java.text.ParseException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class TareaBaseFragment extends Fragment {

    private EditText etDescripcion, etFechaAsignacion, etFechaFinalizacion;
    private Spinner spEmpleadoAsignado, spEstado, spCategoria;
    private Button btnGuardar, btnEliminar;
    private Tarea tareaActual;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tarea, container, false);

    // inicializamos las vistas
        etDescripcion = view.findViewById(R.id.descripcion);
        spEstado = view.findViewById(R.id.spEstado);
        spEmpleadoAsignado = view.findViewById(R.id.spEmpleadoAsignado);
        spCategoria = view.findViewById(R.id.spCategoria);
        etFechaAsignacion = view.findViewById(R.id.etFechaAsignacion);
        etFechaFinalizacion = view.findViewById(R.id.etFechaFinalizacion);
        btnGuardar = view.findViewById(R.id.btnGuardar);
        btnEliminar = view.findViewById(R.id.btnEliminar);

        cargarSpinners();

        btnGuardar.setOnClickListener(v -> {
            try {
                guardarTarea();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });

        btnEliminar.setOnClickListener(v -> {
            if (tareaActual != null && tareaActual.getId() != 0) {
                eliminarTarea(tareaActual.getId());
                tareaActual = null;
            } else {
                Toast.makeText(getContext(), "No hay tarea seleccionada para eliminar", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    public void mostrarTarea(Tarea tarea) {
        this.tareaActual = tarea;

        etDescripcion.setText(tarea.getDescripcion());
        etFechaAsignacion.setText(tarea.getFecha_asignacion());
        etFechaFinalizacion.setText(tarea.getFecha_finalizacion());

        // Setear valores a los spinners como hicimos antes
        // Para seleccionar en los Spinners el valor correcto
        setSpinnerSelection(spEmpleadoAsignado, tarea.getEmpleadoAsignado());
        setSpinnerSelection(spEstado, String.valueOf(tarea.getEstado()));
        setSpinnerSelection(spCategoria, String.valueOf(tarea.getId_categoria()));

        btnEliminar.setVisibility(View.VISIBLE);
    }

    private void setSpinnerSelection(Spinner spinner, Object value) {
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) spinner.getAdapter();
        if (adapter != null) {
            int position = adapter.getPosition(value.toString());
            if (position >= 0) {
                spinner.setSelection(position);

            }
        }
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

        fecha_asignacion = String.valueOf(dateFormat.parse(String.valueOf(fecha_asignacion)));
        fecha_finalizacion = String.valueOf(dateFormat.parse(String.valueOf(fecha_finalizacion)));

        Tarea tarea = new Tarea();
        if (tareaActual != null){
            tarea.setId(tareaActual.getId());
        }
        
        tarea.setDescripcion(descripcion);
        tarea.setEmpleado_asignado(empleado_asignado);
        tarea.setEstado(Integer.parseInt(estado));
        tarea.setFecha_asignacion(fecha_asignacion);
        tarea.setFecha_finalizacion(fecha_finalizacion);
        tarea.setId_categoria(Integer.parseInt(categoria));


        ApiTarea apiTarea = ApiClient.getClient().create(ApiTarea.class);
        
        Call<Tarea> call;
        if (tareaActual != null && tareaActual.getId() != 0) {
            call = apiTarea.actualizarTarea(tarea);
        } else {
            call = apiTarea.crearTarea(tarea);
        }
        
        call.enqueue(new Callback<Tarea>() {
            @Override
            public void onResponse(@NonNull Call<Tarea> call, @NonNull Response<Tarea> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Tarea guardada con éxito", Toast.LENGTH_SHORT).show();
                    tareaActual = response.body();
                    btnEliminar.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(getContext(), "Error al guardar tarea", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Tarea> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Fallo de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void eliminarTarea(int id) {
        ApiTarea apiTarea = ApiClient.getClient().create(ApiTarea.class);
        Call<Tarea> call = apiTarea.eliminarTarea(id);

        call.enqueue(new Callback<Tarea>() {
            @Override
            public void onResponse(@NonNull Call<Tarea> call, @NonNull Response<Tarea> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Tarea eliminada con éxito", Toast.LENGTH_SHORT).show();
                    tareaActual = null;
                    limpiarCampos();
                    btnEliminar.setVisibility(View.GONE);
                } else {
                    Toast.makeText(getContext(), "Error al eliminar tarea", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Tarea> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Fallo de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void limpiarCampos() {
        etDescripcion.setText("");
        etFechaAsignacion.setText("");
        etFechaFinalizacion.setText("");
        spEmpleadoAsignado.setSelection(0);
        spEstado.setSelection(0);
        spCategoria.setSelection(0);
    }
}

