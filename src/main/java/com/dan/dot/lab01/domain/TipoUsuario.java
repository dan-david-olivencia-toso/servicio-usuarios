package com.dan.dot.lab01.domain;

public class TipoUsuario {
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    private Integer id;
    private String tipo;
}
