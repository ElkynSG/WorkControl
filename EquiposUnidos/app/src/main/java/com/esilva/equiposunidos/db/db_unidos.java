package com.esilva.equiposunidos.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class db_unidos extends SQLiteOpenHelper {
    private static final String NAME_DB = "db_unidos";
    private static final int VERSION = 3;

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

    //  TABLA  Caterpila
    public static final String TABLE_CATERPILA = "tb_caterpila";

    public static final String CAT_ID = "id_cat";
    public  static final String CAT_ACEITE_MOTOR="cat_motor";
    public  static final String CAT_COMB_BF13="cat_BF23";
    public  static final String CAT_COMB_BF77="cat_BF77";
    public  static final String CAT_SERVO="cat_servo";
    public  static final String CAT_CABINA="cat_cabina";
    public  static final String CAT_HIDRA_BT93="cat_hibt93";
    public  static final String CAT_HIDRA_BT83="cat_hidr83";
    public  static final String CAT_AIRE_INTER="cat_inter";
    public  static final String CAT_AIRE_EXTER="cat_exter";
    public  static final String CAT_PREFIJO="cat_prefijo";

    //  TABLA  Doorsan
    public static final String TABLE_DOORSAN = "tb_doorsan";

    public static final String DOOR_ID = "id_door";
    public static final String DOOR_ACEITE_MOTOR = "door_motor";
    public static final String DOOR_CONB_4005 = "door_conb05";
    public static final String DOOR_CONB_4004 = "door_con04";
    public static final String DOOR_SERVO = "door_servo";
    public static final String DOOR_PILOTO = "door_piloto";
    public static final String DOOR_HIDRAILI = "door_hidra";
    public static final String DOOR_AIRE_CABI = "door_cabina";
    public static final String DOOR_AIRE_INTER = "door_inter";
    public static final String DOOR_AIRE_EXTER = "door_exter";
    public static final String DOOR_PREFIJO = "door_prefijo";

    //  TABLA  Koma01
    public static final String TABLE_KOMAT_01 = "tb_kom1";

    public static final String KOMA1_ID = "id_kom01";
    public static final String KOMA1_MOTOR = "kom01_motor";
    public static final String KOMA1_TRMPA = "kom01_trampa";
    public static final String KOMA1_COMBUS = "kom01_conbus";
    public static final String KOMA1_CABINA = "kom01_cabina";
    public static final String KOMA1_HIDRAU = "kom01_hidra";
    public static final String KOMA1_RESPIR = "kom01_respi";
    public static final String KOMA1_PILOTO = "kom01_piloto";
    public static final String KOMA1_INTER = "kom01_inter";
    public static final String KOMA1_EXTERN = "kom01_exter";

    //  TABLA  Koma02
    public static final String TABLE_KOMAT_2 = "tb_koma2";

    public static final String KOMA2_ID =     "id_kom2";
    public static final String KOMA2_MOTOR =  "komat2_motor";
    public static final String KOMA2_TRMPA =  "komat2_trampa";
    public static final String KOMA2_COMBUS = "komat2_conbus";
    public static final String KOMA2_CABINA = "komat2_cabina";
    public static final String KOMA2_HIDRAU = "komat2_hidra";
    public static final String KOMA2_RESPIR = "komat2_respi";
    public static final String KOMA2_PILOTO = "komat2_piloto";
    public static final String KOMA2_INTER =  "komat2_inter";
    public static final String KOMA2_EXTERN = "komat2_exter";
    public static final String KOMA2_REFRIG= "komat2_refrig";

    //  TABLA  Aceites
    public static final String TABLE_ACEITES = "tb_aceites";

    public static final String ACEI_ID =     "id_aceite";
    public static final String ACEI_A15W40 = "acei_15w40";
    public static final String ACEI_ISO68 = "acei_iso68";
    public static final String ACEI_TO30 = "acei_to30";
    public static final String ACEI_A80W90 = "acei_80w90";
    public static final String ACEI_S527 = "acei_s527";
    public static final String ACEI_G_LITIO = "acei_glitio";
    public static final String ACEI_MOTOR = "acei_motor";

    //  TABLA  Micelanio
    public static final String TABLE_MICELANIO = "tb_micela";

    public static final String MICEL_ID =     "id_micel";
    public static final String MICEL_AC_R134 = "micel_acr1";
    public static final String MICEL_X70 = "micel_x70";

    //  TABLA  Historial
    public static final String TABLE_HISTORIAL = "tb_historial";

    public static final String NUM_ITEMS =     "numItems";
    public static final String HIS_IS_ADD =    "his_add";
    public static final String HIS_FECHA =     "his_fecha";
    public static final String HIS_USER =      "his_user";
    public static final String HIS_ITEMS =     "items";


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

        //////////////////////////// Inventario   ///////////////////////////////////
        String caterpela = "CREATE TABLE " + TABLE_CATERPILA + "(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CAT_ID +            " INTEGER, " +
                CAT_ACEITE_MOTOR +  " INTEGER, " +
                CAT_COMB_BF13 +     " INTEGER, " +
                CAT_COMB_BF77 +     " INTEGER, " +
                CAT_SERVO +         " INTEGER, " +
                CAT_CABINA +        " INTEGER, " +
                CAT_HIDRA_BT93 +    " INTEGER, " +
                CAT_HIDRA_BT83 +    " INTEGER, " +
                CAT_AIRE_INTER +    " INTEGER, " +
                CAT_AIRE_EXTER +    " INTEGER, " +
                CAT_PREFIJO +       " INTEGER)";
        Database.execSQL(caterpela);

        String doorsan = "CREATE TABLE " + TABLE_DOORSAN + "(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DOOR_ID +            " INTEGER, " +
                DOOR_ACEITE_MOTOR +  " INTEGER, " +
                DOOR_CONB_4005 +     " INTEGER, " +
                DOOR_CONB_4004 +     " INTEGER, " +
                DOOR_SERVO +         " INTEGER, " +
                DOOR_PILOTO +        " INTEGER, " +
                DOOR_HIDRAILI +      " INTEGER, " +
                DOOR_AIRE_CABI +     " INTEGER, " +
                DOOR_AIRE_INTER +    " INTEGER, " +
                DOOR_AIRE_EXTER+     " INTEGER, " +
                DOOR_PREFIJO +       " INTEGER)";
        Database.execSQL(doorsan);

        String koma1 = "CREATE TABLE " + TABLE_KOMAT_01 + "(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KOMA1_ID +       " INTEGER, " +
                KOMA1_MOTOR+     " INTEGER, " +
                KOMA1_TRMPA +    " INTEGER, " +
                KOMA1_COMBUS +   " INTEGER, " +
                KOMA1_CABINA +   " INTEGER, " +
                KOMA1_HIDRAU +   " INTEGER, " +
                KOMA1_RESPIR +   " INTEGER, " +
                KOMA1_PILOTO +   " INTEGER, " +
                KOMA1_INTER +    " INTEGER, " +
                KOMA1_EXTERN +   " INTEGER)";
        Database.execSQL(koma1);

        String koma2 = "CREATE TABLE " + TABLE_KOMAT_2 + "(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KOMA2_ID +       " INTEGER, " +
                KOMA2_MOTOR+     " INTEGER, " +
                KOMA2_TRMPA +    " INTEGER, " +
                KOMA2_COMBUS +   " INTEGER, " +
                KOMA2_CABINA +   " INTEGER, " +
                KOMA2_HIDRAU +   " INTEGER, " +
                KOMA2_RESPIR +   " INTEGER, " +
                KOMA2_PILOTO +   " INTEGER, " +
                KOMA2_INTER +    " INTEGER, " +
                KOMA2_EXTERN +   " INTEGER, " +
                KOMA2_REFRIG +   " INTEGER)";
        Database.execSQL(koma2);

        String aceites = "CREATE TABLE " + TABLE_ACEITES + "(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ACEI_ID +      " INTEGER, " +
                ACEI_A15W40+   " INTEGER, " +
                ACEI_ISO68 +   " INTEGER, " +
                ACEI_TO30 +    " INTEGER, " +
                ACEI_A80W90 +  " INTEGER, " +
                ACEI_S527 +    " INTEGER, " +
                ACEI_G_LITIO + " INTEGER, " +
                ACEI_MOTOR +   " INTEGER)";
        Database.execSQL(aceites);

        String micel = "CREATE TABLE " + TABLE_MICELANIO + "(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MICEL_ID +      " INTEGER, " +
                MICEL_AC_R134+  " INTEGER, " +
                MICEL_X70 +     " INTEGER)";
        Database.execSQL(micel);

        String inventario = "CREATE TABLE " + TABLE_HISTORIAL + "(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NUM_ITEMS  +      " INTEGER, " +
                HIS_IS_ADD  +     " INTEGER, " +
                HIS_FECHA  +      " TEXT, " +
                HIS_USER   +      " TEXT, " +
                HIS_ITEMS  +      " TEXT)";
        Database.execSQL(inventario);

        Log.d("DP_DLOG","onCreate "+"crea tablas de la base de datos");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.d("DP_DLOG","onUpgrade "+"Actualiza base de datos");
    }
}
