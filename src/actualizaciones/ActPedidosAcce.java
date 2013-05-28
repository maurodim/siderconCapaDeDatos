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
        try {
            //ConeccionAcc conA=new ConeccionAcc();
            String database="jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=C:\\Documents and Settings\\Administrador\\Mis documentos\\bases\\hdrL.mdb";
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            Connection con=DriverManager.getConnection(database, "", "");
            Statement s=con.createStatement();
            PedidosParaReparto ped=new PedidosParaReparto();
            Iterator iPed=this.pedidos.listIterator();
            String sql=null;
            
            while(iPed.hasNext()){
                ped=(PedidosParaReparto)iPed.next();
                sql="update pedidos_carga set actualizacionRegistro=1 where numero ="+ped.getiDPedido();
                s.executeUpdate(sql);
                System.out.println("actualizacion "+sql);
            }
            s.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(ActPedidosAcce.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ActPedidosAcce.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
