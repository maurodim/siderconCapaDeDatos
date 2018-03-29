/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objetos;

/**
 *
 * @author Administrador
 */
public class DetalleHdr {
    private Integer numero;
    private Integer numeroHdr;
    private String numeroPedidoTango;
    private String codigoCliente;
    private String razonSocial;
    private String saldo;
    private String vuelto;
    private Boolean contactoTelefonico;
    private String horaContacto;
    private Boolean entregaCompletada;
    private String Observaciones;
    private String empresa;
    private String motivoFaltaDeEntrega;
    private Integer reenviarPedido;
    private String numeroDeComprobante;

    public String getNumeroDeComprobante() {
        return numeroDeComprobante;
    }

    public void setNumeroDeComprobante(String numeroDeComprobante) {
        this.numeroDeComprobante = numeroDeComprobante;
    }

    
    
    public Integer getReenviarPedido() {
        return reenviarPedido;
    }

    public void setReenviarPedido(Integer reenviarPedido) {
        this.reenviarPedido = reenviarPedido;
    }

    public String getMotivoFaltaDeEntrega() {
        return motivoFaltaDeEntrega;
    }

    public void setMotivoFaltaDeEntrega(String motivoFaltaDeEntrega) {
        this.motivoFaltaDeEntrega = motivoFaltaDeEntrega;
    }
    public String getVuelto() {
        return vuelto;
    }

    public void setVuelto(String vuelto) {
        this.vuelto = vuelto;
    }
 
    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }
    
    public String getObservaciones() {
        return Observaciones;
    }

    public void setObservaciones(String Observaciones) {
        this.Observaciones = Observaciones;
    }

    public String getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(String codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public Boolean getContactoTelefonico() {
        return contactoTelefonico;
    }

    public void setContactoTelefonico(Boolean contactoTelefonico) {
        this.contactoTelefonico = contactoTelefonico;
    }

    public Boolean getEntregaCompletada() {
        return entregaCompletada;
    }

    public void setEntregaCompletada(Boolean entregaCompletada) {
        this.entregaCompletada = entregaCompletada;
    }

    public String getHoraContacto() {
        return horaContacto;
    }

    public void setHoraContacto(String horaContacto) {
        this.horaContacto = horaContacto;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Integer getNumeroHdr() {
        return numeroHdr;
    }

    public void setNumeroHdr(Integer numeroHdr) {
        this.numeroHdr = numeroHdr;
    }

    public String getNumeroPedidoTango() {
        return numeroPedidoTango;
    }

    public void setNumeroPedidoTango(String numeroPedidoTango) {
        this.numeroPedidoTango = numeroPedidoTango;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }
    
    
    
}
