package com.example.myapplication_phoenix_journey;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AgregarDatosActivity extends AppCompatActivity {

    private EditText pesoInput;
    private EditText alturaInput;
    private EditText pechoInput;
    private EditText cinturaInput;
    private EditText brazoInput;
    private Button guardarDatosButton;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Eliminar la ActionBar (nombre de la clase en la parte superior)
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Hacer que la actividad ocupe toda la pantalla (sin barra de estado)
        getWindow().setFlags(
                android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        setContentView(R.layout.activity_agregar_datos);

        // Inicializar los campos
        pesoInput = findViewById(R.id.peso_input);
        alturaInput = findViewById(R.id.altura_input);
        pechoInput = findViewById(R.id.pecho_input);
        cinturaInput = findViewById(R.id.cintura_input);
        brazoInput = findViewById(R.id.brazo_input);
        guardarDatosButton = findViewById(R.id.guardar_datos_button);
        backButton = findViewById(R.id.back_button);

        // Agregar unidades al perder el foco
        addUnitOnFocusLost(pesoInput, " kg");
        addUnitOnFocusLost(alturaInput, " cm");
        addUnitOnFocusLost(pechoInput, " cm");
        addUnitOnFocusLost(cinturaInput, " cm");
        addUnitOnFocusLost(brazoInput, " cm");

        // Configuración del botón guardar
        guardarDatosButton.setOnClickListener(v -> {
            // Obtener los valores de los campos
            String peso = pesoInput.getText().toString();
            String altura = alturaInput.getText().toString();
            String pecho = pechoInput.getText().toString();
            String cintura = cinturaInput.getText().toString();
            String brazo = brazoInput.getText().toString();

            // Verificar si los campos están vacíos
            if (peso.isEmpty() || altura.isEmpty() || pecho.isEmpty() || cintura.isEmpty() || brazo.isEmpty()) {
                Toast.makeText(AgregarDatosActivity.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            } else {
                // Guardar los datos o continuar con la lógica que necesites
                Toast.makeText(AgregarDatosActivity.this, "Datos guardados", Toast.LENGTH_SHORT).show();

                // Crear un Intent para ir a la clase SeleccionNivel
                Intent intent = new Intent(AgregarDatosActivity.this, SeleccionNivelActivity.class);
                startActivity(intent);

                // Opcionalmente, puedes terminar esta actividad para que no vuelva a la pantalla anterior
                finish();
            }
        });

        // Configuración del botón de atrás
        backButton.setOnClickListener(v -> {
            // Crear una intención para ir a la actividad anterior
            Intent intent = new Intent(AgregarDatosActivity.this, ObjetivoPrincipalActivity.class);
            startActivity(intent); // Iniciar la actividad
            finish(); // Cerrar la actividad actual
        });
    }

    // Método para agregar las unidades cuando se pierde el foco
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
}
