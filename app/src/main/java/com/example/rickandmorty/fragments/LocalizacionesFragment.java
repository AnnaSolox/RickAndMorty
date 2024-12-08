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
import com.example.rickandmorty.adaptersRecyclerView.LocalizacionesAdapter;
import com.example.rickandmorty.databinding.RecyclerviewFragmentsBinding;
import com.example.rickandmorty.adaptersRecyclerView.RecyclerViewLocalizaciones;
import com.example.rickandmorty.viewmodels.LocalizacionViewModel;

/**
 * Fragmento que muestra la lista de localizaciones en un {@link RecyclerViewLocalizaciones}.
 * <p>
 * Este fragmento configura y muestra un listado de localizaciones utilizando un adaptador personalizado
 * {@link LocalizacionesAdapter}. También permite buscar localizaciones y observa los datos proporcionados por
 * el {@link LocalizacionViewModel}.
 * </p>
 */
public class LocalizacionesFragment extends Fragment {
    private RecyclerviewFragmentsBinding binding;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = RecyclerviewFragmentsBinding.inflate(inflater, container, false)).getRoot();
    }

    /**
     * Se llama después de que la vista del fragmento ha sido creada. Configura el {@link RecyclerViewLocalizaciones},
     * inicializa el adaptador y observa los datos de localizaciones proporcionados por el ViewModel.
     *
     * @param view La vista raíz del fragmento.
     * @param savedInstanceState El estado guardado del fragmento, si existe.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LocalizacionViewModel localizacionViewModel = new ViewModelProvider(requireActivity()).get(LocalizacionViewModel.class);
        LocalizacionesAdapter localizacionesAdapter = new LocalizacionesAdapter(localizacionViewModel, NavHostFragment.findNavController(this));

        RecyclerViewLocalizaciones recyclerViewLocalizaciones = new RecyclerViewLocalizaciones(binding.itemRecycler, localizacionViewModel, localizacionesAdapter);
        recyclerViewLocalizaciones.setupRecyclerView(getContext());
        recyclerViewLocalizaciones.observarLocalizaciones(getViewLifecycleOwner());
        recyclerViewLocalizaciones.configurarBusqueda(binding.searchBar);
        binding.tituloRecycler.setText(R.string.titulo_recycler_localizaciones);
        localizacionViewModel.cargarLocalizaciones();
        localizacionViewModel.obtener().observe(getViewLifecycleOwner(), localizacionesAdapter::establecerLista);
    }
}