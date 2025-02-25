package com.example.myapplication_phoenix_journey;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication_phoenix_journey.basesdedatos.MiBaseDeDatos;

import java.util.ArrayList;
import java.util.List;

public class AmigoDetalleActivity extends AppCompatActivity {

    private RecyclerView recyclerViewAmigos;
    private MiBaseDeDatos dbHelper;
    private UsuarioAdapter usuarioAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amigo_detalle);

        // Inicializar la base de datos
        dbHelper = new MiBaseDeDatos(this);

        // Inicializar las vistas
        recyclerViewAmigos = findViewById(R.id.recycler_view_amigos);

        // Configurar el RecyclerView
        recyclerViewAmigos.setLayoutManager(new LinearLayoutManager(this));
        usuarioAdapter = new UsuarioAdapter(new ArrayList<>(), null, usuario -> {
            // Lógica para eliminar amigo
            dbHelper.eliminarAmigo(dbHelper.obtenerUsuarioIdActivo(), usuario.getId());
            usuario.setAmigo(false); // Desmarcar como amigo
            usuarioAdapter.notifyDataSetChanged();
        }, this, true); // Mostrar el botón de eliminar
        recyclerViewAmigos.setAdapter(usuarioAdapter);

        // Cargar la lista de amigos
        cargarAmigos();
    }

    private void cargarAmigos() {
        int usuarioId = dbHelper.obtenerUsuarioIdActivo();
        List<Usuario> amigos = dbHelper.obtenerAmigos(usuarioId);
        for (Usuario amigo : amigos) {
            amigo.setAmigo(true); // Marcar como amigo
        }
        usuarioAdapter.actualizarUsuarios(amigos);
    }
}
