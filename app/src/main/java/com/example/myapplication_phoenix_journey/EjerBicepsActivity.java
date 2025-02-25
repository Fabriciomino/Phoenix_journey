package com.example.myapplication_phoenix_journey;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class EjerBicepsActivity extends AppCompatActivity {

    // Constantes para los nombres de los extras
    public static final String EXTRA_EJERCICIO = "EJERCICIO";
    public static final String EXTRA_IMAGEN = "IMAGEN";

    private ImageButton backButton;
    private LinearLayout btnCurlBarras, btnCurlMancuernas, btnMartillo, btnConcentrado, btnConcentradoPolea, btnInclinado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejer_biceps);

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

        // Inicialización de los elementos de la interfaz
        backButton = findViewById(R.id.back_button);
        btnCurlBarras = findViewById(R.id.curl_barras);
        btnCurlMancuernas = findViewById(R.id.curl_mancuernas);
        btnMartillo = findViewById(R.id.martillo);
        btnConcentrado = findViewById(R.id.concentrado);
        btnConcentradoPolea = findViewById(R.id.concentrado_polea);
        btnInclinado = findViewById(R.id.curl_inclinado);


        // Configuración de los botones para los ejercicios
        configurarBoton(btnCurlBarras, "Curl con barra Z", R.drawable.predicador);
        configurarBoton(btnCurlMancuernas, "Curl con mancuernas", R.drawable.curl_manc);
        configurarBoton(btnMartillo, "Curl martillo", R.drawable.martillo);
        configurarBoton(btnConcentrado, "Curl concentrado", R.drawable.curl_conc);
        configurarBoton(btnConcentradoPolea, "Curl concentrado con polea", R.drawable.concentrado_polea);
        configurarBoton(btnInclinado, "Curl inclinado", R.drawable.curl_incli);


        // Configuración del botón de "Atrás"
        if (backButton != null) {
            backButton.setOnClickListener(v -> finish());
        }
    }

    private void configurarBoton(LinearLayout boton, final String ejercicio, final int imagenResId) {
        if (boton != null) {
            boton.setOnClickListener(v -> mostrarDialogoConfirmacion(ejercicio, imagenResId));
        }
    }

    private void mostrarDialogoConfirmacion(String ejercicio, int imagenResId) {
        new AlertDialog.Builder(EjerBicepsActivity.this)
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