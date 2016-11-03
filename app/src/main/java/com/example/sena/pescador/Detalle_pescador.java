package com.example.sena.pescador;




import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import android.widget.Toast;

import java.io.File;



public class Detalle_pescador extends AppCompatActivity {

    private EditText uno, dos, tres, cuatro, cinco, seis;

    private ImageView txtcamara2;

    private String nombre, camara, microfono, lat, lon, code;
    private FloatingActionButton flotante;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_pescador);


        uno = (EditText) findViewById(R.id.txtcodigo);
        dos = (EditText) findViewById(R.id.txtnombre2);
        tres = (EditText) findViewById(R.id.txtlatitud);
        cuatro = (EditText) findViewById(R.id.txtlongitud);
        cinco = (EditText) findViewById(R.id.camara);
        seis = (EditText) findViewById(R.id.txtmicrofono);
        txtcamara2 = (ImageView) findViewById(R.id.txtcamara2);
        flotante=(FloatingActionButton)findViewById(R.id.flotante);


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
        dos.setEnabled(false);
       tres.setEnabled(false);
        cuatro.setEnabled(false);
        cinco.setEnabled(false);
        seis.setEnabled(false);
        flotante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent obj = new Intent(Detalle_pescador.this, MapsActivity.class);


                obj.putExtra("lat", lat);
                obj.putExtra("lon", lon);
                startActivity(obj);

            }
        });




        try {
            File root = new File(camara);


            if (root.exists()) {

                Bitmap myBitmap = BitmapFactory.decodeFile(root.getAbsolutePath());
                txtcamara2.setImageBitmap(myBitmap);

            } else {
                Toast.makeText(this, "el archivo no existe", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}




