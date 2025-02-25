package com.example.myapplication_phoenix_journey;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

public class AñadirEjercicioActivity extends AppCompatActivity {

    private ImageButton backButton;
    private LinearLayout btnHombro, btnPecho, btnEspalda, btnAbs, btnCuadriceps, btnBiceps, btnTriceps, btnFemorales;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_ejercicio);

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

        // Inicializar los botones de los diferentes músculos
        inicializarBotonesMúsculos();
    }

    // Inicializar el botón "Atrás"
    private void inicializarBotonAtras() {
        backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> {
            // Devolver un resultado cancelado
            setResult(RESULT_CANCELED);
            finish(); // Cerrar la actividad
        });
    }

    // Inicializar los botones de los diferentes músculos
    private void inicializarBotonesMúsculos() {
        btnHombro = findViewById(R.id.btn_hombro);
        btnPecho = findViewById(R.id.btn_pecho);
        btnEspalda = findViewById(R.id.btn_espalda);
        btnAbs = findViewById(R.id.btn_abs);
        btnCuadriceps = findViewById(R.id.btn_cuadriceps);
        btnBiceps = findViewById(R.id.btn_biceps);
        btnTriceps = findViewById(R.id.btn_triceps);
        btnFemorales = findViewById(R.id.btn_femorales);

        // Configurar las acciones de los botones
        btnHombro.setOnClickListener(v -> {
            // Devolver el nombre de la actividad correspondiente al hombro
            devolverResultado("EjerHombroActivity");
        });

        btnPecho.setOnClickListener(v -> {
            // Devolver el nombre de la actividad correspondiente al pecho
            devolverResultado("EjerPechoActivity");
        });

        btnEspalda.setOnClickListener(v -> {
            // Devolver el nombre de la actividad correspondiente a la espalda
            devolverResultado("EjerEspaldaActivity");
        });

        btnAbs.setOnClickListener(v -> {
            // Devolver el nombre de la actividad correspondiente al abdomen
            devolverResultado("EjerAbsActivity");
        });

        btnCuadriceps.setOnClickListener(v -> {
            // Devolver el nombre de la actividad correspondiente al cuadriceps
            devolverResultado("EjerCuadricepsActivity");
        });

        btnBiceps.setOnClickListener(v -> {
            // Devolver el nombre de la actividad correspondiente al bíceps
            devolverResultado("EjerBicepsActivity");
        });

        btnTriceps.setOnClickListener(v -> {
            // Devolver el nombre de la actividad correspondiente al tríceps
            devolverResultado("EjerTricepsActivity");
        });

        btnFemorales.setOnClickListener(v -> {
            // Devolver el nombre de la actividad correspondiente a los femorales
            devolverResultado("EjerFemoralesActivity");
        });
    }

    // Método para devolver el nombre de la actividad correspondiente
    private void devolverResultado(String nombreActividad) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("ACTIVIDAD", nombreActividad);
        setResult(RESULT_OK, resultIntent);
        finish(); // Cerrar la actividad
    }
}