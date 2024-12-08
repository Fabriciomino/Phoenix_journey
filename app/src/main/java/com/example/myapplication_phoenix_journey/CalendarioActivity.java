package com.example.myapplication_phoenix_journey;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class CalendarioActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private TextView selectedDateText;
    private Button addTrainingButton;
    private ImageButton calendarioButton, usuarioButton, menuIconButton;

    // Mapa para asociar colores a los días de la semana
    private HashMap<String, Integer> dayColorMap;

    // Mapa para asociar días seleccionados con colores
    private HashMap<Long, Integer> selectedDaysMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Eliminar la ActionBar (nombre de la clase en la parte superior)
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Configurar la ventana para un diseño de pantalla completa
        getWindow().setFlags(
                android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        // Cambiar el locale a español para toda la aplicación
        setLocaleToSpanish();

        setContentView(R.layout.activity_calendario);

        // Inicializar los elementos
        calendarView = findViewById(R.id.calendarView);
        selectedDateText = findViewById(R.id.selectedDateText);
        addTrainingButton = findViewById(R.id.btnAddTraining);
        calendarioButton = findViewById(R.id.calendario_button);
        usuarioButton = findViewById(R.id.usuario_button);
        menuIconButton = findViewById(R.id.menu_icon_button);

        // Inicializar mapas de colores
        initializeDayColors();
        selectedDaysMap = new HashMap<>();

        // Configurar el botón "Añadir Entrenamiento"
        addTrainingButton.setOnClickListener(v -> showDayPickerDialog());

        // Listener para mostrar la fecha seleccionada
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            String selectedDate = "Fecha seleccionada: " + dayOfMonth + "/" + (month + 1) + "/" + year;
            selectedDateText.setText(selectedDate);
        });

        // Configurar el botón de menú para abrir MenuActivity
        menuIconButton.setOnClickListener(v -> {
            Intent intent = new Intent(CalendarioActivity.this, MenuActivity.class);
            startActivity(intent);
        });

        // Configurar el botón de usuario para abrir MiUsuarioActivity
        usuarioButton.setOnClickListener(v -> {
            Intent intent = new Intent(CalendarioActivity.this, MiUsuarioActivity.class);
            startActivity(intent);
        });
    }

    private void setLocaleToSpanish() {
        Locale locale = new Locale("es", "ES");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }

    // Inicializar colores asociados a los días de la semana
    private void initializeDayColors() {
        dayColorMap = new HashMap<>();
        dayColorMap.put("Lunes", Color.RED);
        dayColorMap.put("Martes", Color.GREEN);
        dayColorMap.put("Miércoles", Color.BLUE);
        dayColorMap.put("Jueves", Color.YELLOW);
        dayColorMap.put("Viernes", Color.CYAN);
        dayColorMap.put("Sábado", Color.MAGENTA);
        dayColorMap.put("Domingo", Color.LTGRAY);
    }

    // Mostrar diálogo para seleccionar un día
    private void showDayPickerDialog() {
        final String[] daysOfWeek = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Selecciona un día para el entrenamiento")
                .setItems(daysOfWeek, (dialog, which) -> {
                    String selectedDay = daysOfWeek[which];
                    assignColorToDay(selectedDay);
                })
                .create()
                .show();
    }

    // Asignar un color al día seleccionado
    private void assignColorToDay(String selectedDay) {
        if (dayColorMap.containsKey(selectedDay)) {
            int color = dayColorMap.get(selectedDay);

            // Simular la selección de un día en el calendario (no es posible directamente con CalendarView)
            Calendar today = Calendar.getInstance();
            long selectedDateInMillis = today.getTimeInMillis();

            selectedDaysMap.put(selectedDateInMillis, color); // Guardar el color para la fecha seleccionada

            Toast.makeText(this, "Entrenamiento añadido al " + selectedDay, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error: Día no encontrado", Toast.LENGTH_SHORT).show();
        }
    }
}
