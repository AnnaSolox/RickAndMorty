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

import com.bumptech.glide.Glide;
import com.example.rickandmorty.R;
import com.example.rickandmorty.adaptersRecyclerView.EpisodiosAdapter;
import com.example.rickandmorty.databinding.FragmentMostrarPersonajeBinding;
import com.example.rickandmorty.adaptersRecyclerView.RecyclerViewEpisodios;
import com.example.rickandmorty.viewmodels.EpisodioViewModel;
import com.example.rickandmorty.viewmodels.PersonajeViewModel;

import java.util.Objects;

/**
 * Fragmento encargado de mostrar los detalles de un personaje seleccionado y la lista de episodios en los que aparece.
 * <p>
 * Este fragmento utiliza ViewModels para observar el personaje seleccionado y los episodios asociados,
 * permitiendo además marcar el personaje como favorito.
 * </p>
 */
public class MostrarPersonajeFragment extends Fragment {
    private FragmentMostrarPersonajeBinding binding;
    private EpisodioViewModel episodioViewModel;
    private EpisodiosAdapter episodiosAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return (binding = FragmentMostrarPersonajeBinding.inflate(inflater,container,false)).getRoot();
    }

    /**
     * Configura el {@link RecyclerViewEpisodios}, observa los cambios en el personaje seleccionado y en los episodios asociados,
     * y permite alternar el estado de favorito del personaje.
     *
     * @param view La vista raíz del fragmento.
     * @param savedInstanceState El estado guardado del fragmento, si existe.
     */
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
                binding.itemRecyclerFragment.tituloRecycler.setText(R.string.titulo_recycler_episodios);
                episodioViewModel.cargarEpisodiosPorId(personaje.getEpisodios());

                //Gestión del cambio de estado de botón de favorito en la ventana detalla de personaje.
                boolean esFavorito = personajeViewModel.esFavorito(personaje);
                binding.favorito.setImageResource(esFavorito
                        ? R.drawable.ic_rm_fav_checked
                        : R.drawable.ic_rm_fav_unchecked);

                //Cambio de icono de botón según el estado
                binding.favorito.setOnClickListener(v -> {
                    personaje.toggleFavorito();
                    personajeViewModel.toggleFavorito(personaje);
                    binding.favorito.setImageResource(personaje.isEsFavorito()
                            ? R.drawable.ic_rm_fav_checked
                            : R.drawable.ic_rm_fav_unchecked);
                });
            }
        });

        episodioViewModel.getEpisodiosLiveData().observe(getViewLifecycleOwner(), episodios -> {
            Log.d("PersonajesLocalizacion", "Tamaño de la lista de personajes: " + episodios.size());
            if (!episodios.isEmpty()) {
                episodiosAdapter.establecerLista(episodios);
            } else {
                Log.e("Personajes", "La lista de personajes está vacía.");
            }
        });
    }
}