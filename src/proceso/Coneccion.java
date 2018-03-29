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
//import org.apache.commons.dbcp.BasicDataSource;



/**
 *
 * @author USUARIO
 */
public class Coneccion {
	private static Connection cn=null;
        private String driver="com.mysql.jdbc.Driver";
        private String url="jdbc:mysql://192.168.0.111/sidercon";//"jdbc:mysql://192.168.0.111/siderpruebas";
        private String usuario="hdr";//"hdr";
        private String clave="daniel";//"daniel";

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
	public void CerrarCn(Connection cc) throws SQLException{
            cc.close();
        }
	public Coneccion() 
{
    		String driver1="com.mysql.jdbc.Driver";
                /*
		String url="jdbc:mysql://201.235.253.65:3306/maurodim_sidercon";
		String usuario="maurodim_mSider";
		String clave="2428WEBmauro";
*/
                String url1="jdbc:mysql://192.168.0.111/siderpruebas";//"sidercon";
		String usuario1="hdr";
		String clave1="daniel";
                //Connection cn=null;
		//Connection cn=null;
                //BasicDataSource basicDataSource=new BasicDataSource();
                MysqlDataSource dataSource=new MysqlDataSource();
		try{
			//Class.forName(driver1).newInstance();
                    dataSource.setUser(usuario1);
                    dataSource.setDatabaseName("sidercon");
                    dataSource.setPassword(clave1);
                    //dataSource.setServerName("192.168.0.111");
                    
                    dataSource.setServerName("192.168.0.111");
                    cn=dataSource.getConnection();
                 }catch(Exception ex){
                    GuardarMovimientos gArch=new Archivador();
                String cod1=String.valueOf(ex);
                gArch.registrarErrores(cod1, "", "");
			System.out.println("NO SE PUDO CONECTAR A LA BASE "+ex);
		}

		//String driver="com.mysql.jdbc.Driver";
		//String url="jdbc:mysql://192.168.0.111/sidercon";  //"jdbc:mysql://201.235.253.65:3306/maurodim_sidercon";
		//String usuario="mauro";  //"maurodim_mSider";
		//String clave="mauro";

		//Connection cn=null;

		
	}
	
	public static Connection ObtenerConeccion(){			
		return cn;
	}
	public static void CerrarConneccion(Connection cb) throws SQLException{
		cb.close();
	}
}
