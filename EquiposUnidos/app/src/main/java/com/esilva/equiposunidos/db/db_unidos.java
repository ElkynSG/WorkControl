package com.esilva.equiposunidos.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class db_unidos extends SQLiteOpenHelper {
    private static final String NAME_DB = "db_unidos";
    private static final int VERSION = 1;

    //  TABLA  USUARIO
    public static final String TABLE_USUARIO = "tb_usuario";

    public static final String USU_CEDULA=     "cedula";
    public static final String USU_NOMBRE =    "usuario";
    public static final String USU_HUELLA1=     "pulgar";
    public static final String USU_HUELLA2=     "indice";
    public static final String USU_HUELLA3=     "medio";
    public static final String USU_PERFIL=     "perfil";
    public static final String USU_CARGO=      "cargo";
    public static final String USU_FOTO=      "foto";
    public static final String USU_ISENROLADO= "enrolado";

    //  TABLA  USUARIO
    public static final String TABLE_IN_OUT = "tb_registros";

    public static final String REG_CEDULA=          USU_CEDULA;
    public static final String REG_DIA =            "dia";
    public static final String REG_DIA_MES =        "dia_mes";
    public static final String REG_FECHA_IN=        "fecha_input";
    public static final String REG_COMENTARIO_IN=   "coment_input";
    public static final String REG_FECHA_OUT=       "fecha_output";
    public static final String REG_COMENTARIO_OUT=  "coment_output";
    public static final String REG_EQUIPO_IN=          "maquina_in";
    public static final String REG_EQUIPO_OUT=          "maquina_out";
    public static final String REG_ACTIVIDAD_IN=       "actividad_in";
    public static final String REG_ACTIVIDAD_OUT=       "actividad_out";
    public static final String REG_COOR_LATITUD_IN=    "latitud_IN";
    public static final String REG_COOR_LONGITUD_IN=   "longitud_IN";
    public static final String REG_COOR_LATITUD_OUT=    "latitud_OUT";
    public static final String REG_COOR_LONGITUD_OUT=   "longitud_OUT";

    //  TABLA  equipos
    public static final String TABLE_EQUIPOS = "tb_equipos";

    public static final String EQUI_NOMBRE =        "name";
    public static final String EQUI_NUM_REGISTRO =  "register";
    public static final String EQUI_EQUIPO =        "equipo";
    public static final String EQUI_NUM_MOTOR =     "engine";
    public static final String EQUI_NUM_SERIE =     "serie";
    public static final String EQUI_PROPIETARIO =   "propietary";
    public static final String EQUI_PLACA =         "placa";
    public static final String EQUI_TIPO =          "tipo";
    public static final String EQUI_FOTO =          "image";

    //  TABLA  Actividades
    public static final String TABLE_ACTIVIDAD = "tb_actividad";

    public static final String ACT_NOMBRE =    "NOMBRE";

    //  TABLA  Tipo Mantenimiento
    public static final String TABLE_MANTENIMIENTO = "tb_mantenimiento";

    public static final String MANTE_NOMBRE =    "NOMBRE";

    //  TABLA  Lugares
    public static final String TABLE_LUGARES = "tb_lugares";

    public static final String LUGA_NOMBRE =    "NOMBRE";

    //  TABLA  Tecnicos
    public static final String TABLE_TECNICOS = "tb_tecnicos";

    public static final String TEC_NOMBRE =    "NOMBRE";



    public db_unidos(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, NAME_DB, factory, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase Database) {

        ////   Usuarios   ///////
        String base = "CREATE TABLE " + TABLE_USUARIO + "(" +
                USU_CEDULA + " INTEGER PRIMARY KEY, " +
                USU_NOMBRE + " TEXT, " +
                USU_PERFIL + " INTEGER, " +
                USU_CARGO + " TEXT, " +
                USU_FOTO + " TEXT, " +
                USU_ISENROLADO + " INTEGER, " +
                USU_HUELLA1 + " BLOB, " +
                USU_HUELLA2 + " BLOB, " +
                USU_HUELLA3 + " BLOB)";
        Database.execSQL(base);

        ////   Registro de ingresos   ///////
        String feren = "FOREIGN KEY ( " + REG_CEDULA + " ) REFERENCES " + TABLE_USUARIO + " ( " + USU_CEDULA + " )";
        String inout = "CREATE TABLE " + TABLE_IN_OUT + "(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT,"+
                USU_CEDULA +            " INTEGER, " +
                REG_DIA +               " TEXT, " +
                REG_DIA_MES +           " INTEGER, " +
                REG_FECHA_IN +          " TEXT, " +
                REG_COMENTARIO_IN +     " TEXT, " +
                REG_FECHA_OUT +         " TEXT, " +
                REG_COMENTARIO_OUT +    " TEXT, " +
                REG_EQUIPO_IN +         " TEXT, " +
                REG_ACTIVIDAD_IN +      " TEXT, " +
                REG_EQUIPO_OUT +        " TEXT, " +
                REG_ACTIVIDAD_OUT +     " TEXT, " +
                REG_COOR_LATITUD_IN +   " TEXT, " +
                REG_COOR_LONGITUD_IN +  " TEXT, " +
                REG_COOR_LATITUD_OUT +  " TEXT, " +
                REG_COOR_LONGITUD_OUT + " TEXT, " +
                "FOREIGN KEY (" + REG_CEDULA +") REFERENCES  " + TABLE_USUARIO + " (" + REG_CEDULA + "))";
        Database.execSQL(inout);

        ///// Actividades   ////////////
        String act = "CREATE TABLE " + TABLE_ACTIVIDAD + "(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ACT_NOMBRE + " TEXT)";
        Database.execSQL(act);

        ///// Equipos   ////////////
        String equi = "CREATE TABLE " + TABLE_EQUIPOS + "(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                EQUI_NOMBRE +       " TEXT, " +
                EQUI_NUM_REGISTRO + " TEXT, " +
                EQUI_EQUIPO +       " TEXT, " +
                EQUI_NUM_MOTOR +    " TEXT, " +
                EQUI_NUM_SERIE +    " TEXT, " +
                EQUI_PROPIETARIO +  " TEXT, " +
                EQUI_PLACA +        " TEXT, " +
                EQUI_TIPO +         " INTEGER, " +
                EQUI_FOTO +         " TEXT)";
        Database.execSQL(equi);

        ///// Tipo Mantenimiento   ////////////
        String mante = "CREATE TABLE " + TABLE_MANTENIMIENTO + "(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MANTE_NOMBRE + " TEXT)";
        Database.execSQL(mante);

        ///// Lugares   ////////////
        String lug = "CREATE TABLE " + TABLE_LUGARES + "(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                LUGA_NOMBRE + " TEXT)";
        Database.execSQL(lug);

        ///// Tecnicos   ////////////
        String tec = "CREATE TABLE " + TABLE_TECNICOS + "(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TEC_NOMBRE + " TEXT)";
        Database.execSQL(tec);

        Log.d("DP_DLOG","onCreate "+"crea tablas de la base de datos");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.d("DP_DLOG","onUpgrade "+"Actualiza base de datos");
    }
}
