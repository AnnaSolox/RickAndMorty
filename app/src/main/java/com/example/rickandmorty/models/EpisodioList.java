package com.example.rickandmorty.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EpisodioList {
    private Info info;
    @SerializedName("results")
    private ArrayList<Episodio> resultadosEpisodio;
}
