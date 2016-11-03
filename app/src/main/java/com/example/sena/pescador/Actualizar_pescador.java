package com.example.sena.pescador;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Actualizar_pescador extends AppCompatActivity {
    private EditText uno, dos, tres, cuatro, cinco, seis;
    private String nombre, camara, microfono, lat, lon, code;
    private Button btnactualizar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_pescador);
        uno = (EditText) findViewById(R.id.txtcodigo);
        dos = (EditText) findViewById(R.id.txtnombre);
        tres = (EditText) findViewById(R.id.txtlatitud);
        cuatro = (EditText) findViewById(R.id.txtlongitud);
        cinco = (EditText) findViewById(R.id.camara);
        seis = (EditText) findViewById(R.id.txtmicrofono);
        btnactualizar=(Button)findViewById(R.id.btnactualizar);


        code = getIntent().getStringExtra("codigo");
        nombre = getIntent().getStringExtra("nombre");
        lat = getIntent().getStringExtra("latitud");
        lon = getIntent().getStringExtra("longitud");
        camara = getIntent().getStringExtra("camara");
        microfono = getIntent().getStringExtra("microfono");

        uno.setText("" + code);
        dos.setText("" + nombre);
        tres.setText("" + lat);
        cuatro.setText("" + lon);
        cinco.setText("" + camara);
        seis.setText("" + microfono);

        uno.setEnabled(false);

        btnactualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BDSQLITE abrirBD = new BDSQLITE(getApplicationContext());
                SQLiteDatabase basedatos= abrirBD.getReadableDatabase();
                String cod = uno.getText().toString();
                String nom = dos.getText().toString();
                String lat = tres.getText().toString();
                String lon = cuatro.getText().toString();
                String cam = cinco.getText().toString();
                String mic = seis.getText().toString();
                ContentValues registro = new ContentValues();
                registro.put("nombre",cod);
                registro.put("nombre",nom);
                registro.put("latitud",lat);
                registro.put("longitud",lon);
                registro.put("camara",cam);
                registro.put("microfono",mic);
                int canti = basedatos.update("pescador",registro,"codigo="+cod,null);
                basedatos.close();
                if (canti==1) {
                    Toast.makeText(getBaseContext(), "DATOS MODIFICADOS", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getBaseContext(), "DATOS NO MODIFICADOS", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
