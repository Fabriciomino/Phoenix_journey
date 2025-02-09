package com.example.myapplication_phoenix_journey;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MiUsuarioActivity extends AppCompatActivity {

    private LinearLayout infoCuentaButton, ajustesButton, ayudaButton, cerrarSesionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_usuario);

        // Ocultar la ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Inicializar los botones
        infoCuentaButton = findViewById(R.id.infoCuentaButton);
        ajustesButton = findViewById(R.id.ajustesButton);
        ayudaButton = findViewById(R.id.ayudaButton);
        cerrarSesionButton = findViewById(R.id.cerrarSesionButton);

        // Configurar acciones para cada botón
        infoCuentaButton.setOnClickListener(v ->
                Toast.makeText(this, "Información de la cuenta", Toast.LENGTH_SHORT).show()
        );

        ajustesButton.setOnClickListener(v ->
                Toast.makeText(this, "Ajustes", Toast.LENGTH_SHORT).show()
        );

        ayudaButton.setOnClickListener(v ->
                Toast.makeText(this, "Ayuda", Toast.LENGTH_SHORT).show()
        );

        cerrarSesionButton.setOnClickListener(v -> showCerrarSesionDialog());
    }

    // Método para mostrar el diálogo de confirmación de cierre de sesión
    private void showCerrarSesionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cerrar Sesión");
        builder.setMessage("¿Seguro que quieres cerrar sesión?");

        builder.setPositiveButton("Aceptar", (dialog, which) -> {
            Toast.makeText(MiUsuarioActivity.this, "Sesión cerrada", Toast.LENGTH_SHORT).show();
            finish(); // Cierra la actividad
        });

        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
