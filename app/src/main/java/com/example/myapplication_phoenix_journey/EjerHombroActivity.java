package com.example.myapplication_phoenix_journey;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class EjerHombroActivity extends AppCompatActivity {

    // Constantes para los nombres de los extras
    public static final String EXTRA_EJERCICIO = "EJERCICIO";
    public static final String EXTRA_IMAGEN = "IMAGEN";

    private ImageButton backButton;
    private LinearLayout btnMilitarBarra, btnLateralesManc, btnLateralesPolea, btnHombroPost, btnMilitarManc, btnMilitarMaq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejer_hombro);

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

        // Inicializar el botón "Atrás"
        inicializarBotonAtras();

        // Inicializar los botones de ejercicios de hombro
        inicializarBotonesEjercicios();
    }


    // Inicializar el botón "Atrás"
    private void inicializarBotonAtras() {
        backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> {
            // Devolver un resultado cancelado si el usuario presiona el botón de retroceso
            setResult(RESULT_CANCELED);
            finish(); // Cerrar la actividad actual
        });
    }

    // Inicializar los botones de los ejercicios de hombro
    private void inicializarBotonesEjercicios() {
        btnMilitarBarra = findViewById(R.id.militar_barra);
        btnLateralesManc = findViewById(R.id.laterales_manc);
        btnLateralesPolea = findViewById(R.id.laterales_polea);
        btnHombroPost = findViewById(R.id.hombro_post);
        btnMilitarManc = findViewById(R.id.militar_manc);
        btnMilitarMaq = findViewById(R.id.militar_maq);

        // Configurar acciones para los botones de los ejercicios
        configurarBotonEjercicio(btnMilitarBarra, "Press Militar con Barra", R.drawable.militar_barra);
        configurarBotonEjercicio(btnLateralesManc, "Elevaciones Laterales con Mancuernas", R.drawable.laterales_manc);
        configurarBotonEjercicio(btnLateralesPolea, "Elevaciones Laterales en Polea", R.drawable.laterales_polea);
        configurarBotonEjercicio(btnHombroPost, "Elevaciones Posteriores con Mancuernas", R.drawable.hombro_post);
        configurarBotonEjercicio(btnMilitarManc, "Press Militar con Mancuernas", R.drawable.militar_manc);
        configurarBotonEjercicio(btnMilitarMaq, "Press Militar en Máquina", R.drawable.militar_maquina);
    }

    // Método que centraliza la configuración de los botones de los ejercicios
    private void configurarBotonEjercicio(LinearLayout boton, final String ejercicio, final int imagenResId) {
        boton.setOnClickListener(v -> mostrarDialogoConfirmacion(ejercicio, imagenResId));
    }

    // Mostrar un AlertDialog para confirmar la adición del ejercicio a la rutina
    private void mostrarDialogoConfirmacion(String ejercicio, int imagenResId) {
        new AlertDialog.Builder(EjerHombroActivity.this)
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