package com.example.myapplication_phoenix_journey;

import android.graphics.Bitmap;

public class Foto {
    private Bitmap imagen;
    private String fecha;

    public Foto(Bitmap imagen, String fecha) {
        this.imagen = imagen;
        this.fecha = fecha;
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public String getFecha() {
        return fecha;
    }
}
