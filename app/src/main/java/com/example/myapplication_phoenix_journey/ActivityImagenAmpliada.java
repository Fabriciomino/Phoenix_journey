package com.example.myapplication_phoenix_journey;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityImagenAmpliada extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagen_ampliada);

        ImageView imagenAmpliada = findViewById(R.id.imagen_ampliada);

        // Obtener la imagen del intent
        Bitmap imagen = getIntent().getParcelableExtra("imagen");
        if (imagen != null) {
            imagenAmpliada.setImageBitmap(imagen);
        }
    }
}