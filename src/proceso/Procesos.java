/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proceso;

import actualizaciones.Checking;
import actualizaciones.ChequearCantidadesPedidos;
import interfaces.Actualizable;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import objetos.Articulos;
import objetos.Clientes;
import objetos.DetalleHdr;
import objetos.DetallePesosPedido;
import objetos.EncabezadoHdr;
import objetos.Fleteros;
import objetos.Listados;
import objetos.PedidosParaReparto;
import objetos.Vehiculos;
import seguimientos.Archivador;
import seguimientos.GuardarMovimientos;
import siderconcapadatos.SiderconCapaatos;

/**
 *
 * @author MAURO DI
 */
public class Procesos {
    static Connection cp;
    static ArrayList<PedidosParaReparto> listaPed=new ArrayList();
    private Coneccion cn=new Coneccion();
    static int ultimoNumeroDeListado=0;
    static int ultimaRevisionDeListado=0;
    static Connection cT=null;

    public Procesos() {
         
        cp=cn.getCn();
    }
    
    public Map cargarPesosDeArticulos() throws SQLException{
        Map<String,Double> articulos=new HashMap<String,Double>();
        
        // codigo de vista articulosv
        //select *,(select pesos.peso from pesos where pesos.codigo='articulosdesc.CodArticulo')as peso from articulosdesc
        
        //Integer pesoUnitario = 0;
        //Connection cp=cn.ObtenerConeccion();
        String sql="select CodArticulo,Descripcion,peso,Sinonimo from articulosv";
        Statement st=cp.createStatement();
        st.execute(sql);
        ResultSet rs=st.getResultSet();
        while(rs.next()){
            Articulos art=new Articulos();
            art.setCodigo(rs.getString(1));
            art.setDescripcionArticulo(rs.getString(2));
            art.setPesoUnitario(rs.getDouble(3));
            art.setSinonimoArticulo(rs.getString(4));
            //System.out.println(art.getCodigo());
            //pesoUnitario=String.valueOf(art.getPesoUnitario());
            String pesoUnitario=art.getCodigo().trim();
            //System.out.println(pesoUnitario.length()+" "+pesoUnitario+" peso "+art.getPesoUnitario()+" "+art.getDescripcionArticulo()+" sinonimo "+art.getSinonimoArticulo());
            //pesoUnitario++;
            articulos.put(pesoUnitario,art.getPesoUnitario());
        }
        rs.close();
        //cn.CerrarConneccion(cp);
        return articulos;
    }
    public Map cargarArticulos() throws SQLException{
        Map<String,Object> articulos=new HashMap<String,Object>();
        
        //Integer pesoUnitario = 0;
        //Connection cp=cn.ObtenerConeccion();
        String sql="select PESOS.codigo,(select ArticulosDesc.Descripcion from ArticulosDesc where CodArticulo=PESOS.codigo limit 0,1)as descripcion,PESOS.peso,(select ArticulosDesc.Sinonimo from ArticulosDesc where CodArticulo=PESOS.codigo limit 0,1)as sinonimo,(select datos.UMV from datos where datos.COD_ARTICULO=PESOS.codigo limit 0,1)as unidad from PESOS order by codigo";
        Statement st=cp.createStatement();
        st.execute(sql);
        ResultSet rs=st.getResultSet();
        while(rs.next()){
            Articulos art=new Articulos();
            art.setCodigo(rs.getString("codigo"));
            art.setDescripcionArticulo(rs.getString("descripcion"));
            art.setPesoUnitario(rs.getDouble("peso"));
            art.setSinonimoArticulo(rs.getString("sinonimo"));
            //System.out.println(art.getCodigo());
            //pesoUnitario=String.valueOf(art.getPesoUnitario());
            art.setUnidadDeMedida(rs.getString("unidad"));
            String pesoUnitario=art.getCodigo().trim();
            //System.out.println(pesoUnitario.length()+" "+pesoUnitario+" peso "+art.getPesoUnitario()+" "+art.getDescripcionArticulo()+" sinonimo "+art.getSinonimoArticulo());
            //pesoUnitario++;
            articulos.put(pesoUnitario,art);
        }
        rs.close();
        //cn.CerrarConneccion(cp);
        return articulos;
    }
    public Map DetallePedido(String numeroPedido) throws SQLException{
        Map<String,Double> detalle=new HashMap<String,Double>();
        //Connection cp=cn.ObtenerConeccion();
        String sql="select pedidos_carga1.COD_ARTIC,pedidos_carga1.NRO_PEDIDO,pedidos_carga1.CANT_PEDID from pedidos_carga1 where NRO_PEDIDO like '%"+numeroPedido+"%'";
        PreparedStatement st=cp.prepareStatement(sql);
        ResultSet rs=st.executeQuery();
        while(rs.next()){
            detalle.put(rs.getString("COD_ARTIC"),rs.getDouble("CANT_PEDID"));
        }
        rs.close();
        //cn.CerrarConneccion(cp);
        return detalle;
    
    }

    public ArrayList ListarPedidosPorFecha(String fechEnt) throws SQLException, ClassNotFoundException{
	/*
         * ACA SE GENERA UN ARRAY CON TODOS LOS OBJETOS PEDIDOS QUE CORRESPONDEN SER ENTREGADOS
         * EN LA FECHA PUNTUALIZADA. POR LO TANTO SE PUEDE VOLCAR A LA TABLA PARA SU SELECCION
         * 
         */
          //      Connection cp=cn.ObtenerConeccion();
		String sql="select *,(select TABLA1.actualizacion from TABLA1 where TABLA1.COD_CLI=pedidos_carga1.COD_CLIENT group by TABLA1.COD_CLI)as act,sum(pedidos_carga1.peso * pedidos_carga1.CANT_PEDID) as total,(select zonas.descripcion from zonas where zonas.numero=pedidos_carga1.zona)as zonasDescripcion,(select alertas.descripcion from alertas where alertas.numero=pedidos_carga1.alerta)as alertasDescripcion,(select saldosclientesact.saldo from saldosclientesact where saldosclientesact.RAZON_SOC like 'pedidos_carga1.RAZON_SOC%' and saldosclientesact.COD_CLI like 'pedidos_carga1.COD_CLIENT%')as saldo,(select vendedores.nombre from vendedores where vendedores.numero=pedidos_carga1.COD_VENDED)as vendedor from pedidos_carga1 where entrega like '"+fechEnt+"%'and reparto=1 group by NRO_PEDIDO order by RAZON_SOC";
		System.out.println(sql);
                
                PreparedStatement st=cp.prepareStatement(sql);
                //st.execute(sql);
		ResultSet rs=st.executeQuery();
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
                            sald=(Double) actCli.actualizarDatosSaldos(sqlC, empresa, pedidos.getCodigoCliente());
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
                        ChequearCantidadesPedidos chCli=new Clientes();
                        chCli.check(chCli.actualizar(clie));
                        }
                        listaPed.add(pedidos);
		}
                rs.close();
                //ActualizarDatosPedidos act=new ActualizarDatosPedidos();
                //act.setPedidos(listaPed);
                //act.start();
            //    cn.CerrarConneccion(cp);
		return listaPed;
		
	}	
	public ArrayList ListarPedidosPorVehiculo(Integer vehiculo){
		ArrayList<PedidosParaReparto> listaUnidad=new ArrayList();
		//listaUnidad=pedido;
		Iterator iterador=listaPed.iterator();
		while(iterador.hasNext()){
			PedidosParaReparto ped=(PedidosParaReparto)iterador.next();
			if(vehiculo==ped.getVehiculoAsignado()){
                            System.out.println("NOMBRE PEDIDO LISTADO "+ped.getRazonSocial());
				listaUnidad.add(ped);
			}
		}
		return listaUnidad;
	}
	public ArrayList ListarVehiculos() throws SQLException{
		ArrayList<Vehiculos> listVehiculos=new ArrayList();
		//Statement st=cp.createStatement();
         //       Connection cp=cn.ObtenerConeccion();
                String sql="select * from unidades order by numero";
		PreparedStatement st=cp.prepareStatement(sql);
		 ResultSet rs=st.executeQuery();
		 while(rs.next()){
			 Vehiculos uni=new Vehiculos();
			 uni.setNumero(rs.getInt("numero"));
                         uni.setDescripcion(rs.getString("descripcion"));
                        uni.setPatente(rs.getString("patente"));
                        uni.setKilometrosActuales(rs.getInt("kilometrosActuales"));
                         uni.setCapacidadDeCarga(rs.getDouble("carga_max"));
			 listVehiculos.add(uni);
		 }
                 rs.close();
           //      cn.CerrarConneccion(cp);
		 return listVehiculos;
	}
        public ArrayList cargarHdrVehiculo(ArrayList veh,String fecha) throws SQLException{
            String sql=null;
            Vehiculos uni=new Vehiculos();
            Iterator iv=veh.listIterator();
            Statement st=null;
            while(iv.hasNext()){
                uni=(Vehiculos)iv.next();
                sql="select hdr.numero from hdr where numeroVehiculo="+uni.getNumero()+" and fechaEntrega like '"+fecha+"%'";
                st=cp.createStatement();
                st.execute(sql);
                ResultSet rs=st.getResultSet();
                while(rs.next()){
                    uni.setNumeroHdr(rs.getInt(1));
                }
                rs.close();
            }
            st.close();
            return veh;
        }
        public ArrayList cargarSaldosDeClientes() throws SQLException{
            ArrayList saldos=new ArrayList();
            String cod;
            Double saldo=0.00;
            try {
                cT=ConeccionSqlTango.ObtenerConeccion(1);
            } catch (ClassNotFoundException ex) {
                //error            //error    
                GuardarMovimientos gArch=new Archivador();
                String cod1=String.valueOf(ex);
                gArch.registrarErrores(cod1, "", "");
                Logger.getLogger(Procesos.class.getName()).log(Level.SEVERE, null, ex);
            }           
            // Connection cp=cn.ObtenerConeccion();
            //String sql="select saldosclientesact.saldo,saldosclientesact.COD_CLI,saldosclientesact.RAZON_SOC,saldosclientesact.actualizacion from saldosclientesact order by RAZON_SOC";
            String sql="select GVA14.SALDO_CC,GVA14.COD_CLIENT,GVA14.RAZON_SOCI,GVA14.FECHA_MODI from GVA14 where SALDO_CC > 0 order by RAZON_SOCI";
            for(int a=1;a < 4;a++){
                try {
                    cT=ConeccionSqlTango.ObtenerConeccion(a);
                } catch (ClassNotFoundException ex) {
                    //error            //error    
                    GuardarMovimientos gArch=new Archivador();
                String cod1=String.valueOf(ex);
                gArch.registrarErrores(cod1, "", "");
                Logger.getLogger(Procesos.class.getName()).log(Level.SEVERE, null, ex);
                }
            PreparedStatement st=cT.prepareStatement(sql);
            ResultSet rs=st.executeQuery();
            while(rs.next()){
                Clientes cliente=new Clientes();
                cliente.setCodigoCliente(rs.getString("COD_CLIENT"));
                cliente.setRazonSocial(rs.getString("RAZON_SOCI"));
                cliente.setSaldo(rs.getDouble("SALDO_CC"));
                cliente.setFechaActualizacion(rs.getDate("FECHA_MODI"));
                cod=cliente.getCodigoCliente();
                saldo=cliente.getSaldo();
                System.out.println("cliente "+cliente.getRazonSocial()+" saldo :"+cliente.getSaldo());
                saldos.add(cliente);
            }
            rs.close();
            }
            ConeccionSqlTango.CerrarConeccion(cT);
            //cn.CerrarConneccion(cp);
            return saldos;
        }
        public void guardarAsignacionDeVehiculos(ArrayList pedidosTrabajados) throws SQLException, IOException{
            PedidosParaReparto pedidos=new PedidosParaReparto();
            
            String fechaMod=String.valueOf(ultimoNumeroDeListado);
            
            
            String ttx="-- guardarAsignacionDeVehiculos - function - Clase Procesos -\r\n";
           // Connection cp=cn.ObtenerConeccion();
            Iterator ii=pedidosTrabajados.listIterator();
            String sql="";
            Runtime r=Runtime.getRuntime();
            r.gc();
            Statement st=cp.createStatement();
            while(ii.hasNext()){
                pedidos=(PedidosParaReparto) ii.next();
                if(pedidos.getVehiculoAnterior()==0){
                sql="update pedidos_carga1 set vehiculo="+pedidos.getVehiculoAsignado()+",vehiculoAnterior="+pedidos.getVehiculoAsignado()+" where NRO_PEDIDO like '%"+pedidos.getCodigoTangoDePedido()+"%' and entrega like '"+pedidos.getFechaEnvio()+"%'";
                }else{
                    
                sql="update pedidos_carga1 set vehiculo="+pedidos.getVehiculoAsignado()+",vehiculoAnterior="+pedidos.getVehiculoAnterior()+" where NRO_PEDIDO like '%"+pedidos.getCodigoTangoDePedido()+"%' and entrega like '"+pedidos.getFechaEnvio()+"%'";                    
                }
                ttx+=sql+"\r\n";
                st.executeUpdate(sql);                
                
            }
            System.err.println(ttx);
            st.close();
            GuardarMovimientos gArch=new Archivador();
            gArch.registrarMovimiento(ttx, "bdMv.txt","130506");
          //  cn.CerrarConneccion(cp);
        }
        public ArrayList detallePedidoParaCorreccion(String numeroPedido,String fecha) throws SQLException{
            //System.out.println(numeroPedido+" fecha "+fecha);
          //  Connection cp=cn.ObtenerConeccion();
            ArrayList detalles=new ArrayList();
            String sql="select *,(SELECT vendedores.nombre FROM vendedores WHERE vendedores.numero = pedidos_carga1.COD_VENDED) AS vendedor from pedidos_carga1 where NRO_PEDIDO like '%"+numeroPedido+"%' and entrega like '"+fecha+"%' and reparto=1";
            PreparedStatement st=null;
            try{
            st=cp.prepareStatement(sql);
            }catch(Exception ex){
                System.err.println("SE CORTO LA CONECCION");
                cp=cn.getCn();
                st=cp.prepareStatement(sql);
            }
            ResultSet rs=st.executeQuery();
            while(rs.next()){
                PedidosParaReparto pedido=new PedidosParaReparto();
                pedido.setCodigoTangoDePedido(numeroPedido);
                pedido.setCodigoArticulo(rs.getString("COD_ARTIC"));
                pedido.setDescripcionArticulo(rs.getString("DESC_ARTIC"));
                pedido.setCantidadArticulo(rs.getDouble("CANT_PEDID"));
                pedido.setCantidadArticuloPendiente(rs.getDouble("CANT_FACT"));
                if(pedido.getCantidadArticuloPendiente()==null){
                    pedido.setCantidadArticuloPendiente(0.00);
                }
                pedido.setNumeroDeListadoDeMateriales(rs.getInt("listado"));
                pedido.setNumeroDeRevisionDeListado(rs.getInt("revision"));
                pedido.setNombreVendedor(rs.getString("vendedor"));
                pedido.setFechaEnvio(rs.getString("entrega"));
                pedido.setiDPedido(rs.getInt("numero"));
                pedido.setEmpresa(rs.getString("TALON_PEDI"));
                pedido.setVehiculoAsignado(rs.getInt("vehiculo"));
                pedido.setVehiculoAnterior(rs.getInt("vehiculoAnterior"));
                pedido.setPesoTotal(rs.getDouble("peso"));
                pedido.setIdPedidoEnTango(rs.getInt("ID_GVA03"));
                System.out.println("pedido :"+numeroPedido+" fecha"+fecha+" articulo: "+pedido.getDescripcionArticulo());
                detalles.add(pedido);
            }
            rs.close();
          //  cn.CerrarConneccion(cp);
            return detalles;
        }
        public void guardarDetallePedido(ArrayList detalles) throws SQLException{
          //  Connection cp=cn.ObtenerConeccion();
            PedidosParaReparto pedidos=new PedidosParaReparto();
            Iterator ui=detalles.listIterator();
            while(ui.hasNext()){
                pedidos=(PedidosParaReparto) ui.next();
                
                String sql="update pedidos_carga1 set CANT_PEDID="+pedidos.getCantidadArticulo()+",CANT_FACT="+pedidos.getCantidadArticuloPendiente()+",entrega ='"+pedidos.getFechaEnvio()+"',CANT_DESC="+pedidos.getCantidadArticulosTotales()+" where numero="+pedidos.getiDPedido();
                System.out.println(sql);
                Statement st=cp.createStatement();
                st.executeUpdate(sql);
                st.close();
                
            }
         //   cn.CerrarConneccion(cp);
}
	public ArrayList listadoDetalladoPorVehiculo(int numeroVehiculo,String fecha) throws SQLException{
                ArrayList listadoDetallado=new ArrayList();
         //      Connection cp=cn.ObtenerConeccion();
                String sql="select * from pedidos_carga1 where vehiculo="+numeroVehiculo+" and entrega like '"+fecha+"%'";
                PreparedStatement st=cp.prepareStatement(sql);
            ResultSet rs=st.executeQuery();
            while(rs.next()){
              // PedidosParaReparto ped1 = null;
 			PedidosParaReparto pedidos=new PedidosParaReparto();
			pedidos.setiDPedido(rs.getInt("numero"));
			pedidos.setRazonSocial(rs.getString("RAZON_SOC"));
			pedidos.setCodigoTangoDePedido(rs.getString("NRO_PEDIDO"));
			pedidos.setVehiculoAsignado(rs.getInt("vehiculo"));
			pedidos.setPesoTotal(rs.getDouble("peso")* rs.getDouble("CANT_PEDID"));
                        pedidos.setCodigoArticulo(rs.getString("COD_ARTIC"));
                        pedidos.setDescripcionArticulo(rs.getString("DESC_ARTIC")+" "+rs.getString("DESC_ADIC"));
                        pedidos.setPesoItems(rs.getDouble("peso")* rs.getDouble("CANT_PEDID"));
                        pedidos.setCantidadArticulo(rs.getDouble("CANT_PEDID"));
			pedidos.setCodigoCliente(rs.getString("COD_CLIENT"));
                        pedidos.setFechaEnvio(rs.getString("entrega"));
                        String pendiente=String.valueOf(rs.getDouble("CANT_FACT"));
                        pedidos.setNumeroDeListadoDeMateriales(rs.getInt("listado"));
                        pedidos.setNumeroDeRevisionDeListado(rs.getInt("revision"));
                        pedidos.setIdPedidoEnTango(rs.getInt("ID_GVA03"));
                        pedidos.setEmpresa(rs.getString("TALON_PEDI"));
                        Double articulosPendientes=0.00;
                        if(pendiente==null){
                            articulosPendientes=0.00;
                        }else{
                            articulosPendientes=Double.parseDouble(pendiente);
                        }
                        //pedidos.setCantidadArticuloPendiente(articulosPendientes);
                        //articulosPendientes=pedidos.getCantidadArticulo()- pedidos.getCantidadArticulosEntregados();
                        pedidos.setCantidadArticuloPendiente(articulosPendientes);
	      		listadoDetallado.add(pedidos); 
            }
         //   cn.CerrarConneccion(cp);
	    return listadoDetallado;
            
        }
        public ArrayList ListadoDeArticulos() throws SQLException{
        //    Connection cp=cn.ObtenerConeccion();
            ArrayList articulos = new ArrayList();
            String sql="select PESOS.codigo,(select ArticulosDesc.Descripcion from ArticulosDesc where CodArticulo=PESOS.codigo limit 0,1),PESOS.peso,(select ArticulosDesc.Sinonimo from ArticulosDesc where CodArticulo=PESOS.codigo limit 0,1),(select datos.UMV from datos where datos.COD_ARTICULO=PESOS.codigo limit 0,1)as unidad from PESOS order by codigo";
            int cantidad=0;
        Statement st=cp.createStatement();
        st.execute(sql);
        ResultSet rs=st.getResultSet();
        while(rs.next()){
            Articulos art = new Articulos();
            art.setCodigo(rs.getString(1));
            art.setDescripcionArticulo(rs.getString(2));
            art.setPesoUnitario(rs.getDouble(3));
            art.setSinonimoArticulo(rs.getString(4));
            art.setUnidadDeMedida(rs.getString("unidad"));
            //System.out.println(art.getCodigo());
            //pesoUnitario=String.valueOf(art.getPesoUnitario());
            String pesoUnitario=art.getCodigo();
            cantidad++;
            System.out.println(cantidad+" "+pesoUnitario+" peso "+art.getPesoUnitario());
            //pesoUnitario++;
            articulos.add(art);
        }
        rs.close();
       // cn.CerrarConneccion(cp);
        return articulos;
        }
        public ArrayList ListarFleteros() throws SQLException{
            //Fleteros conductor=new Fleteros();
       //     Connection cp=cn.ObtenerConeccion();
            ArrayList lista=new ArrayList();
            String sql="select * from fleteros order by numero";
            Statement st=cp.createStatement();
            st.execute(sql);
            ResultSet rs=st.getResultSet();
            while(rs.next()){
                Fleteros conductor=new Fleteros();
                conductor.setNumeroFletero(rs.getInt("numero"));
                conductor.setNombreFletero(rs.getString("nombre"));
                conductor.setCelularFletero(rs.getString("celular"));
                lista.add(conductor);
            }
            rs.close();
       //     cn.CerrarConneccion(cp);
            return lista;
        }
        public void GuardarModificacionesVehiculos(ArrayList unidades) throws SQLException{
            Vehiculos uni=new Vehiculos();
            String sql=null;
       //     Connection cp=cn.ObtenerConeccion();
            //System.out.println(sql);
            //Statement st=cp.createStatement();
            Iterator iu=unidades.listIterator();
            while(iu.hasNext()){
                uni=(Vehiculos)iu.next();
                String patente=uni.getPatente();
                Double cargar=uni.getCapacidadDeCarga();
                Integer kilometros=uni.getKilometrosActuales();
                if((patente==null) ||("".equals(patente))) {
                    patente="N/D";
                }
                System.out.println("patente "+patente);
                if(cargar!=null) {
                    cargar=0.00;
                }
                if(kilometros==null) {
                    kilometros=0;
                }
                System.out.println("kilometros "+kilometros);
                sql="update unidades set patente ='"+patente+"', kilometrosActuales="+kilometros+" where numero="+uni.getNumero();
                System.out.println(sql);
                Statement st=cp.createStatement();
                st.executeUpdate(sql);
                System.out.println(sql);
                st.close();
            }
       //     cn.CerrarConneccion(cp);
            
        }
        public void GuardarDatosFleteros(ArrayList listFleteros) throws SQLException{
                Fleteros chofer=new Fleteros();
        //        Connection cp=cn.ObtenerConeccion();
                Iterator ic=listFleteros.listIterator();
                while(ic.hasNext()){
                    chofer=(Fleteros)ic.next();
                    Integer numero=chofer.getNumeroFletero();
                    String nombre=chofer.getNombreFletero();
                    String celular=chofer.getCelularFletero();
                    Integer condicion=chofer.getCondicion();
                    if(condicion==null) {
                        condicion=0;
                    }
                    System.out.println(condicion);
                    String sql=null;
                    switch(condicion){
                        case 2:
                             sql="delete from fleteros where numero="+numero;   
                            break;
                        case 3:
                            sql="insert into fleteros (nombre,celular,numero) values ('"+nombre+"','"+celular+"',"+numero+")";
                            break;
                        default:
                            sql="update fleteros set nombre ='"+nombre+"',celular='"+celular+"' where numero="+numero;
                            break;
                    }
                    Statement st=cp.createStatement();
                    st.executeUpdate(sql);
                    st.close();
          //          cn.CerrarConneccion(cp);
                    System.out.println(sql);
                }
        }
        public void NuevoDatoVehiculos(String descripcion,String patente,Double capacidad,Integer kilometros) throws SQLException{
          //  Connection cp=cn.ObtenerConeccion();
            String sql="insert into unidades (descripcion,patente,carga_max,kilometrosActuales) values('"+descripcion+"','"+patente+"','"+capacidad+"','"+kilometros+"')";
            Statement st=cp.createStatement();
            st.executeUpdate(sql);
            st.close();
          //  cn.CerrarConneccion(cp);
        }
        public void EliminarVehiculo(int numero) throws SQLException{
          //  Connection cp=cn.ObtenerConeccion();
            String sql="delete from unidades where numero="+numero;
            Statement st=cp.createStatement();
            st.executeUpdate(sql);
            st.close();
         //   cn.CerrarConneccion(cp);
        }
        public void ModificacionDeArticulos(ArrayList artic) throws SQLException{
            Iterator ita=artic.listIterator();
        //    Connection cp=cn.ObtenerConeccion();
            String sql=null;
            Articulos ar=new Articulos();
            int flagP=0;
            while(ita.hasNext()){
                ar=(Articulos)ita.next();
                switch(ar.getEstado()){
                    case 2:
                        sql="delete from PESOS where codigo='"+ar.getCodigo()+"'";
                        Statement st=cp.createStatement();
                        st.executeUpdate(sql);
                        //st.close();
                        sql="delete from ArticulosDesc where CodArticulo='"+ar.getCodigo()+"'";
                        //Statement st=cp.createStatement();
                        st.executeUpdate(sql);
                        st.close();
                        break;
                    case 3:
                        sql="insert into PESOS (codigo,peso) values('"+ar.getCodigo()+"',"+ar.getPesoUnitario()+")";
                        Statement sh;
                        try{
                            sh=cp.createStatement();
                            sh.executeUpdate(sql);
                        }catch(com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException exxxx){
                            System.err.println(" error en pesos "+exxxx);
                            sql="update PESOS set peso="+ar.getPesoUnitario()+" where codigo='"+ar.getCodigo()+"'";
                            sh=cp.createStatement();
                            sh.executeUpdate(sql);
                            flagP=1;
                            System.out.println("FLAG ESTA EN : "+flagP);
                        }
                        if(flagP==0){
                        sql="insert into ArticulosDesc (CodArticulo,Descripcion,Sinonimo) values ('"+ar.getCodigo()+"','"+ar.getDescripcionArticulo()+"','"+ar.getSinonimoArticulo()+"')";
                        }else{
                        sql="update ArticulosDesc set Descripcion='"+ar.getDescripcionArticulo()+"',Sinonimo='"+ar.getSinonimoArticulo()+"' where CodArticulo ='"+ar.getCodigo()+"'";    
                        
                        }
                        sh.executeUpdate(sql);
                        if(flagP==0){
                            sql="insert into datos (COD_ARTICULO,DESCRIPCION,SINONIMO,UMS,UMV) values ('"+ar.getCodigo()+"','"+ar.getDescripcionArticulo().trim()+"','"+ar.getSinonimoArticulo().trim()+"','KGS','"+ar.getUnidadDeMedida()+"')";
            
                        }else{
                            sql="update datos set DESCRIPCION='"+ar.getDescripcionArticulo().trim()+"',SINONIMO='"+ar.getSinonimoArticulo().trim()+"',UMS='KGS',UMV='"+ar.getUnidadDeMedida()+"' where COD_ARTICULO='"+ar.getCodigo()+"'";
                            flagP=0;
                        }
                        sh.executeUpdate(sql);
                        sh.close();
                        break;
                    default:
                        sql="update PESOS set peso="+ar.getPesoUnitario()+" where PESOS.codigo='"+ar.getCodigo()+"'";
                        Statement sp=cp.createStatement();
                        int hech=sp.executeUpdate(sql);
                        sql="update ArticulosDesc set Descripcion='"+ar.getDescripcionArticulo()+"',Sinonimo='"+ar.getSinonimoArticulo()+"' where CodArticulo='"+ar.getCodigo()+"'";
                        sp=cp.createStatement();
                        hech=sp.executeUpdate(sql);

                        System.out.println("cantidad devuelta por hech "+hech);
                        /*
                        if(hech==1){
                            
                        }else{
                          sql="insert into PESOS (codigo,peso) values('"+ar.getCodigo()+"',"+ar.getPesoUnitario()+")";
                        
                        hech=sp.executeUpdate(sql);  
                        }
                        */
                        
                        //st.close();
                        sql="update ArticulosDesc set Descripcion='"+ar.getDescripcionArticulo()+"',Sinonimo='"+ar.getSinonimoArticulo()+"' where CodArticulo='"+ar.getCodigo()+"'";
                        //Statement st=cp.createStatement();
                        sp.executeUpdate(sql);
                        sql="update datos set UMV='"+ar.getUnidadDeMedida()+"' where COD_ARTICULO='"+ar.getCodigo()+"'";
                        System.out.println(sql);
                        sp.executeUpdate(sql);
                        sp.close();
                        break;
                }
            }
         //   cn.CerrarConneccion(cp);
        }
        public void GuardarNuevoArticulo(Articulos ar,String medida){
        try {
            //   Connection cp=cn.ObtenerConeccion();
            String sql="insert into pesos (codigo,peso) values('"+ar.getCodigo()+"',"+ar.getPesoUnitario()+")";
            System.out.println(sql);
            Statement sh=cp.createStatement();
            int flagP=0;
            try{
                   
            sh.executeUpdate(sql);
            }catch(com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException exxxx){
                            System.err.println(" error en pesos "+exxxx);
                            sql="update PESOS set peso="+ar.getPesoUnitario()+" where codigo='"+ar.getCodigo()+"'";
                            //sh=cp.createStatement();
                            sh.executeUpdate(sql);
                            System.out.println(sql);
                            flagP=0;
            }
            System.out.println(" tamaño de la descripcion :"+ar.getDescripcionArticulo().length()+" FLAGP "+flagP);
            
            if(flagP==0){
                        sql="insert into articulosDesc (CodArticulo,Descripcion,Sinonimo) values ('"+ar.getCodigo()+"','"+ar.getDescripcionArticulo()+"','"+ar.getSinonimoArticulo()+"')";
                        }else{
                        sql="update articulosDesc set Descripcion='"+ar.getDescripcionArticulo()+"',Sinonimo='"+ar.getSinonimoArticulo()+"' where CodArticulo ='"+ar.getCodigo().trim()+"'";    
                        System.out.println("Ingreso: "+sql);
                        }
            
                        sh.executeUpdate(sql);
                        if(flagP==0){
                            sql="insert into datos (COD_ARTICULO,DESCRIPCION,SINONIMO,UMS,UMV) values ('"+ar.getCodigo()+"','"+ar.getDescripcionArticulo().trim()+"','"+ar.getSinonimoArticulo().trim()+"','KGS','"+medida+"')";
            
                        }else{
                            sql="update datos set DESCRIPCION='"+ar.getDescripcionArticulo().trim()+"',SINONIMO='"+ar.getSinonimoArticulo().trim()+"',UMS='KGS',UMV='"+medida+"' where COD_ARTICULO='"+ar.getCodigo()+"'";
                            flagP=0;
                        }
            /*
            if(flagP==0){
            sql="insert into ArticulosDesc (CodArticulo,Descripcion,Sinonimo) values ('"+ar.getCodigo()+"','"+ar.getDescripcionArticulo().trim()+"','"+ar.getSinonimoArticulo().trim()+"')";
            }else{
                
            }
            System.out.println(sql);
            sh.executeUpdate(sql);
            if(flagP==0){
            sql="insert into datos (COD_ARTICULO,DESCRIPCION,SINONIMO,UMS,UMV) values ('"+ar.getCodigo()+"','"+ar.getDescripcionArticulo().trim()+"','"+ar.getSinonimoArticulo().trim()+"','KGS','"+medida+"')";
            }else{
                
            }
                        */
            sh.executeUpdate(sql);
            
            System.out.println(sql);
            
            sh.close();
            //       cn.CerrarConneccion(cp);
        } catch (SQLException ex) {
            Logger.getLogger(Procesos.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        public Double pesoTotalPedido(String numeroPedido,String fech) throws SQLException{
        //    Connection cp=cn.ObtenerConeccion();
            DetallePesosPedido detalle=new DetallePesosPedido();
            Double pesoTotal=0.00;
            Double pesoUnitario=0.00;
            Double cantidad=0.00;
            String sql="select pedidos_carga1.COD_ARTIC,pedidos_carga1.CANT_PEDID,(select PESOS.peso from PESOS where codigo = pedidos_carga1.COD_ARTIC),pedidos_carga1.entrega,pedidos_carga1.NRO_PEDIDO from pedidos_carga1 where NRO_PEDIDO ='"+numeroPedido+"' and entrega like '"+fech+"%'";
            Statement st=cp.createStatement();
            st.execute(sql);
            ResultSet rs=st.getResultSet();
            while(rs.next()){
                cantidad=rs.getDouble(2);
                pesoUnitario=rs.getDouble(3);
                pesoTotal+=pesoUnitario * cantidad;
            }
            rs.close();
            st.close();
      //      cn.CerrarConneccion(cp);
            return pesoTotal;
            
        }
        public ArrayList listadoDeMaterialesPendientes() throws SQLException{
        //    Connection cp=cn.ObtenerConeccion();
            ArrayList listado=new ArrayList();
            String sql="select pedidos_carga1.NRO_PEDIDO,pedidos_carga1.RAZON_SOC,pedidos_carga1.COD_CLIENT,pedidos_carga1.COD_ARTIC,pedidos_carga1.DESC_ARTIC,sum(pedidos_carga1.CANT_FACT),pedidos_carga1.CANT_DESC,pedidos_carga1.CANT_PEDID,pedidos_carga1.numero,pedidos_carga1.TALON_PEDI,pedidos_carga1.FEC_PEDIDO from pedidos_carga1 where CANT_FACT > 0 group by COD_ARTIC,RAZON_SOC order by RAZON_SOC";
            Statement st=cp.createStatement();
            st.execute(sql);
            ResultSet rs=st.getResultSet();
            ChequearCantidadesPedidos ch=new Checking();
            while(rs.next()){
                PedidosParaReparto ped=new PedidosParaReparto();
                PedidosParaReparto pedi=new PedidosParaReparto();
                ped.setCodigoTangoDePedido(rs.getString(1));
                ped.setRazonSocial(rs.getString(2));
                ped.setCodigoCliente(rs.getString(3));
                ped.setCodigoArticulo(rs.getString(4));
                ped.setDescripcionArticulo(rs.getString(5));
                Double pendiente=rs.getDouble(6);
                Double totales=rs.getDouble(7);
                Double art=rs.getDouble(8);
                art=totales - pendiente;
                ped.setCantidadArticuloPendiente(pendiente);
                ped.setCantidadArticulosTotales(totales);
                ped.setCantidadArticulo(art);
                ped.setiDPedido(rs.getInt(9));
                ped.setEmpresa(rs.getString("TALON_PEDI"));
                ped.setFechaPedidosTango(rs.getString("FEC_PEDIDO"));
                //pedi=(PedidosParaReparto) ch.check(ped);
                //if(ped.getCantidadArticulo()> 0){
                listado.add(ped);
                //}
                System.out.println("pendientes "+pendiente+"/"+totales+"/"+art+" tamaño matriz "+listado.size());
                
            }
            rs.close();
            st.close();
       //     cn.CerrarConneccion(cp);
            return listado;
        }
        public void GuardarModificacionesPendientes(ArrayList pendientes) throws SQLException{
         //   Connection cp=cn.ObtenerConeccion();
            PedidosParaReparto pedidos=new PedidosParaReparto();
            Iterator ipp=pendientes.listIterator();
            String sql=null;
            Statement st=null;
            System.err.println(pendientes.size());
            while(ipp.hasNext()){
                pedidos=(PedidosParaReparto)ipp.next();
                
                switch(pedidos.getEstado()){
                    case 1:
                        st=cp.createStatement();
                        sql="insert into pedidos_carga1 (NRO_PEDIDO,CANT_PEDID,entrega,CANT_DESC,COD_CLIENT,RAZON_SOC,COD_ARTIC,DESC_ARTIC,vehiculo,peso,reparto,numeroOriginal,TALON_PEDI) values ('"+pedidos.getCodigoTangoDePedido()+"',"+pedidos.getCantidadArticulo()+",'"+pedidos.getFechaEnvio()+"',"+pedidos.getCantidadArticulosTotales()+",'"+pedidos.getCodigoCliente()+"','"+pedidos.getRazonSocial().trim()+"','"+pedidos.getCodigoArticulo()+"','"+pedidos.getDescripcionArticulo()+"',0,0.00,1,"+pedidos.getiDPedido()+",'"+pedidos.getEmpresa()+"')";
                        //sql="update pedidos_carga1 set NRO_PEDIDO='"+pedidos.getCodigoTangoDePedido()+"',CANT_PEDID="+pedidos.getCantidadArticulo()+",CANT_FACT="+pedidos.getCantidadArticuloPendiente()+",entrega='"+pedidos.getFechaEnvio()+"' where numero="+pedidos.getiDPedido();
                        System.out.println(sql);
                        
                        st.executeUpdate(sql);
                       
                         sql="update pedidos_carga1 set CANT_FACT="+pedidos.getCantidadArticuloPendiente()+" where numero="+pedidos.getiDPedido();
                        System.err.println("esta haciendo update "+sql);
                        
                        st.executeUpdate(sql);
                        st.close();

                        break;
                    case 2:
                        st=cp.createStatement();
                        sql="insert into pedidos_carga1 (NRO_PEDIDO,CANT_PEDID,entrega,CANT_DESC,COD_CLIENT,RAZON_SOC,COD_ARTIC,DESC_ARTIC,vehiculo,peso,reparto,motivo_anulacion,TALON_PEDI) values ('"+pedidos.getCodigoTangoDePedido()+"',0,'00/00/0000',"+pedidos.getCantidadArticulosTotales()+",'"+pedidos.getCodigoCliente()+"','"+pedidos.getRazonSocial().trim()+"','"+pedidos.getCodigoArticulo()+"','"+pedidos.getDescripcionArticulo()+"',0,0.00,1,'eliminado por sistema hdr','"+pedidos.getEmpresa()+"')";
                        //sql="update pedidos_carga1 set NRO_PEDIDO='"+pedidos.getCodigoTangoDePedido()+"',CANT_PEDID="+pedidos.getCantidadArticulo()+",CANT_FACT="+pedidos.getCantidadArticuloPendiente()+",entrega='"+pedidos.getFechaEnvio()+"' where numero="+pedidos.getiDPedido();
                        System.out.println(sql);
                        
                        st.executeUpdate(sql);
                         sql="update pedidos_carga1 set CANT_FACT=0 where numero="+pedidos.getiDPedido();
                         st.executeUpdate(sql);
                        st.close();
                        break;
                    default:
                        break;
                }
              }
         //   cn.CerrarConneccion(cp);
        }
        public ArrayList DetalleDeEntregasMaterialesPendientes(String pedido) throws SQLException{
            ArrayList detalle=new ArrayList();
        //    Connection cp=cn.ObtenerConeccion();
            String sql="select * from pedidos_carga1 where NRO_PEDIDO like '%"+pedido+"' and CANT_DESC > 0 order by RAZON_SOC";
            Statement st=cp.createStatement();
            st.execute(sql);
            ResultSet rs=st.getResultSet();
            ChequearCantidadesPedidos ch=new Checking();
            while(rs.next()){
                PedidosParaReparto pedi=new PedidosParaReparto();
                pedi.setRazonSocial(rs.getString("RAZON_SOC"));
                pedi.setCantidadArticulo(rs.getDouble("CANT_PEDID"));
                pedi.setFechaEnvio(rs.getString("entrega"));
                pedi.setCodigoArticulo(rs.getString("COD_ARTIC"));
                pedi.setDescripcionArticulo(rs.getString("DESC_ARTIC"));
                pedi.setVehiculoAsignado(rs.getInt("vehiculo"));
                pedi=(PedidosParaReparto)ch.check(pedi);
                if(pedi.getCantidadArticuloPendiente()> 0){
                    detalle.add(pedi);
                }
            }
            rs.close();
            st.close();
            
            return detalle;
        }
        public Listados GenerarNuevoListado(int vehiculo,String fecha,Boolean listadoCh,Boolean listadoCH1) throws SQLException{
            System.out.println(vehiculo+" "+fecha);
            Listados list=new Listados();
            Boolean chq=ChequearListado(vehiculo,fecha);
            System.out.println(vehiculo+" "+fecha+" "+chq);
            Statement st=cp.createStatement();
            String sql=null;
            ResultSet rs=null;
            if(chq){
                //ultimaRevisionDeListado;
                
                list.setNumeroListado(ultimoNumeroDeListado);
               
                // listadoCH1 debe ser true para incrementar numero de revision
                
                if(listadoCH1){
                ultimaRevisionDeListado++;
                }
                list.setNumeroRevision(ultimaRevisionDeListado);
                /*
                list.setNumeroListado(ultimoNumeroDeListado);
                //Listados.nuevaRevision();
                list.setNumeroListado(Listados.nuevaRevision());
                */
                System.out.println("NUMERO DE LISTADO Y REVISION ESTATICO :"+list.getNumeroListado()+" / "+list.getNumeroRevision());
                //return list;
            }else{
            sql="insert into listadosDeMateriales (fechaEntrega,vehiculo) values ('"+fecha+"',"+vehiculo+")";
            //st=cp.createStatement();
            st.executeUpdate(sql);
            
            //st.close();
            sql="select LAST_INSERT_ID()";
            //Statement ss=cp.createStatement();
            st.execute(sql);
            rs=st.getResultSet();
        
            while(rs.next()){
                ultimoNumeroDeListado=rs.getInt(1);
                System.err.println(" LA FUNCION LAST_INSERT_ID() DA :"+ultimoNumeroDeListado);
            }
            list.setNumeroListado(ultimoNumeroDeListado);
            rs.close();
            //list.setNumeroRevision(0);
            }

            // listadoCH al ser true guarda los datos en las bases sobre los distintos listados y revisiones
            
            if(listadoCh){
            sql="insert into historicolistadorevision (numeroListado,numeroRevision) values ("+ultimoNumeroDeListado+","+ultimaRevisionDeListado+")";
            st.executeUpdate(sql);
            
            sql="update listadosDeMateriales set revision="+ultimaRevisionDeListado+" where fechaEntrega like '"+fecha+"%' and vehiculo="+vehiculo;
            System.out.println("SQL = "+sql);
            st.executeUpdate(sql);            
            st.close();
            //Listados.nuevoListado();
            System.err.println(sql+" "+chq);
            }
            return list;
            
        }
        private Boolean ChequearListado(int vehiculo,String fecha) throws SQLException{
            String sql="select * from listadosDeMateriales where fechaEntrega like '"+fecha+"%' and vehiculo="+vehiculo;
            Boolean ccch = false;
            try{
            Statement st=cp.createStatement();
            st.execute(sql);
            ResultSet rs=st.getResultSet();
 
             //ccch= false;   
            /*
             * ACA LEE EL LA BASE CUAL ES EL ULTIMO NUMERO DE LISTADO Y REVISION EMITIDO
             */
            while(rs.next()){
                ultimoNumeroDeListado=rs.getInt(1);
                ultimaRevisionDeListado=rs.getInt("revision");
                
                ccch=true;
            
            }
            rs.close();
            st.close();
            return ccch;
            }catch(Exception ex){
                GuardarMovimientos gArch=new Archivador();
                String cod1=String.valueOf(ex);
                gArch.registrarErrores(cod1, "", "");
                System.err.println("ERROR DE CONEXION PARA SACAR NUMERO DE LISTADO");
                return ccch;
            }
        }
        public void GuardarNumeroListadoEnPedido(ArrayList pedidos,Integer seleccion,String fecha) throws SQLException, IOException{
            Iterator iip=pedidos.listIterator();
            String sql=null;
            PedidosParaReparto pe=new PedidosParaReparto();
            PedidosParaReparto pedi=new PedidosParaReparto();
            ChequearCantidadesPedidos ch=new Checking();
            //FileWriter revisiones=null;
            String fch=fecha.replaceAll("/","_");
            //String ruta="C://bases//STHDR//revHdr//"+fch+"testRevision.txt";
            //revisiones=new FileWriter(ruta);
            String txx="";
             //Runtime r=Runtime.getRuntime();
        //r.gc();
            
            while(iip.hasNext()){
                pe=(PedidosParaReparto)iip.next();
                int revision=pe.getNumeroDeRevisionDeListado();
                pe=(PedidosParaReparto)ch.check(pe);
                //revision++;
                //sql="update pedidos_carga1 set revision="+revision+",listado="+pe.getNumeroDeListadoDeMateriales()+" where NRO_PEDIDO ='"+pe.getCodigoTangoDePedido()+"' and vehiculo="+seleccion+" and entrega like '"+fecha+"%'";
                txx+=revision+" - "+pe.getCodigoTangoDePedido()+"\r\n";
                
            
                sql="update pedidos_carga1 set revision="+revision+",revisionado=1,listado="+pe.getNumeroDeListadoDeMateriales()+",CANT_PEDID="+pe.getCantidadArticulo()+",vehiculoAnterior="+pe.getVehiculoAsignado()+",cantOriginal="+pe.getCantidadArticulo()+" where numero ="+pe.getiDPedido(); 
                Statement st=cp.createStatement();
                st.executeUpdate(sql);
                
                //sql="insert into historicoListadoRevision (numeroListado,numeroRevision) values ("+pe.getNumeroDeListadoDeMateriales()+","+pe.getNumeroDeRevisionDeListado()+")";
                //st.executeUpdate(sql);
                st.close();
               
                
                
                System.err.println(sql+" NUMERO ID "+pe.getiDPedido()+" articulo "+pe.getCodigoArticulo()+"cantidad "+pe.getCantidadArticulo());
            }
            //revisiones.write(txx);
            //revisiones.close();
            
        }
        public ArrayList UltimaHdr() throws SQLException{
            ArrayList listadoEnc=new ArrayList();
            EncabezadoHdr enc=new EncabezadoHdr();
            String sql="select hdr.numero,hdr.kmInicio,hdr.kmFinal,hdr.numeroFletero,hdr.numeroVehiculo,hdr.pesoCarga,hdr.listadoNumero,hdr.fechaEntrega,hdr.horaInicio,hdr.horaFinal,(select encabezado_otpc.peso_total from encabezado_otpc where encabezado_otpc.numero=hdr.listadoNumero),(select unidades.descripcion from unidades where unidades.numero=hdr.numeroVehiculo),(select fleteros.nombre from fleteros where fleteros.numero=hdr.numeroFletero),hdr.fechaImpresion from hdr order by numero";
            Statement st=cp.createStatement();
            st.execute(sql);
            ResultSet rs=st.getResultSet();
            while(rs.next()){
                enc=new EncabezadoHdr();
                enc.setNumero(rs.getInt(1));
                enc.setKmIniciales(rs.getInt(2));
                enc.setKmFinales(rs.getInt(3));
                enc.setNumeroOperador(rs.getInt(4));
                enc.setNumeroVehiculo(rs.getInt(5));
                enc.setPesoEntregado(rs.getDouble(6));
                enc.setNumeroListado(rs.getInt(7));
                enc.setFechaReparto(rs.getString(8));
                String horaS=rs.getString(9);
                String horaF=rs.getString(10);
                enc.setPesoListado(rs.getDouble(11));
                enc.setDescripcionVehiculo(rs.getString(12));
                enc.setNombreOperador(rs.getString(13));
                enc.setFechaImpresion(rs.getString(14));
                System.err.println(" horas "+horaS+" "+horaF+" fecha "+rs.getString(14));
                Integer hrS=Integer.parseInt(horaS.substring(0,2));
                Integer minS=Integer.parseInt(horaS.substring(3,4));
                Integer hrF=Integer.parseInt(horaF.substring(0, 2));
                Integer minF=Integer.parseInt(horaF.substring(3,4));
                enc.setHoraSalida(hrS);
                enc.setMinutosSalida(minS);
                enc.setHoraLlegada(hrF);
                enc.setMinutosLlegada(minF);
                listadoEnc.add(enc);
            }
            rs.close();
            st.close();
            return listadoEnc;
        }
        public ArrayList cargarDetalleHdr(Integer numeroHdr) throws SQLException{
            ArrayList detalle=new ArrayList();
            String sql="select detalle_hdr.cliente,detalle_hdr.numero_cli,detalle_hdr.comprobante,detalle_hdr.importe,detalle_hdr.entregado,detalle_hdr.motivoFallido,detalle_hdr.numero,detalle_hdr.reenviar,(select pedidos_carga1.NRO_PEDIDO from pedidos_carga1 where pedidos_carga1.hdr1=detalle_hdr.hdr and pedidos_carga1.COD_CLIENT=detalle_hdr.numero_cli group by pedidos_carga1.NRO_PEDIDO limit 0,1) from detalle_hdr where hdr="+numeroHdr;
            System.out.println(sql);
            Boolean entreg=true;
            Statement st=cp.createStatement();
            st.execute(sql);
            ResultSet rs=st.getResultSet();
            while(rs.next()){
                DetalleHdr det=new DetalleHdr();
                det.setRazonSocial(rs.getString(1));
                det.setCodigoCliente(rs.getString(2));
                det.setNumeroDeComprobante(rs.getString(3));
                det.setSaldo(rs.getString(4));
                if(rs.getInt(5)==1){
                    entreg=false;
                }
                det.setEntregaCompletada(entreg);
                det.setMotivoFaltaDeEntrega(rs.getString(6));
                det.setNumero(rs.getInt(7));
                det.setReenviarPedido(rs.getInt(8));
                det.setNumeroPedidoTango(rs.getString(9));
                System.out.println("numero de pedido"+det.getNumeroPedidoTango());
                detalle.add(det);
            }
            rs.close();
            st.close();
            return detalle;
        }
        public ArrayList actualizarComprobantesPedidos(ArrayList cargados) throws SQLException, ParseException{
        try {
            cT=ConeccionSqlTango.ObtenerConeccion(1);
        } catch (ClassNotFoundException ex) {
            //error
            //error            //error            //error            
            GuardarMovimientos gArch=new Archivador();
                String cod1=String.valueOf(ex);
                gArch.registrarErrores(cod1, "", "");
                Logger.getLogger(Procesos.class.getName()).log(Level.SEVERE, null, ex);
        }
            PedidosParaReparto ped=new PedidosParaReparto();
            ArrayList resultado=new ArrayList();
            Iterator ic=cargados.listIterator();
            String sql=null;
            String fechaActual=null;
            SimpleDateFormat sfd=new SimpleDateFormat("yyyy-MM-dd");
            DecimalFormat fr=new DecimalFormat("00");
            DecimalFormat cf=new DecimalFormat("####.##");
        Calendar c1=Calendar.getInstance();
	Calendar c2=new GregorianCalendar();
	String dia=Integer.toString(c2.get(Calendar.DAY_OF_MONTH));
	String mes=Integer.toString(c2.get(Calendar.MONTH));
	String ano=Integer.toString(c2.get(Calendar.YEAR));
	
        int da=Integer.parseInt(dia);
        int me=Integer.parseInt(mes);
        me++;
        //da=da - 1;
        dia=fr.format(da);
        mes=fr.format(me);
        fechaActual=ano+"-"+mes+"-"+dia;
            Statement st=cp.createStatement();
            Statement xt=cT.createStatement();
            while(ic.hasNext()){
                ped=(PedidosParaReparto)ic.next();
                String comprobante=" ";
                System.out.println("ACTUALIZANDO PEDIDOS "+ped.getiDPedido());
                String empresa=ped.getEmpresa();
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
                                xt=sqlC.createStatement();
                                break;
                            case 2:
                                sqlC=(Connection)SiderconCapaatos.sqlSd;
                                xt=sqlC.createStatement();
                                break;
                            case 3:
                                sqlC=(Connection)SiderconCapaatos.sqlSdSrl;
                                xt=sqlC.createStatement();
                                break;
                        }
                
                //sql="truncate TABLA2";
                //st.executeUpdate(sql);
                //sql="select TABLA2.N_COMP_REM from TABLA2 where COD_CLI like '%"+ped.getCodigoCliente()+"' and FECHA_REM like '%"+fechaActual+"%' group by N_COMP_REM";
                
                
                //sql="select GVA21.N_REMITO from GVA21 where NRO_PEDIDO like '%"+ped.getCodigoTangoDePedido()+"' and FECHA_INGRESO like '%"+fechaActual+"' group by N_REMITO";
                String pedT=ped.getCodigoTangoDePedido().substring(2);
                
                        sql="select GVA21.N_REMITO from GVA21 where NRO_PEDIDO like '%"+pedT+"' group by N_REMITO";
                xt.execute(sql);
                System.out.println("COMPROBANTES QUE LEE EL SISTEMA "+sql);
                ResultSet xs=xt.getResultSet();
                String momentaneo=" ";
                while(xs.next()){
                        momentaneo=xs.getString(1);
       
                            comprobante+=momentaneo; 
                            System.out.println("comprobante encontrado "+comprobante+" nro pedido "+ped.getCodigoTangoDePedido());
                
                       
                }
                //comprobante=" ";
                xs.close();
                xt.close();
                Integer listado=0;
                String cli=null;
                Double saldo=0.00;
                sql="select pedidos_carga1.listado,pedidos_carga1.TALON_PEDI,pedidos_carga1.orden_num,pedidos_carga1.COND_VENTA,(select saldosclientesact.saldo from saldosclientesact where saldosclientesact.RAZON_SOC like pedidos_carga1.RAZON_SOC and saldosclientesact.COD_CLI like pedidos_carga1.COD_CLIENT)as saldo from pedidos_carga1 where numero="+ped.getiDPedido();
                st.execute(sql);
                ResultSet rr=st.getResultSet();
                while(rr.next()){
                    listado=rr.getInt(1);
                    ped.setEmpresa(rr.getString(2));
                    ped.setNumeroDeProceso(rr.getInt(3));
                    ped.setCondicionDeVenta(rr.getInt(4));
                    String empresa1=ped.getEmpresa();
                    
                    /*
                    int numeroConeccion1=0;
                        if(empresa1.equals("BU")){
                            numeroConeccion1=1;
                        }else{
                            if(empresa1.equals("SD")){
                                numeroConeccion1=2;
                            }else{
                                numeroConeccion1=3;
                            }
                        }
                        sqlC=null;
                        switch (numeroConeccion1){
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
                */
                    cli=ped.getCodigoCliente();
                    String trim = cli.trim();
                    Clientes cliente=new Clientes();
                    Actualizable actCli=new Clientes();
                    saldo=(Double)actCli.actualizarDatosSaldos(sqlC, empresa1,cli);
                    //Double sal=Math.ceil(saldo);
                    //saldo=sal;
                    ped.setSaldoCliente(saldo);
                    System.err.println("SALDO DEL CLIENTE "+saldo+" cliente "+ped.getRazonSocial());
                    }
                    rr.close();
                    ped.setNumeroComprobante(comprobante);
                    ped.setNumeroDeListadoDeMateriales(listado);
                    resultado.add(ped);
            }
            st.close();
            return resultado;
        } 
        public ArrayList listarPedidosPorZona(String fechEnt,int zonaNumero) throws SQLException{
            	/*
         * ACA SE GENERA UN ARRAY CON TODOS LOS OBJETOS PEDIDOS QUE CORRESPONDEN SER ENTREGADOS
         * EN LA FECHA PUNTUALIZADA. POR LO TANTO SE PUEDE VOLCAR A LA TABLA PARA SU SELECCION
         * 
         */
          //      Connection cp=cn.ObtenerConeccion();
		String sql="select *,(select TABLA1.actualizacion from TABLA1 where TABLA1.COD_CLI=pedidos_carga1.COD_CLIENT group by TABLA1.COD_CLI)as act,sum(pedidos_carga1.peso * pedidos_carga1.CANT_PEDID) as total,(select zonas.descripcion from zonas where zonas.numero=pedidos_carga1.zona)as zonasDescripcion,(select alertas.descripcion from alertas where alertas.numero=pedidos_carga1.alerta)as alertasDescripcion from pedidos_carga1 where entrega like '"+fechEnt+"%'and reparto=1 and zona="+zonaNumero+" group by NRO_PEDIDO order by RAZON_SOC";
		System.out.println(sql);
                
                PreparedStatement st=cp.prepareStatement(sql);
                //st.execute(sql);
		ResultSet rs=st.executeQuery();
		//synchronized rs;
                while(rs.next()){
			PedidosParaReparto pedidos=new PedidosParaReparto();
			pedidos.setiDPedido(rs.getInt("numero"));
			pedidos.setRazonSocial(rs.getString("RAZON_SOC"));
			pedidos.setCodigoTangoDePedido(rs.getString("NRO_PEDIDO"));
			pedidos.setVehiculoAsignado(rs.getInt("vehiculo"));
			pedidos.setPesoTotal(rs.getDouble("peso")* rs.getDouble("CANT_PEDID"));
                        pedidos.setCodigoArticulo(rs.getString("COD_ARTIC"));
                        pedidos.setDescripcionArticulo(rs.getString("DESC_ARTIC")+" "+rs.getString("DESC_ADIC"));
                        pedidos.setPesoItems(rs.getDouble("peso")* rs.getDouble("CANT_PEDID"));
                        pedidos.setCantidadArticulo(rs.getDouble("CANT_PEDID"));
			pedidos.setCodigoCliente(rs.getString("COD_CLIENT"));
                        pedidos.setFechaEnvio(rs.getString("entrega"));
                        pedidos.setFechaActualizacionSaldoCliente(rs.getDate("act"));
                        pedidos.setNumeroDeListadoDeMateriales(rs.getInt("listado"));
                        pedidos.setNumeroDeHojaDeRuta(rs.getInt("hdr1"));
                        pedidos.setNumeroDeProceso(rs.getInt("orden_num"));
                        pedidos.setNumeroDeFletero(rs.getInt("fletero"));
                        pedidos.setNumeroComprobante(rs.getString("N_REMITO"));
                        
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
                        listaPed.add(pedidos);
		}
                rs.close();
                //ActualizarDatosPedidos act=new ActualizarDatosPedidos();
                //act.setPedidos(listaPed);
                //act.start();
            //    cn.CerrarConneccion(cp);
		return listaPed;
		

        }
        public ArrayList actualizarVistaHdr(ArrayList carga){
            PedidosParaReparto ped=new PedidosParaReparto();
            PedidosParaReparto pedi=new PedidosParaReparto();
            ArrayList cc=new ArrayList();
            Iterator ic=carga.listIterator();
            String comprobante="";
            String codigoCliente="";
            String empresa="";
            String empresaV="";
            String codigoClienteV="";
            String comp="";
            ArrayList posiciones=new ArrayList();
            int a=0;
            int b=0;
            int numeroAnterior=0;
            int numeroAnteriorCargado=-1;
            while(ic.hasNext()){
                ped=(PedidosParaReparto)ic.next();
                empresaV=ped.getEmpresa();
                codigoClienteV=ped.getRazonSocial();
                
                comp=ped.getNumeroComprobante();
                System.out.println("REMITO ENCONTRADO "+comp);
                if((empresa.equals(empresaV))&&(codigoCliente.equals(codigoClienteV))){
                    empresa=ped.getEmpresa();
                    codigoCliente=ped.getRazonSocial();
                    b=a-1;
                    
                    
                    if(numeroAnteriorCargado==b){
                        pedi=(PedidosParaReparto) carga.get(a);
                        comprobante+=pedi.getNumeroComprobante();
                        posiciones.add(a);
                        
                    }else{
                        pedi=(PedidosParaReparto) carga.get(b);
                        comprobante+=pedi.getNumeroComprobante();
                        
                        posiciones.add(b);
                        pedi=(PedidosParaReparto)carga.get(a);
                        comprobante+=pedi.getNumeroComprobante();
                        posiciones.add(a);
                    }
                    //comprobante+=pedi.getNumeroComprobante();
                    numeroAnteriorCargado=a;
                    
                }else{
                    int c=0;
                    int hh=a-1;
                    if(numeroAnteriorCargado == hh){
                        int fh=0;
                    for(int ff=0;ff < posiciones.size();ff++){
                        //fh=ff -1;
                        c=(Integer)posiciones.get(ff);
                       fh=c;
                        pedi=(PedidosParaReparto)carga.get(fh);
                        String raz=pedi.getRazonSocial();
                        pedi.setNumeroComprobante(comprobante);
                    }
                    posiciones.clear();
                    comprobante="";
                    }
                    empresa=ped.getEmpresa();
                    codigoCliente=ped.getRazonSocial();
                    numeroAnterior=a;
                    //a++;
                }
                a++;
                cc.add(ped);
            }
            /*
           Iterator ifd=carga.listIterator();
           while(ifd.hasNext()){
               ped=(PedidosParaReparto)ifd.next();
               cc.add(ped);
           }
           */
            return cc;
        }

    public ArrayList DetallePedidoCompleto(String numeroPedido) throws SQLException {
                    ArrayList detalles=new ArrayList();
            String sql="select * from pedidos_carga1 where NRO_PEDIDO like '%"+numeroPedido+"%' and reparto=1";
            PreparedStatement st=cp.prepareStatement(sql);
            ResultSet rs=st.executeQuery();
            while(rs.next()){
                PedidosParaReparto pedido=new PedidosParaReparto();
                pedido.setCodigoTangoDePedido(numeroPedido);
                pedido.setCodigoArticulo(rs.getString("COD_ARTIC"));
                pedido.setDescripcionArticulo(rs.getString("DESC_ARTIC"));
                pedido.setCantidadArticulo(rs.getDouble("CANT_PEDID"));
                pedido.setCantidadArticuloPendiente(rs.getDouble("CANT_FACT"));
                if(pedido.getCantidadArticuloPendiente()==null){
                    pedido.setCantidadArticuloPendiente(0.00);
                }
                pedido.setFechaEnvio(rs.getString("entrega"));
                pedido.setiDPedido(rs.getInt("numero"));
                pedido.setIdPedidoEnTango(rs.getInt("ID_GVA03"));
                System.out.println("pedido :"+numeroPedido+" articulo: "+pedido.getDescripcionArticulo());
                detalles.add(pedido);
            }
            rs.close();
          //  cn.CerrarConneccion(cp);
            return detalles;

    }
}
