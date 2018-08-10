/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objetos;

import actualizaciones.ChequearCantidadesPedidos;
import interfaces.Actualizable;
import interfaces.Editables;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import proceso.Coneccion;
import siderconcapadatos.SiderconCapaatos;
import siderconcapadatos.tablas.miTablaModificacion;

/**
 *
 * @author USUARIO
 */
public class PedidosParaReparto implements Editables{
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
        private Integer idPedidoEnTango;
        private int notificado;

    public int getNotificado() {
        return notificado;
    }

    public void setNotificado(int notificado) {
        this.notificado = notificado;
    }
        
        

    public Integer getIdPedidoEnTango() {
        return idPedidoEnTango;
    }

    public void setIdPedidoEnTango(Integer idPedidoEnTango) {
        this.idPedidoEnTango = idPedidoEnTango;
    }
        

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

    @Override
    public ArrayList ListarDetalleDePedidos(String codigoPedido) {
            
                Coneccion cn=new Coneccion();
                Connection cp=cn.getCn();
                ArrayList listaPed=new ArrayList();
                try {
                String sql="select *,(select TABLA1.actualizacion from TABLA1 where TABLA1.COD_CLI=pedidos_carga1.COD_CLIENT group by TABLA1.COD_CLI)as act,sum(pedidos_carga1.peso * pedidos_carga1.CANT_PEDID) as total,(select zonas.descripcion from zonas where zonas.numero=pedidos_carga1.zona)as zonasDescripcion,(select alertas.descripcion from alertas where alertas.numero=pedidos_carga1.alerta)as alertasDescripcion,(select saldosclientesact.saldo from saldosclientesact where saldosclientesact.RAZON_SOC like 'pedidos_carga1.RAZON_SOC%' and saldosclientesact.COD_CLI like 'pedidos_carga1.COD_CLIENT%')as saldo,(select vendedores.nombre from vendedores where vendedores.numero=pedidos_carga1.COD_VENDED)as vendedor from pedidos_carga1 where NRO_PEDIDO like '%"+codigoPedido+"'and reparto=1";
                System.out.println(sql);
                
                PreparedStatement st=cp.prepareStatement(sql);
                //st.execute(sql);
                ResultSet rs=st.executeQuery();
                ChequearCantidadesPedidos chp=new Clientes();
                //synchronized rs;
                while(rs.next()){
                    PedidosParaReparto pedidos=new PedidosParaReparto();
                    Clientes clie=new Clientes();
                    pedidos.setiDPedido(rs.getInt("numero"));
                    pedidos.setRazonSocial(rs.getString("RAZON_SOC"));
                    pedidos.setCodigoTangoDePedido(rs.getString("NRO_PEDIDO"));
                    pedidos.setVehiculoAsignado(rs.getInt("vehiculo"));
                    //pedidos.setPesoTotal(rs.getDouble("total"));
                    pedidos.setCodigoArticulo(rs.getString("COD_ARTIC"));
                    pedidos.setDescripcionArticulo(rs.getString("DESC_ARTIC")+" "+rs.getString("DESC_ADIC"));
                    pedidos.setPesoItems(rs.getDouble("peso")* rs.getDouble("CANT_PEDID"));
                    pedidos.setPesoTotal(rs.getDouble("peso")* rs.getDouble("CANT_PEDID"));
                    pedidos.setCantidadArticulo(rs.getDouble("CANT_PEDID"));
                    pedidos.setCodigoCliente(rs.getString("COD_CLIENT"));
                    pedidos.setFechaEnvio(rs.getString("entrega"));
                    pedidos.setFechaActualizacionSaldoCliente(rs.getDate("act"));
                    pedidos.setNumeroDeListadoDeMateriales(rs.getInt("listado"));
                    pedidos.setNumeroDeRevisionDeListado(rs.getInt("revision"));
                    pedidos.setNumeroDeHojaDeRuta(rs.getInt("hdr1"));
                    pedidos.setNumeroDeProceso(rs.getInt("orden_num"));
                    pedidos.setNumeroDeFletero(rs.getInt("fletero"));
                    pedidos.setNumeroComprobante(rs.getString("N_REMITO"));
                    pedidos.setEmpresa(rs.getString("TALON_PEDI"));
                    pedidos.setVerificadorRevision(rs.getInt("revisionado"));
                    pedidos.setVehiculoAnterior(rs.getInt("vehiculoAnterior"));
                    pedidos.setIdPedidoEnTango(rs.getInt("ID_GVA03"));
                    //pedidos.setSaldoACobrar(rs.getDouble("saldo"));
                    clie.setCodigoCliente(pedidos.getCodigoCliente());
                    clie.setRazonSocial(pedidos.getRazonSocial());
                    clie.setEmpresa(pedidos.getEmpresa());
                    //ACA TENDRIA QUE HACER UNA INTERFAZ PAR QUE ME BUSQUE Y ACTUALICE LOS SALDOS
                    
                    //Iterator iSc=SiderconCapaatos.saldoCliente.listIterator();
                    Double sald=0.00;
                    //Clientes cli=new Clientes();
                    Actualizable actCli=new Clientes();
                    
                    //while(iSc.hasNext()){
                    //  cli=(Clientes)iSc.next();
                    String empresa=pedidos.getEmpresa();
                    int numeroConeccion=0;
                    if(empresa.equals("BU")){
                        numeroConeccion=1;
                    }else{
                        if(empresa.equals("SD")){
                            numeroConeccion=2;
                        }else{
                            numeroConeccion=3;
                        }
                    }
                    Connection sqlC=null;
                    switch (numeroConeccion){
                        case 1:
                            sqlC=(Connection)SiderconCapaatos.sqlBu;
                            break;
                        case 2:
                            sqlC=(Connection)SiderconCapaatos.sqlSd;
                            break;
                        case 3:
                            sqlC=(Connection)SiderconCapaatos.sqlSdSrl;
                            break;
                    }
                    if(SiderconCapaatos.falloConecion==0){
                        Clientes cliente=new Clientes();
                        //Actualizable actCli=new Clientes();
                        cliente.setCodigoCliente(pedidos.getCodigoCliente());
                        cliente.setEmpresa(empresa);
                        cliente=(Clientes) chp.actualizar(cliente);
                        sald=cliente.getSaldo();
                        //sald=(Double) actCli.actualizarDatosSaldos(sqlC, empresa, pedidos.getCodigoCliente());
                        //sald=cli.getSaldo();
                    }
                    
                    pedidos.setSaldoCliente(sald);
                    sald=0.00;
                    pedidos.setNumeroVendedor(rs.getInt("COD_VENDED"));
                    pedidos.setNombreVendedor(rs.getString("vendedor"));
                    System.err.println(" numero v"+pedidos.getNumeroVendedor()+" nombre v "+pedidos.getNombreVendedor()+" cliente "+pedidos.getRazonSocial()+" saldo "+pedidos.getSaldoCliente());
                    String pendiente=String.valueOf(rs.getDouble("CANT_FACT"));
                    
                    Double articulosPendientes=0.00;
                    if(pendiente==null){
                        
                    }else{
                        articulosPendientes=Double.parseDouble(pendiente);
                    }
                    //pedidos.setCantidadArticulosEntregados(articulosPendientes);
                    //articulosPendientes=pedidos.getCantidadArticulo()- pedidos.getCantidadArticulosEntregados();
                    pedidos.setCantidadArticuloPendiente(articulosPendientes);
                    pedidos.setZonaAsignada(rs.getInt("zona"));
                    pedidos.setAlertaAsignada(rs.getInt("alerta"));
                    if(pedidos.getZonaAsignada() <=1){
                        pedidos.setZonaDescripcion("SANTA FE");
                    }else{
                        pedidos.setZonaDescripcion(rs.getString("zonasDescripcion"));
                    }
                    if(pedidos.getAlertaAsignada() > 0){
                        pedidos.setAlertaDescripcion(rs.getString("alertasDescripcion"));
                    }
                    if(SiderconCapaatos.falloConecion==1){
                        
                    }else{
                        //ChequearCantidadesPedidos chCli=new Clientes();
                        //chCli.check(chCli.actualizar(clie));
                    }
                    listaPed.add(pedidos);
                }
                rs.close();
                //ActualizarDatosPedidos act=new ActualizarDatosPedidos();
                //act.setPedidos(listaPed);
                //act.start();
                //    cn.CerrarConneccion(cp);
                
            } catch (SQLException ex) {
                Logger.getLogger(PedidosParaReparto.class.getName()).log(Level.SEVERE, null, ex);
            }
            return listaPed;
    }

    @Override
    public ArrayList ListadoPedidosPorFecha(String fecha) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean EliminarItems(Integer id) {
        Coneccion cn=new Coneccion();
        Boolean verif=false;
                Connection cp=cn.getCn();
        String sql="delete from pedidos_carga1 where numero="+id;
        System.out.println(sql);
        Statement st;
            try {
                st = cp.createStatement();
                st.execute(sql);
                verif=true;
            } catch (SQLException ex) {
                Logger.getLogger(PedidosParaReparto.class.getName()).log(Level.SEVERE, null, ex);
            }
        return verif;
    }

    @Override
    public DefaultTableModel MostrarEnTabla(ArrayList listado) {
        miTablaModificacion mod=new miTablaModificacion();
        PedidosParaReparto pedid=new PedidosParaReparto();
        //Procesos pro=new Procesos();
        
        Iterator ii=listado.listIterator();
        //jTable1 = new javax.swing.JTable();

        //jTable1.setModel(mod);
        mod.addColumn("Nro Pedido");
        mod.addColumn("Cod Articulo");
        mod.addColumn("descrip Articulo");
        mod.addColumn("cant a enviar");
        mod.addColumn("cant s/fecha");
        mod.addColumn("fecha de entrega");
        mod.addColumn("eliminar Item");
        mod.addColumn("Vendedor");
        Object[] fila=new Object[8];
        while(ii.hasNext()){
        pedid=(PedidosParaReparto)ii.next();
        fila[0]=pedid.getCodigoTangoDePedido();
        fila[1]=pedid.getCodigoArticulo();
        fila[2]=pedid.getDescripcionArticulo();
        fila[3]=pedid.getCantidadArticulo();
        fila[4]=pedid.getCantidadArticuloPendiente();
        fila[5]=pedid.getFechaEnvio();
        fila[6]=true;
        fila[7]=pedid.getNombreVendedor();
        mod.addRow(fila);
        }
        return mod;
    }
  	
}
