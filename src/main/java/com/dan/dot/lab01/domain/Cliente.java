package com.dan.dot.lab01.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
@Entity
@Table(name = "cliente")
public class Cliente {
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Double getMaxCuentaCorriente() {
        return maxCuentaCorriente;
    }

    public void setMaxCuentaCorriente(Double maxCuentaCorriente) {
        this.maxCuentaCorriente = maxCuentaCorriente;
    }

    public Boolean getHabilitadoOnline() {
        return habilitadoOnline;
    }

    public void setHabilitadoOnline(Boolean habilitadoOnline) {
        this.habilitadoOnline = habilitadoOnline;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario user) {
        this.usuario = user;
    }

    public List<Obra> getObras() {
        return obras;
    }

    public void setObras(List<Obra> obras) {
        this.obras = obras;
    }

    public Date getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "razon_social")
    private String razonSocial;
    private String cuit;
    private String mail;
    @Column(name = "max_cuenta_corriente")
    private Double maxCuentaCorriente;
    @Column(name = "habilitado_online")
    private Boolean habilitadoOnline;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    @NotNull
    private Usuario usuario;
    @Column(name = "fecha_baja")
    private Date fechaBaja;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Obra> obras;

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", razonSocial='" + razonSocial + '\'' +
                ", cuit='" + cuit + '\'' +
                ", mail='" + mail + '\'' +
                '}';
    }
}
