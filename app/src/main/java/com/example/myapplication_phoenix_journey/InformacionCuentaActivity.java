package com.example.myapplication_phoenix_journey;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication_phoenix_journey.basesdedatos.MiBaseDeDatos;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class InformacionCuentaActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1; // Código de solicitud para seleccionar una imagen

    private TextView usernameValue, emailValue, passwordValue;
    private ImageButton eyeButton, changeProfileImageButton;
    private ImageView profileImage;
    private MiBaseDeDatos dbHelper;
    private int usuarioId;
    private String passwordReal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        setContentView(R.layout.activity_informacion_cuenta);

        // Inicializar vistas
        usernameValue = findViewById(R.id.username_value);
        emailValue = findViewById(R.id.email_value);
        passwordValue = findViewById(R.id.password_value);
        eyeButton = findViewById(R.id.eye_button);
        profileImage = findViewById(R.id.profile_image);
        changeProfileImageButton = findViewById(R.id.change_profile_image_button);

        // Inicializar la base de datos
        dbHelper = new MiBaseDeDatos(this);

        // Obtener el ID del usuario activo
        usuarioId = dbHelper.obtenerUsuarioIdActivo();

        // Cargar la información del usuario
        cargarInformacionUsuario();

        // Cargar la imagen de perfil
        cargarImagenPerfil();

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

        // Configurar el botón para cambiar la foto de perfil
        changeProfileImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirGaleria();
            }
        });
    }

    // Método para cargar la información del usuario
    private void cargarInformacionUsuario() {
        String[] usuarioInfo = dbHelper.obtenerInformacionUsuario(usuarioId);
        if (usuarioInfo != null) {
            usernameValue.setText(usuarioInfo[0]); // Nombre de usuario
            emailValue.setText(usuarioInfo[1]);   // Correo electrónico
            passwordReal = usuarioInfo[2];        // Almacenar la contraseña real
            passwordValue.setText("********");     // Mostrar la contraseña oculta inicialmente
        } else {
            Toast.makeText(this, "Error al cargar la información del usuario", Toast.LENGTH_SHORT).show();
        }
    }

    // Método para cargar la imagen de perfil desde el sistema de archivos
    private void cargarImagenPerfil() {
        String imagenPerfilRuta = dbHelper.obtenerRutaImagenPerfil(usuarioId);
        if (imagenPerfilRuta != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagenPerfilRuta);
            profileImage.setImageBitmap(bitmap);
        } else {
            profileImage.setImageResource(R.drawable.ic_profile_placeholder); // Imagen por defecto
        }
    }

    // Método para abrir la galería y seleccionar una imagen
    private void abrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    // Método para manejar el resultado de la selección de la imagen
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            profileImage.setImageURI(imageUri);
            guardarImagenPerfil(imageUri); // Guardar la imagen en el sistema de archivos
        }
    }

    // Método para guardar la imagen en el sistema de archivos y actualizar la ruta en la base de datos
    private void guardarImagenPerfil(Uri imageUri) {
        String imagenPerfilRuta = guardarImagenEnArchivo(imageUri);
        if (imagenPerfilRuta != null) {
            boolean actualizado = dbHelper.actualizarImagenPerfil(usuarioId, imagenPerfilRuta);
            if (actualizado) {
                Toast.makeText(this, "Imagen de perfil actualizada", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error al actualizar la imagen", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Método para guardar la imagen en el sistema de archivos y devolver la ruta
    private String guardarImagenEnArchivo(Uri imageUri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(imageUri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

            // Crear un directorio para almacenar las imágenes de perfil
            File directorio = new File(getFilesDir(), "imagenes_perfil");
            if (!directorio.exists()) {
                directorio.mkdir();
            }

            // Crear un archivo para la imagen de perfil
            File archivoImagen = new File(directorio, usuarioId + "_perfil.png");
            try (FileOutputStream fos = new FileOutputStream(archivoImagen)) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.flush();
            }
            return archivoImagen.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Método para mostrar un diálogo de confirmación antes de eliminar la cuenta
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

    // Método para eliminar la cuenta del usuario
    private void eliminarCuenta() {
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
