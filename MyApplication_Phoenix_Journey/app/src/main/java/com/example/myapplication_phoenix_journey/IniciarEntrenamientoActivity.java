package com.example.myapplication_phoenix_journey;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class IniciarEntrenamientoActivity extends AppCompatActivity {

    private TextView mostrarDatosTextView;
    private TextView duracionEntrenamientoTextView;
    private ImageButton backButton;
    private Button iniciarEntrenamientoButton;
    private EditText manualMinutosInput;
    private Button iniciarCronometroButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        getWindow().setFlags(
                android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_entrenamiento);

        // Inicializar vistas
        mostrarDatosTextView = findViewById(R.id.mostrar_datos);
        duracionEntrenamientoTextView = findViewById(R.id.duracion_entrenamiento);
        backButton = findViewById(R.id.back_button);
        iniciarEntrenamientoButton = findViewById(R.id.iniciar_entrenamiento_button);
        manualMinutosInput = findViewById(R.id.manual_minutos_input);
        iniciarCronometroButton = findViewById(R.id.iniciar_cronometro_button);

        // Recibir datos desde el Intent
        Intent intent = getIntent();
        String datosGuardados = intent.getStringExtra("DATOS_GUARDADOS");

        // Mostrar datos si existen
        if (datosGuardados != null && !datosGuardados.isEmpty()) {
            mostrarDatosTextView.setText(datosGuardados);
        } else {
            mostrarDatosTextView.setText("No hay datos guardados.");
        }

        // Acción para el botón de atrás
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Regresa a la actividad anterior
            }
        });

        // Acción para iniciar entrenamiento
        iniciarEntrenamientoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarEntrenamientoButton.setVisibility(View.GONE); // Ocultar botón
                duracionEntrenamientoTextView.setVisibility(View.VISIBLE); // Mostrar cronómetro
                manualMinutosInput.setVisibility(View.VISIBLE); // Mostrar input
                iniciarCronometroButton.setVisibility(View.VISIBLE); // Mostrar botón iniciar
            }
        });

        // Acción para iniciar la cuenta regresiva
        iniciarCronometroButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String minutosStr = manualMinutosInput.getText().toString();

                if (minutosStr.isEmpty()) {
                    Toast.makeText(IniciarEntrenamientoActivity.this, "Ingresa los minutos.", Toast.LENGTH_SHORT).show();
                    return;
                }

                int minutos = Integer.parseInt(minutosStr);
                long tiempoEnMilisegundos = minutos * 60 * 1000;

                new CountDownTimer(tiempoEnMilisegundos, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        long minutosRestantes = millisUntilFinished / (60 * 1000);
                        long segundosRestantes = (millisUntilFinished / 1000) % 60;
                        duracionEntrenamientoTextView.setText(String.format("Duración: %02d:%02d", minutosRestantes, segundosRestantes));
                    }

                    @Override
                    public void onFinish() {
                        duracionEntrenamientoTextView.setText("Entrenamiento terminado!");
                        Toast.makeText(IniciarEntrenamientoActivity.this, "¡Entrenamiento completado!", Toast.LENGTH_SHORT).show();
                    }
                }.start();

                manualMinutosInput.setVisibility(View.GONE); // Ocultar input de minutos
                iniciarCronometroButton.setVisibility(View.GONE); // Ocultar botón iniciar
            }
        });
    }
}
