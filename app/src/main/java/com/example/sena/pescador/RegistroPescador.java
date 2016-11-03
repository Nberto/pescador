package com.example.sena.pescador;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sena on 21/04/2016.
 */
public class RegistroPescador extends android.support.v4.app.Fragment {

    private EditText txtcamara, txtlat, txtlong, txtnombres, txtmicrofono;
    private Button btnagregar;
    private ImageButton btnregistrar, microfono;
    private ImageButton camara;

    private LocationManager manager;
    private LocationListener listener;

    private class GPS implements LocationListener {
        private String statusString = "";

        @Override
        public void onLocationChanged(final Location location) {
            location.getLongitude();
            location.getLatitude();

            if (location != null) {
                btnregistrar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Double lat, lon;
                        lat = location.getLatitude();
                        lon = location.getLongitude();

                        txtlat.setText("" + lat);
                        txtlong.setText("" + lon);

                    }
                });
            }

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }

    private final int TAKE_PICTURE = 1;
    Uri outputFileUri;
    static final int SOUND_RECORDING = 0;
    Uri outputFile;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.resgistro_pescador, container, false);

        txtlat = (EditText) view.findViewById(R.id.txtlat);
        txtlong = (EditText) view.findViewById(R.id.txtlong);
        txtcamara = (EditText) view.findViewById(R.id.txtcamara);
        txtnombres = (EditText) view.findViewById(R.id.txtnombres);
        txtmicrofono = (EditText) view.findViewById(R.id.txtmicrofono);
        btnregistrar = (ImageButton) view.findViewById(R.id.btnregistrar);
        btnagregar = (Button) view.findViewById(R.id.btnagregar);
        camara = (ImageButton) view.findViewById(R.id.camara);
        microfono = (ImageButton) view.findViewById(R.id.microfono);
//GPS

        manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        listener = new GPS();


        /*camara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cam = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File file = new File(Environment.getExternalStorageDirectory(),"/DCIM/Camera/IMG"+agregardatos()+ ".jpg");

                outputFileUri = Uri.fromFile(file);
                cam.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                startActivityForResult(cam, TAKE_PICTURE);

            }
        });*/


        camara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File root = Environment.getExternalStorageDirectory();
                File file = new File(root.getAbsolutePath(), "/DCIM/Camera/IMG" + agregardatos() + ".jpg");

                try {
                    file.createNewFile();
                    FileOutputStream Stream = new FileOutputStream(file);

                    Stream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Intent cam = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                outputFileUri = Uri.fromFile(file);
                cam.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                startActivityForResult(cam, TAKE_PICTURE);
            }


        });
        microfono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File root = Environment.getExternalStorageDirectory();
                File f = new File(root.getAbsolutePath(), "/Recording/record" + agregaraudio() + ".3gpp");

                try {
                    f.createNewFile();
                    FileOutputStream outputStream = new FileOutputStream(f);

                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Intent micro = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
                outputFile = Uri.fromFile(f);
                micro.putExtra(MediaStore.EXTRA_OUTPUT, outputFile);
                startActivityForResult(micro, SOUND_RECORDING);
            }
        });

        btnagregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BDSQLITE abrirBD = new BDSQLITE(getContext());

                SQLiteDatabase basededatos = abrirBD.getReadableDatabase();

                String nom = txtnombres.getText().toString();
                String lat = txtlat.getText().toString();
                String lon = txtlong.getText().toString();
                String cam = txtcamara.getText().toString();
                String mic = txtmicrofono.getText().toString();

                ContentValues registro = new ContentValues();
                registro.put("nombre", nom);
                registro.put("latitud", lat);
                registro.put("longitud", lon);
                registro.put("camara", cam);
                registro.put("microfono", mic);

                basededatos.insert("pescador", null, registro);
                basededatos.close();
                txtnombres.setText("");
                txtlat.setText("");
                txtlong.setText("");
                txtcamara.setText("");
                txtmicrofono.setText("");
                Toast.makeText(getContext(), "REGISTROS GUARDADOS", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();


        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, listener);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_PICTURE && resultCode == getActivity().RESULT_OK) {
         // String dir=outputFileUri.getPath().toString();
           txtcamara.setText(outputFileUri.getPath().toString());
        }
        if (requestCode == SOUND_RECORDING && resultCode == getActivity().RESULT_OK) {
            txtmicrofono.setText("" + outputFile.getPath().toString());
        }


    }


    @SuppressLint("SimpleDateFormat")
    private String agregardatos() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmdd_hhmmss");
        String date = dateFormat.format(new Date());
        String nuevocodigo = "_" + date;
        return nuevocodigo;
    }

    private String agregaraudio() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
        String date = dateFormat.format(new Date());
        String nuevocodigo = "-" + date;
        return nuevocodigo;
    }


}