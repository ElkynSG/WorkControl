package com.esilva.equiposunidos.db;



import static com.esilva.equiposunidos.db.db_unidos.BASE_ISENROLADO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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
            registro.put(db_unidos.BASE_CEDULA, usuario.getCedula());
            registro.put(db_unidos.BASE_NOMBRE, usuario.getNombre());
            registro.put(db_unidos.BASE_PERFIL, usuario.getPerfil());
            registro.put(db_unidos.BASE_CARGO, usuario.getCargo());
            registro.put(db_unidos.BASE_FOTO, usuario.getFoto());
            return BaseDeDatos.insert(db_unidos.TABLE_USUARIO,null,registro);
        }catch (Exception e){
            return 0;
        }
    }
    public Boolean usu_update(User usuario){
        //UPDATE USUARIOS SET NAMBRE='eLKYN',CARGO='OPERADOR',PERFIL=2 WHERE CEDULA=1
        try {
            String sentence = "UPDATE "+ db_unidos.TABLE_USUARIO+" SET "+db_unidos.BASE_HUELLA1+
                    "=?,"+db_unidos.BASE_HUELLA2+"=?,"+db_unidos.BASE_HUELLA3+"=?,"+ BASE_ISENROLADO+ "="+String.valueOf(usuario.getIsEnrolado())+ " WHERE "+db_unidos.BASE_CEDULA+" = ?";
            BaseDeDatos.execSQL(sentence, new Object[]{usuario.getHuellaPulgar(), usuario.getHuellaIndice(), usuario.getHuellaMedio(), usuario.getCedula()});
        }catch (Exception e){
            return false;
        }

        return true;

    }
    public List<User> usu_getAll(){
        List<User> usuarios = new ArrayList<User>();

        try {
            Cursor fila = BaseDeDatos.rawQuery("SELECT * FROM "+ db_unidos.TABLE_USUARIO,null);

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
            Cursor fila = BaseDeDatos.rawQuery("SELECT * FROM "+ db_unidos.TABLE_USUARIO +" WHERE "+BASE_ISENROLADO+" = 1",null);

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
        BaseDeDatos.delete(db_unidos.TABLE_USUARIO,null,null);
    }
    public Boolean usu_isExistUsers(){
        try {
            Cursor fila = BaseDeDatos.rawQuery("SELECT * FROM "+ db_unidos.TABLE_USUARIO,null);
            if(fila == null )
                return false;
            if(!fila.moveToFirst())
                return false;
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public void closeBaseDtos(){
        BaseDeDatos.close();
    }
}
