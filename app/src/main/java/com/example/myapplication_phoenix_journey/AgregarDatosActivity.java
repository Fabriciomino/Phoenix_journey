package com.example.myapplication_phoenix_journey;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication_phoenix_journey.basesdedatos.MiBaseDeDatos;

public class AgregarDatosActivity extends AppCompatActivity {

    private EditText pesoInput;
    private EditText alturaInput;
    private EditText pechoInput;
    private EditText cinturaInput;
    private EditText brazoInput;
    private Button guardarDatosButton;

    private ImageButton backButton; // Botón de retroceso

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Ocultar la barra de acción si está disponible
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Configurar la ventana para que ocupe toda la pantalla
        getWindow().setFlags(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY,
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );
        setContentView(R.layout.activity_agregar_datos);

        // Inicializar vistas
        pesoInput = findViewById(R.id.peso_input);
        alturaInput = findViewById(R.id.altura_input);
        pechoInput = findViewById(R.id.pecho_input);
        cinturaInput = findViewById(R.id.cintura_input);
        brazoInput = findViewById(R.id.brazo_input);
        guardarDatosButton = findViewById(R.id.guardar_datos_button);

        // Agregar unidades automáticamente al perder el foco
        addUnitOnFocusLost(pesoInput, " kg");
        addUnitOnFocusLost(alturaInput, " cm");
        addUnitOnFocusLost(pechoInput, " cm");
        addUnitOnFocusLost(cinturaInput, " cm");
        addUnitOnFocusLost(brazoInput, " cm");

        // Configurar botón "Guardar Datos"
        guardarDatosButton.setOnClickListener(v -> {
            String peso = pesoInput.getText().toString();
            String altura = alturaInput.getText().toString();
            String pecho = pechoInput.getText().toString();
            String cintura = cinturaInput.getText().toString();
            String brazo = brazoInput.getText().toString();

            if (peso.isEmpty() || altura.isEmpty() || pecho.isEmpty() || cintura.isEmpty() || brazo.isEmpty()) {
                Toast.makeText(AgregarDatosActivity.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            } else {
                // Obtener el ID del usuario activo
                MiBaseDeDatos dbHelper = new MiBaseDeDatos(AgregarDatosActivity.this);
                int usuarioId = dbHelper.obtenerUsuarioIdActivo();

                // Insertar o actualizar los datos de progreso corporal
                dbHelper.insertarActualizarProgresoCorporal(usuarioId, peso, altura, pecho, cintura, brazo);

                Toast.makeText(AgregarDatosActivity.this, "Datos guardados", Toast.LENGTH_SHORT).show();

                // Ir a la siguiente actividad
                Intent intent = new Intent(AgregarDatosActivity.this, SeleccionNivelActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Inicializar el botón "Atrás"
        backButton = findViewById(R.id.back_button);

        // Configurar acción del botón "Atrás"
        backButton.setOnClickListener(v -> finish()); // Finalizar la actividad actual y regresar a la anterior
    }

    private void addUnitOnFocusLost(EditText editText, String unit) {
        editText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                String text = editText.getText().toString().trim();
                if (!text.isEmpty() && !text.endsWith(unit)) {
                    editText.setText(text + unit);
                }
            }
        });
    }
}
