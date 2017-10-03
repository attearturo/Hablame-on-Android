package com.example.estudiante.social;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

public class Login extends AppCompatActivity implements Observer, View.OnClickListener {
    private Button bt_login, registrarme;
    private EditText correo, pass;
    private AdapterView registrarseLink;
    private String cadena_correo, cadena_pass, posibles_respuestas;
    private boolean formulario_completo;
    private Intent intent = null;
    private Usuario usuario = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        ComunicacionCliente.getInstance().addObserver(this);

        bt_login = (Button) findViewById(R.id.bt_login);
        bt_login.setOnClickListener(this);
        registrarme = (Button) findViewById(R.id.registrarme);
        registrarme.setOnClickListener(this);

        correo = (EditText) findViewById(R.id.correo_usuario);
        pass = (EditText) findViewById(R.id.pass_usuario);


        this.formulario_completo = false;

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.bt_login:

                if (correo.getText().toString().length() > 0) {
                    cadena_correo = correo.getText().toString();
                    if (pass.getText().toString().length() > 0) {
                        if (correo.getText().toString().contains("@") && correo.getText().toString().contains(".com")) {
                            cadena_pass = pass.getText().toString();
                            this.formulario_completo = true;
                        } else {
                            onToast(view, "Ups! Parece que este correo no es válido");
                        }
                    }
                }

                if (formulario_completo == true) {
                    usuario = new Usuario(cadena_correo, cadena_pass);
                    ComunicacionCliente.getInstance().peticion_acceso(usuario);
                }

                break;
            case R.id.registrarme:
                intent = new Intent(this, SingIn.class);
                startActivity(intent);
                break;
        }
    }

    private void draw_interface(final String cadena) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                posibles_respuestas = cadena;

                switch (posibles_respuestas) {
                    case "denegado":
                        onToast(findViewById(R.id.activity_login), "Este correo no tiene una cuenta en Habláme. Aprovecha y registrate").show();
                        break;

                    case "pass_no":
                        onToast(findViewById(R.id.activity_login), "Ups! Contraseña incorrecta").show();
                        break;

                }
            }
        });
    }

    public Toast onToast(View v, String mensaje) {
        Toast toast = Toast.makeText(this, mensaje, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        return toast;
    }

    @Override
    public void update(Observable observable, Object o) {

        if (o instanceof Mensaje) {
            Mensaje mensaje = (Mensaje) o;
            draw_interface(mensaje.getMensaje());

        } else if (o instanceof Usuario) {
            usuario = (Usuario) o;
            intent = new Intent(this, Home.class);
            intent.putExtra("USUARIO", usuario);

            ComunicacionCliente.getInstance().deleteObserver(this);
            startActivity(intent);
        }
    }
}
