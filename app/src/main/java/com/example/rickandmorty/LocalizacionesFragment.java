package com.example.rickandmorty;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rickandmorty.adaptersRecyclerView.LocalizacionesAdapter;
import com.example.rickandmorty.databinding.FragmentLocalizacionesBinding;
import com.example.rickandmorty.viewmodels.LocalizacionViewModel;

public class LocalizacionesFragment extends Fragment {
    private FragmentLocalizacionesBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return (binding = FragmentLocalizacionesBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LocalizacionesAdapter localizacionesAdapter = new LocalizacionesAdapter();

        binding.recyclerView.setAdapter(localizacionesAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        LocalizacionViewModel localizacionViewModel = new ViewModelProvider(this).get(LocalizacionViewModel.class);
        localizacionViewModel.cargarLocalizaciones();
        localizacionViewModel.getLocalizacionLiveData().observe(getViewLifecycleOwner(), localizacions -> {
            Log.d("Localizaciones", "Tamaño de la lista de localizaciones: " + localizacions.size());
            localizacionesAdapter.establecerLista(localizacions);
        });
    }
}