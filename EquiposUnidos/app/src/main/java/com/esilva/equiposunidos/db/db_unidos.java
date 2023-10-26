package com.esilva.equiposunidos.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class db_unidos extends SQLiteOpenHelper {
    private static final String NAME_DB = "db_unidos";
    private static final int VERSION = 1;

    //  TABLA  USUARIO
    public static final String TABLE_USUARIO = "tb_usuario";

    public static final String BASE_CEDULA=     "cedula";
    public static final String BASE_NOMBRE =    "usuario";
    public static final String BASE_HUELLA1=     "pulgar";
    public static final String BASE_HUELLA2=     "indice";
    public static final String BASE_HUELLA3=     "medio";
    public static final String BASE_PERFIL=     "perfil";
    public static final String BASE_CARGO=      "cargo";
    public static final String BASE_FOTO=      "foto";
    public static final String BASE_ISENROLADO= "enrolado";


    public db_unidos(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, NAME_DB, factory, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase Database) {
        String base = "CREATE TABLE "+ TABLE_USUARIO+"(" +
                BASE_CEDULA     +" INTEGER PRIMARY KEY, " +
                BASE_NOMBRE     +" TEXT, " +
                BASE_PERFIL     +" INTEGER, " +
                BASE_CARGO      +" TEXT, " +
                BASE_FOTO       +" TEXT, " +
                BASE_ISENROLADO +" INTEGER, " +
                BASE_HUELLA1    +" BLOB, " +
                BASE_HUELLA2    +" BLOB, " +
                BASE_HUELLA3    +" BLOB)";
        Database.execSQL(base);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
