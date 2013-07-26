/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actualizaciones;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import objetos.PedidosParaReparto;

/**
 *
 * @author Administrador
 */
public class ActPedidosAcce extends Thread{
    private ArrayList pedidos;

    public void setPedidos(ArrayList pedidos) {
        this.pedidos = pedidos;
    }
    @Override
    public synchronized void run(){
        
            //ConeccionAcc conA=new ConeccionAcc();
            String database="jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=C:\\Documents and Settings\\Administrador\\Mis documentos\\bases\\hdrL.mdb";
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ActPedidosAcce.class.getName()).log(Level.SEVERE, null, ex);
        }
            Connection con = null;
        try {
            con = DriverManager.getConnection(database, "", "");
        } catch (SQLException ex) {
            Logger.getLogger(ActPedidosAcce.class.getName()).log(Level.SEVERE, null, ex);
            try {
                con.close();
            } catch (SQLException ex1) {
                Logger.getLogger(ActPedidosAcce.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
            Statement s = null;
        try {
            s = con.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(ActPedidosAcce.class.getName()).log(Level.SEVERE, null, ex);
        }
            PedidosParaReparto ped=new PedidosParaReparto();
            Iterator iPed=this.pedidos.listIterator();
            String sql=null;
            
            while(iPed.hasNext()){
                ped=(PedidosParaReparto)iPed.next();
                sql="update pedidos_carga set actualizacionRegistro=1 where numero ="+ped.getiDPedido();
            try {
                s.executeUpdate(sql);
            } catch (SQLException ex) {
                Logger.getLogger(ActPedidosAcce.class.getName()).log(Level.SEVERE, null, ex);
            }
                System.out.println("actualizacion "+sql);
            }
        try {
            s.close();
        } catch (SQLException ex) {
            Logger.getLogger(ActPedidosAcce.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(ActPedidosAcce.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
