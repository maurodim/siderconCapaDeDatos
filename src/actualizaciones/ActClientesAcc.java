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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Administrador
 */
public class ActClientesAcc extends Thread{
    ArrayList clientes;

    public void setClientes(ArrayList clientes) {
        this.clientes = clientes;
    }
    @Override
    public synchronized void run(){
        try {
            //ConeccionAcc conA=new ConeccionAcc();
            String database="jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=C:\\Documents and Settings\\Administrador\\Mis documentos\\bases\\hdrL.mdb";
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            Connection con=DriverManager.getConnection(database, "", "");
            Statement s=con.createStatement();
            //ClientesAct cli=new ClientesAct();
            //Iterator iPed=this.clientes.listIterator();
            String sql="";
            //while(iPed.hasNext()){
             //   cli=(ClientesAct)iPed.next();
                sql="update TABLA1 set revizado=1";
                s.executeUpdate(sql);
                System.out.println("base access "+sql);
            //}
            s.close();
            con.close();
            JOptionPane.showMessageDialog(null, "Proceso de exportacion de saldos Terminado"); 
        } catch (SQLException ex) {
            Logger.getLogger(ActClientesAcc.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ActClientesAcc.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
