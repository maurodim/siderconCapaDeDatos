/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actualizaciones;

import actualizaciones.objetosActualizables.ClientesAct;
import actualizaciones.objetosActualizables.Comprobantes;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import objetos.PedidosParaReparto;
import seguimientos.Archivador;
import seguimientos.GuardarMovimientos;

/**
 *
 * @author Administrador
 */
public class ImportarPedidos implements Actualizar{
    public void listarPedidos(){
        ConeccionAcc conA=new ConeccionAcc();
        
        ArrayList pedidos=new ArrayList();
        ArrayList Clientes=new ArrayList();
        ArrayList comproban=new ArrayList();
        System.out.println(conA.getUrl());
        //String database="jdbc:odbc:Driver={Microsoft Access Driver(*.mdb)};DBQ=";
        //database+=conA.getUrl().trim()+";DriverID=22;READONLY=true}";
        //DriverID=22;READONLY=true
        String database="jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=C:\\Documents and Settings\\Administrador\\Mis documentos\\bases\\hdrL.mdb";
        System.out.println(database);
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            System.out.println(conA.getDriver());
        try {
            String sql="select * from pedidos_carga where reparto =1 and actualizacionRegistro < 1";
            System.out.println(sql);
            Connection con=DriverManager.getConnection(database, "", "");
            Statement s=con.createStatement();
            s.execute(sql);
            ResultSet rs=s.getResultSet();
            while(rs.next()){
              PedidosParaReparto ped=new PedidosParaReparto();
              
              String nPed=rs.getString(1);
              
              ped.setCodigoTangoDePedido(nPed);
                String fechaP=rs.getString("FEC_PEDIDO");
               ped.setFechaPedidosTango(fechaP);
               String codC=rs.getString("COD_CLIENT");
               ped.setCodigoCliente(codC);
               String razonS=rs.getString("RAZON_SOC");
               ped.setRazonSocial(razonS);
               int condV=rs.getInt("COND_VENTA");
               ped.setCondicionDeVenta(condV);
               String obs=rs.getString("LEYENDA_1");
               ped.setObservaciones1(rs.getString("LEYENDA_2"));
               ped.setObservaciones2(rs.getString("LEYENDA_3"));
               ped.setObservaciones(obs);
               String codA=rs.getString("COD_ARTIC");
               ped.setCodigoArticulo(codA);
               String descA=rs.getString("DESC_ARTIC")+" "+rs.getString("DESC_ADIC");
               ped.setDescripcionArticulo(descA);
               Double cantA=rs.getDouble("CANT_PEDID");
               ped.setCantidadArticulo(cantA);
               Integer num=rs.getInt("numero");
               ped.setiDPedido(num);
               DecimalFormat fr=new DecimalFormat("00");
                Calendar c1=Calendar.getInstance();
                Calendar c2=new GregorianCalendar();
                String dia=Integer.toString(c2.get(Calendar.DAY_OF_MONTH));
                String mes=Integer.toString(c2.get(Calendar.MONTH));
                String ano=Integer.toString(c2.get(Calendar.YEAR));

                int da=Integer.parseInt(dia);
                int me=Integer.parseInt(mes);
                me++;
                dia=fr.format(da);
                mes=fr.format(me);
                //String fechaE=dia+"/"+mes+"/"+ano;
               String fechaE=rs.getString("entrega");
               ped.setFechaEnvio(fechaE);
               if(rs.getInt("TALON_PEDI")==6){
                   ped.setEmpresa("BU");
               }else{
                   ped.setEmpresa("SD");
               }
               ped.setNumeroDeProceso(rs.getInt("orden_num"));
               ped.setVerificacion(false);
               System.out.println("importando "+fechaE+" fc "+razonS);
                pedidos.add(ped);
            }
            sql="select * from TABLA1 where revizado=0 order by RAZON_SOC";
            s.execute(sql);
            rs=s.getResultSet();
            Double total=0.00;
            String cliente=null;
            String nuevoCli=null;
            String codigoCli=null;
            Date fecha=null;
            String numeroComp=null;
            int revizado=0;
            Integer numero=0;
            Date fechaExportacion=null;
            while(rs.next()){
                
                nuevoCli=rs.getString("RAZON_SOC");

                    total=rs.getDouble("IMPORTE");
                ClientesAct cl=new ClientesAct();
                cl.setCodigo(rs.getString("COD_CLI"));
                cl.setRazonSocial(nuevoCli);
                cl.setFecha(rs.getDate("FECHA"));
                cl.setNumComprobante("N_COMP");
                   
                cl.setRevizado(rs.getInt("revizado"));
                cl.setNumero(rs.getInt("numero"));
                cl.setFechaExportacion(rs.getDate("fechaExportacion"));
                System.err.println("saldos :"+cl.getRazonSocial()+"fecha "+cl.getFechaExportacion());
                  cl.setImporte(total);
                Clientes.add(cl);
                cliente=nuevoCli;
                
                
            }
            sql="select * from TABLA2 where revizado=0 and FECHA_REM is not null";
            s.execute(sql);
            rs=s.getResultSet();
            while(rs.next()){
                Comprobantes comp=new Comprobantes();
                comp.setCodigoCliente(rs.getString("COD_CLI"));
                comp.setRazonSocial(rs.getString("RAZON_SOCI"));
                comp.setNumeroComprobante("N_COMP");
                comp.setCondicionVenta(rs.getInt("COND_VTA"));
                comp.setCodigoArticulo(rs.getString("COD_ARTICU"));
                comp.setDescripcion(rs.getString("DESCRIPCIO"));
                comp.setCantidad(rs.getDouble("CANT_REM"));
                comp.setNumeroRemito(rs.getString("N_COMP_REM"));
                comp.setFechaRemito(rs.getDate("FECHA_REM"));
                comp.setFechaEmisionComprobante(rs.getString("FECHA_EMI"));
                comp.setCodigoDeposito(1);
                comp.setRevizado(rs.getInt("revizado"));
                comp.setNumero(rs.getInt("numero"));
                System.err.println("FECHA comprobantes "+comp.getFechaRemito());
                comproban.add(comp);
            }
            rs.close();
            s.close();
            con.close();
        } catch (SQLException ex) {
           System.out.println("no entra en connection");
            GuardarMovimientos gArch=new Archivador();
                String cod1=String.valueOf(ex);
                gArch.registrarErrores(cod1, "", "");
                Logger.getLogger(ImportarPedidos.class.getName()).log(Level.SEVERE, null, ex);
        }
 
            
        } catch (ClassNotFoundException ex) {
            System.out.println("no entra en forname");
            GuardarMovimientos gArch=new Archivador();
                String cod1=String.valueOf(ex);
                gArch.registrarErrores(cod1, "", "");
                Logger.getLogger(ImportarPedidos.class.getName()).log(Level.SEVERE, null, ex);
        }

        ActPedidosMysql acPed=new ActPedidosMysql();
        acPed.setPedidos(pedidos);
        acPed.start();
        ActClientesMysql acCli=new ActClientesMysql();
        acCli.setClientes(Clientes);
        acCli.start();
        ActComprobantesMysql acmD=new ActComprobantesMysql();
        acmD.setListadoComprobantes(comproban);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ex) {
            GuardarMovimientos gArch=new Archivador();
                String cod1=String.valueOf(ex);
                gArch.registrarErrores(cod1, "", "");
                Logger.getLogger(ImportarPedidos.class.getName()).log(Level.SEVERE, null, ex);
        }
        acmD.start();
        
    }
    private void cargarMonto(Double total,ClientesAct cli){
        Double saldo=0.00;
        saldo=cli.getImporte();
        saldo+=total;
        cli.setImporte(saldo);
    }
}
