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
import objetos.Articulos;
import proceso.Coneccion;

/**
 *
 * @author MAURO DI
 */
public class ActualizacionPesos extends Thread{
    private static ArrayList listadoDePesos=new ArrayList();
    private static Coneccion con=new Coneccion();
    private static Connection cnn;
    
    public ActualizacionPesos() throws SQLException {
         
        cnn=con.getCn();
        String sql="select * from pesos";
        Articulos art=null;
        Statement st=cnn.createStatement();
        st.execute(sql);
        ResultSet rs=st.getResultSet();
        while(rs.next()){
            art=new Articulos();
            art.setCodigo(rs.getString("codigo"));
            art.setPesoUnitario(rs.getDouble("peso"));
            listadoDePesos.add(art);
        }
        rs.close();
        st.close();
        ////Coneccion.CerrarConneccion(cnn);
        
    }
    public synchronized void run(){
        try {
            Iterator il=listadoDePesos.listIterator();
            Statement st=cnn.createStatement();
            String sql="";
            Articulos art=new Articulos();
            String arr="";
            while(il.hasNext()){
                art=(Articulos)il.next();
                arr=art.getCodigo().trim();
                sql="update pedidos_carga1 set peso="+art.getPesoUnitario()+" where COD_ARTIC like '"+arr+"'";
                st.executeUpdate(sql);
                System.out.println(" MODIFICACION DE PESOS "+sql);
                
            }
            st.close();
            con.CerrarCn(cnn);
            ////Coneccion.CerrarConneccion(cnn);
        } catch (SQLException ex) {
            Logger.getLogger(ActualizacionPesos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
