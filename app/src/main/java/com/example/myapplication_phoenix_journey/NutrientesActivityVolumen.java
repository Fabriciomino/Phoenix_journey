package com.example.myapplication_phoenix_journey;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication_phoenix_journey.basesdedatos.MiBaseDeDatos;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NutrientesActivityVolumen extends AppCompatActivity {

    private EditText proteinasInput, carbosInput, grasasInput, grasasTotalesInput;
    private Button almacenarButton;

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
        getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_nutrientes_volumen);

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
                Toast.makeText(NutrientesActivityVolumen.this, "Por favor ingresa todos los valores", Toast.LENGTH_SHORT).show();
            } else {
                MiBaseDeDatos dbHelper = new MiBaseDeDatos(NutrientesActivityVolumen.this);
                int usuarioId = dbHelper.obtenerUsuarioIdActivo();

                if (usuarioId != -1) {
                    dbHelper.insertarNutrientes(usuarioId, proteinas, carbohidratos, grasas, grasasTotales, "Volumen");
                    Toast.makeText(NutrientesActivityVolumen.this, "Datos almacenados con éxito", Toast.LENGTH_SHORT).show();

                    // Generar PDF automáticamente
                    String[] informacionUsuario = dbHelper.obtenerInformacionUsuario(usuarioId);
                    String informacionNutrientes = dbHelper.obtenerNutrientesDelDia(usuarioId, "Volumen");
                    generarPdf(informacionUsuario, informacionNutrientes);

                    proteinasInput.setText("");
                    carbosInput.setText("");
                    grasasInput.setText("");
                    grasasTotalesInput.setText("");
                } else {
                    Toast.makeText(NutrientesActivityVolumen.this, "No hay usuario activo", Toast.LENGTH_SHORT).show();
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

    private void generarPdf(String[] informacionUsuario, String informacionNutrientes) {
        // Eliminar duplicación de unidades
        informacionNutrientes = informacionNutrientes.replace(" g g", " g")
                .replace(" kcal kcal", " kcal");

        // Obtener la fecha actual
        String fechaActual = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

        // Configurar documento PDF
        PdfDocument pdfDocument = new PdfDocument();
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(12);

        // Usar tamaño mayor para el contenido
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(400, 700, 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);
        Canvas canvas = page.getCanvas();

        int x = 20;
        int y = 30;
        int lineSpacing = 20;
        int maxWidth = 360;

        // Título
        paint.setTextSize(16);
        canvas.drawText("Informe de Nutrientes - Volumen", x, y, paint);
        y += 30;

        // Fecha
        paint.setTextSize(12);
        canvas.drawText("Fecha: " + fechaActual, x, y, paint);
        y += lineSpacing + 10;

        // Información del Usuario
        canvas.drawText("Información del Usuario:", x, y, paint);
        y += lineSpacing;
        canvas.drawText("Nombre: " + informacionUsuario[0], x, y, paint);
        y += lineSpacing;
        canvas.drawText("Email: " + informacionUsuario[1], x, y, paint);
        y += 30;

        // Información de Nutrientes
        paint.setTextSize(14);
        canvas.drawText("Información de Nutrientes:", x, y, paint);
        y += lineSpacing;

        // Dividir texto de nutrientes en varias líneas
        paint.setTextSize(12);
        String[] lineasNutrientes = dividirTextoEnLineas(informacionNutrientes, maxWidth, paint);
        for (String linea : lineasNutrientes) {
            canvas.drawText(linea, x, y, paint);
            y += lineSpacing;
        }

        pdfDocument.finishPage(page);

        // Guardar el PDF en el directorio exclusivo de la aplicación
        File directory = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        if (directory != null && !directory.exists()) {
            directory.mkdirs();
        }
        String targetPdf = (directory != null ? directory.getAbsolutePath() : "") + "/InformeNutrientesVolumen.pdf";
        File filePath = new File(targetPdf);
        try {
            pdfDocument.writeTo(new FileOutputStream(filePath));
            Toast.makeText(NutrientesActivityVolumen.this, "PDF generado con éxito: " + targetPdf, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(NutrientesActivityVolumen.this, "Error al generar el PDF", Toast.LENGTH_SHORT).show();
        }
        pdfDocument.close();
    }

    private String[] dividirTextoEnLineas(String texto, int maxWidth, Paint paint) {
        String[] palabras = texto.split(" ");
        StringBuilder lineaActual = new StringBuilder();
        List<String> lineas = new ArrayList<>();

        for (String palabra : palabras) {
            if (paint.measureText(lineaActual + palabra) > maxWidth) {
                lineas.add(lineaActual.toString());
                lineaActual = new StringBuilder();
            }
            lineaActual.append(palabra).append(" ");
        }
        lineas.add(lineaActual.toString());
        return lineas.toArray(new String[0]);
    }
}
