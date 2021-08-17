package com.dan.dot.lab01.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "obra")
public class Obra {

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Float getLatitud() {
        return latitud;
    }

    public void setLatitud(Float latitud) {
        this.latitud = latitud;
    }

    public Float getLongitud() {
        return longitud;
    }

    public void setLongitud(Float longitud) {
        this.longitud = longitud;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Integer getSuperficie() {
        return superficie;
    }

    public void setSuperficie(Integer superficie) {
        this.superficie = superficie;
    }

    public TipoObra getTipo() {
        return tipo;
    }

    public void setTipo(TipoObra tipo) {
        this.tipo = tipo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Boolean getHabilitado() { return habilitado; }

    public void setHabilitado(Boolean habilitado) { this.habilitado = habilitado; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Id: ").append(this.id).append("\n");
        sb.append("Descripción: ").append(this.descripcion).append("\n");
        sb.append("Latitud: ").append(this.latitud).append("\n");
        sb.append("Longitud: ").append(this.longitud).append("\n");
        sb.append("Dirección: ").append(this.direccion).append("\n");
        sb.append("Superficie: ").append(this.superficie).append("\n");
        sb.append(this.tipo.toString());
        return sb.toString();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String descripcion;
    private Float latitud;
    private Float longitud;
    private String direccion;
    private Integer superficie;
    private Boolean habilitado;
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_tipo_obra", referencedColumnName = "id")
    private TipoObra tipo;
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_cliente", referencedColumnName = "id")
    @JsonBackReference // RO 16-08-21 - Anotación para marcar que esta propiedad se debe ignorar al serializar, evitando referencias recursivas en loop
    private Cliente cliente;
}
