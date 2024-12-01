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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.rickandmorty.adaptersRecyclerView.EpisodiosAdapter;
import com.example.rickandmorty.databinding.FragmentEpisodiosBinding;
import com.example.rickandmorty.viewmodels.EpisodioViewModel;

public class EpisodiosFragment extends Fragment {
    private FragmentEpisodiosBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return (binding = FragmentEpisodiosBinding.inflate(inflater, container, false)).getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EpisodiosAdapter episodiosAdapter = new EpisodiosAdapter();

        binding.recyclerView.setAdapter(episodiosAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        EpisodioViewModel episodioViewModel = new ViewModelProvider(this).get(EpisodioViewModel.class);
        episodioViewModel.cargarEpisodios();
        episodioViewModel.getEpisodiosLiveData().observe(getViewLifecycleOwner(), episodios -> {
            Log.d("Episodios", "Tamaño de la lista de episodios: " + episodios.size());
            episodiosAdapter.establecerLista(episodios);
        });


    }

}