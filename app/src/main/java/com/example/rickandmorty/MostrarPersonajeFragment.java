package com.example.rickandmorty;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.rickandmorty.adaptersRecyclerView.EpisodiosAdapter;
import com.example.rickandmorty.databinding.FragmentMostrarPersonajeBinding;
import com.example.rickandmorty.utils.BottomNavUtil;
import com.example.rickandmorty.utils.RecyclerViewEpisodios;
import com.example.rickandmorty.viewmodels.EpisodioViewModel;
import com.example.rickandmorty.viewmodels.PersonajeViewModel;

import java.util.Objects;

public class MostrarPersonajeFragment extends Fragment {
    private FragmentMostrarPersonajeBinding binding;
    private EpisodioViewModel episodioViewModel;
    private EpisodiosAdapter episodiosAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return (binding = FragmentMostrarPersonajeBinding.inflate(inflater,container,false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        PersonajeViewModel personajeViewModel = new ViewModelProvider(requireActivity()).get(PersonajeViewModel.class);
        episodioViewModel = new ViewModelProvider(requireActivity()).get(EpisodioViewModel.class);
        episodiosAdapter = new EpisodiosAdapter(episodioViewModel, NavHostFragment.findNavController(this), R.id.action_mostrarPersonajeFragment_to_mostrarEpisodioFragment);

        RecyclerViewEpisodios recyclerViewEpisodios = new RecyclerViewEpisodios(binding.itemRecyclerFragment.itemRecycler, episodioViewModel, episodiosAdapter);
        recyclerViewEpisodios.setupRecyclerView(getContext());
        recyclerViewEpisodios.observarEpisodios(getViewLifecycleOwner());
        recyclerViewEpisodios.configurarBusqueda(binding.itemRecyclerFragment.searchBar);

        personajeViewModel.seleccionado().observe(getViewLifecycleOwner(), personaje -> {
            if(personaje != null){
                binding.titulo.setText(personaje.getNombre());
                binding.valorEstado.setText(personaje.getEstado());
                binding.valorEspecie.setText(personaje.getEspecie());
                binding.valorTipo.setText(!Objects.equals(personaje.getTipo(), "") ? personaje.getTipo() : "unknown");
                binding.valorGenero.setText(personaje.getGenero());
                binding.valorOrigen.setText(personaje.getOrigen().getNombre());
                binding.valorLocalizacion.setText(personaje.getLocalizacion().getNombre());
                Glide.with(binding.imagePjItem.getContext()).load(personaje.getImagen()).into(binding.imagePjItem);
                binding.itemRecyclerFragment.tituloRecycler.setText("Episodios");
                episodioViewModel.cargarEpisodiosPorId(personaje.getEpisodios());
            }
        });

        episodioViewModel.getEpisodiosLiveData().observe(getViewLifecycleOwner(), episodios -> {
            Log.d("PersonajesLocalizacion", "Tamaño de la lista de personajes: " + episodios.size());
            if (episodios != null && !episodios.isEmpty()) {
                episodiosAdapter.establecerLista(episodios);
            } else {
                Log.e("Personajes", "La lista de personajes está vacía.");
            }
        });
    }
}