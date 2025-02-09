package com.example.myapplication_phoenix_journey;

import android.graphics.Bitmap;

public class ItemFotoEjercicio {

    private Bitmap imagen;
    private String texto;

    public ItemFotoEjercicio(Bitmap imagen, String texto) {
        this.imagen = imagen;
        this.texto = texto;
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
