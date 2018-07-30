/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import java.sql.SQLException;

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
            String ruta="C://src//listadosDePreparacion//ListadoDeMaterialesConsolidado.jasper";
            return ruta;
            
        }        
    },
    DETALLADO(){
        public String direccion() throws SQLException{
            String ruta="C://src//listadosDePreparacion//revisionDeListados.jasper";
            return ruta;
            
        }        
    },
    DESCARGA(){
        public String direccion() throws SQLException{
           String ruta="C://src//listadosDePreparacion//descargaDeMateriales.jasper";
            return ruta;
            
        }        
    },
    HDR(){
        public String direccion() throws SQLException{
             String ruta="C://src//hojasDeRuta//HojaDeRuta.jasper";
            return ruta;
            
        }
    };
    
}
