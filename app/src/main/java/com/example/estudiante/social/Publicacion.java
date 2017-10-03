package com.example.estudiante.social;

/**
 * Created by estudiante on 04/05/17.
 */

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Publicacion implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String nombre, texto, codigo, correo, fecha;
    // private ArrayList<Comentario> comentarios = new ArrayList<>();

    public Publicacion(String nombre, String texto, String correo) {

        this.texto = texto;
        this.nombre = nombre;
        this.correo = correo;
        String cadena = new SimpleDateFormat("yyyy/MM/dd HH:mm").format(new Date()).replace("/", "-");

        String[] fecha_hora = cadena.split(" ");
        this.fecha = fecha_hora[1];

        String cadena_dos = cadena.replace(" ", "-");
        String cadena_tres = cadena_dos.replace(":", "-");
        this.codigo = cadena_tres;

    }

    public String getFecha() {
        return fecha;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

}