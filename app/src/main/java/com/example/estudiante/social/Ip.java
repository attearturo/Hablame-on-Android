package com.example.estudiante.social;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Observable;
import java.util.Observer;

public class Ip extends AppCompatActivity implements View.OnClickListener {
    private Button conectar;
    private EditText ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ip);
        ip = (EditText) findViewById(R.id.ip);
        conectar = (Button) findViewById(R.id.bt_ip);
        conectar.setOnClickListener(this);
        getSupportActionBar().hide();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_ip:
                if (ip.getText().toString() != "172.30.167.46") {
                    ComunicacionCliente.getInstance().setIpServidor(ip.getText().toString());
                    startActivity(new Intent(this, Login.class));
                    break;
                } else {
                    onToast(v, "Ups! Parece que este correo no es válido");
                }
        }
    }

    public void onToast(View v, String mensaje) {
        Toast toast = Toast.makeText(this, mensaje, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
    }

}
