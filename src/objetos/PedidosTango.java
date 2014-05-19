/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objetos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import proceso.Coneccion;
import siderconcapadatos.SiderconCapaatos;

/**
 *
 * @author mauro
 */
public class PedidosTango {
    private String codigoPedido;
    private String empresa;
    private String codigoArticulo;
    private Double cantidad;
    private Integer verif=0;
    private ArrayList sentenciasDeBorrado=new ArrayList();

    public Integer getVerif() {
        return verif;
    }

    public void setVerif(Integer verif) {
        this.verif = verif;
    }

    public String getCodigoPedido() {
        return codigoPedido;
    }

    public void setCodigoPedido(String codigoPedido) {
        this.codigoPedido = codigoPedido;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getCodigoArticulo() {
        return codigoArticulo;
    }

    public void setCodigoArticulo(String codigoArticulo) {
        this.codigoArticulo = codigoArticulo;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }
    
    public Boolean verificarPedidos(ArrayList listadoDePedidos) throws SQLException, InterruptedException{
        Iterator itL=listadoDePedidos.listIterator();
        Integer tamano=listadoDePedidos.size();
        Integer tamanoO=tamano;
        PedidosParaReparto pedidos=new PedidosParaReparto();
        PedidosTango pedTango=null;
        String sql="";
        String sentencias="";
        Statement xt=null;
        Connection sqlC=null;
        int bandera=0;
        while(itL.hasNext()){
            pedidos=(PedidosParaReparto)itL.next();
            pedTango=new PedidosTango();
            bandera=0;
            pedTango.setEmpresa(pedidos.getEmpresa());
            pedTango.setCodigoPedido(pedidos.getCodigoTangoDePedido());
            pedTango.setCodigoArticulo(pedidos.getCodigoArticulo());
            pedTango.setCantidad(pedidos.getCantidadArticulo());
            pedTango.setVerif(pedidos.getiDPedido());
             String codigoPedido=pedTango.getCodigoPedido().substring(2);
             System.out.println(" datos de pedidos "+pedTango.getVerif()+" empresa "+pedTango.getEmpresa()+"  cod Ped "+pedTango.getCodigoPedido()+" cod Art "+pedTango.getCodigoArticulo()+" cant "+pedTango.getCantidad());
            // aca debo verificar la cantidad
            sql="select * from GVA03 where NRO_PEDIDO like '%"+codigoPedido+"' and COD_ARTICU ='"+pedTango.getCodigoArticulo()+"'";
           
            String empresa=pedTango.getEmpresa();
            //Statement xt=null;
        
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
                        
                        switch (numeroConeccion){
                            case 1:
                                sqlC=(Connection)SiderconCapaatos.sqlBu;
                                try {
                                    xt=sqlC.createStatement();
                                } catch (SQLException ex) {
                                    Logger.getLogger(PedidosTango.class.getName()).log(Level.SEVERE, null, ex);
                                    System.out.println(" excepcion tango EN LA CONECCION¡¡¡ "+sql+" empresa //"+empresa);
                                }
                                break;
                            case 2:
                                sqlC=(Connection)SiderconCapaatos.sqlSd;
                                try {
                                    xt=sqlC.createStatement();
                                } catch (SQLException ex) {
                                    Logger.getLogger(PedidosTango.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                break;
                            case 3:
                                sqlC=(Connection)SiderconCapaatos.sqlSdSrl;
                                try {
                                    xt=sqlC.createStatement();
                                } catch (SQLException ex) {
                                    Logger.getLogger(PedidosTango.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                break;
                        }
            try {
                            xt.execute(sql);
                            ResultSet rs=xt.getResultSet();
                            Double cantidadT=0.00;
                            Double cantidadTPendiente=0.00;
                            Double cantidadEq=0.00;
                            while(rs.next()){
                                bandera=1;
                                cantidadT=rs.getDouble("CANT_A_DES");
                                cantidadTPendiente=rs.getDouble("CANT_PEDID");
                                cantidadEq=rs.getDouble("CAN_EQUI_V");
                                System.out.println("CANT A DES "+cantidadT+" articulo "+rs.getString("COD_ARTICU")+" pedido "+rs.getString("NRO_PEDIDO"));
                                 System.out.println("EN TANGO "+sql+" / / "+empresa);
                                Thread.sleep(3000);
                                if(cantidadT==0){
                                    String ped=rs.getString("NRO_PEDIDO").substring(2);
                                sentencias="update pedidos_carga1 set CANT_FACT=0 where COD_ARTIC='"+rs.getString("COD_ARTICU")+"' and NRO_PEDIDO like '%"+ped+"'";
                                sentenciasDeBorrado.add(sentencias);
                                System.out.println("HAY QUE BORRAR "+sentenciasDeBorrado.size());
                                Thread.sleep(10000);
                                System.out.println(sentencias);
                                System.out.println("EN TANGO "+sql);
                                }
                            }
                            rs.close();
                            xt.close();
                            
            } catch (SQLException ex) {
                Logger.getLogger(PedidosTango.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(" excepcion tango "+sql+" empresa //"+empresa);
            }
            if(bandera==0){
                sentencias="update pedidos_carga1 set CANT_FACT=0 where COD_ARTIC='"+pedTango.getCodigoArticulo()+"' and NRO_PEDIDO like '%"+codigoPedido+"'";
                System.out.println(" para limpiar "+sentencias);
                sentenciasDeBorrado.add(sentencias);
            }
            tamano=tamano - 1;
            System.out.println(" TAMAÑO ACTUAL DE LA MATRIZ "+tamano+" ORIGINAL "+tamanoO);
        }
        verificarCantidadEnTango();
        return null;
        
    }
    private void verificarCantidadEnTango() throws SQLException{
        Iterator itS=sentenciasDeBorrado.listIterator();
        String sql="";
        Coneccion con=new Coneccion();
        Connection cp=con.getCn();
        Statement st=cp.createStatement();
        while(itS.hasNext()){
            sql=(String)itS.next();
            st.executeUpdate(sql);
            System.out.println(sql);
        }
        st.close();
        con.CerrarCn(cp);
        
        
        
    }
}
