package com.example.myapplication_phoenix_journey;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class EjerFemoralesActivity extends AppCompatActivity {

    // Constantes para los nombres de los extras
    public static final String EXTRA_EJERCICIO = "EJERCICIO";
    public static final String EXTRA_IMAGEN = "IMAGEN";

    private ImageButton backButton;
    private LinearLayout btnCurlAcostado, btnCurlDePie, btnCurlSentado, btnPesoMuertoRumano;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejer_femorales);

        // Eliminar la ActionBar (nombre de la clase en la parte superior)
        ocultarActionBar();

        // Configurar la ventana para un diseño de pantalla completa
        configurarPantallaCompleta();

        // Inicializar el botón "Atrás"
        inicializarBotonAtras();

        // Inicializar los botones de ejercicios de femorales
        inicializarBotonesEjercicios();
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

    // Inicializar el botón "Atrás"
    private void inicializarBotonAtras() {
        backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> finish()); // Fin de la actividad actual
    }

    // Inicializar los botones de los ejercicios de femorales
    private void inicializarBotonesEjercicios() {
        btnCurlAcostado = findViewById(R.id.curl_acostado);
        btnCurlDePie = findViewById(R.id.curl_de_pie);
        btnCurlSentado = findViewById(R.id.curl_sentado);
        btnPesoMuertoRumano = findViewById(R.id.peso_muerto_rumano);

        // Configurar acciones para los botones de los ejercicios
        configurarBotonEjercicio(btnCurlAcostado, "Curl femoral acostado", R.drawable.flexion_femoral_tumbado);
        configurarBotonEjercicio(btnCurlDePie, "Curl femoral de pie", R.drawable.flexion_femoral_depie);
        configurarBotonEjercicio(btnCurlSentado, "Curl femoral sentado", R.drawable.flexion_femoral_sentado);
        configurarBotonEjercicio(btnPesoMuertoRumano, "Peso muerto rumano con mancuernas", R.drawable.peso_muerto_rumano_manc);
    }

    // Método que centraliza la configuración de los botones de los ejercicios
    private void configurarBotonEjercicio(LinearLayout boton, final String ejercicio, final int imagenResId) {
        boton.setOnClickListener(v -> mostrarDialogoConfirmacion(ejercicio, imagenResId));
    }

    // Mostrar un AlertDialog para confirmar la adición del ejercicio a la rutina
    private void mostrarDialogoConfirmacion(String ejercicio, int imagenResId) {
        new AlertDialog.Builder(EjerFemoralesActivity.this)
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