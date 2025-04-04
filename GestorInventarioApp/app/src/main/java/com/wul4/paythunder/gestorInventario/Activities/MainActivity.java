package com.wul4.paythunder.gestorInventario.Activities;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import com.wul4.paythunder.gestorInventario.R;
import com.wul4.paythunder.gestorInventario.Utils.Constantes;
import com.wul4.paythunder.gestorInventario.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    public final String TAG = MainActivity.class.getSimpleName();
    public SharedPreferences preferences = null;
    public SharedPreferences.Editor prefEditor = null;

    //Atributos nuevos:

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //Ocultamos el teclado para que al abrir el login no se vea tan feo
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
           startActivity(intent);

            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Definir los destinos de nivel superior
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);

        //Hacemos invisible el boton de registrarse entodas las vistas menos en el LoginFragment
        navController.addOnDestinationChangedListener((controller,destination,arguments)->
        {
            if(destination.getId()==R.id.loginFragment){
                binding.appBarMain.fab.setVisibility(View.VISIBLE);
            }else{
                binding.appBarMain.fab.setVisibility(View.GONE);
            }
        });
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);



        //Configuramos el listener para la opción de cerrar sesión del menu lateral
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            int id = menuItem.getItemId();

            if (id == R.id.action_logout) {
                cerrarSesion();
                //obtenemos el layout principal y cerramos la ventana
                binding.drawerLayout.closeDrawers();
                return true;
            } else {
                //Navegamos al fragment correspondiente
                NavigationUI.onNavDestinationSelected(menuItem, navController);
                binding.drawerLayout.closeDrawers();
                return false;
            }
        });


        preferences = getSharedPreferences(Constantes.PREFERENCES_NAME, MODE_PRIVATE);
        prefEditor = preferences.edit();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            //Añadir la actividad de settings
            // startActivity(new Intent(this, SettingsActivity.class));
            return true;
        } else if (id == R.id.action_logout) {
            cerrarSesion();
            return true;

        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Metodo para cerrar la sesión. Borra el token del sharedpreferences y navega al LoginFragment
     */
    private void cerrarSesion() {
        // Borrar token del sharedpreferences
        SharedPreferences prefs = getSharedPreferences(Constantes.PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove("token");
        editor.apply();

        // Navegar al LoginFragment
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        navController.navigate(R.id.loginFragment);
    }


}