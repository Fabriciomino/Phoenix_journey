package com.example.myapplication_phoenix_journey;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ObjetivoPrincipalActivity extends AppCompatActivity {

    private Button aumentoMuscularButton;
    private Button perdidaPesoButton;
    private Button gananciaFuerzaButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objetivo_principal);
        // Eliminar la ActionBar (nombre de la clase en la parte superior)
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        // Configurar la ventana para un diseño de pantalla completa
        getWindow().setFlags(
                android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );


        aumentoMuscularButton = findViewById(R.id.aumento_muscular_button);
        perdidaPesoButton = findViewById(R.id.perdida_peso_button);
        gananciaFuerzaButton = findViewById(R.id.ganancia_fuerza_button);

        aumentoMuscularButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAgregarDatosActivity();
            }
        });

        perdidaPesoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAgregarDatosActivity();
            }
        });

        gananciaFuerzaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAgregarDatosActivity();
            }
        });
    }

    private void openAgregarDatosActivity() {
        Intent intent = new Intent(ObjetivoPrincipalActivity.this, AgregarDatosActivity.class);
        startActivity(intent);
    }
}
