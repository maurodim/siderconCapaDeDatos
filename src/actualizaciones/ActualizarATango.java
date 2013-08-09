/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actualizaciones;

import interfaces.Ideable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import proceso.Coneccion;

/**
 *
 * @author mauro di
 */
public class ActualizarATango implements Ideable{
    private Connection con;
    private Coneccion cone;
    private Integer numeroMy;
    private Integer Id_gva03;
    private ArrayList items=new ArrayList();

    public ActualizarATango() {
       cone=new Coneccion();
       con=cone.getCn();
    }
    
    @Override
    public synchronized void ActualizarIds() {
        String sql="select pedidos_carga1.NRO_PEDIDO from pedidos_carga1 where ID_GVA03=0 group by NRO_PEDIDO";
        /*
         * primero genero matrizcon los numero de pedidos, luego si, individualmente voy trayendo con respecto a los pedidos de tango y los asigno, de acuerdo con lo cargado en sistema
         * hdr
         * 
         */
        ArrayList pedidos=new ArrayList();
        String numPedidos="";
        try {
            Statement st=con.createStatement();
            st.executeQuery(sql);
            ResultSet rs=st.getResultSet();
            while(rs.next()){
                numPedidos=rs.getString("NRO_PEDIDO");
                System.out.println("NRO PEDIDO LEIDO SIN REFERENCIA A ID TANGO "+numPedidos.substring(2));
                pedidos.add(numPedidos);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(ActualizarATango.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
}
