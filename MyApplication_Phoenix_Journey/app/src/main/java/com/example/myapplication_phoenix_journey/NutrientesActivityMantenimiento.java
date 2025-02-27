package com.example.myapplication_phoenix_journey;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class NutrientesActivityMantenimiento extends AppCompatActivity {

    private EditText proteinasInput, carbosInput, grasasInput, grasasTotalesInput;
    private Button almacenarButton;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Configurar la ventana para un diseño de pantalla completa
        getWindow().setFlags(
                android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        setContentView(R.layout.activity_nutrientes_mantenimiento);

        // Inicialización de los campos de entrada
        proteinasInput = findViewById(R.id.proteinas_input);
        carbosInput = findViewById(R.id.carbos_input);
        grasasInput = findViewById(R.id.grasas_input);
        grasasTotalesInput = findViewById(R.id.grasas_totales_input);

        // Inicialización de los botones
        almacenarButton = findViewById(R.id.almacenar_button);
        backButton = findViewById(R.id.back_button);

        // Añadir unidades a los campos cuando el foco se pierde
        addUnitOnFocusLost(proteinasInput, " g");
        addUnitOnFocusLost(carbosInput, " g");
        addUnitOnFocusLost(grasasInput, " g");
        addUnitOnFocusLost(grasasTotalesInput, " kcal");

        // Acción del botón de almacenamiento
        almacenarButton.setOnClickListener(v -> {
            // Obtener los datos de los campos
            String proteinas = proteinasInput.getText().toString();
            String carbohidratos = carbosInput.getText().toString();
            String grasas = grasasInput.getText().toString();
            String grasasTotales = grasasTotalesInput.getText().toString();

            // Validación de los campos
            if (proteinas.isEmpty() || carbohidratos.isEmpty() || grasas.isEmpty() || grasasTotales.isEmpty()) {
                Toast.makeText(NutrientesActivityMantenimiento.this, "Por favor ingresa todos los valores", Toast.LENGTH_SHORT).show();
            } else {
                // Guardar los datos en SharedPreferences
                storeNutrientesData(proteinas, carbohidratos, grasas, grasasTotales);

                // Mostrar mensaje de éxito
                Toast.makeText(NutrientesActivityMantenimiento.this, "Datos almacenados con éxito", Toast.LENGTH_SHORT).show();

                // Limpiar los campos después de almacenar
                proteinasInput.setText("");
                carbosInput.setText("");
                grasasInput.setText("");
                grasasTotalesInput.setText("");
            }
        });

        // Acción del botón de atrás
        backButton.setOnClickListener(v -> {
            // Regresar a la actividad DietaActivity
            Intent intent = new Intent(NutrientesActivityMantenimiento.this, DietaActivity.class);
            startActivity(intent);
            finish();
        });
    }

    // Método para agregar la unidad al texto cuando el campo pierde el foco
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

    // Método para almacenar los datos de los nutrientes
    private void storeNutrientesData(String proteinas, String carbohidratos, String grasas, String grasasTotales) {
        // Guardar los datos en SharedPreferences
        getSharedPreferences("Nutrientes", MODE_PRIVATE)
                .edit()
                .putString("proteinas", proteinas)
                .putString("carbohidratos", carbohidratos)
                .putString("grasas", grasas)
                .putString("grasasTotales", grasasTotales)
                .apply();
    }
}
