package com.example.estudiante.social;

import java.io.Serializable;

/**
 * Created by estudiante on 03/05/17.
 */

public class Comentario implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String de;
    private String comentario;

    public Comentario(String de, String comentario) {
        this.de = de;
        this.comentario = comentario;
    }

    public String getDe() {
        return de;
    }

    public String getComentario() {
        return comentario;
    }

}

