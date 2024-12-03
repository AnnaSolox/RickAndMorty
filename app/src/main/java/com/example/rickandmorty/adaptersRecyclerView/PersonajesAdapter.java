package com.example.rickandmorty.adaptersRecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.rickandmorty.activities.SecondActivity;
import com.example.rickandmorty.databinding.ItemPersonajeBinding;
import com.example.rickandmorty.models.Personaje;
import com.example.rickandmorty.utils.FiltradoUtilidad;
import com.example.rickandmorty.viewmodels.PersonajeViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter para mostrar una lista de personajes en un {@link RecyclerView}.
 * <p>
 * Este adaptador se encarga de gestionar la visualización de una lista de personajes en un {@link RecyclerView},
 * permitiendo la carga de imágenes con {@link Glide}, la asignación de nombres a través de {@link ItemPersonajeBinding},
 * y la navegación entre pantallas usando un {@link NavController}.
 * </p>
 */
public class PersonajesAdapter extends RecyclerView.Adapter<PersonajeViewHolder> {
    private List<Personaje> personajes = new ArrayList<>();
    private List<Personaje> personajesOriginal;
    private final NavController navController;
    private final PersonajeViewModel personajeViewModel;
    private final int actionId;


    /**
     * Constructor para crear una instancia del adaptador de personajes.
     *
     * @param personajeViewModel El ViewModel asociado a los personajes.
     * @param navController El controlador de navegación para manejar las transiciones.
     * @param actionId El ID de acción para la navegación.
     */
    public PersonajesAdapter(PersonajeViewModel personajeViewModel, NavController navController, int actionId){
        this.personajeViewModel = personajeViewModel;
        this.navController = navController;
        this.actionId = actionId;
    }

    @NonNull
    @Override
    public PersonajeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PersonajeViewHolder(ItemPersonajeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PersonajeViewHolder holder, int position) {
        Personaje personaje = personajes.get(position);
        Glide.with(holder.binding.imagePj.getContext()).load(personaje.getImagen()).into(holder.binding.imagePj);
        holder.binding.toolItemPj.setTitle(personaje.getNombre());

        holder.itemView.setOnClickListener(view -> {
                personajeViewModel.seleccionar(personaje);
                navController.navigate(actionId);
        });
    }

    @Override
    public int getItemCount() {
        return personajes != null ? personajes.size() : 0;
    }

    /**
     * Establece una nueva lista de personajes en el adaptador.
     *
     * @param personajes La nueva lista de personajes.
     */
    @SuppressLint("NotifyDataSetChanged")
    public void establecerLista(List<Personaje> personajes){
        this.personajes = personajes;
        this.personajesOriginal = new ArrayList<>(personajes);
        notifyDataSetChanged();
    }

    /**
     * Filtra los personajes por nombre.
     *
     * @param filtro El texto para filtrar por nombre.
     */
    @SuppressLint("NotifyDataSetChanged")
    public void filtradoPorNombre(String filtro) {
        personajes = FiltradoUtilidad.filtro(personajesOriginal, filtro, (item, textoFiltro) -> item.getNombre().toLowerCase().contains(textoFiltro.toLowerCase()));
        notifyDataSetChanged();
    }
}
