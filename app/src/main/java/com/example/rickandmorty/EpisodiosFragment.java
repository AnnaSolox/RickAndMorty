package com.example.rickandmorty;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.rickandmorty.adaptersRecyclerView.EpisodiosAdapter;
import com.example.rickandmorty.databinding.FragmentEpisodiosBinding;
import com.example.rickandmorty.viewmodels.EpisodioViewModel;

public class EpisodiosFragment extends Fragment {
    private FragmentEpisodiosBinding binding;
    private EpisodioViewModel episodioViewModel;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return (binding = FragmentEpisodiosBinding.inflate(inflater, container, false)).getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_toolbar);
        toolbar.setOnMenuItemClickListener(this::manejarClicItemMenu);


        EpisodiosAdapter episodiosAdapter = new EpisodiosAdapter();

        binding.recyclerView.setAdapter(episodiosAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        episodioViewModel = new ViewModelProvider(this).get(EpisodioViewModel.class);
        episodioViewModel.cargarEpisodios();
        episodioViewModel.getEpisodiosLiveData().observe(getViewLifecycleOwner(), episodios -> {
            Log.d("Episodios", "Tamaño de la lista de episodios: " + episodios.size());
            episodiosAdapter.establecerLista(episodios);
        });


    }

    private boolean manejarClicItemMenu(MenuItem item){
        if(item.getItemId() == R.id.personajes){
            navController.navigate(R.id.action_episodiosFragment_to_personajesFragment);
            return true;
        } else if(item.getItemId() == R.id.localizaciones){
            navController.navigate(R.id.action_episodiosFragment_to_localizacionesFragment);
            return true;
        } else if(item.getItemId() == R.id.episodios){
            return true;
        }
        return false;
    }

}