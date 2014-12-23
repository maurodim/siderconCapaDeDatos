/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proceso;

import config.Configuracion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import seguimientos.Archivador;
import seguimientos.GuardarMovimientos;
import siderconcapadatos.SiderconCapaatos;

/**
 *
 * @author mauro di
 */
public class ConeccionSqlTango {
    static String driver;
    static String url;
    static String usuario;
    static String clave;
    public ConeccionSqlTango(int base) {
 		driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
                switch (base){
                    case 1:
                        url="jdbc:sqlserver://SERVERTANGO;databaseName=BU3";
                        break;
                    case 2:
                        url="jdbc:sqlserver://SERVERTANGO;databaseName=SIDERCON";
                        break;
                    case 3:
                        url="jdbc:sqlserver://SERVERTANGO;databaseName=SIDERCON_S_R_L";
                        break;
                    default:
                        url="jdbc:sqlserver://SERVERTANGO;databaseName=BU2";
                        break;
                }
		  //"jdbc:mysql://201.235.253.65:3306/maurodim_sidercon";
		usuario="Axoft";  //"maurodim_mSider";
		clave="Axoft";

		

    }
public static Connection ObtenerConeccion(int bb) throws ClassNotFoundException{
            Class.forName(ConeccionSqlTango.driver);
        String filename="\\\\Server\\bases Caja (SERVER)\\hdr.mdb";
        String databse="jdbc:odbc:Driver={Microsoft Access Driver(*.mdb)};DBQ=";
        databse+=filename.trim()+";DriverID=22;READONLY=true}";
        Iterator icon=SiderconCapaatos.listadoDeConfiguraciones.listIterator();
        Configuracion conf=new Configuracion();
        switch (bb){
                    case 1:
                        while(icon.hasNext()){
                            conf=(Configuracion)icon.next();
                            if(conf.getNombreConeccion().equals("Coneccion Tango BU")){
                        
                                ConeccionSqlTango.url=conf.getStringDeUrl();
                            }
                        }
                        break;
                    case 2:
                        while(icon.hasNext()){
                            conf=(Configuracion)icon.next();
                            if(conf.getNombreConeccion().equals("Coneccion Tango SD")){
                        
                                ConeccionSqlTango.url=conf.getStringDeUrl();
                            }
                        }
                        break;
                    default:
                        while(icon.hasNext()){
                            conf=(Configuracion)icon.next();
                            if(conf.getNombreConeccion().equals("Coneccion Tango SD_SRL")){
                        
                                ConeccionSqlTango.url=conf.getStringDeUrl();
                            }
                        }
                        break;
                }
        String cadenn=ConeccionSqlTango.url+";user="+ConeccionSqlTango.usuario+";password="+ConeccionSqlTango.clave+";";
        String cadenax="";
        Connection ccnn=null;
        try {
            ccnn = DriverManager.getConnection(cadenn); //this.url,this.usuario,this.clave);
            SiderconCapaatos.falloConecion=0;
        } catch (SQLException ex) {
            SiderconCapaatos.falloConecion=1;
            JOptionPane.showMessageDialog(null,"SE HA DETECTADO UN ERROR EN LA CONEXION AL SERVERTANGO\n CERRAR EL PROCESO Y VOLVER A INTENTARLO","CONEXION A SERVERTANGO ",JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(ConeccionSqlTango.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ccnn;
}    
public static Boolean CerrarConeccion(Connection cn){
    try{
        cn.close();
        return true;
    }catch(Exception ex){
        GuardarMovimientos gArch=new Archivador();
                String cod1=String.valueOf(ex);
                gArch.registrarErrores(cod1, "", "");
        return false;
    }
}
 private void emitirMensaje(){
        JOptionPane.showMessageDialog(null,"","CONEXION A SERVERTANGO ",JOptionPane.ERROR_MESSAGE);
    }
}
