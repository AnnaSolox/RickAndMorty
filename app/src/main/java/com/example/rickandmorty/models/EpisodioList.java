package com.example.rickandmorty.models;

import com.google.gson.annotations.SerializedName;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class EpisodioList {
    private Info info;
    @SerializedName("results")
    private ArrayList<Episodio> resultadosEpisodio;
}
