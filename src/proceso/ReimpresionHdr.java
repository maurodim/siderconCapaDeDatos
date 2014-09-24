/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proceso;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 * @author Administrador
 */
public class ReimpresionHdr {
    private Integer numero;

    public void setNumero(Integer numero) {
        this.numero = numero;
    }
    public void reimpresion(){
        Map listConsolidado=new HashMap();
        Coneccion con=new Coneccion();
        Connection ch=con.ObtenerConeccion();
        listConsolidado.put("numeroHdr",this.numero);
        //System.out.println(fechaEnvio+" "+numVehiculo+" "+descVehiculo+" "+total);
        String master="C://src//hojasDeRuta//HojaDeRuta.jasper";
        String destino="////COLOSSUS//ventas//Archivos HDR//"+this.numero+"hdr.pdf";
        JasperReport reporte = null;
        try {
            reporte = (JasperReport)JRLoader.loadObject(master);
        } catch (JRException ex) {
            Logger.getLogger(ReimpresionHdr.class.getName()).log(Level.SEVERE, null, ex);
        }
        JasperPrint jasperPrint = null;
        try {
            jasperPrint = JasperFillManager.fillReport(reporte, listConsolidado,ch);
        } catch (JRException ex) {
            Logger.getLogger(ReimpresionHdr.class.getName()).log(Level.SEVERE, null, ex);
        }
        JRExporter exporter=new JRPdfExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE,new java.io.File(destino));
        try {
            exporter.exportReport();
                     //cnn.close();
        } catch (JRException ex) {
            Logger.getLogger(ReimpresionHdr.class.getName()).log(Level.SEVERE, null, ex);
        }
                 
            File f=new File(destino);
            if(f.exists()){
            try {
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+destino);
                System.out.println("RUTA "+destino);
            } catch (IOException ex) {
                Logger.getLogger(ReimpresionHdr.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("NO IMPRIMIO por "+ex);
            }
}

    }
    
}
