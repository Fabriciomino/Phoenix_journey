package com.example.myapplication_phoenix_journey;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class AmigosActivity extends AppCompatActivity {

    private TextView agregarAmigosText;
    private EditText nombrePerfilEditText;
    private Button buscarUsuarioButton;

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

        // Configuración de la actividad, como poner texto o configurar listeners
        agregarAmigosText.setText("Agregar Amigos");

        // Configurar el listener para el botón de búsqueda
        buscarUsuarioButton.setOnClickListener(v -> {
            String nombrePerfil = nombrePerfilEditText.getText().toString();
            // Lógica para buscar usuario o agregarlo.
        });

    }
}
