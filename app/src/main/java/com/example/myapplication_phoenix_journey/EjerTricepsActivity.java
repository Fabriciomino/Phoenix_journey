package com.example.myapplication_phoenix_journey;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class EjerTricepsActivity extends AppCompatActivity {
    public static final String EXTRA_EJERCICIO = "EJERCICIO";
    public static final String EXTRA_IMAGEN = "IMAGEN";

    private ImageButton backButton;
    private LinearLayout btnExtensionesCuerda, btnCatana, btnPressFrances, btnFrancesManc, btnExtensionManc, btnFondosMaquina, btnCatanaAlta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejer_triceps);

        // Ocultar la barra de acción si está presente
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Configurar ventana para que ocupe toda la pantalla
        getWindow().setFlags(
                android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        // Configuración del botón "Atrás"
        backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> finish());

        // Vinculación de los botones con sus respectivos IDs
        btnExtensionesCuerda = findViewById(R.id.extensiones_cuerda);
        btnCatana = findViewById(R.id.catana);
        btnPressFrances = findViewById(R.id.press_frances);
        btnFrancesManc = findViewById(R.id.frances_manc);
        btnExtensionManc = findViewById(R.id.extension_manc);
        btnFondosMaquina = findViewById(R.id.fondos_maquina);
        btnCatanaAlta = findViewById(R.id.catana_alta);

        // Configuración de los botones con nombres de los ejercicios
        configurarBotonEjercicio(btnExtensionesCuerda, "Extensiones con cuerda alta",R.drawable.exten_triceps);
        configurarBotonEjercicio(btnCatana, "Extensión catana en cuerda",R.drawable.catana);
        configurarBotonEjercicio(btnPressFrances, "Press francés",R.drawable.frances);
        configurarBotonEjercicio(btnFrancesManc, "Press francés con mancuernas",R.drawable.frances_manc);
        configurarBotonEjercicio(btnExtensionManc, "Extensión de tríceps con mancuerna",R.drawable.triceps_manc);
        configurarBotonEjercicio(btnFondosMaquina, "Fondos en máquina",R.drawable.fondos_maqui);
        configurarBotonEjercicio(btnCatanaAlta, "Extensión catana en cuerda alta",R.drawable.catana_alta);
    }

    // Método que centraliza la configuración de los botones de los ejercicios
    private void configurarBotonEjercicio(LinearLayout boton, final String ejercicio, final int imagenResId) {
        boton.setOnClickListener(v -> mostrarDialogoConfirmacion(ejercicio, imagenResId));
    }

    // Mostrar un AlertDialog para confirmar la adición del ejercicio a la rutina
    private void mostrarDialogoConfirmacion(String ejercicio, int imagenResId) {
        new AlertDialog.Builder(EjerTricepsActivity.this)
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
