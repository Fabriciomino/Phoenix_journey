package com.example.myapplication_phoenix_journey;

import android.app.AlertDialog;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class CalendarioFragment extends Fragment {

    private CalendarView calendarView;
    private TextView selectedDateText;
    private Button addTrainingButton;

    // Mapa para asociar colores a los días de la semana
    private HashMap<String, Integer> dayColorMap;

    // Mapa para asociar días seleccionados con colores
    private HashMap<Long, Integer> selectedDaysMap;

    // Animación para el escalado del botón
    private android.view.animation.ScaleAnimation scaleAnimation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflar el layout del fragmento
        View view = inflater.inflate(R.layout.fragment_calendario, container, false);

        // Cambiar la configuración del locale a español
        setLocaleToSpanish();

        // Inicializar los elementos
        calendarView = view.findViewById(R.id.calendarView);
        selectedDateText = view.findViewById(R.id.selectedDateText);
        addTrainingButton = view.findViewById(R.id.btnAddTraining);

        // Inicializar mapas de colores
        initializeDayColors();
        selectedDaysMap = new HashMap<>();

        // Inicializar animación de escala
        scaleAnimation = new android.view.animation.ScaleAnimation(
                1.0f, 1.1f, 1.0f, 1.1f,
                android.view.animation.Animation.RELATIVE_TO_SELF, 0.5f,
                android.view.animation.Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(300);

        // Configurar el botón "Añadir Entrenamiento"
        addTrainingButton.setOnClickListener(v -> showDayPickerDialog());

        // Listener para mostrar la fecha seleccionada
        calendarView.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            String selectedDate = "Fecha seleccionada: " + dayOfMonth + "/" + (month + 1) + "/" + year;
            selectedDateText.setText(selectedDate);
        });

        return view;
    }

    // Cambiar la configuración del locale a español
    private void setLocaleToSpanish() {
        Locale locale = new Locale("es", "ES");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getContext().getResources().updateConfiguration(config, getContext().getResources().getDisplayMetrics());
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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

            Toast.makeText(getContext(), "Entrenamiento añadido al " + selectedDay, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Error: Día no encontrado", Toast.LENGTH_SHORT).show();
        }
    }

    // Método para mostrar toast
    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
