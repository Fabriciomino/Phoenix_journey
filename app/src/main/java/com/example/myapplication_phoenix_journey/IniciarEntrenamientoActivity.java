package com.example.myapplication_phoenix_journey;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class IniciarEntrenamientoActivity extends AppCompatActivity {

    private ImageButton backButton;
    private Button iniciarEntrenamientoButton;
    private Button anadirDescansoButton;
    private EditText duracionDescansoInput;
    private TextView restoTitle;
    private TextView cronometroText;
    private TextView cuentaAtrasText;

    private boolean cronometroActivo = false;
    private long tiempoEntrenamiento = 0; // En segundos
    private Handler handler = new Handler();
    private CountDownTimer cuentaAtras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_entrenamiento); // Asegúrate de que el nombre del layout coincida
        // Eliminar la ActionBar (nombre de la clase en la parte superior)
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Configurar la ventana para un diseño de pantalla completa
        getWindow().setFlags(
                android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        // Inicialización de las vistas
        backButton = findViewById(R.id.back_button);
        iniciarEntrenamientoButton = findViewById(R.id.iniciar_entrenamiento_button);
        anadirDescansoButton = findViewById(R.id.anadir_descanso_button);
        duracionDescansoInput = findViewById(R.id.duracion_descanso_input);
        restoTitle = findViewById(R.id.resto_title);
        cronometroText = findViewById(R.id.cronometro_text);
        cuentaAtrasText = findViewById(R.id.cuenta_atras_text);

        // Configuración de los listeners
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica para el botón de atrás
                onBackPressed();
            }
        });

        iniciarEntrenamientoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica para iniciar el entrenamiento
                iniciarEntrenamiento();
            }
        });

        anadirDescansoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica para añadir el descanso
                anadirDescanso();
            }
        });
    }

    private void iniciarEntrenamiento() {
        if (!cronometroActivo) {
            // Iniciar cronómetro ascendente
            cronometroActivo = true;
            iniciarEntrenamientoButton.setText("Detener Entrenamiento");

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (cronometroActivo) {
                        tiempoEntrenamiento++;
                        actualizarCronometro();
                        handler.postDelayed(this, 1000); // Actualizar cada segundo
                    }
                }
            }, 1000);

            restoTitle.setText("Entrenamiento iniciado.");
        } else {
            // Detener cronómetro
            cronometroActivo = false;
            handler.removeCallbacksAndMessages(null); // Detener el handler
            iniciarEntrenamientoButton.setText("Iniciar Entrenamiento");

            restoTitle.setText("Entrenamiento detenido.");
        }
    }

    private void actualizarCronometro() {
        int minutos = (int) (tiempoEntrenamiento / 60);
        int segundos = (int) (tiempoEntrenamiento % 60);
        cronometroText.setText(String.format("%02d:%02d", minutos, segundos));
    }

    private void anadirDescanso() {
        String duracionDescanso = duracionDescansoInput.getText().toString();

        if (!duracionDescanso.isEmpty()) {
            int minutosDescanso = Integer.parseInt(duracionDescanso);

            // Iniciar la cuenta atrás para el descanso
            cuentaAtras = new CountDownTimer(minutosDescanso * 60000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    // Actualizar la cuenta atrás
                    int minutosRestantes = (int) (millisUntilFinished / 60000);
                    int segundosRestantes = (int) (millisUntilFinished % 60000 / 1000);
                    cuentaAtrasText.setText(String.format("%02d:%02d", minutosRestantes, segundosRestantes));
                }

                @Override
                public void onFinish() {
                    cuentaAtrasText.setText("¡Descanso finalizado!");
                }
            }.start();

            restoTitle.setText("Descanso de " + duracionDescanso + " minutos iniciado.");
        } else {
            restoTitle.setText("Por favor, introduce una duración de descanso.");
        }
    }
}
