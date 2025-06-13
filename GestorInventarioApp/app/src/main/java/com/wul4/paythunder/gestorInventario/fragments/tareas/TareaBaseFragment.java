package com.wul4.paythunder.gestorInventario.fragments.tareas;// imports iguales

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
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
import com.wul4.paythunder.gestorInventario.entities.Categoria;
import com.wul4.paythunder.gestorInventario.entities.Tarea;
import com.wul4.paythunder.gestorInventario.utils.ApiClient;
import com.wul4.paythunder.gestorInventario.utils.dto.TareaCategoriaDTO;
import com.wul4.paythunder.gestorInventario.utils.interfaces.ApiTarea;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TareaBaseFragment extends Fragment {

    private EditText etDescripcion, etFechaAsignacion, etFechaFinalizacion;
    private Spinner spEmpleadoAsignado, spEstado, spCategoria;
    private Button btnCrearNuevaTarea, btnEliminarTarea;
    private Tarea tareaActual;
    private List<Categoria> listaCategorias;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tarea_base, container, false);

        etDescripcion = view.findViewById(R.id.etDescripcion);
        spEstado = view.findViewById(R.id.spEstado);
        spEmpleadoAsignado = view.findViewById(R.id.spEmpleadoAsignado);
        spCategoria = view.findViewById(R.id.spCategoria);
        etFechaAsignacion = view.findViewById(R.id.etFechaAsignacion);
        etFechaAsignacion.setOnClickListener(v -> mostrarDatePicker(etFechaAsignacion));
        etFechaFinalizacion = view.findViewById(R.id.etFechaFinalizacion);
        etFechaFinalizacion.setOnClickListener(v -> mostrarDatePicker(etFechaFinalizacion));
        btnCrearNuevaTarea = view.findViewById(R.id.btnCrearNuevaTarea);
        btnEliminarTarea = view.findViewById(R.id.btnEliminarTarea);

        cargarSpinners();

        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("tarea_seleccionada")) {
            Tarea tarea = (Tarea) bundle.getSerializable("tarea_seleccionada");
            if (tarea != null) mostrarTarea(tarea);
        }

        btnCrearNuevaTarea.setOnClickListener(v -> guardarTarea());

        btnEliminarTarea.setOnClickListener(v -> {
            if (tareaActual != null && tareaActual.getId() != 0) {
                eliminarTarea(tareaActual.getId());
                tareaActual = null;
            } else {
                Toast.makeText(requireContext(), "No hay tarea seleccionada para eliminar", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void mostrarDatePicker(EditText editText) {
        final Calendar calendario = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                (view, year, month, dayOfMonth) -> {
                    String fechaSeleccionada = String.format(Locale.getDefault(), "%02d/%02d/%d", dayOfMonth, month + 1, year);
                    editText.setText(fechaSeleccionada);
                },
                calendario.get(Calendar.YEAR),
                calendario.get(Calendar.MONTH),
                calendario.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    public void mostrarTarea(Tarea tarea) {
        this.tareaActual = tarea;

        etDescripcion.setText(tarea.getDescripcion());
        etFechaAsignacion.setText(tarea.getFecha_asignacion());
        etFechaFinalizacion.setText(tarea.getFecha_finalizacion());
        setSpinnerSelection(spEmpleadoAsignado, tarea.getEmpleadoAsignado());
        setSpinnerSelection(spEstado, tarea.getEstado());
        setCategoriaSpinnerSelection(tarea.getId_categoria());

        btnEliminarTarea.setVisibility(View.VISIBLE);
    }

    private void setSpinnerSelection(Spinner spinner, Object value) {
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) spinner.getAdapter();
        if (adapter != null) {
            int position = adapter.getPosition(value.toString());
            if (position >= 0) spinner.setSelection(position);
        }
    }

    private void setCategoriaSpinnerSelection(int idCategoria) {
        for (int i = 0; i < listaCategorias.size(); i++) {
            if (listaCategorias.get(i).getId() == idCategoria) {
                spCategoria.setSelection(i + 1);
                break;
            }
        }
    }

    private void cargarSpinners() {
        ArrayAdapter<String> empleadosAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, new String[]{"Empleado 1", "Empleado 2"});
        empleadosAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEmpleadoAsignado.setAdapter(empleadosAdapter);

        ArrayAdapter<String> estadoAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, new String[]{"Pendiente", "En progreso", "Completado"});
        estadoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEstado.setAdapter(estadoAdapter);

        listaCategorias = obtenerCategorias();
        List<String> nombresCategorias = new ArrayList<>();
        nombresCategorias.add("Seleccione una categoría");
        for (Categoria categoria : listaCategorias) {
            nombresCategorias.add(categoria.getNombre());
        }

        ArrayAdapter<String> categoriaAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, nombresCategorias);
        categoriaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategoria.setAdapter(categoriaAdapter);
    }

    private List<Categoria> obtenerCategorias() {
        List<Categoria> lista = new ArrayList<>();
        lista.add(new Categoria("Urgente", 1));
        lista.add(new Categoria("Normal", 2));
        lista.add(new Categoria("Baja", 3));
        return lista;
    }

    private void guardarTarea() {
        String descripcion = etDescripcion.getText().toString().trim();
        String fechaAsignacionStr = etFechaAsignacion.getText().toString().trim();
        String fechaFinalizacionStr = etFechaFinalizacion.getText().toString().trim();
        String empleado_asignado = spEmpleadoAsignado.getSelectedItem().toString();
        String estado = spEstado.getSelectedItem().toString();
        int posCategoria = spCategoria.getSelectedItemPosition();

        if (descripcion.isEmpty() || fechaAsignacionStr.isEmpty() || fechaFinalizacionStr.isEmpty()) {
            Toast.makeText(requireContext(), "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (posCategoria == 0) {
            Toast.makeText(requireContext(), "Selecciona una categoría válida", Toast.LENGTH_SHORT).show();
            return;
        }

        int idCategoria = listaCategorias.get(posCategoria - 1).getId();

        SimpleDateFormat formatoBackend = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault());
        SimpleDateFormat formatoVista = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        String fecha_asignacion, fecha_finalizacion;

        try {
            fecha_asignacion = formatoBackend.format(Objects.requireNonNull(formatoVista.parse(fechaAsignacionStr)));
            fecha_finalizacion = formatoBackend.format(Objects.requireNonNull(formatoVista.parse(fechaFinalizacionStr)));
        } catch (ParseException | NullPointerException e) {
            Toast.makeText(requireContext(), "Error en el formato de fecha", Toast.LENGTH_SHORT).show();
            return;
        }

        TareaCategoriaDTO tareaDTO = new TareaCategoriaDTO();
        if (tareaActual != null) tareaDTO.setId(tareaActual.getId());
        tareaDTO.setDescripcion(descripcion);
        tareaDTO.setEmpleado_asignado(empleado_asignado);
        tareaDTO.setEstado(estado);
        tareaDTO.setFecha_asignacion(fecha_asignacion);
        tareaDTO.setFecha_finalizacion(fecha_finalizacion);
        tareaDTO.setId_categoria(idCategoria);

        ApiTarea apiTarea = ApiClient.getClient().create(ApiTarea.class);
        Call<Tarea> call = apiTarea.crearTarea(tareaDTO);
        call.enqueue(new Callback<Tarea>() {
            @Override
            public void onResponse(Call<Tarea> call, Response<Tarea> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Tarea guardada con éxito", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Error al guardar tarea", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Tarea> call, Throwable t) {
                Log.e("API_ERROR", "Error al guardar tarea", t);
            }
        });

//        Call<Tarea> call = (tareaActual != null && tareaActual.getId() != 0)
//                ? apiTarea.actualizarTarea(tareaDTO)
//                : apiTarea.getCrearTarea(tareaDTO);
//
//        call.enqueue(new Callback<Tarea>() {
//            @Override
//            public void onResponse(@NonNull Call<Tarea> call, @NonNull Response<Tarea> response) {
//                if (response.isSuccessful()) {
//                    Toast.makeText(requireContext(), "Tarea guardada con éxito", Toast.LENGTH_SHORT).show();
//                    tareaActual = response.body();
//                    btnEliminarTarea.setVisibility(View.VISIBLE);
//                } else {
//                    Toast.makeText(requireContext(), "Error al guardar tarea", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<Tarea> call, @NonNull Throwable t) {
//                Log.e("API_ERROR", "Error al guardar tarea", t);
//                Toast.makeText(requireContext(), "Fallo al guardar tarea. Ver logcat.", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    private void eliminarTarea(int id) {
        ApiTarea apiTarea = ApiClient.getClient().create(ApiTarea.class);
        Call<Tarea> call = apiTarea.eliminarTarea(id);

        call.enqueue(new Callback<Tarea>() {
            @Override
            public void onResponse(@NonNull Call<Tarea> call, @NonNull Response<Tarea> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(requireContext(), "Tarea eliminada con éxito", Toast.LENGTH_SHORT).show();
                    tareaActual = null;
                    limpiarCampos();
                    btnEliminarTarea.setVisibility(View.GONE);
                } else {
                    Toast.makeText(requireContext(), "Error al eliminar tarea", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Tarea> call, @NonNull Throwable t) {
                Toast.makeText(requireContext(), "Fallo de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
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
