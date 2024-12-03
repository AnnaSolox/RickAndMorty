package com.example.rickandmorty.adaptersRecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.rickandmorty.MostrarPersonajeFragment;
import com.example.rickandmorty.databinding.ItemPersonajeBinding;
import com.example.rickandmorty.models.Personaje;

import java.util.ArrayList;
import java.util.List;

public class FavoritosAdapter extends RecyclerView.Adapter<PersonajeViewHolder> {
    private List<Personaje> personajes = new ArrayList<>();
    private final Context context;

    public FavoritosAdapter(Context context){
        this.context = context;
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
            Intent intent = new Intent(context, MostrarPersonajeFragment.class);
            intent.putExtra("personajeId", personaje.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return personajes != null ? personajes.size() : 0;
    }

    public void establecerLista(List<Personaje> personajes) {
        this.personajes = personajes;
        notifyDataSetChanged();
    }
}
