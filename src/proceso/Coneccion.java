/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proceso;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;
import seguimientos.Archivador;
import seguimientos.GuardarMovimientos;

/**
 *
 * @author USUARIO
 */
public class Coneccion {
	public Connection cn=null;
        private static String driver="com.mysql.jdbc.Driver";
        private static String url="jdbc:mysql://192.168.0.111/sidercon";
        private static String usuario="hdr";//"hdr";
        private static String clave="daniel";//"daniel";

    public static String getDriver() {
        return driver;
    }

    public static String getUrl() {
        return url;
    }

    public static String getUsuario() {
        return usuario;
    }

    public static String getClave() {
        return clave;
    }
        
        
	public Connection getCn() {
		return cn;
	}
	
	public Coneccion() 
{
		//String driver="com.mysql.jdbc.Driver";
		//String url="jdbc:mysql://192.168.0.111/sidercon";  //"jdbc:mysql://201.235.253.65:3306/maurodim_sidercon";
		//String usuario="mauro";  //"maurodim_mSider";
		//String clave="mauro";

		//Connection cn=null;
		try{
			Class.forName(driver);
			cn=DriverManager.getConnection(url,usuario,clave);
				
		}catch(Exception ex){
                    GuardarMovimientos gArch=new Archivador();
                String cod1=String.valueOf(ex);
                gArch.registrarErrores(cod1, "", "");
			System.out.println("NO SE PUDO CONECTAR "+ex);
		}
		
	}
	
	public static Connection ObtenerConeccion(){
		String driver1="com.mysql.jdbc.Driver";
                /*
		String url="jdbc:mysql://201.235.253.65:3306/maurodim_sidercon";
		String usuario="maurodim_mSider";
		String clave="2428WEBmauro";
*/
                String url1="jdbc:mysql://192.168.0.111/sidercon";//"sidercon";
		String usuario1="hdr";//"hdr";
		String clave1="daniel";//daniel";
                Connection cn=null;
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
