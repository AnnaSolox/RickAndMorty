package com.example.rickandmorty;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.rickandmorty.adaptersRecyclerView.LocalizacionAdapter;
import com.example.rickandmorty.databinding.RecyclerviewFragmentsBinding;
import com.example.rickandmorty.utils.RecyclerViewEpisodios;
import com.example.rickandmorty.viewmodels.PersonajeViewModel;

public class PersonajesFragment extends Fragment {
    private RecyclerviewFragmentsBinding binding;
    private PersonajeViewModel personajeViewModel;
    private LocalizacionAdapter personajesAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = RecyclerviewFragmentsBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        personajeViewModel = new ViewModelProvider(requireActivity()).get(PersonajeViewModel.class);
        personajesAdapter = new LocalizacionAdapter();
        RecyclerViewEpisodios recyclerViewPersonajes = new RecyclerViewEpisodios(binding.recyclerView, personajeViewModel, personajesAdapter);
        recyclerViewPersonajes.setupRecyclerView(getContext());
        recyclerViewPersonajes.observarPersonajes(getViewLifecycleOwner());
        recyclerViewPersonajes.configurarBusqueda(binding.searchBar);
        binding.tituloRecycler.setText(R.string.personajesTitleFragment);
        personajeViewModel.cargarPerrsonajes();

    }

}