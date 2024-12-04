package com.example.rickandmorty.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.rickandmorty.R;
import com.example.rickandmorty.databinding.ActivitySecondBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * En esta actividad se habilita la navegación a través de los fragmentos {@code episodiosFragment},
 * {@code personajesFragment} y {@code localizacionesFragment} mediante un {@link NavController}.
 * También se configura el comportamiento de la barra de herramientas para manejar el menú de opciones
 * y la visibilidad de los fragmentos en el {@code BottomNavigationView}.
 */

public class SecondActivity extends AppCompatActivity {
    private ActivitySecondBinding binding;
    private NavController navController;

    /**
     * Este método configura el {@link NavController}, la barra de herramientas, y el
     * {@code BottomNavigationView}. También establece un listener para aplicar los márgenes
     * de las barras del sistema y manejar la visibilidad de la barra de navegación según el
     * fragmento actual.
     *
     * @param savedInstanceState El estado guardado de la actividad, si está disponible.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySecondBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        navController = ((NavHostFragment) Objects.requireNonNull(
                getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment)))
                .getNavController();

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.episodiosFragment, R.id.personajesFragment, R.id.localizacionesFragment
        ).build();

        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);
        if (binding.bottomNav != null) {
            NavigationUI.setupWithNavController(binding.bottomNav, navController);
            navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
                // Lista de fragmentos donde se muestra el BottomNavigationView
                List<Integer> fragmentsConBottomNav = Arrays.asList(
                        R.id.episodiosFragment,
                        R.id.personajesFragment,
                        R.id.localizacionesFragment
                );

                if (fragmentsConBottomNav.contains(destination.getId())) {
                    binding.bottomNav.setVisibility(View.VISIBLE);
                } else {
                    binding.bottomNav.setVisibility(View.GONE);
                }
            });
        } else if (binding.navigationRail != null) {
            NavigationUI.setupWithNavController(binding.navigationRail, navController);
            navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
                // Lista de fragmentos donde se muestra el NavigationRailView
                List<Integer> fragmentsConBottomNav = Arrays.asList(
                        R.id.episodiosFragment,
                        R.id.personajesFragment,
                        R.id.localizacionesFragment
                );

                if (fragmentsConBottomNav.contains(destination.getId())) {
                    binding.navigationRail.setVisibility(View.VISIBLE);
                } else {
                    binding.navigationRail.setVisibility(View.GONE);
                }
            });
        }
    }

    /**
     * Infla el menú de opciones para esta actividad.
     * <p>
     * Este método carga el archivo de menú {@code menu_toolbar} para mostrar las opciones
     * en la barra de herramientas.
     * </p>
     *
     * @param menu El objeto {@link Menu} al que se añadirá el contenido del menú.
     * @return {@code true} si el menú se ha creado correctamente.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    /**
     * Maneja la selección de un ítem del menú de opciones.
     * <p>
     * Este método maneja la interacción con los ítems del menú usando la navegación de destinos
     * o el comportamiento predeterminado de la acción.
     * </p>
     *
     * @param item El ítem seleccionado en el menú.
     * @return {@code true} si el ítem se ha manejado correctamente, {@code false} de lo contrario.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return NavigationUI.onNavDestinationSelected(item, navController) || super.onOptionsItemSelected(item);
    }


}