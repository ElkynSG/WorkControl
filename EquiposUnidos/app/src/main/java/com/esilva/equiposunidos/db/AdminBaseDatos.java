package com.esilva.equiposunidos.db;




import static com.esilva.equiposunidos.db.db_unidos.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.esilva.equiposunidos.db.models.Equipos;
import com.esilva.equiposunidos.db.models.Historial;
import com.esilva.equiposunidos.db.models.InventarioAceite;
import com.esilva.equiposunidos.db.models.InventarioCaterpila;
import com.esilva.equiposunidos.db.models.InventarioDoorsan;
import com.esilva.equiposunidos.db.models.InventarioKomatsu1;
import com.esilva.equiposunidos.db.models.InventarioKomatsu2;
import com.esilva.equiposunidos.db.models.InventarioMicelanio;
import com.esilva.equiposunidos.db.models.Registros;
import com.esilva.equiposunidos.db.models.User;

import java.util.ArrayList;
import java.util.List;

public class AdminBaseDatos {

    private Context context;
    private SQLiteDatabase BaseDeDatos;

    public AdminBaseDatos(Context context) {
        this.context = context;
        db_unidos admin = new db_unidos(context, null);
        BaseDeDatos = admin.getWritableDatabase();
    }

    //  TODO Tabla USUARIOS
    public long usu_insert(User usuario){
        //INSERT INTO USUARIOS (CEDULA, NAMBRE, CARGO, PERFIL) VALUES (1050200224, 'RUBIO', 'OFIC', 2)
        try {
            ContentValues registro = new ContentValues();
            registro.put(USU_CEDULA, usuario.getCedula());
            registro.put(USU_NOMBRE, usuario.getNombre());
            registro.put(USU_PERFIL, usuario.getPerfil());
            registro.put(USU_CARGO, usuario.getCargo());
            registro.put(USU_FOTO, usuario.getFoto());
            return BaseDeDatos.insert(TABLE_USUARIO,null,registro);
        }catch (Exception e){
            return 0;
        }
    }
    public User usu_getByUser(int cedula){
        User usuario = new User();

        try {
            Cursor fila = BaseDeDatos.rawQuery("SELECT * FROM "+ TABLE_USUARIO+
                    " WHERE "+USU_CEDULA+ "="+String.valueOf(cedula),null);

            if(fila == null )
                return null;

            if(!fila.moveToFirst())
                return null;

            for(fila.moveToFirst(); !fila.isAfterLast(); fila.moveToNext()){
                usuario.setCedula(fila.getInt(0));
                usuario.setNombre(fila.getString(1));
                usuario.setPerfil(fila.getInt(2));
                usuario.setCargo(fila.getString(3));
                usuario.setFoto(fila.getString(4));
                usuario.setIsEnrolado(fila.getInt(5));
                usuario.setHuellaPulgar(fila.getBlob(6));
                usuario.setHuellaIndice(fila.getBlob(7));
                usuario.setHuellaMedio(fila.getBlob(8));
            }
        }catch (Exception e){
            return null;
        }

        return usuario;
    }
    public Boolean usu_update(User usuario){
        //UPDATE USUARIOS SET NAMBRE='eLKYN',CARGO='OPERADOR',PERFIL=2 WHERE CEDULA=1
        try {
            String sentence = "UPDATE "+ TABLE_USUARIO+" SET "+USU_HUELLA1+
                    "=?,"+USU_HUELLA2+"=?,"+USU_HUELLA3+"=?,"+ USU_ISENROLADO+ "="+String.valueOf(usuario.getIsEnrolado())+ " WHERE "+USU_CEDULA+" = ?";
            BaseDeDatos.execSQL(sentence, new Object[]{usuario.getHuellaPulgar(), usuario.getHuellaIndice(), usuario.getHuellaMedio(), usuario.getCedula()});
        }catch (Exception e){
            return false;
        }

        return true;

    }

    public List<String> user_getAll_string(){
        List<String> user = new ArrayList<String>();

        try {
            Cursor fila = BaseDeDatos.rawQuery("SELECT * FROM "+ TABLE_USUARIO,null);

            if(fila == null )
                return null;

            if(!fila.moveToFirst())
                return null;

            for(fila.moveToFirst(); !fila.isAfterLast(); fila.moveToNext()){
                user.add(fila.getString(1));
            }
        }catch (Exception e){
            return null;
        }

        return user;
    }

    public List<User> user_getSupervisores(){
        List<User> user = new ArrayList<User>();

        try {
            Cursor fila = BaseDeDatos.rawQuery(
                    "SELECT * FROM " + TABLE_USUARIO +
                            " WHERE " +USU_CARGO+ " IN  (?, ?, ?, ?)",
                    new String[]{"Gerente", "Lider de Mantenimiento","Coordinadora HSEQ","Inspector HSEQ"}
            );

            if(fila == null )
                return null;

            if(!fila.moveToFirst())
                return null;

            for(fila.moveToFirst(); !fila.isAfterLast(); fila.moveToNext()){
                User numItem = new User();
                numItem.setCedula(fila.getInt(0));
                numItem.setNombre(fila.getString(1));
                numItem.setPerfil(fila.getInt(2));
                numItem.setCargo(fila.getString(3));
                user.add(numItem);
            }

            fila.close();
        }catch (Exception e){
            return null;
        }

        return user;
    }
    public List<User> usu_getAll(){
        List<User> usuarios = new ArrayList<User>();

        try {
            Cursor fila = BaseDeDatos.rawQuery("SELECT * FROM "+ TABLE_USUARIO,null);

            if(fila == null )
                return null;

            if(!fila.moveToFirst())
                return null;

            for(fila.moveToFirst(); !fila.isAfterLast(); fila.moveToNext()){
                User numItem = new User();
                numItem.setCedula(fila.getInt(0));
                numItem.setNombre(fila.getString(1));
                numItem.setPerfil(fila.getInt(2));
                numItem.setCargo(fila.getString(3));
                numItem.setFoto(fila.getString(4));
                numItem.setIsEnrolado(fila.getInt(5));
                numItem.setHuellaPulgar(fila.getBlob(6));
                numItem.setHuellaIndice(fila.getBlob(7));
                numItem.setHuellaMedio(fila.getBlob(8));
                usuarios.add(numItem);
            }
        }catch (Exception e){
            return null;
        }

        return usuarios;
    }
    public List<User> usu_getAll_Enrroled(){
        List<User> usuarios = new ArrayList<User>();

        try {
            Cursor fila = BaseDeDatos.rawQuery("SELECT * FROM "+ TABLE_USUARIO +" WHERE "+USU_ISENROLADO+" = 1",null);

            if(fila == null )
                return null;

            if(!fila.moveToFirst())
                return null;

            for(fila.moveToFirst(); !fila.isAfterLast(); fila.moveToNext()){
                User numItem = new User();
                numItem.setCedula(fila.getInt(0));
                numItem.setNombre(fila.getString(1));
                numItem.setPerfil(fila.getInt(2));
                numItem.setCargo(fila.getString(3));
                numItem.setFoto(fila.getString(4));
                numItem.setIsEnrolado(fila.getInt(5));
                numItem.setHuellaPulgar(fila.getBlob(6));
                numItem.setHuellaIndice(fila.getBlob(7));
                numItem.setHuellaMedio(fila.getBlob(8));
                usuarios.add(numItem);
            }
        }catch (Exception e){
            return null;
        }

        return usuarios;
    }
    public void usu_deleteUsers(){
        BaseDeDatos.delete(TABLE_USUARIO,null,null);
    }
    public int usu_deleteByUser(int cedula){
        return BaseDeDatos.delete(TABLE_USUARIO,USU_CEDULA + " = ?",new String[] { String.valueOf(cedula) });
    }
    public boolean usu_deleteeMonthBefore(String date){

        String sentenciaSQL = "DELETE FROM " +TABLE_IN_OUT +" WHERE "+REG_FECHA_IN+" < ?";
        String[] argumentos = {date};

        try {
            BaseDeDatos.execSQL(sentenciaSQL, argumentos);
            return true;
        }catch (Exception e){
            return false;
        }
    }
    public Boolean usu_isExistUsers(){
        try {
            Cursor fila = BaseDeDatos.rawQuery("SELECT * FROM "+ TABLE_USUARIO,null);
            if(fila == null )
                return false;
            if(!fila.moveToFirst())
                return false;
        }catch (Exception e){
            return false;
        }
        return true;
    }
    ////////////////////////////////////////////////////////////////

    //  TODO Tabla REGISTROS
    public long reg_insert(Registros registros){
        //INSERT INTO USUARIOS (CEDULA, NAMBRE, CARGO, PERFIL) VALUES (1050200224, 'RUBIO', 'OFIC', 2)
        try {
            ContentValues registro = new ContentValues();
            registro.put(REG_CEDULA, registros.getCedula());
            registro.put(REG_DIA, registros.getDia());
            registro.put(REG_DIA_MES, registros.getDia_mes());
            registro.put(REG_FECHA_IN, registros.getFecha_in());
            registro.put(REG_COMENTARIO_IN, registros.getComentario_in());
            registro.put(REG_EQUIPO_IN, registros.getEquipo_in());
            registro.put(REG_ACTIVIDAD_IN, registros.getActividad_in());
            registro.put(REG_COOR_LATITUD_IN, registros.getLatitud_in());
            registro.put(REG_COOR_LONGITUD_IN, registros.getLongitud_in());
            return BaseDeDatos.insert(TABLE_IN_OUT,null,registro);
        }catch (Exception e){
            return 0;
        }
    }

    public long reg_insertAll(Registros registros){
        //INSERT INTO USUARIOS (CEDULA, NAMBRE, CARGO, PERFIL) VALUES (1050200224, 'RUBIO', 'OFIC', 2)
        try {
            ContentValues registro = new ContentValues();
            registro.put(REG_CEDULA, registros.getCedula());
            registro.put(REG_DIA, registros.getDia());
            registro.put(REG_DIA_MES, registros.getDia_mes());
            registro.put(REG_FECHA_IN, registros.getFecha_in());
            registro.put(REG_FECHA_OUT, registros.getFecha_out());
            registro.put(REG_COMENTARIO_IN, registros.getComentario_in());
            registro.put(REG_COMENTARIO_OUT, registros.getComentario_out());
            registro.put(REG_EQUIPO_IN, registros.getEquipo_in());
            registro.put(REG_EQUIPO_OUT, registros.getEquipo_out());
            registro.put(REG_ACTIVIDAD_IN, registros.getActividad_in());
            registro.put(REG_ACTIVIDAD_OUT, registros.getActividad_out());
            registro.put(REG_COOR_LATITUD_IN, registros.getLatitud_in());
            registro.put(REG_COOR_LONGITUD_IN, registros.getLongitud_in());
            registro.put(REG_COOR_LATITUD_OUT, registros.getLatitud_out());
            registro.put(REG_COOR_LONGITUD_OUT, registros.getLongitud_out());
            return BaseDeDatos.insert(TABLE_IN_OUT,null,registro);
        }catch (Exception e){
            return 0;
        }
    }
    public Boolean reg_update(Registros register){
        //UPDATE USUARIOS SET NAMBRE='eLKYN',CARGO='OPERADOR',PERFIL=2 WHERE CEDULA=1
        try {
            String sentence = "UPDATE "+ TABLE_IN_OUT+" SET "+
                    REG_FECHA_OUT+"=?,"+
                    REG_COMENTARIO_OUT+"=?,"+
                    REG_EQUIPO_OUT+"=?,"+
                    REG_ACTIVIDAD_OUT+"=?,"+
                    REG_COOR_LATITUD_OUT+"=?,"+
                    REG_COOR_LONGITUD_OUT+"=?"+
                    " WHERE ID="+String.valueOf(register.getId());
            Log.d("DP_DLOG","reg_update "+"sentende "+sentence);

            BaseDeDatos.execSQL(sentence, new Object[]{register.getFecha_out(),register.getComentario_out(),register.getEquipo_out(),
                                                        register.getActividad_out(),register.getLatitud_out(),register.getLongitud_out()});
        }catch (Exception e){
            return false;
        }

        return true;

    }
    public List<Registros> reg_getAll(){
        List<Registros> register = new ArrayList<Registros>();

        try {
            Cursor fila = BaseDeDatos.rawQuery("SELECT * FROM "+ TABLE_IN_OUT,null);

            if(fila == null )
                return null;

            if(!fila.moveToFirst())
                return null;

            for(fila.moveToFirst(); !fila.isAfterLast(); fila.moveToNext()){
                Registros numItem = new Registros();
                numItem.setId(fila.getInt(0));
                numItem.setCedula(fila.getInt(1));
                numItem.setDia(fila.getString(2));
                numItem.setDia_mes(fila.getInt(3));
                numItem.setFecha_in(fila.getString(4));
                numItem.setComentario_in(fila.getString(5));
                numItem.setFecha_out(fila.getString(6));
                numItem.setComentario_out(fila.getString(7));
                numItem.setEquipo_in(fila.getString(8));
                numItem.setActividad_in(fila.getString(9));
                numItem.setEquipo_out(fila.getString(10));
                numItem.setActividad_out(fila.getString(11));
                numItem.setLatitud_in(fila.getString(12));
                numItem.setLongitud_in(fila.getString(13));
                numItem.setLatitud_out(fila.getString(14));
                numItem.setLongitud_out(fila.getString(15));

                register.add(numItem);
            }
        }catch (Exception e){
            return null;
        }

        return register;
    }
    public List<Registros> reg_getAllByUser(int cedula){
        List<Registros> register = new ArrayList<Registros>();

        try {
            Cursor fila = BaseDeDatos.rawQuery("SELECT * FROM "+ TABLE_IN_OUT +" WHERE "+REG_CEDULA+" = "+String.valueOf(cedula),null);

            if(fila == null )
                return null;

            if(!fila.moveToFirst())
                return null;

            for(fila.moveToFirst(); !fila.isAfterLast(); fila.moveToNext()){
                Registros numItem = new Registros();
                numItem.setId(fila.getInt(0));
                numItem.setCedula(fila.getInt(1));
                numItem.setDia(fila.getString(2));
                numItem.setDia_mes(fila.getInt(3));
                numItem.setFecha_in(fila.getString(4));
                numItem.setComentario_in(fila.getString(5));
                numItem.setFecha_out(fila.getString(6));
                numItem.setComentario_out(fila.getString(7));
                numItem.setEquipo_in(fila.getString(8));
                numItem.setActividad_in(fila.getString(9));
                numItem.setEquipo_out(fila.getString(10));
                numItem.setActividad_out(fila.getString(11));
                numItem.setLatitud_in(fila.getString(12));
                numItem.setLongitud_in(fila.getString(13));
                numItem.setLatitud_out(fila.getString(14));
                numItem.setLongitud_out(fila.getString(15));

                register.add(numItem);
            }
        }catch (Exception e){
            return null;
        }

        return register;
    }
    public Registros reg_getAllLastByUser(int cedula){
        Registros register = new Registros();

        try {
            //"SELECT * FROM registros WHERE cedula = ? ORDER BY id DESC LIMIT 1";
            Cursor fila = BaseDeDatos.rawQuery("SELECT * FROM "+ TABLE_IN_OUT +
                                            " WHERE "+REG_CEDULA+" = "+String.valueOf(cedula)+"" +
                                            " ORDER BY ID DESC LIMIT 1",null);

            if(fila == null )
                return null;

            if(!fila.moveToFirst())
                return null;

            for(fila.moveToFirst(); !fila.isAfterLast(); fila.moveToNext()){

                register.setId(fila.getInt(0));
                register.setCedula(fila.getInt(1));
                register.setDia(fila.getString(2));
                register.setDia_mes(fila.getInt(3));
                register.setFecha_in(fila.getString(4));
                register.setComentario_in(fila.getString(5));
                register.setFecha_out(fila.getString(6));
                register.setComentario_out(fila.getString(7));
                register.setEquipo_in(fila.getString(8));
                register.setActividad_in(fila.getString(9));
                register.setEquipo_out(fila.getString(10));
                register.setActividad_out(fila.getString(11));
                register.setLatitud_in(fila.getString(12));
                register.setLongitud_in(fila.getString(13));
                register.setLatitud_out(fila.getString(14));
                register.setLongitud_out(fila.getString(15));
            }
        }catch (Exception e){
            return null;
        }

        return register;
    }
    public List<Registros> reg_getAllByUserMes(String mes, int cedula){
        List<Registros> register = new ArrayList<Registros>();

        try {
            Cursor fila = BaseDeDatos.rawQuery("SELECT * FROM "+TABLE_IN_OUT+" WHERE cedula = ? AND strftime('%Y-%m', fecha_input) = ?",
                                    new String[] {String.valueOf(cedula), mes});

            if(fila == null )
                return null;

            if(!fila.moveToFirst())
                return null;

            for(fila.moveToFirst(); !fila.isAfterLast(); fila.moveToNext()){
                Registros numItem = new Registros();
                numItem.setId(fila.getInt(0));
                numItem.setCedula(fila.getInt(1));
                numItem.setDia(fila.getString(2));
                numItem.setDia_mes(fila.getInt(3));
                numItem.setFecha_in(fila.getString(4));
                numItem.setComentario_in(fila.getString(5));
                numItem.setFecha_out(fila.getString(6));
                numItem.setComentario_out(fila.getString(7));
                numItem.setEquipo_in(fila.getString(8));
                numItem.setActividad_in(fila.getString(9));
                numItem.setEquipo_out(fila.getString(10));
                numItem.setActividad_out(fila.getString(11));
                numItem.setLatitud_in(fila.getString(12));
                numItem.setLongitud_in(fila.getString(13));
                numItem.setLatitud_out(fila.getString(14));
                numItem.setLongitud_out(fila.getString(15));

                register.add(numItem);
            }
        }catch (Exception e){
            return null;
        }

        return register;
    }
    public void reg_deleteRegistros(){
        BaseDeDatos.delete(TABLE_IN_OUT,null,null);
    }
    public int numRegisterAll(){
        Cursor fila = BaseDeDatos.rawQuery("SELECT COUNT(*) FROM "+TABLE_IN_OUT,null);
        if(fila == null )
            return 0;

        if(fila.moveToFirst())
            return fila.getInt(0);
        else
            return 0;
    }
    public Boolean reg_isExistRegister(){
        try {
            Cursor fila = BaseDeDatos.rawQuery("SELECT 1 FROM "+TABLE_IN_OUT+" LIMIT 1",null);
            if(fila == null )
                return false;
            if(!fila.moveToFirst())
                return false;
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public Boolean reg_isExistRegisterByUser(int cedula){
        try {
            Cursor fila = BaseDeDatos.rawQuery("SELECT 1 FROM "+TABLE_IN_OUT+" WHERE "+REG_CEDULA+"="+String.valueOf(cedula)+"LIMIT 1",null);
            if(fila == null )
                return false;
            if(!fila.moveToFirst())
                return false;
        }catch (Exception e){
            return false;
        }
        return true;
    }
    ////////////////////////////////////////////////////////////////

    // TODO Tabla ACTIVIDADES
    public long act_insert(String actividad){
        try {
            ContentValues registro = new ContentValues();
            registro.put(ACT_NOMBRE, actividad);
            return BaseDeDatos.insert(TABLE_ACTIVIDAD,null,registro);
        }catch (Exception e){
            return 0;
        }
    }
    public List<String> act_getAll(){
        List<String> actividad = new ArrayList<String>();

        try {
            Cursor fila = BaseDeDatos.rawQuery("SELECT * FROM "+ TABLE_ACTIVIDAD,null);

            if(fila == null )
                return null;

            if(!fila.moveToFirst())
                return null;

            for(fila.moveToFirst(); !fila.isAfterLast(); fila.moveToNext()){
                actividad.add(fila.getString(1));
            }
        }catch (Exception e){
            return null;
        }

        return actividad;
    }
    public void act_delete(){
        BaseDeDatos.delete(TABLE_ACTIVIDAD,null,null);
    }
    public Boolean act_isExistActivities(){
        try {
            Cursor fila = BaseDeDatos.rawQuery("SELECT * FROM "+ TABLE_ACTIVIDAD,null);
            if(fila == null )
                return false;
            if(!fila.moveToFirst())
                return false;
        }catch (Exception e){
            return false;
        }
        return true;
    }
    ////////////////////////////////////////////////////////////////

    // TODO Tabla EQUIPO
    public long equi_insert(Equipos equipo){
        try {
            ContentValues registro = new ContentValues();
            registro.put(EQUI_NOMBRE, equipo.getNombre());
            registro.put(EQUI_NUM_REGISTRO, equipo.getNumeroRegistro());
            registro.put(EQUI_EQUIPO, equipo.getEquipo());
            registro.put(EQUI_NUM_MOTOR, equipo.getNumeroMotor());
            registro.put(EQUI_NUM_SERIE, equipo.getNumeroSerie());
            registro.put(EQUI_PROPIETARIO, equipo.getPropietario());
            registro.put(EQUI_PLACA, equipo.getPlaca());
            registro.put(EQUI_TIPO, equipo.getTipo());
            registro.put(EQUI_FOTO, equipo.getFoto());

            return BaseDeDatos.insert(TABLE_EQUIPOS,null,registro);
        }catch (Exception e){
            return 0;
        }
    }
    public List<Equipos> equi_getAll(){
        List<Equipos> equipos = new ArrayList<Equipos>();

        try {
            Cursor fila = BaseDeDatos.rawQuery("SELECT * FROM "+ TABLE_EQUIPOS,null);

            if(fila == null )
                return null;

            if(!fila.moveToFirst())
                return null;

            for(fila.moveToFirst(); !fila.isAfterLast(); fila.moveToNext()){
                Equipos equipos1 = new Equipos();
                equipos1.setNombre(fila.getString(1));
                equipos1.setNumeroRegistro(fila.getString(2));
                equipos1.setEquipo(fila.getString(3));
                equipos1.setNumeroMotor(fila.getString(4));
                equipos1.setNumeroSerie(fila.getString(5));
                equipos1.setPropietario(fila.getString(6));
                equipos1.setPlaca(fila.getString(7));
                equipos1.setTipo(fila.getInt(8));
                equipos1.setFoto(fila.getString(9));

                equipos.add(equipos1);
            }
        }catch (Exception e){
            return null;
        }

        return equipos;
    }
    public void equi_delete(){
        BaseDeDatos.delete(TABLE_EQUIPOS,null,null);
    }
    public Boolean equi_isExistEquipos(){
        try {
            Cursor fila = BaseDeDatos.rawQuery("SELECT * FROM "+ TABLE_EQUIPOS,null);
            if(fila == null )
                return false;
            if(!fila.moveToFirst())
                return false;
        }catch (Exception e){
            return false;
        }
        return true;
    }
    ////////////////////////////////////////////////////////////////

    // TODO Tabla TIPO MANTENIMIENTO
    public long mante_insert(String equipo){
        try {
            ContentValues registro = new ContentValues();
            registro.put(MANTE_NOMBRE, equipo);
            return BaseDeDatos.insert(TABLE_MANTENIMIENTO,null,registro);
        }catch (Exception e){
            return 0;
        }
    }
    public List<String> mante_getAll(){
        List<String> equipos = new ArrayList<String>();

        try {
            Cursor fila = BaseDeDatos.rawQuery("SELECT * FROM "+ TABLE_MANTENIMIENTO,null);

            if(fila == null )
                return null;

            if(!fila.moveToFirst())
                return null;

            for(fila.moveToFirst(); !fila.isAfterLast(); fila.moveToNext()){
                equipos.add(fila.getString(1));
            }
        }catch (Exception e){
            return null;
        }

        return equipos;
    }
    public void mante_delete(){
        BaseDeDatos.delete(TABLE_MANTENIMIENTO,null,null);
    }
    public Boolean mante_isExistMante(){
        try {
            Cursor fila = BaseDeDatos.rawQuery("SELECT * FROM "+ TABLE_MANTENIMIENTO,null);
            if(fila == null )
                return false;
            if(!fila.moveToFirst())
                return false;
        }catch (Exception e){
            return false;
        }
        return true;
    }
    ////////////////////////////////////////////////////////////////

    // TODO Tabla LUGARES
    public long luga_insert(String equipo){
        try {
            ContentValues registro = new ContentValues();
            registro.put(LUGA_NOMBRE, equipo);
            return BaseDeDatos.insert(TABLE_LUGARES,null,registro);
        }catch (Exception e){
            return 0;
        }
    }
    public List<String> luga_getAll(){
        List<String> equipos = new ArrayList<String>();

        try {
            Cursor fila = BaseDeDatos.rawQuery("SELECT * FROM "+ TABLE_LUGARES,null);

            if(fila == null )
                return null;

            if(!fila.moveToFirst())
                return null;

            for(fila.moveToFirst(); !fila.isAfterLast(); fila.moveToNext()){
                equipos.add(fila.getString(1));
            }
        }catch (Exception e){
            return null;
        }

        return equipos;
    }
    public void luga_delete(){
        BaseDeDatos.delete(TABLE_LUGARES,null,null);
    }
    public Boolean luga_isExistLuga(){
        try {
            Cursor fila = BaseDeDatos.rawQuery("SELECT * FROM "+ TABLE_LUGARES,null);
            if(fila == null )
                return false;
            if(!fila.moveToFirst())
                return false;
        }catch (Exception e){
            return false;
        }
        return true;
    }
    ////////////////////////////////////////////////////////////////

    // TODO Tabla TECNICOS
    public long tec_insert(String equipo){
        try {
            ContentValues registro = new ContentValues();
            registro.put(TEC_NOMBRE, equipo);
            return BaseDeDatos.insert(TABLE_TECNICOS,null,registro);
        }catch (Exception e){
            return 0;
        }
    }
    public List<String> tec_getAll(){
        List<String> equipos = new ArrayList<String>();

        try {
            Cursor fila = BaseDeDatos.rawQuery("SELECT * FROM "+ TABLE_TECNICOS,null);

            if(fila == null )
                return null;

            if(!fila.moveToFirst())
                return null;

            for(fila.moveToFirst(); !fila.isAfterLast(); fila.moveToNext()){
                equipos.add(fila.getString(1));
            }
        }catch (Exception e){
            return null;
        }

        return equipos;
    }
    public void tec_delete(){
        BaseDeDatos.delete(TABLE_TECNICOS,null,null);
    }
    public Boolean tec_isExistTec(){
        try {
            Cursor fila = BaseDeDatos.rawQuery("SELECT * FROM "+ TABLE_TECNICOS,null);
            if(fila == null )
                return false;
            if(!fila.moveToFirst())
                return false;
        }catch (Exception e){
            return false;
        }
        return true;
    }
    ////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////
    // TODO Tabla INVENTARIO
    public InventarioCaterpila cat_get(){
        InventarioCaterpila caterpila = new InventarioCaterpila();
        try {
            Cursor fila = BaseDeDatos.rawQuery("SELECT * FROM "+ TABLE_CATERPILA,null);
            if(fila == null )
                return null;
            if(!fila.moveToFirst())
                return null;
            for(fila.moveToFirst(); !fila.isAfterLast(); fila.moveToNext()){
                caterpila.setId(fila.getInt(1));
                caterpila.setfAceiteMotor(fila.getInt(2));
                caterpila.setfCombustibleBF13(fila.getInt(3));
                caterpila.setfCombustibleBF77(fila.getInt(4));
                caterpila.setfServo(fila.getInt(5));
                caterpila.setfCabina(fila.getInt(6));
                caterpila.setfHidrailicoBT93(fila.getInt(7));
                caterpila.setfHidrailicoBT83(fila.getInt(8));
                caterpila.setfAireInterno(fila.getInt(9));
                caterpila.setfAireExterno(fila.getInt(10));
                caterpila.setfPrefijo(fila.getInt(11));
                break;
            }
        }catch (Exception e){
            return null;
        }
        return caterpila;
    }
    public long cat_insert(InventarioCaterpila caterpila){
        try {
            ContentValues registro = new ContentValues();
            registro.put(CAT_ID, caterpila.getId());
            registro.put(CAT_ACEITE_MOTOR, caterpila.getfAceiteMotor());
            registro.put(CAT_COMB_BF13, caterpila.getfCombustibleBF13());
            registro.put(CAT_COMB_BF77, caterpila.getfCombustibleBF77());
            registro.put(CAT_SERVO, caterpila.getfServo());
            registro.put(CAT_CABINA, caterpila.getfCabina());
            registro.put(CAT_HIDRA_BT93, caterpila.getfHidrailicoBT93());
            registro.put(CAT_HIDRA_BT83, caterpila.getfHidrailicoBT83());
            registro.put(CAT_AIRE_INTER, caterpila.getfAireInterno());
            registro.put(CAT_AIRE_EXTER, caterpila.getfAireExterno());
            registro.put(CAT_PREFIJO, caterpila.getfPrefijo());
            return BaseDeDatos.insert(TABLE_CATERPILA,null,registro);
        }catch (Exception e){
            return 0;
        }
    }
    public boolean cat_update(InventarioCaterpila caterpila){
        try {
            String sentence = "UPDATE "+ TABLE_CATERPILA+" SET "+
                        CAT_ACEITE_MOTOR+   "=?,"+
                        CAT_COMB_BF13+      "=?,"+
                        CAT_COMB_BF77+      "=?,"+
                        CAT_SERVO+          "=?,"+
                        CAT_CABINA+         "=?,"+
                        CAT_HIDRA_BT93+     "=?,"+
                        CAT_HIDRA_BT83+     "=?,"+
                        CAT_AIRE_INTER+     "=?,"+
                        CAT_AIRE_EXTER+     "=?,"+
                        CAT_PREFIJO+        "=?"+
                    " WHERE "+ CAT_ID+"="+String.valueOf(caterpila.getId());
            Log.d("DP_DLOG","reg_update "+"sentende "+sentence);

            BaseDeDatos.execSQL(sentence, new Object[]{caterpila.getfAceiteMotor(), caterpila.getfCombustibleBF13(),caterpila.getfCombustibleBF77(),
                                                       caterpila.getfServo(),       caterpila.getfCabina(),         caterpila.getfHidrailicoBT93(),caterpila.getfHidrailicoBT83(),
                                                       caterpila.getfAireInterno(), caterpila.getfAireExterno(),    caterpila.getfPrefijo()});
        }catch (Exception e){
            return false;
        }
        return true;
    }
    public void cat_delete(){
        BaseDeDatos.delete(TABLE_CATERPILA,null,null);
    }
    public Boolean cat_isExistTec(){
        try {
            Cursor fila = BaseDeDatos.rawQuery("SELECT * FROM "+ TABLE_CATERPILA,null);
            if(fila == null )
                return false;
            if(!fila.moveToFirst())
                return false;
        }catch (Exception e){
            return false;
        }
        return true;
    }

    ////////////////////////////////////////////////////////////////
    public InventarioDoorsan door_get(){
        InventarioDoorsan doorsan = new InventarioDoorsan();
        try {
            Cursor fila = BaseDeDatos.rawQuery("SELECT * FROM "+ TABLE_DOORSAN,null);
            if(fila == null )
                return null;
            if(!fila.moveToFirst())
                return null;
            for(fila.moveToFirst(); !fila.isAfterLast(); fila.moveToNext()){
                doorsan.setId(fila.getInt(1));
                doorsan.setfAceiteMotor(fila.getInt(2));
                doorsan.setfCombustible4005(fila.getInt(3));
                doorsan.setfCombustible4004(fila.getInt(4));
                doorsan.setfServoTrasmision(fila.getInt(5));
                doorsan.setfPiloto(fila.getInt(6));
                doorsan.setfHidrailico(fila.getInt(7));
                doorsan.setfAireCabina(fila.getInt(8));
                doorsan.setfAireInterno(fila.getInt(9));
                doorsan.setfAireExterno(fila.getInt(10));
                doorsan.setfPrefijo(fila.getInt(11));
                break;
            }
        }catch (Exception e){
            return null;
        }
        return doorsan;
    }
    public long door_insert(InventarioDoorsan doorsan){
        try {
            ContentValues registro = new ContentValues();
            registro.put(DOOR_ID,            doorsan.getId());
            registro.put(DOOR_ACEITE_MOTOR,  doorsan.getfAceiteMotor());
            registro.put(DOOR_CONB_4005,     doorsan.getfCombustible4005());
            registro.put(DOOR_CONB_4004,     doorsan.getfCombustible4004());
            registro.put(DOOR_SERVO,         doorsan.getfServoTrasmision());
            registro.put(DOOR_PILOTO,        doorsan.getfPiloto());
            registro.put(DOOR_HIDRAILI,      doorsan.getfHidrailico());
            registro.put(DOOR_AIRE_CABI,     doorsan.getfAireCabina());
            registro.put(DOOR_AIRE_INTER,    doorsan.getfAireInterno());
            registro.put(DOOR_AIRE_INTER,    doorsan.getfAireExterno());
            registro.put(DOOR_PREFIJO,       doorsan.getfPrefijo());
            return BaseDeDatos.insert(TABLE_DOORSAN,null,registro);
        }catch (Exception e){
            return 0;
        }
    }
    public boolean door_update(InventarioDoorsan doorsan){
        try {
            String sentence = "UPDATE "+ TABLE_DOORSAN+" SET "+
                    DOOR_ACEITE_MOTOR+   "=?,"+
                    DOOR_CONB_4005+      "=?,"+
                    DOOR_CONB_4004+      "=?,"+
                    DOOR_SERVO+          "=?,"+
                    DOOR_PILOTO+         "=?,"+
                    DOOR_HIDRAILI+       "=?,"+
                    DOOR_AIRE_CABI+      "=?,"+
                    DOOR_AIRE_INTER+     "=?,"+
                    DOOR_AIRE_EXTER+     "=?,"+
                    DOOR_PREFIJO+        "=?"+
                    " WHERE "+ DOOR_ID+"="+String.valueOf(doorsan.getId());
            Log.d("DP_DLOG","reg_update "+"sentende "+sentence);

            BaseDeDatos.execSQL(sentence, new Object[]{doorsan.getfAceiteMotor(), doorsan.getfCombustible4005(),doorsan.getfCombustible4004(),
                                                       doorsan.getfServoTrasmision(),       doorsan.getfPiloto(),         doorsan.getfHidrailico(),
                                                       doorsan.getfAireCabina(), doorsan.getfAireInterno(), doorsan.getfAireExterno(),
                                                       doorsan.getfPrefijo()});
        }catch (Exception e){
            return false;
        }
        return true;
    }
    public void door_delete(){
        BaseDeDatos.delete(TABLE_DOORSAN,null,null);
    }
    public Boolean door_isExistTec(){
        try {
            Cursor fila = BaseDeDatos.rawQuery("SELECT * FROM "+ TABLE_DOORSAN,null);
            if(fila == null )
                return false;
            if(!fila.moveToFirst())
                return false;
        }catch (Exception e){
            return false;
        }
        return true;
    }

    ////////////////////////////////////////////////////////////////
    public InventarioKomatsu1 komat1_get(){
        InventarioKomatsu1 komatsu = new InventarioKomatsu1();
        try {
            Cursor fila = BaseDeDatos.rawQuery("SELECT * FROM "+ TABLE_KOMAT_01,null);
            if(fila == null )
                return null;
            if(!fila.moveToFirst())
                return null;
            for(fila.moveToFirst(); !fila.isAfterLast(); fila.moveToNext()){
                komatsu.setId(fila.getInt(1));
                komatsu.setfAceiteMotor(fila.getInt(2));
                komatsu.setfTrampaCombustible(fila.getInt(3));
                komatsu.setfCombustible(fila.getInt(4));
                komatsu.setfCabina(fila.getInt(5));
                komatsu.setfHidraulico(fila.getInt(6));
                komatsu.setfRespiradero(fila.getInt(7));
                komatsu.setfPiloto(fila.getInt(8));
                komatsu.setfAireInterno(fila.getInt(9));
                komatsu.setfAireExterno(fila.getInt(10));
                break;
            }
        }catch (Exception e){
            return null;
        }
        return komatsu;
    }
    public long komat1_insert(InventarioKomatsu1 komatsu1){
        try {
            ContentValues registro = new ContentValues();
            registro.put(KOMA1_ID,        komatsu1.getId());
            registro.put(KOMA1_MOTOR,     komatsu1.getfAceiteMotor());
            registro.put(KOMA1_TRMPA,     komatsu1.getfTrampaCombustible());
            registro.put(KOMA1_COMBUS,    komatsu1.getfCombustible());
            registro.put(KOMA1_CABINA,    komatsu1.getfCabina());
            registro.put(KOMA1_HIDRAU,    komatsu1.getfHidraulico());
            registro.put(KOMA1_RESPIR,    komatsu1.getfRespiradero());
            registro.put(KOMA1_PILOTO,    komatsu1.getfPiloto());
            registro.put(KOMA1_INTER,     komatsu1.getfAireInterno());
            registro.put(KOMA1_EXTERN,    komatsu1.getfAireExterno());
            return BaseDeDatos.insert(TABLE_KOMAT_01,null,registro);
        }catch (Exception e){
            return 0;
        }
    }
    public boolean komat1_update(InventarioKomatsu1 komatsu1){
        try {
            String sentence = "UPDATE "+ TABLE_KOMAT_01+" SET "+
                    KOMA1_MOTOR+   "=?,"+
                    KOMA1_TRMPA+   "=?,"+
                    KOMA1_COMBUS+  "=?,"+
                    KOMA1_CABINA+  "=?,"+
                    KOMA1_HIDRAU+  "=?,"+
                    KOMA1_RESPIR+  "=?,"+
                    KOMA1_PILOTO+  "=?,"+
                    KOMA1_INTER+   "=?,"+
                    KOMA1_EXTERN+  "=?"+
                    " WHERE "+ KOMA1_ID+"="+String.valueOf(komatsu1.getId());
            Log.d("DP_DLOG","reg_update "+"sentende "+sentence);

            BaseDeDatos.execSQL(sentence, new Object[]{komatsu1.getfAceiteMotor(), komatsu1.getfTrampaCombustible(),komatsu1.getfCombustible(),
                                                    komatsu1.getfCabina(),       komatsu1.getfHidraulico(),         komatsu1.getfRespiradero(),
                                                    komatsu1.getfPiloto(), komatsu1.getfAireInterno(), komatsu1.getfAireExterno()});
        }catch (Exception e){
            return false;
        }
        return true;
    }
    public void komat1_delete(){
        BaseDeDatos.delete(TABLE_KOMAT_01,null,null);
    }
    public Boolean komat1_isExistTec(){
        try {
            Cursor fila = BaseDeDatos.rawQuery("SELECT * FROM "+ TABLE_KOMAT_01,null);
            if(fila == null )
                return false;
            if(!fila.moveToFirst())
                return false;
        }catch (Exception e){
            return false;
        }
        return true;
    }

    ////////////////////////////////////////////////////////////////
    public InventarioKomatsu2 komat2_get(){
        InventarioKomatsu2 komatsu = new InventarioKomatsu2();
        try {
            Cursor fila = BaseDeDatos.rawQuery("SELECT * FROM "+ TABLE_KOMAT_2,null);
            if(fila == null )
                return null;
            if(!fila.moveToFirst())
                return null;
            for(fila.moveToFirst(); !fila.isAfterLast(); fila.moveToNext()){
                komatsu.setId(fila.getInt(1));
                komatsu.setfAceiteMotor(fila.getInt(2));
                komatsu.setfTrampaCombustible(fila.getInt(3));
                komatsu.setfCombustible(fila.getInt(4));
                komatsu.setfCabina(fila.getInt(5));
                komatsu.setfHidraulico(fila.getInt(6));
                komatsu.setfRespiradero(fila.getInt(7));
                komatsu.setfPiloto(fila.getInt(8));
                komatsu.setfAireInterno(fila.getInt(9));
                komatsu.setfAireExterno(fila.getInt(10));
                komatsu.setfRefregerante(fila.getInt(11));
                break;
            }
        }catch (Exception e){
            return null;
        }
        return komatsu;
    }
    public long komat2_insert(InventarioKomatsu2 komatsu2){
        try {
            ContentValues registro = new ContentValues();
            registro.put(KOMA2_ID,     komatsu2.getId());
            registro.put(KOMA2_MOTOR,  komatsu2.getfAceiteMotor());
            registro.put(KOMA2_TRMPA,  komatsu2.getfTrampaCombustible());
            registro.put(KOMA2_COMBUS, komatsu2.getfCombustible());
            registro.put(KOMA2_CABINA, komatsu2.getfCabina());
            registro.put(KOMA2_HIDRAU, komatsu2.getfHidraulico());
            registro.put(KOMA2_RESPIR, komatsu2.getfRespiradero());
            registro.put(KOMA2_PILOTO, komatsu2.getfPiloto());
            registro.put(KOMA2_INTER,  komatsu2.getfAireInterno());
            registro.put(KOMA2_EXTERN, komatsu2.getfAireExterno());
            registro.put(KOMA2_REFRIG, komatsu2.getfRefregerante());
            return BaseDeDatos.insert(TABLE_KOMAT_2,null,registro);
        }catch (Exception e){
            return 0;
        }
    }
    public boolean komat2_update(InventarioKomatsu2 komatsu2){
        try {
            String sentence = "UPDATE "+ TABLE_KOMAT_2+" SET "+
                    KOMA2_MOTOR+   "=?,"+
                    KOMA2_TRMPA+   "=?,"+
                    KOMA2_COMBUS+  "=?,"+
                    KOMA2_CABINA+  "=?,"+
                    KOMA2_HIDRAU+  "=?,"+
                    KOMA2_RESPIR+  "=?,"+
                    KOMA2_PILOTO+  "=?,"+
                    KOMA2_INTER+   "=?,"+
                    KOMA2_EXTERN+  "=?,"+
                    KOMA2_REFRIG+  "=?"+
                    " WHERE "+ KOMA2_ID+"="+String.valueOf(komatsu2.getId());
            Log.d("DP_DLOG","reg_update "+"sentende "+sentence);

            BaseDeDatos.execSQL(sentence, new Object[]{komatsu2.getfAceiteMotor(), komatsu2.getfTrampaCombustible(),komatsu2.getfCombustible(),
                                                        komatsu2.getfCabina(),       komatsu2.getfHidraulico(),         komatsu2.getfRespiradero(),
                                                        komatsu2.getfPiloto(), komatsu2.getfAireInterno(), komatsu2.getfAireExterno(),
                                                        komatsu2.getfRefregerante()});
        }catch (Exception e){
            return false;
        }
        return true;
    }
    public void komat2_delete(){
        BaseDeDatos.delete(TABLE_KOMAT_2,null,null);
    }
    public Boolean komat2_isExistTec(){
        try {
            Cursor fila = BaseDeDatos.rawQuery("SELECT * FROM "+ TABLE_KOMAT_2,null);
            if(fila == null )
                return false;
            if(!fila.moveToFirst())
                return false;
        }catch (Exception e){
            return false;
        }
        return true;
    }

    ////////////////////////////////////////////////////////////////
    public InventarioAceite aceite_get(){
        InventarioAceite aceite = new InventarioAceite();
        try {
            Cursor fila = BaseDeDatos.rawQuery("SELECT * FROM "+ TABLE_ACEITES,null);
            if(fila == null )
                return null;
            if(!fila.moveToFirst())
                return null;
            for(fila.moveToFirst(); !fila.isAfterLast(); fila.moveToNext()){
                aceite.setId(fila.getInt(1));
                aceite.setA15W40(fila.getInt(2));
                aceite.setIso68(fila.getInt(3));
                aceite.setTo30(fila.getInt(4));
                aceite.setA80W90(fila.getInt(5));
                aceite.setS527(fila.getInt(6));
                aceite.setG_Litio(fila.getInt(7));
                aceite.setMotor(fila.getInt(8));
                break;
            }
        }catch (Exception e){
            return null;
        }
        return aceite;
    }
    public long aceite_insert(InventarioAceite aceite){
        try {
            ContentValues registro = new ContentValues();
            registro.put(ACEI_ID,     aceite.getId());
            registro.put(ACEI_A15W40,  aceite.getA15W40());
            registro.put(ACEI_ISO68,  aceite.getIso68());
            registro.put(ACEI_TO30, aceite.getTo30());
            registro.put(ACEI_A80W90, aceite.getA80W90());
            registro.put(ACEI_S527, aceite.getS527());
            registro.put(ACEI_G_LITIO, aceite.getG_Litio());
            registro.put(ACEI_MOTOR, aceite.getMotor());
            return BaseDeDatos.insert(TABLE_ACEITES,null,registro);
        }catch (Exception e){
            return 0;
        }
    }
    public boolean aceite_update(InventarioAceite aceite){
        try {
            String sentence = "UPDATE "+ TABLE_ACEITES+" SET "+
                    ACEI_A15W40+   "=?,"+
                    ACEI_ISO68+    "=?,"+
                    ACEI_TO30+     "=?,"+
                    ACEI_A80W90+   "=?,"+
                    ACEI_S527+     "=?,"+
                    ACEI_G_LITIO+  "=?,"+
                    ACEI_MOTOR+    "=?"+
                    " WHERE "+ ACEI_ID+"="+String.valueOf(aceite.getId());
            Log.d("DP_DLOG","reg_update "+"sentende "+sentence);

            BaseDeDatos.execSQL(sentence, new Object[]{aceite.getA15W40(), aceite.getIso68(),aceite.getTo30(),
                                                        aceite.getA80W90(),       aceite.getS527(),         aceite.getG_Litio(),
                                                        aceite.getMotor()});
        }catch (Exception e){
            return false;
        }
        return true;
    }
    public void aceite_delete(){
        BaseDeDatos.delete(TABLE_ACEITES,null,null);
    }
    public Boolean aceite_isExistTec(){
        try {
            Cursor fila = BaseDeDatos.rawQuery("SELECT * FROM "+ TABLE_ACEITES,null);
            if(fila == null )
                return false;
            if(!fila.moveToFirst())
                return false;
        }catch (Exception e){
            return false;
        }
        return true;
    }

    ////////////////////////////////////////////////////////////////
    public InventarioMicelanio micelanio_get(){
        InventarioMicelanio micelanio = new InventarioMicelanio();
        try {
            Cursor fila = BaseDeDatos.rawQuery("SELECT * FROM "+ TABLE_MICELANIO,null);
            if(fila == null )
                return null;
            if(!fila.moveToFirst())
                return null;
            for(fila.moveToFirst(); !fila.isAfterLast(); fila.moveToNext()){
                micelanio.setId(fila.getInt(1));
                micelanio.setAC_R134(fila.getInt(2));
                micelanio.setX70(fila.getInt(3));
                break;
            }
        }catch (Exception e){
            return null;
        }
        return micelanio;
    }
    public long mice_insert(InventarioMicelanio micelanio){
        try {
            ContentValues registro = new ContentValues();
            registro.put(MICEL_ID,     micelanio.getId());
            registro.put(MICEL_AC_R134, micelanio.getAC_R134());
            registro.put(MICEL_X70,  micelanio.getX70());
            return BaseDeDatos.insert(TABLE_MICELANIO,null,registro);
        }catch (Exception e){
            return 0;
        }
    }
    public boolean mice_update(InventarioMicelanio micelanio){
        try {
            String sentence = "UPDATE "+ TABLE_MICELANIO+" SET "+
                    MICEL_AC_R134+   "=?,"+
                    MICEL_X70+       "=?"+
                    " WHERE "+ MICEL_ID+"="+String.valueOf(micelanio.getId());
            Log.d("DP_DLOG","reg_update "+"sentende "+sentence);

            BaseDeDatos.execSQL(sentence, new Object[]{micelanio.getAC_R134(), micelanio.getX70()});
        }catch (Exception e){
            return false;
        }
        return true;
    }
    public void mice_delete(){
        BaseDeDatos.delete(TABLE_MICELANIO,null,null);
    }
    public Boolean mice_isExistTec(){
        try {
            Cursor fila = BaseDeDatos.rawQuery("SELECT * FROM "+ TABLE_MICELANIO,null);
            if(fila == null )
                return false;
            if(!fila.moveToFirst())
                return false;
        }catch (Exception e){
            return false;
        }
        return true;
    }


    public void initInventario(){
        if(!cat_isExistTec()){
            InventarioCaterpila inventarioCaterpila = new InventarioCaterpila();
            cat_insert(inventarioCaterpila);
        }
        if(!door_isExistTec()){
            InventarioDoorsan inventarioDoorsan = new InventarioDoorsan();
            door_insert(inventarioDoorsan);
        }
        if(!komat1_isExistTec()){
            InventarioKomatsu1 inventarioKomatsu1 = new InventarioKomatsu1();
            komat1_insert(inventarioKomatsu1);
        }
        if(!komat2_isExistTec()){
            InventarioKomatsu2 inventarioKomatsu2 = new InventarioKomatsu2();
            komat2_insert(inventarioKomatsu2);
        }
        if(!aceite_isExistTec()){
            InventarioAceite inventarioAceite = new InventarioAceite();
            aceite_insert(inventarioAceite);
        }
        if(!mice_isExistTec()){
            InventarioMicelanio inventarioMicelanio = new InventarioMicelanio();
            mice_insert(inventarioMicelanio);
        }
    }

    ////////////////////////////////////////////////////////////////
    public List<Historial> getAll10_histo(){
        List<Historial> history = new ArrayList<>();
        String[] columnas = {NUM_ITEMS, HIS_IS_ADD,HIS_FECHA, HIS_USER,HIS_ITEMS};
        String orderBy = "ID" + " DESC";
        String limit = "10";
        try {
            Cursor fila = BaseDeDatos.query(TABLE_HISTORIAL, columnas, null, null, null, null, orderBy, limit);

            if(fila == null )
                return null;
            if(!fila.moveToFirst())
                return null;
            for(fila.moveToFirst(); !fila.isAfterLast(); fila.moveToNext()){
                Historial historial = new Historial();
                historial.setNumItems(fila.getInt(0));
                historial.setAdd(fila.getInt(1)>0?true:false);
                historial.setFecha(fila.getString(2));
                historial.setUser(fila.getString(3));
                historial.setData(fila.getString(4));

                history.add(historial);
            }
        }catch (Exception e){
            return null;
        }
        return history;
    }
    private long insert_histo(Historial historial){
        try {
            ContentValues registro = new ContentValues();
            registro.put(NUM_ITEMS,     historial.getNumItems());
            registro.put(HIS_IS_ADD,    historial.isAdd()?1:0);
            registro.put(HIS_FECHA, historial.getFecha());
            registro.put(HIS_USER,  historial.getUser());
            registro.put(HIS_ITEMS,  historial.getData());
            return BaseDeDatos.insert(TABLE_HISTORIAL,null,registro);
        }catch (Exception e){
            return 0;
        }
    }
    public void history_delete(){
        BaseDeDatos.delete(TABLE_HISTORIAL,null,null);
    }
    public Boolean history_isExist(){
        try {
            Cursor fila = BaseDeDatos.rawQuery("SELECT * FROM "+ TABLE_HISTORIAL,null);
            if(fila == null )
                return false;
            if(!fila.moveToFirst())
                return false;
        }catch (Exception e){
            return false;
        }
        return true;
    }
    private int countHistory(){
        String consulta = "SELECT COUNT(*) FROM "+TABLE_HISTORIAL;
        Cursor cursor = BaseDeDatos.rawQuery(consulta, null);
        int numeroDeRegistros = 0;

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                numeroDeRegistros = cursor.getInt(0);
            }

            cursor.close();
        }
        return numeroDeRegistros;
    }

    public long history_insert(Historial historial){
        long ret=0;
        int conta = countHistory();

        if(conta>10){
            insert_histo(historial);
            List<Historial> all_histo = getAll10_histo();
            history_delete();
            for (Historial historial1:all_histo) {
                ret = insert_histo(historial1);
            }
        }else {
            return insert_histo(historial);
        }
        return ret;
    }

    public void closeBaseDtos(){
        BaseDeDatos.close();
    }
}
