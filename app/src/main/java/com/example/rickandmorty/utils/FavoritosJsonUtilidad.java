package com.example.rickandmorty.utils;

import android.content.Context;
import android.util.Log;

import com.example.rickandmorty.models.Personaje;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

/**
 * Clase utilitaria para la gestión de personajes favoritos mediante un archivo JSON.
 * <p>
 * Proporciona métodos para guardar y cargar una lista de personajes favoritos desde
 * el almacenamiento interno del dispositivo.
 * </p>
 */
public class FavoritosJsonUtilidad {
    private static final String FAVORITOS_FILE = "favoritos.json";

    /**
     * Guarda la lista de personajes favoritos en un archivo JSON.
     *
     * @param context El contexto necesario para acceder al almacenamiento interno.
     * @param favoritos Un {@link Set} de objetos {@link Personaje} que se guardarán.
     */
    public static void guardarFavoritos(Context context, Set<Personaje> favoritos) {
        try {
            Gson gson = new Gson();
            String json = gson.toJson(favoritos);

            FileOutputStream fos = context.openFileOutput(FAVORITOS_FILE, Context.MODE_PRIVATE);
            Writer writer = new OutputStreamWriter(fos);
            writer.write(json);
            writer.close();

            Log.d("FavoritosStorage", "Favoritos guardados exitosamente.");
        } catch (Exception e) {
            Log.e("FavoritosStorage", "Error guardando favoritos: " + e.getMessage());
        }
    }

    /**
     * Carga la lista de personajes favoritos desde un archivo JSON.
     *
     * @param context El contexto necesario para acceder al almacenamiento interno.
     * @return Un {@link Set} de objetos {@link Personaje} cargados desde el archivo.
     *         Si ocurre un error o el archivo no existe, se devuelve un conjunto vacío.
     */
    public static Set<Personaje> cargarFavoritos(Context context) {
        Set<Personaje> favoritos = new HashSet<>();
        try {
            FileInputStream fis = context.openFileInput(FAVORITOS_FILE);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            StringBuilder jsonBuilder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
            reader.close();

            Gson gson = new Gson();
            Type type = new TypeToken<HashSet<Personaje>>() {}.getType();
            favoritos = gson.fromJson(jsonBuilder.toString(), type);

            Log.d("FavoritosStorage", "Favoritos cargados exitosamente.");
        } catch (Exception e) {
            Log.e("FavoritosStorage", "Error cargando favoritos: " + e.getMessage());
        }

        return favoritos;
    }
}
