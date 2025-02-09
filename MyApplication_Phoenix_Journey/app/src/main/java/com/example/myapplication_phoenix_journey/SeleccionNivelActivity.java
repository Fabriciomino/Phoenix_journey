package com.example.myapplication_phoenix_journey;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SeleccionNivelActivity extends AppCompatActivity {

    private Button botonPrincipiante;
    private Button botonAvanzado;
    private Button botonTerminar;
    private ImageButton backButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_nivel);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        getWindow().setFlags(
                android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );


        // Inicializa los botones
        botonPrincipiante = findViewById(R.id.boton_principiante);
        botonAvanzado = findViewById(R.id.boton_avanzado);
        botonTerminar = findViewById(R.id.boton_terminar);
        backButton = findViewById(R.id.back_button);


        // OnClickListener para el botón Principiante
        botonPrincipiante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Selecciona el botón Principiante y deselecciona el botón Avanzado
                seleccionarBoton(botonPrincipiante, botonAvanzado);
            }
        });

        // OnClickListener para el botón Avanzado
        botonAvanzado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Selecciona el botón Avanzado y deselecciona el botón Principiante
                seleccionarBoton(botonAvanzado, botonPrincipiante);
            }
        });

        // Acción para el botón Terminar
        botonTerminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí puedes agregar la acción que desees, como cambiar de actividad.
                // Mensaje de confirmación (opcional)
                Toast.makeText(SeleccionNivelActivity.this, "Nivel seleccionado", Toast.LENGTH_SHORT).show();

                // Cambiar de actividad a MainActivity
                Intent intent = new Intent(SeleccionNivelActivity.this, MainActivity.class);
                startActivity(intent);  // Iniciar la MainActivity
                finish();  // Finalizar la actividad actual (opcional)
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear una intención para ir a la
                Intent intent = new Intent(SeleccionNivelActivity.this, AgregarDatosActivity.class);
                startActivity(intent); // Iniciar la actividad
                finish(); // Cerrar la actividad de login
            }
        });
    }

    // Función para manejar la selección de un botón y deseleccionar el otro
    private void seleccionarBoton(Button botonSeleccionado, Button botonDeseleccionado) {
        // Cambiar fondo del botón seleccionado
        botonSeleccionado.setBackgroundResource(R.drawable.button_selected);  // Cambiar fondo a seleccionado
        // Cambiar fondo del botón deseleccionado
        botonDeseleccionado.setBackgroundResource(R.drawable.button_default);  // Cambiar fondo a no seleccionado
    }
}
