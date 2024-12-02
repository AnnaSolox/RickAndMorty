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
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.rickandmorty.adaptersRecyclerView.PersonajesAdapter;
import com.example.rickandmorty.databinding.BottomNavViewBinding;
import com.example.rickandmorty.databinding.FragmentLocalizacionEpisodioBinding;
import com.example.rickandmorty.utils.BottomNavUtil;
import com.example.rickandmorty.utils.RecyclerViewPersonajes;
import com.example.rickandmorty.viewmodels.EpisodioViewModel;
import com.example.rickandmorty.viewmodels.PersonajeViewModel;

public class MostrarEpisodioFragment extends Fragment {
    private FragmentLocalizacionEpisodioBinding binding;
    private PersonajeViewModel personajeViewModel;
    private PersonajesAdapter personajesAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return (binding = FragmentLocalizacionEpisodioBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BottomNavUtil.ocultarBottomNavigationView(getActivity(), R.id.bottomNav);

        EpisodioViewModel episodioViewModel = new ViewModelProvider(requireActivity()).get(EpisodioViewModel.class);
        personajeViewModel = new ViewModelProvider(requireActivity()).get(PersonajeViewModel.class);
        personajesAdapter = new PersonajesAdapter(personajeViewModel, NavHostFragment.findNavController(this));
        RecyclerViewPersonajes recyclerViewPersonajes = new RecyclerViewPersonajes(binding.itemRecyclerFragment.itemRecycler, personajeViewModel, personajesAdapter);
        recyclerViewPersonajes.setupRecyclerView(getContext());
        recyclerViewPersonajes.observarPersonajes(getViewLifecycleOwner());
        recyclerViewPersonajes.configurarBusqueda(binding.itemRecyclerFragment.searchBar);

        episodioViewModel.seleccionado().observe(getViewLifecycleOwner(), episodio -> {
            if(episodio != null){
                binding.titulo.setText(episodio.getNombre());
                binding.info1.setText(episodio.getIdentificador());
                binding.info2.setText(episodio.getFechaLanzamiento().toString());
                binding.itemRecyclerFragment.tituloRecycler.setText("Personajes");
                personajeViewModel.cargarPersonajesPorIds(episodio.getPersonajes());
            } else {
                Log.e("Fragment", "El episodio seleccionado es nulo");
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        BottomNavUtil.mostrarBottomNavigationView(getActivity(), R.id.bottomNav);
    }
}