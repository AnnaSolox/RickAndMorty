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

import com.example.rickandmorty.adaptersRecyclerView.PersonajesAdapter;
import com.example.rickandmorty.databinding.RecyclerviewFragmentsBinding;
import com.example.rickandmorty.utils.RecyclerViewPersonajes;
import com.example.rickandmorty.viewmodels.PersonajeViewModel;

public class PersonajesFragment extends Fragment {
    private RecyclerviewFragmentsBinding binding;
    private PersonajeViewModel personajeViewModel;
    private PersonajesAdapter personajesAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = RecyclerviewFragmentsBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        personajeViewModel = new ViewModelProvider(requireActivity()).get(PersonajeViewModel.class);
        personajesAdapter = new PersonajesAdapter(personajeViewModel, NavHostFragment.findNavController(this));
        RecyclerViewPersonajes recyclerViewPersonajes = new RecyclerViewPersonajes(binding.itemRecycler, personajeViewModel, personajesAdapter);
        recyclerViewPersonajes.setupRecyclerView(getContext());
        recyclerViewPersonajes.observarPersonajes(getViewLifecycleOwner());
        recyclerViewPersonajes.configurarBusqueda(binding.searchBar);
        binding.tituloRecycler.setText(R.string.personajesTitleFragment);
        personajeViewModel.cargarPerrsonajes();

    }

}