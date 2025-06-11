package com.wul4.paythunder.gestorInventario.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
    private AppBarConfiguration appBarConfig;
    private SharedPreferences prefs;
    private SharedPreferences.Editor prefsEditor;
    private boolean mostrarMenu = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1) Binding + layout
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 2) Preferences
        prefs = getSharedPreferences(Constantes.PREFERENCES_NAME, MODE_PRIVATE);
        prefsEditor = prefs.edit();

        // 3) No abrir teclado al inicio
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        // 4) Toolbar
        Toolbar toolbar = binding.appBarMain.toolbar;
        setSupportActionBar(toolbar);

        // 5) FAB (sigue habilitado sólo en login)
        binding.appBarMain.fab.setOnClickListener(v ->
                startActivity(new Intent(this, RegisterActivity.class))
        );

        // 6) NavController + Drawer
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navView = binding.navView;
        NavController nav = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);

        // Destinos top-level incluyen tu fragment_home, nav_almacen, nav_productos, tarea_fragment
        appBarConfig = new AppBarConfiguration.Builder(
                R.id.nav_home,
                R.id.nav_almacen,
                R.id.nav_productos,
                R.id.tarea_fragment
        )
                .setOpenableLayout(drawer)
                .build();

        NavigationUI.setupActionBarWithNavController(this, nav, appBarConfig);
        NavigationUI.setupWithNavController(navView, nav);

        nav.addOnDestinationChangedListener((controller, destination, args) -> {
            boolean isLogin       = destination.getId() == R.id.loginFragment;
            boolean isManageUsers = destination.getId() == R.id.nav_manage_users;
            mostrarMenu = !isLogin && !isManageUsers;
            invalidateOptionsMenu();

            // Bloquea/oculta drawer en login o en manage_users
            boolean lock = isLogin || isManageUsers;
            drawer.setDrawerLockMode(
                    lock ? DrawerLayout.LOCK_MODE_LOCKED_CLOSED
                            : DrawerLayout.LOCK_MODE_UNLOCKED
            );
            navView.setVisibility(lock ? View.GONE : View.VISIBLE);

            // FAB sólo en login
            binding.appBarMain.fab.setVisibility(isLogin ? View.VISIBLE : View.GONE);
        });


        // 8) Logout lateral (si lo tuvieras) o puedes eliminar este listener
        navView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.action_logout) {
                doLogout(nav);
                return true;
            }
            boolean handled = NavigationUI.onNavDestinationSelected(item, nav);
            drawer.closeDrawers();
            return handled;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mostrarMenu) return false;
        getMenuInflater().inflate(R.menu.main, menu);

        // Mostrar “Gestionar usuarios” sólo a admin
        String rol = prefs.getString("rol", "");
        boolean isAdmin = "admin".equalsIgnoreCase(rol);
        MenuItem mu = menu.findItem(R.id.nav_manage_users);
        if (mu != null) mu.setVisible(isAdmin);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        NavController nav = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            doLogout(nav);
            return true;
        } else if (id == R.id.nav_manage_users) {
            nav.navigate(R.id.nav_manage_users);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void doLogout(NavController nav) {
        prefsEditor.remove("token")
                .remove("rol")
                .apply();
        nav.navigate(R.id.loginFragment);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController nav = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(nav, appBarConfig) || super.onSupportNavigateUp();
    }
}
