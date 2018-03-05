/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proceso;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Administrador
 */
public class ConeccionAccesRemota {
    private Connection ccnn=null;
    
    public Connection ObtenerConexion() throws ClassNotFoundException, SQLException{
        Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
        String filename="\\\\Server\\bases Caja (SERVER)\\hdr.mdb";
        String databse="jdbc:odbc:Driver={Microsoft Access Driver(*.mdb)};DBQ=";
        databse+=filename.trim()+";DriverID=22;READONLY=true}";
        ccnn=DriverManager.getConnection(databse,"","");
        return ccnn;
    }
    public void CerrarConexion(Connection cc) throws SQLException{
        cc.close();
    }
}
