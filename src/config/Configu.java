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
public enum Configu {
    
    /**
     *
     */
    CONSOLIDADO(){
        public String direccion() throws SQLException{
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
    },
    DETALLADO(){
        public String direccion() throws SQLException{
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
    },
    DESCARGA(){
        public String direccion() throws SQLException{
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
    },
    HDR(){
        public String direccion() throws SQLException{
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
    };
    
}
