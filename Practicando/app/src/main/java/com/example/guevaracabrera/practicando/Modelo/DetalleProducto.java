package com.example.guevaracabrera.practicando.Modelo;

/**
 * Created by Alehexis on 27/10/2015.
 */
public class DetalleProducto {
    private int IdDetalle;
    private Pedido pedido;
    private Productos producto;
    private double precio;
    private int cantidad;
    private double Subtotal;


    public DetalleProducto(double precio, int cantidad, Productos producto) {
        this.precio = precio;
        this.cantidad = cantidad;
        this.producto = producto;
    }

    public DetalleProducto() {

    }

    public DetalleProducto(Pedido pedido, Productos producto, double precio, int cantidad) {
        this.pedido = pedido;
        this.producto = producto;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public int getIdDetalle() {
        return IdDetalle;
    }

    public void setIdDetalle(int idDetalle) {
        IdDetalle = idDetalle;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Productos getProducto() {
        return producto;
    }

    public void setProducto(Productos producto) {
        this.producto = producto;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getSubtotal() {
        return Subtotal;
    }

    public void setSubtotal() {
        Subtotal = this.cantidad * this.precio;
    }
}
