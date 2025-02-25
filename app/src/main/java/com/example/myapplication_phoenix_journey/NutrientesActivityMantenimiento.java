package com.example.myapplication_phoenix_journey;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.myapplication_phoenix_journey.basesdedatos.MiBaseDeDatos;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NutrientesActivityMantenimiento extends AppCompatActivity {
    private static final int REQUEST_CODE_STORAGE_PERMISSION = 1;

    private EditText proteinasInput, carbosInput, grasasInput, grasasTotalesInput;
    private Button almacenarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Ocultar la barra de acción si está disponible
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Verificar y solicitar permisos de almacenamiento (aunque getExternalFilesDir no requiere permisos en API >= 19)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE_STORAGE_PERMISSION);
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

        setContentView(R.layout.activity_nutrientes_mantenimiento);

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
                Toast.makeText(NutrientesActivityMantenimiento.this, "Por favor ingresa todos los valores", Toast.LENGTH_SHORT).show();
            } else {
                MiBaseDeDatos dbHelper = new MiBaseDeDatos(NutrientesActivityMantenimiento.this);
                int usuarioId = dbHelper.obtenerUsuarioIdActivo();

                if (usuarioId != -1) {
                    if (!dbHelper.nutrientesYaAlmacenadosHoy(usuarioId, "Mantenimiento")) {
                        dbHelper.insertarNutrientes(usuarioId, proteinas, carbohidratos, grasas, grasasTotales, "Mantenimiento");
                        Toast.makeText(NutrientesActivityMantenimiento.this, "Datos almacenados con éxito", Toast.LENGTH_SHORT).show();

                        // Generar PDF automáticamente
                        String[] informacionUsuario = dbHelper.obtenerInformacionUsuario(usuarioId);
                        String informacionNutrientes = dbHelper.obtenerNutrientesDelDia(usuarioId, "Mantenimiento");
                        generarPdf(informacionUsuario, informacionNutrientes);

                        proteinasInput.setText("");
                        carbosInput.setText("");
                        grasasInput.setText("");
                        grasasTotalesInput.setText("");
                    } else {
                        Toast.makeText(NutrientesActivityMantenimiento.this, "Ya has almacenado datos para hoy", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(NutrientesActivityMantenimiento.this, "No hay usuario activo", Toast.LENGTH_SHORT).show();
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
        // Eliminar duplicación de unidades (por ejemplo, " g g" se reemplaza por " g" y " kcal kcal" por " kcal")
        informacionNutrientes = informacionNutrientes.replace(" g g", " g")
                .replace(" kcal kcal", " kcal");

        // Obtener la fecha actual
        String fechaActual = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

        // Configurar documento PDF con tamaño adecuado
        PdfDocument pdfDocument = new PdfDocument();
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(12);

        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(400, 700, 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);
        Canvas canvas = page.getCanvas();

        int x = 20;
        int y = 30;
        int lineSpacing = 20;
        int maxWidth = 360; // Ancho máximo para evitar que el texto se salga

        // Título del documento
        paint.setTextSize(16);
        canvas.drawText("Informe de Nutrientes - Mantenimiento", x, y, paint);
        y += 30;

        // Fecha
        paint.setTextSize(12);
        canvas.drawText("Fecha: " + fechaActual, x, y, paint);
        y += lineSpacing + 10;

        // Información del usuario
        canvas.drawText("Información del Usuario:", x, y, paint);
        y += lineSpacing;
        canvas.drawText("Nombre: " + informacionUsuario[0], x, y, paint);
        y += lineSpacing;
        canvas.drawText("Email: " + informacionUsuario[1], x, y, paint);
        y += 30;

        // Información de nutrientes
        paint.setTextSize(14);
        canvas.drawText("Información de Nutrientes:", x, y, paint);
        y += lineSpacing;

        // Dividir el texto de nutrientes en varias líneas si es muy largo
        paint.setTextSize(12);
        String[] lineasNutrientes = dividirTextoEnLineas(informacionNutrientes, maxWidth, paint);
        for (String linea : lineasNutrientes) {
            canvas.drawText(linea, x, y, paint);
            y += lineSpacing;
        }

        pdfDocument.finishPage(page);

        // Guardar el PDF en el directorio DOCUMENTS de la aplicación
        String directoryPath = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath();
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        String targetPdf = directoryPath + "/InformeNutrientesMantenimiento.pdf";
        File filePath = new File(targetPdf);
        try {
            pdfDocument.writeTo(new FileOutputStream(filePath));
            Toast.makeText(NutrientesActivityMantenimiento.this, "PDF generado con éxito: " + targetPdf, Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(NutrientesActivityMantenimiento.this, "Error al generar el PDF", Toast.LENGTH_SHORT).show();
        }
        pdfDocument.close();
    }

    /**
     * Divide un texto largo en varias líneas respetando un ancho máximo.
     *
     * @param texto    Texto a dividir.
     * @param maxWidth Ancho máximo permitido para el texto.
     * @param paint    Paint utilizado para medir el ancho del texto.
     * @return Un arreglo de cadenas con el texto dividido.
     */
    private String[] dividirTextoEnLineas(String texto, int maxWidth, Paint paint) {
        String[] palabras = texto.split(" ");
        StringBuilder lineaActual = new StringBuilder();
        List<String> lineas = new ArrayList<>();

        for (String palabra : palabras) {
            // Se añade la palabra y se mide si excede el ancho máximo
            if (paint.measureText(lineaActual + palabra) > maxWidth) {
                lineas.add(lineaActual.toString());
                lineaActual = new StringBuilder();
            }
            lineaActual.append(palabra).append(" ");
        }
        lineas.add(lineaActual.toString()); // Agregar la última línea
        return lineas.toArray(new String[0]);
    }

}
