/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actualizaciones;

import actualizaciones.objetosActualizables.ordenesDeTrabajo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrador
 */
public class GestionarOrdenesDeTrabajo implements ActualizarOrdenesDeTrabajo{

    @Override
    public ArrayList listarOT() {
        ArrayList listado=new ArrayList();
        try {
            
            ConeccionAcc conA=new ConeccionAcc();
    String database="jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=C:\\Documents and Settings\\Administrador\\Mis documentos\\bases\\hdrL.mdb";
    System.out.println(database);
   
        Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
        System.out.println(conA.getDriver());

        String sql="select * from pedidos_carga where reparto =1 and actualizacionRegistro = 1 and orden_num > 0";
        System.out.println(sql);
        Connection con=DriverManager.getConnection(database, "", "");
        Statement s=con.createStatement();
        s.execute(sql);
        ResultSet rs=s.getResultSet();
        while(rs.next()){
            ordenesDeTrabajo ot=new ordenesDeTrabajo();
            ot.setNumeroOrden(rs.getInt("orden_num"));
            ot.setNumeroPedido(rs.getString("NRO_PEDIDO"));
            ot.setCodigoArticulo(rs.getString("COD_ARTIC"));
            ot.setDescripcionArticulo(rs.getString("DESC_ARTIC"));
            ot.setCantidadPedido(rs.getDouble("CANT_PEDID"));
            ot.setNumeroOriginal(rs.getInt("numero"));
            System.out.println("ORDENES IMPORTADAS "+ot.getNumeroOriginal());
            //ot.setNumeroPedido(rs.getString("NRO_PEDID"));
            listado.add(ot);
        }
       
        } catch (SQLException ex) {
            Logger.getLogger(GestionarOrdenesDeTrabajo.class.getName()).log(Level.SEVERE, null, ex);
          
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GestionarOrdenesDeTrabajo.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        return listado;    

    }

    @Override
    public void actualzarOrdenes(ArrayList ot) {
        try {
            Iterator io=ot.listIterator();
            ordenesDeTrabajo oo=new ordenesDeTrabajo();
            ConeccionAcc conA=new ConeccionAcc();
        String database="jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=C:\\Documents and Settings\\Administrador\\Mis documentos\\bases\\hdrL.mdb";
        System.out.println(database);
       
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            System.out.println(conA.getDriver());

            String sql=null;
            //System.out.println(sql);
            Connection con=DriverManager.getConnection(database, "", "");
            Statement s=con.createStatement();
            while(io.hasNext()){
                oo=(ordenesDeTrabajo)io.next();
                sql="update pedidos_carga set actualizacionRegistro =2 where numero="+oo.getNumeroOriginal(); 
                System.out.println("ACTUALIZANDO OT // "+sql);
            s.executeUpdate(sql);
            }
        } catch (SQLException ex) {
            Logger.getLogger(GestionarOrdenesDeTrabajo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GestionarOrdenesDeTrabajo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
