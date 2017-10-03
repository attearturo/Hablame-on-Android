package com.example.estudiante.social;

import java.io.Serializable;

/**
 * Created by estudiante on 03/05/17.
 */

public class Mensaje implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String mensaje;

    public Mensaje(String mensaje) {
        // TODO Auto-generated constructor stub
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

}
