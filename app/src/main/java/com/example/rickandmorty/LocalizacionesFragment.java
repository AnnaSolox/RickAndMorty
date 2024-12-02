package com.example.rickandmorty;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.rickandmorty.adaptersRecyclerView.LocalizacionesAdapter;
import com.example.rickandmorty.databinding.RecyclerviewFragmentsBinding;
import com.example.rickandmorty.utils.RecyclerViewLocalizaciones;
import com.example.rickandmorty.viewmodels.LocalizacionViewModel;

public class LocalizacionesFragment extends Fragment {
    private RecyclerviewFragmentsBinding binding;
    private LocalizacionesAdapter localizacionesAdapter;
    private LocalizacionViewModel localizacionViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return (binding = RecyclerviewFragmentsBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        localizacionViewModel = new ViewModelProvider(requireActivity()).get(LocalizacionViewModel.class);
        localizacionesAdapter = new LocalizacionesAdapter(localizacionViewModel, NavHostFragment.findNavController(this));

        RecyclerViewLocalizaciones recyclerViewLocalizaciones = new RecyclerViewLocalizaciones(binding.recyclerView,localizacionViewModel, localizacionesAdapter);
        recyclerViewLocalizaciones.setupRecyclerView(getContext());
        recyclerViewLocalizaciones.observarPersonajes(getViewLifecycleOwner());
        recyclerViewLocalizaciones.configurarBusqueda(binding.searchBar);
        binding.tituloRecycler.setText(R.string.localizacionesTitleFragment);
        localizacionViewModel.cargarLocalizaciones();
        localizacionViewModel.obtener().observe(getViewLifecycleOwner(), localizacionesAdapter::establecerLista);
    }
}