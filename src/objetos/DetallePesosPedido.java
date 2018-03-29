/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objetos;

/**
 *
 * @author Administrador
 */
public class DetallePesosPedido {
    private String codigoArticulo;
    private String descripcionArticulo;
    private Double cantidadArticulo;
    private Double pesoUnitario;
    private Double pesoTotal;
    private Integer numero;

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    
    public Double getCantidadArticulo() {
        return cantidadArticulo;
    }

    public void setCantidadArticulo(Double cantidadArticulo) {
        this.cantidadArticulo = cantidadArticulo;
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

    public Double getPesoTotal() {
        return pesoTotal;
    }

    public void setPesoTotal(Double pesoTotal) {
        this.pesoTotal = pesoTotal;
    }

    public Double getPesoUnitario() {
        return pesoUnitario;
    }

    public void setPesoUnitario(Double pesoUnitario) {
        this.pesoUnitario = pesoUnitario;
    }
    
}
