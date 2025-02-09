package com.example.myapplication_phoenix_journey;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class FotosProgresoActivity extends AppCompatActivity {

    private RecyclerView recyclerFotos;
    private FotosAdapterActivity fotosAdapter;
    private ArrayList<Foto> fotosList = new ArrayList<>();
    private LinearLayout botonAgregarFoto;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fotos_progreso);

        // Eliminar la ActionBar (nombre de la clase en la parte superior)
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Configurar la ventana para un diseño de pantalla completa
        getWindow().setFlags(
                android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        // Configurar RecyclerView
        recyclerFotos = findViewById(R.id.recycler_fotos_progreso);
        recyclerFotos.setLayoutManager(new LinearLayoutManager(this));
        fotosAdapter = new FotosAdapterActivity(fotosList, this::eliminarFoto);
        recyclerFotos.setAdapter(fotosAdapter);

        // Botón para agregar fotos
        botonAgregarFoto = findViewById(R.id.boton_agregar_foto_layout);
        botonAgregarFoto.setOnClickListener(v -> abrirCamara());

        // Inicializar el botón de retroceso y su funcionalidad
        backButton = findViewById(R.id.back_button); // Asegúrate de que tienes un botón con este ID en tu layout
        backButton.setOnClickListener(v -> {
            // Al presionar el botón, se vuelve a la actividad del menú
            Intent intent = new Intent(FotosProgresoActivity.this, HomeFragment.class);
            startActivity(intent);
            finish(); // Cierra esta actividad para que no quede en la pila de actividades
        });
    }

    private final ActivityResultLauncher<Intent> tomarFotoLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Bitmap foto = (Bitmap) result.getData().getExtras().get("data");
                    if (foto != null) {
                        String fechaActual = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
                        fotosList.add(new Foto(foto, fechaActual));
                        fotosAdapter.notifyDataSetChanged();
                        Toast.makeText(this, "Foto agregada correctamente", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    private void abrirCamara() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            tomarFotoLauncher.launch(intent);
        } else {
            Toast.makeText(this, "No se encontró una aplicación de cámara", Toast.LENGTH_SHORT).show();
        }
    }

    private void eliminarFoto(int position) {
        fotosList.remove(position);
        fotosAdapter.notifyItemRemoved(position);
        Toast.makeText(this, "Foto eliminada", Toast.LENGTH_SHORT).show();
    }
}
