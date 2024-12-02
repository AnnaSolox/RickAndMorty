package com.example.rickandmorty.models;

import com.google.gson.annotations.SerializedName;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class Episodio {
    private int id;
    @SerializedName("name")
    private String nombre;
    @SerializedName("air_date")
    private LocalDate fechaLanzamiento;
    @SerializedName("episode")
    private String identificador;
    @SerializedName("characters")
    private List<String> personajes;
}
