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

public class NutrientesActivityDefinicion extends AppCompatActivity {

    private EditText proteinasInput, carbosInput, grasasInput, grasasTotalesInput;
    private Button almacenarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_nutrientes_definicion);

        proteinasInput = findViewById(R.id.proteinas_input);
        carbosInput = findViewById(R.id.carbos_input);
        grasasInput = findViewById(R.id.grasas_input);
        grasasTotalesInput = findViewById(R.id.grasas_totales_input);

        almacenarButton = findViewById(R.id.almacenar_button);

        addUnitOnFocusLost(proteinasInput, " g");
        addUnitOnFocusLost(carbosInput, " g");
        addUnitOnFocusLost(grasasInput, " g");
        addUnitOnFocusLost(grasasTotalesInput, " kcal");

        almacenarButton.setOnClickListener(v -> {
            String proteinas = proteinasInput.getText().toString();
            String carbohidratos = carbosInput.getText().toString();
            String grasas = grasasInput.getText().toString();
            String grasasTotales = grasasTotalesInput.getText().toString();

            if (proteinas.isEmpty() || carbohidratos.isEmpty() || grasas.isEmpty() || grasasTotales.isEmpty()) {
                Toast.makeText(NutrientesActivityDefinicion.this, "Por favor ingresa todos los valores", Toast.LENGTH_SHORT).show();
            } else {
                MiBaseDeDatos dbHelper = new MiBaseDeDatos(NutrientesActivityDefinicion.this);
                int usuarioId = dbHelper.obtenerUsuarioIdActivo();

                if (usuarioId != -1) {
                    dbHelper.insertarNutrientes(usuarioId, proteinas, carbohidratos, grasas, grasasTotales);
                    Toast.makeText(NutrientesActivityDefinicion.this, "Datos almacenados con éxito", Toast.LENGTH_SHORT).show();

                    proteinasInput.setText("");
                    carbosInput.setText("");
                    grasasInput.setText("");
                    grasasTotalesInput.setText("");
                } else {
                    Toast.makeText(NutrientesActivityDefinicion.this, "No hay usuario activo", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

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
