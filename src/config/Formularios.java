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
public class Formularios {
    private String consolidado;
    private String detallado;
    private String descarga;
    private String hdr;
    private String consolidadoR;

    public String getConsolidadoR() throws SQLException {
           String ruta="C://src//listadosDePreparacion//ListadoDeMaterialesConsolidadoR.jasper";
            
            return ruta;
    }

    
    public String getConsolidado() throws SQLException {
            String ruta="C://src//listadosDePreparacion//ListadoDeMaterialesConsolidado.jasper";
            
            return ruta;
    }

    public String getDetallado() throws SQLException {
            String ruta="C://src//listadosDePreparacion//revisionDeListados.jasper";
            
            return ruta;
            
    }

    public String getDescarga() throws SQLException {
            String ruta="C://src//listadosDePreparacion//descargaDeMateriales.jasper";
            
            return ruta;
            
    }

    public String getHdr() throws SQLException {
            String ruta="C://src//hojasDeRuta//HojaDeRuta.jasper";
            
            return ruta;
            
    }
    
}
