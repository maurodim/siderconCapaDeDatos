/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actualizaciones;

import actualizaciones.objetosActualizables.ordenesDeTrabajo;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import proceso.Coneccion;

/**
 *
 * @author Administrador
 */
public class OrdenesTrabajoMy implements ActualizarOrdenesDeTrabajo{

    @Override
    public ArrayList listarOT() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void actualzarOrdenes(ArrayList ot) {
        try {
            Coneccion con=new Coneccion();
            ordenesDeTrabajo oot=new ordenesDeTrabajo();
            Connection cn=null;
            String sql=null;
            cn=con.ObtenerConeccion();
            Statement st=cn.createStatement();
            Iterator oo=ot.listIterator();
            while(oo.hasNext()){
                oot=(ordenesDeTrabajo)oo.next();
                sql="update pedidos_carga1 set orden_num="+oot.getNumeroOrden()+" where NRO_PEDIDO like '%"+oot.getNumeroPedido()+"' and COD_ARTIC like '"+oot.getCodigoArticulo()+"'";
                System.err.println("ACTUALIZANDO MY OT "+sql);
                st.executeUpdate(sql);
            }
            st.close();
            con.CerrarConneccion(cn);
        } catch (SQLException ex) {
            Logger.getLogger(OrdenesTrabajoMy.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
