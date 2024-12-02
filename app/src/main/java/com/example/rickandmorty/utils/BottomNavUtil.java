package com.example.rickandmorty.utils;

import android.app.Activity;
import android.view.View;

public class BottomNavUtil {
    /**
     * Oculta el BottomNavigationView.
     *
     * @param activity  La actividad desde la cual acceder al BottomNavigationView.
     * @param viewId    El ID del BottomNavigationView.
     */
    public static void ocultarBottomNavigationView(Activity activity, int viewId) {
        if (activity != null) {
            View bottomNavigationView = activity.findViewById(viewId);
            if (bottomNavigationView != null) {
                bottomNavigationView.setVisibility(View.GONE);
            }
        }
    }

    /**
     * Muestra el BottomNavigationView.
     *
     * @param activity  La actividad desde la cual acceder al BottomNavigationView.
     * @param viewId    El ID del BottomNavigationView.
     */
    public static void mostrarBottomNavigationView(Activity activity, int viewId) {
        if (activity != null) {
            View bottomNavigationView = activity.findViewById(viewId);
            if (bottomNavigationView != null) {
                bottomNavigationView.setVisibility(View.VISIBLE);
            }
        }
    }
}
