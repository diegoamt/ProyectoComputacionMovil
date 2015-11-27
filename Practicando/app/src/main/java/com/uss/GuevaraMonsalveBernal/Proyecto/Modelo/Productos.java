package com.uss.GuevaraMonsalveBernal.Proyecto.Modelo;

import java.io.Serializable;

/**
 * Created by Alehexis on 27/10/2015.
 */
@SuppressWarnings("serial")
public class Productos implements Serializable {
    private int idproducto;
    private String nombre;
    private double precio;
    private String descripcion;
    private String urL;

    public Productos(int idproducto, String urL, String descripcion, double precio, String nombre) {
        this.idproducto = idproducto;
        this.urL = urL;
        this.descripcion = descripcion;
        this.precio = precio;
        this.nombre = nombre;
    }

    public Productos(int idproducto, String nombre) {
        this.idproducto = idproducto;
        this.nombre = nombre;
    }

    public Productos() {

    }

    public int getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(int idproducto) {
        this.idproducto = idproducto;
    }

    public String getUrL() {
        return urL;
    }

    public void setUrL(String urL) {
        this.urL = urL;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
