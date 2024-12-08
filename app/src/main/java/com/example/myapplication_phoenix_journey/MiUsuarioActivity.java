package com.example.myapplication_phoenix_journey;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MiUsuarioActivity extends AppCompatActivity {

    private LinearLayout infoCuentaButton, ajustesButton, ayudaButton, cerrarSesionButton;
    private ImageButton calendarioButton, usuarioButton, menuIconButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_usuario);

        // Eliminar la ActionBar (nombre de la clase en la parte superior)
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Configurar la ventana para un diseño de pantalla completa
        getWindow().setFlags(
                android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        // Inicializar los botones
        infoCuentaButton = findViewById(R.id.infoCuentaButton);
        ajustesButton = findViewById(R.id.ajustesButton);
        ayudaButton = findViewById(R.id.ayudaButton);
        cerrarSesionButton = findViewById(R.id.cerrarSesionButton);
        calendarioButton = findViewById(R.id.calendario_button);
        usuarioButton = findViewById(R.id.usuario_button);
        menuIconButton = findViewById(R.id.menu_icon_button);

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

        // Configurar botón para ir a CalendarioActivity
        calendarioButton.setOnClickListener(v -> {
            Intent intent = new Intent(MiUsuarioActivity.this, CalendarioActivity.class);
            startActivity(intent);
        });

        // Configurar botón para ir a MenuActivity
        menuIconButton.setOnClickListener(v -> {
            Intent intent = new Intent(MiUsuarioActivity.this, MenuActivity.class);
            startActivity(intent);
        });
    }

    private void showCerrarSesionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cerrar Sesión");
        builder.setMessage("¿Seguro que quieres cerrar sesión?");

        builder.setPositiveButton("Aceptar", (dialog, which) -> {
            // Mostrar mensaje de cierre de sesión
            Toast.makeText(MiUsuarioActivity.this, "Sesión cerrada", Toast.LENGTH_SHORT).show();

            // Redirigir a LoginActivity
            Intent intent = new Intent(MiUsuarioActivity.this, MainActivity.class);
            startActivity(intent);

            // Cerrar la actividad actual
            finish();
        });

        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
