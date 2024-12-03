package com.example.rickandmorty.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rickandmorty.R;
import com.example.rickandmorty.databinding.ActivityMainBinding;

/**
 * Actividad principal de la aplicación que maneja la inicialización de la interfaz de usuario
 * y la configuración de la navegación a la segunda actividad.
 */
public class MainActivity extends AppCompatActivity {
    /**
     * Este método inflará el layout de la actividad, aplicará el padding necesario para
     * que el contenido se ajuste a las barras del sistema (como la barra de estado y de navegación),
     * y configurará el evento de clic en el botón {@code btnRickypedia} para iniciar la segunda actividad.
     *
     * @param savedInstanceState El estado guardado de la actividad, si está disponible.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.btnRickypedia.setOnClickListener(v -> {
            Intent intent = new Intent(this,SecondActivity.class);
            startActivity(intent);
        });
    }
}