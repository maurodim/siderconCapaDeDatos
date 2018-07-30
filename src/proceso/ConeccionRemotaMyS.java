/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proceso;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import seguimientos.Archivador;
import seguimientos.GuardarMovimientos;

/**
 *
 * @author mauro di
 */
public class ConeccionRemotaMyS {
    private static Connection cn=null;
    
    	public static Connection ObtenerConeccion(){
		
		return cn;
	}
	public static void CerrarConneccion(Connection cb) throws SQLException{
		cb.close();
	}

    
}
