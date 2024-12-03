package com.example.rickandmorty.adaptersRecyclerView;

import android.content.Context;
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

public class PersonajesAdapter extends RecyclerView.Adapter<PersonajeViewHolder> {
    private List<Personaje> personajes = new ArrayList<>();
    private List<Personaje> personajesOriginal;
    private final NavController navController;
    private final PersonajeViewModel personajeViewModel;
    private final int actionId;


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
            if (view.getContext() instanceof FragmentActivity) {
                personajeViewModel.seleccionar(personaje);
                navController.navigate(actionId);
            } else {
                Intent intent = new Intent(view.getContext(), SecondActivity.class);
                intent.putExtra("personajeId", personaje.getId());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return personajes != null ? personajes.size() : 0;
    }

    public void establecerLista(List<Personaje> personajes){
        this.personajes = personajes;
        this.personajesOriginal = new ArrayList<>(personajes);
        notifyDataSetChanged();
    }

    public void filtradoPorNombre(String filtro) {
        personajes = FiltradoUtilidad.filtro(personajesOriginal, filtro, (item, textoFiltro) -> item.getNombre().toLowerCase().contains(textoFiltro.toLowerCase()));
        notifyDataSetChanged();
    }
}
