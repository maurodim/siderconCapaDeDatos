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
    
    public Boolean extraerIdTango(){
        Boolean verificado=true;
        try {
            //ArrayList listadoPedidos=new ArrayList();
            String sql="select * from pedidos_carga1 where idcheck=0";
            PedidosParaReparto ped=null;
            Connection cp=Coneccion.ObtenerConeccion();
            stat=cp.createStatement();
            stat.execute(sql);
            ResultSet rs=stat.getResultSet();
            Ideable chk=new Checking();
            while(rs.next()){
                ped=new PedidosParaReparto();
                ped.setCodigoTangoDePedido(rs.getString("NRO_PEDIDO"));
                ped.setCodigoArticulo(rs.getString("COD_ARTIC"));
                ped.setDescripcionArticulo(rs.getString("DESC_ARTIC"));
                ped.setiDPedido(rs.getInt("numero"));
                ped.setEmpresa(rs.getString("TALON_PEDI"));
                ped.setCantidadArticulo(0.00);
                //System.out.println("DATOS RASTREABLES :"+ped.getCodigoTangoDePedido()+" "+ped.getCodigoArticulo()+" "+ped.getCantidadArticulo()+" "+ped.getDescripcionArticulo()+" "+ped.getEmpresa());
                Integer idT=chk.leerId(ped.getCodigoTangoDePedido(),ped.getCodigoArticulo(),ped.getCantidadArticulo(), ped.getDescripcionArticulo(),ped.getEmpresa());
                ped.setIdPedidoEnTango(idT);
                System.err.append("ID MYSQL "+ped.getiDPedido()+" / ID TANGO "+ped.getIdPedidoEnTango()+"\n");
                if(chk.guardarIdEnMysql(ped.getIdPedidoEnTango(),ped.getiDPedido())){
                chk.marcarComoLeido(ped.getiDPedido());
                }
               
            }
            rs.close();
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(rastrearIdTango.class.getName()).log(Level.SEVERE, null, ex);
            verificado=false;
        }
        return verificado;
    }
    
}
