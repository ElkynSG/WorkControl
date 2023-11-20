package com.esilva.equiposunidos.db;




import static com.esilva.equiposunidos.db.db_unidos.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.esilva.equiposunidos.db.models.Equipos;
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
    public void usu_deleteByUser(int cedula){

        BaseDeDatos.delete(TABLE_USUARIO,USU_CEDULA + " = ?",new String[] { String.valueOf(cedula) });
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


    public void closeBaseDtos(){
        BaseDeDatos.close();
    }
}
