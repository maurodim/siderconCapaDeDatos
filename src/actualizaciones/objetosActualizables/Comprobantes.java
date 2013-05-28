/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actualizaciones.objetosActualizables;

import java.util.Date;

/**
 *
 * @author Administrador
 */
public class Comprobantes {
    private String codigoCliente;
    private String razonSocial;
    private String numeroComprobante;
    private int condicionVenta;
    private String codigoArticulo;
    private String descripcion;
    private Double cantidad;
    private String numeroRemito;
    private Date fechaRemito;
    private int revizado;
    private Integer numero;
    private String fechaEmisionComprobante;
    private int codigoDeposito;

    public int getCodigoDeposito() {
        return codigoDeposito;
    }

    public void setCodigoDeposito(int codigoDeposito) {
        this.codigoDeposito = codigoDeposito;
    }

    public String getFechaEmisionComprobante() {
        return fechaEmisionComprobante;
    }

    public void setFechaEmisionComprobante(String fechaEmisionComprobante) {
        this.fechaEmisionComprobante = fechaEmisionComprobante;
    }
    

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public String getCodigoArticulo() {
        return codigoArticulo;
    }

    public void setCodigoArticulo(String codigoArticulo) {
        this.codigoArticulo = codigoArticulo;
    }

    public String getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(String codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public int getCondicionVenta() {
        return condicionVenta;
    }

    public void setCondicionVenta(int condicionVenta) {
        this.condicionVenta = condicionVenta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaRemito() {
        return fechaRemito;
    }

    public void setFechaRemito(Date fechaRemito) {
        this.fechaRemito = fechaRemito;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getNumeroComprobante() {
        return numeroComprobante;
    }

    public void setNumeroComprobante(String numeroComprobante) {
        this.numeroComprobante = numeroComprobante;
    }

    public String getNumeroRemito() {
        return numeroRemito;
    }

    public void setNumeroRemito(String numeroRemito) {
        this.numeroRemito = numeroRemito;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public int getRevizado() {
        return revizado;
    }

    public void setRevizado(int revizado) {
        this.revizado = revizado;
    }
    
}
