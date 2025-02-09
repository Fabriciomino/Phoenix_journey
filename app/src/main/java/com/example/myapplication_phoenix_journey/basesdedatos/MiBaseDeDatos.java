package com.example.myapplication_phoenix_journey.basesdedatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.Random;

public class MiBaseDeDatos extends SQLiteOpenHelper {
    private Context context;


    public static final String DB_NAME = "mi_base_de_datos.db";
    public static final int DB_VERSION = 28; // Incrementar la versión para aplicar cambios en la base de datos

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

    // Tabla de progreso corporal
    public static final String TABLE_PROGRESO_CORPORAL = "progreso_corporal";
    public static final String COLUMN_PROGRESO_ID = "id";
    public static final String COLUMN_USUARIO_PROGRESO_ID = "usuario_id";
    public static final String COLUMN_PESO = "peso";
    public static final String COLUMN_ALTURA = "altura";
    public static final String COLUMN_PECHO = "pecho";
    public static final String COLUMN_CINTURA = "cintura";
    public static final String COLUMN_BRAZO = "brazo";

    public MiBaseDeDatos(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;  // Almacena el contexto


    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear la tabla de usuarios con el campo 'activo'
        String CREATE_TABLE_USUARIOS = "CREATE TABLE " + TABLE_USUARIOS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_ACTIVO + " INTEGER DEFAULT 1)"; // 1 para activo, 0 para inactivo
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

        // Crear tablas para los ejercicios de cada músculo (por ejemplo, Hombro)
        String CREATE_TABLE_HOMBRO = "CREATE TABLE hombro (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ejercicio TEXT, " +
                "descripcion TEXT)";
        db.execSQL(CREATE_TABLE_HOMBRO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Si necesitas borrar la base de datos en caso de que la versión haya cambiado
        if (oldVersion < newVersion) {
            // Elimina la base de datos existente antes de actualizarla
            context.deleteDatabase(DB_NAME); // Elimina la base de datos
        }

        // Aquí puedes realizar las actualizaciones necesarias, por ejemplo:
        if (oldVersion < 11) {
            // Añadir columna de imagen
            db.execSQL("ALTER TABLE " + TABLE_RUTINAS + " ADD COLUMN " + COLUMN_IMAGEN + " INTEGER;");
        }
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

        cursor.close();
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

        if (cursor != null && cursor.moveToFirst()) {
            // Si ya existe, actualizar los datos
            cursor.close();
            return db.update(TABLE_PROGRESO_CORPORAL, values, COLUMN_USUARIO_PROGRESO_ID + "=?", new String[]{String.valueOf(usuarioId)});
        } else {
            // Si no existe, insertar un nuevo registro
            cursor.close();
            return db.insert(TABLE_PROGRESO_CORPORAL, null, values);
        }
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
    public long insertarNutrientes(int usuarioId, String proteinas, String carbohidratos, String grasas, String grasasTotales) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USUARIO_ID_NUTRIENTE, usuarioId);
        values.put(COLUMN_PROTEINAS, proteinas);
        values.put(COLUMN_CARBOHIDRATOS, carbohidratos);
        values.put(COLUMN_GRASAS, grasas);
        values.put(COLUMN_GRASAS_TOTALES, grasasTotales);
        return db.insert(TABLE_NUTRIENTES, null, values);
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

    public boolean eliminarUsuario(int usuarioId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_USUARIOS, COLUMN_ID + "=?", new String[]{String.valueOf(usuarioId)}) > 0;
    }
}