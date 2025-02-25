package com.example.myapplication_phoenix_journey;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class DietaFragment extends Fragment {

    private Button mantenimientoButton, volumenButton, deficitButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {




        // Inflar el layout del fragmento
        View view = inflater.inflate(R.layout.fragment_dieta, container, false);

        // Inicialización de los elementos de la interfaz
        mantenimientoButton = view.findViewById(R.id.mantenimiento_button);
        volumenButton = view.findViewById(R.id.volumen_button);
        deficitButton = view.findViewById(R.id.deficit_button);

        // Evento para el botón Mantenimiento
        mantenimientoButton.setOnClickListener(v -> {
            // Redirigir a la actividad para Mantenimiento
            startActivity(new Intent(getContext(), NutrientesActivityMantenimiento.class));
        });

        // Evento para el botón Volumen
        volumenButton.setOnClickListener(v -> {
            // Redirigir a la actividad para Volumen
            startActivity(new Intent(getContext(), NutrientesActivityVolumen.class));
        });

        // Evento para el botón Déficit
        deficitButton.setOnClickListener(v -> {
            // Redirigir a la actividad para Déficit
            startActivity(new Intent(getContext(), NutrientesActivityDefinicion.class));
        });

        return view;
    }
}
