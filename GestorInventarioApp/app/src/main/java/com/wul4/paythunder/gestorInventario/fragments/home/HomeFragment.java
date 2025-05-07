
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

    private TextView conExistencias;
    private TextView conFaltantes;
    private TextView usuariosActivos;

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
        productosContados = binding.textProductosContados;
        conExistencias = binding.conExistencias;
        conFaltantes = binding.conFaltantes;
        usuariosActivos = binding.usuariosActivos;

        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class); // Inicializar ViewModel

        // Crear instancia del servicio API y del Response que maneja las llamadas
        ApiHome apiHome = ApiClient.getClient().create(ApiHome.class);
        HomeResponse homeResponse = new HomeResponse(homeViewModel, apiHome);
        homeResponse.fetchAllData(); // Llamada para obtener los datos


        homeViewModel.gettotalProductosContados().observe(getViewLifecycleOwner(), productosContados -> {
            this.productosContados.setText(String.valueOf(productosContados));

        });

        homeViewModel.getlistarConExistencias().observe(getViewLifecycleOwner(), conexistencias -> {
            this.conExistencias.setText(String.valueOf(conexistencias));
        });


        homeViewModel.getListarConFaltantes().observe(getViewLifecycleOwner(), confaltantes -> {
            this.conFaltantes.setText(String.valueOf(confaltantes));
        });

       homeViewModel.gettotalProductosContados().observe(getViewLifecycleOwner(), productosContados -> {
           this.productosContados.setText(String.valueOf(productosContados));

       });

       homeViewModel.getlistarConExistencias().observe(getViewLifecycleOwner(), conexistencias -> {
           this.conExistencias.setText(String.valueOf(conexistencias));
       });


        homeViewModel.getListarConFaltantes().observe(getViewLifecycleOwner(), confaltantes -> {
            this.conFaltantes.setText(String.valueOf(confaltantes));
        });

        homeViewModel.getlistarusuariosactivos().observe(getViewLifecycleOwner(), UsuariosActivos -> {
            this.usuariosActivos.setText(String.valueOf(UsuariosActivos));

        });


    }

    // Este método es llamado cuando la vista del fragmento es destruida.
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}