package com.example.myapplication_phoenix_journey;

import android.graphics.Bitmap;

public class Foto {
    private int id;
    private Bitmap imagen;
    private String fecha;

    public Foto(int id, Bitmap imagen, String fecha) {
        this.id = id;
        this.imagen = imagen;
        this.fecha = fecha;
    }

    public Foto(Bitmap imagen, String fecha) {
        this.imagen = imagen;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }

    public Bitmap getBitmap() {
        return imagen; // Alias para getImagen(), ya que el otro código lo requiere con este nombre
    }

    public String getFecha() {
        return fecha;
    }

    @Override
    public String toString() {
        return "Foto{" +
                "id=" + id +
                ", fecha='" + fecha + '\'' +
                '}';
    }
}
