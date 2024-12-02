package com.example.rickandmorty.models;

import com.google.gson.annotations.SerializedName;

import java.net.URI;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class Localizacion {
    private int id;
    @SerializedName("name")
    private String nombre;
    @SerializedName("type")
    private String tipo;
    private String dimension;
    @SerializedName("residents")
    private List<String> residentes;
}
