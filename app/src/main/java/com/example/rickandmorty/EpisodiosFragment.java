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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.rickandmorty.adaptersRecyclerView.EpisodiosAdapter;
import com.example.rickandmorty.databinding.RecyclerviewFragmentsBinding;
import com.example.rickandmorty.utils.RecyclerViewEpisodios;
import com.example.rickandmorty.viewmodels.EpisodioViewModel;

public class EpisodiosFragment extends Fragment {
    private RecyclerviewFragmentsBinding binding;
    private EpisodioViewModel episodioViewModel;
    private EpisodiosAdapter episodiosAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return (binding = RecyclerviewFragmentsBinding.inflate(inflater, container, false)).getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        episodioViewModel = new ViewModelProvider(requireActivity()).get(EpisodioViewModel.class);
        episodiosAdapter = new EpisodiosAdapter(episodioViewModel, NavHostFragment.findNavController(this), R.id.action_episodiosFragment_to_mostrarEpisodioFragment);

        binding.itemRecycler.setAdapter(episodiosAdapter);
        binding.itemRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        RecyclerViewEpisodios recyclerEpisodios = new RecyclerViewEpisodios(binding.itemRecycler,episodioViewModel, episodiosAdapter);
        recyclerEpisodios.setupRecyclerView(getContext());
        recyclerEpisodios.observarEpisodios(getViewLifecycleOwner());
        recyclerEpisodios.configurarBusqueda(binding.searchBar);
        binding.tituloRecycler.setText("Episodios");
        episodioViewModel.cargarEpisodios();
        episodioViewModel.obtener().observe(getViewLifecycleOwner(), episodiosAdapter::establecerLista);

    }

}