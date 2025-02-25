package com.example.myapplication_phoenix_journey;

public class Usuario {
    private int id;
    private String nombre;
    private String imagenPerfilRuta;
    private boolean esAmigo;

    public Usuario(int id, String nombre, String imagenPerfilRuta) {
        this.id = id;
        this.nombre = nombre;
        this.imagenPerfilRuta = imagenPerfilRuta;
        this.esAmigo = false; // Por defecto, no es amigo
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getImagenPerfilRuta() {
        return imagenPerfilRuta;
    }

    public boolean isAmigo() {
        return esAmigo;
    }

    public void setAmigo(boolean esAmigo) {
        this.esAmigo = esAmigo;
    }
}
