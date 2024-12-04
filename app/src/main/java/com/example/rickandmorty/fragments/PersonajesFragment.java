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
 * Fragmento encargado de mostrar la lista de personajes utilizando un {@link RecyclerViewPersonajes}.
 * <p>
 * Este fragmento utiliza el patrón MVVM para observar los datos de personajes desde el ViewModel.
 * Incluye funcionalidades de búsqueda y navegación al detalle de un personaje.
 * </p>
 */
public class PersonajesFragment extends Fragment {
    private RecyclerviewFragmentsBinding binding;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = RecyclerviewFragmentsBinding.inflate(inflater, container, false)).getRoot();
    }

    /**
     * Configura el {@link RecyclerViewPersonajes}, observa los datos de personajes y gestiona la navegación
     * hacia el detalle de un personaje seleccionado.
     *
     * @param view La vista raíz del fragmento.
     * @param savedInstanceState El estado guardado del fragmento, si existe.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        PersonajeViewModel personajeViewModel = new ViewModelProvider(requireActivity()).get(PersonajeViewModel.class);
        PersonajesAdapter personajesAdapter = new PersonajesAdapter(personajeViewModel, NavHostFragment.findNavController(this), R.id.action_personajesFragment_to_mostrarPersonajeFragment);
        RecyclerViewPersonajes recyclerViewPersonajes = new RecyclerViewPersonajes(binding.itemRecycler, personajeViewModel, personajesAdapter);
        recyclerViewPersonajes.setupRecyclerView(getContext(),3);
        recyclerViewPersonajes.observarPersonajes(getViewLifecycleOwner());
        recyclerViewPersonajes.configurarBusqueda(binding.searchBar);
        binding.tituloRecycler.setText(R.string.titulo_recycler_personajes);
        personajeViewModel.cargarPersonajes();
    }

}