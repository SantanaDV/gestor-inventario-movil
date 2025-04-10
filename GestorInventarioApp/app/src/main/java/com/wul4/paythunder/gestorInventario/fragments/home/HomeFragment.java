package com.wul4.paythunder.gestorInventario.fragments.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import com.wul4.paythunder.gestorInventario.utils.ApiClient;
import com.wul4.paythunder.gestorInventario.utils.interfaces.ApiHome;
import com.wul4.paythunder.gestorInventario.databinding.FragmentHomeBinding;
import com.wul4.paythunder.gestorInventario.response.HomeResponse;


// La clase HomeFragment es un Fragment que representa la pantalla principal de la aplicación.
public class HomeFragment extends Fragment {

    // binding es una instancia de FragmentHomeBinding que se utiliza para acceder a las vistas
    // definidas en el layout del fragmento.

    private FragmentHomeBinding binding;

    //declaro los TextView que voy a usar para mostrar los datos
    private TextView Productos;
    private TextView Conexistencias;
    private TextView Confaltantes;
    private TextView Total_usuarios;

    // Este método es llamado cuando el fragmento necesita crear su vista.
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Se infla el layout del fragmento utilizando FragmentHomeBinding.
        // Esto permite acceder a las vistas definidas en el layout a través de la variable binding.
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        //obtengo las referenicas de los TextView desde el binding
        Productos = binding.Productos;
        Conexistencias = binding.conexistencias;
        Confaltantes = binding.confaltantes;
        Total_usuarios = binding.totalUsuarios;

        //obtengo una instancia del ViewModel, que contiene la lógica de negocio y los datos que se mostrarán en la vista.
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        // Crea el servicio de la API para Home (ApiHome)
        ApiHome apiHome = ApiClient.getClient().create(ApiHome.class);
// Crea una instancia de HomeResponse pasando el ViewModel y el servicio
        HomeResponse homeResponse = new HomeResponse(homeViewModel, apiHome);
// Ejecuta la llamada a la API para actualizar los datos
        homeResponse.fetchData();


        // Se obtiene la vista raíz del fragmento.
        final TextView textView = binding.Productos;

        // Se observa el LiveData<String> que contiene el texto que se mostrará en el TextView.
        // Cuando el valor del LiveData cambia, se actualiza automáticamente el texto del TextView.
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Observar los datos del ViewModel y actualizar los TextView
        homeViewModel.getProductos().observe(getViewLifecycleOwner(), total -> {
            Productos.setText(String.valueOf(total));
        });

        homeViewModel.getConexistencias().observe(getViewLifecycleOwner(), existencias -> {
            Conexistencias.setText(String.valueOf(existencias));
        });

        homeViewModel.getConfaltantes().observe(getViewLifecycleOwner(), faltantes -> {
            Confaltantes.setText(String.valueOf(faltantes));
        });

        homeViewModel.getTotal_usuarios().observe(getViewLifecycleOwner(), UsuariosActivos -> {
            Total_usuarios.setText(String.valueOf(UsuariosActivos));

        });



        // Se devuelve la vista raíz del fragmento.
        return root;
    }

    // Este método es llamado cuando la vista del fragmento es destruida.
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}