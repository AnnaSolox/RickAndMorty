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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.rickandmorty.R;
import com.example.rickandmorty.adaptersRecyclerView.EpisodiosAdapter;
import com.example.rickandmorty.databinding.RecyclerviewFragmentsBinding;
import com.example.rickandmorty.adaptersRecyclerView.RecyclerViewEpisodios;
import com.example.rickandmorty.viewmodels.EpisodioViewModel;

/**
 * Fragmento que muestra la lista de episodios en un {@link RecyclerViewEpisodios}.
 * <p>
 * Este fragmento se encarga de inflar el layout, configurar el {@link RecyclerViewEpisodios} para mostrar los episodios,
 * inicializar el {@link EpisodiosAdapter} y gestionar la interacción entre el {@link EpisodioViewModel}
 * y el adaptador. Además, configura una barra de búsqueda para filtrar los episodios y observa los cambios
 * en los datos del ViewModel.
 * </p>
 */
public class EpisodiosFragment extends Fragment {
    private RecyclerviewFragmentsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return (binding = RecyclerviewFragmentsBinding.inflate(inflater, container, false)).getRoot();
    }

    /**
     * Se llama después de que la vista del fragmento ha sido creada. Configura el {@link RecyclerViewEpisodios},
     * el adaptador y observa el ViewModel para actualizar la lista de episodios.
     *
     * @param view La vista raíz del fragmento.
     * @param savedInstanceState El estado guardado del fragmento, si existe.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EpisodioViewModel episodioViewModel = new ViewModelProvider(requireActivity()).get(EpisodioViewModel.class);
        EpisodiosAdapter episodiosAdapter = new EpisodiosAdapter(episodioViewModel, NavHostFragment.findNavController(this), R.id.action_episodiosFragment_to_mostrarEpisodioFragment);

        binding.itemRecycler.setAdapter(episodiosAdapter);
        binding.itemRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        RecyclerViewEpisodios recyclerEpisodios = new RecyclerViewEpisodios(binding.itemRecycler, episodioViewModel, episodiosAdapter);
        recyclerEpisodios.setupRecyclerView(getContext());
        recyclerEpisodios.observarEpisodios(getViewLifecycleOwner());
        recyclerEpisodios.configurarBusqueda(binding.searchBar);
        binding.tituloRecycler.setText(R.string.titulo_recycler_episodios);
        episodioViewModel.cargarEpisodios();
        episodioViewModel.obtener().observe(getViewLifecycleOwner(), episodiosAdapter::establecerLista);

    }

}