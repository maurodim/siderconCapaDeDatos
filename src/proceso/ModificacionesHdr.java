/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proceso;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import objetos.DetalleHdr;

/**
 *
 * @author Administrador
 */
public class ModificacionesHdr implements AbmHdr{

    @Override
    public void agregarItem(Object item,Integer numero,String comprobante) {
        try {
            DetalleHdr detalle=new DetalleHdr();
            detalle=(DetalleHdr) item;
            Coneccion con=new Coneccion();
            String sql="insert into detalle_hdr (cliente,empresa,comprobante,importe,observaciones,hdr) values ('"+detalle.getRazonSocial()+"','"+detalle.getEmpresa()+"','"+comprobante+"','"+detalle.getSaldo()+"','"+detalle.getObservaciones()+"',"+numero+")";
            Connection cn=con.ObtenerConeccion();
            Statement st=cn.createStatement();
            st.executeUpdate(sql);
            st.close();
            con.CerrarConneccion(cn);
        } catch (SQLException ex) {
            Logger.getLogger(ModificacionesHdr.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public void anularHdr(Integer numeroHdr) {
        try {
            Coneccion con=new Coneccion();
            String sql="delete from hdr where numero="+numeroHdr;
            Connection cn=con.ObtenerConeccion();
            Statement st=cn.createStatement();
            st.executeUpdate(sql);
            sql="delete from detalle_hdr where hdr="+numeroHdr;
            st.executeUpdate(sql);
            st.close();
            con.CerrarConneccion(cn);
        } catch (SQLException ex) {
            Logger.getLogger(ModificacionesHdr.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
