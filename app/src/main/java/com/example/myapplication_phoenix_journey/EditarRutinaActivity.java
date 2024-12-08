package com.example.myapplication_phoenix_journey;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EditarRutinaActivity extends AppCompatActivity {

    private TextView daySelectedText;  // TextView para mostrar el día seleccionado
    private TextView savedDataText;   // TextView para mostrar el texto guardado
    private EditText musclesInput;    // EditText para ingresar los músculos
    private Button guardarDatosButton; // Botón para guardar los datos
    private ImageButton backButton;   // Botón de retroceso
    private ImageButton añadirEjercicioButton; // Botón para añadir ejercicio

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Eliminar la ActionBar (nombre de la clase en la parte superior)
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Configurar pantalla completa
        getWindow().setFlags(
                android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_rutina);

        // Inicializar vistas
        daySelectedText = findViewById(R.id.day_selected_text);
        savedDataText = findViewById(R.id.saved_data_text);
        musclesInput = findViewById(R.id.muscles_input);
        guardarDatosButton = findViewById(R.id.guardar_datos_button);
        backButton = findViewById(R.id.back_button);
        añadirEjercicioButton = findViewById(R.id.añadir_ejercicio);

        // Obtener datos del intent
        Intent intent = getIntent();
        String dayName = intent.getStringExtra("DIA_SELECCIONADO");

        // Configurar el día seleccionado si está presente
        if (dayName != null) {
            daySelectedText.setText(dayName);
        }

        // Configurar acción del botón de retroceso
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(EditarRutinaActivity.this, CrearRutinaActivity.class);
                startActivity(backIntent);
                finish(); // Finalizar la actividad actual
            }
        });

        // Configurar acción del botón "Añadir Ejercicio"
        añadirEjercicioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent añadirEjercicioIntent = new Intent(EditarRutinaActivity.this, AñadirEjercicioActivity.class);
                startActivity(añadirEjercicioIntent);
            }
        });

        // Configurar acción del botón "Guardar Datos"
        guardarDatosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputText = musclesInput.getText().toString().trim();
                if (!inputText.isEmpty()) {
                    savedDataText.setText(inputText);
                    musclesInput.setVisibility(View.GONE); // Ocultar el EditText
                } else {
                    savedDataText.setText("No se ingresaron datos.");
                }
            }
        });
    }
}
