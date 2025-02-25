package com.example.myapplication_phoenix_journey;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication_phoenix_journey.basesdedatos.MiBaseDeDatos;
import java.util.ArrayList;
import java.util.List;

public class AmigosActivity extends AppCompatActivity {

    private TextView agregarAmigosText;
    private EditText nombrePerfilEditText;
    private Button buscarUsuarioButton;
    private RecyclerView recyclerView;
    private MiBaseDeDatos dbHelper;
    private UsuarioAdapter usuarioAdapter;
    private Button verAmigoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amigos);

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

        // Inicializar las vistas
        agregarAmigosText = findViewById(R.id.agregar_amigos_text);
        nombrePerfilEditText = findViewById(R.id.nombre_perfil_input);
        buscarUsuarioButton = findViewById(R.id.buscar_usuario_button);
        recyclerView = findViewById(R.id.recycler_view_usuarios);
        verAmigoButton = findViewById(R.id.ver_amigo_button);

        // Configuración de la actividad, como poner texto o configurar listeners
        agregarAmigosText.setText("Agregar Amigos");

        // Inicializar la base de datos
        dbHelper = new MiBaseDeDatos(this);

        // Configurar el RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        usuarioAdapter = new UsuarioAdapter(new ArrayList<>(), usuario -> {
            // Lógica para agregar amigo
            dbHelper.agregarAmigo(dbHelper.obtenerUsuarioIdActivo(), usuario.getId());
            usuario.setAmigo(true); // Marcar como amigo
            usuarioAdapter.notifyDataSetChanged();
        }, null, this, false); // No mostrar el botón de eliminar
        recyclerView.setAdapter(usuarioAdapter);

        // Configurar el listener para el botón de búsqueda
        buscarUsuarioButton.setOnClickListener(v -> {
            String nombrePerfil = nombrePerfilEditText.getText().toString();
            buscarUsuarios(nombrePerfil);
        });

        // Configurar el listener para el botón de ver amigo
        verAmigoButton.setOnClickListener(v -> {
            // Abrir la actividad de detalle del amigo
            Intent intent = new Intent(AmigosActivity.this, AmigoDetalleActivity.class);
            startActivity(intent);
        });
    }

    // Método para buscar usuarios por nombre
    private void buscarUsuarios(String nombrePerfil) {
        List<Usuario> usuariosEncontrados = dbHelper.buscarUsuariosPorNombre(nombrePerfil);
        usuarioAdapter.actualizarUsuarios(usuariosEncontrados);
    }
}
