package com.wul4.paythunder.gestorInventario.login;




import android.app.ActionBar;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.wul4.paythunder.gestorInventario.R;
import com.wul4.paythunder.gestorInventario.Utils.ApiClient;
import com.wul4.paythunder.gestorInventario.Utils.ApiService;
import com.wul4.paythunder.gestorInventario.request.LoginRequest;
import com.wul4.paythunder.gestorInventario.response.LoginResponse;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginFragment extends Fragment {

    private TextInputEditText etEmail, etPassword;
    private MaterialButton btnSignIn;
    Log log;


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

            // Creamos una instancia de LoginRequest con los datos ingresados en la petición de login
            LoginRequest loginRequest = new LoginRequest(email, password);



            /**
             *  Obtenemos la instancia del servicio API y implementammos la interfaz con las distintas peticiones
             *  ahora tenemos un objeto que nos permite hacer llamadas a la api mediante Retrofit que se encarga de construir las peticiones HTTP
             */
            ApiService apiService = ApiClient.getClient().create(ApiService.class);

            /**
             * Hacemos la llamada al login
             * Call es una representación de la petición HTTP que al ejecutarse devolvera un objeto en este caso un LoginResponse (la respuesta de la llamada al Json
             * Se llama al metood login de la interfaz ApiService y se le pasa la petición de login como parametro esto configura una peticion POST a la URL en este caso
             */
            Call<LoginResponse> call = apiService.login(loginRequest);

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
                        //Se tiene una respuesta exitosa
                        String token = response.body().getToken();

                        //Lo guardamos en el shared preferences
                        SharedPreferences preferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                        preferences.edit().putString("token", token).apply();


                        //Navegamos al fragmento de home
                        NavController navController = Navigation.findNavController(v);
                        navController.navigate(R.id.nav_home);

                    } else {
                        Toast.makeText(getContext(), "usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                    }
                }

                /**
                 * Se ejecuta cuando la llamada HTTP falla.
                 * @param call
                 * @param t
                 */
                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    // Fallo en la llamada (problema de red, etc.)
                    Toast.makeText(getContext(), "Ha habido un error al iniciar sesión, pongase en contacto con un administrador", Toast.LENGTH_SHORT).show();
                    Log.i("LoginFragment", "Error: " + t.getMessage());

                }
            });


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
}