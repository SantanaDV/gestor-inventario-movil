package com.wul4.paythunder.gestorInventario.fragments.login;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.wul4.paythunder.gestorInventario.R;
import com.wul4.paythunder.gestorInventario.utils.ApiClient;
import com.wul4.paythunder.gestorInventario.utils.SimpleTextWatcher;
import com.wul4.paythunder.gestorInventario.utils.interfaces.ApiAuth;
import com.wul4.paythunder.gestorInventario.request.LoginRequest;
import com.wul4.paythunder.gestorInventario.response.LoginResponse;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginFragment extends Fragment {

    private TextInputEditText etEmail, etPassword;
    private CheckBox cbRememberMe;
    private MaterialButton btnSignIn;
    private SharedPreferences preferences;
    private LoginViewModel loginViewModel;

    private Log log;


    public LoginFragment() {
        // Constructor vacío requerido
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflamos el layout
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        //Inicializamos el viewModel
        loginViewModel = new LoginViewModel();

        //Desabilitamos el boton de retroceso en el fragmento de login
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Aquí no hacemos nada, evitando que se ejecute la acción de retroceso
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
        // Obtenemos las referencias a los elementos de la interfaz
        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);
        btnSignIn = view.findViewById(R.id.btnSignIn);
        cbRememberMe = view.findViewById(R.id.cbRememberMe);

        //Si hay algo en el viewModel lo mostramos en los campos de texto
        if (loginViewModel.getUsername().getValue() != null) {
            etEmail.setText(loginViewModel.getUsername().getValue());
        }
        if (loginViewModel.getPassword().getValue() != null) {
            etPassword.setText(loginViewModel.getPassword().getValue());
        }

        //Actualizamos el viewmodel con los valores de los campos de texto en el momento
        //Utilizamos una clase abstracta para simplificar el código
        etEmail.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loginViewModel.setUsername(s.toString());
            }
        });

        etPassword.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loginViewModel.setPassword(s.toString());
            }
        });

        //Desactivamos el menu de settings en el fragmento de login


        //Obtenemos las preferencias compartidas
        preferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        //Obtenemos del shared preferences las credenciales guardadas
        String emailGuardado = preferences.getString("email", "");
        String passwordGuardado = preferences.getString("password", "");

        //Si hay credenciales guardadas las mostramos en los campos de texto y activamos el campo de recordar
        if (!emailGuardado.isEmpty() && !passwordGuardado.isEmpty()) {
            etEmail.setText(emailGuardado);
            etPassword.setText(passwordGuardado);
            cbRememberMe.setChecked(true);
        }

        // Configuramos el botón de inicio de sesión
        btnSignIn.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(getContext(), "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            }

            // Creamos una instancia de LoginRequest con los datos ingresados en la petición de login
            LoginRequest loginRequest = new LoginRequest(email, password);
            //Intenamos el login
            intentarLogin(loginRequest, v, 3);
        });

        return view;
    }

    /**
     * Ocultamos tanto en el onResume como en el onStop el actionbar
     */
    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }

    @Override
    public void onPause() {
        loginViewModel.setPassword(etPassword.getText().toString());
        loginViewModel.setUsername(etEmail.getText().toString());
        super.onPause();
    }

    /**
     * Metodo que intenta hacer el login con los datos ingresados en los campos de texto
     * Se reintenta en caso de problemas de conexión
     *
     * @param loginRequest
     * @param view
     * @param intentosRestantes
     */

    private void intentarLogin(LoginRequest loginRequest, View view, int intentosRestantes) {
        /**
         *  Obtenemos la instancia del servicio API y implementammos la interfaz con las distintas peticiones
         *  ahora tenemos un objeto que nos permite hacer llamadas a la api mediante Retrofit que se encarga de construir las peticiones HTTP
         */
        ApiAuth apiAuth = ApiClient.getClient().create(ApiAuth.class);
        /**
         * Hacemos la llamada al login
         * Call es una representación de la petición HTTP que al ejecutarse devolvera un objeto en este caso un LoginResponse (la respuesta de la llamada al Json
         * Se llama al metood login de la interfaz ApiAuth y se le pasa la petición de login como parametro esto configura una peticion POST a la URL en este caso
         */
        Call<LoginResponse> call = apiAuth.login(loginRequest);

        /**
         * Ejecutamos la llamada asincrona (en segundo plano) para no bloquear el hilo principal.
         * Utilizamos la interfaz Callback para manejar la respuesta de la llamada.
         */
        call.enqueue(new Callback<LoginResponse>() {
            /**
             * Se ejecuta cuando la llamada HTTP es exitosa. en caso contrario da un mensaje de error
             * @param call
             * @param response
             */
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String token = response.body().getToken();

                    // Guardar token
                    preferences.edit().putString("token", token).apply();

                    // Guardar email/contraseña si está marcado
                    if (cbRememberMe.isChecked()) {
                        preferences.edit().putString("email", loginRequest.getEmail()).apply();
                        preferences.edit().putString("password", loginRequest.getContrasena()).apply();
                    } else {
                        preferences.edit().remove("email").apply();
                        preferences.edit().remove("password").apply();
                    }

                    // Ir al home
                    NavController navController = Navigation.findNavController(view);
                    navController.navigate(R.id.nav_home);

                } else {
                    Toast.makeText(getContext(), "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
            }

            /**
             * Se ejecuta cuando la llamada HTTP falla, se intenta el login por si hubiera mala conexion.
             * @param call
             * @param t
             */
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.i("LoginFragment", "Error al conectar (intentos restantes: " + intentosRestantes + "): " + t.getMessage());

                if (intentosRestantes > 0) {
                    // Reintentar tras 2 segundos
                    new android.os.Handler().postDelayed(() -> {
                        intentarLogin(loginRequest, view, intentosRestantes - 1);
                    }, 2000);
                } else {
                    Toast.makeText(getContext(), "No se pudo conectar. Revisa la red o contacta con el administrador.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}