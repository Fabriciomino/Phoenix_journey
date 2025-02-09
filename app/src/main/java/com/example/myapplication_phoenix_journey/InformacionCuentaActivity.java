package com.example.myapplication_phoenix_journey;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication_phoenix_journey.basesdedatos.MiBaseDeDatos;

public class InformacionCuentaActivity extends AppCompatActivity {

    private TextView usernameValue, emailValue, passwordValue;
    private ImageButton eyeButton;
    private MiBaseDeDatos dbHelper;
    private int usuarioId;
    private String passwordReal; // Variable para almacenar la contraseña real

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_cuenta);

        // Inicializar vistas
        usernameValue = findViewById(R.id.username_value);
        emailValue = findViewById(R.id.email_value);
        passwordValue = findViewById(R.id.password_value);
        eyeButton = findViewById(R.id.eye_button);

        // Inicializar la base de datos
        dbHelper = new MiBaseDeDatos(this);

        // Obtener el ID del usuario activo
        usuarioId = dbHelper.obtenerUsuarioIdActivo();

        // Cargar la información del usuario
        cargarInformacionUsuario();

        // Configurar el botón de ojo para mostrar/ocultar la contraseña
        eyeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (passwordValue.getText().toString().equals("********")) {
                    // Mostrar la contraseña real
                    passwordValue.setText(passwordReal);
                } else {
                    // Ocultar la contraseña
                    passwordValue.setText("********");
                }
            }
        });

        // Configurar el botón de eliminar cuenta
        findViewById(R.id.delete_account_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoConfirmacion();
            }
        });
    }

    private void cargarInformacionUsuario() {
        // Obtener la información del usuario desde la base de datos
        String[] usuarioInfo = dbHelper.obtenerInformacionUsuario(usuarioId);
        if (usuarioInfo != null) {
            usernameValue.setText(usuarioInfo[0]); // Nombre de usuario
            emailValue.setText(usuarioInfo[1]);    // Correo electrónico
            passwordReal = usuarioInfo[2];         // Almacenar la contraseña real
            passwordValue.setText("********");     // Mostrar la contraseña oculta inicialmente
        } else {
            Toast.makeText(this, "Error al cargar la información del usuario", Toast.LENGTH_SHORT).show();
        }
    }

    private void mostrarDialogoConfirmacion() {
        new AlertDialog.Builder(this)
                .setTitle("Eliminar Cuenta")
                .setMessage("¿Estás seguro de que deseas eliminar tu cuenta? Esta acción no se puede deshacer.")
                .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        eliminarCuenta();
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void eliminarCuenta() {
        // Eliminar la cuenta del usuario
        boolean eliminado = dbHelper.eliminarUsuario(usuarioId);
        if (eliminado) {
            Toast.makeText(this, "Cuenta eliminada correctamente", Toast.LENGTH_SHORT).show();

            // Cerrar la actividad actual y regresar a MainActivity
            Intent intent = new Intent(InformacionCuentaActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // Limpiar el stack de actividades
            startActivity(intent);
            finish(); // Cerrar la actividad actual
        } else {
            Toast.makeText(this, "Error al eliminar la cuenta", Toast.LENGTH_SHORT).show();
        }
    }
}