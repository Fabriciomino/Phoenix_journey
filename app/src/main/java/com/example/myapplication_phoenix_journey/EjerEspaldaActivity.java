package com.example.myapplication_phoenix_journey;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class EjerEspaldaActivity extends AppCompatActivity {

    // Constantes para los nombres de los extras
    public static final String EXTRA_EJERCICIO = "EJERCICIO";
    public static final String EXTRA_IMAGEN = "IMAGEN";

    private ImageButton backButton;
    private LinearLayout btnJalones, btnRemo, btnPullOver, btnGironda, btnRemoT, btnDominadas, btnRemoMaquina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejer_espalda);

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
        inicializarBotonAtras();
        inicializarBotonesEjercicios();
    }

    private void inicializarBotonAtras() {
        backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> finish());
    }

    private void inicializarBotonesEjercicios() {
        btnJalones = findViewById(R.id.jalon);
        btnRemo = findViewById(R.id.remo);
        btnPullOver = findViewById(R.id.pull_over);
        btnGironda = findViewById(R.id.gironda);
        btnRemoT = findViewById(R.id.remoT);
        btnDominadas = findViewById(R.id.dominadas);
        btnRemoMaquina = findViewById(R.id.remo_maquina);

        // Configurar acciones para cada botón
        configurarBotonEjercicio(btnJalones, "Jalón al Pecho", R.drawable.jalon);
        configurarBotonEjercicio(btnRemo, "Remo con Barra", R.drawable.remo);
        configurarBotonEjercicio(btnPullOver, "Pull Over", R.drawable.pull_over);
        configurarBotonEjercicio(btnGironda, "Remo Gironda", R.drawable.gironda);
        configurarBotonEjercicio(btnRemoT, "Remo-T", R.drawable.remot);
        configurarBotonEjercicio(btnDominadas, "Dominadas", R.drawable.dominadas);
        configurarBotonEjercicio(btnRemoMaquina, "Remo en Máquina", R.drawable.remo_maquina);
    }

    private void configurarBotonEjercicio(LinearLayout boton, final String ejercicio, final int imagenResId) {
        boton.setOnClickListener(v -> mostrarDialogoConfirmacion(ejercicio, imagenResId));
    }

    private void mostrarDialogoConfirmacion(String ejercicio, int imagenResId) {
        new AlertDialog.Builder(EjerEspaldaActivity.this)
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