package com.example.myapplication_phoenix_journey.basesdedatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.example.myapplication_phoenix_journey.NutrientesActivityDefinicion;
import com.example.myapplication_phoenix_journey.Usuario;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MiBaseDeDatos extends SQLiteOpenHelper {
    private Context context;

    public static final String DB_NAME = "mi_base_de_datos.db";
    public static final int DB_VERSION = 62; // Incrementar la versión para aplicar cambios en la base de datos

    // Tabla de usuarios
    public static final String TABLE_USUARIOS = "usuarios";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_ACTIVO = "activo"; // Nuevo campo para saber si el usuario está activo

    // Tabla de rutinas
    public static final String TABLE_RUTINAS = "rutinas";
    public static final String COLUMN_RUTINA_ID = "id";
    public static final String COLUMN_USUARIO_ID = "usuario_id";
    public static final String COLUMN_DIA = "dia";
    public static final String COLUMN_MUSCULOS = "musculos";
    public static final String COLUMN_EJERCICIO = "ejercicio"; // Nueva columna para el ejercicio
    public static final String COLUMN_IMAGEN = "imagen"; // Nueva columna para el recurso de la imagen

    // Tabla de nutrientes
    public static final String TABLE_NUTRIENTES = "nutrientes";
    public static final String COLUMN_NUTRIENTE_ID = "id";
    public static final String COLUMN_USUARIO_ID_NUTRIENTE = "usuario_id";
    public static final String COLUMN_PROTEINAS = "proteinas";
    public static final String COLUMN_CARBOHIDRATOS = "carbohidratos";
    public static final String COLUMN_GRASAS = "grasas";
    public static final String COLUMN_GRASAS_TOTALES = "grasas_totales";
    public static final String COLUMN_TIPO_DIETA = "tipo_dieta";



    // Tabla de progreso corporal
    public static final String TABLE_PROGRESO_CORPORAL = "progreso_corporal";
    public static final String COLUMN_PROGRESO_ID = "id";
    public static final String COLUMN_USUARIO_PROGRESO_ID = "usuario_id";
    public static final String COLUMN_PESO = "peso";
    public static final String COLUMN_ALTURA = "altura";
    public static final String COLUMN_PECHO = "pecho";
    public static final String COLUMN_CINTURA = "cintura";
    public static final String COLUMN_BRAZO = "brazo";

    // Tabla de fotos de progreso
    public static final String TABLE_FOTOS_PROGRESO = "fotos_progreso";
    public static final String COLUMN_FOTO_ID = "id";
    public static final String COLUMN_USUARIO_FOTO_ID = "usuario_id";
    public static final String COLUMN_FOTO = "foto";
    public static final String COLUMN_FECHA = "fecha";

    // Tabla de estado de botones
    public static final String TABLE_ESTADO_BOTONES = "estado_botones";
    public static final String COLUMN_BOTON_ID = "id";
    public static final String COLUMN_BOTON_NOMBRE = "nombre";
    public static final String COLUMN_BOTON_VISIBLE = "visible";
    public static final String COLUMN_USUARIO_ID_BOTON = "usuario_id"; // Nueva columna para el ID del usuario

    // Tabla de amigos
    public static final String TABLE_AMIGOS = "amigos";
    public static final String COLUMN_AMIGO_ID = "id";
    public static final String COLUMN_USUARIO_ID_AMIGO = "usuario_id";
    public static final String COLUMN_AMIGO_USUARIO_ID = "amigo_usuario_id";

    public MiBaseDeDatos(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;  // Almacena el contexto
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_USUARIOS = "CREATE TABLE " + TABLE_USUARIOS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_ACTIVO + " INTEGER DEFAULT 1, " + // 1 para activo, 0 para inactivo
                "imagen_perfil_ruta TEXT)"; // Columna para la ruta de la imagen de perfil

        db.execSQL(CREATE_TABLE_USUARIOS);

        // Crear la tabla de rutinas con la relación con la tabla de usuarios
        String CREATE_TABLE_RUTINAS = "CREATE TABLE " + TABLE_RUTINAS + " (" +
                COLUMN_RUTINA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USUARIO_ID + " INTEGER, " +
                COLUMN_DIA + " TEXT, " +
                COLUMN_MUSCULOS + " TEXT, " +
                COLUMN_EJERCICIO + " TEXT, " + // Nueva columna para el ejercicio
                COLUMN_IMAGEN + " INTEGER, " + // Nueva columna para el recurso de la imagen
                "FOREIGN KEY(" + COLUMN_USUARIO_ID + ") REFERENCES " + TABLE_USUARIOS + "(" + COLUMN_ID + ") ON DELETE CASCADE)";
        db.execSQL(CREATE_TABLE_RUTINAS);

        // Crear tabla de nutrientes
        String CREATE_TABLE_NUTRIENTES = "CREATE TABLE " + TABLE_NUTRIENTES + " (" +
                COLUMN_NUTRIENTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USUARIO_ID_NUTRIENTE + " INTEGER, " +
                COLUMN_PROTEINAS + " TEXT, " +
                COLUMN_CARBOHIDRATOS + " TEXT, " +
                COLUMN_GRASAS + " TEXT, " +
                COLUMN_GRASAS_TOTALES + " TEXT, " +
                COLUMN_FECHA + " TEXT, " + // Columna para la fecha
                COLUMN_TIPO_DIETA + " TEXT, " + // Nueva columna para el tipo de dieta
                "FOREIGN KEY(" + COLUMN_USUARIO_ID_NUTRIENTE + ") REFERENCES " + TABLE_USUARIOS + "(" + COLUMN_ID + ") ON DELETE CASCADE)";
        db.execSQL(CREATE_TABLE_NUTRIENTES);


        // Crear la tabla de progreso corporal
        String CREATE_TABLE_PROGRESO = "CREATE TABLE " + TABLE_PROGRESO_CORPORAL + " (" +
                COLUMN_PROGRESO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USUARIO_PROGRESO_ID + " INTEGER, " +
                COLUMN_PESO + " TEXT, " +
                COLUMN_ALTURA + " TEXT, " +
                COLUMN_PECHO + " TEXT, " +
                COLUMN_CINTURA + " TEXT, " +
                COLUMN_BRAZO + " TEXT, " +
                "FOREIGN KEY(" + COLUMN_USUARIO_PROGRESO_ID + ") REFERENCES " + TABLE_USUARIOS + "(" + COLUMN_ID + ") ON DELETE CASCADE)";
        db.execSQL(CREATE_TABLE_PROGRESO);

        // Crear la tabla de fotos de progreso
        String CREATE_TABLE_FOTOS_PROGRESO = "CREATE TABLE " + TABLE_FOTOS_PROGRESO + " (" +
                COLUMN_FOTO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USUARIO_FOTO_ID + " INTEGER, " +
                COLUMN_FOTO + " BLOB, " +
                COLUMN_FECHA + " TEXT, " +
                "FOREIGN KEY(" + COLUMN_USUARIO_FOTO_ID + ") REFERENCES " + TABLE_USUARIOS + "(" + COLUMN_ID + ") ON DELETE CASCADE)";
        db.execSQL(CREATE_TABLE_FOTOS_PROGRESO);

        // Agregar la creación de la tabla en el método onCreate
        String CREATE_TABLE_ESTADO_BOTONES = "CREATE TABLE " + TABLE_ESTADO_BOTONES + " (" +
                COLUMN_BOTON_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_BOTON_NOMBRE + " TEXT, " +
                COLUMN_BOTON_VISIBLE + " INTEGER, " + // 1 para visible, 0 para no visible
                COLUMN_USUARIO_ID_BOTON + " INTEGER, " + // ID del usuario
                "FOREIGN KEY(" + COLUMN_USUARIO_ID_BOTON + ") REFERENCES " + TABLE_USUARIOS + "(" + COLUMN_ID + ") ON DELETE CASCADE)";
        db.execSQL(CREATE_TABLE_ESTADO_BOTONES);

        // Crear la tabla de amigos
        String CREATE_TABLE_AMIGOS = "CREATE TABLE " + TABLE_AMIGOS + " (" +
                COLUMN_AMIGO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USUARIO_ID_AMIGO + " INTEGER, " +
                COLUMN_AMIGO_USUARIO_ID + " INTEGER, " +
                "FOREIGN KEY(" + COLUMN_USUARIO_ID_AMIGO + ") REFERENCES " + TABLE_USUARIOS + "(" + COLUMN_ID + ") ON DELETE CASCADE, " +
                "FOREIGN KEY(" + COLUMN_AMIGO_USUARIO_ID + ") REFERENCES " + TABLE_USUARIOS + "(" + COLUMN_ID + ") ON DELETE CASCADE)";
        db.execSQL(CREATE_TABLE_AMIGOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Eliminar las tablas si existen
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RUTINAS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NUTRIENTES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROGRESO_CORPORAL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOTOS_PROGRESO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ESTADO_BOTONES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AMIGOS);



        // Crear las tablas nuevamente
        onCreate(db);
    }

    // Método para generar un ID de usuario aleatorio de 6 dígitos
    public int generarUsuarioId() {
        Random random = new Random();
        return 100000 + random.nextInt(900000);  // Genera un número entre 100000 y 999999
    }

    // Método para insertar un usuario
    public long insertarUsuario(String username, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        int usuarioId = generarUsuarioId();  // Generamos el ID aleatorio para el usuario
        values.put(COLUMN_ID, usuarioId);     // Insertamos el ID generado
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_ACTIVO, 1); // Por defecto el usuario estará activo

        return db.insert(TABLE_USUARIOS, null, values);
    }

    public boolean usuarioExiste(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID};
        String selection = COLUMN_USERNAME + "=?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query(TABLE_USUARIOS, columns, selection, selectionArgs, null, null, null);

        boolean existe = cursor != null && cursor.moveToFirst();
        if (cursor != null) {
            cursor.close();
        }
        return existe;
    }

    // Método para iniciar sesión y establecer un usuario como activo
    public boolean iniciarSesion(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID};
        String selection = COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(TABLE_USUARIOS, columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            // Desmarcar cualquier usuario activo anterior
            desmarcarUsuarioActivo();

            // Obtener el ID del usuario que ha iniciado sesión
            int usuarioId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
            // Marcar este usuario como activo
            marcarUsuarioActivo(usuarioId);
            cursor.close();
            return true;  // Inicio de sesión exitoso
        }

        if (cursor != null) {
            cursor.close();
        }
        return false;  // Credenciales incorrectas
    }

    // Método para desmarcar cualquier usuario como activo
    private void desmarcarUsuarioActivo() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ACTIVO, 0);  // Marcar como inactivo
        db.update(TABLE_USUARIOS, values, COLUMN_ACTIVO + " = 1", null);  // Desmarcar al usuario activo
    }

    // Método para marcar un usuario como activo
    private void marcarUsuarioActivo(int usuarioId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ACTIVO, 1);  // Marcar como activo
        db.update(TABLE_USUARIOS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(usuarioId)});
    }

    // Método para obtener el ID del usuario activo
    public int obtenerUsuarioIdActivo() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID}; // Seleccionamos solo el ID del usuario
        String selection = COLUMN_ACTIVO + " = 1"; // Verificamos si el usuario está activo
        Cursor cursor = db.query(TABLE_USUARIOS, columns, selection, null, null, null, null);

        int usuarioId = -1;
        if (cursor != null && cursor.moveToFirst()) {
            usuarioId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
            cursor.close();
        }
        return usuarioId;
    }

    public long insertarRutina(int usuarioId, String dia, String musculos, String ejercicio, int imagenResId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USUARIO_ID, usuarioId);
        values.put(COLUMN_DIA, dia);
        values.put(COLUMN_MUSCULOS, musculos); // Puede ser null si solo se guarda un ejercicio
        values.put(COLUMN_EJERCICIO, ejercicio); // Guardar el ejercicio
        values.put(COLUMN_IMAGEN, imagenResId); // Guardar el recurso de la imagen

        // Insertar un nuevo registro
        return db.insert(TABLE_RUTINAS, null, values);
    }

    public Cursor obtenerRutina(int usuarioId, String dia) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_MUSCULOS, COLUMN_EJERCICIO, COLUMN_IMAGEN};
        String selection = COLUMN_USUARIO_ID + "=? AND " + COLUMN_DIA + "=?";
        String[] selectionArgs = {String.valueOf(usuarioId), dia};
        return db.query(TABLE_RUTINAS, columns, selection, selectionArgs, null, null, null);
    }

    public void eliminarEjercicio(int usuarioId, String dia, String ejercicio) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RUTINAS, COLUMN_USUARIO_ID + "=? AND " + COLUMN_DIA + "=? AND " + COLUMN_EJERCICIO + "=?",
                new String[]{String.valueOf(usuarioId), dia, ejercicio});
        db.close();
    }

    // Insertar o actualizar los datos de progreso corporal
    public long insertarActualizarProgresoCorporal(int usuarioId, String peso, String altura, String pecho, String cintura, String brazo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USUARIO_PROGRESO_ID, usuarioId);
        values.put(COLUMN_PESO, peso);
        values.put(COLUMN_ALTURA, altura);
        values.put(COLUMN_PECHO, pecho);
        values.put(COLUMN_CINTURA, cintura);
        values.put(COLUMN_BRAZO, brazo);

        // Verificar si ya existe el progreso para ese usuario
        String selection = COLUMN_USUARIO_PROGRESO_ID + "=?";
        String[] selectionArgs = {String.valueOf(usuarioId)};
        Cursor cursor = db.query(TABLE_PROGRESO_CORPORAL, null, selection, selectionArgs, null, null, null);

        db.beginTransaction();
        try {
            if (cursor != null && cursor.moveToFirst()) {
                // Si ya existe, actualizar los datos
                cursor.close();
                db.update(TABLE_PROGRESO_CORPORAL, values, COLUMN_USUARIO_PROGRESO_ID + "=?", new String[]{String.valueOf(usuarioId)});
            } else {
                // Si no existe, insertar un nuevo registro
                cursor.close();
                db.insert(TABLE_PROGRESO_CORPORAL, null, values);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("MiBaseDeDatos", "Error al insertar/actualizar progreso corporal", e);
        } finally {
            db.endTransaction();
        }

        return -1; // Devolver -1 ya que no se puede obtener el ID de la fila afectada en una transacción
    }

    // Obtener el progreso corporal de un usuario
    public String obtenerProgresoCorporal(int usuarioId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_PESO, COLUMN_ALTURA, COLUMN_PECHO, COLUMN_CINTURA, COLUMN_BRAZO};
        String selection = COLUMN_USUARIO_PROGRESO_ID + "=?";
        String[] selectionArgs = {String.valueOf(usuarioId)};
        Cursor cursor = db.query(TABLE_PROGRESO_CORPORAL, columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String peso = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PESO));
            String altura = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ALTURA));
            String pecho = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PECHO));
            String cintura = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CINTURA));
            String brazo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BRAZO));
            cursor.close();
            return peso + ", " + altura + ", " + pecho + ", " + cintura + ", " + brazo;
        } else {
            cursor.close();
            return null; // No se encontró el progreso
        }
    }

    // Método para insertar nutrientes
    public long insertarNutrientes(int usuarioId, String proteinas, String carbohidratos, String grasas, String grasasTotales, String tipoDieta) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USUARIO_ID_NUTRIENTE, usuarioId);
        values.put(COLUMN_PROTEINAS, proteinas);
        values.put(COLUMN_CARBOHIDRATOS, carbohidratos);
        values.put(COLUMN_GRASAS, grasas);
        values.put(COLUMN_GRASAS_TOTALES, grasasTotales);
        values.put(COLUMN_FECHA, obtenerFechaActual()); // Guardar la fecha actual
        values.put(COLUMN_TIPO_DIETA, tipoDieta); // Guardar el tipo de dieta
        return db.insert(TABLE_NUTRIENTES, null, values);
    }

    public boolean nutrientesYaAlmacenadosHoy(int usuarioId, String tipoDieta) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_NUTRIENTE_ID};
        String selection = COLUMN_USUARIO_ID_NUTRIENTE + "=? AND " + COLUMN_FECHA + "=? AND " + COLUMN_TIPO_DIETA + "=?";
        String[] selectionArgs = {String.valueOf(usuarioId), obtenerFechaActual(), tipoDieta};
        Cursor cursor = db.query(TABLE_NUTRIENTES, columns, selection, selectionArgs, null, null, null);

        boolean yaAlmacenados = cursor.moveToFirst();
        cursor.close();
        return yaAlmacenados;
    }



    private String obtenerFechaActual() {
        // Obtener la fecha actual en formato yyyy-MM-dd
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new java.util.Date());
    }


    public boolean nutrientesYaAlmacenadosHoy(int usuarioId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_NUTRIENTE_ID};
        String selection = COLUMN_USUARIO_ID_NUTRIENTE + "=? AND " + COLUMN_FECHA + "=?";
        String[] selectionArgs = {String.valueOf(usuarioId), obtenerFechaActual()};
        Cursor cursor = db.query(TABLE_NUTRIENTES, columns, selection, selectionArgs, null, null, null);

        boolean yaAlmacenados = cursor.moveToFirst();
        cursor.close();
        return yaAlmacenados;
    }

    public String obtenerNutrientesDelDia(int usuarioId, String tipoDieta) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_PROTEINAS, COLUMN_CARBOHIDRATOS, COLUMN_GRASAS, COLUMN_GRASAS_TOTALES};
        String selection = COLUMN_USUARIO_ID_NUTRIENTE + "=? AND " + COLUMN_FECHA + "=? AND " + COLUMN_TIPO_DIETA + "=?";
        String[] selectionArgs = {String.valueOf(usuarioId), obtenerFechaActual(), tipoDieta};
        Cursor cursor = db.query(TABLE_NUTRIENTES, columns, selection, selectionArgs, null, null, null);

        StringBuilder nutrientes = new StringBuilder();
        if (cursor.moveToFirst()) {
            String proteinas = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PROTEINAS));
            String carbohidratos = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CARBOHIDRATOS));
            String grasas = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GRASAS));
            String grasasTotales = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GRASAS_TOTALES));
            nutrientes.append("Proteínas: ").append(proteinas).append(" g\n");
            nutrientes.append("Carbohidratos: ").append(carbohidratos).append(" g\n");
            nutrientes.append("Grasas: ").append(grasas).append(" g\n");
            nutrientes.append("Grasas Totales: ").append(grasasTotales).append(" kcal\n");
        }
        cursor.close();
        return nutrientes.toString();
    }



    public String[] obtenerInformacionUsuario(int usuarioId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_USERNAME, COLUMN_EMAIL, COLUMN_PASSWORD};
        String selection = COLUMN_ID + "=?";
        String[] selectionArgs = {String.valueOf(usuarioId)};
        Cursor cursor = db.query(TABLE_USUARIOS, columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String[] usuarioInfo = {
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD))
            };
            cursor.close();
            return usuarioInfo;
        } else {
            cursor.close();
            return null;
        }
    }

    // Método para actualizar la ruta de la imagen de perfil del usuario
    public boolean actualizarImagenPerfil(int usuarioId, String imagenPerfilRuta) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("imagen_perfil_ruta", imagenPerfilRuta);

        int rowsAffected = db.update(TABLE_USUARIOS, values, COLUMN_ID + "=?", new String[]{String.valueOf(usuarioId)});
        return rowsAffected > 0;
    }

    // Método para obtener la ruta de la imagen de perfil
    public String obtenerRutaImagenPerfil(int usuarioId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"imagen_perfil_ruta"};
        String selection = COLUMN_ID + "=?";
        String[] selectionArgs = {String.valueOf(usuarioId)};
        Cursor cursor = db.query(TABLE_USUARIOS, columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String imagenPerfilRuta = cursor.getString(cursor.getColumnIndexOrThrow("imagen_perfil_ruta"));
            cursor.close();
            return imagenPerfilRuta;
        } else {
            cursor.close();
            return null;
        }
    }


    // Método para insertar una foto de progreso
    public long insertarFotoProgreso(int usuarioId, Bitmap foto, String fecha) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USUARIO_FOTO_ID, usuarioId);
        values.put(COLUMN_FOTO, bitmapToByteArray(foto));
        values.put(COLUMN_FECHA, fecha);
        return db.insert(TABLE_FOTOS_PROGRESO, null, values);
    }

    // Método para obtener todas las fotos de progreso de un usuario
    public Cursor obtenerFotosProgreso(int usuarioId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_FOTO_ID, COLUMN_FOTO, COLUMN_FECHA};
        String selection = COLUMN_USUARIO_FOTO_ID + "=?";
        String[] selectionArgs = {String.valueOf(usuarioId)};
        return db.query(TABLE_FOTOS_PROGRESO, columns, selection, selectionArgs, null, null, null);
    }

    // Método para convertir un Bitmap a un array de bytes
    private byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    // Método para convertir un array de bytes a un Bitmap
    private Bitmap byteArrayToBitmap(byte[] byteArray) {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }

    // Método para eliminar una foto de progreso
    public boolean eliminarFotoProgreso(int fotoId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_FOTOS_PROGRESO, COLUMN_FOTO_ID + "=?", new String[]{String.valueOf(fotoId)}) > 0;
    }

    public boolean eliminarUsuario(int usuarioId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_USUARIOS, COLUMN_ID + "=?", new String[]{String.valueOf(usuarioId)}) > 0;
    }

    // Método para insertar o actualizar el estado de un botón para un usuario específico
    public void actualizarEstadoBoton(int usuarioId, String nombreBoton, boolean esVisible) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_BOTON_NOMBRE, nombreBoton);
        values.put(COLUMN_BOTON_VISIBLE, esVisible ? 1 : 0);
        values.put(COLUMN_USUARIO_ID_BOTON, usuarioId);

        // Verificar si ya existe un registro para el botón y el usuario
        String selection = COLUMN_BOTON_NOMBRE + "=? AND " + COLUMN_USUARIO_ID_BOTON + "=?";
        String[] selectionArgs = {nombreBoton, String.valueOf(usuarioId)};
        Cursor cursor = db.query(TABLE_ESTADO_BOTONES, null, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            // Si existe, actualizar el estado
            db.update(TABLE_ESTADO_BOTONES, values, COLUMN_BOTON_NOMBRE + "=? AND " + COLUMN_USUARIO_ID_BOTON + "=?", selectionArgs);
            cursor.close();
        } else {
            // Si no existe, insertar un nuevo registro
            db.insert(TABLE_ESTADO_BOTONES, null, values);
        }
    }

    // Método para obtener el estado de un botón para un usuario específico
    public boolean obtenerEstadoBoton(int usuarioId, String nombreBoton) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_BOTON_VISIBLE};
        String selection = COLUMN_BOTON_NOMBRE + "=? AND " + COLUMN_USUARIO_ID_BOTON + "=?";
        String[] selectionArgs = {nombreBoton, String.valueOf(usuarioId)};
        Cursor cursor = db.query(TABLE_ESTADO_BOTONES, columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int esVisible = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BOTON_VISIBLE));
            cursor.close();
            return esVisible == 1;
        } else {
            cursor.close();
            return false; // Por defecto, el botón no es visible
        }
    }

    public List<Usuario> buscarUsuariosPorNombre(String nombre) {
        List<Usuario> usuarios = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_USERNAME, "imagen_perfil_ruta"};
        String selection = COLUMN_USERNAME + " LIKE ?";
        String[] selectionArgs = {"%" + nombre + "%"};
        Cursor cursor = db.query(TABLE_USUARIOS, columns, selection, selectionArgs, null, null, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
            String nombreUsuario = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME));
            String imagenPerfilRuta = cursor.getString(cursor.getColumnIndexOrThrow("imagen_perfil_ruta"));
            usuarios.add(new Usuario(id, nombreUsuario, imagenPerfilRuta));
        }
        cursor.close();
        return usuarios;
    }

    // Método para agregar un amigo
    public long agregarAmigo(int usuarioId, int amigoId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USUARIO_ID_AMIGO, usuarioId);
        values.put(COLUMN_AMIGO_USUARIO_ID, amigoId);
        return db.insert(TABLE_AMIGOS, null, values);
    }

    // Método para obtener todos los amigos de un usuario
    public List<Usuario> obtenerAmigos(int usuarioId) {
        List<Usuario> amigos = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_AMIGO_USUARIO_ID};
        String selection = COLUMN_USUARIO_ID_AMIGO + "=?";
        String[] selectionArgs = {String.valueOf(usuarioId)};
        Cursor cursor = db.query(TABLE_AMIGOS, columns, selection, selectionArgs, null, null, null);

        while (cursor.moveToNext()) {
            int amigoId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_AMIGO_USUARIO_ID));
            String[] amigoInfo = obtenerInformacionUsuario(amigoId);
            if (amigoInfo != null) {
                String imagenPerfilRuta = obtenerRutaImagenPerfil(amigoId);
                amigos.add(new Usuario(amigoId, amigoInfo[0], imagenPerfilRuta));
            }
        }
        cursor.close();
        return amigos;
    }

    // Método para eliminar un amigo
    public boolean eliminarAmigo(int usuarioId, int amigoId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_USUARIO_ID_AMIGO + "=? AND " + COLUMN_AMIGO_USUARIO_ID + "=?";
        String[] selectionArgs = {String.valueOf(usuarioId), String.valueOf(amigoId)};
        return db.delete(TABLE_AMIGOS, selection, selectionArgs) > 0;
    }
}

