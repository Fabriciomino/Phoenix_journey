package com.example.myapplication_phoenix_journey;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProgresoCorporalActivity extends AppCompatActivity {

    private EditText pesoInput;
    private EditText alturaInput;
    private EditText pechoInput;
    private EditText cinturaInput;
    private EditText brazoInput;
    private Button guardarDatosButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prog_corporal);

        // Eliminar la ActionBar (nombre de la clase en la parte superior)
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Configurar la ventana para un diseño de pantalla completa
        getWindow().setFlags(
                android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        // Inicializar vistas
        pesoInput = findViewById(R.id.peso_input);
        alturaInput = findViewById(R.id.altura_input);
        pechoInput = findViewById(R.id.pecho_input);
        cinturaInput = findViewById(R.id.cintura_input);
        brazoInput = findViewById(R.id.brazo_input);
        guardarDatosButton = findViewById(R.id.guardar_datos_button);

        // Agregar unidades al perder el foco
        addUnitOnFocusLost(pesoInput, " kg");
        addUnitOnFocusLost(alturaInput, " cm");
        addUnitOnFocusLost(pechoInput, " cm");
        addUnitOnFocusLost(cinturaInput, " cm");
        addUnitOnFocusLost(brazoInput, " cm");

        // Configurar botón "Guardar Datos"
        guardarDatosButton.setOnClickListener(v -> {
            // Guardar los datos ingresados en SharedPreferences
            saveData();

            // Mostrar el mensaje de datos guardados
            Toast.makeText(ProgresoCorporalActivity.this, "Datos modificados correctamente", Toast.LENGTH_SHORT).show();
        });

        // Configurar botón "Atrás"
        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProgresoCorporalActivity.this, MenuActivity.class);
            startActivity(intent);
            finish();
        });
    }

    // Método para agregar unidades automáticamente al perder el foco
    private void addUnitOnFocusLost(EditText editText, String unit) {
        editText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) { // Solo se activa cuando se pierde el foco
                String text = editText.getText().toString().trim();

                // Verificar si no contiene ya las unidades y no está vacío
                if (!text.isEmpty() && !text.endsWith(unit)) {
                    editText.setText(text + unit);
                }
            }
        });
    }

    // Método para guardar los datos en SharedPreferences
    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("ProgresoCorporal", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Guardar los datos de los EditText
        editor.putString("peso", pesoInput.getText().toString());
        editor.putString("altura", alturaInput.getText().toString());
        editor.putString("pecho", pechoInput.getText().toString());
        editor.putString("cintura", cinturaInput.getText().toString());
        editor.putString("brazo", brazoInput.getText().toString());

        // Aplicar los cambios
        editor.apply();
    }
}
