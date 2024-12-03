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

public class FavoritosJsonUtilidad {
    private static final String FAVORITOS_FILE = "favoritos.json";

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
