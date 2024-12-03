package com.example.rickandmorty.fragments;

import android.os.Bundle;
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
import com.example.rickandmorty.databinding.RecyclerviewFragmentsBinding;
import com.example.rickandmorty.utils.RecyclerViewPersonajes;
import com.example.rickandmorty.viewmodels.PersonajeViewModel;

/**
 * Fragmento que muestra la lista de personajes favoritos en un {@link RecyclerViewPersonajes}.
 * <p>
 * Este fragmento configura y muestra un listado de personajes favoritos utilizando un adaptador personalizado
 * {@link PersonajesAdapter}. Permite buscar personajes favoritos y observa los datos proporcionados por
 * el {@link PersonajeViewModel}.
 * </p>
 */
public class FavoritosFragment extends Fragment {
    private RecyclerviewFragmentsBinding binding;
    private PersonajesAdapter personajesAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return (binding = RecyclerviewFragmentsBinding.inflate(inflater, container, false)).getRoot();
    }

    /**
     * Se llama después de que la vista del fragmento ha sido creada. Configura el {@link RecyclerViewPersonajes},
     * inicializa el adaptador y observa los datos de favoritos proporcionados por el ViewModel.
     *
     * @param view La vista raíz del fragmento.
     * @param savedInstanceState El estado guardado del fragmento, si existe.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        PersonajeViewModel personajeViewModel = new ViewModelProvider(requireActivity()).get(PersonajeViewModel.class);
        personajesAdapter = new PersonajesAdapter(personajeViewModel, NavHostFragment.findNavController(this), R.id.action_favoritosFragment_to_mostrarPersonajeFragment);

        RecyclerViewPersonajes recyclerViewPersonajes = new RecyclerViewPersonajes(binding.itemRecycler, personajeViewModel, personajesAdapter);
        recyclerViewPersonajes.setupRecyclerView(getContext());
        recyclerViewPersonajes.observarFavoritos(getViewLifecycleOwner());
        recyclerViewPersonajes.configurarBusqueda(binding.searchBar);
        binding.tituloRecycler.setText(R.string.titulo_recycler_favoritos);

        personajeViewModel.getFavoritosLiveData().observe(getViewLifecycleOwner(), personajes -> personajesAdapter.establecerLista(personajes));
    }
}