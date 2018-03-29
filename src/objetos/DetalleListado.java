/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objetos;

/**
 *
 * @author mauro di
 */
public class DetalleListado {
    private String codigoArticulo;
    private String descripcionArticulo;
    private Double cantidad;
    private Double peso;

    public DetalleListado(String codigoArticulo, String descripcionArticulo, Double cantidad, Double peso) {
        this.codigoArticulo = codigoArticulo;
        this.descripcionArticulo = descripcionArticulo;
        this.cantidad = cantidad;
        this.peso = peso;
    }

    public String getCodigoArticulo() {
        return codigoArticulo;
    }

    public void setCodigoArticulo(String codigoArticulo) {
        this.codigoArticulo = codigoArticulo;
    }

    public String getDescripcionArticulo() {
        return descripcionArticulo;
    }

    public void setDescripcionArticulo(String descripcionArticulo) {
        this.descripcionArticulo = descripcionArticulo;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }
    
}
