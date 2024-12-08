package com.example.myapplication_phoenix_journey;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PhoenixJourneyActivity extends AppCompatActivity {

    // Declaración de los elementos del layout
    private ImageButton bancaButton;
    private ImageButton sentadillaButton;
    private ImageButton pesomuertoButton;
    private ImageButton pasosButton;
    private ImageButton entrenamientoButton;
    private ImageButton alimentacionButton;
    private ImageButton amigosButton;

    private ImageButton backButton;  // Botón Atrás

    private TextView bancaText;
    private TextView sentadillaText;
    private TextView pesomuertoText;
    private TextView pasosText;
    private TextView entrenamientoText;
    private TextView alimentacionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phoenix_journey);
        // Eliminar la ActionBar (nombre de la clase en la parte superior)
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        // Configurar la ventana para un diseño de pantalla completa
        getWindow().setFlags(
                android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        // Inicializar las vistas
        bancaButton = findViewById(R.id.banca_button);
        sentadillaButton = findViewById(R.id.sentadilla_button);
        pesomuertoButton = findViewById(R.id.pesomuerto_button);
        pasosButton = findViewById(R.id.pasos_button);
        entrenamientoButton = findViewById(R.id.entrenamiento_button);
        alimentacionButton = findViewById(R.id.alimentacion_button);
        backButton = findViewById(R.id.back_button);
        amigosButton = findViewById(R.id.amigos_button);

        bancaText = findViewById(R.id.banca);
        sentadillaText = findViewById(R.id.sentadilla);
        pesomuertoText = findViewById(R.id.pesomuerto);
        pasosText = findViewById(R.id.pasos);
        entrenamientoText = findViewById(R.id.entrenamiento);
        alimentacionText = findViewById(R.id.alimentacion);

        // Configurar botones para que inicien ocultos
        bancaButton.setVisibility(View.GONE);
        sentadillaButton.setVisibility(View.GONE);
        pesomuertoButton.setVisibility(View.GONE);
        pasosButton.setVisibility(View.GONE);
        entrenamientoButton.setVisibility(View.GONE);
        alimentacionButton.setVisibility(View.GONE);

        // Configurar el botón Atrás para regresar a MenuActivity
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PhoenixJourneyActivity.this, MenuActivity.class);
                startActivity(intent);
                finish();  // Finaliza la actividad actual para que no quede en la pila de actividades
            }
        });
        amigosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PhoenixJourneyActivity.this, AmigosActivity.class);
                startActivity(intent);
            }
        });

    }

    public void toggleBancaButton(View view) {
        if (bancaButton.getVisibility() == View.GONE) {
            bancaButton.setVisibility(View.VISIBLE);
        } else {
            bancaButton.setVisibility(View.GONE);
        }
    }

    public void toggleSentadillaButton(View view) {
        if (sentadillaButton.getVisibility() == View.GONE) {
            sentadillaButton.setVisibility(View.VISIBLE);
        } else {
            sentadillaButton.setVisibility(View.GONE);
        }
    }

    public void togglePesomuertoButton(View view) {
        if (pesomuertoButton.getVisibility() == View.GONE) {
            pesomuertoButton.setVisibility(View.VISIBLE);
        } else {
            pesomuertoButton.setVisibility(View.GONE);
        }
    }

    public void togglePasosButton(View view) {
        if (pasosButton.getVisibility() == View.GONE) {
            pasosButton.setVisibility(View.VISIBLE);
        } else {
            pasosButton.setVisibility(View.GONE);
        }
    }

    public void toggleEntrenamientoButton(View view) {
        if (entrenamientoButton.getVisibility() == View.GONE) {
            entrenamientoButton.setVisibility(View.VISIBLE);
        } else {
            entrenamientoButton.setVisibility(View.GONE);
        }
    }

    public void toggleAlimentacionButton(View view) {
        if (alimentacionButton.getVisibility() == View.GONE) {
            alimentacionButton.setVisibility(View.VISIBLE);
        } else {
            alimentacionButton.setVisibility(View.GONE);
        }
    }
}
