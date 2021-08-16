package com.dan.dot.lab01.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "empleado")
public class Empleado {
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario user) {
        this.usuario = user;
    }

    public Boolean getHabilitado() { return habilitado; }

    public void setHabilitado(Boolean enabled) { this.habilitado = enabled; }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String mail;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    @OneToOne(cascade = CascadeType.PERSIST)
    @NotNull
    private Usuario usuario;

    private Boolean habilitado = true;
}
