package com.wul4.paythunder.helloworld.login;


import android.app.ActionBar;
import android.os.Bundle;
import android.telecom.Call;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.wul4.paythunder.helloworld.R;
import com.wul4.paythunder.helloworld.Utils.ApiClient;
import com.wul4.paythunder.helloworld.Utils.ApiService;
import com.wul4.paythunder.helloworld.request.LoginRequest;


public class LoginFragment extends Fragment {

    private TextInputEditText etEmail, etPassword;
    private MaterialButton btnSignIn;


    public LoginFragment() {
        // Constructor vacío requerido
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflamos el layout
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        // Obtenemos las referencias a los elementos de la interfaz
        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);
        btnSignIn = view.findViewById(R.id.btnSignIn);

        // Configuramos el botón de inicio de sesión
        btnSignIn.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(getContext(), "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            }

            // Crear la petición de login
            LoginRequest loginRequest = new LoginRequest(email,password);


            // Obtener la instancia del servicio API
            ApiService apiService = ApiClient.getClient().create(ApiService.class);

            //Hacemos la llamada al login

            Call<Lo>

            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.nav_home);


        });

        return view;
    }

    /**
     * Ocultamos tanto en el onResume como en el onStop el actionbar
     */
    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }
    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }
}