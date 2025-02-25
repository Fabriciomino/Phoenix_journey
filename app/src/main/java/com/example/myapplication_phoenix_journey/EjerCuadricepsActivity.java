package com.example.myapplication_phoenix_journey;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class EjerCuadricepsActivity extends AppCompatActivity {

    // Constantes para los nombres de los extras
    public static final String EXTRA_EJERCICIO = "EJERCICIO";
    public static final String EXTRA_IMAGEN = "IMAGEN";

    private ImageButton backButton;
    private LinearLayout btnSentadilla, btnPrensa, btnExtensiones, btnHackSquat, btnSentadillaSmith, btnStepUps, btnMaquinaAbd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejer_cuadriceps);

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
        btnSentadilla = findViewById(R.id.sentadilla);
        btnPrensa = findViewById(R.id.prensa);
        btnExtensiones = findViewById(R.id.extensiones_cuads);
        btnHackSquat = findViewById(R.id.hack_squat);
        btnSentadillaSmith = findViewById(R.id.sentadilla_smith);
        btnStepUps = findViewById(R.id.step_ups);
        btnMaquinaAbd = findViewById(R.id.maquina_abd);

        configurarBotonEjercicio(btnSentadilla, "Sentadilla", R.drawable.sentadilla);
        configurarBotonEjercicio(btnPrensa, "Prensa de Piernas", R.drawable.prensa);
        configurarBotonEjercicio(btnExtensiones, "Extensiones de Cuádriceps", R.drawable.extension_cuads);
        configurarBotonEjercicio(btnHackSquat, "Hack-squat", R.drawable.hack_squat);
        configurarBotonEjercicio(btnSentadillaSmith, "Sentadilla en Máquina Smith", R.drawable.sentadilla_smith);
        configurarBotonEjercicio(btnStepUps, "Step-ups", R.drawable.step_ups);
        configurarBotonEjercicio(btnMaquinaAbd, "Abductor en Máquina", R.drawable.maquina_abd);
    }

    private void configurarBotonEjercicio(LinearLayout boton, final String ejercicio, final int imagenResId) {
        boton.setOnClickListener(v -> mostrarDialogoConfirmacion(ejercicio, imagenResId));
    }

    private void mostrarDialogoConfirmacion(String ejercicio, int imagenResId) {
        new AlertDialog.Builder(EjerCuadricepsActivity.this)
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