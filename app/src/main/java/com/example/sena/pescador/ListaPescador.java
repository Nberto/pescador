package com.example.sena.pescador;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by sena on 21/04/2016.
 */
public class ListaPescador extends Fragment {
    private ListView lv;
    private ArrayList<Integer> codigos;
    private Cursor c;
    BDSQLITE datos1 = new BDSQLITE(getContext());
    private ArrayList<String> datos2 = new ArrayList<>();

    public ListaPescador() {

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lista_pescador, container, false);
        lv = (ListView) view.findViewById(R.id.lv);
        verlista();


  return view;
    }

    public boolean verlista() {
        ArrayList<String> lista = new ArrayList<String>();
        codigos = new ArrayList<>();
        datos1 = new BDSQLITE(getContext());

        c = datos1.cursor();

        if (c.moveToFirst()) {
            do {
                lista.add(c.getString(1));
                codigos.add(c.getInt(0));
            } while (c.moveToNext());

        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, lista);

        lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int cod = codigos.get(position);
                SQLiteDatabase basedatos = datos1.getReadableDatabase();

                c = basedatos.rawQuery("select codigo,nombre,latitud,longitud,camara,microfono from pescador where codigo=" + cod, null);
                //c = datos1.buscardatos(cod);
                if (c.moveToFirst()) {

                    String codigo = String.valueOf(c.getInt(0));
                    String nombre = c.getString(1);
                    String lat = String.valueOf(c.getDouble(2));
                    String lon = String.valueOf(c.getDouble(3));
                    String camara = c.getString(4);
                    String microfono = c.getString(5);

                    Intent obj = new Intent(getContext(), Detalle_pescador.class);

                    obj.putExtra("nombre", nombre);
                    obj.putExtra("latitud", lat);
                    obj.putExtra("longitud", lon);
                    obj.putExtra("camara", camara);
                    obj.putExtra("microfono", microfono);
                    obj.putExtra("codigo", codigo);

                    startActivity(obj);
                } else {

                    Toast.makeText(getContext(), "IDENTIFICACION NO EXISTE", Toast.LENGTH_SHORT).show();
                }
                lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                        String[] opc = new String[]{"ELIMINAR", "ACTUALIZAR", "CANCELAR"};

                        Toast.makeText(getContext(), "pos :" + position, Toast.LENGTH_SHORT).show();
                        AlertDialog opcion = new AlertDialog.Builder(getContext()).setTitle("Opciones").setItems(opc, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int selecion) {
                                if (selecion == 0) {
                                    BDSQLITE abrir = new BDSQLITE(getContext());

                                    SQLiteDatabase basededatos = abrir.getReadableDatabase();
                                    String codigo = String.valueOf(c.getInt(0));
                                    basededatos.delete("pescador", "codigo=" + codigo, null);
                                    basededatos.close();

                                    if (codigo != "") {
                                        Toast.makeText(getContext(), "USUARIO ELIMINADO", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getContext(), "USUARIO NO EXISTE", Toast.LENGTH_SHORT).show();
                                    }
                                    return;
                                } else if (selecion == 1) {
                                    SQLiteDatabase basedatos = datos1.getReadableDatabase();
                                    int cod = codigos.get(position);
                                    c = basedatos.rawQuery("select codigo,nombre,latitud,longitud,camara,microfono from pescador where codigo=" + cod, null);
                                    //c = datos1.buscardatos(cod);
                                    if (c.moveToFirst()) {

                                        String codigo = String.valueOf(c.getInt(0));
                                        String nombre = c.getString(1);
                                        String lat = String.valueOf(c.getDouble(2));
                                        String lon = String.valueOf(c.getDouble(3));
                                        String camara = c.getString(4);
                                        String microfono = c.getString(5);

                                        Intent obj = new Intent(getContext(), Actualizar_pescador.class);

                                        obj.putExtra("nombre", nombre);
                                        obj.putExtra("latitud", lat);
                                        obj.putExtra("longitud", lon);
                                        obj.putExtra("camara", camara);
                                        obj.putExtra("microfono", microfono);
                                        obj.putExtra("codigo", codigo);

                                        startActivity(obj);
                                    }


                                } else if (selecion == 2) {


                                }

                            }
                        }).create();
                        opcion.show();


                        return true;
                    }
                });
            }
        });


    return false;

    }

}

