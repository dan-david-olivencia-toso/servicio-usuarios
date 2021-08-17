package com.dan.dot.lab01.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tipo_obra")
public class TipoObra {
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String descripcion) {
        this.tipo = descripcion;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Id: ").append(this.id).append("\n");
        sb.append("Tipo: ").append(this.tipo).append("\n");

        return sb.toString();
    }

    @Id
    private Integer id;
    private String tipo;
}
