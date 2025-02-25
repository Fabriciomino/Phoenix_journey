package com.example.myapplication_phoenix_journey;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication_phoenix_journey.basesdedatos.MiBaseDeDatos;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class FotosProgresoActivity extends AppCompatActivity {

    private RecyclerView recyclerFotos;
    private FotosAdapterActivity fotosAdapter;
    private ArrayList<Foto> fotosList = new ArrayList<>();
    private LinearLayout botonAgregarFoto;
    private MiBaseDeDatos db;

    // Definir el listener para clics en fotos
    private final FotosAdapterActivity.OnFotoClickListener clickListener = foto -> {
        // Aquí implementas lo que debe suceder cuando se hace clic en una foto
        Intent intent = new Intent(FotosProgresoActivity.this, ActivityImagenAmpliada.class);
        intent.putExtra("imagen", foto.getImagen());
        startActivity(intent);
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fotos_progreso);

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

        // Inicializar la base de datos
        db = new MiBaseDeDatos(this);

        // Configurar RecyclerView
        recyclerFotos = findViewById(R.id.recycler_fotos_progreso);
        recyclerFotos.setLayoutManager(new LinearLayoutManager(this));
        fotosAdapter = new FotosAdapterActivity(fotosList, this::eliminarFoto, clickListener);
        recyclerFotos.setAdapter(fotosAdapter);

        // Botón para agregar fotos
        botonAgregarFoto = findViewById(R.id.boton_agregar_foto_layout);
        botonAgregarFoto.setOnClickListener(v -> abrirCamara());



        // Obtener fotos de la base de datos
        cargarFotosDeBaseDeDatos();
    }

    private final ActivityResultLauncher<Intent> tomarFotoLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Bitmap foto = (Bitmap) result.getData().getExtras().get("data");
                    if (foto != null) {
                        String fechaActual = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
                        int usuarioId = db.obtenerUsuarioIdActivo(); // Asegúrate de tener este método en tu base de datos
                        long fotoId = db.insertarFotoProgreso(usuarioId, foto, fechaActual);
                        Foto nuevaFoto = new Foto((int) fotoId, foto, fechaActual);
                        fotosList.add(nuevaFoto);
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

    private void cargarFotosDeBaseDeDatos() {
        int usuarioId = db.obtenerUsuarioIdActivo(); // Asegúrate de tener este método en tu base de datos
        Cursor cursor = db.obtenerFotosProgreso(usuarioId);
        while (cursor.moveToNext()) {
            int fotoId = cursor.getInt(cursor.getColumnIndexOrThrow(MiBaseDeDatos.COLUMN_FOTO_ID));
            byte[] fotoBytes = cursor.getBlob(cursor.getColumnIndexOrThrow(MiBaseDeDatos.COLUMN_FOTO));
            String fecha = cursor.getString(cursor.getColumnIndexOrThrow(MiBaseDeDatos.COLUMN_FECHA));
            Bitmap foto = BitmapFactory.decodeByteArray(fotoBytes, 0, fotoBytes.length);
            fotosList.add(new Foto(fotoId, foto, fecha));
        }
        cursor.close();
        fotosAdapter.notifyDataSetChanged();
    }

    private void eliminarFoto(int position) {
        Foto foto = fotosList.get(position);
        db.eliminarFotoProgreso(foto.getId()); // Asegúrate de que tu clase Foto tenga un método getId()
        fotosList.remove(position);
        fotosAdapter.notifyItemRemoved(position);
        Toast.makeText(this, "Foto eliminada", Toast.LENGTH_SHORT).show();
    }
}