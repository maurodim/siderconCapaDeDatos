/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actualizaciones;

/**
 *
 * @author Administrador
 */
public class ConeccionAcc {
    	private String driver="sun.jdbc.odbc.JdbcOdbcDriver";
	private String url="C:\\Documents and Settings\\Administrador\\Mis documentos\\bases\\hdrL.mdb";
                //"serverHdr";//"\\\\Server\\bases Caja (server)\\hdr.mdb";  //"jdbc:mysql://201.235.253.65:3306/maurodim_sidercon";
	private String usuario="";  //"maurodim_mSider";
	private String clave="";

    public String getClave() {
        return clave;
    }

    public String getDriver() {
        return driver;
    }

    public String getUrl() {
        return url;
    }

    public String getUsuario() {
        return usuario;
    }
            

}
