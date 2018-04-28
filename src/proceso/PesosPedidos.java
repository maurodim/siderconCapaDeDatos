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
import javax.swing.JOptionPane;
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
            String sql="update pedidos_carga1 inner join pesos ON pesos.codigo= pedidos_carga1.COD_ARTIC set pedidos_carga1.es=pesos.estruc";
            Statement st=cp.createStatement();
                st.executeUpdate(sql);
                System.out.println("UPDATE de ESTRUCTURA SUPERIOR "+sql);
            String codigo=null;
                Double pesoIt=0.00;
            Procesos pr = new Procesos();
                try {
                    art=pr.cargarPesosDeArticulos();
                } catch (SQLException ex) {
                    Logger.getLogger(PesosPedidos.class.getName()).log(Level.SEVERE, null, ex);
                }
            sql="select pedidos_carga1.COD_ARTIC,pedidos_carga1.CANT_PEDID,pedidos_carga1.numero,(select pesos.peso from pesos where pesos.codigo=pedidos_carga1.COD_ARTIC)as peso,(select pesos.estruc from pesos where pesos.codigo=pedidos_carga1.COD_ARTIC)as estructura from pedidos_carga1 where actualizacionPesos=0";
                //Statement st=cp.createStatement();
                st.execute(sql);
                ResultSet rs=st.getResultSet();
                while(rs.next()){
                    pesDet=new DetallePesosPedido();
                    pesDet.setCodigoArticulo(rs.getString(1));
                    pesDet.setCantidadArticulo(rs.getDouble(2));
                    pesDet.setNumero(rs.getInt(3));
                    codigo=rs.getString(1);
                    codigo.trim();
                    //ACA DEBERIA LLAMAR A UNA INTERFACE QUE DEVUELVA UN DOUBLE Y TOMO EL PESO
                    pesoIt=rs.getDouble("peso");
                    pesDet.setEstructura(rs.getInt("estructura"));
                    //pesoIt=(Double)art.get(codigo);
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
                    if(pesDet.getCantidadArticulo() > 0){
                        
                       //JOptionPane.showMessageDialog(null," numero id "+pesDet.getNumero()+" peso "+pesDet.getPesoUnitario()+" descr "+pesDet.getDescripcionArticulo()+" cod "+pesDet.getCodigoArticulo());
                    }
                    sql="update pedidos_carga1 set peso="+pesDet.getPesoUnitario()+",es="+pesDet.getEstructura()+",actualizacionPesos=1 where numero="+pesDet.getNumero();
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
