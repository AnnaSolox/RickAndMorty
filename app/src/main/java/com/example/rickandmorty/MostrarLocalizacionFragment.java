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
import androidx.navigation.fragment.NavHostFragment;

import com.example.rickandmorty.adaptersRecyclerView.PersonajesAdapter;
import com.example.rickandmorty.databinding.FragmentLocalizacionEpisodioBinding;
import com.example.rickandmorty.utils.RecyclerViewPersonajes;
import com.example.rickandmorty.viewmodels.LocalizacionViewModel;
import com.example.rickandmorty.viewmodels.PersonajeViewModel;


public class MostrarLocalizacionFragment extends Fragment {
    private FragmentLocalizacionEpisodioBinding binding;
    private PersonajeViewModel personajeViewModel;
    private PersonajesAdapter personajesAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return (binding = FragmentLocalizacionEpisodioBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LocalizacionViewModel localizacionViewModel = new ViewModelProvider(requireActivity()).get(LocalizacionViewModel.class);
        personajeViewModel = new ViewModelProvider(requireActivity()).get(PersonajeViewModel.class);
        personajesAdapter = new PersonajesAdapter(personajeViewModel, NavHostFragment.findNavController(this), R.id.action_mostrarLocalizacionFragment_to_mostrarPersonajeFragment);
        RecyclerViewPersonajes recyclerViewPersonajes = new RecyclerViewPersonajes(binding.itemRecyclerFragment.itemRecycler, personajeViewModel, personajesAdapter);
        recyclerViewPersonajes.setupRecyclerView(getContext());
        recyclerViewPersonajes.observarPersonajes(getViewLifecycleOwner());
        recyclerViewPersonajes.configurarBusqueda(binding.itemRecyclerFragment.searchBar);

        localizacionViewModel.seleccionada().observe(getViewLifecycleOwner(), localizacion -> {
            if(localizacion != null){
                binding.titulo.setText(localizacion.getNombre());
                binding.descriptionInfo1.setText(R.string.descr_info1_localizacion);
                binding.info1.setText(localizacion.getDimension());
                binding.descriptionInfo2.setText(R.string.descr_info2_localizacion);
                binding.info2.setText(localizacion.getTipo());
                binding.itemRecyclerFragment.tituloRecycler.setText(R.string.titulo_recycler_residentes);
                personajeViewModel.cargarPersonajesPorIds(localizacion.getResidentes());
            } else {
                Log.e("Fragment", "La localización seleccionada es nula");
            }
        });

        personajeViewModel.getPersonajeLiveData().observe(getViewLifecycleOwner(), personajes -> {
            Log.d("PersonajesLocalizacion", "Tamaño de la lista de personajes: " + personajes.size());
            if (!personajes.isEmpty()) {
                personajesAdapter.establecerLista(personajes);
            } else {
                Log.e("Personajes", "La lista de personajes está vacía.");
            }
        });

    }
}