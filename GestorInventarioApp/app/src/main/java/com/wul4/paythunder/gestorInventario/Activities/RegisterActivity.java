package com.wul4.paythunder.gestorInventario.Activities;

import static java.security.AccessController.getContext;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;


import com.google.android.material.textfield.TextInputEditText;
import com.wul4.paythunder.gestorInventario.R;
import com.wul4.paythunder.gestorInventario.Utils.ApiClient;
import com.wul4.paythunder.gestorInventario.Utils.Funciones;
import com.wul4.paythunder.gestorInventario.Utils.interfaces.ApiAuth;
import com.wul4.paythunder.gestorInventario.entities.Usuario;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText etEmail, etPassword, etConfirmPassword, etFullName;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Cogemos las referencias del layout
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        etFullName = findViewById(R.id.etFullName);
        btnRegister = findViewById(R.id.btnRegister);

        //Creamos el objeto de ApiAuth
        ApiAuth apiAuth = ApiClient.getClient().create(ApiAuth.class);



        //Comprobamos las distintas restricciones de contrasena
        btnRegister.setOnClickListener(v -> {

            if(Funciones.comprobarFormatoCorreo(etEmail.getText().toString())){
            if (Funciones.comprobarCadenasIguales(etPassword.getText().toString(), etConfirmPassword.getText().toString())) {
                if (Funciones.stringMayorASeisCaracteres(etPassword.getText().toString())) {
                    if (Funciones.stringNumerosYLetras(etPassword.getText().toString())) {
                        //En caso de que todo este bien procedemos a registrar al usuario
                        //Creamos el objeto de Usuario
                        Usuario usuario = new Usuario();
                        usuario.setEmail(etEmail.getText().toString());
                        usuario.setContrasena(etPassword.getText().toString());
                        usuario.setNombre(etFullName.getText().toString());

                        //Hacemos la llamada al registro
                        Call<Usuario> call = apiAuth.register(usuario);

                        call.enqueue(new Callback<Usuario>() {
                            @Override
                            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                                if(response.isSuccessful() && response.body() != null){
                                    Toast.makeText(getApplicationContext(), "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }

                            @Override
                            public void onFailure(Call<Usuario> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "El usuario ya existe en la base de datos", Toast.LENGTH_SHORT).show();
                            }
                        });



                        Toast.makeText(getApplicationContext(), "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "La contraseña debe contener solo letras y numeros", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "La contraseña debe tener mas de 6 caracteres", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            }

            }else{
                Toast.makeText(getApplicationContext(), "El email no es valido", Toast.LENGTH_SHORT).show();
            }
        });
    }
}