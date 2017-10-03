package com.example.estudiante.social;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Observable;
import java.util.Observer;

public class SingIn extends AppCompatActivity implements Observer, View.OnClickListener {

    private EditText correo, nombre, pass, iniciarSesion;
    private String cadena_correo, cadena_nombre, cadena_pass, cadena_inter, cadena_exter;
    private Button singin, iniciar;
    private Usuario usuario;
    private boolean formulario_completo;
    private Intent intent = null;

    private Spinner listLearning, listTeaching;
    private String[] learningData = {"Aprendo", "Español", "Inglés", "Alemán", "Francés", "Portugues"};
    private String[] teachingData = {"Enseño", "Español", "Inglés", "Alemán", "Francés", "Portugues"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_in);
        getSupportActionBar().hide();

        ComunicacionCliente.getInstance().addObserver(this);

        formulario_completo = false;

        correo = (EditText) findViewById(R.id.correo);
        nombre = (EditText) findViewById(R.id.nombre);
        pass = (EditText) findViewById(R.id.pass);

        singin = (Button) findViewById(R.id.bt_singin);
        singin.setOnClickListener(this);

        iniciar = (Button) findViewById(R.id.iniciar);
        iniciar.setOnClickListener(this);

        listLearning = (Spinner) findViewById(R.id.listLearning);
        listTeaching = (Spinner) findViewById(R.id.listTeaching);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, learningData);
        ArrayAdapter<String> adapterT = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, teachingData);
        listLearning.setAdapter(adapter);
        listTeaching.setAdapter(adapterT);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.bt_singin:
                if (nombre.getText().toString().length() > 0) {
                    cadena_nombre = nombre.getText().toString();
                    if (correo.getText().toString().length() > 0) {
                        if (correo.getText().toString().contains("@") && correo.getText().toString().contains(".com")) {
                        //if (correo.getText().toString().contains(".com")) {
                            cadena_correo = correo.getText().toString();
                            if (pass.getText().toString().length() > 0) {
                                cadena_pass = pass.getText().toString();
                                            formulario_completo = true;
                            }
                        } else {
                            onToast(view, "Ups! Parece que este correo no es válido");
                        }
                    }
                }

                if (formulario_completo) {
                    usuario = new Usuario(cadena_nombre, cadena_inter, cadena_exter, cadena_correo, cadena_pass);
                    ComunicacionCliente.getInstance().peticion_acceso(usuario);
                    ComunicacionCliente.getInstance().peticion_acceso(usuario);
                } else {
                    onToast(view, "Por favor no dejes datos sin rellenar");
                }


                break;

            case R.id.iniciar:
                intent = new Intent(this, Login.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        ComunicacionCliente.getInstance().deleteObserver(this);
    }

    private void draw_interface() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                onToast(findViewById(R.id.action_settings), "Ups! Este usuario no esta disponible");
            }
        });
    }

    public void onToast(View v, String mensaje) {
        Toast toast = Toast.makeText(this, mensaje, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
    }

    @Override
    public void update(Observable observable, Object o) {
        System.out.println("====================== UPDATE SINGIN ======================");
        if (o instanceof Mensaje) {
            Mensaje m = (Mensaje) o;
            if (m.getMensaje().contains("aceptado")) {

                intent = new Intent(this, Home.class);
                intent.putExtra("USUARIO", usuario);

                ComunicacionCliente.getInstance().deleteObserver(this);
                startActivity(intent);

            } else if (m.getMensaje().contains("denegado")) {
                draw_interface();
            }

        }

    }

}
