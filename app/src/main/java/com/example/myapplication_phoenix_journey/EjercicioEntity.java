package com.example.myapplication_phoenix_journey;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ejercicios")
public class EjercicioEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String nombreEjercicio;
    public String comentario;
    public byte[] foto; // Guardaremos la imagen en formato byte[]

    public EjercicioEntity(String nombreEjercicio, String comentario, byte[] foto) {
        this.nombreEjercicio = nombreEjercicio;
        this.comentario = comentario;
        this.foto = foto;
    }
}
