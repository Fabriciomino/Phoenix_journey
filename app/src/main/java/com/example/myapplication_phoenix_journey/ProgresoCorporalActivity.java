package com.example.myapplication_phoenix_journey;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication_phoenix_journey.basesdedatos.MiBaseDeDatos;

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

        // Configurar botón para ver fotos de progreso
        ImageButton botonVerFotos = findViewById(R.id.boton_ver_fotos);
        botonVerFotos.setOnClickListener(v -> {
            Intent intent = new Intent(ProgresoCorporalActivity.this, FotosProgresoActivity.class);
            startActivity(intent);
        });

        // Obtener el ID del usuario activo
        MiBaseDeDatos dbHelper = new MiBaseDeDatos(ProgresoCorporalActivity.this);
        int usuarioId = dbHelper.obtenerUsuarioIdActivo();

        // Obtener los datos de progreso corporal
        String progreso = dbHelper.obtenerProgresoCorporal(usuarioId);
        if (progreso != null) {
            String[] datos = progreso.split(", ");
            pesoInput.setText(datos[0]);
            alturaInput.setText(datos[1]);
            pechoInput.setText(datos[2]);
            cinturaInput.setText(datos[3]);
            brazoInput.setText(datos[4]);
        }

        // Configurar botón "Guardar Datos"
        guardarDatosButton.setOnClickListener(v -> {
            String peso = pesoInput.getText().toString();
            String altura = alturaInput.getText().toString();
            String pecho = pechoInput.getText().toString();
            String cintura = cinturaInput.getText().toString();
            String brazo = brazoInput.getText().toString();

            // Actualizar los datos en la base de datos
            dbHelper.insertarActualizarProgresoCorporal(usuarioId, peso, altura, pecho, cintura, brazo);

            Toast.makeText(ProgresoCorporalActivity.this, "Datos modificados correctamente", Toast.LENGTH_SHORT).show();
        });
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
