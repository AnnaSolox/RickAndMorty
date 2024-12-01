package com.example.rickandmorty.activities;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.rickandmorty.R;
import com.example.rickandmorty.databinding.ActivityMainBinding;
import com.example.rickandmorty.databinding.ActivitySecondBinding;
import com.example.rickandmorty.databinding.BottomNavViewBinding;

import java.util.Objects;

public class SecondActivity extends AppCompatActivity {
    private ActivitySecondBinding binding;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
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

        String fragmentoACargar = getIntent().getStringExtra("EXTRA_FRAGMENT");
        if ("episodios".equals(fragmentoACargar)) {
            navController.navigate(R.id.episodiosFragment);
            navController.popBackStack(R.id.episodiosFragment, true);
        } else if ("personajes".equals(fragmentoACargar)) {
            navController.popBackStack(R.id.episodiosFragment, true);
            navController.navigate(R.id.personajesFragment);
        } else if ("localizaciones".equals(fragmentoACargar)) {
            navController.popBackStack(R.id.episodiosFragment, true);
            navController.navigate(R.id.localizacionesFragment);
        }

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.episodiosFragment, R.id.personajesFragment, R.id.localizacionesFragment
        ).build();

        NavigationUI.setupWithNavController(binding.bottomNav.bottomNavView, navController);
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return NavigationUI.onNavDestinationSelected(item, navController) || super.onOptionsItemSelected(item);
    }


}