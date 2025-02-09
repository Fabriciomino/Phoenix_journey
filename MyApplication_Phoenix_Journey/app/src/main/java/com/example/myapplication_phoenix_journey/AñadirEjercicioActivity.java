package com.example.myapplication_phoenix_journey;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class AñadirEjercicioActivity extends AppCompatActivity {

    private ImageButton backButton; // Botón de retroceso

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        getWindow().setFlags(
                android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_ejercicio);

        // Inicializar el botón "Atrás"
        backButton = findViewById(R.id.back_button);

        // Configurar acción del botón "Atrás"
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Finalizar la actividad actual y regresar a la anterior
            }
        });
    }
}
