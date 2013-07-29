/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actualizaciones;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import objetos.PedidosParaReparto;
import proceso.Coneccion;

/**
 *
 * @author Administrador
 */
public class ActPedidosMysql extends Thread{
    private ArrayList pedidos;

    public void setPedidos(ArrayList pedidos) {
        this.pedidos = pedidos;
    }
    public synchronized void run(){
        try {
            PedidosParaReparto ped=new PedidosParaReparto();
            Iterator ipedi=this.pedidos.listIterator();
            while(ipedi.hasNext()){
                ped=(PedidosParaReparto)ipedi.next();
                Boolean veri=verificarExistencia(ped);
                ped.setVerificacion(veri);
            }
            ArrayList duplicados=new ArrayList();
            //ped.setVerificacion(verificarExistencia(ped));
            Coneccion con=new Coneccion();
            Connection cp=null;
            String sql=null;
            cp=con.getCn();
            Statement st=cp.createStatement();
            
            Iterator iped=this.pedidos.listIterator();
            while(iped.hasNext()){
                ped=(PedidosParaReparto)iped.next();
                Boolean ver=ped.getVerificacion();
                if(ver){
                    
                    duplicados.add(ped);
                }else{
                sql="insert into pedidos_carga1 (NRO_PEDIDO,FEC_PEDIDO,COD_CLIENT,RAZON_SOC,COND_VENTA,LEYENDA_1,LEYENDA_2,LEYENDA_3,COD_ARTIC,DESC_ARTIC,CANT_PEDID,entrega,reparto,peso,TALON_PEDI,orden_num,numeroOriginal) values ('"+ped.getCodigoTangoDePedido()+"','"+ped.getFechaPedidosTango()+"','"+ped.getCodigoCliente()+"','"+ped.getRazonSocial()+"',"+ped.getCondicionDeVenta()+",'"+ped.getObservaciones()+"','"+ped.getObservaciones1()+"','"+ped.getObservaciones2()+"','"+ped.getCodigoArticulo()+"','"+ped.getDescripcionArticulo()+"',"+ped.getCantidadArticulo()+",'"+ped.getFechaEnvio()+"',1,0.00"+",'"+ped.getEmpresa()+"',"+ped.getNumeroDeProceso()+","+ped.getiDPedido()+")";
                st.executeUpdate(sql);
                System.out.println(sql);
                }
            }
            st.close();
            con.CerrarCn(cp);
            ActPedidosAcce PedAc=new ActPedidosAcce();
            PedAc.setPedidos(pedidos);
            PedAc.start();
            Iterator idup=duplicados.listIterator();
            String detalle="Se ha actualizado en el sistema :\n";
            while(idup.hasNext()){
                ped=(PedidosParaReparto)idup.next();
                detalle+="PED Nº "+ped.getCodigoTangoDePedido()+" razon social "+ped.getRazonSocial().trim()+" art "+ped.getDescripcionArticulo()+" cant "+ped.getCantidadArticulo()+"\n";
                
            }
            if(duplicados.size() > 0){
                
                JOptionPane.showMessageDialog(null,detalle,"Actualizacion de Pedidos",JOptionPane.ERROR_MESSAGE);
            
            }
        } catch (SQLException ex) {
            Logger.getLogger(ActPedidosMysql.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private Boolean verificarExistencia(Object pedido) throws SQLException{
        PedidosParaReparto pedid=new PedidosParaReparto();
        pedid=(PedidosParaReparto)pedido;
        Boolean verifi=false;
        String sqll="select * from pedidos_carga1 where NRO_PEDIDO like '%"+pedid.getCodigoTangoDePedido()+"' and DESC_ARTIC like '"+pedid.getDescripcionArticulo()+"%' and CANT_PEDID ="+pedid.getCantidadArticulo();
        //String fechaEntrega="";
        //String razonSocial="";
        Coneccion cc=new Coneccion(); 
        Connection ctn=cc.getCn();
        Statement stt=ctn.createStatement();
        stt.execute(sqll);
        ResultSet rs=stt.getResultSet();
        while(rs.next()){
            verifi=true;
            
        }
        cc.CerrarCn(ctn);
        return verifi;
    }
}
