/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actualizaciones.objetosActualizables;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Date;

/**
 *
 * @author Administrador
 */
public class ClientesAct {
    private String codigo;
    private String razonSocial;
    private Date fecha;
    private String numComprobante;
    private Double importe;
    private int revizado;
    private Integer numero;
    private Date fechaExportacion;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getFechaExportacion() {
        return fechaExportacion;
    }

    public void setFechaExportacion(Date fechaExportacion) {
        this.fechaExportacion = fechaExportacion;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe){
        System.err.println("IMPORTE QUE INGRESA :"+importe);
        this.importe =importe;
    }

    public String getNumComprobante() {
        return numComprobante;
    }

    public void setNumComprobante(String numComprobante) {
        this.numComprobante = numComprobante;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        System.out.println("nombre con error:"+razonSocial);
        this.razonSocial = razonSocial.replace("'"," ");
    }

    public int getRevizado() {
        return revizado;
    }

    public void setRevizado(int revizado) {
        this.revizado = revizado;
    }
    
    
}
