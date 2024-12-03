package com.example.rickandmorty.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDate;

/**
 * Clase de utilidad para configurar y obtener instancias personalizadas de Gson.
 * Proporciona una configuración predeterminada de Gson que incluye adaptadores personalizados
 * para manejar tipos de datos específicos, como {@link LocalDate}.
 */
public class GsonConfig {
    /**
     * Crea y devuelve una instancia configurada de Gson.
     * La configuración incluye un adaptador registrado para serializar y deserializar
     * objetos de tipo {@link LocalDate}.
     *
     * @return Una instancia configurada de {@link Gson}.
     */

    public static Gson getGson(){
        return new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
    }
}
