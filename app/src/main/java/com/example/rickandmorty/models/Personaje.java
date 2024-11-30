package com.example.rickandmorty.models;

import com.google.gson.annotations.SerializedName;

import java.net.URI;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

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
    private URI imagen;
    @SerializedName("episode")
    private List<URI> episodios;


}
