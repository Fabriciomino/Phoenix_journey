package com.example.myapplication_phoenix_journey;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {

    private ImageButton centerButton;
    private LinearLayout misRutinasButton, sesionButton;
    private LinearLayout progresoButton, nutricionButton;
    private ImageButton calendarioButton, usuarioButton, menuIconButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Ocultar la barra de acción (ActionBar)
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Configurar la ventana para un diseño de pantalla completa
        getWindow().setFlags(
                android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        // Inicializar los botones (ahora LinearLayouts en lugar de Buttons)
        centerButton = findViewById(R.id.center_button);
        misRutinasButton = findViewById(R.id.top_left_button);
        sesionButton = findViewById(R.id.top_right_button);
        progresoButton = findViewById(R.id.bottom_left_button);
        nutricionButton = findViewById(R.id.bottom_right_button);
        calendarioButton = findViewById(R.id.calendario_button);
        usuarioButton = findViewById(R.id.usuario_button);
        menuIconButton = findViewById(R.id.menu_icon_button);

        // Configurar eventos de clic para el botón central
        centerButton.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, PhoenixJourneyActivity.class);
            startActivity(intent);
        });

        // Configuración de cada LinearLayout como si fuera un botón
        misRutinasButton.setOnClickListener(v -> {
            startActivity(new Intent(MenuActivity.this, CrearRutinaActivity.class));
        });

        sesionButton.setOnClickListener(v -> {
            startActivity(new Intent(MenuActivity.this, IniciarEntrenamientoActivity.class));
        });

        progresoButton.setOnClickListener(v -> {
            startActivity(new Intent(MenuActivity.this, ProgresoCorporalActivity.class));
        });

        nutricionButton.setOnClickListener(v -> {
            startActivity(new Intent(MenuActivity.this, DietaActivity.class));
        });

        // Evento para el botón de calendario
        calendarioButton.setOnClickListener(v -> {
            showToast("Calendario");
            // Iniciar la actividad CalendarioActivity
            Intent intent = new Intent(MenuActivity.this, CalendarioActivity.class);
            startActivity(intent);
        });

        // Evento para el botón de usuario
        usuarioButton.setOnClickListener(v -> {
            showToast("Usuario");
            // Iniciar la actividad MiUsuarioActivity
            Intent intent = new Intent(MenuActivity.this, MiUsuarioActivity.class);
            startActivity(intent);
        });

        // Evento para el botón de menú
        menuIconButton.setOnClickListener(v -> {
            showToast("Menú");
            // Aquí puedes agregar la lógica para abrir el menú o realizar cualquier acción que desees
        });
    }

    /**
     * Método auxiliar para mostrar un mensaje Toast.
     *
     * @param message El mensaje que se mostrará.
     */
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
