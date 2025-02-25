package com.example.myapplication_phoenix_journey;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication_phoenix_journey.basesdedatos.MiBaseDeDatos;

public class EditarRutinaActivity extends AppCompatActivity {

    private TextView daySelectedText;  // Muestra el día seleccionado
    private TextView savedDataText;    // Muestra la rutina guardada
    private TextView ejercicioText;    // TextView para mostrar y editar músculos
    private EditText musclesInput;     // Permite ingresar los músculos trabajados
    private Button guardarDatosButton; // Botón para guardar los datos ingresados
    private ImageButton añadirEjercicioButton; // Botón para añadir un nuevo ejercicio
    private ScrollView ejerciciosContainer; // Contenedor ScrollView para los ejercicios añadidos
    private LinearLayout ejerciciosList; // Contenedor LinearLayout dentro del ScrollView
    private LinearLayout iniciarEntrenamientoLayout; // Vista para iniciar entrenamiento

    private MiBaseDeDatos dbHelper;
    private int usuarioId;
    private String dayName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_rutina);

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
        Intent intent = getIntent();
        dayName = intent.getStringExtra("DIA_SELECCIONADO");
        usuarioId = intent.getIntExtra("USUARIO_ID", -1);

        // Inicializar la base de datos
        dbHelper = new MiBaseDeDatos(this);

        // Mostrar el día seleccionado en la interfaz
        if (dayName != null) {
            daySelectedText.setText(dayName);
        }

        // Cargar ejercicios guardados al iniciar la actividad
        cargarEjerciciosGuardados();


        configurarBotonGuardarDatos();
        configurarBotonAñadirEjercicio();

        // Configurar el TextView ejercicio_text para editar músculos
        configurarTextViewEjercicio();

        // Configuración del layout para iniciar entrenamiento
        iniciarEntrenamientoLayout.setOnClickListener(v -> {
            // Abrir la actividad de entrenamiento
            Intent intentEntrenamiento = new Intent(EditarRutinaActivity.this, EntrenamientoActivity.class);
            startActivity(intentEntrenamiento);
        });
    }

    // Inicializa las vistas
    private void inicializarVistas() {
        daySelectedText = findViewById(R.id.day_selected_text);
        savedDataText = findViewById(R.id.saved_data_text);
        ejercicioText = findViewById(R.id.ejercicio_text); // Referencia al TextView ejercicio_text
        musclesInput = findViewById(R.id.muscles_input);
        guardarDatosButton = findViewById(R.id.guardar_datos_button);
        añadirEjercicioButton = findViewById(R.id.añadir_ejercicio);
        ejerciciosContainer = findViewById(R.id.ejercicios_container); // ScrollView
        ejerciciosList = findViewById(R.id.ejercicios_list); // LinearLayout dentro del ScrollView
        iniciarEntrenamientoLayout = findViewById(R.id.iniciar_entrenamiento);
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
        new AlertDialog.Builder(EditarRutinaActivity.this)
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
            Intent addExerciseIntent = new Intent(EditarRutinaActivity.this, AñadirEjercicioActivity.class);
            startActivityForResult(addExerciseIntent, 1); // Abrir la actividad de añadir ejercicio
        });
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
                    intent = new Intent(EditarRutinaActivity.this, EjerHombroActivity.class);
                    break;
                case "EjerPechoActivity":
                    intent = new Intent(EditarRutinaActivity.this, EjerPechoActivity.class);
                    break;
                case "EjerEspaldaActivity":
                    intent = new Intent(EditarRutinaActivity.this, EjerEspaldaActivity.class);
                    break;
                case "EjerAbsActivity":
                    intent = new Intent(EditarRutinaActivity.this, EjerAbsActivity.class);
                    break;
                case "EjerCuadricepsActivity":
                    intent = new Intent(EditarRutinaActivity.this, EjerCuadricepsActivity.class);
                    break;
                case "EjerBicepsActivity":
                    intent = new Intent(EditarRutinaActivity.this, EjerBicepsActivity.class);
                    break;
                case "EjerTricepsActivity":
                    intent = new Intent(EditarRutinaActivity.this, EjerTricepsActivity.class);
                    break;
                case "EjerFemoralesActivity":
                    intent = new Intent(EditarRutinaActivity.this, EjerFemoralesActivity.class);
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
}
