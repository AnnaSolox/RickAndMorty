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
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.rickandmorty.adaptersRecyclerView.PersonajesAdapter;
import com.example.rickandmorty.databinding.FragmentPersonajesBinding;
import com.example.rickandmorty.viewmodels.PersonajeViewModel;

public class PersonajesFragment extends Fragment {
    private FragmentPersonajesBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return (binding = FragmentPersonajesBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        PersonajesAdapter personajesAdapter = new PersonajesAdapter();
        binding.recyclerView.setAdapter(personajesAdapter);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        PersonajeViewModel personajeViewModel = new ViewModelProvider(this).get(PersonajeViewModel.class);
        personajeViewModel.cargarPerrsonajes();
        personajeViewModel.getPersonajeLiveData().observe(getViewLifecycleOwner(), personajes -> {
            Log.d("Personajes", "Tamaño de la lista de personajes: " + personajes.size());
            personajesAdapter.establecerLista(personajes);
        });
    }
}