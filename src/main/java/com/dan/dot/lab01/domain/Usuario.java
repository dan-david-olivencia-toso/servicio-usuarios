package com.dan.dot.lab01.domain;

import javax.persistence.*;

@Entity
@Table(name = "usuario")
public class Usuario {
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String user) {
        this.usuario = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public TipoUsuario getTipo() {
        return tipo;
    }

    public void setTipo(TipoUsuario tipo) {
        this.tipo = tipo;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String usuario;
    private String password;
    @JoinColumn(name="id_tipo_usuario", referencedColumnName = "id")
    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    private TipoUsuario tipo;
}
