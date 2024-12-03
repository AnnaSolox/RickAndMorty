package com.example.rickandmorty.models;

import com.google.gson.annotations.SerializedName;

import java.net.URI;
import java.util.List;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor

public class Personaje {
    private int id;
    @SerializedName("name")
    private String nombre;
    @SerializedName("status")
    private String estado;
    @SerializedName("species")
    private String especie;
    @SerializedName("type")
    private String tipo;
    @SerializedName("gender")
    private String genero;
    @SerializedName("origin")
    private Localizacion origen;
    @SerializedName("location")
    private Localizacion localizacion;
    @SerializedName("image")
    private String imagen;
    @SerializedName("episode")
    private List<String> episodios;
    public boolean esFavorito;

    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Personaje personaje = (Personaje) o;
        return id == personaje.id;
    }

    public int hashCode() {
        return Integer.hashCode(id);
    }

    public void toggleFavorito(){
        this.esFavorito = !this.esFavorito;
    }
}
