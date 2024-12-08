package com.example.rickandmorty.fragments;

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

import com.example.rickandmorty.R;
import com.example.rickandmorty.adaptersRecyclerView.PersonajesAdapter;
import com.example.rickandmorty.databinding.FragmentLocalizacionEpisodioBinding;
import com.example.rickandmorty.adaptersRecyclerView.RecyclerViewPersonajes;
import com.example.rickandmorty.viewmodels.EpisodioViewModel;
import com.example.rickandmorty.viewmodels.PersonajeViewModel;

/**
 * Fragmento encargado de mostrar los detalles de un episodio y los personajes asociados a él.
 * <p>
 * Este fragmento utiliza un {@link PersonajeViewModel} para observar el episodio seleccionado y cargar los personajes relacionados.
 * Además, permite realizar búsquedas entre los personajes utilizando un {@link RecyclerViewPersonajes} configurado dinámicamente.
 * </p>
 */
public class MostrarEpisodioFragment extends Fragment {
    private FragmentLocalizacionEpisodioBinding binding;
    private PersonajeViewModel personajeViewModel;
    private PersonajesAdapter personajesAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return (binding = FragmentLocalizacionEpisodioBinding.inflate(inflater, container, false)).getRoot();
    }

    /**
     * Configura el {@link RecyclerViewPersonajes}, observa los cambios en el episodio seleccionado y gestiona la
     * carga de personajes relacionados.
     *
     * @param view La vista raíz del fragmento.
     * @param savedInstanceState El estado guardado del fragmento, si existe.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EpisodioViewModel episodioViewModel = new ViewModelProvider(requireActivity()).get(EpisodioViewModel.class);
        personajeViewModel = new ViewModelProvider(requireActivity()).get(PersonajeViewModel.class);
        personajesAdapter = new PersonajesAdapter(personajeViewModel, NavHostFragment.findNavController(this), R.id.action_mostrarEpisodioFragment_to_mostrarPersonajeFragment);
        RecyclerViewPersonajes recyclerViewPersonajes = new RecyclerViewPersonajes(binding.itemRecyclerFragment.itemRecycler, personajeViewModel, personajesAdapter);
        recyclerViewPersonajes.setupRecyclerView(getContext(),2);
        recyclerViewPersonajes.observarPersonajes(getViewLifecycleOwner());
        recyclerViewPersonajes.configurarBusqueda(binding.itemRecyclerFragment.searchBar);

        episodioViewModel.seleccionado().observe(getViewLifecycleOwner(), episodio -> {
            if(episodio != null){
                binding.titulo.setText(episodio.getNombre());
                binding.descriptionInfo1.setText(R.string.descr_info1_episodio);
                binding.info1.setText(episodio.getIdentificador());
                binding.descriptionInfo2.setText(R.string.descr_info2_episodio);
                binding.info2.setText(episodio.getFechaLanzamiento().toString());
                binding.itemRecyclerFragment.tituloRecycler.setText(R.string.titulo_recycler_personajes);
                personajeViewModel.cargarPersonajesPorIds(episodio.getPersonajes());
            } else {
                Log.e("Fragment", "El episodio seleccionado es nulo");
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