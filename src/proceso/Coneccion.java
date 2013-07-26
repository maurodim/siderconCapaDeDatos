/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proceso;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;
import javax.sql.DataSource;
import seguimientos.Archivador;
import seguimientos.GuardarMovimientos;


/**
 *
 * @author USUARIO
 */
public class Coneccion {
	private Connection cn=null;
        private String driver="com.mysql.jdbc.Driver";
        private String url="jdbc:mysql://192.168.0.111/siderpruebas";
        private String usuario="mauro";//"hdr";
        private String clave="mauro";//"daniel";

    public  String getDriver() {
        return driver;
    }

    public  String getUrl() {
        return url;
    }

    public  String getUsuario() {
        return usuario;
    }

    public  String getClave() {
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

		
	}
	
	public Connection ObtenerConeccion(){
            
		String driver1="com.mysql.jdbc.Driver";
                /*
		String url="jdbc:mysql://201.235.253.65:3306/maurodim_sidercon";
		String usuario="maurodim_mSider";
		String clave="2428WEBmauro";
*/
                String url1="jdbc:mysql://192.168.0.111/siderpruebas";//"sidercon";
		String usuario1="hdr";//"hdr";
		String clave1="daniel";//daniel";
                Connection cn=null;
		//Connection cn=null;
                MysqlDataSource dataSource=new MysqlDataSource();
		try{
			//Class.forName(driver1).newInstance();
                    dataSource.setUser(usuario1);
                    dataSource.setDatabaseName("siderpruebas");
                    dataSource.setPassword(clave1);
                    dataSource.setServerName("192.168.0.111");
			cn=dataSource.getConnection();
			
		}catch(Exception ex){
                    GuardarMovimientos gArch=new Archivador();
                String cod1=String.valueOf(ex);
                gArch.registrarErrores(cod1, "", "");
			System.out.println("NO SE PUDO CONECTAR A LA BASE "+ex);
		}
		return cn;
	}
	public void CerrarConneccion(Connection cb) throws SQLException{
		cb.close();
	}
}
