package com.example.myapplication_phoenix_journey;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.myapplication_phoenix_journey.basesdedatos.MiBaseDeDatos;

public class MiUsuarioFragment extends Fragment {

    private LinearLayout infoCuentaButton, ajustesButton, medidasButton, cerrarSesionButton;
    private MiBaseDeDatos miBaseDeDatos; // Instancia de la base de datos

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflar el layout del fragmento
        View view = inflater.inflate(R.layout.fragment_mi_usuario, container, false);

        // Inicializar la base de datos
        miBaseDeDatos = new MiBaseDeDatos(getContext());

        // Inicializar los botones
        infoCuentaButton = view.findViewById(R.id.infoCuentaButton);
        ajustesButton = view.findViewById(R.id.ajustesButton);
        medidasButton = view.findViewById(R.id.medidas);
        cerrarSesionButton = view.findViewById(R.id.cerrarSesionButton);

        // Configurar acciones para cada botón
        infoCuentaButton.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), InformacionCuentaActivity.class);
            startActivity(intent);
        });

        ajustesButton.setOnClickListener(v ->
                Toast.makeText(getContext(), "Ajustes", Toast.LENGTH_SHORT).show()
        );

        // Configurar eventos de clic para el botón de medidas
        medidasButton.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ProgresoCorporalActivity.class);
            startActivity(intent);
        });

        cerrarSesionButton.setOnClickListener(v -> showCerrarSesionDialog());

        return view;
    }

    private void showCerrarSesionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Cerrar Sesión");
        builder.setMessage("¿Seguro que quieres cerrar sesión?");

        builder.setPositiveButton("Aceptar", (dialog, which) -> {
            // Actualizar el estado de "activo" en la base de datos
            cerrarSesion();

            // Mostrar mensaje de cierre de sesión
            Toast.makeText(getContext(), "Sesión cerrada", Toast.LENGTH_SHORT).show();

            // Redirigir a MainActivity (pantalla de login)
            Intent intent = new Intent(getContext(), MainActivity.class);
            startActivity(intent);

            // Cerrar la actividad actual (en este caso, es un fragmento, no actividad)
            requireActivity().finish();
        });

        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // Método para cerrar sesión y actualizar el estado del usuario
    private void cerrarSesion() {
        // Obtener el usuario activo (por ejemplo, mediante un SharedPreference o un método similar)
        int usuarioIdActivo = obtenerUsuarioIdActivo(); // Asegúrate de tener este método implementado

        // Conectar a la base de datos y actualizar el estado "activo"
        SQLiteDatabase db = miBaseDeDatos.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("activo", 0); // Establecer el estado a inactivo (0)

        // Actualizar el usuario con el ID correspondiente
        db.update("usuarios", values, "id=?", new String[]{String.valueOf(usuarioIdActivo)});
    }

    // Método para obtener el ID del usuario activo (este método depende de tu implementación)
    private int obtenerUsuarioIdActivo() {
        // Aquí deberías tener una lógica para obtener el ID del usuario que está actualmente activo.
        // Puede ser a través de un SharedPreferences, una variable global, o similar.
        // Ejemplo:
        return 1; // Suponiendo que el usuario activo es el con ID 1, cambia esto según tu lógica
    }
}
