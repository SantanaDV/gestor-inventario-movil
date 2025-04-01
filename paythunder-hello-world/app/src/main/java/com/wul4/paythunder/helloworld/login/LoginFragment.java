package com.wul4.paythunder.helloworld.login;



import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.wul4.paythunder.helloworld.R;


public class LoginFragment extends Fragment {

   private TextInputEditText etEmail, etPassword;
   private MaterialButton btnSignIn;



    public LoginFragment() {
        // Constructor vacío requerido
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflamos el layout
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);
        btnSignIn = view.findViewById(R.id.btnSignIn);
if (getActivity() != null){
    //getActivity().getSupportActionBar().setTitle("Login");
}
        btnSignIn.setOnClickListener(v ->{
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                Toast.makeText(getContext(), "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            }


            //IMPLEMENTAR LÓGICA DE LLAMADA A API DE LOGIN
            //GUARDAR EL TOKEN PARA LAS DISTINTAS PETICIONES
            //GUARDAR EL TOKEN EN EL SHARED PREFERENCES.
            //y vamos a la pantalla de home

            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.nav_home);


        });

        return view;
    }



}