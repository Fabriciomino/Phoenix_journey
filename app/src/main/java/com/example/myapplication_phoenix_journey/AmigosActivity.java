package com.example.myapplication_phoenix_journey;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class AmigosActivity extends AppCompatActivity {

    private TextView agregarAmigosText;
    private EditText nombrePerfilEditText;
    private Button buscarUsuarioButton;
    private ImageButton backButton;  // Añadir ImageButton para Atrás

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amigos);

        // Eliminar la ActionBar (nombre de la clase en la parte superior)
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Configurar la ventana para un diseño de pantalla completa
        getWindow().setFlags(
                android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        // Inicializar las vistas
        agregarAmigosText = findViewById(R.id.agregar_amigos_text);
        nombrePerfilEditText = findViewById(R.id.nombre_perfil_input);
        buscarUsuarioButton = findViewById(R.id.buscar_usuario_button);
        backButton = findViewById(R.id.back_button);  // Inicializa el botón de Atrás

        // Configuración de la actividad, como poner texto o configurar listeners
        agregarAmigosText.setText("Agregar Amigos");

        // Configurar el listener para el botón de búsqueda
        buscarUsuarioButton.setOnClickListener(v -> {
            String nombrePerfil = nombrePerfilEditText.getText().toString();
            // Lógica para buscar usuario o agregarlo.
        });

        // Configurar el listener para el botón de Atrás
        backButton.setOnClickListener(v -> {
            // Crear una intención para ir a la actividad PhoenixJourneyActivity
            Intent intent = new Intent(AmigosActivity.this, PhoenixJourneyActivity.class);
            startActivity(intent);
            finish();  // Opcionalmente, puedes cerrar la actividad actual si ya no la necesitas
        });
    }
}
