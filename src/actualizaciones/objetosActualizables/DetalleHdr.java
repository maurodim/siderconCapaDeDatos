/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actualizaciones.objetosActualizables;

import actualizaciones.ActualizarHdr;
import java.util.ArrayList;

/**
 *
 * @author Administrador
 */
public class DetalleHdr implements ActualizarHdr{
        private Integer numero;
        private String cliente;
        private String numeroCliente;
        private String comprobante;
        private String importe;
        private String observaciones;
        private String vuelto;
        private Integer orden;
        private Integer entregado;
        private Integer hdr;
        private String empresa;
        private String motivoFallido;
        private String reenviar;

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getComprobante() {
        return comprobante;
    }

    public void setComprobante(String comprobante) {
        this.comprobante = comprobante;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public Integer getEntregado() {
        return entregado;
    }

    public void setEntregado(Integer entregado) {
        this.entregado = entregado;
    }

    public Integer getHdr() {
        return hdr;
    }

    public void setHdr(Integer hdr) {
        this.hdr = hdr;
    }

    public String getImporte() {
        return importe;
    }

    public void setImporte(String importe) {
        this.importe = importe;
    }

    public String getMotivoFallido() {
        return motivoFallido;
    }

    public void setMotivoFallido(String motivoFallido) {
        this.motivoFallido = motivoFallido;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getNumeroCliente() {
        return numeroCliente;
    }

    public void setNumeroCliente(String numeroCliente) {
        this.numeroCliente = numeroCliente;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public String getReenviar() {
        return reenviar;
    }

    public void setReenviar(String reenviar) {
        this.reenviar = reenviar;
    }

    public String getVuelto() {
        return vuelto;
    }

    public void setVuelto(String vuelto) {
        this.vuelto = vuelto;
    }

    @Override
    public ArrayList leerEncabezados() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList leerDetalles() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void copiarEncabezados(ArrayList listadoEnc) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void copiarDetalles(ArrayList listadoDet) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
        
        
}
