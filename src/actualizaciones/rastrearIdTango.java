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
import java.util.logging.Level;
import java.util.logging.Logger;
import objetos.PedidosParaReparto;
import proceso.Coneccion;

/**
 *
 * @author mauro
 */
public class rastrearIdTango {
    private Integer idTango;
    private Integer idMy;
    private int revisado;
    private static Statement stat;
    
    public Boolean extraerIdTango(String empresa){
        Boolean verificado=true;
        
        try {
            //ArrayList listadoPedidos=new ArrayList();
            String sql="select * from pedidos_carga1 where ID_GVA03=0 or CANT_DESC is null and TALON_PEDI like '"+empresa+"' order by numero desc";
            PedidosParaReparto ped=null;
            Connection cp=Coneccion.ObtenerConeccion();
            stat=cp.createStatement();
            stat.execute(sql);
            ResultSet rs=stat.getResultSet();
            Ideable chk=new Checking();
            ArrayList listado=new ArrayList();
            while(rs.next()){
                ped=new PedidosParaReparto();
                ped.setCodigoTangoDePedido(rs.getString("NRO_PEDIDO"));
                ped.setCodigoArticulo(rs.getString("COD_ARTIC"));
                ped.setDescripcionArticulo(rs.getString("DESC_ARTIC"));
                ped.setiDPedido(rs.getInt("numero"));
                ped.setEmpresa(rs.getString("TALON_PEDI"));
                ped.setCantidadArticulo(rs.getDouble("CANT_PEDID"));
                
                listado.add(ped);
                //System.out.println("DATOS RASTREABLES :"+ped.getCodigoTangoDePedido()+" "+ped.getCodigoArticulo()+" "+ped.getCantidadArticulo()+" "+ped.getDescripcionArticulo()+" "+ped.getEmpresa());
               
                    /*
                    Integer idT=chk.leerId(ped.getCodigoTangoDePedido(),ped.getCodigoArticulo(),ped.getCantidadArticulo(), ped.getDescripcionArticulo(),ped.getEmpresa());
                    ped.setIdPedidoEnTango(idT);
                    System.err.append("ID MYSQL "+ped.getiDPedido()+" / ID TANGO "+ped.getIdPedidoEnTango()+"\n");
                    if(chk.guardarIdEnMysql(ped.getIdPedidoEnTango(),ped.getiDPedido())){
                    chk.marcarComoLeido(ped.getiDPedido());

                    }
                   */
            }
            if(listado.size() > 0)chk.leerId(listado,empresa);
            
            
            rs.close();
            
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(rastrearIdTango.class.getName()).log(Level.SEVERE, null, ex);
            verificado=false;
        }
        return verificado;
    }
    public Integer ExtraerIdPedidoTango(String empresa,Object pedido){
        Integer verificado=0;
        PedidosParaReparto pedi=(PedidosParaReparto) pedido;
        ArrayList lst=new ArrayList();
        lst.add(pedido);
        Ideable chk=new Checking();
        if(lst.size() > 0){
            verificado=chk.leerId(lst,empresa);
            chk.guardarIdEnMysql(verificado, pedi.getiDPedido());
        }
           
            
        
        return verificado;
    }
}
