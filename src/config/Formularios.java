/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import proceso.ConeccionRemotaMyS;

/**
 *
 * @author mauro di
 */
public class Formularios {
    private String consolidado;
    private String detallado;
    private String descarga;
    private String hdr;

    public String getConsolidado() throws SQLException {
            String ruta="";
            String sql="select * from impresiones where numero = 1 and permiso = 1";
            Connection cnR=ConeccionRemotaMyS.ObtenerConeccion();
            Statement st=cnR.createStatement();
            st.execute(sql);
            ResultSet rs=st.getResultSet();
            while(rs.next()){
                ruta=rs.getString("direccion");
            }
            st.close();
            ConeccionRemotaMyS.CerrarConneccion(cnR);
            
            return ruta;
    }

    public String getDetallado() throws SQLException {
            String ruta="";
            String sql="select * from impresiones where numero = 2 and permiso = 1";
            Connection cnR=ConeccionRemotaMyS.ObtenerConeccion();
            Statement st=cnR.createStatement();
            st.execute(sql);
            ResultSet rs=st.getResultSet();
            while(rs.next()){
                ruta=rs.getString("direccion");
                System.out.println("RUTA /////////////////////////////////////"+ruta);
            }
            st.close();
            ConeccionRemotaMyS.CerrarConneccion(cnR);
            
            return ruta;
            
    }

    public String getDescarga() throws SQLException {
            String ruta="";
            String sql="select * from impresiones where numero = 3 and permiso = 1";
            Connection cnR=ConeccionRemotaMyS.ObtenerConeccion();
            Statement st=cnR.createStatement();
            st.execute(sql);
            ResultSet rs=st.getResultSet();
            while(rs.next()){
                ruta=rs.getString("direccion");
            }
            st.close();
            ConeccionRemotaMyS.CerrarConneccion(cnR);
            
            return ruta;
            
    }

    public String getHdr() throws SQLException {
            String ruta="";
            String sql="select * from impresiones where numero = 4 and permiso = 1";
            Connection cnR=ConeccionRemotaMyS.ObtenerConeccion();
            Statement st=cnR.createStatement();
            st.execute(sql);
            ResultSet rs=st.getResultSet();
            while(rs.next()){
                ruta=rs.getString("direccion");
            }
            st.close();
            ConeccionRemotaMyS.CerrarConneccion(cnR);
            
            return ruta;
            
    }
    
}
