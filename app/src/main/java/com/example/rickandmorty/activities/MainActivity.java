package com.example.rickandmorty.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.rickandmorty.EpisodiosFragment;
import com.example.rickandmorty.LocalizacionesFragment;
import com.example.rickandmorty.PersonajesFragment;
import com.example.rickandmorty.R;
import com.example.rickandmorty.databinding.ActivityMainBinding;
import com.example.rickandmorty.utils.FavoritosJsonUtilidad;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
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

        binding.btnFavoritos.setOnClickListener(v -> {
            Intent intent = new Intent(this,SecondActivity.class);
            startActivity(intent);
        });
    }
}