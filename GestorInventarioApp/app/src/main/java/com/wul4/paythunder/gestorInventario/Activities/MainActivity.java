package com.wul4.paythunder.gestorInventario.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.wul4.paythunder.gestorInventario.R;
import com.wul4.paythunder.gestorInventario.databinding.ActivityMainBinding;
import com.wul4.paythunder.gestorInventario.utils.Constantes;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private AppBarConfiguration appBarConfiguration;
    private boolean mostrarMenu = true;
    private SharedPreferences preferences;
    private SharedPreferences.Editor prefEditor;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Evitar que el teclado aparezca al inicio si hay campos de texto
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        setSupportActionBar(binding.appBarMain.toolbar);

        // Fab: lanza RegisterActivity
        binding.appBarMain.fab.setOnClickListener(v -> {
            startActivity(new Intent(this, com.wul4.paythunder.gestorInventario.activities.RegisterActivity.class));
        });

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navView = binding.navView;
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);

        // IDs de nivel superior según tu nav-graph
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,
                R.id.nav_almacen,
                R.id.nav_productos,
                R.id.tarea_fragment
        )
                .setOpenableLayout(drawer)
                .build();

        // Gestiona visibilidad de fab y drawer en login
        navController.addOnDestinationChangedListener((controller, destination, args) -> {
            boolean isLogin = destination.getId() == R.id.loginFragment;
            mostrarMenu = !isLogin;
            invalidateOptionsMenu();

            binding.appBarMain.fab.setVisibility(isLogin
                    ? View.VISIBLE
                    : View.GONE
            );
            binding.navView.setVisibility(isLogin
                    ? View.GONE
                    : View.VISIBLE
            );

             // ocultar/mostrar drawer
            navView.setVisibility(isLogin ? View.GONE : View.VISIBLE);
        });

        // Conecta toolbar + drawer con navController
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        // Listener para los items del menú lateral
        navView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.action_logout) {
                cerrarSesion();
                drawer.closeDrawers();
                return true;
            }
            boolean handled = NavigationUI.onNavDestinationSelected(item, navController);
            drawer.closeDrawers();
            return handled;
        });

        // SharedPreferences para token
        preferences = getSharedPreferences(Constantes.PREFERENCES_NAME, MODE_PRIVATE);
        prefEditor = preferences.edit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mostrarMenu) {
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }
        return false;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            cerrarSesion();
            return true;
        }
        // Otros items del toolbar (settings, etc)
        return super.onOptionsItemSelected(item);
    }

    private void cerrarSesion() {
        // Borramos token
        prefEditor.remove("token");
        prefEditor.apply();
        // Navegamos al login
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        navController.navigate(R.id.loginFragment);
    }
}
