/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actualizaciones;

import actualizaciones.objetosActualizables.Comprobantes;
import java.sql.Connection;
import java.sql.DriverManager;
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
public class ActComprobantesAcc extends Thread{
    private ArrayList listadoComprobantes=new ArrayList();

    public void setListadoComprobantes(ArrayList listadoComprobantes) {
        this.listadoComprobantes = listadoComprobantes;
    }
    @Override
    public synchronized void run(){
        try {
            //ConeccionAcc conA=new ConeccionAcc();
            String database="jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=C:\\Documents and Settings\\Administrador\\Mis documentos\\bases\\hdrL.mdb";
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            Connection con=DriverManager.getConnection(database, "", "");
            Statement s=con.createStatement();
            Iterator icP=this.listadoComprobantes.listIterator();
            String sql=null;
            Comprobantes comp=new Comprobantes();
            while(icP.hasNext()){
                comp=(Comprobantes)icP.next();
                sql="update TABLA2 set revizado=1 where numero="+comp.getNumero();
                s.executeUpdate(sql);
            }
            s.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(ActComprobantesAcc.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ActComprobantesAcc.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
