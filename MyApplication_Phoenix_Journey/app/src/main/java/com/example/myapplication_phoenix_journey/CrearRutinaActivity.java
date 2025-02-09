package com.example.myapplication_phoenix_journey;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class CrearRutinaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_rutina);
        // Ocultar la barra de acción (ActionBar)
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Configurar la ventana para un diseño de pantalla completa
        getWindow().setFlags(
                android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        // Configurar botón de Atrás
        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CrearRutinaActivity.this, MenuActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Configurar los botones de los días
        Button botonLunes = findViewById(R.id.boton_lunes);
        Button botonMartes = findViewById(R.id.boton_martes);
        Button botonMiercoles = findViewById(R.id.boton_miercoles);
        Button botonJueves = findViewById(R.id.boton_jueves);
        Button botonViernes = findViewById(R.id.boton_viernes);
        Button botonSabado = findViewById(R.id.boton_sabado);
        Button botonDomingo = findViewById(R.id.boton_domingo);

        botonLunes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Enviar el nombre del día a EditarRutinaActivity
                Intent intent = new Intent(CrearRutinaActivity.this, EditarRutinaActivity.class);
                intent.putExtra("DIA_SELECCIONADO", "Lunes");
                startActivity(intent);
            }
        });

        botonMartes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CrearRutinaActivity.this, EditarRutinaActivity.class);
                intent.putExtra("DIA_SELECCIONADO", "Martes");
                startActivity(intent);
            }
        });

        botonMiercoles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CrearRutinaActivity.this, EditarRutinaActivity.class);
                intent.putExtra("DIA_SELECCIONADO", "Miércoles");
                startActivity(intent);
            }
        });

        botonJueves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CrearRutinaActivity.this, EditarRutinaActivity.class);
                intent.putExtra("DIA_SELECCIONADO", "Jueves");
                startActivity(intent);
            }
        });

        botonViernes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CrearRutinaActivity.this, EditarRutinaActivity.class);
                intent.putExtra("DIA_SELECCIONADO", "Viernes");
                startActivity(intent);
            }
        });

        botonSabado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CrearRutinaActivity.this, EditarRutinaActivity.class);
                intent.putExtra("DIA_SELECCIONADO", "Sábado");
                startActivity(intent);
            }
        });

        botonDomingo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CrearRutinaActivity.this, EditarRutinaActivity.class);
                intent.putExtra("DIA_SELECCIONADO", "Domingo");
                startActivity(intent);
            }
        });
    }
}
