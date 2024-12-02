package com.example.rickandmorty;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.rickandmorty.adaptersRecyclerView.PersonajesAdapter;
import com.example.rickandmorty.databinding.FragmentMostrarLocalizacionBinding;
import com.example.rickandmorty.utils.RecyclerViewPersonajes;
import com.example.rickandmorty.viewmodels.LocalizacionViewModel;
import com.example.rickandmorty.viewmodels.PersonajeViewModel;


public class MostrarLocalizacionFragment extends Fragment {
    private FragmentMostrarLocalizacionBinding binding;
    private PersonajeViewModel personajeViewModel;
    private PersonajesAdapter personajesAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return (binding = FragmentMostrarLocalizacionBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LocalizacionViewModel localizacionViewModel = new ViewModelProvider(requireActivity()).get(LocalizacionViewModel.class);
        personajeViewModel = new ViewModelProvider(requireActivity()).get(PersonajeViewModel.class);
        personajesAdapter = new PersonajesAdapter();
        RecyclerViewPersonajes recyclerViewPersonajes = new RecyclerViewPersonajes(binding.itemRecyclerResidentes.recyclerView, personajeViewModel, personajesAdapter);
        recyclerViewPersonajes.setupRecyclerView(getContext());
        recyclerViewPersonajes.observarPersonajes(getViewLifecycleOwner());
        recyclerViewPersonajes.configurarBusqueda(binding.itemRecyclerResidentes.searchBar);

        localizacionViewModel.seleccionada().observe(getViewLifecycleOwner(), localizacion -> {
            if(localizacion != null){
                binding.tituloLocalizacion.setText(localizacion.getNombre());
                binding.textDimension.setText(localizacion.getDimension());
                binding.textTipo.setText(localizacion.getTipo());
                binding.itemRecyclerResidentes.tituloRecycler.setText("Residentes");
                personajeViewModel.cargarPersonajesPorIds(localizacion.getResidentes());
            } else {
                Log.e("Fragment", "La localización seleccionada es nula");
            }
        });

        personajeViewModel.getPersonajeLiveData().observe(getViewLifecycleOwner(), personajes -> {
            Log.d("PersonajesLocalizacion", "Tamaño de la lista de personajes: " + personajes.size());
            if (personajes != null && !personajes.isEmpty()) {
                personajesAdapter.establecerLista(personajes);
            } else {
                Log.e("Personajes", "La lista de personajes está vacía.");
            }
        });

    }
}