package com.wul4.paythunder.gestorInventario.fragments.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
    private TextView productosContados;

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
        return binding.getRoot(); // Inflamos la vista, pero la lógica se mueve a onViewCreated
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inicializamos TextViews usando ViewBinding
        productosContados = binding.productosContados;
        Conexistencias = binding.conexistencias;
        Confaltantes = binding.confaltantes;
        Total_usuarios = binding.totalUsuarios;

        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class); // Inicializar ViewModel

        // Crear instancia del servicio API y del Response que maneja las llamadas
        ApiHome apiHome = ApiClient.getClient().create(ApiHome.class);
        HomeResponse homeResponse = new HomeResponse(homeViewModel, apiHome);
        homeResponse.fetchAllData(); // Llamada para obtener los datos


        // Se obtiene la vista raíz del fragmento.
       // final TextView textView = binding.Productos;

       // homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Observadores para actualizar la UI automáticamente
        homeViewModel.getproductosContados().observe(getViewLifecycleOwner(), productosCont -> {
            productosContados.setText(String.valueOf(productosCont));
        });

        homeViewModel.getConexistencias().observe(getViewLifecycleOwner(), conexistencias -> {
            Conexistencias.setText(String.valueOf(conexistencias));
        });

        homeViewModel.getConfaltantes().observe(getViewLifecycleOwner(), confaltantes -> {
            Confaltantes.setText(String.valueOf(confaltantes));
        });

        homeViewModel.getTotal_usuarios().observe(getViewLifecycleOwner(), UsuariosActivos -> {
            Total_usuarios.setText(String.valueOf(UsuariosActivos));

        });


    }

    // Este método es llamado cuando la vista del fragmento es destruida.
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}