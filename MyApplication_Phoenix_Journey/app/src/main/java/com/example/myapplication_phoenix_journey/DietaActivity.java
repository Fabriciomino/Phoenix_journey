package com.example.myapplication_phoenix_journey;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class DietaActivity extends AppCompatActivity {

    private ImageButton backButton;
    private Button mantenimientoButton, volumenButton, deficitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Configurar la ventana para un diseño de pantalla completa
        getWindow().setFlags(
                android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dieta); // Asegúrate de que el nombre del layout sea correcto


        // Inicialización de los elementos de la interfaz
        backButton = findViewById(R.id.back_button);
        mantenimientoButton = findViewById(R.id.mantenimiento_button);
        volumenButton = findViewById(R.id.volumen_button);
        deficitButton = findViewById(R.id.deficit_button);

        // Evento para el botón de Atrás
        backButton.setOnClickListener(v -> {
            // Regresar a la actividad anterior (MenuActivity)
            startActivity(new Intent(DietaActivity.this, MenuActivity.class));
            finish(); // Finaliza la actividad actual para evitar que quede en la pila de actividades
        });

        // Configuración de los eventos de los botones de dieta

        // Evento para el botón Mantenimiento
        mantenimientoButton.setOnClickListener(v -> {
            // Redirigir a la actividad para Mantenimiento
            startActivity(new Intent(DietaActivity.this, NutrientesActivityMantenimiento.class));
        });

        // Evento para el botón Volumen
        volumenButton.setOnClickListener(v -> {
            // Redirigir a la actividad para Volumen
            startActivity(new Intent(DietaActivity.this, NutrientesActivityVolumen.class));
        });

        // Evento para el botón Déficit
        deficitButton.setOnClickListener(v -> {
            // Redirigir a la actividad para Déficit
            startActivity(new Intent(DietaActivity.this, NutrientesActivity.class));
        });
    }
}
