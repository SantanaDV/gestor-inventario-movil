package com.wul4.paythunder.gestorInventario.Activities;


import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;


import com.google.android.material.textfield.TextInputEditText;
import com.wul4.paythunder.gestorInventario.R;
import com.wul4.paythunder.gestorInventario.Utils.ApiClient;

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
        //Utilizamos el metodo de Utils para poner la pantalla completa
        com.wul4.paythunder.gestorInventario.utils.Utils.setFullscreen(this);

        //Cogemos las referencias del layout
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        etFullName = findViewById(R.id.etFullName);
        btnRegister = findViewById(R.id.btnRegister);

        //Ocultamos el teclado para que al abrir el login no se vea tan feo
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        //Ocultamos tambien el menu de settings
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }



        //Comprobamos las distintas restricciones de contrasena
        btnRegister.setOnClickListener(v -> {
            if (!(com.wul4.paythunder.gestorInventario.utils.Funciones.editTextEmpty(etEmail) || com.wul4.paythunder.gestorInventario.utils.Funciones.editTextEmpty(etPassword) || com.wul4.paythunder.gestorInventario.utils.Funciones.editTextEmpty(etConfirmPassword) || Funciones.editTextEmpty(etFullName))) {
                if (com.wul4.paythunder.gestorInventario.utils.Funciones.comprobarFormatoCorreo(etEmail.getText().toString())) {
                    if (com.wul4.paythunder.gestorInventario.utils.Funciones.comprobarCadenasIguales(etPassword.getText().toString(), etConfirmPassword.getText().toString())) {
                        if (com.wul4.paythunder.gestorInventario.utils.Funciones.stringMayorASeisCaracteres(etPassword.getText().toString())) {
                            if (com.wul4.paythunder.gestorInventario.utils.Funciones.stringNumerosYLetras(etPassword.getText().toString())) {

                                //En caso de que todo este bien procedemos a registrar al usuario
                                //Creamos el objeto de Usuario
                                com.wul4.paythunder.gestorInventario.utils.interfaces.ApiAuth apiAuth = ApiClient.getClient().create(com.wul4.paythunder.gestorInventario.utils.interfaces.ApiAuth.class);
                                Usuario usuario = new Usuario();
                                usuario.setEmail(etEmail.getText().toString());
                                usuario.setContrasena(etPassword.getText().toString());
                                usuario.setNombre(etFullName.getText().toString());
                                //Comprobamos que el email no existe ya en la base de datos con una llamada a la api
                                Call<Boolean> callBoolean = apiAuth.existeEmail(usuario.getEmail());

                                callBoolean.enqueue(new Callback<Boolean>() {
                                    @Override
                                    public void onResponse(Call<Boolean> callExiste, Response<Boolean> response) {
                                        if (response.isSuccessful()) {
                                            Boolean existe = response.body();
                                            if (existe) {
                                                Toast.makeText(getApplicationContext(), "El email ya existe", Toast.LENGTH_SHORT).show();
                                            } else {
                                                //Hacemos la llamada al registro
                                                Call<Usuario> call = apiAuth.register(usuario);

                                                call.enqueue(new Callback<Usuario>() {
                                                    @Override
                                                    public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                                                        if (response.isSuccessful() && response.body() != null) {
                                                            Toast.makeText(getApplicationContext(), "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                                                            finish();
                                                        }
                                                    }

                                                    @Override
                                                    public void onFailure(Call<Usuario> call, Throwable t) {
                                                        Toast.makeText(getApplicationContext(), "Ha habido un problema, pongase en contacto con un administrador", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Boolean> callExiste, Throwable t) {
                                        Toast.makeText(getApplicationContext(), "Ha habido un problema, pongase en contacto con un administrador", Toast.LENGTH_SHORT).show();
                                    }
                                });


                            } else {
                                Toast.makeText(getApplicationContext(), "La contraseña debe contener solo letras y numeros", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "La contraseña debe tener mas de 6 caracteres", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "El email no es valido", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            }


        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.setFullscreen(this);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus){
        super.onWindowFocusChanged(hasFocus);
        Utils.setFullscreen(this);
    }
}