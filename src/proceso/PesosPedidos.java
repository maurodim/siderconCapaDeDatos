/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proceso;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import objetos.Articulos;
import objetos.DetallePesosPedido;

/**
 *
 * @author Administrador
 */
public class PesosPedidos extends Thread{
    private Connection cp;
    private Coneccion con;

    public PesosPedidos() {
        con=new Coneccion();
        cp=con.getCn();
    }
    
    
    @Override
    public synchronized void run(){
        try {
            
            Map art=new HashMap();
            ArrayList pesosArt=new ArrayList();
            DetallePesosPedido pesDet=new DetallePesosPedido();
             
            
            String codigo=null;
                Double pesoIt=0.00;
            Procesos pr = new Procesos();
                try {
                    art=pr.cargarPesosDeArticulos();
                } catch (SQLException ex) {
                    Logger.getLogger(PesosPedidos.class.getName()).log(Level.SEVERE, null, ex);
                }
            String sql="select pedidos_carga1.COD_ARTIC,pedidos_carga1.CANT_PEDID,pedidos_carga1.numero from pedidos_carga1 where actualizacionPesos=0";
                Statement st=cp.createStatement();
                st.execute(sql);
                ResultSet rs=st.getResultSet();
                while(rs.next()){
                    pesDet=new DetallePesosPedido();
                    pesDet.setCodigoArticulo(rs.getString(1));
                    pesDet.setCantidadArticulo(rs.getDouble(2));
                    pesDet.setNumero(rs.getInt(3));
                    codigo=rs.getString(1);
                    codigo.trim();
                    pesoIt=(Double)art.get(codigo);
                    if(pesoIt==null){
                        pesoIt=0.00;
                    }
                    pesDet.setPesoUnitario(pesoIt);
                    pesosArt.add(pesDet);
                }
                rs.close();
                st.close();
                Iterator ilp=pesosArt.listIterator();
                Statement ht=cp.createStatement();
                while(ilp.hasNext()){
                    pesDet=(DetallePesosPedido)ilp.next();
                    //codigo=pesDet.getCodigoArticulo();
                    //codigo.trim();
                    //pesoIt=(Double)art.get(codigo);
                    sql="update pedidos_carga1 set peso="+pesDet.getPesoUnitario()+",actualizacionPesos=1 where numero="+pesDet.getNumero();
                    System.out.println("UPDATE "+sql);
                    ht.executeUpdate(sql);
                }
                ht.close();
                //Coneccion.CerrarConneccion(cp);    
        } catch (SQLException ex) {
            Logger.getLogger(PesosPedidos.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            con.CerrarCn(cp);
        } catch (SQLException ex) {
            Logger.getLogger(PesosPedidos.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
}
