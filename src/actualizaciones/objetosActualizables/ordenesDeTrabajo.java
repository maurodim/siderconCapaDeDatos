/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actualizaciones.objetosActualizables;

/**
 *
 * @author Mauro Di
 */
public class ordenesDeTrabajo {
    private Integer numeroOrden;
    private Integer numeroOriginal;
    private Integer numeroDestino;
    private String numeroPedido;
    private String fechaEntrega;
    private String codigoArticulo;
    private String descripcionArticulo;
    private Double cantidadPedido;

    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
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

    public Double getCantidadPedido() {
        return cantidadPedido;
    }

    public void setCantidadPedido(Double cantidadPedido) {
        this.cantidadPedido = cantidadPedido;
    }
    
    

    public Integer getNumeroDestino() {
        return numeroDestino;
    }

    public void setNumeroDestino(Integer numeroDestino) {
        this.numeroDestino = numeroDestino;
    }

    public Integer getNumeroOrden() {
        return numeroOrden;
    }

    public void setNumeroOrden(Integer numeroOrden) {
        this.numeroOrden = numeroOrden;
    }

    public Integer getNumeroOriginal() {
        return numeroOriginal;
    }

    public void setNumeroOriginal(Integer numeroOriginal) {
        this.numeroOriginal = numeroOriginal;
    }

    public String getNumeroPedido() {
        return numeroPedido;
    }

    public void setNumeroPedido(String numeroPedido) {
        this.numeroPedido = numeroPedido;
    }
    
    
}
