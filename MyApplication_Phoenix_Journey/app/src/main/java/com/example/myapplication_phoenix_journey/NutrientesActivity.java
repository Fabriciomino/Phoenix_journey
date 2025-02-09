package com.example.myapplication_phoenix_journey;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class NutrientesActivity extends AppCompatActivity {

    private ImageButton backButton;
    private Button almacenarButton;
    private EditText proteinasInput, carbosInput, grasasInput, grasasTotalesInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Ocultar la barra de acción
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Configurar la ventana para un diseño de pantalla completa
        getWindow().setFlags(
                android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrientes); // Asegúrate de que el layout sea correcto

        // Inicialización de los elementos de la interfaz
        backButton = findViewById(R.id.back_button);
        almacenarButton = findViewById(R.id.almacenar_button);
        proteinasInput = findViewById(R.id.proteinas_input);
        carbosInput = findViewById(R.id.carbos_input);
        grasasInput = findViewById(R.id.grasas_input);
        grasasTotalesInput = findViewById(R.id.grasas_totales_input);

        // Añadir las unidades a los campos de texto cuando se pierde el foco
        addUnitOnFocusLost(proteinasInput, " g");
        addUnitOnFocusLost(carbosInput, " g");
        addUnitOnFocusLost(grasasInput, " g");
        addUnitOnFocusLost(grasasTotalesInput, " kcal");

        // Evento para el botón de Atrás
        backButton.setOnClickListener(v -> {
            // Regresar a la actividad DietaActivity
            startActivity(new Intent(NutrientesActivity.this, DietaActivity.class));
            finish(); // Finaliza la actividad actual
        });

        // Evento para el botón de Almacenar
        almacenarButton.setOnClickListener(v -> {
            // Obtener los valores introducidos
            String proteinas = proteinasInput.getText().toString();
            String carbohidratos = carbosInput.getText().toString();
            String grasas = grasasInput.getText().toString();
            String grasasTotales = grasasTotalesInput.getText().toString();

            // Validación de campos
            if (proteinas.isEmpty() || carbohidratos.isEmpty() || grasas.isEmpty() || grasasTotales.isEmpty()) {
                Toast.makeText(NutrientesActivity.this, "Por favor ingresa todos los valores", Toast.LENGTH_SHORT).show();
            } else {
                // Aquí puedes almacenar los datos o realizar alguna acción adicional
                // Por ejemplo, guardarlos en una base de datos o en preferencias compartidas.

                // Mostrar un mensaje de éxito
                Toast.makeText(NutrientesActivity.this, "Datos almacenados con éxito", Toast.LENGTH_SHORT).show();

                // Limpiar los campos después de almacenar
                proteinasInput.setText("");
                carbosInput.setText("");
                grasasInput.setText("");
                grasasTotalesInput.setText("");
            }
        });
    }

    // Método para agregar la unidad a los campos EditText cuando se pierde el foco
    private void addUnitOnFocusLost(EditText editText, String unit) {
        editText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                String currentText = editText.getText().toString();
                if (!currentText.endsWith(unit)) {
                    editText.setText(currentText + unit);
                }
            }
        });
    }
}
