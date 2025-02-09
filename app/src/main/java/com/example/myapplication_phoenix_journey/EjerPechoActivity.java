package com.example.myapplication_phoenix_journey;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class EjerPechoActivity extends AppCompatActivity {

    public static final String EXTRA_EJERCICIO = "EJERCICIO";
    public static final String EXTRA_IMAGEN = "IMAGEN";

    private ImageButton backButton;
    private LinearLayout btnPressBanca, btnFondos, btnPressInclinado, btnAperturas, btnFlexiones, btnInclinadoSmith, btnPlanoManc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejer_pecho);

        ocultarActionBar();
        configurarPantallaCompleta();
        inicializarBotonAtras();
        inicializarBotonesEjercicios();
    }

    private void ocultarActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    private void configurarPantallaCompleta() {
        getWindow().setFlags(
                android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
    }

    private void inicializarBotonAtras() {
        backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> finish());
    }

    private void inicializarBotonesEjercicios() {
        btnPressBanca = findViewById(R.id.press_banca);
        btnFondos = findViewById(R.id.fondos);
        btnPressInclinado = findViewById(R.id.inclinado);
        btnAperturas = findViewById(R.id.aperturas);
        btnFlexiones = findViewById(R.id.flexiones);
        btnInclinadoSmith = findViewById(R.id.inclinado_smith);
        btnPlanoManc = findViewById(R.id.plano_manc);

        configurarBotonEjercicio(btnPressBanca, "Press de Banca",R.drawable.plano);
        configurarBotonEjercicio(btnFondos, "Fondos",R.drawable.fondos);
        configurarBotonEjercicio(btnPressInclinado, "Press Inclinado",R.drawable.inclinado);
        configurarBotonEjercicio(btnAperturas, "Aperturas en Polea",R.drawable.aperturas);
        configurarBotonEjercicio(btnFlexiones, "Flexiones",R.drawable.flexiones);
        configurarBotonEjercicio(btnInclinadoSmith, "Press Inclinado en Máquina Smith",R.drawable.incli_smith);
        configurarBotonEjercicio(btnPlanoManc, "Press Plano con Mancuernas",R.drawable.plano_manc);
    }

    // Método que centraliza la configuración de los botones de los ejercicios
    private void configurarBotonEjercicio(LinearLayout boton, final String ejercicio, final int imagenResId) {
        boton.setOnClickListener(v -> mostrarDialogoConfirmacion(ejercicio, imagenResId));
    }

    private void mostrarDialogoConfirmacion(String ejercicio, int imagenResId) {
        new AlertDialog.Builder(EjerPechoActivity.this)
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
