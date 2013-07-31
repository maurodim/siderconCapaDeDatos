/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objetos;

import java.sql.Connection;
import java.util.Date;
import proceso.Coneccion;

/**
 *
 * @author USUARIO
 */
public class PedidosParaReparto {
	/*
	 * ACA SOLAMENTE GENERO EL OBJETO PEDIDO CON LOS DATOS ESENCIALES PARA TRABAJAR LAS 
	 * PRIMERAS PANTALLAS SIN CARGA DE CALCULOS
	 * EL IDPEDIDO DEBE SER UNICO PARA CADA PEDIDO ENVIADO, EL CALCULO DEL PESO Y EL DESDOBLAMIENTO 
	 * DE LA INFORMACION EN LAS DISTINTAS BASES QUE LO HAGA EL SISTEMA DE EXPORTACION DE PEDIDOS
	 * 
	 * TENER EN CUENTA QUE LA GRILLA INICIAL VA A LISTAR ESTOS POR FECHA Y LOS VA A TRABAJAR MODIFICANDO SU CARGA
	 * LA VARIABLE ENTREGACOMPELTADA ES PARA DETERMINAR SI TIENE MATERIALES PENDIENTES
	 * ESE PEDIDO ( PARA REENVIARLO SE GENERA UN NUEVO IDPEDIDO PERO SE 
	 * MANTIENE EL NUMERO DE PEDIDO DE TANGO
     * PARA GENERAR LA INDEPENDENCIA DE ESTE SISTEMA A TANGO Y MANEJARLO  EN FORMA DISTINTA
	 */
	private Integer iDPedido;
	private String codigoTangoDePedido;
	private String razonSocial;
        private String codigoCliente;
	private Double pesoTotal;
	private String fechaEnvio;
	private Integer vehiculoAsignado;
	private Boolean entregaCompletada;
	private Connection coneccionPedidos=null;
	private Integer numeroDeProceso;
        private Boolean confirmacionPorceso;
        private Integer codigoDeposito;
        private String codigoArticulo;
        private String descripcionArticulo;
        private Double cantidadArticulo;
        private Double cantidadArticuloPendiente;
        private Double cantidadArticulosTotales;
        private Double pesoItems;
        private Double saldoCliente;
        private Integer condicionDeVenta;
        private Integer condicionEstadoDelPedido;
        private int estado;
        private Integer numeroDeListadoDeMateriales;
        private Integer numeroDeHojaDeRuta;
        private String fechaPedidosTango;
        private String observaciones;
        private String observaciones1;
        private String observaciones2;
        private Date fechaActualizacionSaldoCliente;
        private String numeroComprobante;
        private String saldoACobrar;
        private String vuelto;
        private String empresa;
        private Integer numeroDeFletero;
        private Boolean verificacion;
        private int zonaAsignada;
        private int alertaAsignada;
        private String zonaDescripcion;
        private String alertaDescripcion;
        private Integer numeroDeRevisionDeListado;
        private Integer numeroVendedor;
        private String nombreVendedor;
        private int verificadorRevision;
        private int vehiculoAnterior;

    public int getVehiculoAnterior() {
        return vehiculoAnterior;
    }

    public void setVehiculoAnterior(int vehiculoAnterior) {
        this.vehiculoAnterior = vehiculoAnterior;
    }
        

    public int getVerificadorRevision() {
        return verificadorRevision;
    }

    public void setVerificadorRevision(int verificadorRevision) {
        this.verificadorRevision = verificadorRevision;
    }
        

    public Integer getNumeroVendedor() {
        return numeroVendedor;
    }

    public void setNumeroVendedor(Integer numeroVendedor) {
        this.numeroVendedor = numeroVendedor;
    }

    public String getNombreVendedor() {
        return nombreVendedor;
    }

    public void setNombreVendedor(String nombreVendedor) {
        this.nombreVendedor = nombreVendedor;
    }

    public Integer getNumeroDeRevisionDeListado() {
        return numeroDeRevisionDeListado;
    }

    public void setNumeroDeRevisionDeListado(Integer numeroDeRevisionDeListado) {
        this.numeroDeRevisionDeListado = numeroDeRevisionDeListado;
    }

        
    public String getZonaDescripcion() {
        return zonaDescripcion;
    }

    public void setZonaDescripcion(String zonaDescripcion) {
        this.zonaDescripcion = zonaDescripcion;
    }

    public String getAlertaDescripcion() {
        return alertaDescripcion;
    }

    public void setAlertaDescripcion(String alertaDescripcion) {
        this.alertaDescripcion = alertaDescripcion;
    }
        

    public int getZonaAsignada() {
        return zonaAsignada;
    }

    public void setZonaAsignada(int zonaAsignada) {
        this.zonaAsignada = zonaAsignada;
    }

    public int getAlertaAsignada() {
        return alertaAsignada;
    }

    public void setAlertaAsignada(int alertaAsignada) {
        this.alertaAsignada = alertaAsignada;
    }
        

    public Boolean getVerificacion() {
        return verificacion;
    }

    public void setVerificacion(Boolean verificacion) {
        this.verificacion = verificacion;
    }
        

    public Integer getNumeroDeFletero() {
        return numeroDeFletero;
    }

    public void setNumeroDeFletero(Integer numeroDeFletero) {
        this.numeroDeFletero = numeroDeFletero;
    }
        
    public String getObservaciones1() {
        return observaciones1;
    }

    public void setObservaciones1(String observaciones1) {
        this.observaciones1 = observaciones1;
    }

    public String getObservaciones2() {
        return observaciones2;
    }

    public void setObservaciones2(String observaciones2) {
        this.observaciones2 = observaciones2;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }
        

    public String getSaldoACobrar() {
        return saldoACobrar;
    }

    public void setSaldoACobrar(String saldoACobrar) {
        this.saldoACobrar = saldoACobrar;
    }

    public String getVuelto() {
        return vuelto;
    }

    public void setVuelto(String vuelto) {
        this.vuelto = vuelto;
    }
        

    public String getNumeroComprobante() {
        return numeroComprobante;
    }

    public void setNumeroComprobante(String numeroComprobante) {
        this.numeroComprobante = numeroComprobante;
    }

        
    public Date getFechaActualizacionSaldoCliente() {
        return fechaActualizacionSaldoCliente;
    }

    public void setFechaActualizacionSaldoCliente(Date fechaActualizacionSaldoCliente) {
        this.fechaActualizacionSaldoCliente = fechaActualizacionSaldoCliente;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
        

        
    public String getFechaPedidosTango() {
        return fechaPedidosTango;
    }

    public void setFechaPedidosTango(String fechaPedidosTango) {
        this.fechaPedidosTango = fechaPedidosTango;
    }

        
        
    public Integer getNumeroDeHojaDeRuta() {
        return numeroDeHojaDeRuta;
    }

    public void setNumeroDeHojaDeRuta(Integer numeroDeHojaDeRuta) {
        this.numeroDeHojaDeRuta = numeroDeHojaDeRuta;
    }

    public Integer getNumeroDeListadoDeMateriales() {
        return numeroDeListadoDeMateriales;
    }

    public void setNumeroDeListadoDeMateriales(Integer numeroDeListadoDeMateriales) {
        this.numeroDeListadoDeMateriales = numeroDeListadoDeMateriales;
    }
        
    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
        
    public Integer getCondicionEstadoDelPedido() {
        return condicionEstadoDelPedido;
    }

    public void setCondicionEstadoDelPedido(Integer condicionEstadoDelPedido) {
        this.condicionEstadoDelPedido = condicionEstadoDelPedido;
    }

    public Double getCantidadArticulosTotales() {
        return cantidadArticulosTotales;
    }

    public void setCantidadArticulosTotales(Double cantidadArticulosEntregados) {
        this.cantidadArticulosTotales = cantidadArticulosEntregados;
    }

        
    public Double getCantidadArticuloPendiente() {
        return cantidadArticuloPendiente;
    }

    public void setCantidadArticuloPendiente(Double cantidadArticuloPendiente) {
        this.cantidadArticuloPendiente = cantidadArticuloPendiente;
    }
   
    public Integer getCondicionDeVenta() {
        return condicionDeVenta;
    }

    public void setCondicionDeVenta(Integer condicionDeVenta) {
        this.condicionDeVenta = condicionDeVenta;
    }
        
    public Double getSaldoCliente() {
        return saldoCliente;
    }

    public void setSaldoCliente(Double saldoCliente) {
        this.saldoCliente = saldoCliente;
    }
        

    public String getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(String codigoCliente) {
        this.codigoCliente = codigoCliente;
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

    public Double getPesoItems() {
        return pesoItems;
    }

    public void setPesoItems(Double pesoItems) {
        this.pesoItems = pesoItems;
    }
        

    public Integer getCodigoDeposito() {
        return codigoDeposito;
    }

    public void setCodigoDeposito(Integer codigoDeposito) {
        this.codigoDeposito = codigoDeposito;
    }

    public Connection getConeccionPedidos() {
        return coneccionPedidos;
    }

    public void setConeccionPedidos(Connection coneccionPedidos) {
        this.coneccionPedidos = coneccionPedidos;
    }

    public Boolean getConfirmacionPorceso() {
        return confirmacionPorceso;
    }

    public void setConfirmacionPorceso(Boolean confirmacionPorceso) {
        this.confirmacionPorceso = confirmacionPorceso;
    }

    public Integer getNumeroDeProceso() {
        return numeroDeProceso;
    }

    public void setNumeroDeProceso(Integer numeroDeProceso) {
        this.numeroDeProceso = numeroDeProceso;
    }
        
	public Boolean getEntregaCompletada() {
		return entregaCompletada;
	}

	public void setEntregaCompletada(Boolean entregaCompletada) {
		this.entregaCompletada = entregaCompletada;
	}
	
	public String getCodigoTangoDePedido() {
		return codigoTangoDePedido;
	}

	public void setCodigoTangoDePedido(String codigoTangoDePedido) {
		this.codigoTangoDePedido = codigoTangoDePedido;
	}

	public String getFechaEnvio() {
		return fechaEnvio;
	}

	public void setFechaEnvio(String fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	public Integer getiDPedido() {
		return iDPedido;
	}

	public void setiDPedido(Integer iDPedido) {
		this.iDPedido = iDPedido;
	}

	public Double getPesoTotal() {
		return pesoTotal;
	}

	public void setPesoTotal(Double pesoTotal) {
            //DecimalFormat dc=new DecimalFormat("####.#");
            //String peso=dc.format(pesoTotal);
            //this.pesoTotal=Double.parseDouble(peso);
            this.pesoTotal = pesoTotal;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public Integer getVehiculoAsignado() {
		return vehiculoAsignado;
	}

	public void setVehiculoAsignado(Integer vehiculoAsignado) {
		this.vehiculoAsignado = vehiculoAsignado;
	}

    public PedidosParaReparto() {
         
       // coneccionPedidos=Coneccion.ObtenerConeccion();
        this.empresa = "";
        this.saldoCliente=0.00;
    }
	
}
