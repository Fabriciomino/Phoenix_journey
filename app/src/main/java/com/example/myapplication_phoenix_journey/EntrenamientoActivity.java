package com.example.myapplication_phoenix_journey;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import com.example.myapplication_phoenix_journey.basesdedatos.MiBaseDeDatos;

public class EntrenamientoActivity extends AppCompatActivity {

    private TextView ejercicioActualText;
    private TextView seriesText;
    private TextView tiempoText;
    private Button iniciarSerieButton;
    private Button siguienteEjercicioButton;
    private NestedScrollView scrollView;
    private TextView ejercicioText;    // TextView para mostrar y editar músculos
    private EditText musclesInput;
    private Button guardarDatosButton; // Botón para guardar los datos ingresados
    private ImageButton backButton;    // Botón para retroceder a la actividad anterior
    private ImageButton añadirEjercicioButton; // Botón para añadir un nuevo ejercicio
    private LinearLayout ejerciciosList;

    private MiBaseDeDatos dbHelper;
    private int usuarioId;
    private String dayName;
    private int seriesActuales = 1;
    private int totalSeries = 0; // Número de series ingresado por el usuario
    private long tiempoDescanso = 0; // Tiempo de descanso ingresado por el usuario en milisegundos
    private CountDownTimer serieTimer;
    private CountDownTimer descansoTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrenamiento);

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
        inicializarVistas();

        // Obtener el nombre del día seleccionado y el ID del usuario desde el Intent
        usuarioId = getIntent().getIntExtra("USUARIO_ID", -1);
        dayName = getIntent().getStringExtra("DIA_SELECCIONADO");

        // Inicializar la base de datos
        dbHelper = new MiBaseDeDatos(this);

        // Cargar ejercicios guardados
        cargarEjerciciosGuardados();

        // Configurar los botones de la interfaz
        configurarBotonRetroceder();
        configurarBotonGuardarDatos();
        configurarBotonAñadirEjercicio();

        // Configurar el TextView ejercicio_text para editar músculos
        configurarTextViewEjercicio();

        if (ejerciciosList.getChildCount() > 0) {
            pedirSeriesYTiempoDescanso();
        } else {
            ejercicioActualText.setText("No hay ejercicios para hoy.");
            iniciarSerieButton.setVisibility(View.GONE);
            siguienteEjercicioButton.setVisibility(View.GONE);
        }
    }

    // Inicializa las vistas
    private void inicializarVistas() {
        ejercicioActualText = findViewById(R.id.ejercicio_actual_text);
        seriesText = findViewById(R.id.series_text);
        tiempoText = findViewById(R.id.tiempo_text);
        iniciarSerieButton = findViewById(R.id.iniciar_serie_button);
        siguienteEjercicioButton = findViewById(R.id.siguiente_ejercicio_button);
        scrollView = findViewById(R.id.ejercicios_container);
        ejercicioText = findViewById(R.id.ejercicio_text);
        musclesInput = findViewById(R.id.muscles_input);
        guardarDatosButton = findViewById(R.id.guardar_datos_button);
        backButton = findViewById(R.id.back_button);
        añadirEjercicioButton = findViewById(R.id.añadir_ejercicio);
        ejerciciosList = findViewById(R.id.ejercicios_list);
    }

    // Configurar el TextView ejercicio_text para editar músculos
    private void configurarTextViewEjercicio() {
        ejercicioText.setOnClickListener(v -> {
            // Mostrar el EditText para editar los músculos
            musclesInput.setText(ejercicioText.getText().toString()); // Copiar el texto actual al EditText
            musclesInput.setVisibility(View.VISIBLE);
            ejercicioText.setVisibility(View.GONE);
            musclesInput.requestFocus(); // Enfocar el EditText
        });
    }

    // Cargar ejercicios guardados desde la base de datos
    private void cargarEjerciciosGuardados() {
        // Obtener los ejercicios guardados para el día y usuario actual
        Cursor cursor = dbHelper.obtenerRutina(usuarioId, dayName);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String musculos = cursor.getString(cursor.getColumnIndexOrThrow(MiBaseDeDatos.COLUMN_MUSCULOS));
                String ejercicio = cursor.getString(cursor.getColumnIndexOrThrow(MiBaseDeDatos.COLUMN_EJERCICIO));
                int imagenResId = cursor.getInt(cursor.getColumnIndexOrThrow(MiBaseDeDatos.COLUMN_IMAGEN));

                // Mostrar los músculos en el TextView ejercicio_text
                if (musculos != null && !musculos.isEmpty()) {
                    ejercicioText.setText(musculos);
                    ejercicioText.setVisibility(View.VISIBLE);
                    musclesInput.setVisibility(View.GONE);
                }

                // Mostrar los ejercicios en el LinearLayout
                if (ejercicio != null && !ejercicio.isEmpty()) {
                    // Inflar el layout del ejercicio
                    View ejercicioView = LayoutInflater.from(this).inflate(R.layout.ejercicio_item, ejerciciosList, false);

                    // Configurar la imagen y el texto
                    ImageView imagenEjercicio = ejercicioView.findViewById(R.id.imagen_ejercicio);
                    TextView textoEjercicio = ejercicioView.findViewById(R.id.texto_ejercicio);

                    imagenEjercicio.setImageResource(imagenResId);
                    textoEjercicio.setText(ejercicio);

                    // Configurar el clic para eliminar el ejercicio
                    ejercicioView.setOnClickListener(v -> mostrarDialogoEliminarEjercicio(ejercicio, ejercicioView));

                    // Añadir el ejercicio al contenedor
                    ejerciciosList.addView(ejercicioView);
                }
            } while (cursor.moveToNext());

            cursor.close();
        }
    }

    // Mostrar un diálogo para confirmar la eliminación del ejercicio
    private void mostrarDialogoEliminarEjercicio(String ejercicio, View ejercicioView) {
        new AlertDialog.Builder(EntrenamientoActivity.this)
                .setTitle("Eliminar ejercicio")
                .setMessage("¿Quieres eliminar el ejercicio " + ejercicio + " de tu rutina?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    // Eliminar el ejercicio de la base de datos
                    dbHelper.eliminarEjercicio(usuarioId, dayName, ejercicio);

                    // Eliminar el ejercicio de la lista de la interfaz
                    ejerciciosList.removeView(ejercicioView);
                })
                .setNegativeButton("No", null)
                .show();
    }

    // Configura el botón de retroceso
    private void configurarBotonRetroceder() {
        backButton.setOnClickListener(v -> {
            Intent backIntent = new Intent(EntrenamientoActivity.this, HomeFragment.class);
            startActivity(backIntent);
            finish();
        });
    }

    // Configura la acción del botón "Guardar Datos"
    private void configurarBotonGuardarDatos() {
        guardarDatosButton.setOnClickListener(v -> {
            String inputText = musclesInput.getText().toString().trim();

            if (!inputText.isEmpty()) {
                // Mostrar el texto ingresado en el TextView ejercicio_text
                ejercicioText.setText(inputText);
                ejercicioText.setVisibility(View.VISIBLE);
                musclesInput.setVisibility(View.GONE);

                // Guardar los músculos en la base de datos
                dbHelper.insertarRutina(usuarioId, dayName, inputText, null, 0); // Guardar solo músculos
            }
        });
    }

    // Configura el botón de añadir ejercicio
    private void configurarBotonAñadirEjercicio() {
        añadirEjercicioButton.setOnClickListener(v -> {
            Intent addExerciseIntent = new Intent(EntrenamientoActivity.this, AñadirEjercicioActivity.class);
            startActivityForResult(addExerciseIntent, 1); // Abrir la actividad de añadir ejercicio
            startActivityForResult(addExerciseIntent, 1); // Abrir la actividad de añadir ejercicio
        });
    }

    private void pedirSeriesYTiempoDescanso() {
        // Crear un diálogo para que el usuario ingrese el número de series y el tiempo de descanso
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Configuración del ejercicio");

        // Añadir un EditText para el número de series
        final EditText seriesInput = new EditText(this);
        seriesInput.setHint("Número de series");
        seriesInput.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);

        // Añadir un EditText para el tiempo de descanso
        final EditText descansoInput = new EditText(this);
        descansoInput.setHint("Tiempo de descanso (segundos)");
        descansoInput.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);

        // Añadir los EditText al diálogo
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(seriesInput);
        layout.addView(descansoInput);
        builder.setView(layout);

        // Configurar el botón "Aceptar"
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String series = seriesInput.getText().toString();
                String descanso = descansoInput.getText().toString();

                if (!series.isEmpty() && !descanso.isEmpty()) {
                    totalSeries = Integer.parseInt(series);
                    tiempoDescanso = Long.parseLong(descanso) * 1000; // Convertir a milisegundos
                    mostrarEjercicioActual();
                }
            }
        });

        // Configurar el botón "Cancelar"
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                // Aquí podrías decidir si quieres cerrar la actividad o no, dependiendo de tu lógica de aplicación
            }
        });

        // Mostrar el diálogo
        builder.show();
    }

    private void mostrarEjercicioActual() {
        if (ejerciciosList.getChildCount() > 0) {
            View ejercicioView = ejerciciosList.getChildAt(0);
            TextView textoEjercicio = ejercicioView.findViewById(R.id.texto_ejercicio);
            String ejercicio = textoEjercicio.getText().toString();

            ejercicioActualText.setText("Ejercicio: " + ejercicio);
            seriesText.setText("Series: " + seriesActuales + "/" + totalSeries);
            tiempoText.setText("Tiempo: 0s");

            // Habilitar el botón de iniciar serie
            iniciarSerieButton.setVisibility(View.VISIBLE);
            siguienteEjercicioButton.setVisibility(View.GONE);
            iniciarSerieButton.setOnClickListener(v -> iniciarSerie());
        } else {
            // Fin del entrenamiento
            ejercicioActualText.setText("¡Entrenamiento completado!");
            seriesText.setText("");
            tiempoText.setText("");
            iniciarSerieButton.setVisibility(View.GONE);
            siguienteEjercicioButton.setVisibility(View.GONE);
        }
    }

    private void iniciarSerie() {
        // Iniciar un cronómetro para la serie (ejemplo: 30 segundos)
        serieTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tiempoText.setText("Tiempo: " + millisUntilFinished / 1000 + "s");
            }

            @Override
            public void onFinish() {
                tiempoText.setText("¡Serie completada!");
                iniciarDescanso();
            }
        }.start();
    }

    private void iniciarDescanso() {
        // Iniciar un cronómetro para el descanso con el tiempo personalizado
        descansoTimer = new CountDownTimer(tiempoDescanso, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tiempoText.setText("Descanso: " + millisUntilFinished / 1000 + "s");
            }

            @Override
            public void onFinish() {
                tiempoText.setText("¡Descanso completado!");
                seriesActuales++;
                if (seriesActuales <= totalSeries) {
                    seriesText.setText("Series: " + seriesActuales + "/" + totalSeries);
                    iniciarSerie(); // Iniciar la siguiente serie
                } else {
                    siguienteEjercicio(); // Pasar al siguiente ejercicio
                }
            }
        }.start();
    }

    private void siguienteEjercicio() {
        if (serieTimer != null) {
            serieTimer.cancel();
        }
        if (descansoTimer != null) {
            descansoTimer.cancel();
        }

        // Eliminar el primer ejercicio del listado y actualizar la UI
        if (ejerciciosList.getChildCount() > 0) {
            ejerciciosList.removeViewAt(0);
            seriesActuales = 1;
            mostrarEjercicioActual();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Obtener el nombre de la actividad a abrir desde AñadirEjercicioActivity
            String nombreActividad = data.getStringExtra("ACTIVIDAD");

            // Abrir la actividad correspondiente según el músculo seleccionado
            Intent intent = null;
            switch (nombreActividad) {
                case "EjerHombroActivity":
                    intent = new Intent(EntrenamientoActivity.this, EjerHombroActivity.class);
                    break;
                case "EjerPechoActivity":
                    intent = new Intent(EntrenamientoActivity.this, EjerPechoActivity.class);
                    break;
                case "EjerEspaldaActivity":
                    intent = new Intent(EntrenamientoActivity.this, EjerEspaldaActivity.class);
                    break;
                case "EjerAbsActivity":
                    intent = new Intent(EntrenamientoActivity.this, EjerAbsActivity.class);
                    break;
                case "EjerCuadricepsActivity":
                    intent = new Intent(EntrenamientoActivity.this, EjerCuadricepsActivity.class);
                    break;
                case "EjerBicepsActivity":
                    intent = new Intent(EntrenamientoActivity.this, EjerBicepsActivity.class);
                    break;
                case "EjerTricepsActivity":
                    intent = new Intent(EntrenamientoActivity.this, EjerTricepsActivity.class);
                    break;
                case "EjerFemoralesActivity":
                    intent = new Intent(EntrenamientoActivity.this, EjerFemoralesActivity.class);
                    break;
            }

            if (intent != null) {
                startActivityForResult(intent, 2); // Usar un código de solicitud diferente
            }
        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            // Si el resultado proviene de una actividad de ejercicios, guardar el ejercicio
            String ejercicio = data.getStringExtra("EJERCICIO");
            int imagenResId = data.getIntExtra("IMAGEN", -1);

            if (ejercicio != null && imagenResId != -1) {
                // Guardar el ejercicio en la base de datos como un nuevo registro
                dbHelper.insertarRutina(usuarioId, dayName, null, ejercicio, imagenResId);

                // Inflar el layout del ejercicio
                View ejercicioView = LayoutInflater.from(this).inflate(R.layout.ejercicio_item, ejerciciosList, false);

                // Configurar la imagen y el texto
                ImageView imagenEjercicio = ejercicioView.findViewById(R.id.imagen_ejercicio);
                TextView textoEjercicio = ejercicioView.findViewById(R.id.texto_ejercicio);

                imagenEjercicio.setImageResource(imagenResId);
                textoEjercicio.setText(ejercicio);

                // Configurar el clic para eliminar el ejercicio
                ejercicioView.setOnClickListener(v -> mostrarDialogoEliminarEjercicio(ejercicio, ejercicioView));

                // Añadir el ejercicio al contenedor
                ejerciciosList.addView(ejercicioView);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (serieTimer != null) {
            serieTimer.cancel();
        }
        if (descansoTimer != null) {
            descansoTimer.cancel();
        }
    }
}