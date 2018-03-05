/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objetos;

/**
 *
 * @author Administrador
 */
public class EncabezadoHdr {
    private Integer numero;
    private String fechaImpresion;
    private String fechaReparto;
    private String descripcionVehiculo;
    private Integer numeroVehiculo;
    private Integer numeroListado;
    private Double pesoListado;
    private Double pesoEntregado;
    private Integer numeroOperador;
    private String nombreOperador;
    private Integer kmIniciales;
    private Integer kmFinales;
    private Integer kmRecorridos;
    private Integer horaSalida;
    private Integer minutosSalida;
    private Integer horaLlegada;
    private Integer minutosLlegada;
    private Double totalCobranza;
    private Double totalVuelto;

    
    public Double getTotalCobranza() {
        return totalCobranza;
    }

    public void setTotalCobranza(Double totalCobranza) {
        this.totalCobranza = totalCobranza;
    }

    public Double getTotalVuelto() {
        return totalVuelto;
    }

    public void setTotalVuelto(Double totalVuelto) {
        this.totalVuelto = totalVuelto;
    }
    public String getDescripcionVehiculo() {
        return descripcionVehiculo;
    }

    public void setDescripcionVehiculo(String descripcionVehiculo) {
        this.descripcionVehiculo = descripcionVehiculo;
    }

    public String getFechaImpresion() {
        return fechaImpresion;
    }

    public void setFechaImpresion(String fechaImpresion) {
        this.fechaImpresion = fechaImpresion;
    }

    public String getFechaReparto() {
        return fechaReparto;
    }

    public void setFechaReparto(String fechaReparto) {
        this.fechaReparto = fechaReparto;
    }

    public Integer getHoraLlegada() {
        return horaLlegada;
    }

    public void setHoraLlegada(Integer horaLlegada) {
        this.horaLlegada = horaLlegada;
    }

    public Integer getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(Integer horaSalida) {
        this.horaSalida = horaSalida;
    }

    public Integer getKmFinales() {
        return kmFinales;
    }

    public void setKmFinales(Integer kmFinales) {
        this.kmFinales = kmFinales;
    }

    public Integer getKmIniciales() {
        return kmIniciales;
    }

    public void setKmIniciales(Integer kmIniciales) {
        this.kmIniciales = kmIniciales;
    }

    public Integer getKmRecorridos() {
        return kmRecorridos;
    }

    public void setKmRecorridos(Integer kmRecorridos) {
        this.kmRecorridos = kmRecorridos;
    }

    public Integer getMinutosLlegada() {
        return minutosLlegada;
    }

    public void setMinutosLlegada(Integer minutosLlegada) {
        this.minutosLlegada = minutosLlegada;
    }

    public Integer getMinutosSalida() {
        return minutosSalida;
    }

    public void setMinutosSalida(Integer minutosSalida) {
        this.minutosSalida = minutosSalida;
    }

    public String getNombreOperador() {
        return nombreOperador;
    }

    public void setNombreOperador(String nombreOperador) {
        this.nombreOperador = nombreOperador;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Integer getNumeroListado() {
        return numeroListado;
    }

    public void setNumeroListado(Integer numeroListado) {
        this.numeroListado = numeroListado;
    }

    public Integer getNumeroOperador() {
        return numeroOperador;
    }

    public void setNumeroOperador(Integer numeroOperador) {
        this.numeroOperador = numeroOperador;
    }

    public Integer getNumeroVehiculo() {
        return numeroVehiculo;
    }

    public void setNumeroVehiculo(Integer numeroVehiculo) {
        this.numeroVehiculo = numeroVehiculo;
    }

    public Double getPesoEntregado() {
        return pesoEntregado;
    }

    public void setPesoEntregado(Double pesoEntregado) {
        this.pesoEntregado = pesoEntregado;
    }

    public Double getPesoListado() {
        return pesoListado;
    }

    public void setPesoListado(Double pesoListado) {
        this.pesoListado = pesoListado;
    }
 
    
}
