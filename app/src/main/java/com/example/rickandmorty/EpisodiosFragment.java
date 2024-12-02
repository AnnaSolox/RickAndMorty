package com.example.rickandmorty;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.rickandmorty.adaptersRecyclerView.EpisodiosAdapter;
import com.example.rickandmorty.databinding.RecyclerviewFragmentsBinding;
import com.example.rickandmorty.utils.RecyclerEpisodios;
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

        episodiosAdapter = new EpisodiosAdapter();
        episodioViewModel = new ViewModelProvider(requireActivity()).get(EpisodioViewModel.class);

        binding.recyclerView.setAdapter(episodiosAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        RecyclerEpisodios recyclerEpisodios = new RecyclerEpisodios(binding.recyclerView,episodioViewModel, episodiosAdapter);
        recyclerEpisodios.setupRecyclerView(getContext());
        recyclerEpisodios.observarPersonajes(getViewLifecycleOwner());
        recyclerEpisodios.configurarBusqueda(binding.searchBar);
        binding.tituloRecycler.setText("Episodios");
        episodioViewModel.cargarEpisodios();

    }

}