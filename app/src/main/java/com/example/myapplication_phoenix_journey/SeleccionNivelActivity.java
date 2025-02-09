package com.example.myapplication_phoenix_journey;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SeleccionNivelActivity extends AppCompatActivity {

    private RelativeLayout botonPrincipiante;
    private RelativeLayout botonAvanzado;
    private Button botonTerminar;
    private ImageButton backButton;
    private ImageView checkPrincipiante; // Ícono de check para el botón Principiante
    private ImageView checkAvanzado; // Ícono de check para el botón Avanzado
    private RelativeLayout selectedLayout = null; // Variable para almacenar el layout seleccionado

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_nivel);

        // Eliminar la ActionBar (nombre de la clase en la parte superior)
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Configurar la ventana para un diseño de pantalla completa
        getWindow().setFlags(
                android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        // Inicializa los botones y los íconos de check
        botonPrincipiante = findViewById(R.id.boton_principiante);
        botonAvanzado = findViewById(R.id.boton_avanzado);
        botonTerminar = findViewById(R.id.boton_terminar);
        backButton = findViewById(R.id.back_button);
        checkPrincipiante = findViewById(R.id.principiante_icon);
        checkAvanzado = findViewById(R.id.avanzado_icon);

        // Inicialmente, deshabilitar el botón "Terminar"
        botonTerminar.setEnabled(false);

        // OnClickListener para el botón Principiante
        botonPrincipiante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionarBoton(botonPrincipiante, checkPrincipiante);
            }
        });

        // OnClickListener para el botón Avanzado
        botonAvanzado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionarBoton(botonAvanzado, checkAvanzado);
            }
        });

        // Acción para el botón Terminar
        botonTerminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mensaje de confirmación (opcional)
                Toast.makeText(SeleccionNivelActivity.this, "Nivel seleccionado", Toast.LENGTH_SHORT).show();

                // Cambiar de actividad a MainActivity
                Intent intent = new Intent(SeleccionNivelActivity.this, MainActivity.class);
                startActivity(intent);  // Iniciar la MainActivity
                finish();  // Finalizar la actividad actual (opcional)
            }
        });

        // Acción para el botón de retroceso
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear una intención para ir a la actividad anterior
                Intent intent = new Intent(SeleccionNivelActivity.this, AgregarDatosActivity.class);
                startActivity(intent); // Iniciar la actividad
                finish(); // Cerrar la actividad actual
            }
        });
    }

    // Función para manejar la selección de un botón
    private void seleccionarBoton(RelativeLayout botonSeleccionado, ImageView checkIcon) {
        // Si ya se había seleccionado otro layout, lo desmarcamos
        if (selectedLayout != null && selectedLayout != botonSeleccionado) {
            selectedLayout.setBackgroundResource(R.drawable.button_momt); // Restaurar el fondo original
            if (selectedLayout == botonPrincipiante) {
                checkPrincipiante.setVisibility(View.GONE); // Ocultar el ícono de "check"
            } else {
                checkAvanzado.setVisibility(View.GONE); // Ocultar el ícono de "check"
            }
        }

        // Marcar el layout como seleccionado
        botonSeleccionado.setBackgroundResource(R.drawable.button_selected); // Cambiar fondo a seleccionado
        checkIcon.setVisibility(View.VISIBLE); // Mostrar el ícono de "check"

        // Actualizar la variable de selección
        selectedLayout = botonSeleccionado;

        // Habilitar el botón "Terminar"
        botonTerminar.setEnabled(true);
    }
}