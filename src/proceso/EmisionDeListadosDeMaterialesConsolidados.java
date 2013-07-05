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
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 * @author Administrador
 */
public class EmisionDeListadosDeMaterialesConsolidados extends Thread{
    static Connection cc;
    private String fechaEnvio;
    private Integer numVehiculo;
    private String descVehiculo;
    private Double total;
    private Integer numeroListado;
    private Integer revision;

    public void setRevision(Integer revision) {
        this.revision = revision;
    }

    public void setDescVehiculo(String descVehiculo) {
        this.descVehiculo = descVehiculo;
    }

    public void setFechaEnvio(String fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public void setNumVehiculo(Integer numVehiculo) {
        this.numVehiculo = numVehiculo;
    }

    public void setNumeroListado(Integer numeroListado) {
        this.numeroListado = numeroListado;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
    
    
    public synchronized void run(){
        Map listConsolidado=new HashMap();
        cc=Coneccion.ObtenerConeccion();
        listConsolidado.put("numListado",this.numeroListado);
        listConsolidado.put("totalKg",this.total);
        listConsolidado.put("revision",this.revision);
        System.out.println(fechaEnvio+"uni "+numVehiculo+" desc "+descVehiculo+" kg "+total+"LISTADO NUM"+this.numeroListado);
        String master="C://src//listadosDePreparacion//ListadoDeMaterialesConsolidado.jasper";
        String destino="C://ListadosHdr//"+numeroListado+" R "+this.revision+" Listado consolidado de materiales.pdf";
        JasperReport reporte = null;
        try {
            reporte = (JasperReport)JRLoader.loadObject(master);
        } catch (JRException ex) {
            Logger.getLogger(EmisionDeListadosDeMaterialesConsolidados.class.getName()).log(Level.SEVERE, null, ex);
        }
        JasperPrint jasperPrint = null;
        try {
            jasperPrint = JasperFillManager.fillReport(reporte, listConsolidado,cc);
        } catch (JRException ex) {
            Logger.getLogger(EmisionDeListadosDeMaterialesConsolidados.class.getName()).log(Level.SEVERE, null, ex);
        }
        JRExporter exporter=new JRPdfExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE,new java.io.File(destino));
        try {
            exporter.exportReport();
                     //cnn.close();
        } catch (JRException ex) {
            Logger.getLogger(EmisionDeListadosDeMaterialesConsolidados.class.getName()).log(Level.SEVERE, null, ex);
        }
                 
                 File f=new File(destino);
                 if(f.exists()){
            try {
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+destino);
            } catch (IOException ex) {
                Logger.getLogger(EmisionDeListadosDeMaterialesConsolidados.class.getName()).log(Level.SEVERE, null, ex);
            }
}
    

    }
}
