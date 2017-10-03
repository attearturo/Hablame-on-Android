package com.example.estudiante.social;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by estudiante on 03/05/17.
 */

public class Usuario implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String nombre;
    private String idioma_interno;
    private String idioma_externo;
    private String correo;
    private String password;
    private boolean nuevo;
    private String codigo = null;
    // private ArrayList<Publicacion> publicaciones = new ArrayList<>();

    public Usuario(String nombre, String idioma_interno, String idioma_externo, String correo, String password) {
        this.nuevo = true;
        this.nombre = nombre;
        this.idioma_externo = idioma_externo;
        this.idioma_interno = idioma_interno;
        this.correo = correo;
        this.password = password;
        this.codigo = new String("user_" + correo.replace("@", "-"));
    }

    public Usuario(String correo, String password) {
        this.nuevo = false;
        this.correo = correo;
        this.codigo = "user_" + correo.replace("@", "-");
        this.password = password;
    }

    public boolean isNuevo() {
        return nuevo;
    }

    public void setNuevo(boolean nuevo) {
        this.nuevo = nuevo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getIdioma_interno() {
        return idioma_interno;
    }

    public String getIdioma_externo() {
        return idioma_externo;
    }

    public String getCorreo() {
        return correo;
    }

    public String getPassword() {
        return password;
    }

    public void setCodigo(String codigo) {
        // TODO Auto-generated method stub
        this.codigo = codigo;
    }

    /* ========== Codigos de validacion ============== */
    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return codigo.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        if (obj instanceof Usuario) {
            Usuario u = (Usuario) obj;
            return codigo.equals(u.getCodigo());
        }
        return false;
    }

    /* =============================================== */
	/*
	 * public void crearPublicacion(String mensaje) { Publicacion p = new
	 * Publicacion(mensaje); publicaciones.add(p); }
	 *
	 * public ArrayList<Publicacion> getPublicaciones() { return publicaciones;
	 * }
	 */
    public String getCodigo() {
        return codigo;
    }
}
