package com.example.myapplication_phoenix_journey;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class EjerAbsActivity extends AppCompatActivity {

    // Constantes para los nombres de los extras
    public static final String EXTRA_EJERCICIO = "EJERCICIO";
    public static final String EXTRA_IMAGEN = "IMAGEN";

    private ImageButton backButton;
    private LinearLayout btnCrunches, btnAbsDeclinado, btnElevacionesPiernas, btnAbsTradicionales;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejer_abs);

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

        // Inicializar los botones y elementos de la interfaz
        inicializarElementos();
    }

    // Eliminar la ActionBar
    private void ocultarActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    // Configurar la ventana para pantalla completa
    private void configurarPantallaCompleta() {
        getWindow().setFlags(
                android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
    }

    // Inicializar los botones y elementos de la interfaz
    private void inicializarElementos() {
        backButton = findViewById(R.id.back_button);
        btnCrunches = findViewById(R.id.crunches);
        btnAbsDeclinado = findViewById(R.id.abs_declinado);
        btnElevacionesPiernas = findViewById(R.id.elevaciones_piernas);
        btnAbsTradicionales = findViewById(R.id.abs_tradicionales);

        // Configurar el botón "Atrás"
        backButton.setOnClickListener(v -> {
            // Devolver un resultado cancelado si el usuario presiona el botón de retroceso
            setResult(RESULT_CANCELED);
            finish(); // Cerrar la actividad actual
        });

        // Configurar los botones de los ejercicios
        configurarBotonEjercicio(btnCrunches, "Crunches en polea", R.drawable.abs_polea);
        configurarBotonEjercicio(btnAbsDeclinado, "Abdominales en banco declinado", R.drawable.abs_declinado);
        configurarBotonEjercicio(btnElevacionesPiernas, "Elevaciones de pierna", R.drawable.abs_elevacion_piernas);
        configurarBotonEjercicio(btnAbsTradicionales, "Abdominales tradicionales", R.drawable.abs_tradicionales);
    }

    // Método que centraliza la configuración de los botones de los ejercicios
    private void configurarBotonEjercicio(LinearLayout boton, final String ejercicio, final int imagenResId) {
        boton.setOnClickListener(v -> mostrarDialogoConfirmacion(ejercicio, imagenResId));
    }

    // Mostrar un AlertDialog para confirmar la adición del ejercicio a la rutina
    private void mostrarDialogoConfirmacion(String ejercicio, int imagenResId) {
        new AlertDialog.Builder(EjerAbsActivity.this)
                .setTitle("¿Seguro de añadir a la rutina?")
                .setMessage("¿Quieres añadir el ejercicio " + ejercicio + " a tu rutina?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    // Si el usuario acepta, enviar el ejercicio y la imagen como resultado
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra(EXTRA_EJERCICIO, ejercicio);
                    resultIntent.putExtra(EXTRA_IMAGEN, imagenResId);
                    setResult(RESULT_OK, resultIntent);
                    finish(); // Cerrar la actividad actual
                })
                .setNegativeButton("No", null)
                .show();
    }
}