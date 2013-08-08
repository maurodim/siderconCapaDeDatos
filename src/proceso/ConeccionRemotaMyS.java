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
		String driver1="com.mysql.jdbc.Driver";
                
		String url1="jdbc:mysql://201.235.253.65:3306/maurodim_sidercon";
		String usuario1="maurodim_mSider";
		String clave1="2428WEBmauro";

                /*
                String url1="jdbc:mysql://192.168.0.111/siderpruebas";//"sidercon";
		String usuario1="mauro";//"hdr";
		String clave1="mauro";//daniel";
                */ 
		//Connection cn=null;
		try{
			Class.forName(driver1).newInstance();
			cn=DriverManager.getConnection(url1,usuario1,clave1);
			
		}catch(Exception ex){
                    GuardarMovimientos gArch=new Archivador();
                String cod1=String.valueOf(ex);
                gArch.registrarErrores(cod1, "", "");
			System.out.println("NO SE PUDO CONECTAR A LA BASE "+ex);
		}
		return cn;
	}
	public static void CerrarConneccion(Connection cb) throws SQLException{
		cb.close();
	}

    
}
