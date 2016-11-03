package com.example.sena.pescador;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sena on 29/04/2016.
 */
public class BDSQLITE extends SQLiteOpenHelper {
    private static final String bd="campo";
    private static final String tabla="pescador";
    SQLiteDatabase datos;

    public BDSQLITE(Context context) {

        super(context,"campo", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table pescador (codigo integer  primary key autoincrement,nombre text,longitud Double,latitud Double,camara text,microfono text)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if existis pescador");
        db.execSQL("create table pescador (codigo integer  primary key autoincrement,nombre text,longitud Double,latitud Double,camara text,microfono text)");


    }
    public Cursor cursor(){
        String[] columnas={"codigo","nombre","longitud","latitud","camara","microfono"};
        Cursor c=this.getReadableDatabase().query(tabla,columnas,null,null,null,null,null);
        return c;

    }

    public Cursor buscardatos(int cod){


        Cursor fila=datos.rawQuery("select codigo,nombre,longitud,latitud,camara,microfono from pescador where codigo="+cod,null);
        return  fila;
    }

}

