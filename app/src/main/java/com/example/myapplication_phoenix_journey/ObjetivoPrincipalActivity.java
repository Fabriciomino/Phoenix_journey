package com.example.myapplication_phoenix_journey;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ObjetivoPrincipalActivity extends AppCompatActivity {

    private RelativeLayout aumentoMuscularButton;
    private RelativeLayout perdidaPesoButton;
    private RelativeLayout gananciaFuerzaButton;
    private RelativeLayout selectedLayout = null; // Variable para almacenar el layout seleccionado
    private ImageView selectedIcon = null; // Variable para almacenar el icono seleccionado

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objetivo_principal);

        // Ocultar la barra de acción si está disponible
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Configurar la ventana para que ocupe toda la pantalla
        getWindow().setFlags(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY,
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        // Configurar la ventana para un diseño de pantalla completa
        getWindow().setFlags(
                android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        aumentoMuscularButton = findViewById(R.id.aumento_muscular_button);
        perdidaPesoButton = findViewById(R.id.perdida_peso_button);
        gananciaFuerzaButton = findViewById(R.id.ganancia_fuerza_button);

        // Establecer los OnClickListeners para cada botón
        aumentoMuscularButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionarBoton(aumentoMuscularButton, R.id.aumento_muscular_icon);
            }
        });

        perdidaPesoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionarBoton(perdidaPesoButton, R.id.perdida_peso_icon);
            }
        });

        gananciaFuerzaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionarBoton(gananciaFuerzaButton, R.id.ganancia_fuerza_icon);
            }
        });

        // Acción para el botón de siguiente
        findViewById(R.id.siguiente_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAgregarDatosActivity();
            }
        });
    }

    private void openAgregarDatosActivity() {
        Intent intent = new Intent(ObjetivoPrincipalActivity.this, AgregarDatosActivity.class);
        startActivity(intent);
    }

    private void seleccionarBoton(RelativeLayout botonSeleccionado, int iconoId) {
        // Si ya se había seleccionado otro layout, lo desmarcamos
        if (selectedLayout != null) {
            selectedLayout.setBackgroundResource(R.drawable.button_momt); // Restaurar el fondo original
            selectedIcon.setVisibility(View.GONE); // Ocultar el ícono de "check"
        }

        // Marcar el layout como seleccionado
        botonSeleccionado.setBackgroundResource(R.drawable.button_selected); // Fondo del botón seleccionado
        ImageView icono = botonSeleccionado.findViewById(iconoId); // Obtener el ícono del botón seleccionado
        icono.setVisibility(View.VISIBLE); // Mostrar el ícono de "check"

        // Actualizar las variables de selección
        selectedLayout = botonSeleccionado;
        selectedIcon = icono;
    }
}
